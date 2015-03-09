package de.seipler.games.rpsm.dice;

/**
 * 
 * @author Georg Seipler
 */
public class ExplodingDie extends Die {

  public ExplodingDie(int type) {
    super(type);
  }

  // @see de.seipler.games.rpsm.die.Die#roll()
  public void roll() {
    int result = 0;
    do {
      super.roll();
      result += getResult();
    } while ((result % getType()) == 0);
    setResult(result);
  }

  // @see java.lang.Object#toString()
  public String toString() {
    return "xD" + getType() + ": " + getResult();
  }

}