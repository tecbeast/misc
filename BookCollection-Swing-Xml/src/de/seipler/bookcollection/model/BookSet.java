package de.seipler.bookcollection.model;

import de.seipler.bookcollection.entity.Book;

/**
 * 
 * @author Georg Seipler
 */
public class BookSet extends EntitySet {
  
  public BookSet() {
    super();
  }
  
  public boolean add(Book book) {
    return set.add(book);
  }

  public int find(Book book) {
    return set.find(book);
  }
  
  public Book get(int index) {
    return (Book) set.get(index);
  }
  
  public Book get(String id) {
    Book result = null;
    if (id != null) {
      for (int i = 0; i < size(); i++) {
        Book book = get(i);
        if (id.equals(book.getId())) {
          result = book;
          break;
        }
      }
    }
    return result;
  }

  public boolean remove(Book book) {
    return set.remove(book);
  }
  
  public void update(Book book) {
    set.update(book);
  }
  
  public boolean validate(EntityCache cache) {
    boolean valid = true;
    for (int i = 0; i < size(); i++) {
      if (!get(i).validate(cache)) {
        valid = false;
      }
    }
    return valid;
  }

}
