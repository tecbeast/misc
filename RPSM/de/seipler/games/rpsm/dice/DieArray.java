package de.seipler.games.rpsm.dice;

import java.util.Arrays;

/**
 * 
 * @author Georg Seipler
 */
public class DieArray {
  
  private Die[] dice;
  
  public DieArray(Die type, int size) {
    if (size < 1) {
      throw new IllegalArgumentException("Parameter size must be >= 1.");
    }
    dice = new Die[size];
    try {
      for (int i = 0; i < dice.length; i++) {
        dice[i] = (Die) type.clone();
      }
    } catch (CloneNotSupportedException impossible) {
      // Die objects are Cloneable. So this won't happen.
    }
  }
  
  public Die get(int index) {
    return dice[index];
  }
  
  public int[] getRoll() {
    int[] roll = new int[dice.length];
    for (int i = 0; i < dice.length; i++) {
      roll[i] = dice[i].getResult();
    }
    return roll;
  }

  public int getTotal() {
    int total = 0;
    for (int i = 0; i < dice.length; i++) {
      total += dice[i].getResult();
    }
    return total;
  }
  
  public void roll() {
    for (int i = 0; i < dice.length; i++) {
      dice[i].roll();
    }
  }
  
  public int size() {
    return dice.length;
  }
  
  public void sortAscending() {
    Arrays.sort(dice);
  }
  
  public void sortDescending() {
    sortAscending();
    // reverse order
    for (int i = 0; i < dice.length / 2; i++) {
      int j = dice.length - i - 1;
      Die swap = dice[i];
      dice[i] = dice[j];
      dice[j] = swap;
    }
  }

  // @see java.lang.Object#toString()
  public String toString() {
    StringBuffer buffer = new StringBuffer();
    buffer.append("[ ");
    for (int i = 0; i < dice.length; i++) {
      if (i > 0) {
        buffer.append(", ");
      }
      buffer.append(dice[i].getResult());
    }
    buffer.append(" ]");
    return buffer.toString();
  }
  

}
