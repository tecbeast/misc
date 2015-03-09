package de.seipler.bookcollection.model;

import de.seipler.bookcollection.entity.Category;

/**
 * 
 * @author Georg Seipler
 */
public class CategorySet extends EntitySet {
  
  public CategorySet() {
    super();
  }

  public boolean add(Category category) {
    return set.add(category);
  }

  public int find(Category category) {
    return set.find(category);
  }
  
  public Category get(int index) {
    return (Category) set.get(index);
  }
  
  public Category get(String id) {
    Category result = null;
    if (id != null) {
      for (int i = 0; i < size(); i++) {
        Category category = get(i);
        if (id.equals(category.getId())) {
          result = category;
          break;
        }
      }
    }
    return result;
  }

  public boolean remove(Category category) {
    return set.remove(category);
  }
  
  public void update(Category category) {
    set.update(category);
  }

}
