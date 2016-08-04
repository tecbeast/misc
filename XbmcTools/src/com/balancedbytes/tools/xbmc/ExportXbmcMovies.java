package com.balancedbytes.tools.xbmc;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.jcraft.jsch.ChannelSftp.LsEntry;

/**
 * 
 * @author TecBeast
 */
public class ExportXbmcMovies implements IExportXbmc {
  
  public static final String HEADER = "Titel;Jahr;FSK;Verzeichnis";
  public static final String SFTP_DIR = "/var/media/Xbmc 01/Filme";
  
  public static void main(String[] args) {

    if ((args == null) || (args.length < 1)) {
      System.out.println("Usage: java ExportXbmcMovies <csv_file>");
      return;
    }
        
    try {
      new ExportXbmcMovies().export(new File(args[0]));
    } catch (Exception any) {
      any.printStackTrace(System.err);
    }
    
  }
  
  public void export(File exportFile) throws IOException {

    System.out.println("Collecting movies from XBMC via SFTP");

    List<String> dirs = sftpCollectDirs();

    System.out.println("Writing output file " + exportFile.getName());

    List<String> exportRows = new ArrayList<String>();
    exportRows.add(HEADER);
    
    List<MoviesInfo> moviesInfo = new ArrayList<MoviesInfo>();
    for (String dir : dirs) {
      String[] pathElements = dir.split(DIR_SEPARATOR);
      if ((pathElements == null) || (pathElements.length < 2)) {
        continue;
      }
      String title = UtilExportXbmc.findTitle(pathElements[pathElements.length - 1]);
      int year = UtilExportXbmc.findYear(pathElements[pathElements.length - 1]);
      int fsk = UtilExportXbmc.findFSK(pathElements[pathElements.length - 2]);
      MoviesInfo info = new MoviesInfo();
      info.setFilename(dir);
      info.setTitle(title);
      info.setYear(year);
      info.setFSK(fsk);
      moviesInfo.add(info);
    }

    Collections.sort(moviesInfo, new Comparator<MoviesInfo>() {
      @Override
      public int compare(MoviesInfo pO1, MoviesInfo pO2) {
        return pO1.getTitle().compareToIgnoreCase(pO2.getTitle());
      }
    });
    
    for (MoviesInfo info : moviesInfo) {
      StringBuilder row = new StringBuilder();
      row.append(info.getTitle());
      row.append(CSV_SEPARATOR);
      if (info.getYear() > 0) {
        row.append(info.getYear());
      }
      row.append(CSV_SEPARATOR);
      row.append(info.getFSK());
      row.append(CSV_SEPARATOR);
      row.append(info.getFilename());
      exportRows.add(row.toString());
    }
    
    UtilExportXbmc.writeFile(exportFile, exportRows);

    System.out.println("Exported " + dirs.size() + " movies");

  }
  
  private List<String> sftpCollectDirs() {
    List<String> result = new ArrayList<>();
    SftpConnector sftp = new SftpConnector(SFTP_HOST, SFTP_PORT, SFTP_USER, SFTP_PASSWORD);
    try {
      sftp.connect();
      List<LsEntry> fskEntries = sftp.ls(SFTP_DIR);
      for (LsEntry fskEntry : fskEntries) {
        if (UtilExportXbmc.isValidDirEntry(fskEntry)) {
          String path = SFTP_DIR + DIR_SEPARATOR + fskEntry.getFilename();
          List<LsEntry> movieEntries = sftp.ls(path);
          for (LsEntry movieEntry : movieEntries) {
            if (UtilExportXbmc.isValidDirEntry(movieEntry)) {
              result.add(path + DIR_SEPARATOR + movieEntry.getFilename());
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
