package de.seipler.games.rpsm.dice;

/**
 * @author Georg Seipler
 */
public class ShadowrunDice {
  
  private DieArray dice;

  public ShadowrunDice(int number) {
    this.dice = new DieArray(new ExplodingDie(Die.D6), number);
  }
  
  public void roll() {
    dice.roll();
    dice.sortDescending();
  }

  public int successesAgainst(int targetNumber) {
    int successes = 0;
    int roll[] = dice.getRoll();
    for (int i = 0; i < roll.length; i++) {
      if (roll[i] >= targetNumber) {
        successes++;
      }
    }
    return successes;
  }
  
  // @see java.lang.Object#toString()
  public String toString() {
    return dice.toString();
  }

  public static void main(String[] args) {
    int targetNumber = 8;
    ShadowrunDice dice = new ShadowrunDice(6);
    dice.roll();
    int successes = dice.successesAgainst(targetNumber);
    System.out.print(dice);
    System.out.print(" -> ");
    System.out.print(targetNumber);
    System.out.print(" = ");
    System.out.print(successes);
    if (successes == 1) {
      System.out.println(" succcess");
    } else {
      System.out.println(" succcesses");
    }
  }
  
}
