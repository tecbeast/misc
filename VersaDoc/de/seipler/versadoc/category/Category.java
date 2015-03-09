package de.seipler.versadoc.category;

import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import de.seipler.versadoc.descriptor.Descriptor;

/**
 * @author Georg Seipler
 */
public class Category {

  private String name;
  private Class type;
  private Map getterByDescriptor;
  private Comparator comparator;

  /**
   * 
   */
  protected Category(String name, Class type) {
    this.name = name;
    this.type = type;
    this.getterByDescriptor = new HashMap();
    
    this.comparator = new Comparator() {
			public int compare(Object o1, Object o2) {
				Method method1 = getDescriptorGetter(o1.getClass());
				if (method1 == null) {
					throw new IllegalArgumentException("Parameter o1 not comparable - no getter for Category registered.");
				}
				Method method2 = getDescriptorGetter(o2.getClass());
				if (method2 == null) {
					throw new IllegalArgumentException("Parameter o2 not comparable - no getter for Category registered.");
				}
				try {
          return ((Comparable) method1.invoke(o1, null)).compareTo((Comparable) method2.invoke(o2, null));
        // IllegalArgumentException, IllegalAccessException, InvocationTargetException, ClassCastException
        } catch (Exception e) {
          e.printStackTrace();
          return 0;
        }
			}
    };
  }
    
	/**
	 * 
	 */
	protected void addDescriptorGetter(Method getter) {
		this.getterByDescriptor.put(getter.getDeclaringClass(), getter);
	}

	/**
	 * 
	 */
	public Object getDescriptorValue(Descriptor descriptor) {
		Method method = getDescriptorGetter(descriptor.getClass());
		try {      
      return method.invoke(descriptor, null);
    // IllegalArgumentException, IllegalAccessException, InvocationTargetException
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
	}

  /**
   *
   */
  public String getName() {
    return this.name;
  }

	/**
	 * 
	 */
	protected Method getDescriptorGetter(Class provider) {
		return (Method) this.getterByDescriptor.get(provider);
	}
  
  /**
   * 
   */
  public Class getType() {
  	return this.type;
  }

  /**
   *
   */
  public Comparator getComparator() {
    return this.comparator;
  }

}
