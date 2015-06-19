package com.balancedbytes.tools.xbmc;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.SwingWorker;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Tool to rename episodes in a directory according to title names from thetvdb.com.
 * 
 * @author TecBeast
 */
public class RenameXbmcEpisodesTask extends SwingWorker<Void, String>{
  
  public static final String FILE_SUFFIX = ".mkv";
  public static final Pattern FILE_PATTERN = Pattern.compile("^s([0-9]+)e([0-9]+).*");
  
  private File fDir;
  private String fUrl;
  private Document fDocument;
  
  public RenameXbmcEpisodesTask(File dir, String url, Document document) {
    fDir = dir;
    fUrl = url;
    fDocument = document;
  }
  
  @Override
  protected Void doInBackground() throws Exception {
    setProgress(0);
    Map<Integer, String> episodeTitleByNr = scanUrl(fUrl);
    List<File> files = scanDir(fDir);
    for (int i = 0; i < files.size(); i++) {
      setProgress((i + 1) * 100 / files.size());
      Thread.sleep(100);
      File newFile = rename(files.get(i), episodeTitleByNr);
      publish(newFile.getName());
    }
    return null;
  }
  
  @Override
  protected void process(List<String> chunks) {
    for (String message : chunks) {
      try {
        fDocument.insertString(fDocument.getLength(), message + "\n", null);
      } catch (BadLocationException e) {
        // no insert
      }
    }
  }
  
  private File rename(File file, Map<Integer, String> episodeTitleByNr) throws IOException {
    
    if ((file == null) || (episodeTitleByNr == null) || (episodeTitleByNr.size() == 0)) {
      return null;
    }
    
    File newFile = null;
    
    String fileName = file.getName().toLowerCase();
    Matcher matcher = FILE_PATTERN.matcher(fileName);
    if (matcher.matches()) {
      
      String season = matcher.group(1);
      String episode = matcher.group(2);
      int episodeNr = 0;
      if (episode.startsWith("0") && (episode.length() > 1)) {
        episodeNr = Integer.parseInt(episode.substring(1));
      } else {
        episodeNr = Integer.parseInt(episode);
      }
      StringBuilder newFileName = new StringBuilder();
      newFileName.append("S").append(season);
      newFileName.append("E").append((episodeNr < 10) ? "0" : "").append(episodeNr);
      newFileName.append(" ").append(escape(episodeTitleByNr.get(episodeNr)));
      newFileName.append(FILE_SUFFIX);
      
      newFile = new File(file.getParent(), newFileName.toString());
      file.renameTo(newFile);
      
    }
    
    return newFile;
    
  }
    
  private Map<Integer, String> scanUrl(String url) throws IOException {

    Map<Integer, String> episodeTitleByNr = new HashMap<Integer, String>();
    int episodeNr = -1;

    Connection connection = Jsoup.connect(url);
    connection.timeout(10 * 1000);
    org.jsoup.nodes.Document doc = connection.get();
    Elements links = doc.select("a[href*=?tab=episode]");

    for (Element link : links) {
      String linkText = link.text();
      if ((linkText != null) && (linkText.length() > 0)) {
        if (episodeNr < 0) {
          try {
            episodeNr = Integer.parseInt(linkText.trim());
          } catch (NumberFormatException nfe) {
            // do nothing
          }
        } else {
          episodeTitleByNr.put(episodeNr, linkText.trim());
          episodeNr = -1;
        }
      }
    }
    
    return episodeTitleByNr;
    
  }
  
  private List<File> scanDir(File dir) {
    List<File> files = new ArrayList<File>();
    for (File file : dir.listFiles()) {
      if (!file.isFile()) {
        continue;
      }
      String fileName = file.getName().toLowerCase();
      if (!fileName.endsWith(FILE_SUFFIX)) {
        continue;
      }
      Matcher matcher = FILE_PATTERN.matcher(fileName);
      if (matcher.matches()) {
        files.add(file);
      }
    }
    return files;
  }
  
  private String escape(String label) {
    if ((label != null) && (label.length() > 0)) {
      label = label.replaceAll("ä", "ae");
      label = label.replaceAll("Ä", "Ae");
      label = label.replaceAll("ö", "oe");
      label = label.replaceAll("Ö", "Oe");
      label = label.replaceAll("ü", "ue");
      label = label.replaceAll("Ü", "Ue");
      label = label.replaceAll("ß", "ss");
      label = label.replaceAll("\\?", "");
      label = label.replaceAll("/", "_");
      label = label.replaceAll(":", "_");
      label = label.replaceAll("’", "");
    }
    return label;
  }

}