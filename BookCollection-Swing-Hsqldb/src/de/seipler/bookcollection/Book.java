package de.seipler.bookcollection;

import java.io.File;
import java.util.Date;

/**
 * 
 * @author Georg Seipler
 */
public class Book extends Entity {
  
  private String isbn;
  private Publisher publisher;
  private String title;
  private String subTitle;
  private AuthorSetSorted authors;
  private Series series;
  private int bookNumber;
  private String category;
  private String genre;
  private String language;
  private String edition;
  private int pages;
  private Date printingDate;
  private String notes;
  private File coverFile;
  
  public Book() {
    this(ID_UNDEFINED);
  }

  public Book(int id) {
    super(id);
    setAuthors(new AuthorSetSorted());
  }

  public AuthorSetSorted getAuthors() {
    return this.authors;
  }

  public int getBookNumber() {
    return this.bookNumber;
  }

  public String getCategory() {
    return this.category;
  }

  public Date getPrintingDate() {
    return this.printingDate;
  }

  public String getEdition() {
    return this.edition;
  }

  public String getGenre() {
    return this.genre;
  }

  public String getIsbn() {
    return this.isbn;
  }

  public String getLanguage() {
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

  public void setAuthors(AuthorSetSorted authors) {
    this.authors = authors;
  }

  public void setBookNumber(int bookNumber) {
    this.bookNumber = bookNumber;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public void setPrintingDate(Date date) {
    this.printingDate = date;
  }

  public void setEdition(String edition) {
    this.edition = edition;
  }

  public void setGenre(String genre) {
    this.genre = genre;
  }

  public void setIsbn(String isbn) {
    this.isbn = isbn;
  }

  public void setLanguage(String language) {
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

}
