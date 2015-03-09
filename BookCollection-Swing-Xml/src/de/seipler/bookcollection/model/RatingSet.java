package de.seipler.bookcollection.model;

import de.seipler.bookcollection.entity.Rating;

/**
 * 
 * @author Georg Seipler
 */
public class RatingSet extends EntitySet {
  
  public RatingSet() {
    super();
  }
  
  public boolean add(Rating rating) {
    return set.add(rating);
  }

  public int find(Rating rating) {
    return set.find(rating);
  }
  
  public Rating get(int index) {
    return (Rating) set.get(index);
  }

  public Rating get(String id) {
    Rating result = null;
    if (id != null) {
      for (int i = 0; i < size(); i++) {
        Rating rating = get(i);
        if (id.equals(rating.getId())) {
          result = rating;
          break;
        }
      }
    }
    return result;
  }

  public boolean remove(Rating rating) {
    return set.remove(rating);
  }

  public void update(Rating rating) {
    set.update(rating);
  }

}
