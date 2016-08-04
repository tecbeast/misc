package com.balancedbytes.tools.xbmc;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.SftpATTRS;

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
  
  public static boolean isValidDirEntry(LsEntry lsEntry) {
    if (lsEntry == null) {
      return false;
    }
    SftpATTRS attributes = lsEntry.getAttrs();
    String filename = lsEntry.getFilename();
    return ((attributes != null) && attributes.isDir() && (filename != null) && !".".equals(filename) && !"..".equals(filename));
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
  
}
