package de.seipler.test.descriptors.zip;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 
 * @author Georg Seipler
 */
public class MP3Descriptor {

  public static String mp3MimeType = "audio/mpeg";
  public static String lineSeparator = System.getProperty("line.separator", "\n");

  private File file;
  private String type;
  private String artist;
  private String title;
  private String album;
  private String genre;

  /**
   * Constructor for MP3Descriptor.
   */
  protected MP3Descriptor() {
    super();
  }

  /**
   * Returns the File.
   * @return File
   */
  public File getFile() {
    return this.file;
  }

  /**
   * 
   */
  public String getType() {
    return this.type;
  }

  /**
   * Returns the album.
   * @return String
   */
  public String getAlbum() {
    return this.album;
  }

  /**
   * Returns the artist.
   * @return String
   */
  public String getArtist() {
    return this.artist;
  }

  /**
   * Returns the genre.
   * @return String
   */
  public String getGenre() {
    return this.genre;
  }

  /**
   * Returns the title.
   * @return String
   */
  public String getTitle() {
    return this.title;
  }

  /**
   * 
   */
  public void writeTo(OutputStream out) throws IOException {
    StringBuffer buffer = new StringBuffer(1024);    
    buffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
    buffer.append(lineSeparator);
    buffer.append("<descriptor>");
    buffer.append(lineSeparator);
    buffer.append("  <file>");
    if (getFile() != null) {
      buffer.append(XMLFormatter.toXML(getFile().getPath()));
    }
    buffer.append("</file>");
    buffer.append(lineSeparator);
    buffer.append("  <type>");
    if (getType() != null) {
      buffer.append(XMLFormatter.toXML(getType()));
    }
    buffer.append("</type>");
    buffer.append(lineSeparator);
    buffer.append("  <artist>");
    if (getArtist() != null) {
      buffer.append(XMLFormatter.toXML(getArtist()));
    }
    buffer.append("</artist>");
    buffer.append(lineSeparator);
    buffer.append("  <title>");
    if (getTitle() != null) {
      buffer.append(XMLFormatter.toXML(getTitle()));
    }
    buffer.append("</title>");
    buffer.append(lineSeparator);
    buffer.append("  <album>");
    if (getAlbum() != null) {
      buffer.append(XMLFormatter.toXML(getAlbum()));
    }
    buffer.append("</album>");
    buffer.append(lineSeparator);
    buffer.append("  <genre>");
    if (getGenre() != null) {
      buffer.append(XMLFormatter.toXML(getGenre()));
    }
    buffer.append("</genre>");
    buffer.append(lineSeparator);
    buffer.append("</descriptor>");
    buffer.append(lineSeparator);
    out.write(buffer.toString().getBytes("UTF-8"));
  }

  /**
   * Sets the type.
   * @param type The type to set
   */
  protected void setType(String type) {
    if (mp3MimeType.equalsIgnoreCase(type)) {
      this.type = mp3MimeType;
    } else {
      this.type = type;
    }
  }

  /**
   * Sets the album.
   * @param album The album to set
   */
  protected void setAlbum(String album) {
    this.album = album;
  }

  /**
   * Sets the artist.
   * @param artist The artist to set
   */
  protected void setArtist(String artist) {
    this.artist = artist;
  }

  /**
   * Sets the file.
   * @param file The file to set
   */
  protected void setFile(File file) {
    this.file = file;
  }

  /**
   * Sets the genre.
   * @param genre The genre to set
   */
  protected void setGenre(String genre) {
    this.genre = genre;
  }

  /**
   * Sets the title.
   * @param title The title to set
   */
  protected void setTitle(String title) {
    this.title = title;
  }
  
  /**
   * 
   */
  public boolean isValid() {
    return (
      (getFile() != null) && (getType() == mp3MimeType)
    );
  }
  
}
