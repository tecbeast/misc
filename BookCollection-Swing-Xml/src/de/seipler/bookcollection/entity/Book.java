package de.seipler.bookcollection.entity;

import java.io.File;
import java.util.Date;

import de.seipler.bookcollection.model.AuthorSet;
import de.seipler.bookcollection.model.EntityCache;
import de.seipler.util.collection.SortedArraySet;

/**
 * 
 * @author Georg Seipler
 */
public class Book extends PrimaryEntity implements Comparable {
  
  private String isbn;
  private Publisher publisher;
  private String title;
  private String subTitle;
  private AuthorSet authors;
  private Series series;
  private int bookNumber;
  private Category category;
  private Genre genre;
  private Language language;
  private Edition edition;
  private int pages;
  private Date printingDate;
  private String notes;
  private File coverFile;

  public Book() {
    setAuthors(new AuthorSet());
  }

  public AuthorSet getAuthors() {
    return this.authors;
  }

  public int getBookNumber() {
    return this.bookNumber;
  }

  public Category getCategory() {
    return this.category;
  }

  public Date getPrintingDate() {
    return this.printingDate;
  }

  public Edition getEdition() {
    return this.edition;
  }

  public Genre getGenre() {
    return this.genre;
  }

  public String getIsbn() {
    return this.isbn;
  }

  public Language getLanguage() {
    return this.language;
  }

  public int getPages() {
    return this.pages;
  }

  public Publisher getPublisher() {
    return this.publisher;
  }

  public Series getSeries() {
    return this.series;
  }

  public String getSubTitle() {
    return this.subTitle;
  }

  public String getTitle() {
    return this.title;
  }

  public void setAuthors(AuthorSet authors) {
    this.authors = authors;
  }

  public void setBookNumber(int bookNumber) {
    this.bookNumber = bookNumber;
  }

  public void setCategory(Category category) {
    this.category = category;
  }

  public void setPrintingDate(Date date) {
    this.printingDate = date;
  }

  public void setEdition(Edition edition) {
    this.edition = edition;
  }

  public void setGenre(Genre genre) {
    this.genre = genre;
  }

  public void setIsbn(String isbn) {
    this.isbn = isbn;
  }

  public void setLanguage(Language language) {
    this.language = language;
  }

  public void setPages(int pages) {
    this.pages = pages;
  }

  public void setPublisher(Publisher publisher) {
    this.publisher = publisher;
  }

  public void setSeries(Series series) {
    this.series = series;
  }

  public void setSubTitle(String subTitle) {
    this.subTitle = subTitle;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getNotes() {
    return this.notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }

  public File getCoverFile() {
    return this.coverFile;
  }

  public void setCoverFile(File file) {
    this.coverFile = file;
  }

  public boolean validate(EntityCache cache) {
    
    boolean valid = true;
    
    boolean authorsValid = true;
    AuthorSet validAuthors = new AuthorSet();
    for (int i = 0; i < getAuthors().size(); i++) {
      Author validAuthor = cache.getAllAuthors().get(getAuthors().get(i).getId());
      if (validAuthor != null) {
        validAuthors.add(validAuthor);
        validAuthor.addParent(this);
      } else {
        authorsValid = false;
      }
    }
    if (authorsValid) {
      setAuthors(validAuthors);
    } else {
      valid = false;
    }
    
    if (getCategory() != null) {
      Category validCategory = cache.getAllCategories().get(getCategory().getId());
      if (validCategory != null) {
        setCategory(validCategory);
        validCategory.addParent(this);
      } else {
        valid = false;
      }
    }
    
    if (getEdition() != null) {
      Edition validEdition = cache.getAllEditions().get(getEdition().getId());
      if (validEdition != null) {
        setEdition(validEdition);
        validEdition.addParent(this);
      } else {
        valid = false;
      }
    }

    if (getGenre() != null) {
      Genre validGenre = cache.getAllGenres().get(getGenre().getId());
      if (validGenre != null) {
        setGenre(validGenre);
        validGenre.addParent(this);
      } else {
        valid = false;
      }
    }

    if (getLanguage() != null) {
      Language validLanguage = cache.getAllLanguages().get(getLanguage().getId());
      if (validLanguage != null) {
        setLanguage(validLanguage);
        validLanguage.addParent(this);
      } else {
        valid = false;
      }
    }

    if (getPublisher() != null) {
      Publisher validPublisher = cache.getAllPublishers().get(getPublisher().getId());
      if (validPublisher != null) {
        setPublisher(validPublisher);
        validPublisher.addParent(this);   
      } else {
        valid = false;
      }
    }
    
    if (getSeries() != null) {
      Series validSeries = cache.getAllSeries().get(getSeries().getId());
      if (validSeries != null) {
        setSeries(validSeries);
        validSeries.addParent(this);
      } else {
        valid = false;
      }
    }
    
    return valid;
    
  }

  public String getDescription() {
    return "Book: " + toString();
  }
  
  public Entity[] getChildren() {
    SortedArraySet children = new SortedArraySet();
    if (series != null) {
      children.add(series);    
    }
    if (authors != null) {
      for (int i = 0; i < authors.size(); i++) {
        children.add(authors.get(i));
      }
    }
    Entity[] result = new Entity[children.size()];
    return (Entity[]) children.toArray(result);
  }

  public String toString() {
    return getTitle();
  }
  
  /**
   * @see java.lang.Comparable#compareTo(java.lang.Object)
   */
  public int compareTo(Object obj) {
    return getId().compareTo(((Book) obj).getId());
  }
  
  /**
   * @see java.lang.Object#equals(java.lang.Object)
   */
  public boolean equals(Object obj) {
    return getId().equals(((Book) obj).getId());
  }
  
}
