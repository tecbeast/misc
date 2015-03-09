package de.seipler.bookcollection.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import de.seipler.bookcollection.Author;
import de.seipler.bookcollection.AuthorSetSorted;
import de.seipler.bookcollection.Category;
import de.seipler.bookcollection.CategorySetSorted;
import de.seipler.bookcollection.Edition;
import de.seipler.bookcollection.EditionSetSorted;
import de.seipler.bookcollection.Genre;
import de.seipler.bookcollection.GenreSetSorted;
import de.seipler.bookcollection.Language;
import de.seipler.bookcollection.LanguageSetSorted;
import de.seipler.bookcollection.Publisher;
import de.seipler.bookcollection.PublisherSetSorted;
import de.seipler.bookcollection.Series;
import de.seipler.bookcollection.SeriesSetSorted;

/**
 * 
 * @author Georg Seipler
 */
public class DbAdapter {

  private DbConfiguration dbConfiguration;  
  private Connection connection;
  
  private PreparedStatement allAuthorsQuery;
  private PreparedStatement allSeriesQuery;
  private PreparedStatement allPublishersQuery;
  private PreparedStatement allCategoriesQuery;
  private PreparedStatement allGenresQuery;
  private PreparedStatement allEditionsQuery;
  private PreparedStatement allLanguagesQuery;

  public DbAdapter(DbConfiguration dbConfiguration) {
    this.dbConfiguration = dbConfiguration;
  }

  public void setConnection(Connection connection) {
    this.connection = connection;
  }
  
  public void initialize() throws SQLException {
    if (connection == null) {
      try {
        Class.forName(dbConfiguration.getJdbcDriverClass());
      } catch (ClassNotFoundException cnfe) {
        throw new SQLException("JDBCDriver Class not found");
      }  
      setConnection(DriverManager.getConnection(dbConfiguration.getUrl(), dbConfiguration.getProperties()));
    }
    this.allAuthorsQuery = connection.prepareStatement(
      "SELECT * FROM authors;"
    );
    this.allSeriesQuery = connection.prepareStatement(
      "SELECT * FROM series;"
    );
    this.allPublishersQuery = connection.prepareStatement(
      "SELECT * FROM publishers;"
    );
    this.allCategoriesQuery = connection.prepareStatement(
      "SELECT * FROM categories;"
    );
    this.allGenresQuery = connection.prepareStatement(
      "SELECT * FROM genres;"
    );
    this.allEditionsQuery = connection.prepareStatement(
      "SELECT * FROM editions;"
    );
    this.allLanguagesQuery = connection.prepareStatement(
      "SELECT * FROM languages;"
    );
  }

  public AuthorSetSorted queryAllAuthors() throws SQLException {
    AuthorSetSorted allAuthors = new AuthorSetSorted();
    ResultSet resultSet = allAuthorsQuery.executeQuery();
    while (resultSet.next()) {
      Author author = new Author(resultSet.getInt(1));
      author.setName(resultSet.getString(2));
      author.setSurname(resultSet.getString(3));
      author.setTitle(resultSet.getString(4));
      String sex = resultSet.getString(5);
      if ("F".equals(sex)) {
        author.setSex(Author.SEX_FEMALE);
      } else if ("M".equals(sex)) {
        author.setSex(Author.SEX_MALE);
      } else if ("N".equals(sex)) {
        author.setSex(Author.SEX_NO_PERSON);
      }
      allAuthors.add(author);
    }
    return allAuthors;
  }

  public PublisherSetSorted queryAllPublishers() throws SQLException {
    PublisherSetSorted allPublishers = new PublisherSetSorted();
    ResultSet resultSet = allPublishersQuery.executeQuery();
    while (resultSet.next()) {
      Publisher publisher = new Publisher(resultSet.getInt(1));
      publisher.setName(resultSet.getString(2));
      allPublishers.add(publisher);
    }
    return allPublishers;
  }

  public CategorySetSorted queryAllCategories() throws SQLException {
    CategorySetSorted allCategories = new CategorySetSorted();
    ResultSet resultSet = allCategoriesQuery.executeQuery();
    while (resultSet.next()) {
      Category category = new Category(resultSet.getInt(1));
      category.setName(resultSet.getString(2));
      allCategories.add(category);
    }
    return allCategories;
  }

  public GenreSetSorted queryAllGenres() throws SQLException {
    GenreSetSorted allGenres = new GenreSetSorted();
    ResultSet resultSet = allGenresQuery.executeQuery();
    while (resultSet.next()) {
      Genre genre = new Genre(resultSet.getInt(1));
      genre.setName(resultSet.getString(2));
      allGenres.add(genre);
    }
    return allGenres;
  }

  public EditionSetSorted queryAllEditions() throws SQLException {
    EditionSetSorted allEditions = new EditionSetSorted();
    ResultSet resultSet = allEditionsQuery.executeQuery();
    while (resultSet.next()) {
      Edition edition = new Edition(resultSet.getInt(1));
      edition.setName(resultSet.getString(2));
      allEditions.add(edition);
    }
    return allEditions;
  }

  public LanguageSetSorted queryAllLanguages() throws SQLException {
    LanguageSetSorted allLanguages = new LanguageSetSorted();
    ResultSet resultSet = allLanguagesQuery.executeQuery();
    while (resultSet.next()) {
      Language language = new Language(resultSet.getInt(1));
      language.setName(resultSet.getString(2));
      allLanguages.add(language);
    }
    return allLanguages;
  }

  public SeriesSetSorted queryAllSeries() throws SQLException {
    SeriesSetSorted allSeries = new SeriesSetSorted();
    ResultSet resultSet = allSeriesQuery.executeQuery();
    while (resultSet.next()) {
      Series series = new Series(resultSet.getInt(1));
      series.setName(resultSet.getString(2));
      series.setBooksTotal(resultSet.getInt(3));
      allSeries.add(series);
    }
    return allSeries;
  }

  public void close() throws SQLException {
    if (connection != null) {
      connection.close();
    }
  }
  
}
