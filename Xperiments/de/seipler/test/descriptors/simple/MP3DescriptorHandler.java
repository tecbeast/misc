package de.seipler.test.descriptors.simple;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * 
 * @author Georg Seipler
 */
public class MP3DescriptorHandler extends DefaultHandler {

  protected final class Terminal {

    public static final int DESCRIPTOR = 1;
    public static final int FILE = 2;
    public static final int TYPE = 3;
    public static final int ARTIST = 4;
    public static final int TITLE = 5;
    public static final int ALBUM = 6;
    public static final int GENRE = 7;

    private int identifier = 0;
    private String keyword = null;

    public Terminal(int identifier, String keyword) {
      this.identifier = identifier;
      this.keyword = keyword;
    }

    public int getIdentifier() {
      return this.identifier;
    }

    public String getKeyword() {
      return this.keyword;
    }

  }

  protected final class TerminalFactory {

    private Map terminalPerKeyword = null;

    public TerminalFactory() {
      terminalPerKeyword = new HashMap();
      terminalPerKeyword.put("descriptor", new Terminal(Terminal.DESCRIPTOR, "descriptor"));
      terminalPerKeyword.put("file", new Terminal(Terminal.FILE, "file"));
      terminalPerKeyword.put("type", new Terminal(Terminal.TYPE, "type"));
      terminalPerKeyword.put("artist", new Terminal(Terminal.ARTIST, "artist"));
      terminalPerKeyword.put("title", new Terminal(Terminal.TITLE, "title"));
      terminalPerKeyword.put("album", new Terminal(Terminal.ALBUM, "album"));
      terminalPerKeyword.put("genre", new Terminal(Terminal.GENRE, "genre"));
    }

    public Terminal getTerminal(String keyword) {
      return (Terminal) terminalPerKeyword.get(keyword);
    }

  }

  private TerminalFactory terminalFactory;
  private Terminal currentTerminal;
  private MP3Descriptor descriptor;
  private StringBuffer valueBuffer;

  /**
   * Constructor for MP3DescriptorReader.
   */
  public MP3DescriptorHandler() {
    this.terminalFactory = new TerminalFactory();
  }

  /**
   * @see org.xml.sax.ContentHandler#startDocument()
   */
  public void startDocument() throws SAXException {
    this.currentTerminal = null;
    this.descriptor = new MP3Descriptor();
    this.valueBuffer = new StringBuffer();
  }

  /**
   * @see org.xml.sax.ContentHandler#characters(char, int, int)
   */
  public void characters(char[] ch, int start, int length) throws SAXException {
    if (this.currentTerminal != null) {
      valueBuffer.append(new String(ch, start, length));
    }
  }

  /**
   * @see org.xml.sax.ContentHandler#endElement(java.lang.String, java.lang.String, java.lang.String)
   */
  public void endElement(String uri, String localName, String qName) throws SAXException {
    if (this.currentTerminal != null) {
      String value = valueBuffer.toString().trim();
      if (value.length() > 0) {
        switch (this.currentTerminal.getIdentifier()) {
          case Terminal.FILE :
            descriptor.setFile(new File(value));
            break;
          case Terminal.TYPE :
            descriptor.setType(value);
            break;
          case Terminal.ARTIST :
            descriptor.setArtist(value);
            break;
          case Terminal.TITLE :
            descriptor.setTitle(value);
            break;
          case Terminal.ALBUM :
            descriptor.setAlbum(value);
            break;
          case Terminal.GENRE :
            descriptor.setGenre(value);
            break;
        }
      }
    }
    this.valueBuffer = new StringBuffer();
    this.currentTerminal = null;
  }

  /**
   * @see org.xml.sax.ContentHandler#startElement(java.lang.String, java.lang.String, java.lang.String, org.xml.sax.Attributes)
   */
  public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
    this.currentTerminal = this.terminalFactory.getTerminal(qName);
  }

  /**
   * @see org.xml.sax.ContentHandler#endDocument()
   */
  public void endDocument() throws SAXException {
    if (!this.descriptor.isValid()) { this.descriptor = null; }
  }

  /**
   * 
   */
  public MP3Descriptor getDescriptor() {
    return this.descriptor;
  }

}
