package de.seipler.util.file;

import java.io.*;

public final class FileUtility {

  /**
   * 
   */
  public static int saveFile(File destination, InputStream inStream) throws IOException {

    FileOutputStream outStream = null;
    byte[] buffer = new byte[4096];
    int bytes_read = 0, bytes_total = 0;
    File parent = null;

    try {

      // create all necessary directories first
      parent = new File(destination.getParent());
      parent.mkdirs();

      // copy inputstream to file
      outStream = new FileOutputStream(destination);
      while ((bytes_read = inStream.read(buffer)) != -1) {
        outStream.write(buffer, 0, bytes_read);
        bytes_total += bytes_read;
      }
      return bytes_total;

    } finally {
      // always close your streams
      if (outStream != null) {
        try { outStream.close(); } catch (IOException ignored) { }
      }
    }

  }
    
}