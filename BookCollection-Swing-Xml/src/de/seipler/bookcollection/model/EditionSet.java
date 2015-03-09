package de.seipler.bookcollection.model;

import de.seipler.bookcollection.entity.Edition;

/**
 * 
 * @author Georg Seipler
 */
public class EditionSet extends EntitySet {
  
  public EditionSet() {
    super();
  }

  public boolean add(Edition edition) {
    return set.add(edition);
  }

  public int find(Edition edition) {
    return set.find(edition);
  }
  
  public Edition get(int index) {
    return (Edition) set.get(index);
  }

  public Edition get(String id) {
    Edition result = null;
    if (id != null) {
      for (int i = 0; i < size(); i++) {
        Edition edition = get(i);
        if (id.equals(edition.getId())) {
          result = edition;
          break;
        }
      }
    }
    return result;
  }

  public boolean remove(Edition edition) {
    return set.remove(edition);
  }
  
  public void update(Edition edition) {
    set.update(edition);
  }

}
