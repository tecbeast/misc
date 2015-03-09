package de.seipler.games.bloodbowl;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Georg Seipler
 */
public class Team {

  private String fName;
  private int fReRolls;
  private int fReRollCost;
  private String fRace;
  private int fFanFactor;
  private int fTeamRating;
  private int fTeamValue;
  private int fAssistentCoaches;
  private int fTreasury;
  private int fCheerleaders;
  private boolean fApothecary;
  private boolean fTeamWizard;
  private String fUrl;
  private Coach fCoach;
  private List fPlayers;
  
  public Team() {
    fPlayers = new ArrayList();
  }

  public boolean hasApothecary() {
    return fApothecary;
  }

  public void setApothecary(boolean pApothecary) {
    fApothecary = pApothecary;
  }

  public int getAssistentCoaches() {
    return fAssistentCoaches;
  }

  public void setAssistentCoaches(int pAssistentCoaches) {
    fAssistentCoaches = pAssistentCoaches;
  }

  public int getCheerleaders() {
    return fCheerleaders;
  }

  public void setCheerleaders(int pCheerleaders) {
    fCheerleaders = pCheerleaders;
  }

  public Coach getCoach() {
    return fCoach;
  }

  public void setCoach(Coach pCoach) {
    fCoach = pCoach;
  }

  public int getFanFactor() {
    return fFanFactor;
  }

  public void setFanFactor(int pFanFactor) {
    fFanFactor = pFanFactor;
  }

  public String getName() {
    return fName;
  }

  public void setName(String pName) {
    fName = pName;
  }

  public String getRace() {
    return fRace;
  }

  public void setRace(String pRace) {
    fRace = pRace;
  }

  public int getReRolls() {
    return fReRolls;
  }

  public void setReRolls(int pReRolls) {
    fReRolls = pReRolls;
  }

  public int getTeamRating() {
    return fTeamRating;
  }

  public void setTeamRating(int pTeamRating) {
    fTeamRating = pTeamRating;
  }

  public boolean hasTeamWizard() {
    return fTeamWizard;
  }

  public void setTeamWizard(boolean pTeamWizard) {
    fTeamWizard = pTeamWizard;
  }

  public int getTreasury() {
    return fTreasury;
  }

  public void setTreasury(int pTreasury) {
    fTreasury = pTreasury;
  }
  
  public void addPlayer(Player pPlayer) {
    pPlayer.setTeam(this);
    fPlayers.add(pPlayer);
  }
  
  public Player[] getPlayers() {
    return (Player[]) fPlayers.toArray(new Player[fPlayers.size()]);
  }
  
  public int getReRollCost() {
    return fReRollCost;
  }
  
  public void setReRollCost(int pReRollCost) {
    fReRollCost = pReRollCost;
  }
  
  public int getTeamValue() {
    return fTeamValue;
  }
  
  public void setTeamValue(int pTeamValue) {
    fTeamValue = pTeamValue;
  }
  
  public String getUrl() {
    return fUrl;
  }
  
  public void setUrl(String pUrl) {
    fUrl = pUrl;
  }
  
  public String toString() {
    String newLine = System.getProperty("line.separator", "\n");
    StringBuffer buffer = new StringBuffer();
    buffer.append("[Team]");
    buffer.append(newLine);
    buffer.append("Name: ");
    buffer.append(getName());
    buffer.append(newLine);
    buffer.append("Url: ");
    buffer.append(getUrl());
    buffer.append(newLine);
    buffer.append("ReRolls: ");
    buffer.append(getReRolls());
    buffer.append(newLine);
    buffer.append("ReRollCost: ");
    buffer.append(getReRollCost());
    buffer.append(newLine);
    buffer.append("Race: ");
    buffer.append(getRace());
    buffer.append(newLine);
    buffer.append("FanFactor: ");
    buffer.append(getFanFactor());
    buffer.append(newLine);
    buffer.append("TeamRating: ");
    buffer.append(getTeamRating());
    buffer.append(newLine);
    buffer.append("TeamValue: ");
    buffer.append(getTeamValue());
    buffer.append(newLine);
    buffer.append("AssistentCoaches: ");
    buffer.append(getAssistentCoaches());
    buffer.append(newLine);
    buffer.append("Treasury: ");
    buffer.append(getTreasury());
    buffer.append(newLine);
    buffer.append("Cheerleaders: ");
    buffer.append(getCheerleaders());
    buffer.append(newLine);
    buffer.append("Apothecary: ");
    buffer.append(Boolean.toString(hasApothecary()));
    buffer.append(newLine);
    buffer.append("TeamWizard: ");
    buffer.append(Boolean.toString(hasTeamWizard()));
    buffer.append(newLine);
    buffer.append(newLine);
    buffer.append(getCoach());
    Player[] players = getPlayers();
    for (int i = 0; i < players.length; i++) {
      buffer.append(newLine);
      buffer.append(players[i]);
    }
    return buffer.toString();
  }
  
}