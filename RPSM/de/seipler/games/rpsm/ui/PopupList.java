package de.seipler.games.rpsm.ui;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.EventListener;

import javax.swing.Action;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.Popup;
import javax.swing.PopupFactory;
import javax.swing.event.EventListenerList;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;
import javax.swing.text.Keymap;
import javax.swing.text.TextAction;

public class PopupList {
  
  protected static KeyStroke ENTER_KEY = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
  protected static KeyStroke ESCAPE_KEY = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
  protected static KeyStroke CURSOR_DOWN_KEY = KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0);
  protected static KeyStroke CURSOR_UP_KEY = KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0);
  
  private EventListenerList listenerList;
  private Popup popup;
  private JList list;
  private JScrollPane scrollPane;
  private JTextComponent parent;
  
  private Action enterAction;
  private Action escapeAction;
  private Action cursorDownAction;
  private Action cursorUpAction;

  private boolean hideOnEscape;
  private boolean hideOnSelect;

  public PopupList(JTextComponent aParent, JList aList) {
    
    this.parent = aParent;
    this.list = aList;
    this.scrollPane = new JScrollPane(list);
    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

    setHideOnEscape(true);
    setHideOnSelect(true);
    
    this.listenerList = new EventListenerList();
    
    this.list.getModel().addListDataListener(
      new ListDataListener(){
        public void contentsChanged(ListDataEvent e) {
        }
        public void intervalAdded(ListDataEvent e) {
          list.setSelectedIndex(0);
        }
        public void intervalRemoved(ListDataEvent e) {
          list.setSelectedIndex(0);
        }
      }
    );

    this.cursorUpAction = new TextAction("popuplist-cursor-up") {
      public void actionPerformed(ActionEvent e) {
        if (popup != null) {
          int currentIndex = list.getSelectedIndex();
          if (currentIndex > 0) {
            list.setSelectedIndex(--currentIndex);
            list.ensureIndexIsVisible(currentIndex);
          }
        }
      }
    };
    
    this.cursorDownAction = new TextAction("popuplist-cursor-down") {
      public void actionPerformed(ActionEvent e) {
        if (popup != null) {
          int currentIndex = list.getSelectedIndex();
          if (list.getModel().getSize() > currentIndex + 1) {
            list.setSelectedIndex(++currentIndex);
            list.ensureIndexIsVisible(currentIndex);
          }
        }
      }
    };

    this.enterAction = new TextAction("popuplist-enter") {
      public void actionPerformed(ActionEvent e) {
        ListSelectionEvent listEvent = new ListSelectionEvent(this, list.getSelectedIndex(), list.getSelectedIndex(), false);
        EventListener[] listeners = listenerList.getListeners(ListSelectionListener.class);
        if (hideOnSelect) { hide(); } 
        for (int i = 0; i < listeners.length; i++) {
          ((ListSelectionListener) listeners[i]).valueChanged(listEvent);
        }
      }
    };

    this.escapeAction = new TextAction("popuplist-escape") {
      public void actionPerformed(ActionEvent e) {
        hide();
      }
    };

    parent.setKeymap(JTextComponent.addKeymap(getClass().getName(), parent.getKeymap()));

  }
  
  public void addListSelectionListener(ListSelectionListener listener) {
    listenerList.add(ListSelectionListener.class, listener);
  }
  
  public JList getList() {
    return list;
  }

  public JTextComponent getParent() {
    return parent;
  }

  public boolean isHideOnEscape() {
    return hideOnEscape;
  }

  public boolean isHideOnSelect() {
    return hideOnSelect;
  }
  
  public boolean isShown() {
    return (popup != null);
  }

  public void hide() {
    if (popup != null) {
      popup.hide();
      popup = null;
      Keymap keyMap = parent.getKeymap();      
      keyMap.removeKeyStrokeBinding(ESCAPE_KEY);
      keyMap.removeKeyStrokeBinding(ENTER_KEY);
      keyMap.removeKeyStrokeBinding(CURSOR_UP_KEY);
      keyMap.removeKeyStrokeBinding(CURSOR_DOWN_KEY);
    }
  }
  
  public void removeListSelectionListener(ListSelectionListener listener) {
    listenerList.remove(ListSelectionListener.class, listener);
  }
  
  public void setHideOnEscape(boolean hideOnEscape) {
    this.hideOnEscape = hideOnEscape;
  }

  public void setHideOnSelect(boolean hideOnSelect) {
    this.hideOnSelect = hideOnSelect;
  }

  protected void setList(JList list) {
    if (list == null) {
      throw new IllegalArgumentException("Parameter list must not be null");
    }
    this.list = list;
  }

  protected void setParent(JTextComponent parent) {
    if (parent == null) {
      throw new IllegalArgumentException("Parameter parent must not be null");
    }
    this.parent = parent;
  }

  public void show() {
    if (popup == null) {
      Keymap keyMap = this.parent.getKeymap();
      keyMap.addActionForKeyStroke(ENTER_KEY, enterAction);
      keyMap.addActionForKeyStroke(CURSOR_UP_KEY, cursorUpAction);
      keyMap.addActionForKeyStroke(CURSOR_DOWN_KEY, cursorDownAction);
      if (hideOnEscape) {
        keyMap.addActionForKeyStroke(ESCAPE_KEY, escapeAction);
      }
      Rectangle caretPosition = null;
      try {
        caretPosition = parent.modelToView(parent.getCaretPosition());
      } catch (BadLocationException ble) {
        caretPosition = new Rectangle();
      }
      Point screenLocation = parent.getLocationOnScreen();
      caretPosition.translate(screenLocation.x, screenLocation.y);
      caretPosition.translate(caretPosition.width, caretPosition.height);
      PopupFactory factory = PopupFactory.getSharedInstance();
      popup = factory.getPopup(parent, scrollPane, caretPosition.x, caretPosition.y);
      popup.show();
    }
  }
  
}
