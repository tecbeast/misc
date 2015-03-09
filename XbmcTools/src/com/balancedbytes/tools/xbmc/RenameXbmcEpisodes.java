package com.balancedbytes.tools.xbmc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Tool to rename episodes in a directory according to title names from thetvdb.com.
 * 
 * @author TecBeast
 */
public class RenameXbmcEpisodes {
  
  public static final String FILE_SUFFIX = ".mkv";

  public static void main(String[] args) throws IOException {

    if ((args == null) || (args.length < 1)) {
      System.out.println("Usage: java RenameXbmcEpisodes <directory>");
      return;
    }
   
    File dir = new File(args[0]);
    String url = readUrlFromInternetShortcut(new File(dir, "thetvdb.url"));
    
    if ((url == null) || (url.length() == 0)) {
      System.err.println("Unable to open URL");
      return;
    }
    
    System.out.println("Fetching url " +  url);

    Map<Integer, String> episodeTitleByNr = scanUrl(url);

    Pattern pattern = Pattern.compile("^s([0-9]+)e([0-9]+).*");
    
    for (File file : dir.listFiles()) {
      if (!file.isFile()) {
        continue;
      }
      String oldFileName = file.getName().toLowerCase();
      if (!oldFileName.endsWith(FILE_SUFFIX)) {
        continue;
      }
      Matcher matcher = pattern.matcher(oldFileName);
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
        File newFile = new File(file.getParent(), newFileName.toString());
        System.out.println(newFile.getAbsolutePath());
        file.renameTo(newFile);
      }
    }
    
  }
  
  private static Map<Integer, String> scanUrl(String url) throws IOException {

    Map<Integer, String> episodeTitleByNr = new HashMap<Integer, String>();
    int episodeNr = -1;

    Document doc = Jsoup.connect(url).get();
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
  
  private static String readUrlFromInternetShortcut(File file) {
    BufferedReader in = null;
    try {
      String line = null;
      in = new BufferedReader(new FileReader(file));
      while ((line = in.readLine()) != null) {
        if ((line.length() > 5) && "url=".equalsIgnoreCase(line.substring(0, 4))) {
          return line.substring(4);
        }
      }
      return null;
    } catch (IOException ioEx) {
      return null;
    } finally {
      if (in != null) {
        try { in.close(); } catch (Exception ioEx) { }
      }
    }
  }
  
  private static String escape(String label) {
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
      label = label.replaceAll("’", "");
    }
    return label;
  }

}