package de.seipler.util.swing.layout;

import java.awt.Component;
import java.awt.Container;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Georg Seipler
 */
public class XmlLayout {
  
  private String id;
  private Map componentsById;
  
  protected XmlLayout(Container root, String id) {
    this.id = id;
    this.componentsById = new HashMap();
    add(root, "ROOT");
  }

  public void add(Component component, String id) {
    this.componentsById.put(id, component);
  }

  public Component get(String id) {
    return (Component) this.componentsById.get(id);
  }

  public String getId() {
    return this.id;
  }

}
