package de.seipler.game.starrealms;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import de.seipler.util.commandline.GetOpt;
import de.seipler.util.log.Log;
import de.seipler.util.log.LogFactory;

/**
 * Game central: controls all running games, handles user input, etc.
 */
public final class StarRealms {

  // usage information
  private final static String USAGE =
  	"java StarRealms [-c configuration] [-t] [filename]"
  ;

  private static String configFile;
  private static StarRealms instance;

  private Properties properties;
  private UserManager userManager;
  private Log log;
  
  /**
   * Hidden constructor for singleton pattern.
   */
  private StarRealms() throws StarRealmsException {
  
		log = LogFactory.getLog(getClass());

  	InputStream in = null;
  
  	if (configFile != null) {
  	  try {
  			in = new FileInputStream(configFile);
  	  } catch (FileNotFoundException fnfe) {
  			// no action necessary - error will be catched because in == null
  	  }
  	} else {
      in = getClass().getClassLoader().getResourceAsStream("StarRealms.properties");
  	}
  
  	// load found properties
  	if (in == null) {
  	  throw new StarRealmsException("Missing Properties");
  	} else {
  	  try {
  			properties = new Properties();
  			properties.load(in);
  	  } catch (IOException ioe) {
  		  throw new StarRealmsException("Unable to load Properties");
  	  }
  	}
  
		LogFactory.getInstance().configure(properties);

    /*
  	// initialize UserManager
  	String userFile = getProperty("user.file");
  	try {
  	  userManager = new UserManager(userFile);
  	} catch (Exception e) {  // IOException, FileNotFoundException
  		throw new StarRealmsException("Unable to read userdata from " + userFile);
  	}
    */
  
  }

  /**
   * This method should be called to make a clean start.
   */
  public void start() {
    // start logging
    log.info("*** Logging started ***");
  }

  /**
   * This method should be called to make a clean stop.
   */
  public void stop() {
  	// stop logging
  	log.info("*** Logging stopped ***");
  }

  /**
   * Singleton pattern.
   */
  public static StarRealms getInstance() {
  	if (instance == null) { instance = new StarRealms(); }
  	return instance;
  }

  /**
   *
   */
  public Log getLog() {
  	return log;
  }

  /**
   *
   */
  public String getProperty(String name) {
  	return properties.getProperty(name);
  }

  /**
   *
   */
  public UserManager getUserManager() {
  	return userManager;
  }

  /**
   *
   */
  public static void main(String[] args) {
  	GetOpt go = new GetOpt(args, "c:t?");
  	go.optErr = true;
  
  	boolean doTurn = false;
  
  	int ch = -1;
  	// process options in command line arguments
  	while ((ch = go.getopt()) != GetOpt.optEOF) {
  	  switch ((char)ch) {
  		case 'c':
  		  configFile = go.optArgGet();
  		  break;
  		case 't':
  		  doTurn = true;
  		  break;
  		case '?':
  		default:
  		  System.out.println(USAGE);
  		  return;
  	  }
  	}
  
  	// initialize StarRealms,
  	// in case of problems stop via uncatched runtime exception
  	StarRealms master = StarRealms.getInstance();
    master.start();
  
  	// do a full turn
  	if (doTurn) {
  
  	// parse user input
  	} else {
  	  BufferedReader parseReader = null;
  
  	  try {
  			if (args.length < go.optIndexGet()) {
  			  parseReader = new BufferedReader(new InputStreamReader(System.in));
  			} else {
  				parseReader = new BufferedReader(new FileReader(args[go.optIndexGet()]));
  			}
  			master.parse(parseReader);
  	  } catch (Exception e) {
        master.log.fatal("Unable to parse Commands");
        master.log.fatal(e);
  	  } finally {
  			if (parseReader != null) {
  			  try { parseReader.close(); } catch (Exception ignore) { }
  			}
  	  }
  
  	}
  
  	master.stop();
  }

  /**
   *
   */
  public void parse(BufferedReader parseReader) throws IOException {
  	Parser parser = new Parser(parseReader);
  	CommandList cmdList = parser.parse();
  	if (cmdList == null) {
  	  System.out.println("[ Parser reports errors ]");
  	  System.out.println(parser.getErrors());
  	} else {
  	  System.out.println(cmdList);
  	}
  }
  
}
