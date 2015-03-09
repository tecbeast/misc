package de.seipler.util.log;

import java.io.*;
import java.text.*;
import java.util.*;

/**
 * 
 * @author Georg Seipler
 */
public class LogWriter {

  private Writer writer;
  private DateFormat dateFormat;
  private String lineSeparator;
  
  /**
   * 
   */
  public LogWriter(Writer writer, DateFormat dateFormat) {
    this.writer = writer;
    this.dateFormat = dateFormat;
    lineSeparator = System.getProperty("line.separator", "\n");
  }

  /**
   * 
   */
  public void write(Class logger, LogLevel level, String message) {
    String header = buildHeader(logger, level);
    try {
      writer.write(buildMultilineMessageWithHeader(header, message));
      writer.flush();
    } catch (IOException ioe) {
      ioe.printStackTrace(System.err);
    }
  }

  /**
   * 
   */
  protected String buildHeader(Class logger, LogLevel level) {
    StringBuffer buffer = new StringBuffer();

    buffer.append(dateFormat.format(new Date()));
    buffer.append(" ");
    buffer.append(level.getFormattedName());
    buffer.append(" ");
    buffer.append(logger.getName());

    return buffer.toString();
  }

  /**
   * Spaltet den uebergebenen Text an Zeilenenden in mehrerer Zeilen auf, numeriert diese
   * und setzt vor jede Zeile den uebergebenen Headerstring.
   */
  protected String buildMultilineMessageWithHeader(String header, String textOhneParameter) {

    int lineSeparatorLength = lineSeparator.length();

    StringBuffer message = new StringBuffer();

    // Message mit Header zusamenbauen und Zeilen ggf. nummerieren

    int line = 1;
    int startPos = 0;
    int endPos = startPos;
    int length = textOhneParameter.length();
    boolean hasLineNumbers = false;

    while (startPos < length) {

      endPos = textOhneParameter.indexOf(lineSeparator, startPos);
      if (endPos < 0) {
        endPos = length;
      } else {
        if (endPos + lineSeparatorLength < length) {
          hasLineNumbers = true;
        }
      }

      if (endPos > startPos) {

        message.append(header);
        message.append("  ");

        if (hasLineNumbers) {
          message.append(rightAlignNumber(line, 3));
          message.append(": ");
        }

        message.append(textOhneParameter.substring(startPos, endPos));
        message.append(lineSeparator);

      }

      startPos = endPos + lineSeparatorLength;
      line++;

    }

    return message.toString();

  }

  /**
   * Formatiert einen String rechtsbuendig auf die angegebene Anzahl Stellen.
   */
  protected String rightAlignText(String text, int digits) {

    String result = null;
    int length = text.length();

    if (length < digits) {
      StringBuffer buffer = new StringBuffer(text);
      for (int i = 0; i < (length - digits); i++) {
        buffer.insert(' ', 0);
      }
      result = buffer.toString();

    } else {
      result = text.substring(length - digits);
    }

    return result;

  }

  /**
   * Formatiert eine Zahl rechtsbuendig mit fuehrenden Nullen auf die angegebene Anzahl Stellen.
   */
  protected String rightAlignNumber(int number, int digits) {

    StringBuffer buffer = new StringBuffer();

    for (int i = 0; i < digits; i++) {
      buffer.insert(0, number % 10);
      number = number / 10;
    }

    return buffer.toString();

  }

  /**
   * Returns the lineSeparator.
   * @return String
   */
  public String getLineSeparator() {
    return this.lineSeparator;
  }

}
