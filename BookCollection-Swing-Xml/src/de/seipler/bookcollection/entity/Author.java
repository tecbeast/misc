package de.seipler.bookcollection.entity;


/**
 * 
 * @author Georg Seipler
 */
public class Author extends SecondaryEntity implements Comparable {
  
  public static final int SEX_UNDEFINED = 0;
  public static final int SEX_FEMALE = 1;
  public static final int SEX_MALE = 2;
  public static final int SEX_NO_PERSON = 3;
  
  private String name;
  private String surname;
  private String title;
  private int sex;
  
  public Author() {
    setName("");
    setSurname("");
    setTitle("");
    setSex(SEX_UNDEFINED);
  }

  public String getName() {
    return name;
  }

  public int getSex() {
    return this.sex;
  }

  public String getSurname() {
    return surname;
  }

  public String getTitle() {
    return title;
  }
  
  public int compareTo(Object obj) {
    int result = 0;
    if (obj instanceof Author) {
      Author anotherAuthor = (Author) obj;
      result = getName().compareToIgnoreCase(anotherAuthor.getName());
      if (result == 0) {
        if (getSurname() != null) {
          if (anotherAuthor.getSurname() != null) {
            result = getSurname().compareToIgnoreCase(anotherAuthor.getSurname());
          }
        } else {
          if (anotherAuthor.getSurname() != null) {
            result = -1;
          }
        }
        if (result == 0) {
          if (getTitle() != null) {
            if (anotherAuthor.getTitle() != null) {
              result = getTitle().compareToIgnoreCase(anotherAuthor.getTitle());
            }
          } else {
            if (anotherAuthor.getTitle() != null) {
              result = -1;
            }
          }
        }
      }
    }
    return result;
  }

  public boolean equals(Object obj) {
    boolean result = false;
    if (obj instanceof Author) {
      Author anotherAuthor = (Author) obj;
      if (
        (getSex() != anotherAuthor.getSex())
        || ((getName() == null) && (anotherAuthor.getName() != null))
        || !getName().equals(anotherAuthor.getName())
        || ((getSurname() == null) && (anotherAuthor.getSurname() != null))
        || !getSurname().equals(anotherAuthor.getSurname())
        || ((getTitle() == null) && (anotherAuthor.getTitle() != null))
        || !getTitle().equals(anotherAuthor.getTitle())
      ) {
        result = false;
      } else {
        result = true;
      }
    }
    return result;
  }
  
  public void setName(String name) {
    if (name == null) {
      throw new IllegalArgumentException("Parameter name must not be null");
    }
    this.name = name;
  }

  public void setSex(int sex) {
    this.sex = sex;
  }

  public void setSurname(String surname) {
    this.surname = surname;
  }

  public void setTitle(String title) {
    this.title = title;
  }
  
  public String getDescription() {
    return "Author: " + toString();
  }

  public String toString() {
    StringBuffer buffer = new StringBuffer();
    buffer.append(getName());
    if ((getSurname() != null) && (getSurname().length() > 0)) {
      buffer.append(", ");
      buffer.append(getSurname());
    }
    if ((getTitle() != null) && (getTitle().length() > 0)) {
      buffer.append(", ");
      buffer.append(getTitle());
    }
    return buffer.toString();
  }

}
