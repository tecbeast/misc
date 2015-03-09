package de.seipler.games.bloodbowl.output;

import java.io.IOException;
import java.util.Properties;

import de.seipler.games.bloodbowl.PlayerStatistics;
import de.seipler.games.bloodbowl.TournamentStatistics;

/**
 * 
 * @author Georg Seipler
 */
public interface IHarvesterOutputHandler {
  
  void initialize(Properties pProperties) throws IOException;

  void beginOutput() throws IOException;
  
  void writeTournamentStatistics(TournamentStatistics[] pTournamentStatistics) throws IOException;
  
  void writePlayerStatistics(PlayerStatistics pPlayerStatistics) throws IOException;
  
  void endOutput() throws IOException;

}
