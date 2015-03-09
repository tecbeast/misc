package de.seipler.games.bloodbowl;

/**
 * 
 * @author Georg Seipler
 */
public class RankedTeam {
  
  private Team fTeam;
  private int fTouchdownsFor;
  private int fTouchdownsAgainst;
  private int fGamesWon;
  private int fGamesTied;
  private int fGamesLost;
  private int fRank;
  private int fPoints;

  public RankedTeam(Team pTeam) {
    fTeam = pTeam;
    fTouchdownsFor = 0;
    fTouchdownsAgainst = 0;
    fGamesWon = 0;
    fGamesTied = 0;
    fGamesLost = 0;
    fPoints = 0;
  }
  
  public int getGamesLost() {
    return fGamesLost;
  }

  public void setGamesLost(int pGamesLost) {
    fGamesLost = pGamesLost;
  }

  public int getGamesTied() {
    return fGamesTied;
  }

  public int getGamesWon() {
    return fGamesWon;
  }

  public Team getTeam() {
    return fTeam;
  }

  public int getTouchdownsAgainst() {
    return fTouchdownsAgainst;
  }
  
  public void setTouchdownsAgainst(int pTouchdownsAgainst) {
    fTouchdownsAgainst = pTouchdownsAgainst;
  }

  public int getTouchdownsFor() {
    return fTouchdownsFor;
  }
  
  public void setTouchdownsFor(int pTouchdownsFor) {
    fTouchdownsFor = pTouchdownsFor;
  }
  
  public int getPoints() {
    return fPoints;
  }

  public int getRank() {
    return fRank;
  }
  
  public void setRank(int pRank) {
    fRank = pRank;
  }
  
  public int getGamesTotal() {
    return (getGamesWon() + getGamesTied() + getGamesLost());
  }
  
  public void setGamesTied(int pGamesTied) {
    fGamesTied = pGamesTied;
  }
  
  public void setGamesWon(int pGamesWon) {
    fGamesWon = pGamesWon;
  }
  
  public void setPoints(int pPoints) {
    fPoints = pPoints;
  }
  
  public String toString() {
    String newLine = System.getProperty("line.separator", "\n"); 
    StringBuffer buffer = new StringBuffer();
    buffer.append("[RankedTeam]");
    buffer.append(newLine);
    buffer.append("Rank: ");
    buffer.append(getRank());
    buffer.append(newLine);
    buffer.append("Name: ");
    buffer.append(getTeam().getName());
    buffer.append(newLine);
    buffer.append("Games: ");
    buffer.append(getGamesWon());
    buffer.append(" / ");
    buffer.append(getGamesTied());
    buffer.append(" / ");
    buffer.append(getGamesLost());
    buffer.append(newLine);
    buffer.append("Touchdowns: ");
    buffer.append(getTouchdownsFor());
    buffer.append(" : ");
    buffer.append(getTouchdownsAgainst());
    buffer.append(newLine);
    return buffer.toString();
  }
  
}
