package de.seipler.util.format;

/**
 * 
 * @author Georg Seipler
 */
public final class StringFormatter {

	/**
	 * 
	 */
	public static String leftAlignText(String value, int length) {
		return leftAlignText(value, length, ' ');
	}

	/**
	 * 
	 */
	public static String leftAlignText(String value, int length, char fillChar) {
		StringBuffer buffer = new StringBuffer(length);
		int valueLength = 0;
		if (value != null) {
			valueLength = value.length();
			if (valueLength > length) {
			 throw new StringFormatterException("String " + value + " is too long (" + valueLength + " > " + length + ")");
			} else {
				buffer.append(value);
			}
		}
		for (int i = valueLength; i < length; i++) { buffer.append(fillChar);  }
		return buffer.toString();
	}

	/**
	 * Remove leading whitespaces.
	 */
	public static String leftSideTrim(String value) {
		if (value != null) {
			int startPos = 0;
			while (startPos < value.length()) {
				if (Character.isWhitespace(value.charAt(startPos))) {	startPos++;	} else { break; }
			}
			return value.substring(startPos);
		}
		return null;
	}
  
	/**
	 * 
	 */
	public static long parseRightAlignedNumber(String value) {
		if (value != null) {
			// remove leading zeroes for conversion
			int startPos = 0;
			while (startPos < value.length()) {
				if (value.charAt(startPos) == '0') { startPos++; } else {	break; }
			}
			long result = -1;
			try {
				result = Long.parseLong(value.substring(startPos));
			} catch (Exception any) {
				throw new StringFormatterException("String " + value + " not a proper numeric value.");
			}
			return result; 
		} else {
			throw new StringFormatterException("String must not be null");
		}
	}

  /**
   * 
   */
  public static String rightAlignNumber(long value, int length) {
    StringBuffer buffer = new StringBuffer(length);
    String valueString = Long.toString(value);
    if (valueString.length() > length) {
      throw new StringFormatterException("Long " + valueString + " is too long (" + valueString.length() + " > " + length + ")");
    }
    for (int i = 0; i < (length - valueString.length()); i++) { buffer.append('0'); }
    buffer.append(valueString);
    return buffer.toString();
  }  

	/**
	 * 
	 */
	public static String rightAlignText(String value, int length) {
		return rightAlignText(value, length, ' ');
	}

	/**
	 * Remove trailing whitespaces.
	 */
	public static String rightAlignText(String value, int length, char fillChar) {
		StringBuffer buffer = new StringBuffer(length);
		int valueLength = 0;
		if (value != null) {
			valueLength = value.length();
			if (valueLength > length) {
			 throw new StringFormatterException("String " + value + " is too long (" + valueLength + " > " + length + ")");
			} else {
				for (int i = valueLength; i < length; i++) { buffer.append(fillChar);  }
				buffer.append(value);
			}
		}
		return buffer.toString();
	}

	/**
	 *
	 */
	public static String rightSideTrim(String value) {
		if (value != null) {
			int realLength = value.length();
			while (realLength > 0) {
				if (Character.isWhitespace(value.charAt(realLength - 1))) {
					realLength--;
				} else {
					break;
				}
			}
			if (realLength > 0) { return value.substring(0, realLength); }
		}
		return null;
	}

}
