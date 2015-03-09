package de.seipler.ausgaben.datenversorgung.xml;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @author Georg Seipler
 */
public class XmlFormatter {

    public static final DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
    public static final String LINE_SEPARATOR = System.getProperty("line.separator", "\n");
    public static final String ENCODING = "UTF-8";

    /**
     * 
     */
    public static String toXml(String pSource) {

        if (pSource == null) {
            return null;
        }

        int i = 0;
        boolean noNeedForConversion = true;
        while (noNeedForConversion && (i < pSource.length())) {
            switch (pSource.charAt(i)) {
            case '&':
            case '<':
            case '>':
            case '"':
            case '\'':
                noNeedForConversion = false;
                break;
            default:
                i++;
                break;
            }
        }

        if (noNeedForConversion) {
            return pSource;
        }

        StringBuffer buffer = new StringBuffer(pSource.substring(0, i));
        while (i < pSource.length()) {
            switch (pSource.charAt(i)) {
            case '&':
                buffer.append("&amp;");
                break;
            case '<':
                buffer.append("&lt;");
                break;
            case '>':
                buffer.append("&gt;");
                break;
            case '"':
                buffer.append("&quot;");
                break;
            case '\'':
                buffer.append("&apos;");
                break;
            default:
                buffer.append(pSource.charAt(i));
                break;
            }
            i++;
        }
        return buffer.toString();

    }

    /**
     * 
     */
    public static void addXmlHeader(StringBuffer pBuffer) {
        pBuffer.append("<?xml version=\"1.0\" encoding=\"");
        pBuffer.append(ENCODING);
        pBuffer.append("\"?>");
        addNewLine(pBuffer);
    }

    /**
     * 
     */
    public static void addElement(StringBuffer pBuffer, int pIndentation, String pName, String pValue) {
        addIndentation(pBuffer, pIndentation);
        if ((pValue == null) || (pValue.length() == 0)) {
            pBuffer.append('<');
            pBuffer.append(pName);
            pBuffer.append(" />");
        } else {
            pBuffer.append('<');
            pBuffer.append(pName);
            pBuffer.append('>');
            pBuffer.append(toXml(pValue));
            pBuffer.append("</");
            pBuffer.append(pName);
            pBuffer.append('>');
        }
        addNewLine(pBuffer);
    }

    /**
     * 
     */
    public static void addElementHeader(StringBuffer pBuffer, int pIndentation, String pName) {
        addElementHeader(pBuffer, pIndentation, pName, (String[]) null, (String[]) null);
    }

    /**
     * 
     */
    public static void addElementHeader(StringBuffer pBuffer, int pIndentation, String pName, String pAttribute, String pValue) {
        addElementHeader(pBuffer, pIndentation, pName, new String[] { pAttribute }, new String[] { pValue });
    }

    /**
     * 
     */
    public static void addElementHeader(
        StringBuffer pBuffer,
        int pIndentation,
        String pName,
        String[] pAttributes,
        String[] pValues
    ) {
        addIndentation(pBuffer, pIndentation);
        pBuffer.append('<');
        pBuffer.append(pName);
        if ((pAttributes != null) && (pValues != null)) {
            for (int i = 0; i < pAttributes.length; i++) {
                if ((pAttributes[i] != null) && (pAttributes[i].length() > 0)) {
                    pBuffer.append(' ');
                    pBuffer.append(pAttributes[i]);
                    pBuffer.append("=\"");
                    if ((i < pValues.length) && (pValues[i] != null)) {
                        pBuffer.append(pValues[i]);
                    }
                    pBuffer.append('"');
                }
            }
        }
        pBuffer.append('>');
        addNewLine(pBuffer);
    }

    /**
     * 
     */
    public static void addElementFooter(StringBuffer pBuffer, int pIndentation, String pName) {
        addIndentation(pBuffer, pIndentation);
        pBuffer.append("</");
        pBuffer.append(pName);
        pBuffer.append('>');
        addNewLine(pBuffer);
    }

    /**
     * 
     */
    public static void addIndentation(StringBuffer pBuffer, int pIndentation) {
        for (int i = 0; i < pIndentation; i++) {
            pBuffer.append("  ");
        }
    }
    
    /**
     * 
     */
    public static void addNewLine(StringBuffer buffer) {
        buffer.append(LINE_SEPARATOR);
    }
    
    /**
     * 
     */
    public static Date stringToDate(String pDateString) {
        Date date;
        try {
            date = dateFormat.parse(pDateString);
        } catch (ParseException pe) {
            date = null;
        }
        return date;
    }

    /**
     * 
     */
    public static String dateToString(Date pDate) {
        String dateString;
        if (pDate != null) {
            dateString = dateFormat.format(pDate);
        } else {
            dateString = null;
        }
        return dateString;
    }
    
}
