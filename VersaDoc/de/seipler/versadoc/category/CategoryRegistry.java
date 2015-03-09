package de.seipler.versadoc.category;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Georg Seipler
 */
public class CategoryRegistry {

	private Map categoryByName;
	
	/**
	 * 
	 */
	public CategoryRegistry() {
		this.categoryByName = new HashMap();
	}
	
	/**
	 * 
	 */
	public void registerCategory(String name, Class type, Method getter) {
		Category registered = (Category) categoryByName.get(name);
		if (registered == null) {
			registered = new Category(name, type);
			categoryByName.put(name, registered);
		} else if (!registered.getType().equals(type)) {
			throw new CategoryRegistryException("Category (" + name + ") already registered for a different type (" + type.getName() + ").");
		}
		registered.addDescriptorGetter(getter);
	}

	/**
	 * 
	 */
	public Category getCategory(String name) {
		return (Category) this.categoryByName.get(name);
	}

}
