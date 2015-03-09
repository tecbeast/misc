package de.seipler.games.rpsm.dice;

import java.util.Random;

/**
 * 
 * @author Georg Seipler
 */
public class Die implements Cloneable, Comparable {
  
  public static final int D3 = 3;  
  public static final int D4 = 4;
  public static final int D6 = 6;
  public static final int D8 = 8;
  public static final int D10 = 10;
  public static final int D12 = 12;
  public static final int D20 = 20;
  public static final int D30 = 30;

  protected static Random random = new Random();
  
  private int type;
  protected int result;
  
  public Die(int type) {
    setType(type);
    roll();
  }
  
  // @see java.lang.Object#clone()
  public Object clone() throws CloneNotSupportedException {
    return super.clone();
  }
  
  // @see java.lang.Comparable#compareTo(java.lang.Object)
  public int compareTo(Object obj) {
    return compareTo((Die) obj);
  }
  
  public int compareTo(Die anotherDie) {
    int result = getType() - anotherDie.getType();
    if (result == 0) {
      result = getResult() - anotherDie.getResult();
    }
    return result;
  }
  
  // @see java.lang.Object#equals(java.lang.Object)
  public boolean equals(Object obj) {
    boolean result = false;
    if (obj == this) {
      result = true;
    } else if (obj instanceof Die) {
      result = (compareTo(obj) == 0);
    }
    return result;
  }
  
  public int getResult() {
    return this.result;
  }
  
  public int getType() {
    return this.type;
  }
  
  public void roll() {
    setResult(random.nextInt(type) + 1);
  }
  
  // @see java.lang.Object#toString()
  public String toString() {
    return "D" + getType() + ": " + getResult();
  }
  
  protected void setResult(int result) {
    this.result = result;
  }
  
  protected void setType(int type) {
    this.type = type;
  }
  
}
