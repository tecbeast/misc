package de.seipler.test.httpclient;

import java.io.IOException;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.regexp.RE;

/**
 *
 */
public class eBayTest {

  public static void main(String[] args) {
    
    RE inputRE = new RE("<[^<]*input[^>]*>");
      
    HttpClient client = new HttpClient();
    client.setConnectionTimeout(5000);
    
    HostConfiguration configuration = client.getHostConfiguration();
    configuration.setProxy(System.getProperty("http.proxyHost"), Integer.parseInt(System.getProperty("http.proxyPort", "80")));

    String url = "http://pages.ebay.de/search/items/search_adv.html";

    HttpMethod method = new GetMethod(url);
    method.setFollowRedirects(true);
    method.setStrictMode(false);

    String responseBody = null;
    try {
      client.executeMethod(method);
      responseBody = method.getResponseBodyAsString();
    } catch (HttpException he) {
      System.err.println("Http error connecting to '" + url + "'");
      System.err.println(he.getMessage());
      System.exit(-4);
    } catch (IOException ioe){
      System.err.println("Unable to connect to '" + url + "'");
      System.exit(-3);
    }

    int pos = 0;
    while (inputRE.match(responseBody, pos)) {
      System.out.println(inputRE.getParen(0));
      pos = inputRE.getParenEnd(0);
    }

    // write out the request headers
    System.out.println("*** Request ***");
    System.out.println("Request Path: " + method.getPath());
    System.out.println("Request Query: " + method.getQueryString());
    Header[] requestHeaders = method.getRequestHeaders();
    for (int i=0; i<requestHeaders.length; i++){
      System.out.print(requestHeaders[i]);
    }

    // write out the response headers
    System.out.println("*** Response ***");
    System.out.println("Status Line: " + method.getStatusLine());
    Header[] responseHeaders = method.getResponseHeaders();
    for (int i=0; i<responseHeaders.length; i++){
      System.out.print(responseHeaders[i]);
    }

    // write out the response body
    System.out.println("*** Response Body ***");
    System.out.println(responseBody);

    // clean up the connection resources
    method.releaseConnection();
    method.recycle();

    System.exit(0);
    
  }
  
}
