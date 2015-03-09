package de.seipler.games.bloodbowl;

/**
 * 
 * @author Georg Seipler
 */
public class PlayerComparatorStarPlayerPoints extends PlayerComparator {

  private PlayerComparator fComparatorGames;
  
  public PlayerComparatorStarPlayerPoints() {
    fComparatorGames = new PlayerComparatorGames();
  }
  
  public int compareWithoutNames(Player pPlayer1, Player pPlayer2) {
    int result;
    if (pPlayer1.getStarPlayerPoints() < pPlayer2.getStarPlayerPoints()) {
      result = 1;
    } else if (pPlayer1.getStarPlayerPoints() > pPlayer2.getStarPlayerPoints()) {
      result = -1;
    } else {
      result = -fComparatorGames.compareWithoutNames(pPlayer1, pPlayer2);
    }
    return result;
  }
  
  public boolean hasComparedStat(Player pPlayer) {
    return (pPlayer.getStarPlayerPoints() > 0);
  }

}
