package de.seipler.games.bloodbowl;

/**
 * 
 * @author Georg Seipler
 */
public class PlayerComparatorInterceptions extends PlayerComparator {

  private PlayerComparator fComparatorGames;
  
  public PlayerComparatorInterceptions() {
    fComparatorGames = new PlayerComparatorGames();
  }
  
  public int compareWithoutNames(Player pPlayer1, Player pPlayer2) {
    int result;
    if (pPlayer1.getInterceptions() < pPlayer2.getInterceptions()) {
      result = 1;
    } else if (pPlayer1.getInterceptions() > pPlayer2.getInterceptions()) {
      result = -1;
    } else {
      result = -fComparatorGames.compareWithoutNames(pPlayer1, pPlayer2);
    }
    return result;
  }
  
  public boolean hasComparedStat(Player pPlayer) {
    return (pPlayer.getInterceptions() > 0);
  }

}
