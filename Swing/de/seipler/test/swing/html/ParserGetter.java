package de.seipler.test.swing.html;
import javax.swing.text.html.HTMLEditorKit;

public class ParserGetter extends HTMLEditorKit {

  // purely to make this method public
  public HTMLEditorKit.Parser getParser() { return super.getParser(); }
  
}