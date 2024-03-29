package de.seipler.game.starrealms;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StreamTokenizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import de.seipler.util.log.Log;
import de.seipler.util.log.LogFactory;

/**
 * Recursive-Descent-Parser for Ashes Commands. Ashes defines a complete
 * Type 2 language for player use. The full grammar is given here in BNF
 * (Backus-Naur-Form):
 * <pre>
 *        parse -> BEGIN game user statements END
 *         game -> GAME NUMBER TURN NUMBER
 *         user -> USER WORD PASSWORD WORD
 *   statements -> statement statements | onBlock statements | (0)
 *    statement -> onBlockStmt ON planet | declare | message | playername
 *      onBlock -> ON planet DO onBlockStmts DONE
 * onBlockStmts -> onBlockStmt onBlockStmts | (0)
 *  onBlockStmt -> build | homeplanet | planetname | research | send | spy
 *        build -> BUILD NUMBER buildType
 *    buildType -> PDU | FP | OP | RP | FY | TY | FI | TR
 *      declare -> DECLARE declareType WITH player
 *  declareType -> WAR | PEACE | NEUTRAL
 *   homeplanet -> HOMEPLANET
 *      message -> MESSAGE TO player WORD
 *   planetname -> PLANETNAME WORD
 *   playername -> PLAYERNAME WORD
 *     research -> RESEARCH researchType
 * researchType -> PR | FM | TM
 *         send -> SEND NUMBER sendType TO planet
 *     sendType -> FI | TR | C0 | C1 | C2 | C3 | C4 | C5 | C6 | C7 | C7 | C8 | C9
 *          spy -> SPY
 *       player -> WORD | NUMBER
 *       planet -> WORD | NUMBER
 * </pre>
 * <b>Note:</b>
 * non-terminal symbols are in small letters and terminals in big,
 * (0) is for an empty set (Epsilon) and (+) indicates 1..N occurences.
 */
public class Parser {

  /** An error occured during parsing. */
  public final static int ERROR  = -1;
  /** End of file. */
  public final static int EOF    =  0;
  /** End of line. */
  public final static int EOL    =  1;
  /** An integer value. */
  public final static int NUMBER =  2;
  /** A String value. */
  public final static int WORD   =  3;

  public final static int BEGIN      = 100;
  public final static int GAME       = 101;
  public final static int TURN       = 102;
  public final static int END        = 103;
  public final static int USER       = 104;
  public final static int PASSWORD   = 105;
  public final static int ON         = 106;
  public final static int DO         = 107;
  public final static int DONE       = 108;
  public final static int BUILD      = 109;
  public final static int PLANETNAME = 110;
  public final static int PLAYERNAME = 111;
  public final static int SEND       = 112;
  public final static int RESEARCH   = 113;
  public final static int MESSAGE    = 114;
  public final static int TO         = 115;
  public final static int DECLARE    = 116;
  public final static int WITH       = 117;
  public final static int HOMEPLANET = 118;
  public final static int SPY        = 119;

  public final static int PDU = 200;
  public final static int FP  = 201;
  public final static int OP  = 202;
  public final static int RP  = 203;
  public final static int FY  = 204;
  public final static int TY  = 205;
  public final static int FI  = 206;
  public final static int TR  = 207;
  public final static int PR  = 208;
  public final static int FM  = 209;
  public final static int TM  = 210;
  public final static int C0  = 211;
  public final static int C1  = 212;
  public final static int C2  = 213;
  public final static int C3  = 214;
  public final static int C4  = 215;
  public final static int C5  = 216;
  public final static int C6  = 217;
  public final static int C7  = 218;
  public final static int C8  = 219;
  public final static int C9  = 220;

  public final static int WAR     = 300;
  public final static int PEACE   = 302;
  public final static int NEUTRAL = 303;


  public final static String UNDEFINED = "undefined";

  private final static HashMap textToToken = new HashMap();
  private final static HashMap tokenToText = new HashMap();

  static {

		textToToken.put("ERROR",      new Integer(ERROR));
		textToToken.put("EOF",        new Integer(EOF));
		textToToken.put("EOL",        new Integer(EOL));
		textToToken.put("NUMBER",     new Integer(NUMBER));
		textToToken.put("WORD",       new Integer(WORD));

    textToToken.put("begin",      new Integer(BEGIN));
    textToToken.put("game",       new Integer(GAME));
		textToToken.put("turn",       new Integer(TURN));
		textToToken.put("end",        new Integer(END));
		textToToken.put("user",       new Integer(USER));
		textToToken.put("password",   new Integer(PASSWORD));
		textToToken.put("on",         new Integer(ON));
		textToToken.put("do",         new Integer(DO));
		textToToken.put("done",       new Integer(DONE));
		textToToken.put("build",      new Integer(BUILD));
		textToToken.put("planetname", new Integer(PLANETNAME));
		textToToken.put("playername", new Integer(PLAYERNAME));
		textToToken.put("send",       new Integer(SEND));
		textToToken.put("research",   new Integer(RESEARCH));
		textToToken.put("message",    new Integer(MESSAGE));
		textToToken.put("to",         new Integer(TO));
		textToToken.put("declare",    new Integer(DECLARE));
		textToToken.put("with",       new Integer(WITH));
		textToToken.put("homeplanet", new Integer(HOMEPLANET));
		textToToken.put("spy",        new Integer(SPY));

		textToToken.put("pdu",        new Integer(PDU));
		textToToken.put("fp",         new Integer(FP));
		textToToken.put("op",         new Integer(OP));
		textToToken.put("rp",         new Integer(RP));
		textToToken.put("fy",         new Integer(FY));
		textToToken.put("ty",         new Integer(TY));
		textToToken.put("fi",         new Integer(FI));
		textToToken.put("tr",         new Integer(TR));
		textToToken.put("pr",         new Integer(PR));
		textToToken.put("fm",         new Integer(FM));
		textToToken.put("tm",         new Integer(TM));
		textToToken.put("c0",         new Integer(C0));
		textToToken.put("c1",         new Integer(C1));
		textToToken.put("c2",         new Integer(C2));
		textToToken.put("c3",         new Integer(C3));
		textToToken.put("c4",         new Integer(C4));
		textToToken.put("c5",         new Integer(C5));
		textToToken.put("c6",         new Integer(C6));
		textToToken.put("c7",         new Integer(C7));
		textToToken.put("c8",         new Integer(C8));
		textToToken.put("c9",         new Integer(C9));

    textToToken.put("war",        new Integer(WAR));
    textToToken.put("peace",      new Integer(PEACE));
    textToToken.put("neutral",    new Integer(NEUTRAL));

		/* build the inverted Hashtable tokenToText */
		Iterator iterator = textToToken.keySet().iterator();
		while (iterator.hasNext()) {
		  String key = (String)iterator.next();
		  tokenToText.put(textToToken.get(key), key);
		}
    
  }

  private StreamTokenizer tokenizer;
  private String word;
  private StringBuffer errorBuffer;
  private int number, lookahead;
  private boolean blocked, errorOccured;
  private Log log;

  // name of this module
  private List symbolList = null;

  /**
   *
   */
  public Parser(BufferedReader br) throws IOException {

    log = LogFactory.getLog(getClass());
  	symbolList = new ArrayList();
  
  	// read until first empty line
  	String line = null;
  	while ((line = br.readLine()) != null) {
  	  if (line.length() == 0) { break; }
  	}
  	if (line == null) {
  	  throw new IOException("! error: input not in mail format (no empty line after header)");
  	}
  
  	// initialize tokenizer and get first lookahead token
  	tokenizer = new StreamTokenizer(br);
  	initTokenizer();
  
  	// set defaults
  	number = -1;
  	word = "";
  	blocked = false;
  	errorOccured = false;
  	errorBuffer = new StringBuffer();
    
  }

  /**
   *
   */
  private void addError(String msg) {
  	if (errorOccured) {	errorBuffer.append('\n'); }
  	errorBuffer.append(msg);
  	errorOccured = true;
  }

  /**
   *
   */
  private Command build() throws ParserException {
  	log.trace("build()");

  	try {
  		match(BUILD);
  		Command cmd = new Command(BUILD);
  		cmd.setNumber(parseNumber());
  		cmd.setType(buildType());
  		return cmd;
  	} catch (ParserException pe) {
  		throw new ParserException("BUILD: " + pe.getMessage());
  	}
  }

  /**
   *
   */
  private int buildType() throws ParserException {
  	log.trace("buildType()");

  	switch (lookahead) {
  		case PDU :
  			match(PDU);
  			return PDU;
  		case FP :
  			match(FP);
  			return FP;
  		case OP :
  			match(OP);
  			return OP;
  		case RP :
  			match(RP);
  			return RP;
  		case FY :
  			match(FY);
  			return FY;
  		case TY :
  			match(TY);
  			return TY;
  		case FI :
  			match(FI);
  			return FI;
  		case TR :
  			match(TR);
  			return TR;
  		default :
  			throw new ParserException("production type expected");
  	}
  }

  /**
   *
   */
  private Command declare() throws ParserException {
  	log.trace("declare()");

  	try {
  		match(DECLARE);
  		Command cmd = new Command(DECLARE);
  		cmd.setType(declareType());
  		match(WITH);
  		cmd.setDestination(player());
  		return cmd;
  	} catch (ParserException pe) {
  		throw new ParserException("DECLARE: " + pe.getMessage());
  	}
  }

  /**
   *
   */
  private int declareType() throws ParserException {
  	log.trace("declareType()");

  	switch (lookahead) {
  		case WAR :
  			match(WAR);
  			return WAR;
  		case PEACE :
  			match(PEACE);
  			return PEACE;
  		case NEUTRAL :
  			match(NEUTRAL);
  			return NEUTRAL;
  		default :
  			throw new ParserException("political status expected");
  	}
  }

  /**
   *
   */
  private int error(String msg, int[] begin, int[] end) {
    log.trace("error()");

  	int startLine = tokenizer.lineno();
  	int endLine = startLine;
  	int result = -1;
  	addError("! error line " + tokenizer.lineno() + ": " + msg);
  
  	do {
  		lookahead = scanner();
  		if (inArray(lookahead, begin)) {
  			result = 1;
  			break;
  		} else
  			if (inArray(lookahead, end)) {
  				result = 0;
  				break;
  			}
  		endLine = tokenizer.lineno();
  	} while (lookahead != EOF);
  
  	if (endLine > startLine) {
  		if (endLine > startLine + 1) {
  			addError("skipping lines " + (startLine + 1) + " - " + endLine + " ...");
  		} else {
  			addError("skipping line " + endLine + " ...");
  		}
  	}
  	
  	return result;
  }

  /**
   *
   */
  private CommandList game() throws ParserException {
    log.trace("game()");

  	CommandList cmdList = new CommandList();
  	try {
  		Command gameCmd = new Command(match(GAME));
  		gameCmd.setNumber(parseNumber());
  		cmdList.add(gameCmd);
  		Command turnCmd = new Command(match(TURN));
  		turnCmd.setNumber(parseNumber());
  		cmdList.add(turnCmd);
  	} catch (ParserException pe) {
  		throw new ParserException("GAME: " + pe.getMessage());
  	}
  	return cmdList;
  }

  /**
   *
   */
  public String getErrors() {
  	return errorBuffer.toString();
  }

  /**
   *
   */ 
  public List getSymbols() {
  	return symbolList;
  }
  
  /**
   * Transforms text to parser token.
   */
  public static String getText(int token) {
  	Integer lookup = new Integer(token);
  	if (tokenToText.containsKey(lookup)) {
  	  return (String)tokenToText.get(lookup);
  	} else {
  	  return UNDEFINED;
  	}
  }
  
  /**
   * Transforms parser token to text.
   */
  public static int getToken(String text) {
  	String lookup = text.toLowerCase();
  	if (textToToken.containsKey(lookup)) {
  	  return ((Integer)textToToken.get(lookup)).intValue();
  	} else {
  	  return ERROR;
  	}
  }

  /**
   *
   */
  private Command homeplanet() throws ParserException {
  	log.trace("homeplanet()");

  	match(HOMEPLANET);
  	return new Command(HOMEPLANET);
  }

  /**
   *
   */
  private boolean inArray(int test, int[] array) {
  	for (int i = 0; i < array.length; i++) {
  		if (test == array[i]) {	return true; }
  	}
  	return false;
  }

  /**
   *
   */
  private void initTokenizer() {
    tokenizer.resetSyntax();
  	tokenizer.wordChars((int) 'a', (int) 'z');
  	tokenizer.wordChars((int) 'A', (int) 'Z');
  	tokenizer.whitespaceChars((int) '\u0000', (int) '\u0020');
  	tokenizer.eolIsSignificant(false);
  	tokenizer.lowerCaseMode(false);
  	tokenizer.parseNumbers();
  	tokenizer.quoteChar('"');
  	tokenizer.slashSlashComments(false);
  	tokenizer.slashStarComments(false);
  	tokenizer.commentChar('#');
  	
  	// get first lookahead token
  	lookahead = scanner();
  }

  /**
   * Test for Parser. Takes name of a textfile as first commandline parameter
   * and parses its contents. Prints String representation of the resulting CommandSet.
   */
  public static void main(String args[]) {
  	BufferedReader br = null;
  	Parser testParser = null;
  	CommandList cmdList = null;
  	try {
  	  br = new BufferedReader(new FileReader(args[0]));
  	  testParser = new Parser(br);
  	  cmdList = testParser.parse();
  	  if (cmdList == null) {
  			System.out.println("[ Parser reports errors ]");
  			System.out.println(testParser.getErrors());
  	  } else {
  			System.out.println(cmdList);
  	  }
  	} catch (Exception e) {
  	  e.printStackTrace();
  	} finally {
  	  try { br.close(); } catch (IOException ie) { }
  	  StarRealms.getInstance().stop();
  	}
  }

  /**
   *
   */ 
  private int match(int token) throws ParserException {
    log.trace("match(" + getText(token) + ")");

  	if (lookahead == token) {
  		lookahead = scanner();
  		return token;
  	} else {
  		throw new ParserException("token " + getText(token).toUpperCase() + " expected");
  	}
  }

  /**
   *
   */
  private Command message() throws ParserException {
    log.trace("message()");
  	try {
  		match(MESSAGE);
  		match(TO);
  		Command cmd = new Command(MESSAGE);
  		cmd.setDestination(player());
      cmd.setText(parseWord());
  		return cmd;
  	} catch (ParserException pe) {
  		throw new ParserException("MESSAGE: " + pe.getMessage());
  	}
  }

  /**
   *
   */
  private CommandList onBlock() throws ParserException {
    log.trace("onBlock()");

  	try {
  		match(ON);
  		int planetNr = planet();
  		match(DO);
  		CommandList cmdList = onBlockStmts();
  		cmdList.setSource(planetNr);
  		match(DONE);
  		return cmdList;
  	} catch (ParserException pe) {
  		throw new ParserException("ON BLOCK: " + pe.getMessage());
  	}
  }

  /**
   *
   */
  private Command onBlockStmt() throws ParserException {
    log.trace("onBlockStmt()");

  	switch (lookahead) {
  		case BUILD :
  			return build();
  		case HOMEPLANET :
  			return homeplanet();
  		case PLANETNAME :
  			return planetname();
  		case RESEARCH :
  			return research();
  		case SEND :
  			return send();
  		case SPY :
  			return spy();
  		default :
  			throw new ParserException("unknown command");
  	}
  }

  /**
   *
   */
  private CommandList onBlockStmts() {
    log.trace("onBlockStmts()");

  	final int[] first = {BUILD, HOMEPLANET, PLANETNAME, RESEARCH, SEND, SPY};
  	final int[] follow = {DONE};
  	try {
  		switch (lookahead) {
  			case BUILD:
  			case HOMEPLANET:
  			case PLANETNAME:
  			case RESEARCH:
  			case SEND:
  			case SPY:
  				CommandList cmdList = new CommandList();
  				cmdList.add(onBlockStmt());
  				cmdList.add(onBlockStmts());
  				return cmdList;
  			case DONE:
  				return null;
  			default:
  				throw new ParserException("unknown command");
  		}
  	} catch (ParserException pe) {
  		if (error(pe.getMessage(), first, follow) > 0) {
  			return onBlockStmts();
  		}
  		return null;
  	}
  }

  /**
   * Starts a parserun.
   */
  public CommandList parse() {
    log.trace("parse()");

  	CommandList cmdList = new CommandList();
  	try {
      match(BEGIN);
  		cmdList.add(game());
  		cmdList.add(user());
  		cmdList.add(statements());
  		match(END);
      return cmdList;
  	} catch (ParserException pe) {
  		errorOccured = true;
  		addError(pe.getMessage());
      return null;
  	}
  }

  /**
   *
   */
  private int parseNumber() throws ParserException {
    log.trace("parseNumber()");

  	if (lookahead == NUMBER) {
  		int aNumber = number;
  		match(NUMBER);
  		return aNumber;
  	} else {
  		throw new ParserException("number expected");
  	}
  }

  /**
   *
   */
  private String parseWord() throws ParserException {
    log.trace("parseWord()");

  	if (lookahead == WORD) {
  		String aWord = word;
  		match(WORD);
  		return aWord;
  	} else {
  		throw new ParserException("word expected");
  	}
  }

  /**
   *
   */
  private int planet() throws ParserException {
    log.trace("planet()");

  	int planetNr = 0;
  	if (lookahead == NUMBER) {
  		return parseNumber();
  	} else
  		if (lookahead == WORD) {
  			/* look up planetname in planetlist, get int */
  			String planetName = parseWord();
  			if (symbolList.contains(planetName)) {
  				planetNr = symbolList.indexOf(planetName) * -1;
  			} else {
  				symbolList.add(planetName);
  				planetNr = symbolList.size() * -1;
  			}
  		} else {
  			throw new ParserException("name or number expected");
  		}
  	return planetNr;
  }

  /**
   *
   */
  private Command planetname() throws ParserException {
    log.trace("planetname()");

  	try {
  		match(PLANETNAME);
  		Command cmd = new Command(PLANETNAME);
  		cmd.setText(parseWord());
  		return cmd;
  	} catch (ParserException pe) {
  		throw new ParserException("PLANETNAME: " + pe.getMessage());
  	}
  }

  /**
   *
   */
  private int player() throws ParserException {
    log.trace("player()");

  	int playerNr = 0;
  	if (lookahead == NUMBER) {
  		playerNr = parseNumber();
  	} else
  		if (lookahead == WORD) {
  			/* look up playername in playerlist, get int */
  			String playerName = parseWord();
  			if (symbolList.contains(playerName)) {
  				playerNr = symbolList.indexOf(playerName) * -1;
  			} else {
  				symbolList.add(playerName);
  				playerNr = symbolList.size() * -1;
  			}
  		} else {
  			throw new ParserException("name or number expected");
  		}
  	return playerNr;
  }

  /**
   *
   */
  private Command playername() throws ParserException {
    log.trace("playername()");
    
  	try {
  		match(PLAYERNAME);
  		Command cmd = new Command(PLAYERNAME);
  		cmd.setText(parseWord());
  		return cmd;
  	} catch (ParserException pe) {
  		throw new ParserException("PLAYERNAME: " + pe.getMessage());
  	}
  }

  /**
   *
   */
  private Command research() throws ParserException {
    log.trace("research()");
    
  	try {
  		match(RESEARCH);
  		Command cmd = new Command(RESEARCH);
  		cmd.setType(researchType());
  		return cmd;
  	} catch (ParserException pe) {
  		throw new ParserException("RESEARCH: " + pe.getMessage());
  	}
  }

  /**
   *
   */
  private int researchType() throws ParserException {
    log.trace("researchType()");
    
  	switch (lookahead) {
  		case PR:
  			match(PR);
  			return PR;
  		case FM:
  			match(FM);
  			return FM;
  		case TM:
  			match(TM);
  			return TM;
  		default:
  			throw new ParserException("improvement expected");
  	}
  }

  /**
   *
   */
  private int scanner() {
  	int token;
  
  	// do not read over EOF (lineno() will be increased otherwise)
  	if (blocked) {
  		return EOF;
  	}
  	try {
  		// get next token from stream
  		switch (tokenizer.nextToken()) {
  			// end of file or stream
  			case StreamTokenizer.TT_EOF:
  				blocked = true;
  				return EOF;
  				// end of line
  			case StreamTokenizer.TT_EOL:
  				return EOL;
  				// a number (double value)
  			case StreamTokenizer.TT_NUMBER:
  				number = (new Double(tokenizer.nval)).intValue();
  				return NUMBER;
  				// a word or string
  				// look up in symboltable (keys in lowercase)
  			case StreamTokenizer.TT_WORD:  // a word (string)
  				word = tokenizer.sval;
  				token = getToken(word);
  				if (token < 0) {
  					return WORD;
  				} else {
  					return token;
  				}
  				// a single character - convert it to a string
  				// look up in symboltable (keys in lowercase)
  			default:
  				if ((char) tokenizer.ttype == '"') {
  					word = tokenizer.sval;
  					return WORD;
  				} else {
  					word = String.valueOf((char) tokenizer.ttype);
  					token = getToken(word);
  					if (token < 0) {
  						return WORD;
  					} else {
  						return token;
  					}
  				}
  		}
  		// this shouldn't happen - return error token
  	} catch (IOException e) {
  		return ERROR;
  	}
  }

  /**
   *
   */
  private Command send() throws ParserException {
    log.trace("send()");
      
  	try {
  		match(SEND);
  		Command cmd = new Command(SEND);
  		cmd.setNumber(parseNumber());
  		cmd.setType(sendType());
  		match(TO);
  		cmd.setSource(planet());
  		return cmd;
  	} catch (ParserException pe) {
  		throw new ParserException("SEND: " + pe.getMessage());
  	}
  }

  /**
   *
   */
  private int sendType() throws ParserException {
    log.trace("sendType()");
    
  	switch (lookahead) {
  		case FI:
  			match(FI);
  			return FI;
  		case TR:
  			match(TR);
  			return TR;
  		case C0:
  			match(C0);
  			return C0;
  		case C1:
  			match(C1);
  			return C1;
  		case C2:
  			match(C2);
  			return C2;
  		case C3:
  			match(C3);
  			return C3;
  		case C4:
  			match(C4);
  			return C4;
  		case C5:
  			match(C5);
  			return C5;
  		case C6:
  			match(C6);
  			return C6;
  		case C7:
  			match(C7);
  			return C7;
  		case C8:
  			match(C8);
  			return C8;
  		case C9:
  			match(C9);
  			return C9;
  		default:
  			throw new ParserException("ship type expected");
  	}
  }

  /**
   *
   */
  private Command spy() throws ParserException {
    log.trace("spy()");
    
  	match(SPY);
  	return new Command(SPY);
  }

  /**
   *
   */
  private Command statement() throws ParserException {
    log.trace("statement()");
    
  	switch (lookahead) {
  		case DECLARE:
  			return declare();
  		case MESSAGE:
  			return message();
  		case PLAYERNAME:
  			return playername();
  		default:
  			Command cmd = onBlockStmt();
  			match(ON);
  			cmd.setSource(planet());
  			return cmd;
  	}
  }
  
  /**
   *
   */
  private CommandList statements() {
    log.trace("statements()");
    
  	final int[] first = {BUILD, DECLARE, HOMEPLANET, MESSAGE, ON, PLANETNAME, PLAYERNAME, RESEARCH, SEND, SPY};
  	final int[] follow = {END};
  	CommandList cmdList = null;
  	try {
  		switch (lookahead) {
  			case BUILD:
  			case DECLARE:
  			case HOMEPLANET:
  			case MESSAGE:
  			case PLANETNAME:
  			case PLAYERNAME:
  			case RESEARCH:
  			case SEND:
  			case SPY:
  				cmdList = new CommandList();
  				cmdList.add(statement());
  				cmdList.add(statements());
  				return cmdList;
  			case ON:
  				cmdList = onBlock();
  				cmdList.add(statements());
  				return cmdList;
  			case END:
  				return null;
  			default:
  				throw new ParserException("unknown command");
  		}
  	} catch (ParserException pe) {
  		if (error(pe.getMessage(), first, follow) > 0) {
  			return statements();
  		}
  		return null;
  	}
  }

  /**
   * 
   */
  private CommandList user() throws ParserException {
    log.trace("user()");

  	CommandList cmdList = new CommandList();
  	try {
  		Command userCmd = new Command(match(USER));
  		userCmd.setText(parseWord());
  		cmdList.add(userCmd);
  		Command pwdCmd = new Command(match(PASSWORD));
  		pwdCmd.setText(parseWord());
  		cmdList.add(pwdCmd);
  	} catch (ParserException pe) {
  		throw new ParserException("USER: " + pe.getMessage());
  	}
  	return cmdList;
  }
  
}
