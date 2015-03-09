package de.seipler.games.bloodbowl.output;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.net.ftp.FTPClient;

import de.seipler.games.bloodbowl.Coach;
import de.seipler.games.bloodbowl.Player;
import de.seipler.games.bloodbowl.PlayerStatistics;
import de.seipler.games.bloodbowl.RankedPlayer;
import de.seipler.games.bloodbowl.RankedTeam;
import de.seipler.games.bloodbowl.Team;
import de.seipler.games.bloodbowl.TournamentStatistics;
import de.seipler.games.bloodbowl.fumbbl.IHarvesterProperties;

/**
 * 
 * @author Georg Seipler
 */
public class WebServerOutputHandler implements IHarvesterOutputHandler {
  
  public static final String FILENAME_PLAYER_STATISTICS = "PlayerStatistics.html";
  public static final String FILENAME_TOURNAMENT_STATISTICS = "TournamentStatistics.html";
  public static final DateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy");
  
  private File fFileTournamentStatistics;
  private File fFilePlayerStatistics;
  private String fFtpHost;
  private String fFtpUploadDirectory;
  private String fFtpUser;
  private String fFtpPassword;
  
  public void initialize(Properties pProperties) {
    String outputDirectoryName = pProperties.getProperty(IHarvesterProperties.OUTPUT_DIRECTORY);
    File outputDirectory = new File(outputDirectoryName);
    fFileTournamentStatistics = new File(outputDirectory, FILENAME_TOURNAMENT_STATISTICS);
    fFilePlayerStatistics = new File(outputDirectory, FILENAME_PLAYER_STATISTICS);
    fFtpHost = pProperties.getProperty(IHarvesterProperties.FTP_HOST);
    fFtpUploadDirectory = pProperties.getProperty(IHarvesterProperties.FTP_UPLOAD_DIRECTORY);
    fFtpUser = pProperties.getProperty(IHarvesterProperties.FTP_USER);
    fFtpPassword = pProperties.getProperty(IHarvesterProperties.FTP_PASSWORD);
  }
  
  public void beginOutput() throws IOException {
  }
  
  public void writeTournamentStatistics(TournamentStatistics[] pTournamentStatistics) throws IOException {
    
    System.out.println("Writing " + fFileTournamentStatistics.getAbsolutePath());
    fFileTournamentStatistics.getParentFile().mkdirs();
    BufferedWriter out = new BufferedWriter(new FileWriter(fFileTournamentStatistics));
    
    String datum = DATE_FORMAT.format(new Date());
    String title = new StringBuffer("Tabelle Stand ").append(datum).toString();
    writePageHeader(out, title);

    for (int i = 0; i < pTournamentStatistics.length; i++) {
      
      out.newLine();
      out.write("<h3>" + pTournamentStatistics[i].getTournament().getName());
      out.write(" am Ende des " + pTournamentStatistics[i].getRound() + ". Spieltages</h3>");
      out.newLine();
      
      out.newLine();
      out.write("<table border=\"0\">");
      
      RankedTeam[] rankedTeams = pTournamentStatistics[i].getRankedTeams();
      for (int j = 0; j < rankedTeams.length; j++) {
        Team team = rankedTeams[j].getTeam();
        Coach coach = team.getCoach();
        out.newLine();
        out.write("<tr>");
        out.write("<td>(<b>" + rankedTeams[j].getRank() + "</b>)</td>");
        out.write("<td></td>");
        out.write("<td>");
        out.write("<a href=\"" + team.getUrl() + "\">" + team.getName() + "</a>");
        out.write(" von <a href=\"" + coach.getUrl() + "\">" + coach.getName() + "</a>");
        out.write(" mit " + rankedTeams[j].getPoints() + " Punkten");
        out.write(", (" + rankedTeams[j].getGamesWon() + "/" + rankedTeams[j].getGamesTied() + "/" + rankedTeams[j].getGamesLost() + ") Siegen");
        out.write(" und " + rankedTeams[j].getTouchdownsFor() + ":" + rankedTeams[j].getTouchdownsAgainst() + " Touchdowns");
        out.write("</td>");
        out.write("</tr>");
      }
      
      out.newLine();
      out.write("</table>");
      out.newLine();
      
    }
    
    writePageFooter(out);

    out.close();
    
  }
  
  public void writePlayerStatistics(PlayerStatistics pPlayerStatistics) throws IOException {
    
    System.out.println("Writing " + fFilePlayerStatistics.getAbsolutePath());
    fFilePlayerStatistics.getParentFile().mkdirs();
    BufferedWriter out = new BufferedWriter(new FileWriter(fFilePlayerStatistics));

    String datum = DATE_FORMAT.format(new Date());
    String title = new StringBuffer("Spielerstatistiken Stand ").append(datum).toString();
    writePageHeader(out, title);
    
    out.newLine();
    out.write("<h3>Wertvollster Spieler (die meisten Star-Spieler-Punkte):</h3>");
    out.newLine();
    
    out.newLine();
    out.write("<table border=\"0\">");
    RankedPlayer[] top5Spp = pPlayerStatistics.topRankedStarPlayerPoints();
    for (int i = 0; i < top5Spp.length; i++) {
      StringBuffer statistic = new StringBuffer();
      statistic.append(top5Spp[i].getPlayer().getStarPlayerPoints());
      statistic.append(" Star-Spieler-Punkte");
      writePlayerStatisticsInTableRows(out, statistic.toString(), top5Spp[i]);
    }
    out.newLine();
    out.write("</table>");
    out.newLine();
    
    out.newLine();
    out.write("<h3>Bester Werfer (die meisten erfolgreichen Pässe):</h3>");
    out.newLine();
    
    out.newLine();
    out.write("<table border=\"0\">");
    RankedPlayer[] top5Cp = pPlayerStatistics.topRankedCompletions();
    for (int i = 0; i < top5Cp.length; i++) {
      StringBuffer statistic = new StringBuffer();
      statistic.append(top5Cp[i].getPlayer().getCompletions());
      statistic.append(" Pässe");
      writePlayerStatisticsInTableRows(out, statistic.toString(), top5Cp[i]);
    }
    out.newLine();
    out.write("</table>");
    out.newLine();
    
    out.newLine();
    out.write("<h3>Bester Scorer (die meisten erzielten Touchdowns):</h3>");
    out.newLine();
    
    out.newLine();
    out.write("<table border=\"0\">");
    RankedPlayer[] top5Td = pPlayerStatistics.topRankedTouchdowns();
    for (int i = 0; i < top5Td.length; i++) {
      StringBuffer statistic = new StringBuffer();
      statistic.append(top5Td[i].getPlayer().getTouchdowns());
      statistic.append(" Touchdowns");
      writePlayerStatisticsInTableRows(out, statistic.toString(), top5Td[i]);
    }
    out.newLine();
    out.write("</table>");
    out.newLine();
    
    out.newLine();
    out.write("<h3>Bester Abfänger (die meisten geglückten Interceptions):</h3>");
    out.newLine();

    out.newLine();
    out.write("<table border=\"0\">");
    RankedPlayer[] top5Int = pPlayerStatistics.topRankedInterceptions();
    for (int i = 0; i < top5Int.length; i++) {
      StringBuffer statistic = new StringBuffer();
      statistic.append(top5Int[i].getPlayer().getInterceptions());
      statistic.append(" Interceptions");
      writePlayerStatisticsInTableRows(out, statistic.toString(), top5Int[i]);
    }
    out.newLine();
    out.write("</table>");
    out.newLine();

    out.newLine();
    out.write("<h3>Brutalster Spieler (die meisten verursachten Verletzungen):</h3>");
    out.newLine();

    out.newLine();
    out.write("<table border=\"0\">");
    RankedPlayer[] top5Cas = pPlayerStatistics.topRankedCasualties();
    for (int i = 0; i < top5Cas.length; i++) {
      StringBuffer statistic = new StringBuffer();
      statistic.append(top5Cas[i].getPlayer().getCasualties());
      statistic.append(" Verletzte");
      writePlayerStatisticsInTableRows(out, statistic.toString(), top5Cas[i]);
    }
    out.newLine();
    out.write("</table>");
    out.newLine();
    
    out.newLine();
    out.write("<h3>Publikumsliebling (die meisten \"Spieler des Tages\"-Auszeichnungen):</h3>");
    out.newLine();

    out.newLine();
    out.write("<table border=\"0\">");
    RankedPlayer[] top5Mvp = pPlayerStatistics.topRankedPlayerAwards();
    for (int i = 0; i < top5Mvp.length; i++) {
      StringBuffer statistic = new StringBuffer();
      statistic.append(top5Mvp[i].getPlayer().getPlayerAwards());
      statistic.append(" Auszeichnungen");
      writePlayerStatisticsInTableRows(out, statistic.toString(), top5Mvp[i]);
    }
    out.newLine();
    out.write("</table>");
    out.newLine();

    writePageFooter(out);

    out.close();
    
  }
  
  public void endOutput() throws IOException {
  
    FTPClient client = new FTPClient();
    client.connect(fFtpHost);
    client.login(fFtpUser, fFtpPassword);
    
    client.enterLocalPassiveMode();
    client.setFileType(FTPClient.ASCII_FILE_TYPE);
    client.changeWorkingDirectory(fFtpUploadDirectory);
    
    System.out.println("Uploading " + fFilePlayerStatistics.getName());
    uploadFileToClient(client, fFilePlayerStatistics);
    System.out.println("Uploading " + fFileTournamentStatistics.getName());
    uploadFileToClient(client, fFileTournamentStatistics);
    
    client.logout();
    client.disconnect();    
    
  }
  
  private void uploadFileToClient(FTPClient pClient, File pFile) throws IOException {

    InputStream fis = new FileInputStream(pFile);
    OutputStream os = pClient.storeFileStream(pFile.getName());
    
    byte buf[] = new byte[8192];
    int bytesRead = fis.read(buf);
    while (bytesRead != -1) {
        os.write(buf, 0, bytesRead);
        bytesRead = fis.read(buf);
    }
    fis.close();
    os.close();
    pClient.completePendingCommand();
    
  }
  
  private void writePlayerStatisticsInTableRows(BufferedWriter pOut, String pStatistic, RankedPlayer pRankedPlayer) throws IOException {
    Player player = pRankedPlayer.getPlayer();
    Team team = player.getTeam();
    Coach coach = team.getCoach();
    pOut.newLine();
    pOut.write("<tr>");
    pOut.write("<td>(<b>" + pRankedPlayer.getRank() + "</b>)</td>");
    pOut.write("<td></td>");
    pOut.write("<td>" + pStatistic + " in " + player.getGames() + " Spielen</td>");
    pOut.write("</tr>");
    pOut.newLine();
    pOut.write("<tr>");
    pOut.write("<td></td>");
    pOut.write("<td></td>");
    pOut.write("<td>für Spieler <a href=\"" + player.getUrl() + "\">" + player.getName() + "</a>");
    pOut.write(" (" + team.getRace() + " " + player.getPosition() + ")</td>");
    pOut.write("</tr>");
    pOut.newLine();
    pOut.write("<tr>");
    pOut.write("<td></td>");
    pOut.write("<td></td>");
    pOut.write("<td>aus Team <a href=\"" + team.getUrl() + "\">" + team.getName() + "</a>");
    pOut.write(" von Coach <a href=\"" + coach.getUrl() + "\">" + coach.getName() + "</a></td>");
    pOut.write("</tr>");
  }
  
  private void writePageHeader(BufferedWriter pOut, String pTitle) throws IOException {
    pOut.write("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">");
    pOut.newLine();
    pOut.write("<html>");
    pOut.newLine();
    pOut.newLine();
    pOut.write("<head>");
    pOut.newLine();
    pOut.write("<title>");
    pOut.write(pTitle);
    pOut.write("</title>");
    pOut.newLine();
    pOut.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"/georg/bloodbowl/format.css\">");
    pOut.newLine();
    pOut.write("</head>");
    pOut.newLine();
    pOut.newLine();
    pOut.write("<body>");
    pOut.newLine();
    pOut.newLine();
    pOut.write("<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">");
    pOut.newLine();
    pOut.write("<tr>");
    pOut.newLine();
    pOut.write("<td valign=\"top\"><img src=\"/georg/bloodbowl/icons/space.gif\" width=\"40\" height=\"1\"></td>");
    pOut.newLine();
    pOut.write("<td valign=\"top\">");
    pOut.newLine();
  }
  
  private void writePageFooter(BufferedWriter pOut) throws IOException {
    pOut.newLine();
    pOut.write("</td>");
    pOut.newLine();
    pOut.write("</tr>");
    pOut.newLine();
    pOut.write("</table>");
    pOut.newLine();
    pOut.newLine();
    pOut.write("</body>");
    pOut.newLine();
    pOut.newLine();
    pOut.write("</html>");
    pOut.newLine();
  }
    
}
