package de.seipler.games.bloodbowl;

/**
 * 
 * @author Georg Seipler
 */
public class PlayerComparatorGames extends PlayerComparator {

  public int compareWithoutNames(Player pPlayer1, Player pPlayer2) {
    int result;
    if (pPlayer1.getGames() < pPlayer2.getGames()) {
      result = 1;
    } else if (pPlayer1.getGames() > pPlayer2.getGames()) {
      result = -1;
    } else {
      result = 0;
    }
    return result;
  }

  public boolean hasComparedStat(Player pPlayer) {
    return (pPlayer.getGames() > 0);
  }
  
}
