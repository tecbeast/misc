package de.seipler.test.descriptors.simple;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 
 * @author Georg Seipler
 */
public class MP3Descriptor {

  public static String mp3MimeType = "audio/mpeg";

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
  public void saveTo(File xmlFile) throws IOException {
    PrintWriter out = new PrintWriter(new FileWriter(xmlFile));
    out.print("<?xml version=\"1.0\"?>");
    out.println();
    out.print("<descriptor>");
    out.println();
    out.print("  <file>");
    if (getFile() != null) {
      out.print(XMLFormatter.toXML(getFile().getPath()));
    }
    out.print("</file>");
    out.println();
    out.print("  <type>");
    if (getType() != null) {
      out.print(XMLFormatter.toXML(getType()));
    }
    out.print("</type>");
    out.println();
    out.print("  <artist>");
    if (getArtist() != null) {
      out.print(XMLFormatter.toXML(getArtist()));
    }
    out.print("</artist>");
    out.println();
    out.print("  <title>");
    if (getTitle() != null) {
      out.print(XMLFormatter.toXML(getTitle()));
    }
    out.print("</title>");
    out.println();
    out.print("  <album>");
    if (getAlbum() != null) {
      out.print(XMLFormatter.toXML(getAlbum()));
    }
    out.print("</album>");
    out.println();
    out.print("  <genre>");
    if (getGenre() != null) {
      out.print(XMLFormatter.toXML(getGenre()));
    }
    out.print("</genre>");
    out.println();
    out.print("</descriptor>");
    out.println();
    out.close();
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
