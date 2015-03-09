package de.seipler.bookcollection.model;

import de.seipler.bookcollection.entity.Language;

/**
 * 
 * @author Georg Seipler
 */
public class LanguageSet extends EntitySet {
  
  public LanguageSet() {
    super();
  }
  
  public boolean add(Language language) {
    return set.add(language);
  }

  public int find(Language language) {
    return set.find(language);
  }
  
  public Language get(int index) {
    return (Language) set.get(index);
  }

  public Language get(String id) {
    Language result = null;
    if (id != null) {
      for (int i = 0; i < size(); i++) {
        Language language = get(i);
        if (id.equals(language.getId())) {
          result = language;
          break;
        }
      }
    }
    return result;
  }

  public boolean remove(Language language) {
    return set.remove(language);
  }

  public void update(Language language) {
    set.update(language);
  }

}
