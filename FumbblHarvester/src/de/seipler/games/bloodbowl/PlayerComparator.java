package de.seipler.games.bloodbowl;

import java.util.Comparator;

/**
 * 
 * @author Georg Seipler
 */
public abstract class PlayerComparator implements Comparator {

  public int compare(Object pO1, Object pO2) {
    Player player1 = (Player) pO1;
    Player player2 = (Player) pO2;
    int result = compareWithoutNames(player1, player2);
    if (result == 0) {
      result = player1.getName().compareTo(player2.getName());
    }
    return result;
  }

  public abstract int compareWithoutNames(Player pPlayer1, Player pPlayer2);
  
  public abstract boolean hasComparedStat(Player pPlayer);
  
}
