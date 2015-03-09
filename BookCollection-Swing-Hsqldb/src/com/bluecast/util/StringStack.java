/*
 * $Id: StringStack.java,v 1.1 2004/04/25 20:12:40 Georg Exp $
 *
 * (C) Copyright 2002 by Yuval Oren. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *   
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 */


package com.bluecast.util;

/**
 * A simple implementation of a stack of Strings
 */
public class StringStack {
    private String[] stack;
    private int pos; // position of the top of the stack

    public StringStack(int initialSize) {
        stack = new String[initialSize];
        pos = -1;
    }

    public String pop() {
        if (pos >= 0) {
            return stack[pos--];
        }
        else {
            return null;
        }
    }

    public void push(String s) {
        if (pos + 1 < stack.length) {
            stack[++pos] = s;
        }
        else {
            setSize(stack.length * 2);
            stack[++pos] = s;
        }
    }

    public void setSize(int newSize) {
        if (newSize != stack.length) {
            String[] newStack = new String[newSize];
            System.arraycopy(stack,0,newStack,0,Math.min(stack.length,newSize));
            stack = newStack;
        }
    }

    public void clear() {
        pos = -1;
    }

        public int size() {
            return pos+1;
        }
}
