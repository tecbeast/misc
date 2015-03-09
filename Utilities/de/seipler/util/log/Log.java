package de.seipler.util.log;

import java.io.*;

/**
 * <p> The six logging levels used by <code>Log</code> are (in order):
 * <ol>
 * <li>trace (the least serious)</li>
 * <li>debug</li>
 * <li>info</li>
 * <li>warn</li>
 * <li>error</li>
 * <li>fatal (the most serious)</li>
 * </ol>
 *
 * <p>Performance is often a logging concern.
 * By examining the appropriate property,
 * a component can avoid expensive operations (producing information
 * to be logged).</p>
 *
 * <p> For example,
 * <code><pre>
 *    if (log.isDebugEnabled()) {
 *        ... do something expensive ...
 *        log.debug(theResult);
 *    }
 * </pre></code>
 * </p>
 *
 * @author Georg Seipler
 */
public class Log {
  
  private Class logger;
  private LogLevel level;
  private LogWriter writer;
  
  /**
   * Default constructor.
   */
  protected Log(Class logger, LogLevel level, LogWriter writer) {
    setLogger(logger);
    setLevel(level);
    setWriter(writer);
  }    

  /**
   * <p>Is trace logging currently enabled?</p>
   *
   * <p>Call this method to prevent having to perform expensive operations
   * (for example, <code>String</code> concatination)
   * when the log level is more than trace.</p>
   */
  public boolean isTraceEnabled() {
    return (this.level.getValue() >= LogLevel.TRACE.getValue());
  }

  /**
   * <p>Is debug logging currently enabled?</p>
   *
   * <p>Call this method to prevent having to perform expensive operations
   * (for example, <code>String</code> concatination)
   * when the log level is more than debug.</p>
   */
  public boolean isDebugEnabled() {
    return (this.level.getValue() >= LogLevel.DEBUG.getValue());
  }

  /**
   * <p>Is info logging currently enabled?</p>
   *
   * <p>Call this method to prevent having to perform expensive operations
   * (for example, <code>String</code> concatination)
   * when the log level is more than info.</p>
   */
  public boolean isInfoEnabled() {
    return (this.level.getValue() >= LogLevel.INFO.getValue());
  }

  /**
   * <p>Is warning logging currently enabled?</p>
   *
   * <p>Call this method to prevent having to perform expensive operations
   * (for example, <code>String</code> concatination)
   * when the log level is more than warning.</p>
   */
  public boolean isWarnEnabled() {
    return (this.level.getValue() >= LogLevel.WARN.getValue());
  }    

  /**
   * <p> Is error logging currently enabled? </p>
   *
   * <p> Call this method to prevent having to perform expensive operations
   * (for example, <code>String</code> concatination)
   * when the log level is more than error. </p>
   */
  public boolean isErrorEnabled() {
    return (this.level.getValue() >= LogLevel.ERROR.getValue());
  }
    
  /**
   * <p> Is fatal logging currently enabled? </p>
   *
   * <p> Call this method to prevent having to perform expensive operations
   * (for example, <code>String</code> concatination)
   * when the log level is more than fatal. </p>
   */
  public boolean isFatalEnabled() {
    return (this.level.getValue() >= LogLevel.FATAL.getValue());
  }

  /**
   * <p>Log a message with trace log level.</p>
   */
  public void trace(Object message) {
    if (isTraceEnabled()) {
      writer.write(this.logger, LogLevel.TRACE, message.toString());
    }
  }

  /**
   * <p>Log an error with trace log level.</p>
   */
  public void trace(Object message, Throwable t) {
    if (isTraceEnabled()) {
      StringBuffer buffer = new StringBuffer();
      buffer.append(message);
      buffer.append(writer.getLineSeparator());
      buffer.append(buildStacktrace(t));
      writer.write(this.logger, LogLevel.TRACE, buffer.toString());
    }
  }

  /**
   * <p>Log a message with debug log level.</p>
   */
  public void debug(Object message) {
    if (isDebugEnabled()) {
      writer.write(this.logger, LogLevel.DEBUG, message.toString());
    }
  }

  /**
   * <p>Log an error with debug log level.</p>
   */
  public void debug(Object message, Throwable t) {
    if (isDebugEnabled()) {
      StringBuffer buffer = new StringBuffer();
      buffer.append(message);
      buffer.append(writer.getLineSeparator());
      buffer.append(buildStacktrace(t));
      writer.write(this.logger, LogLevel.DEBUG, buffer.toString());
    }
  }

  /**
   * <p>Log a message with info log level.</p>
   */
  public void info(Object message) {
    if (isInfoEnabled()) {
      writer.write(this.logger, LogLevel.INFO, message.toString());
    }
  }

  /**
   * <p>Log an error with info log level.</p>
   */
  public void info(Object message, Throwable t) {
    if (isInfoEnabled()) {
      StringBuffer buffer = new StringBuffer();
      buffer.append(message);
      buffer.append(writer.getLineSeparator());
      buffer.append(buildStacktrace(t));
      writer.write(this.logger, LogLevel.INFO, buffer.toString());
    }
  }

  /**
   * <p>Log a message with warn log level.</p>
   */
  public void warn(Object message) {
    if (isWarnEnabled()) {
      writer.write(this.logger, LogLevel.WARN, message.toString());
    }
  }

  /**
   * <p>Log an error with warn log level.</p>
   */
  public void warn(Object message, Throwable t) {
    if (isWarnEnabled()) {
      StringBuffer buffer = new StringBuffer();
      buffer.append(message);
      buffer.append(writer.getLineSeparator());
      buffer.append(buildStacktrace(t));
      writer.write(this.logger, LogLevel.WARN, buffer.toString());
    }
  }

  /**
   * <p>Log a message with error log level.</p>
   */
  public void error(Object message) {
    if (isErrorEnabled()) {
      writer.write(this.logger, LogLevel.ERROR, message.toString());
    }
  }

  /**
   * <p>Log an error with error log level.</p>
   */
  public void error(Object message, Throwable t) {
    if (isErrorEnabled()) {
      StringBuffer buffer = new StringBuffer();
      buffer.append(message);
      buffer.append(writer.getLineSeparator());
      buffer.append(buildStacktrace(t));
      writer.write(this.logger, LogLevel.ERROR, buffer.toString());
    }
  }

  /**
   * <p>Log a message with fatal log level.</p>
   */
  public void fatal(Object message) {
    if (isFatalEnabled()) {
      writer.write(this.logger, LogLevel.FATAL, message.toString());
    }
  }

  /**
   * <p>Log an error with fatal log level.</p>
   */
  public void fatal(Object message, Throwable t) {
    if (isFatalEnabled()) {
      StringBuffer buffer = new StringBuffer();
      buffer.append(message);
      buffer.append(writer.getLineSeparator());
      buffer.append(buildStacktrace(t));
      writer.write(this.logger, LogLevel.FATAL, buffer.toString());
    }
  }

  /**
   * 
   */
  public static String buildStacktrace(Throwable t) {
    if (t != null) {
      StringWriter sw = new StringWriter();
      PrintWriter pw = new PrintWriter(sw);
      t.printStackTrace(pw);
      pw.close();
      return sw.getBuffer().toString();
    } else {
      return null;
    }
  }

  /**
   * Nimmt einen Text mit Platzhaltern fuer Parameter in Form von %Nummer und
   * und substituiert die Platzhalter mit dem entsprechenden Parameter aus der
   * uebergebenen Liste (ab 1 durchnummeriert).
   */
  public static String replaceParameter(String message, Object[] parameters) {

    String result = null;

    if (parameters == null || parameters.length == 0) {

      result = message.trim();

    } else {

      int textLaenge = message.length();
      int positionInText = 0;
      StringBuffer buffer = new StringBuffer();
      // substituiert Parameter in Musterstring
      while (positionInText < textLaenge) {

        char nextChar = message.charAt(positionInText++);
         
        if (nextChar == '%' && positionInText < textLaenge) {
          // substituiert einen Parameter
          char parameter = message.charAt(positionInText++);
          if (parameter == '%') {
            buffer.append('%');
          } else {
              int paramNummer = Integer.parseInt(message.substring(positionInText - 1, positionInText));
              if ((parameters.length >= paramNummer)) {
                Object o = parameters[paramNummer - 1];
                if (o != null) {
                  buffer.append(o.toString());
                }
              }
              else {                
                buffer.append(" ? ");
              }
            }
        } else {
          buffer.append(nextChar);
        }
      }

      result = buffer.toString().trim();

    }

    return result;

  }
  
  /**
   * @return LogLevel
   */
  public LogLevel getLevel() {
    return this.level;
  }

  /**
   * @return Class
   */
  public Class getLogger() {
    return this.logger;
  }

  /**
   * @return LogWriter
   */
  public LogWriter getWriter() {
    return this.writer;
  }

  /**
   * Sets the level.
   * @param level The level to set
   */
  protected void setLevel(LogLevel level) {
    this.level = level;
  }

  /**
   * Sets the logger.
   * @param logger The logger to set
   */
	protected void setLogger(Class logger) {
    this.logger = logger;
  }

  /**
   * Sets the writer.
   * @param writer The writer to set
   */
	protected void setWriter(LogWriter writer) {
    this.writer = writer;
  }

}
