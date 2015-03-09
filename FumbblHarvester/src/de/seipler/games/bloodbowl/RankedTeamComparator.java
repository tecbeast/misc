package de.seipler.games.bloodbowl;

import java.util.Comparator;

/**
 * 
 * @author Georg Seipler
 */
public abstract class RankedTeamComparator implements Comparator {
  
  public int compare(Object pO1, Object pO2) {
    RankedTeam team1 = (RankedTeam) pO1;
    RankedTeam team2 = (RankedTeam) pO2;
    int result = compareWithoutNames(team1, team2);
    if (result == 0) {
      result = team1.getTeam().getName().compareTo(team2.getTeam().getName());
    }
    return result;
  }

  public abstract int compareWithoutNames(RankedTeam pTeam1, RankedTeam pTeam2);
  
}
