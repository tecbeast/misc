package de.seipler.web.fetchurl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Represents an HTML tag.
 * 
 * @author Georg Seipler
 */
public class Tag {

  private String name = null;
  private List attributeList = null;
  private boolean isClosing = false;
  private boolean isComment = false;
  private Attribute dataAttribute = null;
  private Attribute linkAttribute = null;


  /**
   * 
   */
  public Tag(String name) {
    this(name, new LinkedList(), null, null, false, true);
  }
  
  /**
   * 
   */
  public Tag(String name, List attributeList, boolean isClosing) {
    this(name, attributeList, findDataAttribute(attributeList), findLinkAttribute(attributeList), isClosing, false);
  }
 
  /**
   * 
   */
  private Tag(String name, List attributeList, Attribute dataAttribute, Attribute linkAttribute, boolean isClosing, boolean isComment) {
    setName(name);
    this.attributeList = attributeList;
    setDataAttribute(dataAttribute);
    setLinkAttribute(linkAttribute);
    setIsClosing(isClosing);
    setIsComment(isComment);
  }
  
  /**
   * 
   */
  public static Attribute findDataAttribute(List attributeList) {
    Iterator iterator = attributeList.iterator();
    while (iterator.hasNext()) {
      Attribute attribute = (Attribute) iterator.next();
      if (attribute.isData()) { return attribute; }
    }
    return null;
  }
  
  /**
   * 
   */
  public static Attribute findLinkAttribute(List attributeList) {
    Iterator iterator = attributeList.iterator();
    while (iterator.hasNext()) {
      Attribute attribute = (Attribute) iterator.next();
      if (attribute.isLink()) { return attribute; }
    }
    return null;
  }
  
  /**
   * 
   */
  public Attribute getDataAttribute() {
    return this.dataAttribute;
  }
 
  /**
   * 
   */
  public Attribute getLinkAttribute() {
    return this.linkAttribute;
  }
 
  /**
   * 
   */
  public String getName() {
    return this.name;
  }
  
  /**
   * 
   */
  public boolean isClosing() {
    return this.isClosing;
  }
 
  /**
   * 
   */
  public boolean isComment() {
    return this.isComment;
  }

  /**
   * 
   */
  public static Tag parseTag(String tagString) {
    
    Tag resultTag = null;

    // TODO: do this better ...
    // see if the whole thing is a comment
    if (tagString.startsWith("!--") && tagString.endsWith("--") && (tagString.length() > 4)) {
      resultTag = new Tag(tagString.substring(1));
    // otherwise we need to parse the whole enchilada 
    } else {     
      // first off we need to eliminate multiple Whitespace
      // as well as any Whitespace before or after an equal sign ('=')
      // so that we can later easily distinguish key=value pairs
      boolean quoted = false;
      StringBuffer buffer = new StringBuffer();
      for (int i = 0; i < tagString.length(); i++) {
        char inChar = tagString.charAt(i);
        if (!quoted && Character.isWhitespace(inChar)) {
          if (i + 1 < tagString.length()) {
            if (tagString.charAt(i + 1) == '=') {
              i++;
              buffer.append('=');
              if ((i + 1 < tagString.length()) && (Character.isWhitespace(tagString.charAt(i + 1)))) { i++; }
            } else if (!Character.isWhitespace(tagString.charAt(i + 1))) {
              buffer.append(' ');
            }
          }
        } else if (!quoted && ((inChar == '<') || (inChar == '>'))) {
          // don't add any opening or closing brackets
        } else if ((inChar == '"') || (inChar == '\'')) {
          quoted = !quoted;
          buffer.append(inChar);
        } else {
          buffer.append(inChar);
        }
      }

      // for exact parsing we need no whitespace up front and one at the end
      tagString = buffer.toString().trim() + " ";

      boolean isClosing = false;
      List attributeList = new ArrayList();

      buffer.setLength(0);
      String token = null, key = null, name = null;
      boolean inValue = false, isName = true, singleQuoted = false, doubleQuoted = false;
      Attribute dataAttribute = null, linkAttribute = null;

      // we split the remaining string into tokens
      // finding key=value pairs in the process and building the whole tag
      StringTokenizer tokenizer = new StringTokenizer(tagString, " =\"'", true);
      while (tokenizer.hasMoreTokens()) {
        token = tokenizer.nextToken();
        if (!singleQuoted && !doubleQuoted && token.equals(" ")) {
          if (inValue) {
            Attribute attribute = new Attribute(key, buffer.toString());
            if (attribute.isData()) { dataAttribute = attribute; }
            if (attribute.isLink()) { linkAttribute = attribute; }
            attributeList.add(attribute);
            inValue = false;
          } else {
            if (isName) {
              if (buffer.charAt(0) == '/') {
                isClosing = true;
                name = buffer.toString().substring(1).toLowerCase();
              } else {
                isClosing = false;
                name = buffer.toString().toLowerCase();
              }
              isName = false;
            } else {
              // simple attribute
              Attribute attribute = new Attribute(buffer.toString().toLowerCase());
              attributeList.add(attribute);
            }
          }
          buffer.setLength(0);
        } else if (!singleQuoted && !doubleQuoted && token.equals("=")) {
          key = buffer.toString().toLowerCase();
          buffer.setLength(0);
          inValue = true;
        } else if (token.equals("\"")) {
          if (singleQuoted) { buffer.append('"'); } else { doubleQuoted = !doubleQuoted; }
        } else if (token.equals("'")) {
          if (doubleQuoted) { buffer.append('\''); } else { singleQuoted = !singleQuoted; }
        } else {
          buffer.append(token);
        }
      }
      
      resultTag = new Tag(name, attributeList, dataAttribute, linkAttribute, isClosing, false);
      
    }
    
    return resultTag;
    
  }

  /**
   * 
   */
  private void setDataAttribute(Attribute dataAttribute) {
    this.dataAttribute = dataAttribute;
  }

  /**
   * 
   */
  private void setIsClosing(boolean isClosing) {
    this.isClosing = isClosing;
  }
  
  /**
   * 
   */
  private void setIsComment(boolean isComment) {
    this.isComment = isComment;
  }

  /**
   * 
   */
  private void setLinkAttribute(Attribute linkAttribute) {
    this.linkAttribute = linkAttribute;
  }

  /**
   * 
   */
  private void setName(String name) {
    this.name = name;
  }
    
  /**
   * 
   */
  public String toString() {
    StringBuffer buffer = new StringBuffer();
    buffer.append('<');
    if (this.isComment) { buffer.append('!'); }
    if (this.isClosing) { buffer.append('/'); }
    buffer.append(this.name);
    if (this.attributeList != null) {
      Iterator iterator = this.attributeList.iterator();
      while (iterator.hasNext()) {
        buffer.append(' ');
        buffer.append(iterator.next().toString());
      }
    }
    buffer.append('>');
    return buffer.toString();
  }
  
}