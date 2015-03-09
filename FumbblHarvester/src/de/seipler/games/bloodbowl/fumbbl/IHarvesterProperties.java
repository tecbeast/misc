package de.seipler.games.bloodbowl.fumbbl;

/**
 * 
 * @author Georg Seipler
 */
public interface IHarvesterProperties {

  String GROUP_ID = "groupId";
  String TOURNAMENT_IDS = "tournamentIds";
  String OUTPUT_HANDLER = "outputHandler";

  String POINTS_WIN = "points.win";
  String POINTS_DRAW = "points.draw";
  String POINTS_LOSS = "points.loss";
  String POINTS_TD_FOR = "points.tdFor";
  String POINTS_TD_AGAINST = "points.tdAgainst";
  
  String OUTPUT_DIRECTORY = "outputDirectory";
  
  String FTP_HOST = "ftp.host";
  String FTP_UPLOAD_DIRECTORY = "ftp.uploadDirectory";
  String FTP_USER = "ftp.user";
  String FTP_PASSWORD = "ftp.password";
  
  String MATCH_PREFIX = "match."; 
  String TEAM_POINTS_PREFIX = "team.points.";
  String TEAM_IGNORE_PREFIX = "team.ignore.";
  
}
