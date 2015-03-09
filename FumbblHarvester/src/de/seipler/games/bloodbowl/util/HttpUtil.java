package de.seipler.games.bloodbowl.util;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;

/**
 * 
 * @author Georg Seipler
 */
public class HttpUtil {
  
  static {
    Logger.getLogger("org.apache.commons.httpclient.HttpMethodBase").setLevel(Level.OFF);
  }

  public static String decodeHtml(String pSource) {
    String result = pSource.replaceAll("&amp;", "&");
    return result;
  }

  public static String fetchPage(String pUrl) throws IOException {
    HttpClient client = new HttpClient();
    client.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
    HttpMethod method = new GetMethod(pUrl);
    method.setFollowRedirects(true);
    client.executeMethod(method);
    String responseBody = method.getResponseBodyAsString();
    method.releaseConnection();
    return responseBody;
  }
  
}
