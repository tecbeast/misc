package de.seipler.games.bloodbowl;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Georg Seipler
 */
public class Player {
  
  private int fNumber;
  private String fName;
  private String fPosition;
  private int fMovement;
  private int fStrength;
  private int fAgility;
  private int fArmour;
  private List fSkills;
  private List fInjuries;
  private int fCompletions;
  private int fTouchdowns;
  private int fInterceptions;
  private int fCasualties;
  private int fPlayerAwards;
  private int fStarPlayerPoints;
  private int fCost;
  private int fValue;
  private String fUrl;
  private int fGames;
  private Team fTeam;
  
  public Player() {
    fSkills = new ArrayList();
    fInjuries = new ArrayList();
  }

  public void setAgility(int pAgility) {
    fAgility = pAgility;
  }
  
  public int getAgility() {
    return fAgility;
  }

  public void setArmour(int pArmour) {
    fArmour = pArmour;
  }
  
  public int getArmour() {
    return fArmour;
  }

  public void setCasualties(int pCasualties) {
    fCasualties = pCasualties;
  }
  
  public int getCasualties() {
    return fCasualties;
  }
  
  public void setCompletions(int pCompletions) {
    fCompletions = pCompletions;
  }

  public int getCompletions() {
    return fCompletions;
  }

  public void setCost(int pCost) {
    fCost = pCost;
  }
  
  public int getCost() {
    return fCost;
  }

  public void addInjury(String pInjury) {
    fInjuries.add(pInjury);
  }
  
  public String[] getInjuries() {
    return (String[]) fInjuries.toArray(new String[fInjuries.size()]);
  }
  
  public void setInterceptions(int pInterceptions) {
    fInterceptions = pInterceptions;
  }
  
  public int getInterceptions() {
    return fInterceptions;
  }

  public void setMovement(int pMovement) {
    fMovement = pMovement;
  }
  
  public int getMovement() {
    return fMovement;
  }

  public void setName(String pName) {
    fName = pName;
  }
  
  public String getName() {
    return fName;
  }
  
  public void setNumber(int pNumber) {
    fNumber = pNumber;
  }
  
  public int getNumber() {
    return fNumber;
  }
  
  public void setPlayerAwards(int pPlayerAwards) {
    fPlayerAwards = pPlayerAwards;
  }

  public int getPlayerAwards() {
    return fPlayerAwards;
  }
  
  public void setPosition(String pPosition) {
    fPosition = pPosition;
  }

  public String getPosition() {
    return fPosition;
  }

  public void addSkill(String pSkill) {
    fSkills.add(pSkill);
  }

  public String[] getSkills() {
    return (String[]) fSkills.toArray(new String[fSkills.size()]);
  }
  
  public void setStarPlayerPoints(int pStarPlayerPoints) {
    fStarPlayerPoints = pStarPlayerPoints;
  }
  
  public int getStarPlayerPoints() {
    return fStarPlayerPoints;
  }
  
  public void setStrength(int pStrength) {
    fStrength = pStrength;
  }

  public int getStrength() {
    return fStrength;
  }

  public void setTouchdowns(int pTouchdowns) {
    fTouchdowns = pTouchdowns;
  }
  
  public int getTouchdowns() {
    return fTouchdowns;
  }
  
  public Team getTeam() {
    return fTeam;
  }
  
  public void setTeam(Team pTeam) {
    fTeam = pTeam;
  }
  
  public String getUrl() {
    return fUrl;
  }

  public void setUrl(String pUrl) {
    fUrl = pUrl;
  }

  public int getValue() {
    return fValue;
  }

  public void setValue(int pValue) {
    fValue = pValue;
  }
  
  public int getGames() {
    return fGames;
  }
  
  public void setGames(int pGames) {
    fGames = pGames;
  }

  public String toString() {
    String newLine = System.getProperty("line.separator", "\n"); 
    StringBuffer buffer = new StringBuffer();
    buffer.append("[Player]");
    buffer.append(newLine);
    buffer.append("Number: ");
    buffer.append(getNumber());
    buffer.append(newLine);
    buffer.append("Name: ");
    buffer.append(getName());
    buffer.append(newLine);
    buffer.append("Url: ");
    buffer.append(getUrl());
    buffer.append(newLine);
    buffer.append("Position: ");
    buffer.append(getPosition());
    buffer.append(newLine);
    buffer.append("Movement: ");
    buffer.append(getMovement());
    buffer.append(newLine);
    buffer.append("Strength: ");
    buffer.append(getStrength());
    buffer.append(newLine);
    buffer.append("Agility: ");
    buffer.append(getAgility());
    buffer.append(newLine);
    buffer.append("Armour: ");
    buffer.append(getArmour());
    buffer.append(newLine);
    buffer.append("Skills: ");
    String[] skills = getSkills();
    for (int i = 0; i < skills.length; i++) {
      if (i > 0) {
        buffer.append(", ");
      }
      buffer.append(skills[i]);
    }
    buffer.append(newLine);
    buffer.append("Injuries: ");
    String[] injuries = getInjuries();
    for (int i = 0; i < injuries.length; i++) {
      if (i > 0) {
        buffer.append(", ");
      }
      buffer.append(injuries[i]);
    }
    buffer.append(newLine);
    buffer.append("Games: ");
    buffer.append(getGames());
    buffer.append(newLine);
    buffer.append("Completions: ");
    buffer.append(getCompletions());
    buffer.append(newLine);
    buffer.append("Touchdowns: ");
    buffer.append(getTouchdowns());
    buffer.append(newLine);
    buffer.append("Interceptions: ");
    buffer.append(getInterceptions());
    buffer.append(newLine);
    buffer.append("Casualties: ");
    buffer.append(getCasualties());
    buffer.append(newLine);
    buffer.append("PlayerAwards: ");
    buffer.append(getPlayerAwards());
    buffer.append(newLine);
    buffer.append("StarPlayerPoints: ");
    buffer.append(getStarPlayerPoints());
    buffer.append(newLine);
    buffer.append("Cost: ");
    buffer.append(getCost());
    buffer.append(newLine);
    buffer.append("Value: ");
    buffer.append(getValue());
    buffer.append(newLine);
    if (getTeam() != null) {
      buffer.append("Team: ");
      buffer.append(getTeam().getName());
      buffer.append(newLine);
    }
    return buffer.toString();
  }
  
}
