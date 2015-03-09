package de.seipler.ausgaben.common;

import java.math.BigDecimal;

/**
 * 
 * @author Georg Seipler
 */
public final class Betrag {
    
    private BigDecimal fValue;
    
    public Betrag(BigDecimal pBetragAsBigDecimal) {
        fValue = pBetragAsBigDecimal;
        fValue.setScale(2);
    }

    public Betrag(String pBetragAsString) {
        this(new BigDecimal(pBetragAsString));
    }
    
    public BigDecimal asBigDecimal() {
        return fValue;
    }
    
    public String asString() {
        return fValue.toString();
    }
    
}
