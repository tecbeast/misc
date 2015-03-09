package de.seipler.util.swing.layout;

import java.awt.Component;
import java.awt.LayoutManager2;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * 
 * @author Georg Seipler
 */
public abstract class LayoutWrapper implements LayoutManager2 {

  public static final int GRID_BAG_LAYOUT = 1;

  private Map componentById;
  
  public LayoutWrapper() {
    this.componentById = new HashMap();
  }
  
  public abstract void addConstraints(String id, Object constraint);

  /**
   * @see java.awt.LayoutManager2#addLayoutComponent(java.awt.Component, java.lang.Object)
   */
  public void addLayoutComponent(Component component, Object constraints) {
    addLayoutComponent((String) constraints, component);
  }

  /**
   * @see java.awt.LayoutManager#addLayoutComponent(java.lang.String, java.awt.Component)
   */
  public void addLayoutComponent(String id, Component component) {
    componentById.put(id, component);
  }
  
  protected Component getComponent(String id) {
    return (Component) componentById.get(id);
  }
  
  protected abstract DefaultHandler getXmlHandler();

  public abstract void readConstraints(InputStream xmlIn) throws ParserConfigurationException, SAXException, IOException;
  
  public void readConstraints(File file) throws ParserConfigurationException, SAXException, IOException, FileNotFoundException {
    BufferedInputStream in = null;
    try {
      in = new BufferedInputStream(new FileInputStream(file));
    } catch (Exception e) {
      throw new LayoutWrapperException("Unable to open file.", e);
    }
    readConstraints(in);
  }
  
}
