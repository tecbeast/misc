package de.seipler.test.httpclient;

import org.apache.commons.httpclient.HttpConnection;
import org.apache.commons.httpclient.HttpState;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.URI;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.protocol.Protocol;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.ConnectMethod;

/**
 * This example demonstrates how to establishing connection to an HTTP server
 * when higher level of control is desired
 *
 * @author Armando Anton
 * @author Oleg Kalnichevski
 */

public class CustomHttpConnection
{
    public static void main(String[] args) throws Exception
    {
        if (args.length != 1)
        {
            System.err.println("Usage: java CustomHttpConnection <url>");
            System.err.println("<url> The url of a webpage");
            System.exit(1);
        }

        URI uri = new URI(args[0].toCharArray()); // i like this constructor :)

        String schema = uri.getScheme();
        if ((schema == null) || (schema.equals("")))
        {
            schema = "http";
        }
        Protocol protocol = Protocol.getProtocol(schema);

        HttpState state = new HttpState();

        HttpMethod method = new GetMethod(uri.toString());
        String host = uri.getHost();
        int port = uri.getPort();

        HttpConnection connection = new HttpConnection(host, port, protocol);

        connection.setProxyHost(System.getProperty("http.proxyHost"));
        connection.setProxyPort( Integer.parseInt(System.getProperty("http.proxyPort","80")));

        if (System.getProperty("http.proxyUserName") != null)
        {
            state.setProxyCredentials(null, null,
              new UsernamePasswordCredentials(
                System.getProperty("http.proxyUserName"),
                System.getProperty("http.proxyPassword")));
        }

        if (connection.isProxied() && connection.isSecure()) {
            method = new ConnectMethod(method);
        }
        method.execute(state, connection);

        if (method.getStatusCode() == HttpStatus.SC_OK) {
            System.out.println(method.getResponseBodyAsString());
        } else {
            System.out.println("Unexpected failure: " + method.getStatusLine().toString());
        }
        method.releaseConnection();
    }
}
