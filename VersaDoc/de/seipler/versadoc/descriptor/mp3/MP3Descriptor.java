package de.seipler.versadoc.descriptor.mp3;

import java.io.IOException;
import java.io.OutputStream;

import de.seipler.versadoc.descriptor.Descriptor;
import de.seipler.versadoc.util.XMLFormatUtil;

/**
 * 
 * @author Georg Seipler
 */
public class MP3Descriptor extends Descriptor {
  
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
  protected MP3Descriptor() {
  	super();
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
   * Returns the type (constant "audio/mpeg").
   */
  public String getType() {
    return "audio/mpeg";
  }

  /**
   * Sets the album.
   */
  protected void setAlbum(String album) {
    this.album = album;
  }

  /**
   * Sets the artist.
   */
  protected void setArtist(String artist) {
    this.artist = artist;
  }

	/**
	 * Sets the comment.
	 */
	protected void setComment(String comment) {
		this.comment = comment;
	}

  /**
   * Sets the genre.
   */
  protected void setGenre(String genre) {
    this.genre = genre;
  }

  /**
   * Sets the title.
   */
  protected void setTitle(String title) {
    this.title = title;
  }
  
  /**
   * Sets the track.
   */
  protected void setTrack(String track) {
    this.track = track;
  }

  /**
   * Returns the year.
   */
  public String getYear() {
    return this.year;
  }

  /**
   * Sets the year.
   */
  protected void setYear(String year) {
    this.year = year;
  }

	/**
	 * 
	 */
	public void writeTo(OutputStream out, int indentation) throws IOException {
		XMLFormatUtil.writeElementHeaderTo(out, indentation, "descriptor", "type", getType());
		
    String filename = null; if (getFile() != null) { filename = getFile().getName(); }
		XMLFormatUtil.writeElementTo(out, indentation + 1, "filename", filename);
		XMLFormatUtil.writeElementTo(out, indentation + 1, "last-modified", XMLFormatUtil.toTimestamp(getLastModified()));
		XMLFormatUtil.writeElementTo(out, indentation + 1, "author", getAuthor());
		
		XMLFormatUtil.writeElementTo(out, indentation + 1, "album", getAlbum());
		XMLFormatUtil.writeElementTo(out, indentation + 1, "artist", getArtist());
		XMLFormatUtil.writeElementTo(out, indentation + 1, "genre", getGenre());
		XMLFormatUtil.writeElementTo(out, indentation + 1, "title", getTitle());
		XMLFormatUtil.writeElementTo(out, indentation + 1, "track", getTrack());
		XMLFormatUtil.writeElementTo(out, indentation + 1, "year", getYear());
		XMLFormatUtil.writeElementTo(out, indentation + 1, "comment", getComment());
		
		XMLFormatUtil.writeElementFooterTo(out, indentation, "descriptor");
	}

}
