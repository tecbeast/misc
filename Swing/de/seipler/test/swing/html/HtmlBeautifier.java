package de.seipler.test.swing.html;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URL;
import java.util.Enumeration;

import javax.swing.text.AttributeSet;
import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLEditorKit;

public class HtmlBeautifier extends HTMLEditorKit.ParserCallback {

  private Writer out;
  private URL base;
  private String lineSeparator;
  private int indentSteps = 0;
  private boolean preformatted = false;
  
  public HtmlBeautifier(Writer out, URL base) {
    
    this.out = out;
    this.base = base;
    this.lineSeparator = System.getProperty("line.separator");
    
  }

  public void handleStartTag(HTML.Tag tag, MutableAttributeSet attributes, int position) {
    
    try {
      if (tag.isPreformatted()) { preformatted = true; }
      indent();
      out.write("<" + tag);
      this.writeAttributes(attributes);
      /*
      // for the <APPLET> tag we may have to add a codebase attribute
      if (tag == HTML.Tag.APPLET && attributes.getAttribute(HTML.Attribute.CODEBASE) == null) {
        String codebase = base.toString();
        if (codebase.endsWith(".htm") || codebase.endsWith(".html")) {
          codebase = codebase.substring(0, codebase.lastIndexOf('/'));   
        }
        out.write(" codebase=\"" + codebase + "\""); 
      }
      */
      out.write(">");
      out.write(lineSeparator);
      out.flush();
      indentSteps++;
    } catch (IOException e) {
      System.err.println(e);
      e.printStackTrace();
    }
    
  }
  
  public void handleEndTag(HTML.Tag tag, int position) {
    
    try {
      if (tag.isPreformatted()) { preformatted = false; }
      if (indentSteps > 0) { indentSteps--; }
      indent();
      out.write("</" + tag + ">");
      out.write(lineSeparator);
      out.flush();
    } catch (IOException e) {
      System.err.println(e);
    }
    
  }
  
  public void handleComment(char[] text, int position) {
    
    try {
      indent();
      out.write("<!--");
      out.write(text);
      out.write("-->");
      out.write(lineSeparator);
      out.flush();
    } catch (IOException e) {
      System.err.println(e);
    }
    
  }
  
  public void handleText(char[] text, int position) {
    
    try { 
      indent();
      out.write(text);
      out.write(lineSeparator);
      out.flush();
    } catch (IOException e) {
      System.err.println(e);
      e.printStackTrace();
    }
    
  }
  
  public void handleSimpleTag(HTML.Tag tag, MutableAttributeSet attributes, int position) {
    
    try {
      indent();
      out.write("<" + tag);
      this.writeAttributes(attributes);
      out.write(">");
      out.write(lineSeparator);
      out.flush();
    } catch (IOException e) {
      System.err.println(e);
      e.printStackTrace();
    }
    
  }

  private void writeAttributes(AttributeSet attributes) throws IOException {
    
    Enumeration e = attributes.getAttributeNames();
    while (e.hasMoreElements()) {
      Object name = e.nextElement();
      if (!name.equals("_implied_")) {
        Object value = attributes.getAttribute(name);
        try {
          out.write(" " + name + "=\"" + value + "\"");
        } catch (IOException ioe) {
          System.err.println(ioe);
          ioe.printStackTrace();
        }
      }
    }
    
  }
   
  private void indent() throws IOException {
    
    if (!preformatted) {
      for (int i = 0; i < indentSteps; i++) { out.write("  ");  }
    }
    
  }
  
  public static void main(String[] args) { 
    
    for (int i = 0; i < args.length; i++) { 
      
      ParserGetter kit = new ParserGetter();
      HTMLEditorKit.Parser parser = kit.getParser();
    
      try {
        URL u = new URL(args[i]);
        InputStream in = u.openStream();
        InputStreamReader r = new InputStreamReader(in);
        Writer out = new OutputStreamWriter(System.out);
        HTMLEditorKit.ParserCallback callback = new HtmlBeautifier(out, u);
        parser.parse(r, callback, false);
      } catch (IOException e) {
        System.err.println(e); 
        e.printStackTrace();
      }
      
    } 
    
  }
  
}