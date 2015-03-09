package de.seipler.games.bloodbowl.fumbbl;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.seipler.games.bloodbowl.Group;
import de.seipler.games.bloodbowl.GroupConfiguration;
import de.seipler.games.bloodbowl.Team;
import de.seipler.games.bloodbowl.TournamentConfiguration;
import de.seipler.games.bloodbowl.TournamentMatch;
import de.seipler.games.bloodbowl.output.IHarvesterOutputHandler;
import de.seipler.games.bloodbowl.util.HttpUtil;

/**
 * 
 * @author Georg Seipler
 */
public class FumbblHarvester {
  
  private static final Pattern _MATCH_PATTERN = Pattern.compile("([0-9]+):([^:]+):([0-9]+)-([^:]+):([0-9]+)");
  private static final Pattern _TEAM_PATTERN = Pattern.compile("([^:]+):([+-]?[0-9]+)");
  
  private String fConfigFile;
  private Properties fProperties;
  
  public FumbblHarvester(String pConfigFile) {
    fConfigFile = pConfigFile;
    fProperties = new Properties();
  }
  
  public void run() throws IOException {

    InputStream configInputStream = new FileInputStream(fConfigFile);
    fProperties.load(configInputStream);
    configInputStream.close();

    String groupId = fProperties.getProperty(IHarvesterProperties.GROUP_ID);
    String outputHandlerClassName = fProperties.getProperty(IHarvesterProperties.OUTPUT_HANDLER);
    
    Group group = new Group();
    FumbblGroupPageParser groupPageParser = new FumbblGroupPageParser();

    String tournamentIdsProperty = fProperties.getProperty(IHarvesterProperties.TOURNAMENT_IDS);
    if ((tournamentIdsProperty != null) && (tournamentIdsProperty.length() > 0)) {
      String[] tournamentIds = tournamentIdsProperty.split(",");
      for (int i = 0; i < tournamentIds.length; i++) {
        System.out.println("Fetching Group Id \""+ groupId + "\" Tournament Id \"" + tournamentIds[i] + "\"");
        String fumbblTournamentUrl = FumbblUtil.buildFumbblUrl(
          IFumbblData.FUMBBL_GROUP_PAGE_PREFIX + groupId + IFumbblData.FUMBBL_GROUP_TOURNAMENT_PREFIX + tournamentIds[i]
        );
        String tournamentPage = HttpUtil.fetchPage(fumbblTournamentUrl);
        groupPageParser.parseInto(group, tournamentPage);
      }
    } else {
      System.out.println("Fetching Fumbbl Group Id \"" + groupId + "\"");
      String fumbblGroupUrl = FumbblUtil.buildFumbblUrl(IFumbblData.FUMBBL_GROUP_PAGE_PREFIX + groupId);
      String groupPage = HttpUtil.fetchPage(fumbblGroupUrl);
      groupPageParser.parseInto(group, groupPage);
    }
    
    FumbblTeamPageParser teamPageParser = new FumbblTeamPageParser();
    Team[] teams = group.getTeams();
    for (int i = 0; i < teams.length; i++) {
      System.out.println("Fetching Fumbbl Team \"" + teams[i].getName() + "\"");
      String teamPage = HttpUtil.fetchPage(teams[i].getUrl());
      teamPageParser.parseInto(teams[i], teamPage);
    }
    
    int matchNr = 1;
    String matchProperty = new StringBuffer(IHarvesterProperties.MATCH_PREFIX).append(matchNr).toString();
    while (fProperties.getProperty(matchProperty) != null) {
      Matcher matchMatcher = _MATCH_PATTERN.matcher(fProperties.getProperty(matchProperty));
      if (matchMatcher.lookingAt()) {
        int round = Integer.parseInt(matchMatcher.group(1));
        Team team1 = group.getTeamByName(matchMatcher.group(2));
        Team team2 = group.getTeamByName(matchMatcher.group(4));
        if ((team1 != null) && (team2 != null)) {
          TournamentMatch match = group.getMatchByTeamsAndRound(round, team1, team2);
          if (match != null) {            
            int score1 = Integer.parseInt(matchMatcher.group(3));
            int score2 = Integer.parseInt(matchMatcher.group(5));
            if (match.getTeam1().getName().equals(team1.getName())) {
              match.setResult(score1, score2);
            } else {
              match.setResult(score2, score1);
            }
            match.setStatus(TournamentMatch.STATUS_SET);
            System.out.print("Match \"");
            System.out.print(match.getTeam1().getName() + "\" vs. \"" + match.getTeam2().getName());
            System.out.println("\" result is " + match.getScore1() + ":" + match.getScore2());
          }
        }
      }
      matchNr++;
      matchProperty = new StringBuffer(IHarvesterProperties.MATCH_PREFIX).append(matchNr).toString();
    }
    
    String pointsWin = fProperties.getProperty(IHarvesterProperties.POINTS_WIN);
    String pointsDraw = fProperties.getProperty(IHarvesterProperties.POINTS_DRAW);
    String pointsLoss = fProperties.getProperty(IHarvesterProperties.POINTS_LOSS);
    String pointsTdFor = fProperties.getProperty(IHarvesterProperties.POINTS_TD_FOR);
    String pointsTdAgainst = fProperties.getProperty(IHarvesterProperties.POINTS_TD_AGAINST);
    TournamentConfiguration tournamentConfiguration = new TournamentConfiguration();
    tournamentConfiguration.setPointsWin((pointsWin != null) ? Integer.parseInt(pointsWin) : 0);
    tournamentConfiguration.setPointsDraw((pointsDraw != null) ? Integer.parseInt(pointsDraw) : 0);
    tournamentConfiguration.setPointsLoss((pointsLoss != null) ? Integer.parseInt(pointsLoss) : 0);
    tournamentConfiguration.setPointsTdFor((pointsTdFor != null) ? Integer.parseInt(pointsTdFor) : 0);
    tournamentConfiguration.setPointsTdAgainst((pointsTdAgainst != null) ? Integer.parseInt(pointsTdAgainst) : 0);
    
    int teamPointsNr = 1;
    String teamPointsProperty = new StringBuffer(IHarvesterProperties.TEAM_POINTS_PREFIX).append(teamPointsNr).toString();
    while (fProperties.getProperty(teamPointsProperty) != null) {
      Matcher teamMatcher = _TEAM_PATTERN.matcher(fProperties.getProperty(teamPointsProperty));
      if (teamMatcher.lookingAt()) {
        Team team = group.getTeamByName(teamMatcher.group(1));
        String extraPointsString = teamMatcher.group(2);
        if (extraPointsString.startsWith("+")) {
          extraPointsString = extraPointsString.substring(1);
        }
        int extraPoints = Integer.parseInt(extraPointsString);
        tournamentConfiguration.addExtraPointsForTeam(team, extraPoints);
        System.out.println("Team \"" + team.getName() + "\" gets " + extraPoints + " extra points.");
      }
      teamPointsNr++;
      teamPointsProperty = new StringBuffer(IHarvesterProperties.TEAM_POINTS_PREFIX).append(teamPointsNr).toString();
    }
    
    GroupConfiguration groupConfiguration = new GroupConfiguration();
    int teamIgnoreNr = 1;
    String teamIgnoreProperty = new StringBuffer(IHarvesterProperties.TEAM_IGNORE_PREFIX).append(teamIgnoreNr).toString();
    while (fProperties.getProperty(teamIgnoreProperty) != null) {
      String ignoredTeamName = fProperties.getProperty(teamIgnoreProperty);
      groupConfiguration.ignoreTeam(ignoredTeamName);
      System.out.println("Team \"" + ignoredTeamName + "\" is ignored.");
      teamIgnoreNr++;
      teamIgnoreProperty = new StringBuffer(IHarvesterProperties.TEAM_IGNORE_PREFIX).append(teamIgnoreNr).toString();
    }

    try {
      Class outputHandlerClass = Class.forName(outputHandlerClassName);
      IHarvesterOutputHandler outputHandler = (IHarvesterOutputHandler) outputHandlerClass.newInstance();
      outputHandler.initialize(fProperties);
      outputHandler.beginOutput();
      outputHandler.writePlayerStatistics(group.getPlayerStatistics(groupConfiguration));
      outputHandler.writeTournamentStatistics(group.getTournamentStatistics(groupConfiguration, tournamentConfiguration));
      outputHandler.endOutput();
    } catch (InstantiationException e) {
      System.err.println("Property " + IHarvesterProperties.OUTPUT_HANDLER + " not set or unknown class.");
    } catch (IllegalAccessException iae) {
      System.err.println("Property " + IHarvesterProperties.OUTPUT_HANDLER + " not set or unknown class.");
    } catch (ClassNotFoundException cnfe) {
      System.err.println("Property " + IHarvesterProperties.OUTPUT_HANDLER + " not set or unknown class.");
    }
    
  }
  
  public static void main(String[] args) {
    
    if ((args == null) || (args.length != 1)) {
      System.out.println("Usage: java -jar FumbblHarvester.jar propertyFile");
      System.exit(0);
    }

    FumbblHarvester harvester = new FumbblHarvester(args[0]);
    try {
      harvester.run();
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
            
  }

}
