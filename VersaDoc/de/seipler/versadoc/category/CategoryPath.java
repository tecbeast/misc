package de.seipler.versadoc.category;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

/**
 * @author Georg Seipler
 */
public class CategoryPath {
	
	private List categoryList;
	private Comparator comparator;
  
  /**
   * 
   */
  public CategoryPath() {
  	this(10);
  }
  
  /**
   * 
   */
  public CategoryPath(int initialCapacity) {
  	this.categoryList = new ArrayList(initialCapacity);
  	
  	// Comparator Chain
  	this.comparator = new Comparator() {
			public int compare(Object o1, Object o2) {
				int result = 0;
				Iterator categoryIterator = categoryList.iterator();
				while (categoryIterator.hasNext()) {
					result = ((Category) categoryIterator.next()).getComparator().compare(o1, o2);
					if (result != 0) { break; }
				}
				return result;
			}
  	};
  }
  
  /**
   * 
   */
  public void addCategory(Category category) {
  	this.categoryList.add(category);
  }
  
	/**
	 * 
	 */
	public Category getCategory(int index) {
		return (Category) this.categoryList.get(index);
	}
	
	/**
	 * 
	 */
	public Comparator getComparator() {
		return this.comparator;
	}

  /**
   * 
   */
  public int size() {
  	return this.categoryList.size();
  }

}
