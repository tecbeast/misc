package de.seipler.test.httpclient;

import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.*;

/**
 * <p>
 * A example that demonstrates how HttpClient APIs can be used to perform 
 * form-based logon.
 * </p>
 *
 * @author Oleg Kalnichevski
 *
 */
public class FormLoginDemo
{
    static final String LOGON_SITE = "developer.java.sun.com";
    static final int    LOGON_PORT = 80;

    public FormLoginDemo() {
        super();
    }

    public static void main(String[] args) throws Exception {

        HttpClient client = new HttpClient();
        client.getHostConfiguration().setHost(LOGON_SITE, LOGON_PORT, "http");
        client.getState().setCookiePolicy(CookiePolicy.COMPATIBILITY);
     	// 'developer.java.sun.com' has cookie compliance problems
        // Their session cookie's domain attribute is in violation of the RFC2109
        // We have to resort to using compatibility cookie policy

        GetMethod authget = new GetMethod("/servlet/SessionServlet");

        client.executeMethod(authget);
        System.out.println("Login form get: " + authget.getStatusLine().toString()); 
        // release any connection resources used by the method
        authget.releaseConnection();
        // See if we got any cookies
        // deprecated: Cookie[] initcookies = client.getState().getCookies(LOGON_SITE, LOGON_PORT, "/", false);
        Cookie[] initcookies = client.getState().getCookies();
        System.out.println("Initial set of cookies:");    
        if (initcookies.length == 0) {
            System.out.println("None");    
        } else {
            for (int i = 0; i < initcookies.length; i++) {
                System.out.println("- " + initcookies[i].toString());    
            }
        }
        
        PostMethod authpost = new PostMethod("/servlet/SessionServlet");
        // Prepare login parameters
        NameValuePair action   = new NameValuePair("action", "login");
        NameValuePair url      = new NameValuePair("url", "/index.html");
        NameValuePair userid   = new NameValuePair("UserId", "userid");
        NameValuePair password = new NameValuePair("Password", "password");
        authpost.setRequestBody( 
          new NameValuePair[] {action, url, userid, password});
        
        client.executeMethod(authpost);
        System.out.println("Login form post: " + authpost.getStatusLine().toString()); 
        // release any connection resources used by the method
        authpost.releaseConnection();
        // See if we got any cookies
        // The only way of telling whether logon succeeded is 
        // by finding a session cookie
        // deprecated: Cookie[] logoncookies = client.getState().getCookies(LOGON_SITE, LOGON_PORT, "/", false);
        Cookie[] logoncookies = client.getState().getCookies();
        System.out.println("Logon cookies:");    
        if (logoncookies.length == 0) {
            System.out.println("None");    
        } else {
            for (int i = 0; i < logoncookies.length; i++) {
                System.out.println("- " + logoncookies[i].toString());    
            }
        }
        // Usually a successful form-based login results in a redicrect to 
        // another url
        int statuscode = authpost.getStatusCode();
        if ((statuscode == HttpStatus.SC_MOVED_TEMPORARILY) ||
            (statuscode == HttpStatus.SC_MOVED_PERMANENTLY) ||
            (statuscode == HttpStatus.SC_SEE_OTHER) ||
            (statuscode == HttpStatus.SC_TEMPORARY_REDIRECT)) {
            Header header = authpost.getResponseHeader("location");
            if (header != null) {
                String newuri = header.getValue();
                if ((newuri == null) || (newuri.equals(""))) {
                    newuri = "/";
                }
                System.out.println("Redirect target: " + newuri); 
                GetMethod redirect = new GetMethod(newuri);

                client.executeMethod(redirect);
                System.out.println("Redirect: " + redirect.getStatusLine().toString()); 
                // release any connection resources used by the method
                redirect.releaseConnection();
            } else {
                System.out.println("Invalid redirect");
                System.exit(1);
            }
        }
    }
}
