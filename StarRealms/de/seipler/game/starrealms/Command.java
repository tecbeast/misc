package de.seipler.game.starrealms;

import java.io.Serializable;

/**
 * A regular Ashes Command. Usually parsed from player input.
 * Consists of:
 * <ul>
 *   <li>token: the command itself, taken from Parser</li>
 *   <li>nr: amount of something (e.g. number of fighters)</li>
 *   <li>type: type of something (e.g. send fighter or transporter)</li>
 *   <li>source: planet the command is issued on</li>
 *   <li>dest: destination for the command (e.g. send x from here to <em>there</em>)</li>
 *   <li>player: player giving the command</li>
 *   <li>list: list of associated objects</li>
 *   <li>text: a text attribute for the command (e.g. a message)</li>
 * </ul>
 * @see Parser
 */
public class Command implements Serializable {

  private int token, number, type, source, destination, player;
  private String text;

  /**
   * Default constructor.
   * Token must be specified at creation time.
   */
  public Command(int token) {
    this.token = token;
  }

  /**
   * Returns destination (planet).
   */
  public int getDestination() {
    return destination;
  }

  /**
   * Returns number.
   */
  public int getNumber() {
    return number;
  }

  /**
   * Returns player.
   */
  public int getPlayer() {
    return player;
  }

  /**
   * Returns source (planet).
   */
  public int getSource() {
    return source;
  }

  /**
   * Returns text.
   */
  public String getText() {
    return text;
  }

  /**
   * Returns token (command).
   */
  public int getToken() {
    return token;
  }

  /**
   * Returns type.
   */
  public int getType() {
    return type;
  }

  /**
   * Specifies destination (planet).
   */
  public void setDestination(int destination) {
    this.destination = destination;
  }

  /**
   * Specifies number.
   */
  public void setNumber(int number) {
    this.number = number;
  }

  /**
   * Specifies executing player.
   */
  public void setPlayer(int player) {
    this.player = player;
  }

  /**
   * Specifies source (planet).
   */
  public void setSource(int source) {
    this.source = source;
  }

  /**
   * Specifies text.
   */
  public void setText(String text) {
    this.text = text;
  }

  /**
   * Specifies type.
   */
  public void setType(int type) {
    this.type = type;
  }

  /**
   * Returns String representation of this Command.
   */
  public String toString() {
    StringBuffer buffer = new StringBuffer();
    buffer.append("player ");
    buffer.append(player);
    buffer.append(": ");
    buffer.append(Parser.getText(token));
    buffer.append(" ");

    switch (token) {
      case Parser.GAME :
      case Parser.TURN :
        buffer.append(number);
        break;
      case Parser.USER :
      case Parser.PASSWORD :
      case Parser.PLAYERNAME :
        buffer.append(text);
        break;
      case Parser.BUILD :
        buffer.append(number);
        buffer.append(" ");
        buffer.append(Parser.getText(type));
        buffer.append(" on ");
        buffer.append(source);
        break;
      case Parser.DECLARE :
        buffer.append(Parser.getText(type));
        buffer.append(" with ");
        buffer.append(destination);
        break;
      case Parser.HOMEPLANET :
        buffer.append("on ");
        buffer.append(source);
        break;
      case Parser.MESSAGE :
        buffer.append("to ");
        buffer.append(destination);
        buffer.append(' ');
        buffer.append('"');
        buffer.append(text);
        buffer.append('"');
        break;
      case Parser.PLANETNAME :
        buffer.append(text);
        buffer.append(" on ");
        buffer.append(source);
        break;
      case Parser.RESEARCH :
        buffer.append(Parser.getText(type));
        buffer.append(" on ");
        buffer.append(source);
        break;
      case Parser.SEND :
        buffer.append(number);
        buffer.append(" ");
        buffer.append(Parser.getText(type));
        buffer.append(" to ");
        buffer.append(destination);
        break;
      case Parser.SPY :
        buffer.append("on ");
        buffer.append(source);
        break;
      default :
        break;
    }
    return buffer.toString();
  }
  
}
