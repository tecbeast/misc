package com.balancedbytes.tools.xbmc;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jcraft.jsch.ChannelSftp.LsEntry;

/**
 * 
 * @author TecBeast
 */
public class ExportXbmcSeries implements IExportXbmc {
  
  public static final String HEADER = "Titel;Jahr;FSK;Staffeln;von;bis";
  public static final String SFTP_DIR = "/var/media/Xbmc 02/Serien";
  
  public static void main(String[] args) {

    if ((args == null) || (args.length < 1)) {
      System.out.println("Usage: java ExportXbmcSeries <csv_file>");
      return;
    }
        
    try {
      new ExportXbmcSeries().export(new File(args[0]));
    } catch (Exception any) {
      any.printStackTrace(System.err);
    }
    
  }
  
  public void export(File exportFile) throws IOException {
    
    System.out.println("Collecting series from XBMC via SFTP");

    List<String> dirs = sftpCollectDirs();
    
    System.out.println("Writing output file " + exportFile.getName());

    List<String> exportRows = new ArrayList<String>();
    exportRows.add(HEADER);
    
    Map<String, SeriesInfo> seriesInfo = new HashMap<String, SeriesInfo>();
    for (String dir : dirs) {
      String[] pathElements = dir.split(DIR_SEPARATOR);
      if ((pathElements == null) || (pathElements.length < 3)) {
        continue;
      }
      // System.out.println(dir);
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
        info.setNrSeasons(1);
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
        info.setNrSeasons(info.getNrSeasons() + 1);
      }
      
    }
    
    List<String> keys = new ArrayList<String>(seriesInfo.keySet());
    Collections.sort(keys);
    
    for (String key : keys) {
      SeriesInfo info = seriesInfo.get(key);
      StringBuilder row = new StringBuilder();
      row.append(info.getTitle());
      row.append(CSV_SEPARATOR);
      if (info.getYear() > 0) {
        row.append(info.getYear());
      }
      row.append(CSV_SEPARATOR);
      row.append(info.getFSK());
      row.append(CSV_SEPARATOR);
      row.append(info.getNrSeasons());
      row.append(CSV_SEPARATOR);
      row.append(info.getMinSeason());
      row.append(CSV_SEPARATOR);
      row.append(info.getMaxSeason());
      exportRows.add(row.toString());
    }

    UtilExportXbmc.writeFile(exportFile, exportRows);
    
    System.out.println("Exported " + seriesInfo.keySet().size() + " series with " + dirs.size() + " seasons");

  }
  
  private List<String> sftpCollectDirs() {
    List<String> result = new ArrayList<String>();
    SftpConnector sftp = new SftpConnector(SFTP_HOST, SFTP_PORT, SFTP_USER, SFTP_PASSWORD);
    try {
      sftp.connect();
      List<LsEntry> fskEntries = sftp.ls(SFTP_DIR);
      for (LsEntry fskEntry : fskEntries) {
        if (UtilExportXbmc.isValidDirEntry(fskEntry)) {
          String seriesPath = SFTP_DIR + DIR_SEPARATOR + fskEntry.getFilename();
          List<LsEntry> seriesEntries = sftp.ls(seriesPath);
          for (LsEntry seriesEntry : seriesEntries) {
            if (UtilExportXbmc.isValidDirEntry(seriesEntry)) {
              String seasonPath = seriesPath + DIR_SEPARATOR + seriesEntry.getFilename();
              List<LsEntry> seasonEntries = sftp.ls(seasonPath);
              for (LsEntry seasonEntry : seasonEntries) {
                if (UtilExportXbmc.isValidDirEntry(seasonEntry)) {
                  result.add(seasonPath + DIR_SEPARATOR + seasonEntry.getFilename());
                }
              }
            }
          }
        }
      }
    } catch (Exception any) {
      any.printStackTrace(System.err);
    } finally {
      sftp.disconnect();
    }
    return result;
  }
  
}
