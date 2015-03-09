package de.seipler.bookcollection.model;

import de.seipler.bookcollection.entity.Series;

/**
 * 
 * @author Georg Seipler
 */
public class SeriesSet extends EntitySet {
  
  public SeriesSet() {
    super();
  }
  
  public boolean add(Series series) {
    return set.add(series);
  }

  public int find(Series series) {
    return set.find(series);
  }
  
  public Series get(int index) {
    return (Series) set.get(index);
  }

  public Series get(String id) {
    Series result = null;
    if (id != null) {
      for (int i = 0; i < set.size(); i++) {
        Series series = get(i);
        if (id.equals(series.getId())) {
          result = series;
          break;
        }
      }
    }
    return result;
  }

  public boolean remove(Series series) {
    return set.remove(series);
  }

  public void update(Series series) {
    set.update(series);
  }

}
