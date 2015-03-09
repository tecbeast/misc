package de.seipler.web.fetchurl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import javax.swing.SwingUtilities;

/**
 * Fetches a given URL with associated links and data.
 * 
 * @author Georg Seipler
 */
public final class FetchUrl {

  //
  // LESSONS LEARNED:
  //
  // - do not ever use URLs as keys since determining hashcode
  //   may involve I/O operations (nameserver lookup ?) and takes
  //   a very variable amount of time to complete !
  //

  private URL startUrl;
  private boolean limitToHost;
  private boolean overrideExtensions;
  private boolean loadData;
  private String topLevelPath;

  private Runnable updateRunner;
  private boolean isStopped;

  private UrlStatusMap statusMap;
  private MimeType mimeType;

  /**
   * 
   */
  public FetchUrl(
    URL startUrl,
    boolean limitToHost,
    boolean noAscend,
    boolean overrideExtensions,
    boolean loadData,
    Runnable updateRunner) {

    this.startUrl = startUrl;
    this.limitToHost = limitToHost;
    this.overrideExtensions = overrideExtensions;
    this.loadData = loadData;
    this.updateRunner = updateRunner;

    if (noAscend) {
      this.topLevelPath = startUrl.getFile();
      int lastSlashPos = topLevelPath.lastIndexOf('/');
      if (lastSlashPos > 0) {
        topLevelPath = topLevelPath.substring(0, lastSlashPos);
      }
    }

    this.statusMap = new UrlStatusMap();
    this.mimeType = new MimeType();

  }

  /**
   * 
   */
  public int downloadAndParsePages(ZipOutputStream zipOut, List linkList, boolean descend) throws IOException {
    
    int nrOfSavedPages = 0;

    int startSize = linkList.size();
    for (int i = 0; i < startSize; i++) {

      URL url = (URL) linkList.get(i);
      UrlStatus status = this.statusMap.get(url);
      if (status == null) { status = new UrlStatus(); }

      if ((status.getType() != UrlStatus.DATA) && (status.getProcessed() < UrlStatus.SAVED)) {

        URLConnection connection = url.openConnection();
        status.update(connection);
        if ((status.getContentType() != null) && (status.getContentType().startsWith("text/html"))) {

          URL base = connection.getURL();
          Reader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
          Page page = Page.read(in, base);
          status.setType(UrlStatus.PAGE);
          
          if (descend) { collectPageLinks(page, linkList); }
          collectPageData(page, linkList); 
          
          String location = findLocation(url, status.getContentType());
          URL alias = this.statusMap.get(location);
          if (alias != null) {
            status = this.statusMap.get(alias);
          } else {
            status.setLocation(location);
            ZipEntry entry = new ZipEntry(location);
            zipOut.putNextEntry(entry);
            page.write(new OutputStreamWriter(zipOut));
            status.setProcessed(UrlStatus.SAVED);
            nrOfSavedPages++;
          }
          this.statusMap.put(page.getBase(), status);

        } else {

          status.setType(UrlStatus.DATA);
          status.setProcessed(UrlStatus.VISITED);

        }

        this.statusMap.put(url, status);

      }

      if (this.updateRunner != null) {
        updateUI();
        if (this.isStopped) { break; }
      }

    }
    
    return nrOfSavedPages;

  }

  /**
   * 
   */
  private void collectPageLinks(Page page, List linkList) {
    Iterator linkIterator = page.getLinkTagList().iterator();
    while (linkIterator.hasNext()) {
      Attribute linkAttribute = ((Tag) linkIterator.next()).getLinkAttribute();
      try {
        URL linkUrl = new URL(page.getBase(), linkAttribute.getValue());
        UrlStatus linkStatus = this.statusMap.get(linkUrl);
        if (linkStatus == null) {
          if (linkUrl.getProtocol().equals("http") || linkUrl.getProtocol().equals("ftp")) {
            if (!this.limitToHost || this.startUrl.getHost().equals(linkUrl.getHost())) {
              if ((this.topLevelPath == null) || ((linkUrl.getFile() != null) && (linkUrl.getFile().startsWith(this.topLevelPath)))) {
                linkList.add(linkUrl);
                linkStatus = new UrlStatus();
                linkStatus.setType(UrlStatus.PAGE);
                linkStatus.setProcessed(UrlStatus.VISITED);
                this.statusMap.put(linkUrl, linkStatus);
              }
            }
          }
        }
      } catch (MalformedURLException skipped) {
      }
    }
  }

  /**
   * 
   */
  private void collectPageData(Page page, List linkList) {
    Iterator dataIterator = page.getDataTagList().iterator();
    while (dataIterator.hasNext()) {
      Attribute dataAttribute = ((Tag) dataIterator.next()).getDataAttribute();
      try {
        URL dataUrl = new URL(page.getBase(), dataAttribute.getValue());
        UrlStatus dataStatus = this.statusMap.get(dataUrl);
        if (dataStatus == null) {
          if (dataUrl.getProtocol().equals("http") || dataUrl.getProtocol().equals("ftp")) {
            if (!this.limitToHost || this.startUrl.getHost().equals(dataUrl.getHost())) {
              linkList.add(dataUrl);
              dataStatus = new UrlStatus();
              dataStatus.setType(UrlStatus.DATA);
              dataStatus.setProcessed(UrlStatus.VISITED);
              this.statusMap.put(dataUrl, dataStatus);
            }
          }
        }
      } catch (MalformedURLException skipped) {
      }
    }
  }

  /**
   * 
   */
  public int transformPages(ZipFile zipFile, boolean changeLinksToAbsolute) throws IOException {

    int nrOfPagesTransformed = 0;

    File tempFile = File.createTempFile("transform", "zip");
    ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(tempFile));

    Enumeration entryEnum = zipFile.entries();
    while (entryEnum.hasMoreElements()) {
      ZipEntry zipEntry = (ZipEntry) entryEnum.nextElement();
      InputStream zipIn = zipFile.getInputStream(zipEntry);
      zipOut.putNextEntry(new ZipEntry(zipEntry.getName()));
      
      UrlStatus status = null;
      URL url = this.statusMap.get(zipEntry.getName());
      if (url != null) { status = this.statusMap.get(url); }
      
      if ((status != null) && (status.getType() == UrlStatus.PAGE) && (status.getProcessed() == UrlStatus.SAVED)) { 
        Page page = Page.read(new InputStreamReader(zipIn), url);
        transformPageLinksAndData(page, changeLinksToAbsolute);
        page.write(new OutputStreamWriter(zipOut));
        nrOfPagesTransformed++;        
      } else {
        copyStream(zipIn, zipOut);
      }

      if (updateRunner != null) {
        updateUI();
        if (this.isStopped) { break; }
      }  
    }

    zipFile.close();
    zipOut.close();

    if (!this.isStopped) {
      File archive = new File(zipFile.getName());
      archive.delete();
      tempFile.renameTo(archive);
    }

    return nrOfPagesTransformed;

  }

  /**
   * 
   */
  private void transformPageLinksAndData(Page page, boolean changeLinksToAbsolute) {

    URL baseUrl = page.getBase();

    // transform all associated links      
    Iterator linkIterator = page.getLinkTagList().iterator();
    while (linkIterator.hasNext()) {
      Attribute linkAttribute = ((Tag) linkIterator.next()).getLinkAttribute();
      try {
        String linkValue = linkAttribute.getValue();
        String transformedLinkValue = transformLink(baseUrl, linkValue, changeLinksToAbsolute);
        if (linkValue != transformedLinkValue) { // object identity !
          linkAttribute.setValue(transformedLinkValue);
        }
      } catch (MalformedURLException skipped) {
      }
    }

    // transform all associated data
    Iterator dataIterator = page.getDataTagList().iterator();
    while (dataIterator.hasNext()) {
      Attribute dataAttribute = ((Tag) dataIterator.next()).getDataAttribute();
      try {
        String dataValue = dataAttribute.getValue();
        String transformedDataValue = transformLink(baseUrl, dataValue, changeLinksToAbsolute);
        if (dataValue != transformedDataValue) { // object identity !
          dataAttribute.setValue(transformedDataValue);
        }
      } catch (MalformedURLException skipped) {
      }
    }

  }

  /**
   * 
   */
  private String transformLink(URL baseUrl, String link, boolean changeToAbsolute) throws MalformedURLException {
    UrlStatus baseStatus = (UrlStatus) this.statusMap.get(baseUrl);
    URL url = new URL(baseUrl, link);
    UrlStatus status = this.statusMap.get(url);
    if ((status != null) && (status.getProcessed() == UrlStatus.SAVED)) {
      StringBuffer transformedLink = new StringBuffer(100);
      transformedLink.append(findRelativePath(status.getLocation(), baseStatus.getLocation()));
      if (url.getRef() != null) {
        transformedLink.append('#');
        transformedLink.append(url.getRef());
      }
      return transformedLink.toString();
    } else if (changeToAbsolute) {
      return url.toString();
    } else {
      return link;
    }
  }

  /**
   * 
   */
  public int downloadDataFiles(ZipOutputStream zipOut, List linkList) throws IOException {

    int nrOfFilesSaved = 0;

    Iterator dataIterator = linkList.iterator();
    while (dataIterator.hasNext()) {

      URL url = (URL) dataIterator.next();
      UrlStatus status = this.statusMap.get(url);
      if ((status != null) && (status.getType() == UrlStatus.DATA) && (status.getProcessed() < UrlStatus.SAVED)) {

        URLConnection connection = url.openConnection();
        status.update(connection);

        String location = findLocation(url, status.getContentType());

        URL alias = this.statusMap.get(location);
        if (alias == null) {
          status.setLocation(location);
          ZipEntry zipEntry = new ZipEntry(location);
          zipOut.putNextEntry(zipEntry);
          int size = copyStream(connection.getInputStream(), zipOut);
          status.setContentLength(size);
          status.setProcessed(UrlStatus.SAVED);
          nrOfFilesSaved++;
        }
      }

      if (updateRunner != null) {
        updateUI();
        if (this.isStopped) {
          break;
        }
      }

    }
    
    return nrOfFilesSaved;

  }

  /**
   * 
   */
  private String findRelativePath(String fileLocation, String baseLocation) {
    StringBuffer relativePath = new StringBuffer();

    String filePath = null;
    File file = new File(fileLocation);
    if (file.isDirectory()) {
      filePath = file.getPath();
    } else {
      filePath = file.getParent();
    }
    String basePath = null;
    File base = new File(baseLocation);
    if (base.isDirectory()) {
      basePath = base.getPath();
    } else {
      basePath = base.getParent();
    }
    int fileStartPos = 0, fileEndPos = 0;
    int baseStartPos = 0, baseEndPos = 0;
    boolean pathsDifferent = false;
    while (baseStartPos < basePath.length()) {
      baseEndPos = basePath.indexOf(File.separatorChar, baseStartPos);
      if (baseEndPos < 0) {
        baseEndPos = basePath.length();
      }
      String basePathPiece = basePath.substring(baseStartPos, baseEndPos);
      String filePathPiece = null;
      if (fileStartPos < filePath.length()) {
        fileEndPos = filePath.indexOf(File.separatorChar, fileStartPos);
        if (fileEndPos < 0) {
          fileEndPos = filePath.length();
        }
        filePathPiece = filePath.substring(fileStartPos, fileEndPos);
        if (!pathsDifferent && !filePathPiece.equals(basePathPiece)) {
          pathsDifferent = true;
        }
      } else {
        pathsDifferent = true;
      }
      if (pathsDifferent) {
        if (filePathPiece != null) {
          if (relativePath.length() > 0) {
            relativePath.append('/');
          }
          relativePath.append(filePathPiece);
        }
        relativePath.insert(0, '/');
        relativePath.insert(0, "..");
      }
      fileStartPos = fileEndPos + 1;
      baseStartPos = baseEndPos + 1;
    }
    if (fileStartPos < filePath.length()) {
      if ((relativePath.length() > 0) && (relativePath.charAt(relativePath.length() - 1) != '/')) {
        relativePath.append('/');
      }
      relativePath.append(filePath.substring(fileStartPos).replace(File.separatorChar, '/'));
    }
    if ((relativePath.length() > 0) && (relativePath.charAt(relativePath.length() - 1) != '/')) {
      relativePath.append('/');
    }
    relativePath.append(file.getName());

    return relativePath.toString();
  }

  /**
   * 
   */
  private String findLocation(URL url, String type) {
    StringBuffer filename = new StringBuffer();

    filename.append(url.getHost());
    filename.append(File.separator);
    String urlFile = url.getFile();
    for (int i = 0; i < urlFile.length(); i++) {
      if (urlFile.charAt(i) == '/') {
        if (i > 0) {
          filename.append(File.separatorChar);
        }
      } else if ((urlFile.charAt(i) == '?') || (urlFile.charAt(i) == ':')) {
        if (i < urlFile.length() - 1) {
          filename.append(File.separatorChar);
        }
      } else {
        filename.append(urlFile.charAt(i));
      }
    }
    if ((urlFile.length() == 0) || urlFile.endsWith("/")) {
      filename.append("index.");
      filename.append(mimeType.getExtension("text/html"));
    } else {
      String extension = mimeType.getExtension(type);
      if (extension != null) {
        int dot = filename.length() - 1;
        while ((dot >= 0) && (filename.charAt(dot) != '.')) {
          dot--;
        }
        if (dot + MimeType.MAX_LENGTH + 1 < filename.length()) {
          filename.append('.');
          filename.append(extension);
        } else if (this.overrideExtensions) {
          filename.delete(dot + 1, filename.length());
          filename.append(extension);
        }
      }
    }

    return filename.toString();
  }

  /**
   * 
   */
  public int findNumberOfUnsavedDataLinks(List linkList) {
    int nrOfDataLinks = 0;
    Iterator dataIterator = linkList.iterator();
    while (dataIterator.hasNext()) {
      UrlStatus status = this.statusMap.get((URL) dataIterator.next());
      if ((status != null) && (status.getType() == UrlStatus.DATA) && (status.getProcessed() < UrlStatus.SAVED)) { nrOfDataLinks++; }
    }
    return nrOfDataLinks;
  }

  /**
   * 
   */
  public int findNumberOfUnsavedPageLinks(List linkList) {
    int nrOfPageLinks = 0;
    Iterator dataIterator = linkList.iterator();
    while (dataIterator.hasNext()) {
      UrlStatus status = this.statusMap.get((URL) dataIterator.next());
      if ((status != null) && (status.getType() == UrlStatus.PAGE) && (status.getProcessed() < UrlStatus.SAVED)) { nrOfPageLinks++; }
    }
    return nrOfPageLinks;
  }

  /**
   * 
   */
  public void setIsStopped(boolean isStopped) {
    this.isStopped = isStopped;
  }

  /**
   * 
   */
  private void updateUI() {
    try {
      SwingUtilities.invokeAndWait(updateRunner);
    } catch (InterruptedException ignored) {
    } catch (InvocationTargetException ignored) {
    }
  }

  /**
   * 
   */
  private int copyStream(InputStream in, OutputStream out) throws IOException {
    int bytes_read = 0, bytes_total = 0;
    byte[] buffer = new byte[4096];
    while ((bytes_read = in.read(buffer)) != -1) {
      out.write(buffer, 0, bytes_read);
      bytes_total += bytes_read;
    }
    return bytes_total;
  }

}
