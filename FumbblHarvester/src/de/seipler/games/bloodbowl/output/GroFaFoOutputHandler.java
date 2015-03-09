package de.seipler.games.bloodbowl.output;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

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
public class GroFaFoOutputHandler implements IHarvesterOutputHandler {
  
  public static final String FILENAME_PLAYER_STATISTICS = "PlayerStatistics.txt";
  public static final String FILENAME_TOURNAMENT_STATISTICS = "TournamentStatistics.txt";
  
  private File fFileTournamentStatistics;
  private File fFilePlayerStatistics;
  
  public void initialize(Properties pProperties) {
    String outputDirectoryName = pProperties.getProperty(IHarvesterProperties.OUTPUT_DIRECTORY);
    File outputDirectory = new File(outputDirectoryName);
    fFileTournamentStatistics = new File(outputDirectory, FILENAME_TOURNAMENT_STATISTICS);
    fFilePlayerStatistics = new File(outputDirectory, FILENAME_PLAYER_STATISTICS);
  }
  
  public void beginOutput() {
  }
  
  public void writeTournamentStatistics(TournamentStatistics[] pTournamentStatistics) throws IOException {
    
    System.out.println("Writing " + fFileTournamentStatistics.getAbsolutePath());
    BufferedWriter out = new BufferedWriter(new FileWriter(fFileTournamentStatistics));
    
    for (int i = 0; i < pTournamentStatistics.length; i++) {
      
      out.newLine();
      out.write("[b][u]" + pTournamentStatistics[i].getTournament().getName());
      out.write(" am Ende des " + pTournamentStatistics[i].getRound() + ". Spieltages[/u][/b]");
      out.newLine();
      
      out.newLine();
      out.write("[table]");
      
      RankedTeam[] rankedTeams = pTournamentStatistics[i].getRankedTeams();
      for (int j = 0; j < rankedTeams.length; j++) {
        Team team = rankedTeams[j].getTeam();
        Coach coach = team.getCoach();
        out.newLine();
        out.write("[tr]");
        out.write("[td]([b]" + rankedTeams[j].getRank() + "[/b])[/td]");
        out.write("[td][/td]");
        out.write("[td]");
        out.write("[url=" + team.getUrl() + "]" + team.getName() + "[/url]");
        out.write(" von [url=" + coach.getUrl() + "]" + coach.getName() + "[/url]");
        out.write(" mit " + rankedTeams[j].getPoints() + " Punkten");
        out.write(" und " + rankedTeams[j].getTouchdownsFor() + ":" + rankedTeams[j].getTouchdownsAgainst() + " TDs");
        out.write("[/td]");
        out.write("[/tr]");
      }
      
      out.newLine();
      out.write("[/table]");
      out.newLine();
      
    }
    
    out.close();
    
  }
  
  public void writePlayerStatistics(PlayerStatistics pPlayerStatistics) throws IOException {
    
    System.out.println("Writing " + fFilePlayerStatistics.getAbsolutePath());
    BufferedWriter out = new BufferedWriter(new FileWriter(fFilePlayerStatistics));
    
    out.newLine();
    out.write("[b][u]Wertvollster Spieler (die meisten Star-Spieler-Punkte):[/u][/b]");
    out.newLine();
    
    out.newLine();
    out.write("[table]");
    RankedPlayer[] top5Spp = pPlayerStatistics.topRankedStarPlayerPoints();
    for (int i = 0; i < top5Spp.length; i++) {
      StringBuffer statistic = new StringBuffer();
      statistic.append(top5Spp[i].getPlayer().getStarPlayerPoints());
      statistic.append(" Star-Spieler-Punkte");
      writePlayerStatisticsInTableRows(out, statistic.toString(), top5Spp[i]);
    }
    out.newLine();
    out.write("[/table]");
    out.newLine();
    
    out.newLine();
    out.write("[b][u]Bester Werfer (die meisten erfolgreichen Pässe):[/u][/b]");
    out.newLine();
    
    out.newLine();
    out.write("[table]");
    RankedPlayer[] top5Cp = pPlayerStatistics.topRankedCompletions();
    for (int i = 0; i < top5Cp.length; i++) {
      StringBuffer statistic = new StringBuffer();
      statistic.append(top5Cp[i].getPlayer().getCompletions());
      statistic.append(" Pässe");
      writePlayerStatisticsInTableRows(out, statistic.toString(), top5Cp[i]);
    }
    out.newLine();
    out.write("[/table]");
    out.newLine();
    
    out.newLine();
    out.write("[b][u]Bester Scorer (die meisten erzielten Touchdowns):[/u][/b]");
    out.newLine();
    
    out.newLine();
    out.write("[table]");
    RankedPlayer[] top5Td = pPlayerStatistics.topRankedTouchdowns();
    for (int i = 0; i < top5Td.length; i++) {
      StringBuffer statistic = new StringBuffer();
      statistic.append(top5Td[i].getPlayer().getTouchdowns());
      statistic.append(" Touchdowns");
      writePlayerStatisticsInTableRows(out, statistic.toString(), top5Td[i]);
    }
    out.newLine();
    out.write("[/table]");
    out.newLine();
    
    out.newLine();
    out.write("[b][u]Bester Abfänger (die meisten geglückten Interceptions):[/u][/b]");
    out.newLine();

    out.newLine();
    out.write("[table]");
    RankedPlayer[] top5Int = pPlayerStatistics.topRankedInterceptions();
    for (int i = 0; i < top5Int.length; i++) {
      StringBuffer statistic = new StringBuffer();
      statistic.append(top5Int[i].getPlayer().getInterceptions());
      statistic.append(" Interceptions");
      writePlayerStatisticsInTableRows(out, statistic.toString(), top5Int[i]);
    }
    out.newLine();
    out.write("[/table]");
    out.newLine();

    out.newLine();
    out.write("[b][u]Brutalster Spieler (die meisten verursachten Verletzungen):[/u][/b]");
    out.newLine();

    out.newLine();
    out.write("[table]");
    RankedPlayer[] top5Cas = pPlayerStatistics.topRankedCasualties();
    for (int i = 0; i < top5Cas.length; i++) {
      StringBuffer statistic = new StringBuffer();
      statistic.append(top5Cas[i].getPlayer().getCasualties());
      statistic.append(" Verletzte");
      writePlayerStatisticsInTableRows(out, statistic.toString(), top5Cas[i]);
    }
    out.newLine();
    out.write("[/table]");
    out.newLine();
    
    out.newLine();
    out.write("[b][u]Publikumsliebling (die meisten \"Spieler des Tages\"-Auszeichnungen):[/u][/b]");
    out.newLine();

    out.newLine();
    out.write("[table]");
    RankedPlayer[] top5Mvp = pPlayerStatistics.topRankedPlayerAwards();
    for (int i = 0; i < top5Mvp.length; i++) {
      StringBuffer statistic = new StringBuffer();
      statistic.append(top5Mvp[i].getPlayer().getPlayerAwards());
      statistic.append(" Auszeichnungen");
      writePlayerStatisticsInTableRows(out, statistic.toString(), top5Mvp[i]);
    }
    out.newLine();
    out.write("[/table]");
    out.newLine();

    out.close();
    
  }
  
  public void endOutput() {
  }
  
  private void writePlayerStatisticsInTableRows(BufferedWriter pOut, String pStatistic, RankedPlayer pRankedPlayer) throws IOException {
    Player player = pRankedPlayer.getPlayer();
    Team team = player.getTeam();
    Coach coach = team.getCoach();
    pOut.newLine();
    pOut.write("[tr]");
    pOut.write("[td]([b]" + pRankedPlayer.getRank() + "[/b])[/td]");
    pOut.write("[td][/td]");
    pOut.write("[td]" + pStatistic + " in " + player.getGames() + " Spielen[/td]");
    pOut.write("[/tr]");
    pOut.newLine();
    pOut.write("[tr]");
    pOut.write("[td][/td]");
    pOut.write("[td][/td]");
    pOut.write("[td]für Spieler [url=" + player.getUrl() + "]" + player.getName() + "[/url]");
    pOut.write(" (" + team.getRace() + " " + player.getPosition() + ")[/td]");
    pOut.write("[/tr]");
    pOut.newLine();
    pOut.write("[tr]");
    pOut.write("[td][/td]");
    pOut.write("[td][/td]");
    pOut.write("[td]aus Team [url=" + team.getUrl() + "]" + team.getName() + "[/url]");
    pOut.write(" von Coach [url=" + coach.getUrl() + "]" + coach.getName() + "[/url][/td]");
    pOut.write("[/tr]");
  }
  
}
