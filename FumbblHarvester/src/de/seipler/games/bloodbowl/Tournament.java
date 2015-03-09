package de.seipler.games.bloodbowl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author Georg Seipler
 */
public class Tournament {
  
  private List fMatches;
  private Map fTeamsByName;
  private String fName;
  private int fNrOfRounds;
  
  public Tournament(String pName) {
    fName = pName;
    fMatches = new ArrayList();
    fTeamsByName = new HashMap();
    fNrOfRounds = 0;
  }
  
  public void addMatch(TournamentMatch pMatch) {
    fMatches.add(pMatch);
    addTeam(pMatch.getTeam1());
    addTeam(pMatch.getTeam2());
    if (pMatch.getRound() > fNrOfRounds) {
      fNrOfRounds = pMatch.getRound();
    }
  }
    
  public void addTeam(Team pTeam) {
    fTeamsByName.put(pTeam.getName(), pTeam);
  }

  public Team getTeamByName(String pName) {
    return (Team) fTeamsByName.get(pName);
  }
    
  public String getName() {
    return fName;
  }
  
  public int getNrOfRounds() {
    return fNrOfRounds;
  }
  
  public Team[] getTeams() {
    Team[] teams = new Team[fTeamsByName.size()];
    int i = 0;
    Iterator teamIterator = fTeamsByName.values().iterator();
    while (teamIterator.hasNext()) {
      teams[i++] = (Team) teamIterator.next();
    }
    return teams;
  }
  
  public TournamentMatch[] getMatches() {
    return (TournamentMatch[]) fMatches.toArray(new TournamentMatch[fMatches.size()]);
  }
    
  public TournamentMatch getMatchByTeamsAndRound(int pRound, Team pTeam1, Team pTeam2) {
    TournamentMatch match = null;
    TournamentMatch[] matches = getMatches();
    for (int i = 0; i < matches.length; i++) {
      if ((matches[i].getRound() == pRound)
        && ((matches[i].getTeam1().equals(pTeam1) && matches[i].getTeam2().equals(pTeam2))
        || (matches[i].getTeam1().equals(pTeam2) && matches[i].getTeam2().equals(pTeam1)))) {
        match = matches[i];
        break;
      }
    }
    return match;
  }
  
}
