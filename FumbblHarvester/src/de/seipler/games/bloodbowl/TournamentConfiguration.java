package de.seipler.games.bloodbowl;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author Georg Seipler
 */
public class TournamentConfiguration {
  
  private int fPointsWin;
  private int fPointsDraw;
  private int fPointsLoss;
  private int fPointsTdFor;
  private int fPointsTdAgainst;
  
  private Map fExtraPointsPerTeam;
  
  public TournamentConfiguration() {
    fExtraPointsPerTeam = new HashMap();
  }

  public int getPointsDraw() {
    return fPointsDraw;
  }

  public void setPointsDraw(int pPointsDraw) {
    fPointsDraw = pPointsDraw;
  }

  public int getPointsLoss() {
    return fPointsLoss;
  }

  public void setPointsLoss(int pPointsLoss) {
    fPointsLoss = pPointsLoss;
  }

  public int getPointsTdAgainst() {
    return fPointsTdAgainst;
  }

  public void setPointsTdAgainst(int pPointsTdAgainst) {
    fPointsTdAgainst = pPointsTdAgainst;
  }

  public int getPointsTdFor() {
    return fPointsTdFor;
  }

  public void setPointsTdFor(int pPointsTdFor) {
    fPointsTdFor = pPointsTdFor;
  }

  public int getPointsWin() {
    return fPointsWin;
  }

  public void setPointsWin(int pPointsWin) {
    fPointsWin = pPointsWin;
  }
  
  public void addExtraPointsForTeam(Team pTeam, int pExtraPoints) {
    fExtraPointsPerTeam.put(pTeam, new Integer(pExtraPoints));
  }
  
  public int getExtraPointsForTeam(Team pTeam) {
    int result = 0;
    Integer extraPoints = (Integer) fExtraPointsPerTeam.get(pTeam);
    if (extraPoints != null) {
      result = extraPoints.intValue();
    }
    return result;
  }

  public void evaluateMatch(RankedTeam pTeam1, RankedTeam pTeam2, int pScore1, int pScore2) {

    if ((pTeam1 != null) && (pTeam2 != null)) {
    
      pTeam1.setTouchdownsFor(pTeam1.getTouchdownsFor() + pScore1);
      pTeam1.setTouchdownsAgainst(pTeam1.getTouchdownsAgainst() + pScore2);
      pTeam2.setTouchdownsFor(pTeam2.getTouchdownsFor() + pScore2);
      pTeam2.setTouchdownsAgainst(pTeam2.getTouchdownsAgainst() + pScore1);
      
      if (pScore1 > pScore2) {
        pTeam1.setGamesWon(pTeam1.getGamesWon() + 1);
        pTeam1.setPoints(pTeam1.getPoints() + getPointsWin() + (pScore1 * getPointsTdFor()) + (pScore2 * getPointsTdAgainst()));
        pTeam2.setGamesLost(pTeam2.getGamesLost() + 1);
        pTeam2.setPoints(pTeam2.getPoints() + getPointsLoss() + (pScore2 * getPointsTdFor()) + (pScore1 * getPointsTdAgainst()));
        
      } else if (pScore2 > pScore1) {
        pTeam2.setGamesWon(pTeam2.getGamesWon() + 1);
        pTeam2.setPoints(pTeam2.getPoints() + getPointsWin() + (pScore2 * getPointsTdFor()) - (pScore1 * getPointsTdAgainst()));
        pTeam1.setGamesLost(pTeam1.getGamesLost() + 1);
        pTeam1.setPoints(pTeam1.getPoints() + getPointsLoss() + (pScore1 * getPointsTdFor()) + (pScore2 * getPointsTdAgainst()));
        
      } else {
        pTeam1.setGamesTied(pTeam1.getGamesTied() + 1);
        pTeam1.setPoints(pTeam1.getPoints() + getPointsDraw() + (pScore1 * getPointsTdFor()) + (pScore2 * getPointsTdAgainst()));
        pTeam2.setGamesTied(pTeam2.getGamesTied() + 1);
        pTeam2.setPoints(pTeam2.getPoints() + getPointsDraw() + (pScore2 * getPointsTdFor()) + (pScore1 * getPointsTdAgainst()));
      }
      
    }
    
  }
  
}
