package de.seipler.test.httpclient;

import java.io.IOException;

import org.apache.commons.httpclient.Credentials;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.methods.GetMethod;

/**
 *
 * This is a simple text mode application that demonstrates
 * how to use the Jakarta HttpClient API.
 *
 * @author <a href="mailto:jsdever@apache.org">Jeff Dever</a>
 * @author Ortwin Gl�ck
 */
public class TrivialApp
{

    private static final void printUsage()
    {
        System.out.println();
        System.out.println("Usage: java -classpath <classpath> [-Dorg.apache.commons.logging.simplelog.defaultlog=<loglevel>] TrivialApp <url> [<username> <password>]");
        System.out.println("<classpath> - must contain the commons-httpclient.jar and commons-logging.jar");
        System.out.println("<loglevel> - one of error, warn, info, debug, trace");
        System.out.println("<url> - some valid URL");
        System.out.println("<username> - username for protected page");
        System.out.println("<password> - password for protected page");
        System.out.println();
    }

    public static void main(String[] args)
    {
        if ((args.length != 1) && (args.length != 3)) {
            printUsage();
            System.exit(-1);
        }

        Credentials creds = null;
        if (args.length >= 3) {
            creds = new UsernamePasswordCredentials(args[1], args[2]);
        }

        //create a singular HttpClient object
        HttpClient client = new HttpClient();

        //establish a connection within 5 seconds
        client.setConnectionTimeout(5000);

        //set the default credentials
        if (creds != null) {
            client.getState().setCredentials(null, null, creds);
        }

        String url = args[0];
        HttpMethod method = null;

        //create a method object
            method = new GetMethod(url);
            method.setFollowRedirects(true);
            method.setStrictMode(false);
        //} catch (MalformedURLException murle) {
        //    System.out.println("<url> argument '" + url
        //            + "' is not a valid URL");
        //    System.exit(-2);
        //}

        //execute the method
        String responseBody = null;
        try{
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


        //write out the request headers
        System.out.println("*** Request ***");
        System.out.println("Request Path: " + method.getPath());
        System.out.println("Request Query: " + method.getQueryString());
        Header[] requestHeaders = method.getRequestHeaders();
        for (int i=0; i<requestHeaders.length; i++){
            System.out.print(requestHeaders[i]);
        }

        //write out the response headers
        System.out.println("*** Response ***");
        System.out.println("Status Line: " + method.getStatusLine());
        Header[] responseHeaders = method.getResponseHeaders();
        for (int i=0; i<responseHeaders.length; i++){
            System.out.print(responseHeaders[i]);
        }

        //write out the response body
        System.out.println("*** Response Body ***");
        System.out.println(responseBody);

        //clean up the connection resources
        method.releaseConnection();
        method.recycle();

        System.exit(0);
    }
}
