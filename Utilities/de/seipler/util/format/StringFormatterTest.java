package de.seipler.util.format;

import junit.framework.TestCase;

/**
 * 
 * @author Georg Seipler
 */
public class StringFormatterTest extends TestCase {

  /**
   * Constructor for StringFormatterTest.
   * @param name
   */
  public StringFormatterTest(String name) {
    super(name);
  }

  /**
   * @see junit.framework.TestCase#setUp()
   */
  protected void setUp() throws Exception {
    super.setUp();
  }

  /**
   * @see junit.framework.TestCase#tearDown()
   */
  protected void tearDown() throws Exception {
    super.tearDown();
  }

	/**
	 * 
	 */
	public void testLeftAlignText() {
    
		assertTrue(StringFormatter.leftAlignText("A", 1).equals("A"));

		assertTrue(StringFormatter.leftAlignText("TEST", 7).equals("TEST   "));

		try {
			StringFormatter.leftAlignText("TEST", 3);
			fail();
		} catch (Exception e) {
			assertTrue(e instanceof StringFormatterException);
		}
    
	}
  
  /**
   *
   */
  public void testRightSideTrim() {

    assertTrue(StringFormatter.rightSideTrim("A").equals("A"));

    assertTrue(StringFormatter.rightSideTrim("TEST   ").equals("TEST"));
    
    assertTrue(StringFormatter.rightSideTrim("   AGAIN").equals("   AGAIN"));
    
  }
  
  /**
   * 
   */
  public void testParseRightAlignedNumber() {
    
    assertTrue(StringFormatter.parseRightAlignedNumber("1") == 1);

    assertTrue(StringFormatter.parseRightAlignedNumber("00123") == 123);

    try {
      StringFormatter.parseRightAlignedNumber("TEST");
      fail();
    } catch (Exception e) {
      assertTrue(e instanceof StringFormatterException);
    }
    
  }

	/**
	 *
	 */
	public void testLeftSideTrim() {

		assertTrue(StringFormatter.leftSideTrim("A").equals("A"));

		assertTrue(StringFormatter.leftSideTrim("   TEST").equals("TEST"));
    
		assertTrue(StringFormatter.leftSideTrim("AGAIN   ").equals("AGAIN   "));
    
	}

	/**
	 * 
	 */
	public void testRightAlignNumber() {

		assertTrue(StringFormatter.rightAlignNumber(1, 1).equals("1"));

		assertTrue(StringFormatter.rightAlignNumber(9, 3).equals("009"));

		try {
			StringFormatter.rightAlignNumber(1000, 3);
			fail();
		} catch (Exception e) {
			assertTrue(e instanceof StringFormatterException);
		}
        
	}  

	/**
	 * 
	 */
	public void testRightAlignText() {
    
		assertTrue(StringFormatter.rightAlignText("A", 1).equals("A"));

		assertTrue(StringFormatter.rightAlignText("TEST", 7).equals("   TEST"));

		try {
			StringFormatter.rightAlignText("TEST", 3);
			fail();
		} catch (Exception e) {
			assertTrue(e instanceof StringFormatterException);
		}
    
	}
  
}
