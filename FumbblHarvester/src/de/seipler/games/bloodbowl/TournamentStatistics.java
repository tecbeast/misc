package de.seipler.games.bloodbowl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author Georg Seipler
 */
public class TournamentStatistics {

  private int fRound;
  private Tournament fTournament;
  private GroupConfiguration fGroupConfiguration;
  private TournamentConfiguration fTournamentConfiguration;
  private List fRankedTeams;
  
  public TournamentStatistics(GroupConfiguration pGroupConfiguration, TournamentConfiguration pTournamentConfiguration, Tournament pTournament) {
    fGroupConfiguration = pGroupConfiguration;
    fTournamentConfiguration = pTournamentConfiguration;
    fTournament = pTournament;
    fRankedTeams = new ArrayList();
    evaluateMatchesAndRankTeams();
  }
  
  public Tournament getTournament() {
    return fTournament;
  }
  
  public TournamentConfiguration getTournamentConfiguration() {
    return fTournamentConfiguration;
  }
  
  public GroupConfiguration getGroupConfiguration() {
    return fGroupConfiguration;
  }
  
  public RankedTeam[] getRankedTeams() {
    return (RankedTeam[]) fRankedTeams.toArray(new RankedTeam[fRankedTeams.size()]);
  }
  
  public int getRound() {
    return fRound;
  }
  
  private void evaluateMatchesAndRankTeams() {
    
    Map rankedTeamByTeam = new HashMap();
    Team[] teams = fTournament.getTeams();
    for (int i = 0; i < teams.length; i++) {
      RankedTeam rankedTeam = new RankedTeam(teams[i]);
      int extraPoints = fTournamentConfiguration.getExtraPointsForTeam(teams[i]);
      rankedTeam.setPoints(extraPoints);
      fRankedTeams.add(rankedTeam);
      rankedTeamByTeam.put(teams[i], rankedTeam);
    }
    
    int minUnfinishedRound = Integer.MAX_VALUE;
    TournamentMatch[] matches = fTournament.getMatches();
    for (int i = 0; i < matches.length; i++) {
      if ((matches[i].getStatus() == TournamentMatch.STATUS_UNPLAYED)
        && (matches[i].getRound() < minUnfinishedRound)) {
        minUnfinishedRound = matches[i].getRound();
      }
    }
    
    if (minUnfinishedRound < Integer.MAX_VALUE) {
      fRound = minUnfinishedRound - 1;
    } else {
      fRound = fTournament.getNrOfRounds();
    }
    
    for (int i = 0; i < matches.length; i++) {
      if (matches[i].getRound() <= fRound) {
        RankedTeam rankedTeam1 = (RankedTeam) rankedTeamByTeam.get(matches[i].getTeam1());
        RankedTeam rankedTeam2 = (RankedTeam) rankedTeamByTeam.get(matches[i].getTeam2());
        fTournamentConfiguration.evaluateMatch(rankedTeam1, rankedTeam2, matches[i].getScore1(), matches[i].getScore2());
      }
    }
    
    Iterator rankedTeamIterator = fRankedTeams.iterator();
    while (rankedTeamIterator.hasNext()) {
      RankedTeam rankedTeam = (RankedTeam) rankedTeamIterator.next();
      if (fGroupConfiguration.isTeamIgnored(rankedTeam.getTeam().getName())) {
        rankedTeamIterator.remove();
      }
    }

    RankedTeamComparatorPoints teamComparator = new RankedTeamComparatorPoints();
    Collections.sort(fRankedTeams, teamComparator);
    int rank = 0;
    for (int i = 0; i < fRankedTeams.size(); i++) {
      if ((i == 0) || (teamComparator.compareWithoutNames((RankedTeam) fRankedTeams.get(i - 1), (RankedTeam) fRankedTeams.get(i)) != 0)) {
        rank++;
      }
      ((RankedTeam) fRankedTeams.get(i)).setRank(rank);
    }
    
  }
    
  public String toString() {
    String newLine = System.getProperty("line.separator", "\n"); 
    StringBuffer buffer = new StringBuffer();
    buffer.append("[Tournament]");
    buffer.append(newLine);
    buffer.append("Round: ");
    buffer.append(getRound());
    buffer.append(newLine);
    RankedTeam[] rankedTeams = getRankedTeams();
    for (int i = 0; i < rankedTeams.length; i++) {
      buffer.append(newLine);
      buffer.append(rankedTeams[i]);
    }
    return buffer.toString();
  }
  
}
