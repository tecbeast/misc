package de.seipler.util.swing.layout;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.HashMap;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * 
 * @author Georg Seipler
 */
public class GridBagLayoutXmlHandler extends DefaultHandler {
  
  public static final String TAG_ANCHOR = "anchor";
  public static final String TAG_FILL = "fill";
  public static final String TAG_GRIDBAG_CONSTRAINTS = "gridbagConstraints";
  public static final String TAG_GRIDBAG_DEFAULTS = "gridbagDefaults";  
  public static final String TAG_GRIDHEIGHT = "gridHeight";
  public static final String TAG_GRIDWIDTH = "gridWidth";
  public static final String TAG_GRIDX = "gridX";
  public static final String TAG_GRIDY = "gridY";
  public static final String TAG_INSETS = "insets";
  public static final String TAG_IPADX = "ipadX";
  public static final String TAG_IPADY = "ipadY";
  public static final String TAG_WEIGHTX = "weightX";
  public static final String TAG_WEIGHTY = "weightY";
  
  public static final String ATTRIBUTE_BOTTOM = "bottom";
  public static final String ATTRIBUTE_ID = "id";
  public static final String ATTRIBUTE_LEFT = "left";
  public static final String ATTRIBUTE_RIGHT = "right";
  public static final String ATTRIBUTE_TOP = "top";
  
  public static final String VALUE_RELATIVE = "RELATIVE";
  public static final String VALUE_REMAINDER = "REMAINDER";
  public static final String VALUE_NONE = "NONE";
  public static final String VALUE_BOTH = "BOTH";
  public static final String VALUE_HORIZONTAL = "HORIZONTAL";
  public static final String VALUE_VERTICAL = "VERTICAL";
  public static final String VALUE_CENTER = "CENTER";
  public static final String VALUE_NORTH = "NORTH";
  public static final String VALUE_NORTHEAST = "NORTHEAST";
  public static final String VALUE_EAST = "EAST";
  public static final String VALUE_SOUTHEAST = "SOUTHEAST";
  public static final String VALUE_SOUTH = "SOUTH";
  public static final String VALUE_SOUTHWEST = "SOUTHWEST";
  public static final String VALUE_WEST = "WEST";
  public static final String VALUE_NORTHWEST = "NORTHWEST";
  public static final String VALUE_PAGE_START = "PAGE_START";
  public static final String VALUE_PAGE_END = "PAGE_END";
  public static final String VALUE_LINE_START = "LINE_START";
  public static final String VALUE_LINE_END = "LINE_END";
  public static final String VALUE_FIRST_LINE_START = "FIRST_LINE_START";
  public static final String VALUE_FIRST_LINE_END = "FIRST_LINE_END";
  public static final String VALUE_LAST_LINE_START = "LAST_LINE_START";
  public static final String VALUE_LAST_LINE_END = "LAST_LINE_END";

  protected static final Map ANCHOR_VALUES = new HashMap();
  protected static final Map GRID_VALUES = new HashMap();
  protected static final Map FILL_VALUES = new HashMap();

  static {
    
    GRID_VALUES.put(VALUE_RELATIVE, new Integer(GridBagConstraints.RELATIVE));
    GRID_VALUES.put(VALUE_REMAINDER, new Integer(GridBagConstraints.REMAINDER));
    
    FILL_VALUES.put(VALUE_NONE, new Integer(GridBagConstraints.NONE));
    FILL_VALUES.put(VALUE_BOTH, new Integer(GridBagConstraints.BOTH));
    FILL_VALUES.put(VALUE_HORIZONTAL, new Integer(GridBagConstraints.HORIZONTAL));
    FILL_VALUES.put(VALUE_VERTICAL, new Integer(GridBagConstraints.VERTICAL));
    
    ANCHOR_VALUES.put(VALUE_CENTER, new Integer(GridBagConstraints.CENTER));
    ANCHOR_VALUES.put(VALUE_NORTH, new Integer(GridBagConstraints.NORTH));
    ANCHOR_VALUES.put(VALUE_NORTHEAST, new Integer(GridBagConstraints.NORTHEAST));
    ANCHOR_VALUES.put(VALUE_EAST, new Integer(GridBagConstraints.EAST));
    ANCHOR_VALUES.put(VALUE_SOUTHEAST, new Integer(GridBagConstraints.SOUTHEAST));
    ANCHOR_VALUES.put(VALUE_SOUTH, new Integer(GridBagConstraints.SOUTH));
    ANCHOR_VALUES.put(VALUE_SOUTHWEST, new Integer(GridBagConstraints.SOUTHWEST));
    ANCHOR_VALUES.put(VALUE_WEST, new Integer(GridBagConstraints.WEST));
    ANCHOR_VALUES.put(VALUE_NORTHWEST, new Integer(GridBagConstraints.NORTHWEST));
    ANCHOR_VALUES.put(VALUE_PAGE_START, new Integer(GridBagConstraints.PAGE_START));
    ANCHOR_VALUES.put(VALUE_PAGE_END, new Integer(GridBagConstraints.PAGE_END));
    ANCHOR_VALUES.put(VALUE_LINE_START, new Integer(GridBagConstraints.LINE_START));
    ANCHOR_VALUES.put(VALUE_LINE_END, new Integer(GridBagConstraints.LINE_END));
    ANCHOR_VALUES.put(VALUE_FIRST_LINE_START, new Integer(GridBagConstraints.FIRST_LINE_START));
    ANCHOR_VALUES.put(VALUE_FIRST_LINE_END, new Integer(GridBagConstraints.FIRST_LINE_END));
    ANCHOR_VALUES.put(VALUE_LAST_LINE_START, new Integer(GridBagConstraints.LAST_LINE_START));
    ANCHOR_VALUES.put(VALUE_LAST_LINE_END, new Integer(GridBagConstraints.LAST_LINE_END));
    
  }
  
  private GridBagLayoutWrapper wrapper;
  private GridBagConstraints constraints;
  private GridBagConstraints defaultConstraints;
  private String constraintsId;
  private StringBuffer value;
  private boolean defaultContext;
  
  public GridBagLayoutXmlHandler(GridBagLayoutWrapper wrapper) {
    this.wrapper = wrapper;
    this.value = new StringBuffer();
    this.constraints = new GridBagConstraints();
    this.defaultConstraints = new GridBagConstraints();
    this.defaultContext = false;
  }

  public void characters(char[] ch, int start, int length) throws SAXException {
    value.append(new String(ch, start, length));
  }

  public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
    if (TAG_GRIDBAG_CONSTRAINTS.equals(qName)) {
      constraintsId = attributes.getValue(ATTRIBUTE_ID);
      resetConstraints();
    } else if (TAG_GRIDBAG_DEFAULTS.equals(qName)) {
      defaultContext = true;
    } else if (TAG_INSETS.equals(qName)) {
      int top = getIntValue(attributes.getValue(ATTRIBUTE_TOP));
      int left = getIntValue(attributes.getValue(ATTRIBUTE_LEFT));
      int bottom = getIntValue(attributes.getValue(ATTRIBUTE_BOTTOM));
      int right = getIntValue(attributes.getValue(ATTRIBUTE_RIGHT));
      if (defaultContext) {
        defaultConstraints.insets = new Insets(top, left, bottom, right);
      } else {
        constraints.insets = new Insets(top, left, bottom, right);
      }
    }
  }

  public void endElement(String uri, String localName, String qName) throws SAXException {
    if (TAG_ANCHOR.equals(qName)) {
      if (defaultContext) {
        defaultConstraints.anchor = getIntValue(value.toString().trim(), ANCHOR_VALUES);
      } else {
        constraints.anchor = getIntValue(value.toString().trim(), ANCHOR_VALUES);
      }
    } else if (TAG_GRIDBAG_CONSTRAINTS.equals(qName)) {
      wrapper.addConstraints(constraintsId, constraints);
    } else if (TAG_GRIDBAG_DEFAULTS.equals(qName)) {
      defaultContext = false;
    } else if (TAG_FILL.equals(qName)) {
      if (defaultContext) {
        defaultConstraints.fill = getIntValue(value.toString().trim(), FILL_VALUES);
      } else {
        constraints.fill = getIntValue(value.toString().trim(), FILL_VALUES);
      }
    } else if (TAG_GRIDHEIGHT.equals(qName)) {
      if (defaultContext) {
        defaultConstraints.gridheight = getIntValue(value.toString().trim());
      } else {
        constraints.gridheight = getIntValue(value.toString().trim());
      }
    } else if (TAG_GRIDWIDTH.equals(qName)) {
      if (defaultContext) {
        defaultConstraints.gridwidth = getIntValue(value.toString().trim());
      } else {
        constraints.gridwidth = getIntValue(value.toString().trim());
      }
    } else if (TAG_GRIDX.equals(qName)) {
      if (defaultContext) {
        defaultConstraints.gridx = getIntValue(value.toString().trim(), GRID_VALUES);
      } else {
        constraints.gridx = getIntValue(value.toString().trim(), GRID_VALUES);
      }
    } else if (TAG_GRIDY.equals(qName)) {
      if (defaultContext) {
        defaultConstraints.gridy = getIntValue(value.toString().trim(), GRID_VALUES);
      } else {
        constraints.gridy = getIntValue(value.toString().trim(), GRID_VALUES);
      }
    } else if (TAG_IPADX.equals(qName)) {
      if (defaultContext) {
        defaultConstraints.ipadx = getIntValue(value.toString().trim());
      } else {
        constraints.ipadx = getIntValue(value.toString().trim());
      }
    } else if (TAG_IPADY.equals(qName)) {
      if (defaultContext) {
        defaultConstraints.ipady = getIntValue(value.toString().trim());
      } else {
        constraints.ipady = getIntValue(value.toString().trim());
      }
    } else if (TAG_WEIGHTX.equals(qName)) {
      if (defaultContext) {
        defaultConstraints.weightx = getDoubleValue(value.toString().trim());
      } else {
        constraints.weightx = getDoubleValue(value.toString().trim());
      }
    } else if (TAG_WEIGHTY.equals(qName)) {
      if (defaultContext) {
        defaultConstraints.weighty = getDoubleValue(value.toString().trim());
      } else {
        constraints.weighty = getDoubleValue(value.toString().trim());
      }
    }
    value = new StringBuffer();
  }
  
  private void resetConstraints() {
    constraints.gridx = defaultConstraints.gridx;
    constraints.gridy = defaultConstraints.gridy;
    constraints.gridwidth = defaultConstraints.gridwidth;
    constraints.gridheight = defaultConstraints.gridheight;
    constraints.weightx = defaultConstraints.weightx;
    constraints.weighty = defaultConstraints.weighty;
    constraints.anchor = defaultConstraints.anchor;
    constraints.fill = defaultConstraints.fill;
    constraints.insets = defaultConstraints.insets;
    constraints.ipadx = defaultConstraints.ipadx;
    constraints.ipady = defaultConstraints.ipady;
  }
  
  private int getIntValue(String value) {
    return Integer.parseInt(value);
  }

  private int getIntValue(String value, Map constants) {
    if (constants != null) {
      Integer constant = (Integer) constants.get(value);
      if (constant != null) {
        return constant.intValue();
      }
    }
    return getIntValue(value);
  }

  private double getDoubleValue(String value) {
    return Double.parseDouble(value);
  }

}
