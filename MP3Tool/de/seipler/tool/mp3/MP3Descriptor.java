package de.seipler.tool.mp3;

import helliker.id3.MP3File;

import java.io.File;

/**
 * 
 * @author Georg Seipler
 */
public class MP3Descriptor {
  
  protected static String lineSeparator = System.getProperty("line.separator", "\n");

  private File file;
  private String filename;
	private String album;
  private String artist;
	private String comment;
	private String genre;
  private String title;
	private String track;
	private String year;

  /**
   * Constructor for MP3Descriptor.
   */
  public MP3Descriptor(File file) {
    setFile(file);
    setFilename(file.getName());
  }

  /**
   * Constructor for MP3Descriptor.
   */
  public static MP3Descriptor readFromMP3(File file) throws DescriptorException {
    MP3Descriptor descriptor = new MP3Descriptor(file);
    try {
      MP3File mp3File = new MP3File(file);
      descriptor.setArtist(mp3File.getArtist());
      descriptor.setTitle(mp3File.getTitle());
      descriptor.setAlbum(mp3File.getAlbum());
      descriptor.setYear(mp3File.getYear());
      descriptor.setTrack(mp3File.getTrackString());
      descriptor.setGenre(mp3File.getGenre());
      // use trim to eliminate illegal end of lines
      descriptor.setComment(mp3File.getComment().trim());
      // workaround to bug in mp3 library ?
      // sometimes it returns genre number in brackets in addition to genre name
      int genrePos = descriptor.getGenre().indexOf(')');
      if ((genrePos > 0) && (genrePos < descriptor.getGenre().length())) {
        descriptor.setGenre(descriptor.getGenre().substring(genrePos + 1));
      }
    // FileNotFoundException, NoMPEGFramesException, ID3v2FormatException, CorruptHeaderException, IOException
    } catch (Exception e) {
      throw new DescriptorException(e);
    }
    return descriptor;
  }

  /**
   * Returns the album.
   */
  public String getAlbum() {
    return this.album;
  }

  /**
   * Returns the artist.
   */
  public String getArtist() {
    return this.artist;
  }

	/**
	 * Returns the comment.
	 */
	public String getComment() {
		return this.comment;
	}

  /**
   * Returns the File.
   */
  public File getFile() {
    return this.file;
  }

  /**
   * Returns the Filename.
   */
  public String getFilename() {
    return this.filename;
  }

  /**
   * Returns the genre.
   */
  public String getGenre() {
    return this.genre;
  }

  /**
   * Returns the title.
   */
  public String getTitle() {
    return this.title;
  }

	/**
	 * Returns the track.
	 */
	public String getTrack() {
		return this.track;
	}

  /**
   * Returns the year.
   */
  public String getYear() {
    return this.year;
  }

  /**
   * Sets the album.
   */
  public void setAlbum(String album) {
    this.album = album;
  }

  /**
   * Sets the artist.
   */
  public void setArtist(String artist) {
    this.artist = artist;
  }

	/**
	 * Sets the comment.
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

  /**
   * Sets the File.
   */
  public void setFile(File file) {
    this.file = file;
  }

  /**
   * Sets the Filename.
   */
  public void setFilename(String filename) {
    this.filename = filename;
  }

  /**
   * Sets the genre.
   */
  public void setGenre(String genre) {
    this.genre = genre;
  }

  /**
   * Sets the title.
   */
  public void setTitle(String title) {
    this.title = title;
  }
  
  /**
   * Sets the track.
   */
  public void setTrack(String track) {
    this.track = track;
  }

  /**
   * Sets the year.
   */
  public void setYear(String year) {
    this.year = year;
  }

  /**
   * 
   */
  public String toString() {
    StringBuffer buffer = new StringBuffer();
    buffer.append("filename: ");
    buffer.append(getFilename());
    buffer.append(lineSeparator);
    buffer.append("album   : ");
    buffer.append(getAlbum());
    buffer.append(lineSeparator);
    buffer.append("artist  : ");
    buffer.append(getArtist());
    buffer.append(lineSeparator);
    buffer.append("genre   : ");
    buffer.append(getGenre());
    buffer.append(lineSeparator);
    buffer.append("title   : ");
    buffer.append(getTitle());
    buffer.append(lineSeparator);
    buffer.append("track   : ");
    buffer.append(getTrack());
    buffer.append(lineSeparator);
    buffer.append("year    : ");
    buffer.append(getYear());
    buffer.append(lineSeparator);
    buffer.append("comment : ");
    buffer.append(getComment());
    return buffer.toString();
  }

}
