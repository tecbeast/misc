package de.seipler.web.fetchurl;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * Represents an HTML page.
 * 
 * @author Georg Seipler
 */
public class Page {
  
  private URL base;
  private List contentList;
  private List dataTagList;
  private List linkTagList;

  /**
   * 
   */
  protected Page(URL base, List contentList, List dataTagList, List linkTagList) {
    setBase(base);
    setContentList(contentList);
    setDataTagList(dataTagList);
    setLinkTagList(linkTagList);
  }

  /**
   * 
   */
  public URL getBase() {
    return this.base;
  }

  /**
   * 
   */
  public List getContentList() {
    return this.contentList;
  }

  /**
   * 
   */
  public List getDataTagList() {
    return this.dataTagList;
  }
  
  /**
   * 
   */
  public List getLinkTagList() {
    return this.linkTagList;
  }
  
  /**
   * 
   */
  public static Page read(Reader in, URL base) throws IOException {

    List contentList = new ArrayList();

    int inChar = 0;
    int oldChar = 0;
    int veryOldChar = 0;
    boolean inTag = false;
    boolean inComment = false;
    List dataTagList = new ArrayList();
    List linkTagList = new ArrayList();
    StringBuffer buffer = new StringBuffer();

    while ((inChar = in.read())>= 0) {
      switch (inChar) {
        case (int) '<':
          if (!inTag && !inComment) {
            inTag = true;
            if (buffer.length() > 0) {
              contentList.add(buffer.toString());
              buffer.setLength(0);
            }
          } else {
            buffer.append('<');
          }
          break;
        case (int) '>':
          if (inComment && (oldChar == (int) '-') && (veryOldChar == (int) '-')) {
            Tag tag = Tag.parseTag(buffer.toString());
            contentList.add(tag);
            buffer.setLength(0);
            inComment = false;
          } else if (inTag) {
            inTag = false;
            if (buffer.length() > 0) {
              Tag tag = Tag.parseTag(buffer.toString());
              if (tag.getDataAttribute() != null) { dataTagList.add(tag); }
              if (tag.getLinkAttribute() != null) {
                if (tag.getName().equals("base")) {
                  base = new URL(tag.getLinkAttribute().getValue());
                } else {
                  linkTagList.add(tag);
                }
              }
              contentList.add(tag);
              buffer.setLength(0);
            }
          } else {
            buffer.append('>');
          }
          break;
        case (int) '-':
          buffer.append('-');            
          if (inTag && !inComment && (oldChar == (int) '-') && (veryOldChar == (int) '!')) {
            inTag = false;
            inComment = true;
          }
          break;
        default:
          buffer.append((char) inChar);
          break;
      }
      veryOldChar = oldChar;
      oldChar = inChar;
    }

    // if there are any leftover chars, add them as String
    if (buffer.length() > 0) {
      contentList.add(buffer.toString());
    }
    
    return new Page(base, contentList, dataTagList, linkTagList);
    
  }
  
  /**
   * 
   */
  public int write(Writer out) throws IOException {
    int bytesWritten = 0;
    Iterator iterator = contentList.iterator();
    while (iterator.hasNext()) {
      String buffer = iterator.next().toString();
      out.write(buffer);
      bytesWritten += buffer.length();
    }
    return bytesWritten;
  }

  /**
   * 
   */
  private void setBase(URL base) {
    this.base = base;
  }

  /**
   * 
   */
  private void setContentList(List contentList) {
    this.contentList = contentList;
  }

  /**
   * 
   */
  private void setDataTagList(List dataTagList) {
    this.dataTagList = dataTagList;
  }

  /**
   * 
   */
  private void setLinkTagList(List linkTagList) {
    this.linkTagList = linkTagList;
  }

  /**
   * 
   */
  public String toString() {
    StringBuffer buffer = new StringBuffer();
    Iterator iterator = contentList.iterator();
    while (iterator.hasNext()) {
      buffer.append(iterator.next().toString());
    }
    return buffer.toString();
  }

}