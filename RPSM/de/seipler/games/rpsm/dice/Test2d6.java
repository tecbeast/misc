package de.seipler.games.rpsm.dice;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Test2d6 {
  
  private class Roll {
    
    private int[] fRoll;
    private boolean fHighest;
    
    public Roll(int[] roll, boolean highest) {
      fRoll = roll;
      fHighest = highest;
    }
    
    public int[] getRoll() {
      return fRoll;
    }
    
    public boolean isHighest() {
      return fHighest;
    }
    
    public int getResult() {
      int[] sortedRoll = Arrays.copyOf(fRoll, fRoll.length);
      Arrays.sort(sortedRoll);
      if (fHighest) {
        return sortedRoll[sortedRoll.length - 1] + sortedRoll[sortedRoll.length - 2];
      } else {
        return sortedRoll[0] + sortedRoll[1];
      }
    }
    
    public String toString() {
      StringBuilder result = new StringBuilder();
      result.append("[");
      for (int i = 0; i < fRoll.length; i++) {
        if (i > 0) {
          result.append(",");
        }
        result.append(fRoll[i]);
      }
      result.append("] = ").append(getResult());
      return result.toString();
    }
    
  }
  
  public Roll[] createRolls(int nrOfDice, boolean highest) {
    int nrOfRolls = (int) Math.round(Math.pow(6, nrOfDice));
    Roll[] result = new Roll[nrOfRolls];
    int[] lastRoll = new int[nrOfDice];
    for (int i = 0; i < nrOfRolls; i++) {
      int[] newRoll = Arrays.copyOf(lastRoll, nrOfDice);
      for (int j = newRoll.length - 1; j >= 0; j--) {
        if (newRoll[j] == 0) {
          newRoll[j] = 1;
        } else if (newRoll[j] == 6) {
          newRoll[j] = 1;
        } else {
          newRoll[j] = newRoll[j] + 1;
          break;
        }
      }
      lastRoll = newRoll;
      result[i] = new Roll(newRoll, highest);
    }
    return result;
  }
  
  public Map<Integer, Integer> countResults(Roll[] rolls) {
    Map<Integer, Integer> result = new HashMap<Integer, Integer>();
    for (int i = 0; i < rolls.length; i++) {
      int key = rolls[i].getResult();
      int value = 0;
      if (result.containsKey(key)) {
        value = result.get(key);
      }
      result.put(key, value + 1);
    }
    return result;
  }
  
  public void printAverage(Map<Integer, Integer> results, int nrOfRolls) {
    double average = 0;
    Integer[] keys = results.keySet().toArray(new Integer[results.size()]);
    for (int i = 0; i < keys.length; i++) {
      int count = results.get(keys[i]);
      average += (double) keys[i] * count / nrOfRolls;
    }
    StringBuilder line = new StringBuilder();
    line.append("Average ");
    line.append((double) Math.round((double) 100 * average) / 100);
    System.out.println(line);
  }
  
  public void printResults(Map<Integer, Integer> results, int nrOfRolls) {
    Integer[] keys = results.keySet().toArray(new Integer[results.size()]);
    Arrays.sort(keys);
    for (int i = 0; i < keys.length; i++) {
      int count = results.get(keys[i]);
      StringBuilder line = new StringBuilder();
      if (keys[i] < 10) {
        line.append(" ");
      }
      line.append(keys[i]);
      line.append(" -> ");
      line.append(count).append("/").append(nrOfRolls);
      line.append(" = ");
      line.append(formatAsPercentage(count, nrOfRolls)).append("%");
      System.out.println(line);
    }
  }
  
  private double formatAsPercentage(int count, int total) {
    return (double) Math.round((double) 10000 * count / total) / 100;
  }
    
  public static void main(String[] args) {

    Test2d6 test = new Test2d6();

    Roll[] rolls = null;
    Map<Integer, Integer> results = null;
    
    System.out.println("Standard Roll (2d6)");
    System.out.println("------------------------------");
    rolls = test.createRolls(2, false);
    results = test.countResults(rolls);
    test.printResults(results, rolls.length);
    test.printAverage(results, rolls.length);

    System.out.println();
    System.out.println("1 Advantage (3d6 highest 2)");
    System.out.println("------------------------------");
    rolls = test.createRolls(3, true);
    results = test.countResults(rolls);
    test.printResults(results, rolls.length);
    test.printAverage(results, rolls.length);

    System.out.println();
    System.out.println("2 Advantages (4d6 highest 2)");
    System.out.println("------------------------------");
    rolls = test.createRolls(4, true);
    results = test.countResults(rolls);
    test.printResults(results, rolls.length);
    test.printAverage(results, rolls.length);

    System.out.println();
    System.out.println("3 Advantages (5d6 highest 2)");
    System.out.println("------------------------------");
    rolls = test.createRolls(5, true);
    results = test.countResults(rolls);
    test.printResults(results, rolls.length);
    test.printAverage(results, rolls.length);

    System.out.println();
    System.out.println("1 Disadvantage (3d6 lowest 2)");
    System.out.println("------------------------------");
    rolls = test.createRolls(3, false);
    results = test.countResults(rolls);
    test.printResults(results, rolls.length);
    test.printAverage(results, rolls.length);

    System.out.println();
    System.out.println("2 Disadvantages (4d6 lowest 2)");
    System.out.println("------------------------------");
    rolls = test.createRolls(4, false);
    results = test.countResults(rolls);
    test.printResults(results, rolls.length);
    test.printAverage(results, rolls.length);

    System.out.println();
    System.out.println("3 Disadvantages (5d6 lowest 2)");
    System.out.println("------------------------------");
    rolls = test.createRolls(5, false);
    results = test.countResults(rolls);
    test.printResults(results, rolls.length);
    test.printAverage(results, rolls.length);

  }

}
