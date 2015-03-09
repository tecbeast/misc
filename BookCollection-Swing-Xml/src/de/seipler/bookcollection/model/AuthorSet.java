package de.seipler.bookcollection.model;

import de.seipler.bookcollection.entity.Author;


/**
 * 
 * @author Georg Seipler
 */
public class AuthorSet extends EntitySet {
  
  public AuthorSet() {
    super();
  }
  
  public boolean add(Author author) {
    return set.add(author);
  }
  
  public int find(Author author) {
    return set.find(author);
  }
  
  public Author get(int index) {
    return (Author) set.get(index);
  }
  
  public Author get(String id) {
    Author result = null;
    if (id != null) {
      for (int i = 0; i < size(); i++) {
        Author author = get(i);
        if (id.equals(author.getId())) {
          result = author;
          break;
        }
      }
    }
    return result;
  }

  public boolean remove(Author author) {
    return set.remove(author);
  }
  
  public void update(Author author) {
    set.update(author);
  }
  
}
