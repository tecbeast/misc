package de.seipler.bookcollection.ui;

/**
 * 
 * @author Georg Seipler
 */
public class StateChangeEvent {
  
  private int oldState;
  private int newState;
  
  public StateChangeEvent(int oldState, int newState) {
    this.oldState = oldState;
    this.newState = newState;
  }

  public int getNewState() {
    return this.newState;
  }

  public int getOldState() {
    return this.oldState;
  }

}
