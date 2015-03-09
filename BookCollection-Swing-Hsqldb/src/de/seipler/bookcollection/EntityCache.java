package de.seipler.bookcollection;

import java.sql.SQLException;

import de.seipler.bookcollection.db.DbAdapter;

/**
 * 
 * @author Georg Seipler
 */
public class EntityCache {
  
  private DbAdapter dbAdapter;
  
  private AuthorSetSorted allAuthors;
  private PublisherSetSorted allPublishers;
  private SeriesSetSorted allSeries;
  private CategorySetSorted allCategories;
  private GenreSetSorted allGenres;
  private EditionSetSorted allEditions;
  private LanguageSetSorted allLanguages;
  
  public EntityCache(DbAdapter dbAdapter) {
    this.dbAdapter = dbAdapter;
  }
  
  public void initialize() throws SQLException {
    this.allAuthors = dbAdapter.queryAllAuthors();    
    this.allPublishers = dbAdapter.queryAllPublishers();
    this.allSeries = dbAdapter.queryAllSeries();
    this.allCategories = dbAdapter.queryAllCategories();
    this.allGenres = dbAdapter.queryAllGenres();
    this.allEditions = dbAdapter.queryAllEditions();
    this.allLanguages = dbAdapter.queryAllLanguages();
  }

  public AuthorSetSorted getAllAuthors() {
    return this.allAuthors;
  }
  
  public PublisherSetSorted getAllPublishers() {
    return this.allPublishers;
  }

  public SeriesSetSorted getAllSeries() {
    return this.allSeries;
  }

  public DbAdapter getDbAdapter() {
    return this.dbAdapter;
  }

  public CategorySetSorted getAllCategories() {
    return this.allCategories;
  }

  public EditionSetSorted getAllEditions() {
    return this.allEditions;
  }

  public GenreSetSorted getAllGenres() {
    return this.allGenres;
  }

  public LanguageSetSorted getAllLanguages() {
    return this.allLanguages;
  }

}
