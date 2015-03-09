package com.balancedbytes.tools.xbmc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UtilExportXbmc {
  
  private static final Pattern YEAR_PATTERN = Pattern.compile(" \\(([0-9]+)\\)$");
  private static final Pattern SEASON_PATTERN = Pattern.compile("Staffel ([0-9]+)");

  public static String findTitle(String titleString) {
    if ((titleString != null) && (titleString.length() > 7) && (findYear(titleString) > 0)) {
      return titleString.substring(0, titleString.length() - 7);
    }
    return titleString;
  }

  public static int findSeason(String seasonString) {
    if ((seasonString == null) || (seasonString.length() == 0)) {
      return 0;
    }
    Matcher matcher = SEASON_PATTERN.matcher(seasonString);
    if (matcher.find()) {
      String season = matcher.group(1);
      if (season.startsWith("0") && (season.length() > 1)) {
        season = season.substring(1, season.length());
      }
      return Integer.parseInt(season);
    }
    return 0;
  }
    
  public static int findYear(String titleString) {
    if ((titleString == null) || (titleString.length() == 0)) {
      return 0;
    }
    Matcher matcher = YEAR_PATTERN.matcher(titleString);
    if (matcher.find()) {
      return Integer.parseInt(matcher.group(1));
    }
    return 0;
  }
    
  public static int findFSK(String fskString) {
    if ((fskString != null) && (fskString.length() > 5) && fskString.startsWith("FSK ")) {
      if ('0' == fskString.charAt(4)) {
        return Integer.parseInt(fskString.substring(5));
      } else {
        return Integer.parseInt(fskString.substring(4));
      }
    }
    return 0;
  }
  
  public static void collect(File dir, List<File> emptyDirectories) {
    List<File> nonEmptyDirectories = new ArrayList<File>();
    findEmptyDirectories(dir, emptyDirectories, nonEmptyDirectories);
    for (File file : nonEmptyDirectories) {
      collect(file, emptyDirectories);  // recursion
    }
  }
  
  private static void findEmptyDirectories(File dir, List<File> emptyDirectories, List<File> nonEmptyDirectories) {
    if ((emptyDirectories == null) || (nonEmptyDirectories == null)) {
      return;
    }
    for (File file : findDirectories(dir)) {
      if (findDirectories(file).length > 0) {
        nonEmptyDirectories.add(file);
      } else {
        emptyDirectories.add(file);
      }
    }
  }
  
  private static File[] findDirectories(File dir) {
    if ((dir == null) || !dir.isDirectory()) {
      return new File[0];
    }
    List<File> directories = new ArrayList<File>();
    for (File file : dir.listFiles()) {
      if (file.isDirectory()) {
        directories.add(file);
      }
    }
    return directories.toArray(new File[directories.size()]);
  }
  
  public static void writeFile(File file, List<String> rows) throws IOException {
    if ((rows == null) || (rows.size() == 0)) {
      return;
    }
    BufferedWriter out = new BufferedWriter(new FileWriter(file));
    for (String row : rows) {
      out.write(row);
      out.newLine();
    }
    out.close();
  }
  
  public static Properties loadInfoFile(File dir) throws IOException {
    Properties infoProperties = new Properties();
    File infoFile = new File(dir, "info.txt");
    if (infoFile.exists()) {
      BufferedReader infoIn = new BufferedReader(new InputStreamReader(new FileInputStream(infoFile), Charset.forName("utf-8")));
      infoProperties.load(infoIn);
      infoIn.close();
    }
    return infoProperties;
  }
  
}
