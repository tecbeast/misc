package de.seipler.games.bloodbowl;

/**
 * 
 * @author Georg Seipler
 */
public class RankedTeamComparatorTdFor extends RankedTeamComparator {
  
  public int compareWithoutNames(RankedTeam pTeam1, RankedTeam pTeam2) {
    int result;
    if (pTeam1.getTouchdownsFor() < pTeam2.getTouchdownsFor()) {
      result = 1;
    } else if (pTeam1.getTouchdownsFor() > pTeam2.getTouchdownsFor()) {
      result = -1;
    } else {
      result = 0;
    }
    return result;
  }

}
