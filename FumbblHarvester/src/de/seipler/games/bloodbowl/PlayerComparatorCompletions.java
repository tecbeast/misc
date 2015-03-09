package de.seipler.games.bloodbowl;

/**
 * 
 * @author Georg Seipler
 */
public class PlayerComparatorCompletions extends PlayerComparator {

  private PlayerComparator fComparatorGames;
  
  public PlayerComparatorCompletions() {
    fComparatorGames = new PlayerComparatorGames();
  }
  
  public int compareWithoutNames(Player pPlayer1, Player pPlayer2) {
    int result;
    if (pPlayer1.getCompletions() < pPlayer2.getCompletions()) {
      result = 1;
    } else if (pPlayer1.getCompletions() > pPlayer2.getCompletions()) {
      result = -1;
    } else {
      result = -fComparatorGames.compareWithoutNames(pPlayer1, pPlayer2);
    }
    return result;
  }

  public boolean hasComparedStat(Player pPlayer) {
    return (pPlayer.getCompletions() > 0);
  }
  
}
