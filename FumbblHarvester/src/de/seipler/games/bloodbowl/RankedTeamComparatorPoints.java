package de.seipler.games.bloodbowl;

/**
 * 
 * @author Georg Seipler
 */
public class RankedTeamComparatorPoints extends RankedTeamComparator {
  
  private RankedTeamComparator fComparatorTdDiff;
  
  public RankedTeamComparatorPoints() {
    fComparatorTdDiff = new RankedTeamComparatorTdDiff();
  }
  
  public int compareWithoutNames(RankedTeam pTeam1, RankedTeam pTeam2) {
    int result;
    if (pTeam1.getPoints() < pTeam2.getPoints()) {
      result = 1;
    } else if (pTeam1.getPoints() > pTeam2.getPoints()) {
      result = -1;
    } else {
      result = fComparatorTdDiff.compareWithoutNames(pTeam1, pTeam2);
    }
    return result;
  }

}
