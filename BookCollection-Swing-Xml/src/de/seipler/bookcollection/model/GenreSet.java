package de.seipler.bookcollection.model;

import de.seipler.bookcollection.entity.Genre;

/**
 * 
 * @author Georg Seipler
 */
public class GenreSet extends EntitySet {
  
  public GenreSet() {
    super();
  }
  
  public boolean add(Genre genre) {
    return set.add(genre);
  }

  public int find(Genre genre) {
    return set.find(genre);
  }
  
  public Genre get(int index) {
    return (Genre) set.get(index);
  }

  public Genre get(String id) {
    Genre result = null;
    if (id != null) {
      for (int i = 0; i < size(); i++) {
        Genre genre = get(i);
        if (id.equals(genre.getId())) {
          result = genre;
          break;
        }
      }
    }
    return result;
  }

  public boolean remove(Genre genre) {
    return set.remove(genre);
  }

  public void update(Genre genre) {
    set.update(genre);
  }

}
