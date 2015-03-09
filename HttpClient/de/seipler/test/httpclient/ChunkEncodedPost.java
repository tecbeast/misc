package de.seipler.test.httpclient;

import java.io.File;
import java.io.FileInputStream;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;

/**
 * Example how to use unbuffered chunk-encoded POST request.
 *
 * @author Oleg Kalnichevski
 */
public class ChunkEncodedPost {

  public static void main(String[] args) throws Exception {
    if (args.length != 1)  {
        System.out.println("Usage: ChunkEncodedPost <file>");
        System.out.println("<file> - full path to a file to be posted");
        System.exit(1);
    }
    HttpClient client = new HttpClient();

    PostMethod httppost = new PostMethod("http://localhost:8080/httpclienttest/body");

    httppost.setRequestBody(new FileInputStream(new File(args[0])));
    httppost.setRequestContentLength(PostMethod.CONTENT_LENGTH_CHUNKED);

    client.executeMethod(httppost);

    if (httppost.getStatusCode() == HttpStatus.SC_OK) {
        System.out.println(httppost.getResponseBodyAsString());
    } else {
      System.out.println("Unexpected failure: " + httppost.getStatusLine().toString());
    }
    httppost.releaseConnection();
  }
}
