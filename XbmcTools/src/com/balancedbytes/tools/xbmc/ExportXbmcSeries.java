package com.balancedbytes.tools.xbmc;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author TecBeast
 */
public class ExportXbmcSeries {
  
  public static final String CSV_SEPARATOR = ";";
  public static final String HEADER = "Titel;Jahr;FSK;Staffel(von);Staffel(bis)";
  
  public static void main(String[] args) {

    if ((args == null) || (args.length < 2)) {
      System.out.println("Usage: java ExportXbmcSeries <directory> <csv_file>");
      return;
    }
        
    try {
      new ExportXbmcSeries().export(new File(args[0]), new File(args[1]));
    } catch (Exception any) {
      any.printStackTrace();
    }
    
  }
  
  public void export(File seriesDir, File exportFile) throws IOException {
    
    List<File> emptyDirectories = new ArrayList<File>();
    UtilExportXbmc.collect(seriesDir, emptyDirectories);
    
    List<String> exportRows = new ArrayList<String>();
    exportRows.add(HEADER);
    
    Map<String, SeriesInfo> seriesInfo = new HashMap<String, SeriesInfo>();
    for (File dir : emptyDirectories) {
      String[] pathElements = dir.getAbsolutePath().split("\\\\");
      if ((pathElements == null) || (pathElements.length < 3)) {
        continue;
      }
      int season = UtilExportXbmc.findSeason(pathElements[pathElements.length - 1]);
      String title = UtilExportXbmc.findTitle(pathElements[pathElements.length - 2]);
      int year = UtilExportXbmc.findYear(pathElements[pathElements.length - 2]);
      int fsk = UtilExportXbmc.findFSK(pathElements[pathElements.length - 3]);
      SeriesInfo info = seriesInfo.get(title);
      if (info == null) {
        info = new SeriesInfo();
        info.setTitle(title);
        info.setYear(year);
        info.setFSK(fsk);
        info.setMinSeason(season);
        info.setMaxSeason(season);
        seriesInfo.put(title, info);
      } else {
        if (season > info.getMaxSeason()) {
          info.setMaxSeason(season);
        }
        if (season < info.getMinSeason()) {
          info.setMinSeason(season);
        }
      }
      
    }
    
    System.out.println("Exporting " + seriesInfo.keySet().size() + " series with " + emptyDirectories.size() + " seasons.");
    
    for (SeriesInfo info : seriesInfo.values()) {
      StringBuilder row = new StringBuilder();
      row.append(info.getTitle());
      row.append(CSV_SEPARATOR);
      if (info.getYear() > 0) {
        row.append(info.getYear());
      }
      row.append(CSV_SEPARATOR);
      row.append(info.getFSK());
      row.append(CSV_SEPARATOR);
      row.append(info.getMinSeason());
      row.append(CSV_SEPARATOR);
      row.append(info.getMaxSeason());
      exportRows.add(row.toString());
    }

    UtilExportXbmc.writeFile(exportFile, exportRows);
    
  }
  
}
