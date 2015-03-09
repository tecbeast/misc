package de.seipler.web.fetchurl;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Georg Seipler
 */
public class UrlStatusMap {

	private Map statusPerUrl;
  private Map urlPerLocation;

	/**
	 * Default constructor.
	 */
	public UrlStatusMap() {
		this.statusPerUrl = new HashMap();
    this.urlPerLocation = new HashMap();
	}

	/**
	 * 
	 */
	public UrlStatus get(URL key) {
		return (UrlStatus) this.statusPerUrl.get(transformUrlWithoutAnchor(key));
	}

  /**
   * 
   */
  public URL get(String location) {
    URL url = null;
    String urlString = (String) this.urlPerLocation.get(location);
    if (urlString != null) {
      try { url = new URL(urlString); } catch (MalformedURLException impossible) { }
    }
    return url;
  }

	/**
	 * 
	 */
	public void put(URL url, UrlStatus status) {
    String urlString = transformUrlWithoutAnchor(url);
		this.statusPerUrl.put(urlString, status);
    if (status.getLocation() != null) { this.urlPerLocation.put(status.getLocation(), urlString); }
	}
	
	/**
	 * 
	 */
	private String transformUrlWithoutAnchor(URL url) {
		String urlString = url.toString();
		int anchorPos = urlString.lastIndexOf('#');
		if (anchorPos >= 0) {
			return urlString.substring(0, anchorPos);
		} else {
			return urlString;
		}
	}
	
}
