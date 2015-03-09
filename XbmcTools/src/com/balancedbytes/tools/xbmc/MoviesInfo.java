package com.balancedbytes.tools.xbmc;

import java.io.File;

public class MoviesInfo {

  private String fTitle;
  private int fFSK;
  private int fYear;
  private String fCollection;
  private int fPart;
  private File fFile;
  
  public String getTitle() {
    return fTitle;
  }
  
  public void setTitle(String pTitle) {
    fTitle = pTitle;
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
  
  public String getCollection() {
    return fCollection;
  }
  
  public void setCollection(String pCollection) {
    fCollection = pCollection;
  }
  
  public int getPart() {
    return fPart;
  }
  
  public void setPart(int pPart) {
    fPart = pPart;
  }
  
  public File getFile() {
    return fFile;
  }
  
  public void setFile(File pFile) {
    fFile = pFile;
  }
  
}
