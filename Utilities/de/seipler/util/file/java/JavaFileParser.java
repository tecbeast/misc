package de.seipler.util.file.java;

import java.io.*;
import java.util.*;

/**
 * Parser that builds a structural representation of a Java sourcefile.
 * 
 * @see JavaFileStructure
 *  
 * @author Georg Seipler
 */
public class JavaFileParser {
  
  protected TerminalFactory terminalFactory = null;  

  /**
   * A terminal for the grammer of this parser.
   * Terminals consist of an integer constant and the string
   * representation (token) found be the lexer.
   */
  protected final class Terminal {

    public final static int ABSTRACT   = 1;
    public final static int CLASS      = 2;
    public final static int EXTENDS    = 3;
    public final static int FINAL      = 4;
    public final static int IMPLEMENTS = 5;
    public final static int IMPORT     = 6;
    public final static int INTERFACE  = 7;
    public final static int PACKAGE    = 8;
    public final static int PUBLIC     = 9;
    public final static int PRIVATE    = 10;
    public final static int PROTECTED  = 11;
    public final static int STATIC     = 12;
    public final static int THROWS     = 13;
    
    private int identifier = 0;
    private String keyword = null;
  
    protected Terminal(int identifier, String keyword) {
      this.identifier = identifier;
      this.keyword = keyword;
    }
    
    public int getIdentifier() {
      return this.identifier;
    }

    public String getKeyword() {
      return this.keyword;
    }

  }

  /**
   * Generates terminals according to the tokenstring found by the lexer.
   */
  protected final class TerminalFactory {
    
    private Map terminalPerKeyword = null;
    
    public TerminalFactory() {
      terminalPerKeyword = new HashMap();
      terminalPerKeyword.put("abstract",   new Terminal(Terminal.ABSTRACT, "abstract")); 
      terminalPerKeyword.put("class",      new Terminal(Terminal.CLASS, "class"));
      terminalPerKeyword.put("extends",    new Terminal(Terminal.EXTENDS, "extends"));
      terminalPerKeyword.put("final",      new Terminal(Terminal.FINAL, "final")); 
      terminalPerKeyword.put("implements", new Terminal(Terminal.IMPLEMENTS, "implements"));
      terminalPerKeyword.put("import",     new Terminal(Terminal.IMPORT, "import"));
      terminalPerKeyword.put("interface",  new Terminal(Terminal.INTERFACE, "interface"));
      terminalPerKeyword.put("package",    new Terminal(Terminal.PACKAGE, "package"));
      terminalPerKeyword.put("public",     new Terminal(Terminal.PUBLIC, "public"));
      terminalPerKeyword.put("private",    new Terminal(Terminal.PRIVATE, "private")); 
      terminalPerKeyword.put("protected",  new Terminal(Terminal.PROTECTED, "protected"));
      terminalPerKeyword.put("static",     new Terminal(Terminal.STATIC, "static")); 
      terminalPerKeyword.put("throws",     new Terminal(Terminal.THROWS, "throws"));
    }
    
    public Terminal getTerminal(String keyword) {
      return (Terminal) terminalPerKeyword.get(keyword);
    }
    
  }

  /**
   * All States known by the parser.
   */
  protected class State {

    public final static int SCAN                     = 1;
    public final static int READ_CLASS               = 2;
    public final static int READ_EXTENDS             = 3;
    public final static int READ_IMPLEMENTS          = 4;
    public final static int READ_IMPORT              = 5;
    public final static int READ_INTERFACE           = 6;
    public final static int READ_PACKAGE             = 7; 
    public final static int READ_PARAMETER           = 8;
    public final static int READ_THROWS              = 9;
    public final static int SKIP_TO_END_OF_STATEMENT = 10;
    public final static int SKIP_TO_END_OF_BLOCK     = 11;
    
  }
  
  /**
   * Default Construcutor.
   */
  public JavaFileParser() {
    this.terminalFactory = new TerminalFactory();
  }

  /**
   * Defines the Synty for the tokenizer.
   * Numbers are not treated separately, comments are ignored.
   */
  protected void defineSyntax(StreamTokenizer tokenizer) {
    tokenizer.resetSyntax();
    tokenizer.slashStarComments(true);
    tokenizer.slashSlashComments(true);
    tokenizer.eolIsSignificant(false);
    tokenizer.lowerCaseMode(false);
    tokenizer.wordChars('a', 'z');
    tokenizer.wordChars('A', 'Z');
    tokenizer.wordChars('*', '*');
    tokenizer.wordChars('.', '.');
    tokenizer.wordChars('-', '-');
    tokenizer.wordChars('_', '_');
    tokenizer.wordChars('[', '[');
    tokenizer.wordChars(']', ']');
    tokenizer.wordChars('0', '9');
    tokenizer.wordChars(128 + 32, 255);
    tokenizer.ordinaryChar('{');
    tokenizer.ordinaryChar('}');
    tokenizer.ordinaryChar('(');
    tokenizer.ordinaryChar(')');
    tokenizer.ordinaryChar(',');
    tokenizer.ordinaryChar(';');
    tokenizer.ordinaryChar('=');
    tokenizer.whitespaceChars(0, ' ');
    tokenizer.quoteChar('"');
    tokenizer.quoteChar('\'');
    tokenizer.commentChar('/');
  }

  /**
   * Parses given Java sourcefile and returns associated fileStructure.
   * Uses Terminalfactory to transform found words (tokens) into known
   * keywords and acts on those by setting the appropriate parser state.
   */
  public JavaFile parse(File file) throws IOException, FileNotFoundException {
    
    JavaFile fileStructure = new JavaFile(file);
    Reader in = null;

    try {

      in = new BufferedReader(new FileReader(file));
      StreamTokenizer tokenizer = new StreamTokenizer(in);
      defineSyntax(tokenizer);

      // cache for found class or method structures
      // holds those structures until their endline is known
      // and they can be committed to the fileStructure
      Map structureCache = new HashMap();

      // current non-terminal
      String word = null;
      // last non-terminal
      String lastWord = null;
      // cache for a method name candidate
      String possibleName = null;
      // cache for a method return value candidate 
      String possibleValue = null;

      // cache for a parameter list candidate      
      List parameterList = null;
      // cache for a parameter name list candidate      
      List parameterNameList = null;
      // cache for the currently found modifiers
      JavaModifier modifier = null;

      // current tokenizer token            
      int token = 0;
      // last tokenizer token
      int lastToken = 0;
      // current block depth (outer class is depth 0)
      // equals to the number of open curly brackets
      int blockDepth = 0;
      // last block depth, used when skipping a whole block
      int lastBlockDepth = 0;
      // current number of open round brackets
      int bracketDepth = 0;
      // candidate line for the start of a structure
      int startLineNumber = 0;
      // current parser state
      int state = State.SCAN;

      while ((token = tokenizer.nextToken()) != StreamTokenizer.TT_EOF) {

        // token is a word        
        if ((token == StreamTokenizer.TT_WORD) && (state != State.SKIP_TO_END_OF_BLOCK) && (state != State.SKIP_TO_END_OF_STATEMENT)) {
          Terminal terminal = terminalFactory.getTerminal(tokenizer.sval);

          // token is a terminal
          if (terminal != null) {
            switch (terminal.getIdentifier()) {
              case Terminal.PACKAGE:
                state = State.READ_PACKAGE;
                break;                  
              case Terminal.IMPORT:
                state = State.READ_IMPORT;
                if (fileStructure.getImportsStartLine() == 0) {
                  fileStructure.setImportsStartLine(tokenizer.lineno());
                }
                break;
              case Terminal.CLASS:
                if (startLineNumber == 0) { startLineNumber = tokenizer.lineno(); }
                state = State.READ_CLASS;
                break;
              case Terminal.INTERFACE:
                if (startLineNumber == 0) { startLineNumber = tokenizer.lineno(); }
                state = State.READ_INTERFACE;
                break;
              case Terminal.PUBLIC:
                if (modifier == null) { modifier = new JavaModifier(); }
                if (startLineNumber == 0) { startLineNumber = tokenizer.lineno(); }
                modifier.setVisibility(JavaModifier.PUBLIC);
                break;
              case Terminal.PROTECTED:
                if (modifier == null) { modifier = new JavaModifier(); }
                if (startLineNumber == 0) { startLineNumber = tokenizer.lineno(); }
                modifier.setVisibility(JavaModifier.PROTECTED);
                break;
              case Terminal.PRIVATE:
                if (modifier == null) { modifier = new JavaModifier(); }
                if (startLineNumber == 0) { startLineNumber = tokenizer.lineno(); }
                modifier.setVisibility(JavaModifier.PRIVATE);
                break;
              case Terminal.FINAL:
                if (modifier == null) { modifier = new JavaModifier(); }
                if (startLineNumber == 0) { startLineNumber = tokenizer.lineno(); }
                modifier.setFinal(true);
                break;
              case Terminal.STATIC:
                if (modifier == null) { modifier = new JavaModifier(); }
                if (startLineNumber == 0) { startLineNumber = tokenizer.lineno(); }
                modifier.setStatic(true);
                break;
              case Terminal.ABSTRACT:
                if (modifier == null) { modifier = new JavaModifier(); }
                if (startLineNumber == 0) { startLineNumber = tokenizer.lineno(); }
                modifier.setAbstract(true);
                break;
              case Terminal.EXTENDS:
                state = State.READ_EXTENDS;
                break;
              case Terminal.IMPLEMENTS:
                state = State.READ_IMPLEMENTS;
                break;
              case Terminal.THROWS:
                state = State.READ_THROWS;
                break;
            }
            
          // token is a non-terminal
          } else {
            if ((state == State.SCAN) || (state == State.READ_PARAMETER)) {
              lastWord = word;
              word = tokenizer.sval;
              if (startLineNumber == 0) { startLineNumber = tokenizer.lineno(); }
            } else if ((state == State.READ_CLASS) || (state == State.READ_INTERFACE)) {
              if (modifier == null) { modifier = new JavaModifier(); }
              JavaClass classStructure = new JavaClass(file, tokenizer.sval, modifier, (state == State.READ_INTERFACE));
              classStructure.setStartLine(startLineNumber);
              classStructure.setBlock(blockDepth);
              structureCache.put(new Integer(blockDepth), classStructure);
            } else if (state == State.READ_EXTENDS) {
              JavaClass classStructure = (JavaClass) structureCache.get(new Integer(blockDepth));
              if (classStructure != null) {
                classStructure.setSuperClass(tokenizer.sval);
              } else {
                throw new IllegalStateException("extends clause outside of class or interface context in line " + tokenizer.lineno());
              }
            } else if (state == State.READ_IMPLEMENTS) {
              JavaClass classStructure = (JavaClass) structureCache.get(new Integer(blockDepth));
              if (classStructure != null) {
                classStructure.addInterface(tokenizer.sval);
              } else {
                throw new IllegalStateException("implements clause outside of class context in line " + tokenizer.lineno());
              }
            } else if (state == State.READ_IMPORT) {
              fileStructure.addImport(tokenizer.sval);
              fileStructure.setImportsEndLine(tokenizer.lineno());
            } else if (state == State.READ_PACKAGE) {
              fileStructure.setPackage(tokenizer.sval);
            } else if (state == State.READ_THROWS) {
              JavaMethod methodStructure = (JavaMethod) structureCache.get(new Integer(blockDepth));
              if (methodStructure == null) {
                methodStructure = new JavaMethod();
                structureCache.put(new Integer(blockDepth), methodStructure);
              }            
              methodStructure.addThrowable(tokenizer.sval);
            }
          }

        // token is a single character
        } else {
          token = tokenizer.ttype;
          if (token == '}') {
            if (blockDepth > 0) { blockDepth--; }
            JavaCode structure = (JavaCode) structureCache.get(new Integer(blockDepth));
            if (structure != null) {
              structure.setEndLine(tokenizer.lineno());
              if (structure instanceof JavaClass) {
                fileStructure.addClass((JavaClass) structure);
              } else {
                fileStructure.addMethod((JavaMethod) structure);
              }
              structureCache.remove(new Integer(blockDepth));
            }
            if ((state == State.SKIP_TO_END_OF_BLOCK) && (blockDepth <= lastBlockDepth)) {
              state = State.SCAN;
            }
          } else if (token == '{') {
            if ((state != State.SKIP_TO_END_OF_BLOCK) && (state != State.SKIP_TO_END_OF_STATEMENT)) {
              if (parameterList != null) {
                JavaMethod methodStructure = (JavaMethod) structureCache.get(new Integer(blockDepth));
                if (methodStructure == null) {
                  methodStructure = new JavaMethod();
                  structureCache.put(new Integer(blockDepth), methodStructure);
                }
                methodStructure.setName(possibleName);
                methodStructure.setReturnValue(possibleValue);
                methodStructure.setModifier(modifier);
                methodStructure.addParameters(parameterList);
                methodStructure.addParameterNames(parameterNameList);
                methodStructure.setStartLine(startLineNumber);
                methodStructure.setCodeStartLine(tokenizer.lineno());
                methodStructure.setSource(file);
                parameterList = null;
                parameterNameList = null;
              }
              if (blockDepth >= 1) {
                lastBlockDepth = blockDepth;
                state = State.SKIP_TO_END_OF_BLOCK;
              } else {
                state = State.SCAN;
              }
            }
            word = null;
            modifier = null;
            startLineNumber = 0;
            blockDepth++;
          } else if (state != State.SKIP_TO_END_OF_BLOCK) {
            if (token == '=') {
              state = State.SKIP_TO_END_OF_STATEMENT;
            } else if (token == ';') {
              if ((parameterList != null) && (state != State.SKIP_TO_END_OF_STATEMENT)) {
                JavaMethod methodStructure = (JavaMethod) structureCache.remove(new Integer(blockDepth));
                if (methodStructure == null) { methodStructure = new JavaMethod(); }
                methodStructure.setName(possibleName);
                methodStructure.setReturnValue(possibleValue);
                methodStructure.setModifier(modifier);
                methodStructure.addParameters(parameterList);
                methodStructure.addParameterNames(parameterNameList);
                methodStructure.setStartLine(startLineNumber);
                methodStructure.setCodeStartLine(tokenizer.lineno());
                methodStructure.setEndLine(tokenizer.lineno());
                methodStructure.setSource(file);
                fileStructure.addMethod(methodStructure);
                parameterList = null;
                parameterNameList = null;
              } else if ((state == State.SCAN) || (state == State.SKIP_TO_END_OF_STATEMENT)){
                if ((word != null) && (lastWord != null) && (startLineNumber > 0)) {
                  JavaAttribute attributeStructure = new JavaAttribute(file, word, lastWord, modifier);
                  attributeStructure.setStartLine(startLineNumber);
                  attributeStructure.setBlock(blockDepth);
                  attributeStructure.setEndLine(tokenizer.lineno());
                  fileStructure.addAttribute(attributeStructure);
                }
              }
              state = State.SCAN;
              word = null;
              modifier = null;
              startLineNumber = 0;
            } else if (state != State.SKIP_TO_END_OF_STATEMENT) {            
              if (token == '(') {
                bracketDepth++;
                if (state != State.READ_PARAMETER) {
                  state = State.READ_PARAMETER;
                  parameterList = new ArrayList();
                  parameterNameList = new ArrayList();
                  possibleValue = lastWord;
                  possibleName = word;
                }
              } else if (token == ')') {
                if (bracketDepth > 0) {
                  bracketDepth--;
                } else {
                  throw new IllegalStateException("unmatched closing bracket ')' in line " + tokenizer.lineno());
                }
                if (state == State.READ_PARAMETER) {
                  if (lastToken != '(') { parameterList.add(lastWord); parameterNameList.add(word); }
                  if (bracketDepth == 0) { state = State.SCAN; }
                }
              } else if (token == ',') {
                if (state == State.READ_PARAMETER) { parameterList.add(lastWord); parameterNameList.add(word); }
              }
            }
          }
        }
        
        lastToken = token;
        
      }

      fileStructure.setNrOfLines(tokenizer.lineno());
      return fileStructure;
      
    // always close your streams
    } finally {
      try { in.close(); } catch (Exception ignored) { }
    }
    
  }

}
