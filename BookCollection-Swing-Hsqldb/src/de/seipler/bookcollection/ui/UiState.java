package de.seipler.bookcollection.ui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import de.seipler.bookcollection.Author;

/**
 * 
 * @author Georg Seipler
 */
public class UiState {

  public static final int STATE_UNDEFINED = 0;
  public static final int STATE_EDIT_BOOK = 1;
  public static final int STATE_EDIT_AUTHOR = 2;
  public static final int STATE_EDIT_SERIES = 3;
  public static final int STATE_EDIT_PUBLISHER = 4;  
  
  private int state;
  private List listeners;  
  private Author author;
  
  public UiState() {
    this.state = STATE_UNDEFINED;
    this.listeners = new ArrayList();
  }
  
  public Author getAuthor() {
    return this.author;
  }

  public int getState() {
    return this.state;
  }

  public void setAuthor(Author author) {
    this.author = author;
  }

  public void changeStateTo(int newState) {
    if (newState != state) {
      fireEvent(new StateChangeEvent(state, newState));
      this.state = newState;
    }
  }
  
  public boolean addListener(StateChangeListener listener) {
    return listeners.add(listener);
  }
  
  public boolean removeListener(StateChangeListener listener) {
    return listeners.remove(listener); 
  }

  private void fireEvent(StateChangeEvent event) {
    Iterator iterator = listeners.iterator();
    while (iterator.hasNext()) {
      ((StateChangeListener) iterator.next()).handleEvent(event);
    }
  }

}
