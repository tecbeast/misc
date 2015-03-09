package de.seipler.ausgaben.common;

import java.math.BigDecimal;
import java.util.EmptyStackException;
import java.util.Stack;

/**
 * 
 * @author Georg Seipler
 */
public final class UpnBetragRechner {
    
    private class BigDecimalUpnStack {
        
        private Stack fStack = new Stack();
        
        public BigDecimal peek() throws EmptyStackException {
            return (BigDecimal) fStack.peek();
        }
        
        public BigDecimal pop() throws EmptyStackException {
            return (BigDecimal) fStack.pop();
        }
        
        public void push(BigDecimal pValue) {
            fStack.push(pValue);
        }
        
        public void clear() {
            fStack.clear();
        }
        
    }
    
    private BigDecimalUpnStack fStack;

    public UpnBetragRechner() {
        fStack = new BigDecimalUpnStack();
    }

    private UpnBetragRechner push(BigDecimal pValue) {
        fStack.push(pValue);
        return this;
    }

    public UpnBetragRechner push(Betrag pBetrag) {
        return push(pBetrag.asBigDecimal());
    }

    public UpnBetragRechner push(String pBetragAsString) {
        return push(new Betrag(pBetragAsString));
    }
    
    public UpnBetragRechner add() {
        BigDecimal second = fStack.pop();
        BigDecimal first = fStack.pop();
        fStack.push(first.add(second));
        return this;
    }
    
    public UpnBetragRechner add(Betrag pBetrag) {
        push(pBetrag);
        return add();
    }

    public UpnBetragRechner add(String pBetragAsString) {
        push(pBetragAsString);
        return add();
    }

    public UpnBetragRechner sub() {
        BigDecimal second = fStack.pop();
        BigDecimal first = fStack.pop();
        fStack.push(first.subtract(second));
        return this;
    }

    public UpnBetragRechner sub(Betrag pBetrag) {
        push(pBetrag);
        return sub();
    }

    public UpnBetragRechner sub(String pBetragAsString) {
        push(pBetragAsString);
        return sub();
    }
    
    public UpnBetragRechner neg() {
        BigDecimal value = fStack.pop();
        fStack.push(value.negate());
        return this;
    }
    
    public Betrag pop() throws EmptyStackException {
        return new Betrag(fStack.pop());
    }
    
    public Betrag peek() throws EmptyStackException {
        return new Betrag(fStack.peek());
    }
    
    public void clear() {
        fStack.clear();
    }

}