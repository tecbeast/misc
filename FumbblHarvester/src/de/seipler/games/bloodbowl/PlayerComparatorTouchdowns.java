package de.seipler.games.bloodbowl;

/**
 * 
 * @author Georg Seipler
 */
public class PlayerComparatorTouchdowns extends PlayerComparator {

  private PlayerComparator fComparatorGames;
  
  public PlayerComparatorTouchdowns() {
    fComparatorGames = new PlayerComparatorGames();
  }
  
  public int compareWithoutNames(Player pPlayer1, Player pPlayer2) {
    int result;
    if (pPlayer1.getTouchdowns() < pPlayer2.getTouchdowns()) {
      result = 1;
    } else if (pPlayer1.getTouchdowns() > pPlayer2.getTouchdowns()) {
      result = -1;
    } else {
      result = -fComparatorGames.compareWithoutNames(pPlayer1, pPlayer2);
    }
    return result;
  }
  
  public boolean hasComparedStat(Player pPlayer) {
    return (pPlayer.getTouchdowns() > 0);
  }

}
