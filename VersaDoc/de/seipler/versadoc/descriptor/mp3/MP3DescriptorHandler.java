package de.seipler.versadoc.descriptor.mp3;

import java.io.File;

import de.seipler.versadoc.descriptor.Descriptor;
import de.seipler.versadoc.descriptor.DescriptorHandler;
import de.seipler.versadoc.util.XMLFormatUtil;

/**
 * 
 * @author Georg Seipler
 */
public class MP3DescriptorHandler extends DescriptorHandler {

  private MP3Descriptor currentDescriptor;
  private StringBuffer valueBuffer;

  /**
   * Constructor for MP3DescriptorReader.
   */
  public MP3DescriptorHandler() {    
  }

  /**
   * @see org.xml.sax.ContentHandler#startDocument()
   */
  public void startDescriptor() {
    this.currentDescriptor = new MP3Descriptor();
    this.valueBuffer = new StringBuffer();
  }

  /**
   * @see org.xml.sax.ContentHandler#characters(char, int, int)
   */
  public void characters(char[] ch, int start, int length) {
    valueBuffer.append(new String(ch, start, length));
  }

  /**
   * @see org.xml.sax.ContentHandler#endElement(java.lang.String, java.lang.String, java.lang.String)
   */
  public void endElement(String name) {
	  String value = valueBuffer.toString().trim();
    if ("filename".equals(name)) {
      if (getFile().isDirectory()) {
        this.currentDescriptor.setFile(new File(getFile(), value));
      } else {
        this.currentDescriptor.setFile(new File(getFile().getParentFile(), value));
      }
    } else if ("last-modified".equals(name)) {
      this.currentDescriptor.setLastModified(XMLFormatUtil.fromTimestamp(value));
    } else if ("author".equals(name)) {
      this.currentDescriptor.setAuthor(value);
    } else if ("artist".equals(name)) {
      this.currentDescriptor.setArtist(value);
    } else if ("title".equals(name)) {
      this.currentDescriptor.setTitle(value);
    } else if ("album".equals(name)) {
      this.currentDescriptor.setAlbum(value);
    } else if ("year".equals(name)) {
      this.currentDescriptor.setYear(value);
    } else if ("track".equals(name)) {
    	this.currentDescriptor.setTrack(value);
    } else if ("genre".equals(name)) {
      this.currentDescriptor.setGenre(value);
    } else if ("comment".equals(name)) {
    	this.currentDescriptor.setComment(value);
    }
    this.valueBuffer = new StringBuffer();
  }

  /**
   * 
   */
  public Descriptor getDescriptor() {
    return this.currentDescriptor;
  }

  /**
   * 
   */
  public String getType() {
    return "audio/mpeg";
  }

}
