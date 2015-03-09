package de.seipler.games.bloodbowl;

import java.util.HashSet;
import java.util.Set;

/**
 * 
 * @author Georg Seipler
 */
public class GroupConfiguration {
  
  private Set fIgnoredTeams;
  
  public GroupConfiguration() {
    fIgnoredTeams = new HashSet();
  }
  
  public void ignoreTeam(String pTeamname) {
    fIgnoredTeams.add(pTeamname);
  }
  
  public boolean isTeamIgnored(String pTeamname) {
    return fIgnoredTeams.contains(pTeamname);
  }

}
