package de.seipler.util.swing.layout;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;

import org.xml.sax.Attributes;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * 
 * @author Georg Seipler
 */
public class XmlLayoutHandler extends DefaultHandler {
  
  public static final String TAG_ALIGNMENT = "alignment";
  public static final String TAG_ANCHOR = "anchor";
  public static final String TAG_BORDER_LAYOUT = "borderLayout";
  public static final String TAG_BOX_LAYOUT = "boxLayout";
  public static final String TAG_COMPONENT = "component";
  public static final String TAG_FILL = "fill";
  public static final String TAG_FILLER = "filler";
  public static final String TAG_FLOW_LAYOUT = "flowLayout";
  public static final String TAG_GAPS = "gaps";
  public static final String TAG_GRID_LAYOUT = "gridLayout";
  public static final String TAG_GRIDBAG_CONSTRAINTS = "gridBagConstraints";
  public static final String TAG_GRIDBAG_LAYOUT = "gridBagLayout";
  public static final String TAG_GRIDHEIGHT = "gridHeight";
  public static final String TAG_GRIDWIDTH = "gridWidth";
  public static final String TAG_GRIDX = "gridX";
  public static final String TAG_GRIDY = "gridY";
  public static final String TAG_HORIZONTAL_GLUE = "horizontalGlue";
  public static final String TAG_INSETS = "insets";
  public static final String TAG_IPADX = "ipadX";
  public static final String TAG_IPADY = "ipadY";
  public static final String TAG_LAYOUT = "layout";
  public static final String TAG_MAXIMUM_SIZE = "maximumSize";
  public static final String TAG_MINIMUM_SIZE = "minimumSize";
  public static final String TAG_PLACEMENT = "placement";
  public static final String TAG_PREFERRED_SIZE = "preferredSize";
  public static final String TAG_RIGID_AREA = "rigidArea";
  public static final String TAG_VERTICAL_GLUE = "verticalGlue";
  public static final String TAG_WEIGHTX = "weightX";
  public static final String TAG_WEIGHTY = "weightY";
  
  public static final String ATTRIBUTE_ALIGNMENT = "alignment";
  public static final String ATTRIBUTE_AREA = "area";
  public static final String ATTRIBUTE_BOTTOM = "bottom";
  public static final String ATTRIBUTE_COLUMNS = "columns";
  public static final String ATTRIBUTE_HEIGHT = "height";
  public static final String ATTRIBUTE_HORIZONTAL = "horizontal";
  public static final String ATTRIBUTE_ID = "id";
  public static final String ATTRIBUTE_LEFT = "left";
  public static final String ATTRIBUTE_ORIENTATION = "orientation";
  public static final String ATTRIBUTE_RIGHT = "right";
  public static final String ATTRIBUTE_ROWS = "rows";
  public static final String ATTRIBUTE_TOP = "top";
  public static final String ATTRIBUTE_VERTICAL = "vertical";
  public static final String ATTRIBUTE_WIDTH = "width";
  
  public static final String VALUE_TRUE = "true";
  
  public static final String VALUE_AFTER_LAST_LINE = "AFTER_LAST_LINE";
  public static final String VALUE_AFTER_LINE_ENDS = "AFTER_LINE_ENDS";
  public static final String VALUE_BEFORE_FIRST_LINE = "BEFORE_FIRST_LINE";
  public static final String VALUE_BEFORE_LINE_BEGINS = "BEFORE_LINE_BEGINS";
  public static final String VALUE_BOTH = "BOTH";
  public static final String VALUE_BOTTOM = "BOTTOM";
  public static final String VALUE_CENTER = "CENTER";
  public static final String VALUE_EAST = "EAST";
  public static final String VALUE_FIRST_LINE_END = "FIRST_LINE_END";
  public static final String VALUE_FIRST_LINE_START = "FIRST_LINE_START";
  public static final String VALUE_HORIZONTAL = "HORIZONTAL";
  public static final String VALUE_LAST_LINE_END = "LAST_LINE_END";
  public static final String VALUE_LAST_LINE_START = "LAST_LINE_START";
  public static final String VALUE_LEADING = "LEADING";
  public static final String VALUE_LEFT = "LEFT";
  public static final String VALUE_LINE_AXIS = "LINE_AXIS";
  public static final String VALUE_LINE_END = "LINE_END";
  public static final String VALUE_LINE_START = "LINE_START";
  public static final String VALUE_MAX_VALUE = "MAX_VALUE";
  public static final String VALUE_NONE = "NONE";
  public static final String VALUE_NORTH = "NORTH";
  public static final String VALUE_NORTHEAST = "NORTHEAST";
  public static final String VALUE_NORTHWEST = "NORTHWEST";
  public static final String VALUE_PAGE_AXIS = "PAGE_AXIS";
  public static final String VALUE_PAGE_END = "PAGE_END";
  public static final String VALUE_PAGE_START = "PAGE_START";
  public static final String VALUE_RELATIVE = "RELATIVE";
  public static final String VALUE_REMAINDER = "REMAINDER";
  public static final String VALUE_RIGHT = "RIGHT";
  public static final String VALUE_SOUTH = "SOUTH";
  public static final String VALUE_SOUTHEAST = "SOUTHEAST";
  public static final String VALUE_SOUTHWEST = "SOUTHWEST";
  public static final String VALUE_TOP = "TOP";
  public static final String VALUE_TRAILING = "TRAILING";
  public static final String VALUE_VERTICAL = "VERTICAL";
  public static final String VALUE_WEST = "WEST";
  public static final String VALUE_X_AXIS = "X_AXIS";
  public static final String VALUE_Y_AXIS = "Y_AXIS";
  
  protected static final Map GRIDBAG_ANCHOR_VALUES = new HashMap();
  protected static final Map GRIDBAG_GRID_VALUES = new HashMap();
  protected static final Map GRIDBAG_FILL_VALUES = new HashMap();
  protected static final Map COMPONENT_ALIGNMENT_VALUES = new HashMap();
  protected static final Map SIZE_VALUES = new HashMap();
  protected static final Map BOX_LAYOUT_ORIENTATION_VALUES = new HashMap();
  protected static final Map BORDER_LAYOUT_AREA_VALUES = new HashMap();
  protected static final Map FLOW_LAYOUT_ALIGNMENT_VALUES = new HashMap();

  static {
    
    GRIDBAG_GRID_VALUES.put(VALUE_RELATIVE, new Integer(GridBagConstraints.RELATIVE));
    GRIDBAG_GRID_VALUES.put(VALUE_REMAINDER, new Integer(GridBagConstraints.REMAINDER));
    
    GRIDBAG_FILL_VALUES.put(VALUE_NONE, new Integer(GridBagConstraints.NONE));
    GRIDBAG_FILL_VALUES.put(VALUE_BOTH, new Integer(GridBagConstraints.BOTH));
    GRIDBAG_FILL_VALUES.put(VALUE_HORIZONTAL, new Integer(GridBagConstraints.HORIZONTAL));
    GRIDBAG_FILL_VALUES.put(VALUE_VERTICAL, new Integer(GridBagConstraints.VERTICAL));
    
    GRIDBAG_ANCHOR_VALUES.put(VALUE_CENTER, new Integer(GridBagConstraints.CENTER));
    GRIDBAG_ANCHOR_VALUES.put(VALUE_NORTH, new Integer(GridBagConstraints.NORTH));
    GRIDBAG_ANCHOR_VALUES.put(VALUE_NORTHEAST, new Integer(GridBagConstraints.NORTHEAST));
    GRIDBAG_ANCHOR_VALUES.put(VALUE_EAST, new Integer(GridBagConstraints.EAST));
    GRIDBAG_ANCHOR_VALUES.put(VALUE_SOUTHEAST, new Integer(GridBagConstraints.SOUTHEAST));
    GRIDBAG_ANCHOR_VALUES.put(VALUE_SOUTH, new Integer(GridBagConstraints.SOUTH));
    GRIDBAG_ANCHOR_VALUES.put(VALUE_SOUTHWEST, new Integer(GridBagConstraints.SOUTHWEST));
    GRIDBAG_ANCHOR_VALUES.put(VALUE_WEST, new Integer(GridBagConstraints.WEST));
    GRIDBAG_ANCHOR_VALUES.put(VALUE_NORTHWEST, new Integer(GridBagConstraints.NORTHWEST));
    GRIDBAG_ANCHOR_VALUES.put(VALUE_PAGE_START, new Integer(GridBagConstraints.PAGE_START));
    GRIDBAG_ANCHOR_VALUES.put(VALUE_PAGE_END, new Integer(GridBagConstraints.PAGE_END));
    GRIDBAG_ANCHOR_VALUES.put(VALUE_LINE_START, new Integer(GridBagConstraints.LINE_START));
    GRIDBAG_ANCHOR_VALUES.put(VALUE_LINE_END, new Integer(GridBagConstraints.LINE_END));
    GRIDBAG_ANCHOR_VALUES.put(VALUE_FIRST_LINE_START, new Integer(GridBagConstraints.FIRST_LINE_START));
    GRIDBAG_ANCHOR_VALUES.put(VALUE_FIRST_LINE_END, new Integer(GridBagConstraints.FIRST_LINE_END));
    GRIDBAG_ANCHOR_VALUES.put(VALUE_LAST_LINE_START, new Integer(GridBagConstraints.LAST_LINE_START));
    GRIDBAG_ANCHOR_VALUES.put(VALUE_LAST_LINE_END, new Integer(GridBagConstraints.LAST_LINE_END));

    COMPONENT_ALIGNMENT_VALUES.put(VALUE_TOP, new Double(Component.TOP_ALIGNMENT));
    COMPONENT_ALIGNMENT_VALUES.put(VALUE_LEFT, new Double(Component.LEFT_ALIGNMENT));
    COMPONENT_ALIGNMENT_VALUES.put(VALUE_CENTER, new Double(Component.CENTER_ALIGNMENT));
    COMPONENT_ALIGNMENT_VALUES.put(VALUE_BOTTOM, new Double(Component.BOTTOM_ALIGNMENT));
    COMPONENT_ALIGNMENT_VALUES.put(VALUE_RIGHT, new Double(Component.RIGHT_ALIGNMENT));
    
    SIZE_VALUES.put(VALUE_MAX_VALUE, new Integer(Integer.MAX_VALUE));
    
    BOX_LAYOUT_ORIENTATION_VALUES.put(VALUE_X_AXIS, new Integer(BoxLayout.X_AXIS));
    BOX_LAYOUT_ORIENTATION_VALUES.put(VALUE_LINE_AXIS, new Integer(BoxLayout.LINE_AXIS));
    BOX_LAYOUT_ORIENTATION_VALUES.put(VALUE_Y_AXIS, new Integer(BoxLayout.Y_AXIS));
    BOX_LAYOUT_ORIENTATION_VALUES.put(VALUE_PAGE_AXIS, new Integer(BoxLayout.PAGE_AXIS));
    
    BORDER_LAYOUT_AREA_VALUES.put(VALUE_NORTH, BorderLayout.NORTH);
    BORDER_LAYOUT_AREA_VALUES.put(VALUE_SOUTH, BorderLayout.SOUTH);
    BORDER_LAYOUT_AREA_VALUES.put(VALUE_EAST, BorderLayout.EAST);
    BORDER_LAYOUT_AREA_VALUES.put(VALUE_WEST, BorderLayout.WEST);
    BORDER_LAYOUT_AREA_VALUES.put(VALUE_CENTER, BorderLayout.CENTER);
    BORDER_LAYOUT_AREA_VALUES.put(VALUE_BEFORE_FIRST_LINE, BorderLayout.BEFORE_FIRST_LINE);
    BORDER_LAYOUT_AREA_VALUES.put(VALUE_AFTER_LAST_LINE, BorderLayout.AFTER_LAST_LINE);
    BORDER_LAYOUT_AREA_VALUES.put(VALUE_BEFORE_LINE_BEGINS, BorderLayout.BEFORE_LINE_BEGINS);
    BORDER_LAYOUT_AREA_VALUES.put(VALUE_AFTER_LINE_ENDS, BorderLayout.AFTER_LINE_ENDS);
    BORDER_LAYOUT_AREA_VALUES.put(VALUE_PAGE_START, BorderLayout.PAGE_START);
    BORDER_LAYOUT_AREA_VALUES.put(VALUE_PAGE_END, BorderLayout.PAGE_END);
    BORDER_LAYOUT_AREA_VALUES.put(VALUE_LINE_START, BorderLayout.LINE_START);
    BORDER_LAYOUT_AREA_VALUES.put(VALUE_LINE_END, BorderLayout.LINE_END);

    FLOW_LAYOUT_ALIGNMENT_VALUES.put(VALUE_LEFT, new Integer(FlowLayout.LEFT));
    FLOW_LAYOUT_ALIGNMENT_VALUES.put(VALUE_CENTER, new Integer(FlowLayout.CENTER));
    FLOW_LAYOUT_ALIGNMENT_VALUES.put(VALUE_RIGHT, new Integer(FlowLayout.RIGHT));
    FLOW_LAYOUT_ALIGNMENT_VALUES.put(VALUE_LEADING, new Integer(FlowLayout.LEADING));
    FLOW_LAYOUT_ALIGNMENT_VALUES.put(VALUE_TRAILING, new Integer(FlowLayout.TRAILING));
    
  }

  private Locator locator;  
  private XmlLayoutFactory layoutFactory;
  private XmlLayout xmlLayout;
  private GridLayout gridLayout;
  private GridBagLayout gridBagLayout;
  private BorderLayout borderLayout;
  private FlowLayout flowLayout;
  private GridBagConstraints gridBagConstraints;
  private boolean inFiller;
  private Dimension minimumSize;
  private Dimension preferredSize;
  private Dimension maximumSize;
  private Dimension gaps;
  private Stack componentStack;
  private StringBuffer value;
  
  protected XmlLayoutHandler(XmlLayoutFactory layoutFactory) {
    this.layoutFactory = layoutFactory;
    this.value = new StringBuffer();
    this.componentStack = new Stack();
    this.gridBagConstraints = new GridBagConstraints();
  }

  public void setDocumentLocator(Locator locator) {
    this.locator = locator;
  }
  
  public void characters(char[] ch, int start, int length) throws SAXException {
    value.append(new String(ch, start, length));
  }

  public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
    
    if (TAG_ALIGNMENT.equals(qName)) {
      checkComponent();
      if (attributes.getValue(ATTRIBUTE_HORIZONTAL) != null) {
        double horizontal = getDoubleValue(attributes.getValue(ATTRIBUTE_HORIZONTAL), COMPONENT_ALIGNMENT_VALUES);
        ((JComponent) componentStack.peek()).setAlignmentX((float) horizontal);
      }
      if (attributes.getValue(ATTRIBUTE_VERTICAL) != null) {
        double vertical = getDoubleValue(attributes.getValue(ATTRIBUTE_VERTICAL), COMPONENT_ALIGNMENT_VALUES);
        ((JComponent) componentStack.peek()).setAlignmentY((float) vertical);
      }
      
    } else if (TAG_ANCHOR.equals(qName)) {
      checkGridBagConstraints();
      
    } else if (TAG_BORDER_LAYOUT.equals(qName)) {
      checkContainer();
      borderLayout = new BorderLayout();
      
    } else if (TAG_BOX_LAYOUT.equals(qName)) {
      checkContainer();
      int orientation = getIntValue(attributes.getValue(ATTRIBUTE_ORIENTATION), BOX_LAYOUT_ORIENTATION_VALUES);
      Container container = (Container) componentStack.peek();
      container.setLayout(new BoxLayout(container, orientation));
      
    } else if (TAG_COMPONENT.equals(qName)) {
      checkLayout();
      String id = attributes.getValue(ATTRIBUTE_ID);
      Component component = xmlLayout.get(id);
      if (component == null) {
        component = new JPanel();
      }
      xmlLayout.add(component, id);
      if (componentStack.size() > 0) {
        ((Container) componentStack.peek()).add(component);
      }
      componentStack.push(component);
      
    } else if (TAG_FILL.equals(qName)) {
      checkGridBagConstraints();

    } else if (TAG_FILLER.equals(qName)) {
      checkBoxLayout();
      inFiller = true;

    } else if (TAG_FLOW_LAYOUT.equals(qName)) {
      checkContainer();
      if (attributes.getValue(ATTRIBUTE_ALIGNMENT) != null) {
        int alignment = getIntValue(attributes.getValue(ATTRIBUTE_ALIGNMENT), FLOW_LAYOUT_ALIGNMENT_VALUES);
        flowLayout = new FlowLayout(alignment);
      } else {
        flowLayout = new FlowLayout();
      }
      
    } else if (TAG_GAPS.equals(qName)) {
      checkBorderFlowOrGridLayout();
      int horizontal = getIntValue(attributes.getValue(ATTRIBUTE_HORIZONTAL));
      int vertical = getIntValue(attributes.getValue(ATTRIBUTE_VERTICAL));
      gaps = new Dimension(horizontal, vertical);
      
    } else if (TAG_GRID_LAYOUT.equals(qName)) {
      checkContainer();
      int rows = getIntValue(attributes.getValue(ATTRIBUTE_ROWS));
      int columns = getIntValue(attributes.getValue(ATTRIBUTE_COLUMNS));
      gridLayout = new GridLayout(rows, columns);
      
    } else if (TAG_GRIDBAG_CONSTRAINTS.equals(qName)) {
      checkGridBagLayout();
      gridBagConstraints = new GridBagConstraints();

    } else if (TAG_GRIDBAG_LAYOUT.equals(qName)) {
      checkContainer();
      gridBagLayout = new GridBagLayout();
      
    } else if (TAG_GRIDHEIGHT.equals(qName)) {
      checkGridBagConstraints();

    } else if (TAG_GRIDWIDTH.equals(qName)) {
      checkGridBagConstraints();

    } else if (TAG_GRIDX.equals(qName)) {
      checkGridBagConstraints();

    } else if (TAG_GRIDY.equals(qName)) {
      checkGridBagConstraints();

    } else if (TAG_HORIZONTAL_GLUE.equals(qName)) {
      checkBoxLayout();
      if (componentStack.size() > 0) {
        ((Container) componentStack.peek()).add(Box.createHorizontalGlue());
      }
      
    } else if (TAG_INSETS.equals(qName)) {
      checkGridBagLayout();
      int top = getIntValue(attributes.getValue(ATTRIBUTE_TOP));
      int left = getIntValue(attributes.getValue(ATTRIBUTE_LEFT));
      int bottom = getIntValue(attributes.getValue(ATTRIBUTE_BOTTOM));
      int right = getIntValue(attributes.getValue(ATTRIBUTE_RIGHT));
      gridBagConstraints.insets = new Insets(top, left, bottom, right);
      
    } else if (TAG_IPADX.equals(qName)) {
      checkGridBagConstraints();

    } else if (TAG_IPADY.equals(qName)) {
      checkGridBagConstraints();

    } else if (TAG_LAYOUT.equals(qName)) {
      String id = attributes.getValue(ATTRIBUTE_ID);
      xmlLayout = layoutFactory.get(id);
      
    } else if (TAG_MAXIMUM_SIZE.equals(qName)) {
      if (!inFiller) { checkComponent(); }
      int width = getIntValue(attributes.getValue(ATTRIBUTE_WIDTH), SIZE_VALUES);
      int height = getIntValue(attributes.getValue(ATTRIBUTE_HEIGHT), SIZE_VALUES);
      maximumSize = new Dimension(width, height);

    } else if (TAG_MINIMUM_SIZE.equals(qName)) {
      if (!inFiller) { checkComponent(); }
      int width = getIntValue(attributes.getValue(ATTRIBUTE_WIDTH), SIZE_VALUES);
      int height = getIntValue(attributes.getValue(ATTRIBUTE_HEIGHT), SIZE_VALUES);
      minimumSize = new Dimension(width, height);

    } else if (TAG_PLACEMENT.equals(qName)) {
      checkBorderLayout();
      String area = getStringValue(attributes.getValue(ATTRIBUTE_AREA), BORDER_LAYOUT_AREA_VALUES);
      Component component = (Component) componentStack.pop();
      Container container = (Container) componentStack.peek();
      ((BorderLayout) container.getLayout()).addLayoutComponent(component, area);
      componentStack.push(component);
      
    } else if (TAG_PREFERRED_SIZE.equals(qName)) {
      if (!inFiller) { checkComponent(); }
      int width = getIntValue(attributes.getValue(ATTRIBUTE_WIDTH), SIZE_VALUES);
      int height = getIntValue(attributes.getValue(ATTRIBUTE_HEIGHT), SIZE_VALUES);
      preferredSize = new Dimension(width, height);

    } else if (TAG_RIGID_AREA.equals(qName)) {
      checkBoxLayout();
      int width = getIntValue(attributes.getValue(ATTRIBUTE_WIDTH), SIZE_VALUES);
      int height = getIntValue(attributes.getValue(ATTRIBUTE_HEIGHT), SIZE_VALUES);
      if (componentStack.size() > 0) {
        ((Container) componentStack.peek()).add(Box.createRigidArea(new Dimension(width, height)));
      }
      
    } else if (TAG_VERTICAL_GLUE.equals(qName)) {
      checkBoxLayout();
      if (componentStack.size() > 0) {
        ((Container) componentStack.peek()).add(Box.createVerticalGlue());
      }

    } else if (TAG_WEIGHTX.equals(qName)) {
      checkGridBagConstraints();

    } else if (TAG_WEIGHTY.equals(qName)) {
      checkGridBagConstraints();
      
    }
    
  }

  public void endElement(String uri, String localName, String qName) throws SAXException {
    
    if (TAG_ANCHOR.equals(qName)) {
      gridBagConstraints.anchor = getIntValue(value.toString().trim(), GRIDBAG_ANCHOR_VALUES);
      
    } else if (TAG_BORDER_LAYOUT.equals(qName)) {
      if (gaps != null) {
        borderLayout.setHgap(gaps.width);
        borderLayout.setVgap(gaps.height);
      }
      ((Container) componentStack.peek()).setLayout(borderLayout);
      borderLayout = null;
      gaps = null;

    } else if (TAG_COMPONENT.equals(qName)) {
      Component component = (Component) componentStack.pop();
      if (minimumSize != null) {
        ((JComponent) component).setMinimumSize(minimumSize);
        minimumSize = null;
      }
      if (preferredSize != null) {
        ((JComponent) component).setPreferredSize(preferredSize);
        preferredSize = null;
      }
      if (maximumSize != null) {
        ((JComponent) component).setMaximumSize(maximumSize);
        maximumSize = null;
      }

    } else if (TAG_FILL.equals(qName)) {
      gridBagConstraints.fill = getIntValue(value.toString().trim(), GRIDBAG_FILL_VALUES);

    } else if (TAG_FILLER.equals(qName)) {
      if (componentStack.size() > 0) {
        Box.Filler filler = new Box.Filler(minimumSize, preferredSize, maximumSize);
        ((Container) componentStack.peek()).add(filler);
      }
      minimumSize = null;
      preferredSize = null;
      maximumSize = null;
      inFiller = false;
          
    } else if (TAG_FLOW_LAYOUT.equals(qName)) {
      if (gaps != null) {
        flowLayout.setHgap(gaps.width);
        flowLayout.setVgap(gaps.height);
      }
      ((Container) componentStack.peek()).setLayout(flowLayout);
      flowLayout = null;
      gaps = null;

    } else if (TAG_GRID_LAYOUT.equals(qName)) {
      if (gaps != null) {
        gridLayout.setHgap(gaps.width);
        gridLayout.setVgap(gaps.height);
      }
      ((Container) componentStack.peek()).setLayout(gridLayout);
      gridLayout = null;
      gaps = null;

    } else if (TAG_GRIDBAG_CONSTRAINTS.equals(qName)) {
      Component component = (Component) componentStack.pop();
      Container container = (Container) componentStack.peek();
      ((GridBagLayout) container.getLayout()).addLayoutComponent(component, gridBagConstraints);
      componentStack.push(component);
      gridBagConstraints = null;
      
    } else if (TAG_GRIDBAG_LAYOUT.equals(qName)) {
      ((Container) componentStack.peek()).setLayout(gridBagLayout);
      gridBagLayout = null;
          
    } else if (TAG_GRIDHEIGHT.equals(qName)) {
      gridBagConstraints.gridheight = getIntValue(value.toString().trim());

    } else if (TAG_GRIDWIDTH.equals(qName)) {
      gridBagConstraints.gridwidth = getIntValue(value.toString().trim());

    } else if (TAG_GRIDX.equals(qName)) {
      gridBagConstraints.gridx = getIntValue(value.toString().trim(), GRIDBAG_GRID_VALUES);

    } else if (TAG_GRIDY.equals(qName)) {
      gridBagConstraints.gridy = getIntValue(value.toString().trim(), GRIDBAG_GRID_VALUES);

    } else if (TAG_IPADX.equals(qName)) {
      gridBagConstraints.ipadx = getIntValue(value.toString().trim());

    } else if (TAG_IPADY.equals(qName)) {
      gridBagConstraints.ipady = getIntValue(value.toString().trim());

    } else if (TAG_LAYOUT.equals(qName)) {
      xmlLayout = null;
    
    } else if (TAG_WEIGHTX.equals(qName)) {
      gridBagConstraints.weightx = getDoubleValue(value.toString().trim());

    } else if (TAG_WEIGHTY.equals(qName)) {
      gridBagConstraints.weighty = getDoubleValue(value.toString().trim());

    }
    
    value = new StringBuffer();
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

  private double getDoubleValue(String value, Map constants) {
    if (constants != null) {
      Double constant = (Double) constants.get(value);
      if (constant != null) {
        return constant.doubleValue();
      }
    }
    return getDoubleValue(value);
  }

  private String getStringValue(String value, Map constants) {
    if (constants != null) {
      String constant = (String) constants.get(value);
      if (constant != null) {
        return constant;
      }
    }
    return value;
  }
  
  private boolean inLayout(Class layoutClass) {
    boolean result = false;
    if (componentStack.size() > 1) {
      Component component = (Component) componentStack.pop();
      Container container = (Container) componentStack.peek();
      result = layoutClass.isInstance(container.getLayout());
      componentStack.push(component);
    }
    return result;
  }
  
  private void checkGridBagConstraints() throws SAXParseException {
    if (gridBagConstraints == null) {
      throw new SAXParseException("Not within GridBag Constraints.", locator);
    }
  }
  
  private void checkGridBagLayout() throws SAXParseException {
    if (!inLayout(GridBagLayout.class)) {
      throw new SAXParseException("Container Layout is not GridBag.", locator);
    }
  }

  private void checkBorderLayout() throws SAXParseException {
    if (!inLayout(BorderLayout.class)) {
      throw new SAXParseException("Container Layout is not BorderLayout.", locator);
    }
  }
  
  private void checkBoxLayout() throws SAXParseException {
    if (!inLayout(BoxLayout.class)) {
      throw new SAXParseException("Container Layout is not BoxLayout.", locator);
    }
  }

  private void checkBorderFlowOrGridLayout() throws SAXParseException {
    if (!(inLayout(BorderLayout.class) || inLayout(FlowLayout.class) || inLayout(GridLayout.class))) {
      throw new SAXParseException("Container Layout is not BorderLayout, FlowLayout or GridLayout.", locator);
    }
  }
  
  private void checkComponent() throws SAXParseException {
    if (componentStack.size() < 1) {
      throw new SAXParseException("Not within a Component.", locator);
    }
  }

  private void checkContainer() throws SAXParseException {
    checkComponent();
    if (!(componentStack.peek() instanceof Container)) {
      throw new SAXParseException("Not within a Container.", locator);
    }
  }

  private void checkLayout() throws SAXParseException {
    if (xmlLayout == null) {
      throw new SAXParseException("Not within a Layout.", locator);
    }
  }

}
