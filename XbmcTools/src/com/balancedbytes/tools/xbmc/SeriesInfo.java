package com.balancedbytes.tools.xbmc;

public class SeriesInfo {

  private String fTitle;
  private int fNrSeasons;
  private int fMinSeason;
  private int fMaxSeason;
  private int fFSK;
  private int fYear;
  
  public String getTitle() {
    return fTitle;
  }
  
  public void setTitle(String pTitle) {
    fTitle = pTitle;
  }
  
  public int getMinSeason() {
    return fMinSeason;
  }
  
  public void setMinSeason(int pMinSeason) {
    fMinSeason = pMinSeason;
  }
  
  public int getMaxSeason() {
    return fMaxSeason;
  }
  
  public void setMaxSeason(int pMaxSeason) {
    fMaxSeason = pMaxSeason;
  }
  
  public int getFSK() {
    return fFSK;
  }
  
  public void setFSK(int pFSK) {
    fFSK = pFSK;
  }
  
  public int getYear() {
    return fYear;
  }
  
  public void setYear(int pYear) {
    fYear = pYear;
  }
  
  public int getNrSeasons() {
    return fNrSeasons;
  }
  
  public void setNrSeasons(int pNrSeasons) {
    fNrSeasons = pNrSeasons;
  }
  
}
