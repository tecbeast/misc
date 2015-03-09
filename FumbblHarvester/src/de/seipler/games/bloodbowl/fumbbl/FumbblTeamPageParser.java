package de.seipler.games.bloodbowl.fumbbl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.seipler.games.bloodbowl.Coach;
import de.seipler.games.bloodbowl.Player;
import de.seipler.games.bloodbowl.Team;

/**
 * 
 * @author Georg Seipler
 */
public class FumbblTeamPageParser {

  // <th>#</th><th>Name</th><th>Position</th><th>Ma</th><th>St</th><th>Ag</th><th>Av</th><th>Skills</th><th>Inj</th><th>G</th><th>Cp</th><th>Td</th><th>It</th><th>Cs</th><th>vp</th><th>Sp</th><th>Cost</th><th>Op</th></tr>
  // <tr class="odd" align="center"><td>1</td><td align="left"><img border="" src="images/bio.png" /> <a href="FUMBBL.php?page=player&amp;player_id=3429173">Orrosh Eisklinge</a></td><td align="left">Blitzer</td><td>6</td><td>3</td><td>3</td><td>9</td><td align="left">Block, Guard, Dodge, Mighty Blow</td><td>&nbsp</td><td>8</td><td>0</td><td>7</td><td>1</td><td>8</td><td>2</td><td>49</td><td align="right">80k&nbsp;(158k)</td><td>&nbsp;</td></tr>
  private static final Pattern _PATTERN_PLAYER_LINE = Pattern.compile("<tr[^>]*><td[^>]*>([0-9]+)</td><td[^>]*>(<img[^>]+>\\s*)*<a href=\"([^\"]+)\">([^<]+)</a></td><td[^>]*>([^<]+)</td><td[^>]*>([0-9]+)</td><td[^>]*>([0-9]+)</td><td[^>]*>([0-9]+)</td><td[^>]*>([0-9]+)</td><td[^>]*>([^<]+)</td><td[^>]*>([^<]+)</td><td[^>]*>([0-9]+)</td><td[^>]*>([0-9]+)</td><td[^>]*>([0-9]+)</td><td[^>]*>([0-9]+)</td><td[^>]*>([0-9]+)</td><td[^>]*>([0-9]+)</td><td[^>]*>([0-9]+)</td><td[^>]*>([0-9]+)k[^(]+\\(([0-9]+)k\\)</td><td[^>]*>([^<]+)</td></tr>", Pattern.CASE_INSENSITIVE);

  // <th style="background: black; color: white">Team Name:</th><td align="center">Bond Girl Boot Camp</td>
  private static final Pattern _PATTERN_TEAM_NAME = Pattern.compile("<th[^>]*>Team Name:</th><td[^>]*>([^<]+)</td>", Pattern.CASE_INSENSITIVE);
  // <th style="background: black; color: white">Re-Rolls (120k):</th><td align="center">4</td>
  private static final Pattern _PATTERN_RE_ROLLS = Pattern.compile("<th[^>]*>Re-Rolls \\(([0-9]+)k\\):</th><td[^>]*>([0-9]+)</td>", Pattern.CASE_INSENSITIVE);
  // <th style="background: black; color: white">Race:</th><td align="center">Amazon</td>
  private static final Pattern _PATTERN_RACE = Pattern.compile("<th[^>]*>Race:</th><td[^>]*>([^<]+)</td>", Pattern.CASE_INSENSITIVE);
  // <th style="background: black; color: white">Fan Factor:</th><td align="center">13</td>
  private static final Pattern _PATTERN_FAN_FACTOR = Pattern.compile("<th[^>]*>Fan Factor:</th><td[^>]*>([0-9]+)</td>", Pattern.CASE_INSENSITIVE); 
  // <th style="background: black; color: white">Rating:</th><td align="center">100 / 94</td>
  private static final Pattern _PATTERN_TEAM_RATING = Pattern.compile("<th[^>]*>Rating:</th><td[^>]*>([0-9]+) / ([0-9]+)</td>", Pattern.CASE_INSENSITIVE); 
  // <th style="background: black; color: white">Assistant Coaches:</th><td align="center">0</td>
  private static final Pattern _PATTERN_assistantCoaches = Pattern.compile("<th[^>]*>Assistant Coaches:</th><td[^>]*>([0-9]+)</td>", Pattern.CASE_INSENSITIVE); 
  // <th style="background: black; color: white">Treasury:</th><td align="center">0</td>
  private static final Pattern _PATTERN_TREASURY = Pattern.compile("<th[^>]*>Treasury:</th><td[^>]*>([0-9]+)</td>", Pattern.CASE_INSENSITIVE); 
  // <th style="background: black; color: white">Cheerleaders:</th><td align="center">0</td>
  private static final Pattern _PATTERN_CHEERLEADERS = Pattern.compile("<th[^>]*>Cheerleaders:</th><td[^>]*>([0-9]+)</td>", Pattern.CASE_INSENSITIVE); 
  // <th style="background: black; color: white">Coach:</th><td align="center"><a href="/~Kalimar">Kalimar</a></td>
  private static final Pattern _PATTERN_COACH = Pattern.compile("<th[^>]*>Coach:</th><td[^>]*><a href=\"([^\"]+)\">([^<]+)</a></td>", Pattern.CASE_INSENSITIVE);
  // <th style="background: black; color: white">Apothecary:</th><td align="center">Yes</td>
  private static final Pattern _PATTERN_APOTHECARY = Pattern.compile("<th[^>]*>Apothecary:</th><td[^>]*>([^<]+)</td>", Pattern.CASE_INSENSITIVE);
  // <th style="background: black; color: white">Team Value:</th><td align="center">1440k</td><th style="background: black; color: white">Team Wizard:</th><td align="center">No</td>
  private static final Pattern _PATTERN_TEAM_WIZARD = Pattern.compile("<th[^>]*>Team Wizard:</th><td[^>]*>([^<]+)</td>", Pattern.CASE_INSENSITIVE);
  
  public void parseInto(Team pTeam, String pTeamPage) {
    
    Matcher matcherTeamName = _PATTERN_TEAM_NAME.matcher(pTeamPage);
    if (matcherTeamName.find()) {
      pTeam.setName(matcherTeamName.group(1));
    }
    
    Matcher matcherReRolls = _PATTERN_RE_ROLLS.matcher(pTeamPage);
    if (matcherReRolls.find()) {
      pTeam.setReRollCost(Integer.parseInt(matcherReRolls.group(1)));
      pTeam.setReRolls(Integer.parseInt(matcherReRolls.group(2)));
    }
    
    Matcher matcherRace = _PATTERN_RACE.matcher(pTeamPage);
    if (matcherRace.find()) {
      pTeam.setRace(matcherRace.group(1));
    }
    
    Matcher matcherFanFactor = _PATTERN_FAN_FACTOR.matcher(pTeamPage);
    if (matcherFanFactor.find()) {
      pTeam.setFanFactor(Integer.parseInt(matcherFanFactor.group(1)));
    }
    
    Matcher matcherTeamRating = _PATTERN_TEAM_RATING.matcher(pTeamPage);
    if (matcherTeamRating.find()) {
      pTeam.setTeamRating(Integer.parseInt(matcherTeamRating.group(1)));
      pTeam.setTeamValue(Integer.parseInt(matcherTeamRating.group(2)));
    }
    
    Matcher matcherAssistantCoaches = _PATTERN_assistantCoaches.matcher(pTeamPage);
    if (matcherAssistantCoaches.find()) {
      pTeam.setAssistentCoaches(Integer.parseInt(matcherAssistantCoaches.group(1)));
    }

    Matcher matcherTreasury = _PATTERN_TREASURY.matcher(pTeamPage);
    if (matcherTreasury.find()) {
      pTeam.setTreasury(Integer.parseInt(matcherTreasury.group(1)));
    }
    
    Matcher matcherCheerleaders = _PATTERN_CHEERLEADERS.matcher(pTeamPage);
    if (matcherCheerleaders.find()) {
      pTeam.setCheerleaders(Integer.parseInt(matcherCheerleaders.group(1)));
    }
    
    Matcher matcherCoach = _PATTERN_COACH.matcher(pTeamPage);
    if (matcherCoach.find()) {
      Coach coach = new Coach();
      coach.setUrl(FumbblUtil.buildFumbblUrl(matcherCoach.group(1)));
      coach.setName(matcherCoach.group(2));
      pTeam.setCoach(coach);
    }
    
    Matcher matcherApothecary = _PATTERN_APOTHECARY.matcher(pTeamPage);
    if (matcherApothecary.find()) {
      pTeam.setApothecary("yes".equals(matcherApothecary.group(1).toLowerCase()));
    }
    
    Matcher matcherTeamWizard = _PATTERN_TEAM_WIZARD.matcher(pTeamPage);
    if (matcherTeamWizard.find()) {
      pTeam.setTeamWizard("yes".equals(matcherTeamWizard.group(1).toLowerCase()));
    }
  
    Matcher matcherPlayerLine = _PATTERN_PLAYER_LINE.matcher(pTeamPage);
    while (matcherPlayerLine.find()) {
      Player player = new Player();
      player.setNumber(Integer.parseInt(matcherPlayerLine.group(1)));
      player.setUrl(FumbblUtil.buildFumbblUrl(matcherPlayerLine.group(3)));
      player.setName(matcherPlayerLine.group(4));
      player.setPosition(matcherPlayerLine.group(5));
      player.setMovement(Integer.parseInt(matcherPlayerLine.group(6)));
      player.setStrength(Integer.parseInt(matcherPlayerLine.group(7)));
      player.setAgility(Integer.parseInt(matcherPlayerLine.group(8)));
      player.setArmour(Integer.parseInt(matcherPlayerLine.group(9)));
      String skillList = matcherPlayerLine.group(10);
      String[] skills = skillList.split(",\\s*");
      if ((skills.length > 0) && !skills[0].startsWith("&")) {
        for (int i = 0; i < skills.length; i++) {
          player.addSkill(skills[i]);
        }
      }
      String injuryList = matcherPlayerLine.group(11);
      String[] injuries = injuryList.split(",\\s*");
      if ((injuries.length > 0) && !injuries[0].startsWith("&")) {
        for (int i = 0; i < injuries.length; i++) {
          player.addInjury(injuries[i]);
        }
      }
      player.setGames(Integer.parseInt(matcherPlayerLine.group(12)));
      player.setCompletions(Integer.parseInt(matcherPlayerLine.group(13)));
      player.setTouchdowns(Integer.parseInt(matcherPlayerLine.group(14)));
      player.setInterceptions(Integer.parseInt(matcherPlayerLine.group(15)));
      player.setCasualties(Integer.parseInt(matcherPlayerLine.group(16)));
      player.setPlayerAwards(Integer.parseInt(matcherPlayerLine.group(17)));
      player.setStarPlayerPoints(Integer.parseInt(matcherPlayerLine.group(18)));
      player.setCost(Integer.parseInt(matcherPlayerLine.group(19)));
      player.setValue(Integer.parseInt(matcherPlayerLine.group(20)));
      pTeam.addPlayer(player);
    }
    
  }

}
