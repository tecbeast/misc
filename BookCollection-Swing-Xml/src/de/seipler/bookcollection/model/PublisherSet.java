package de.seipler.bookcollection.model;

import de.seipler.bookcollection.entity.Publisher;

/**
 * 
 * @author Georg Seipler
 */
public class PublisherSet extends EntitySet {
  
  public PublisherSet() {
    super();
  }
  
  public boolean add(Publisher publisher) {
    return set.add(publisher);
  }

  public int find(Publisher publisher) {
    return set.find(publisher);
  }
  
  public Publisher get(int index) {
    return (Publisher) set.get(index);
  }

  public Publisher get(String id) {
    Publisher result = null;
    if (id != null) {
      for (int i = 0; i < size(); i++) {
        Publisher publisher = get(i);
        if (id.equals(publisher.getId())) {
          result = publisher;
          break;
        }
      }
    }
    return result;
  }

  public boolean remove(Publisher publisher) {
    return set.remove(publisher);
  }

  public void update(Publisher publisher) {
    set.update(publisher);
  }

}
