package de.seipler.games.bloodbowl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * 
 * @author Georg Seipler
 */
public class PlayerStatistics {
  
  public static final int RANKS = 5;
  
  private List fPlayers;

  public PlayerStatistics() {
    fPlayers = new ArrayList();
  }
  
  public PlayerStatistics(GroupConfiguration pGroupConfiguration, Team[] pTeams) {
    this();
    for (int i = 0; i < pTeams.length; i++) {
      if (!pGroupConfiguration.isTeamIgnored(pTeams[i].getName())) {
        addTeam(pTeams[i]);        
      }
    }
  }
  
  public void addTeam(Team pTeam) {
    Player[] players = pTeam.getPlayers();
    for (int i = 0; i < players.length; i++) {
      fPlayers.add(players[i]);
    }
  }
  
  public RankedPlayer[] topRankedStarPlayerPoints() {
    return rankPlayers(new PlayerComparatorStarPlayerPoints());
  }
  
  public RankedPlayer[] topRankedCompletions() {
    return rankPlayers(new PlayerComparatorCompletions());
  }

  public RankedPlayer[] topRankedTouchdowns() {
    return rankPlayers(new PlayerComparatorTouchdowns());
  }
  
  public RankedPlayer[] topRankedInterceptions() {
    return rankPlayers(new PlayerComparatorInterceptions());
  }

  public RankedPlayer[] topRankedCasualties() {
    return rankPlayers(new PlayerComparatorCasualties());
  }

  public RankedPlayer[] topRankedPlayerAwards() {
    return rankPlayers(new PlayerComparatorPlayerAwards());
  }
  
  private RankedPlayer[] rankPlayers(PlayerComparator pPlayerComparator) {
    List topRanked = new ArrayList();
    Collections.sort(fPlayers, pPlayerComparator);
    Player[] players = (Player[]) fPlayers.toArray(new Player[fPlayers.size()]);
    int rank = 0;
    for (int i = 0; i < players.length; i++) {
      if ((i == 0) || (pPlayerComparator.compareWithoutNames(players[i - 1], players[i]) != 0)) {
        if ((rank >= RANKS)  || (topRanked.size() >= RANKS)) {
          break;
        }
        rank++;
      }
      if (pPlayerComparator.hasComparedStat(players[i])) {
        RankedPlayer rankedPlayer = new RankedPlayer(players[i]);
        rankedPlayer.setRank(rank);
        topRanked.add(rankedPlayer);
      }
    }
    return (RankedPlayer[]) topRanked.toArray(new RankedPlayer[topRanked.size()]);
  }
  
}
