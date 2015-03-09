package com.balancedbytes.tools.xbmc;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.Properties;

public class ImportXbmcMovieInfo {
  
  public static final String CSV_SEPARATOR = ";";
  
  public static void main(String[] args) {

    if ((args == null) || (args.length < 2)) {
      System.out.println("Usage: java ImportXbmcMovieInfo <csv_file> <directory>");
      return;
    }
        
    try {
      new ImportXbmcMovieInfo().importInfos(new File(args[0]), new File(args[1]));
    } catch (Exception any) {
      any.printStackTrace();
    }
    
  }
  
  public void importInfos(File csvFile, File dir) throws IOException {
    
    BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(csvFile), Charset.forName("utf-8")));
    String row;
    int rowNr = 0;
    while ((row = in.readLine()) != null) {
      if (rowNr++ > 0) {
        String[] columns = row.split(CSV_SEPARATOR);
        if ((columns == null) || (columns.length < 5)) {
          continue;
        }
        String title = columns[0];
        String year = columns[1];
        String fsk = columns[2];
        String collection = columns[3];
        String part = columns[4];
        String newTitle = null;
        if (columns.length > 5) {
          newTitle = columns[5];
        }
        Properties movieInfo = new Properties();
        if (provided(newTitle)) {
          movieInfo.setProperty("title", newTitle);
        }
        if (provided(collection)) {
          movieInfo.setProperty("collection", collection);
        }
        if (provided(part)) {
          movieInfo.setProperty("part", part);
        }
        if (movieInfo.size() == 0) {
          continue;
        }
        StringBuilder fskPart = new StringBuilder();
        fskPart.append("FSK ");
        if (fsk.length() < 2) {
          fskPart.append("0");
        }
        fskPart.append(fsk);
        File movieDir = new File(dir, fskPart.toString());
        StringBuilder titlePart = new StringBuilder();
        titlePart.append(title);
        if (provided(year)) {
          titlePart.append(" (").append(year).append(")");
        }
        movieDir = new File(movieDir, titlePart.toString());
        movieDir.mkdirs();
        File outFile = new File(movieDir, "info.txt");
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), Charset.forName("utf-8")));
        movieInfo.store(out, null);
        out.close();
      }
    }
    in.close();
    
  }
  
  private boolean provided(String text) {
    return ((text != null) && (text.length() > 0));
  }

}
