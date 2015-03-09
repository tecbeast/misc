package de.seipler.games.bloodbowl;

/**
 * 
 * @author Georg Seipler
 */
public class PlayerComparatorCasualties extends PlayerComparator {

  private PlayerComparator fComparatorGames;
  
  public PlayerComparatorCasualties() {
    fComparatorGames = new PlayerComparatorGames();
  }
  
  public int compareWithoutNames(Player pPlayer1, Player pPlayer2) {
    int result;
    if (pPlayer1.getCasualties() < pPlayer2.getCasualties()) {
      result = 1;
    } else if (pPlayer1.getCasualties() > pPlayer2.getCasualties()) {
      result = -1;
    } else {
      result = -fComparatorGames.compareWithoutNames(pPlayer1, pPlayer2);
    }
    return result;
  }

  public boolean hasComparedStat(Player pPlayer) {
    return (pPlayer.getCasualties() > 0);
  }
  
}
