package de.seipler.test.descriptors.zip;

/**
 * @author Georg Seipler
 */
public class XMLFormatterPerformance {

  public static final int NR_OF_LOOPS = 1000000;
  
  public static void main(String[] args) {
        
    long time1 = System.currentTimeMillis();
    
    for (int i = 0; i < NR_OF_LOOPS; i++) {
      XMLFormatter.toXML("dies ist ein Test ohne Sonderzeichen");
      XMLFormatter.toXML("dieser Teil hat < dagegen > auch Sonderzeichen");
    }
    
    long time2 = System.currentTimeMillis();
    System.out.println(NR_OF_LOOPS + " Sets of conversion in " + (time2 - time1) + " ms.");    
    
  }

}
