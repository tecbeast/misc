package de.seipler.games.bloodbowl;

import java.util.ArrayList;
import java.util.List;


/**
 * 
 * @author Georg Seipler
 */
public class Group {
  
  public static final int RANKS = 5;
  
  private List fTournaments;
  private List fTeams;
  
  public Group() {
    fTournaments = new ArrayList();
    fTeams = new ArrayList();
  }
  
  public void addTournament(Tournament pTournament) {
    fTournaments.add(pTournament);
    Team[] teams = pTournament.getTeams();
    for (int i = 0; i < teams.length; i++) {
      addTeam(teams[i]);
    }
  }
  
  public void addTeam(Team pTeam) {
    fTeams.add(pTeam);
  }
  
  public Team[] getTeams() {
    return (Team[]) fTeams.toArray(new Team[fTeams.size()]);
  }
  
  public PlayerStatistics getPlayerStatistics(GroupConfiguration pGroupConfiguration) {
    return new PlayerStatistics(pGroupConfiguration, getTeams());
  }

  public TournamentStatistics[] getTournamentStatistics(GroupConfiguration pGroupConfiguration, TournamentConfiguration pTournamentConfiguration) {
    TournamentStatistics[] tournamentStatistics = new TournamentStatistics[fTournaments.size()];
    for (int i = 0; i < fTournaments.size(); i++) {
      tournamentStatistics[i] = new TournamentStatistics(pGroupConfiguration, pTournamentConfiguration, (Tournament) fTournaments.get(i));
    }
    return tournamentStatistics;
  }
  
  public Tournament[] getTournaments() {
    return (Tournament[]) fTournaments.toArray(new Tournament[fTournaments.size()]);
  }
  
  public Team getTeamByName(String pTeamName) {
    Team team = null;
    Tournament[] tournaments = getTournaments();
    for (int i = 0; i < tournaments.length; i++) {
      team = tournaments[i].getTeamByName(pTeamName);
      if (team != null) {
        break;
      }
    }
    return team;
  }
  
  public TournamentMatch getMatchByTeamsAndRound(int pRound, Team pTeam1, Team pTeam2) {
    TournamentMatch match = null;
    Tournament[] tournaments = getTournaments();
    for (int i = 0; i < tournaments.length; i++) {
      match = tournaments[i].getMatchByTeamsAndRound(pRound, pTeam1, pTeam2);
      if (match != null) {
        break;
      }
    }
    return match;
  }
  
}
