package com.balancedbytes.tools.xbmc;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

  public static long DELAY = 100;  // delay in ms
  
  public static final String FILE_SUFFIX = ".mkv";
  public static final Pattern FILE_PATTERN = Pattern.compile("^s([0-9]+)e([0-9]+).*");
  
  private File fDir;
  private String fUrl;
  private Document fDocument;
  private String fSeason;
  
  public RenameXbmcEpisodesTask(File dir, String url, Document document, String season) {
    fDir = dir;
    fUrl = url;
    fDocument = document;
    fSeason = season;
  }
  
  @Override
  protected Void doInBackground() throws Exception {
    setProgress(0);
    Map<Integer, String> episodeTitleByNr = scanUrl(fUrl);
    List<File> files = scanDir(fDir);
    for (int i = 0; i < files.size(); i++) {
      setProgress((i + 1) * 100 / files.size());
      if (DELAY > 0) {
        Thread.sleep(DELAY);
      }
      File newFile = rename(files.get(i), episodeTitleByNr, i + 1);
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
  
  private File rename(File file, Map<Integer, String> episodeTitleByNr, int episodeNr) throws IOException {
    
    if ((file == null) || (episodeTitleByNr == null) || (episodeTitleByNr.size() == 0)) {
      return null;
    }
    
    File newFile = null;
    String mySeason = fSeason;
    int myEpisodeNr = episodeNr;
    
    if ((mySeason == null) || (mySeason.length() == 0)) {
      String fileName = file.getName().toLowerCase();
      Matcher matcher = FILE_PATTERN.matcher(fileName);
      if (matcher.matches()) {
        mySeason = matcher.group(1);
        String episode = matcher.group(2);
        myEpisodeNr = 0;
        if (episode.startsWith("0") && (episode.length() > 1)) {
          myEpisodeNr = Integer.parseInt(episode.substring(1));
        } else {
          myEpisodeNr = Integer.parseInt(episode);
        }
      }
    }
    
    if ((mySeason != null) && (mySeason.length() > 0)) {
      StringBuilder newFileName = new StringBuilder();
      newFileName.append("S");
      if (mySeason.length() == 1) {
        newFileName.append("0");
      }
      newFileName.append(mySeason);
      newFileName.append("E").append((myEpisodeNr < 10) ? "0" : "").append(myEpisodeNr);
      newFileName.append(" ").append(escape(episodeTitleByNr.get(myEpisodeNr)));
      newFileName.append(FILE_SUFFIX);
      newFile = new File(file.getParent(), newFileName.toString());
      // System.out.println("Rename " + file.getName() + " to " + newFile.getName());
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
      if ((fSeason != null) && (fSeason.length() > 0)) {
        files.add(file);
      } else {
        Matcher matcher = FILE_PATTERN.matcher(fileName);
        if (matcher.matches()) {
          files.add(file);
        }
      }
    }
    Collections.sort(files, new Comparator<File>() {
      public int compare(File pO1, File pO2) {
        return pO1.getName().compareTo(pO2.getName());
      }
    });
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