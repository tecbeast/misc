package com.balancedbytes.tools.xbmc;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * 
 * @author TecBeast
 */
public class ExportXbmcMovies {
  
  public static final String CSV_SEPARATOR = ";";
  public static final String HEADER = "Titel;Jahr;FSK;Sammlung;Teil;Verzeichnis";
  
  public static void main(String[] args) {

    if ((args == null) || (args.length < 2)) {
      System.out.println("Usage: java ExportXbmcMovies <directory> <csv_file>");
      return;
    }
        
    try {
      new ExportXbmcMovies().export(new File(args[0]), new File(args[1]));
    } catch (Exception any) {
      any.printStackTrace();
    }
    
  }
  
  public void export(File movieDir, File exportFile) throws IOException {
    
    List<File> emptyDirectories = new ArrayList<File>();
    UtilExportXbmc.collect(movieDir, emptyDirectories);
    
    List<String> exportRows = new ArrayList<String>();
    exportRows.add(HEADER);
    
    Map<String, MoviesInfo> moviesInfo = new HashMap<String, MoviesInfo>();
    for (File dir : emptyDirectories) {
      String[] pathElements = dir.getAbsolutePath().split("\\\\");
      if ((pathElements == null) || (pathElements.length < 2)) {
        continue;
      }
      String title = UtilExportXbmc.findTitle(pathElements[pathElements.length - 1]);
      int year = UtilExportXbmc.findYear(pathElements[pathElements.length - 1]);
      int fsk = UtilExportXbmc.findFSK(pathElements[pathElements.length - 2]);
      MoviesInfo info = new MoviesInfo();
      info.setFile(dir);
      info.setTitle(title);
      info.setYear(year);
      info.setFSK(fsk);
      fillFromInfoFile(dir, info);
      moviesInfo.put(title, info);
    }
    
    System.out.println("Exporting " + emptyDirectories.size() + " movies.");
    
    for (MoviesInfo info : moviesInfo.values()) {
      StringBuilder row = new StringBuilder();
      row.append(info.getTitle());
      row.append(CSV_SEPARATOR);
      if (info.getYear() > 0) {
        row.append(info.getYear());
      }
      row.append(CSV_SEPARATOR);
      row.append(info.getFSK());
      row.append(CSV_SEPARATOR);
      if ((info.getCollection() != null) && (info.getCollection().length() > 0)) {
        row.append(info.getCollection());
      }
      row.append(CSV_SEPARATOR);
      if (info.getPart() > 0) {
        row.append(info.getPart());
      }
      row.append(CSV_SEPARATOR);
      if (info.getFile() != null) {
        row.append(info.getFile().getName());
      }
      exportRows.add(row.toString());
    }
    
    UtilExportXbmc.writeFile(exportFile, exportRows);
    
  }
  
  private void fillFromInfoFile(File dir, MoviesInfo info) throws IOException {
    Properties properties = UtilExportXbmc.loadInfoFile(dir);
    if (properties.containsKey("title")) {
      info.setTitle(properties.getProperty("title"));
    }
    if (properties.containsKey("collection")) {
      info.setCollection(properties.getProperty("collection"));
    }
    if (properties.containsKey("part")) {
      info.setPart(Integer.parseInt(properties.getProperty("part")));
    }
  }

}
