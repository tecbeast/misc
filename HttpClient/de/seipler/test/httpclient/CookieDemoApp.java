package de.seipler.test.httpclient;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpState;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;

/**
 *
 * This is a sample application that demonstrates
 * how to use the Jakarta HttpClient API.
 *
 * This application sets an HTTP cookie and
 * updates the cookie's value across multiple
 * HTTP GET requests.
 *
 * @author Sean C. Sullivan
 * @author Oleg Kalnichevski
 *
 */
public class CookieDemoApp {

    /**
     *
     * Usage:
     *          java CookieDemoApp http://mywebserver:80/
     *
     *  @param args command line arguments
     *                 Argument 0 is a URL to a web server
     *
     *
     */
    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.err.println("Usage: java CookieDemoApp <url>");
            System.err.println("<url> The url of a webpage");
            System.exit(1);
        }
        // Get target URL
        String strURL = args[0];
        System.out.println("Target URL: " + strURL);

        // Get initial state object
        HttpState initialState = new HttpState();
        // Initial set of cookies can be retrieved from persistent storage and 
        // re-created, using a persistence mechanism of choice,
        Cookie mycookie = new Cookie(".foobar.com", "mycookie", "stuff", "/", null, false);
        // and then added to your HTTP state instance
        initialState.addCookie(mycookie);
        // RFC 2101 cookie management spec is used per default
        // to parse, validate, format & match cookies
        initialState.setCookiePolicy(CookiePolicy.RFC2109);
        // A different cookie management spec can be selected
        // when desired

//      initialState.setCookiePolicy(CookiePolicy.NETSCAPE_DRAFT);
        // Netscape Cookie Draft spec is provided for completeness
        // You would hardly want to use this spec in real life situations
//      initialState.setCookiePolicy(CookiePolicy.COMPATIBILITY);
        // Compatibility policy is provided in order to mimic cookie
        // management of popular web browsers that is in some areas 
        // not 100% standards compliant

        // Get HTTP client instance
        HttpClient httpclient = new HttpClient();
        httpclient.setConnectionTimeout(30000);
        httpclient.setState(initialState);
        // Get HTTP GET method
        GetMethod httpget = new GetMethod(strURL);
        // Execute HTTP GET
        int result = httpclient.executeMethod(httpget);
        // Display status code
        System.out.println("Response status code: " + result);
        // Get all the cookies
        Cookie[] cookies = httpclient.getState().getCookies();
        // Display the cookies
        System.out.println("Present cookies: ");
        for (int i = 0; i < cookies.length; i++) {
            System.out.println(" - " + cookies[i].toExternalForm());
        }
        // Release current connection to the connection pool once you are done
        httpget.releaseConnection();
    }
}
