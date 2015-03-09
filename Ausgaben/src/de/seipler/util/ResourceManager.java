package de.seipler.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Properties;

/**
 * This class is responsible for loading resources (files) for us. It avoids the
 * complexity of having to figure out exactly where a file lives. Resources can
 * be retrievd as URLs, Files, InputStreams, and OutputStreams.
 * <p>
 * In the context of a web-application, resources will get loaded relative to
 * the directory in which the servlet context is deployed. For example, if the
 * web app is deployed in /usr/local/tomcat/webapps/foobar, then
 * getResource("/WEB-INF/conf/foo.conf") will return a handle to
 * /usr/local/tomcat/webapps/foobar/WEB-INF/conf/foo.conf.
 * <p>
 * In the context of a non-web-application, resources will be loaded relative to
 * the classpath.
 * <p>
 * In the context of EJB, resource loading will be delegated to the EJB
 * container (or at least that's what I'm thinking..)
 * <p>
 * During application (or servlet engine) startup, the method
 * setServletContext() must be called to bootstrap the ResourceLoader with the
 * ServletContext.
 */
public class ResourceManager {

    public ResourceManager() {
        super();
    }

    /**
     * Get the specified resource as a URL
     */
    public URL getResourceAsUrl(String name) throws ResourceManagerException {
        URL url = getClass().getResource(name);
        if (url == null) {
            throw new ResourceManagerException("No such file or directory: " + name);
        } else {
            return url;
        }
    }

    /**
     * Get the specified resource as a File
     */
    public File getResourceAsFile(String name) throws ResourceManagerException {
        return getResourceAsFile(name, false);
    }

    public boolean exists(String name) {
        try {
            File f = getResourceAsFile(name, false);
            return (f.exists());
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Get the specified resource as a File and possibly ignore a file not found
     * error (such as in the case when you're getting a log file which may not yet exist)
     */
    public File getResourceAsFile(String name, boolean ignoreNotFoundError) throws ResourceManagerException {
        ResourceManagerException throwMe = null;
        try {
            URL url = getResourceAsUrl(name);
            if (url != null) {
                File f = new File(url.getFile());
                if (f.exists() || ignoreNotFoundError) {
                    return f;
                } else {
                    throwMe = new ResourceManagerException("No such file or directory: " + name);
                }
            } else {
                throwMe = new ResourceManagerException("No such file or directory: " + name);
            }
        } catch (ResourceManagerException ex) {
            // try another way
            File path = new File(name);
            File f = getResourceAsFile(path.getParent());
            File target = new File(f, path.getName());
            if (target.exists() || ignoreNotFoundError) {
                return target;
            } else {
                throwMe = new ResourceManagerException("No such file or directory: " + name);
            }
        }
        if (throwMe != null) {
            throw throwMe;
        }
        return null; // never reached
    }

    /**
     * Get the specified resource as an InputStream.
     * Caller is responsible for closing the stream.
     */
    public InputStream getResourceAsInputStream(String name) throws ResourceManagerException {
        InputStream is = getClass().getResourceAsStream(name);
        if (is == null) {
            throw new ResourceManagerException("No such file or directory: " + name);
        } else {
            return is;
        }
    }

    public OutputStream getResourceAsOutputStream(String name) throws ResourceManagerException {
        return getResourceAsOutputStream(name, true);
    }

    /**
     * Get the specified resource as an OutputStream.
     * Caller is responsible for closing the stream.
     */
    public OutputStream getResourceAsOutputStream(String name, boolean create) throws ResourceManagerException {
        try {
            File f = getResourceAsFile(name, create);
            if (f != null) {
                return new FileOutputStream(f);
            } else {
                throw new ResourceManagerException("No such file or directory: " + name);
            }
        } catch (IOException e) {
            throw new ResourceManagerException("Could not load " + name + " for writing", e);
        }
    }

    /**
     * Get the specified resource as a Java Properties object.
     */
    public Properties getResourceAsProperties(String name) throws ResourceManagerException {
        Properties p = new Properties();
        InputStream is = null;
        boolean success = false;
        try {
            is = getResourceAsInputStream(name);
            p.load(is);
            success = true;
            return p;
        } catch (IOException e) {
            throw new ResourceManagerException("Could not load " + name + " as properties", e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    if (!success) {
                        // throw this exception up only if we don't have the properties
                        // file loaded. otherwise, let's just be silent about it.
                        throw new ResourceManagerException("While closing stream", e);
                    }
                }
            }
        }
    }

}
