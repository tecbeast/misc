package de.seipler.web.fetchurl;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

public class UrlStatus {

	public static final int UNPROCESSED = 0;
	public static final int VISITED = 1;
	public static final int SAVED = 2;
	public static final int TRANSFORMED = 3;
  
  public static final int UNKNOWN = 0;
  public static final int PAGE = 1;
  public static final int DATA = 2; 

  private URL url;
  private String contentType;
  private String contentEncoding;
  private int contentLength;
  private long date;
  private long lastModified;
  private long expiration;
  
  private String location; // file location (relative dir)
  private int processed;  // state of processing
  private int type;   

  /**
   * 
   */
  public UrlStatus() {
    
    setUrl(null);
    setContentType(null);
    setContentEncoding(null);
    setContentLength(-1);
    setDate(-1);
    setLastModified(-1);
		setExpiration(-1);
    setType(UNKNOWN);
   	setProcessed(UNPROCESSED);
   	
  }

	/**
	 * 
	 */
	public void update(URLConnection connection) throws IOException {
    
    connection.connect();  // just to be sure - won't do it twice

		setUrl(connection.getURL());
		setContentType(connection.getContentType());
		setContentEncoding(connection.getContentEncoding());
		setContentLength(connection.getContentLength());
		if (connection.getDate() > 0) { setDate(connection.getDate()); } 
		if (connection.getLastModified() > 0) { setLastModified(connection.getLastModified()); } 
		if (connection.getExpiration() > 0) { setExpiration(connection.getExpiration()); } 
    
	}

  /**
   * 
   */
  public boolean equals(Object object) {
    if (object instanceof UrlStatus) {
      UrlStatus otherStatus = (UrlStatus) object;
      return otherStatus.getUrl().equals(getUrl());
    }
    return false;
  }

  /**
   * 
   */
  public int hashCode() {
    return getUrl().hashCode();
  }

  /**
   * 
   */
  public String getEncoding() {
    return this.contentEncoding;
  }

  /**
   * 
   */
  public long getExpiration() {
    return this.expiration;
  }

  /**
   * 
   */
  public long getDate() {
    return this.date;
  }

  /**
   * 
   */
  public String getLocation() {
    return this.location;
  }

  /**
   * 
   */
  public long getLastModified() {
    return this.lastModified;
  }

  /**
   * 
   */
  public int getContentLength() {
    return this.contentLength;
  }

  /**
   * 
   */
  public String getContentType() {
    return this.contentType;
  }

  /**
   * 
   */
  public int getType() {
    return this.type;
  }

  /**
   * 
   */
  public URL getUrl() {
    return this.url;
  }

  /**
   *
   */
  public int getProcessed() {
    return this.processed;
  }

  /**
   * 
   */
  protected void setContentEncoding(String contentEncoding) {
    this.contentEncoding = contentEncoding;
  }

  /**
   * 
   */
  protected void setExpiration(long expiration) {
    this.expiration = expiration;
  }

  /**
   * 
   */
  protected void setDate(long date) {
    this.date = date;
  }

  /**
   * 
   */
  public void setLocation(String location) {
    this.location = location;
  }

  /**
   * 
   */
  protected void setLastModified(long lastModified) {
    this.lastModified = lastModified;
  }

  /**
   * 
   */
  public void setContentLength(int contentLength) {
    this.contentLength = contentLength;
  }

  /**
   * 
   */
  protected void setContentType(String contentType) {
    this.contentType = contentType;
  }
  
  /**
   * 
   */
  protected void setUrl(URL url) {
    this.url = url;
  }

  /**
   *
   */
  public void setProcessed(int processed) {
    this.processed = processed;
  }

  /**
   *
   */
  public void setType(int type) {
    this.type = type;
  }

}