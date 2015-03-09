package de.seipler.util.collection;

import java.util.EventListener;

/**
 * 
 * @author Georg Seipler
 */
public interface ContentChangeListener extends EventListener {
  
  public void handleContentChange(ContentChangeEvent event);

}
