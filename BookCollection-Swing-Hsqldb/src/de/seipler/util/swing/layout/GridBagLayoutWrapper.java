package de.seipler.util.swing.layout;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.LayoutManager2;
import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

/**
 * 
 * @author Georg Seipler
 */
public class GridBagLayoutWrapper extends LayoutWrapper {
  
  private LayoutManager2 layoutManager;
  private DefaultHandler xmlHandler;
  
  protected GridBagLayoutWrapper() {
    this.layoutManager = new GridBagLayout();
    this.xmlHandler = new GridBagLayoutXmlHandler(this);
  }
  
  /**
   * @see java.awt.LayoutManager2#getLayoutAlignmentX(java.awt.Container)
   */
  public float getLayoutAlignmentX(Container target) {
    return layoutManager.getLayoutAlignmentX(target);
  }

  /**
   * @see java.awt.LayoutManager2#getLayoutAlignmentY(java.awt.Container)
   */
  public float getLayoutAlignmentY(Container target) {
    return layoutManager.getLayoutAlignmentY(target);
  }

  /**
   * @see java.awt.LayoutManager2#invalidateLayout(java.awt.Container)
   */
  public void invalidateLayout(Container target) {
    layoutManager.invalidateLayout(target);
  }

  /**
   * @see java.awt.LayoutManager2#maximumLayoutSize(java.awt.Container)
   */
  public Dimension maximumLayoutSize(Container target) {
    return layoutManager.maximumLayoutSize(target);
  }

  /**
   * @see java.awt.LayoutManager#layoutContainer(java.awt.Container)
   */
  public void layoutContainer(Container parent) {
    layoutManager.layoutContainer(parent);
  }

  /**
   * @see java.awt.LayoutManager#minimumLayoutSize(java.awt.Container)
   */
  public Dimension minimumLayoutSize(Container parent) {
    return layoutManager.minimumLayoutSize(parent);
  }

  /**
   * @see java.awt.LayoutManager#preferredLayoutSize(java.awt.Container)
   */
  public Dimension preferredLayoutSize(Container parent) {
    return layoutManager.preferredLayoutSize(parent);
  }

  /**
   * @see java.awt.LayoutManager#removeLayoutComponent(java.awt.Component)
   */
  public void removeLayoutComponent(Component comp) {
    layoutManager.removeLayoutComponent(comp);
  }

  /**
   * @see de.seipler.test.swing.layout.LayoutWrapper#addConstraint(java.lang.String, java.lang.Object)
   */
  public void addConstraints(String id, Object constraint) {
    Component component = getComponent(id);
    layoutManager.addLayoutComponent(component, constraint);
  }

  /**
   * @see de.seipler.test.swing.layout.LayoutWrapper#readConstraints(java.io.Reader)
   */
  public void readConstraints(InputStream xmlIn) throws ParserConfigurationException, SAXException, IOException {
    SAXParserFactory xmlParserFactory = SAXParserFactory.newInstance();
    xmlParserFactory.setNamespaceAware(false);
    XMLReader xmlReader = null; 
    try {
      xmlReader = xmlParserFactory.newSAXParser().getXMLReader();
      xmlReader.setContentHandler(xmlHandler);
    } catch (Exception e) {
      throw new LayoutWrapperException("Unable to initialize parser.", e);
    }
    InputSource inputSource = new InputSource(xmlIn);
    try {
      xmlReader.parse(inputSource);
    } catch (Exception e) {
      throw new LayoutWrapperException("Parsing error.", e);
    }
    try {
      xmlIn.close();
    } catch (Exception e) {
      throw new LayoutWrapperException("Unable to close stream.", e);
    }
  }

  /**
   * @see de.seipler.util.swing.layout.LayoutWrapper#getXmlHandler()
   */
  protected DefaultHandler getXmlHandler() {
    return this.xmlHandler;
  }

}
