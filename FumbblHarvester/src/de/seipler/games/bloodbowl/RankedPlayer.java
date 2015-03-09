package de.seipler.games.bloodbowl;

/**
 * 
 * @author Georg Seipler
 */
public class RankedPlayer {
  
  private int fRank;
  private Player fPlayer;
  
  public RankedPlayer(Player pPlayer) {
    fPlayer = pPlayer;
  }
  
  public void setRank(int pRank) {
    fRank = pRank;
  }
  
  public int getRank() {
    return fRank;
  }
  
  public Player getPlayer() {
    return fPlayer;
  }
  
}
