package de.seipler.games.bloodbowl.fumbbl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.seipler.games.bloodbowl.Group;
import de.seipler.games.bloodbowl.Team;
import de.seipler.games.bloodbowl.Tournament;
import de.seipler.games.bloodbowl.TournamentMatch;

/**
 * 
 * @author Georg Seipler
 */
public class FumbblGroupPageParser {
  
  // <th>Tournaments</th>
  private static final Pattern _PATTERN_TOURNAMENTS = Pattern.compile("<th>Tournaments</th>", Pattern.CASE_INSENSITIVE);
  // <div style="text-align: center; font-weight: bold;">GroFaFo Liga 1 - Saison 2</div>
  private static final Pattern _PATTERN_TOURNAMENT_NAME = Pattern.compile("<div[^>]*>([^<]+)</div>", Pattern.CASE_INSENSITIVE);
  // <div style="font-weight: bold; text-align center;">Round 1</div>
  private static final Pattern _PATTERN_ROUND_NUMBER = Pattern.compile("<div[^>]*>Round ([0-9]+)</div>", Pattern.CASE_INSENSITIVE);
  // <div style="font-size: 1.5em; text-align: center;">Tournament Members</div>
  private static final Pattern _PATTERN_TOURNAMENT_MEMBERS = Pattern.compile("<div[^>]*>Tournament Members</div>", Pattern.CASE_INSENSITIVE);
  // <table border="0" width="100%"><tr valign="middle" align="left"><td width="1"><a href="FUMBBL.php?page=match&amp;id=1494811"><img border="0" src="FUMBBL/Images/Details_small.gif" /></a></td><td><a href="FUMBBL.php?page=team&amp;op=view&amp;team_id=291572">Orroshs Wilder Haufen</a><br /><a href="FUMBBL.php?page=team&amp;op=view&amp;team_id=291826" style="font-weight: normal;">Belegost Bashers</a></td><td align="right">1<br />0</td></tr></table>
  // <table border="0" width="100%"><tr valign="middle" align="left"><td width="1"></td><td><a href="FUMBBL.php?page=team&amp;op=view&amp;team_id=291572" style="font-weight: normal;">Orroshs Wilder Haufen</a><br /><a href="FUMBBL.php?page=team&amp;op=view&amp;team_id=291726" style="font-weight: normal;">Permanent Disaster</a></td><td align="right"><br /></td></tr></table>
  private static final Pattern _PATTERN_MATCH = Pattern.compile("<table[^>]*><tr[^>]*><td[^>]*>(<a href=\"([^\"]+)\"><img[^>]*></a>)?</td><td[^>]*><a href=\"([^\"]+)\"[^>]*>([^<]+)</a><br[^>]*><a href=\"([^\"]+)\"[^>]*>([^<]+)</a></td><td[^>]*>([0-9]*)<br[^>]*>([0-9]*)</td></tr></table>", Pattern.CASE_INSENSITIVE);
    
  public void parseInto(Group pGroup, String pGroupPage) {

    Matcher matcherTournaments = _PATTERN_TOURNAMENTS.matcher(pGroupPage);
    Matcher matcherTournamentName = _PATTERN_TOURNAMENT_NAME.matcher(pGroupPage);
    Matcher matcherTournamentMembers = _PATTERN_TOURNAMENT_MEMBERS.matcher(pGroupPage);
    Matcher matcherRoundNumber = _PATTERN_ROUND_NUMBER.matcher(pGroupPage);
    Matcher matchMatcher = _PATTERN_MATCH.matcher(pGroupPage);
    
    if (matcherTournaments.find()) {
      
      int startPosLeague = matcherTournaments.end();
      while (matcherTournamentName.find(startPosLeague) && matcherTournamentMembers.find(startPosLeague)) {

        String tournamentName = matcherTournamentName.group(1);
        Tournament tournament = new Tournament(tournamentName);
        
        int endPosLeague = matcherTournamentMembers.start();
        List roundStartPositions = new ArrayList();
        List roundEndPositions = new ArrayList();
        int startPosRound = startPosLeague;
        int endPosRound = endPosLeague;
        
        while (matcherRoundNumber.find(startPosRound) && (startPosRound < endPosLeague)) {
          startPosRound = matcherRoundNumber.end();
          endPosRound = matcherRoundNumber.start();
          if (startPosRound > endPosLeague) {
            startPosRound = endPosLeague;
            roundEndPositions.add(new Integer(endPosRound));
          } else {
            if (roundStartPositions.size() > 0) {
              roundEndPositions.add(new Integer(endPosRound));
            }
            roundStartPositions.add(new Integer(startPosRound));
          }
        }
        roundEndPositions.add(new Integer(endPosLeague));
        
        for (int i = 0; i < roundStartPositions.size(); i++) {
          
          startPosRound = ((Integer) roundStartPositions.get(i)).intValue();
          endPosRound = ((Integer) roundEndPositions.get(i)).intValue();
  
          int currentPos = startPosRound;
          
          while (matchMatcher.find(currentPos) && (currentPos < endPosRound)) {
            currentPos = matchMatcher.end();
            if (currentPos < endPosRound) {
              String team1Name = matchMatcher.group(4);
              String team1Url = FumbblUtil.buildFumbblUrl(matchMatcher.group(3));
              Team team1 = getTeam(tournament, team1Name, team1Url);
              String team2Name = matchMatcher.group(6);
              String team2Url = FumbblUtil.buildFumbblUrl(matchMatcher.group(5)); 
              Team team2 = getTeam(tournament, team2Name, team2Url);
              TournamentMatch match = new TournamentMatch(i + 1, team1, team2);
              String matchUrl = matchMatcher.group(2);
              if ((matchUrl != null) && (matchUrl.length() > 0)) {
                match.setUrl(FumbblUtil.buildFumbblUrl(matchUrl));
              }
              String team1Score = matchMatcher.group(7);
              String team2Score = matchMatcher.group(8);
              if ((team1Score != null) && (team1Score.length() > 0) && (team2Score != null) && (team2Score.length() > 0)) {
                int score1 = Integer.parseInt(team1Score);
                int score2 = Integer.parseInt(team2Score);
                match.setResult(score1, score2);
                match.setStatus(TournamentMatch.STATUS_PLAYED);
              }
              tournament.addMatch(match);
            }
          }
          
        }
        
        pGroup.addTournament(tournament);
        startPosLeague = matcherTournamentMembers.end();
        
      }
      
    }
    
  }
  
  private Team getTeam(Tournament pTournament, String pTeamName, String pTeamUrl) {
    Team team = pTournament.getTeamByName(pTeamName);
    if (team == null) {
      team = new Team();
      team.setName(pTeamName);
      team.setUrl(pTeamUrl);
    }
    return team;
  }
    
}
