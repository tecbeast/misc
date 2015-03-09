package de.seipler.games.bloodbowl;

/**
 * 
 * @author Georg Seipler
 */
public class TournamentMatch {
  
  public static final int STATUS_UNPLAYED = 0;
  public static final int STATUS_PLAYED = 1;
  public static final int STATUS_SET = 2;
  
  private Team fTeam1;
  private Team fTeam2;
  private int fScore1;
  private int fScore2;
  private int fStatus;
  private int fRound;
  private String fUrl;
  
  public TournamentMatch(int pRound, Team pTeam1, Team pTeam2) {
    fRound = pRound;
    fTeam1 = pTeam1;
    fTeam2 = pTeam2;
    setStatus(STATUS_UNPLAYED);
  }

  public void setResult(int pScore1, int pScore2) {
    fScore1 = pScore1;
    fScore2 = pScore2;
  }
  
  public Team getTeam1() {
    return fTeam1;
  }
  
  public Team getTeam2() {
    return fTeam2;
  }
  
  public int getScore1() {
    return fScore1;
  }
  
  public int getScore2() {
    return fScore2;
  }
  
  public void setStatus(int pStatus) {
    fStatus = pStatus;
  }
  
  public int getStatus() {
    return fStatus;
  }
  
  public int getRound() {
    return fRound;
  }
  
  public void setUrl(String pUrl) {
    fUrl = pUrl;
  }
  
  public String getUrl() {
    return fUrl;
  }
  
}
