package de.seipler.games.rpsm.dice;

/**
 * 
 * @author Georg Seipler
 */
public class FengShuiDice {
  
  private Die positiveDie;
  private Die negativeDie;
  
  public FengShuiDice() {
    this.positiveDie = new ExplodingDie(Die.D6);
    this.negativeDie = new ExplodingDie(Die.D6);
  }

  public int getRoll() {
    return (positiveDie.getResult() - negativeDie.getResult());
  }
  
  public void roll() {
    positiveDie.roll();
    negativeDie.roll();
  }
  
  // @see java.lang.Object#toString()
  public String toString() {
    StringBuffer buffer = new StringBuffer();
    buffer.append("[ +");
    buffer.append(positiveDie.getResult());
    buffer.append(", -");
    buffer.append(negativeDie.getResult());
    buffer.append(" ]");
    return buffer.toString();
  }
  
  public static void main(String[] args) {
    int actionValue = 10;
    FengShuiDice dice = new FengShuiDice();
    dice.roll();
    System.out.print(actionValue);
    System.out.print(" + ");
    System.out.print(dice);
    System.out.print(" = ");
    System.out.print(actionValue + dice.getRoll());
  }
  
}
