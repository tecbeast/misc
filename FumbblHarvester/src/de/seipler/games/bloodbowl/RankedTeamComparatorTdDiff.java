package de.seipler.games.bloodbowl;

/**
 * 
 * @author Georg Seipler
 */
public class RankedTeamComparatorTdDiff extends RankedTeamComparator {
  
  private RankedTeamComparator fComparatorTdFor;
  
  public RankedTeamComparatorTdDiff() {
    fComparatorTdFor = new RankedTeamComparatorTdFor();
  }
  
  public int compareWithoutNames(RankedTeam pTeam1, RankedTeam pTeam2) {
    int result;
    int tdDiff1 = pTeam1.getTouchdownsFor() - pTeam1.getTouchdownsAgainst();
    int tdDiff2 = pTeam2.getTouchdownsFor() - pTeam2.getTouchdownsAgainst();
    if (tdDiff1 < tdDiff2) {
      result = 1;
    } else if (tdDiff1 > tdDiff2) {
      result = -1;
    } else {
      result = fComparatorTdFor.compareWithoutNames(pTeam1, pTeam2);
    }
    return result;
  }

}
