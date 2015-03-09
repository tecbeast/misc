package de.seipler.bookcollection.model;

import de.seipler.bookcollection.entity.Status;

/**
 * 
 * @author Georg Seipler
 */
public class StatusSet extends EntitySet {
  
  public StatusSet() {
    super();
  }
  
  public boolean add(Status status) {
    return set.add(status);
  }

  public int find(Status status) {
    return set.find(status);
  }
  
  public Status get(int index) {
    return (Status) set.get(index);
  }

  public Status get(String id) {
    Status result = null;
    if (id != null) {
      for (int i = 0; i < size(); i++) {
        Status status = get(i);
        if (id.equals(status.getId())) {
          result = status;
          break;
        }
      }
    }
    return result;
  }

  public boolean remove(Status status) {
    return set.remove(status);
  }

  public void update(Status status) {
    set.update(status);
  }

}
