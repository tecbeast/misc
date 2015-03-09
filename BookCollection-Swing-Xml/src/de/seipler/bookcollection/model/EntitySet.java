package de.seipler.bookcollection.model;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

import de.seipler.util.collection.ContentChangeEvent;
import de.seipler.util.collection.ContentChangeListener;
import de.seipler.util.collection.SortedArraySet;

/**
 * @author Georg Seipler
 */
public class EntitySet {

  protected EntitySetListModel listModel;
  protected SortedArraySet set;
  
  class EntitySetListModel extends AbstractListModel implements ComboBoxModel, ContentChangeListener {

    private boolean emptyValue;
    private Object selectedItem;
  
    // implements javax.swing.ComboBoxModel
    public Object getSelectedItem() {
      return this.selectedItem;
    }

    // implements javax.swing.ComboBoxModel
    public void setSelectedItem(Object anItem) {
      if ((selectedItem != null && !selectedItem.equals(anItem)) || selectedItem == null && anItem != null) {
        selectedItem = anItem;
        fireContentsChanged(this, -1, -1);
      }
    }

    // implements javax.swing.ListModel
    public int getSize() {
      if (emptyValue) {
        return set.size() + 1;
      } else {
        return set.size();
      }
    }

    // implements javax.swing.ListModel
    public Object getElementAt(int index) {
      if (index < 0) {
        return null;
      } else if (emptyValue) {
        if (index == 0) {
          return null;
        } else {
          return set.get(index - 1);
        }
      } else {
        return set.get(index);
      }
    }
  
    protected void setEmptyValue(boolean emptyValue) {
      this.emptyValue = emptyValue;
    }
  
    public boolean hasEmptyValue() {
      return this.emptyValue;
    }

    // implements ContentChangeListener
    public void handleContentChange(ContentChangeEvent event) {
      switch (event.getChange()) {
        case ContentChangeEvent.OBJECT_ADDED:
          fireIntervalAdded(this, event.getIndex(), event.getIndex());
          break;
        case ContentChangeEvent.OBJECT_REMOVED:
          fireIntervalRemoved(this, event.getIndex(), event.getIndex());
          break;
      }
    }
  
  }
  
  protected EntitySet() {
    this.set = new SortedArraySet();
  }
  
  public int size() {
    return set.size();
  }
  
  public EntitySetListModel getListModel(boolean emptyValue) {
    if (listModel == null) {
      listModel = new EntitySetListModel();
      set.addListener(listModel); 
    }
    listModel.setEmptyValue(emptyValue);
    return this.listModel;
  }
  
}
