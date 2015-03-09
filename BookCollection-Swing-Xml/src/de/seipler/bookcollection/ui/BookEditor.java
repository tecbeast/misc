package de.seipler.bookcollection.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.CompoundBorder;

import de.seipler.bookcollection.entity.Author;
import de.seipler.bookcollection.entity.Book;
import de.seipler.bookcollection.entity.Series;
import de.seipler.util.swing.layout.XmlLayout;
import de.seipler.util.swing.layout.XmlLayoutFactory;

/**
 * 
 * @author Georg Seipler
 */
public class BookEditor extends JComponent {
  
  private JDialog dialog;
  
  private Book book;
  
  private JTextField isbnField;
  private JComboBox publisherCombo;
  private JTextField titleField;
  private JTextField subtitleField;
  private JList authorList;
  private JComboBox authorCombo;
  private JButton addAuthorButton;
  private JComboBox seriesCombo;
  private JTextField bookNumberField;
  private JTextField booksTotalField;
  private JComboBox categoryCombo;
  private JComboBox genreCombo;
  private JComboBox statusCombo;
  private JComboBox languageCombo;
  private JComboBox editionCombo;
  private JComboBox ratingCombo;
  private JTextField pagesField;
  private JTextField printingDateField;
  private JTextField priceField;
  private JTextArea notesArea;
  
  private JButton saveBookButton;
  private JButton newBookButton;
  private JButton helpBookButton;
  
  public BookEditor(final BookCollectionUi ui) {
    
    this.book = new Book();
    
    XmlLayoutFactory xmlLayoutFactory = new XmlLayoutFactory();

    // GENERAL SETUP
    
    XmlLayout bookLayout = xmlLayoutFactory.create(this, "bookLayout");
    
    // ISBN SECTION
    
    JPanel isbnComponent = new JPanel();
    isbnComponent.setBorder(createBorder("ISBN"));
    bookLayout.add(isbnComponent, "isbnComponent");

    this.isbnField = new JTextField();
    bookLayout.add(isbnField, "isbnField");
    
    // PUBLISHER SECTION
    
    JPanel publisherComponent = new JPanel();
    publisherComponent.setBorder(createBorder("Publisher"));
    bookLayout.add(publisherComponent, "publisherComponent");

    this.publisherCombo = new JComboBox();
    publisherCombo.setModel(ui.getEntityCache().getAllPublishers().getListModel(true));
    publisherCombo.setEditable(false);
    bookLayout.add(publisherCombo, "publisherCombo");
  
    // TITLE SECTION
    
    JPanel titleComponent = new JPanel();
    titleComponent.setBorder(createBorder("Titles"));
    bookLayout.add(titleComponent, "titleComponent");
    
    JLabel titleLabel = new JLabel("Title");
    bookLayout.add(titleLabel, "titleLabel");
    
    this.titleField = new JTextField();
    bookLayout.add(titleField, "titleField");
    
    JLabel subtitleLabel = new JLabel("Subtitle");
    bookLayout.add(subtitleLabel, "subtitleLabel");

    this.subtitleField = new JTextField();
    bookLayout.add(subtitleField, "subtitleField");

    // AUTHOR SECTION
    
    JPanel authorComponent = new JPanel();
    authorComponent.setBorder(createBorder("Authors"));
    bookLayout.add(authorComponent, "authorComponent");

    this.authorList = new JList();
    authorList.setModel(book.getAuthors().getListModel(false));
    authorList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    authorList.setVisibleRowCount(3);
    JScrollPane authorListScrollPane = new JScrollPane(authorList);
    bookLayout.add(authorListScrollPane, "authorListScrollPane");

    this.authorCombo = new JComboBox();
    authorCombo.setModel(ui.getEntityCache().getAllAuthors().getListModel(true));
    authorCombo.setEditable(false);
    bookLayout.add(authorCombo, "authorCombo");

    this.addAuthorButton = new JButton("Add");
    bookLayout.add(addAuthorButton, "addAuthorButton");

    // SERIES SECTION
    
    JPanel seriesComponent = new JPanel();
    seriesComponent.setBorder(createBorder("Series"));
    bookLayout.add(seriesComponent, "seriesComponent");
    
    JLabel seriesLabel = new JLabel("Series");
    bookLayout.add(seriesLabel, "seriesLabel");
    
    this.seriesCombo = new JComboBox();
    seriesCombo.setModel(ui.getEntityCache().getAllSeries().getListModel(true)); 
    seriesCombo.setEditable(false);
    bookLayout.add(seriesCombo, "seriesCombo");
    
    JLabel bookNumberLabel = new JLabel("Book Number");
    bookLayout.add(bookNumberLabel, "bookNumberLabel");
    
    this.bookNumberField = new JTextField(3);
    bookLayout.add(bookNumberField, "bookNumberField");
    
    JLabel booksTotalLabel1 = new JLabel("of");
    bookLayout.add(booksTotalLabel1, "booksTotalLabel1");

    this.booksTotalField = new JTextField(3);
    booksTotalField.setEditable(false);
    bookLayout.add(booksTotalField, "booksTotalField");
    
    JLabel booksTotalLabel2 = new JLabel("Books Total");
    bookLayout.add(booksTotalLabel2, "booksTotalLabel2");

    // CATEGORY SECTION
    
    JPanel categoryComponent = new JPanel();
    categoryComponent.setBorder(createBorder("Category"));
    bookLayout.add(categoryComponent, "categoryComponent");
    
    this.categoryCombo = new JComboBox();
    categoryCombo.setModel(ui.getEntityCache().getAllCategories().getListModel(true));
    categoryCombo.setEditable(true);
    bookLayout.add(categoryCombo, "categoryCombo");
    
    // GENRE SECTION
    
    JPanel genreComponent = new JPanel();
    genreComponent.setBorder(createBorder("Genre"));
    bookLayout.add(genreComponent, "genreComponent");
    
    this.genreCombo = new JComboBox();
    genreCombo.setModel(ui.getEntityCache().getAllGenres().getListModel(true));
    genreCombo.setEditable(true);
    bookLayout.add(genreCombo, "genreCombo");
    
    // STATUS SECTION
    
    JPanel statusComponent = new JPanel();
    statusComponent.setBorder(createBorder("Status"));
    bookLayout.add(statusComponent, "statusComponent");
    
    this.statusCombo = new JComboBox();
    statusCombo.setModel(ui.getEntityCache().getAllStatus().getListModel(true));
    statusCombo.setEditable(true);
    bookLayout.add(statusCombo, "statusCombo");
                
    // LANGUAGE SECTION
    
    JPanel languageComponent = new JPanel();
    languageComponent.setBorder(createBorder("Language"));
    bookLayout.add(languageComponent, "languageComponent");
    
    this.languageCombo = new JComboBox();
    languageCombo.setModel(ui.getEntityCache().getAllLanguages().getListModel(true));
    languageCombo.setEditable(true);
    bookLayout.add(languageCombo, "languageCombo");
    
    // EDITION SECTION

    JPanel editionComponent = new JPanel();
    editionComponent.setBorder(createBorder("Edition"));
    bookLayout.add(editionComponent, "editionComponent");
    
    this.editionCombo = new JComboBox();
    editionCombo.setModel(ui.getEntityCache().getAllEditions().getListModel(true));
    editionCombo.setEditable(true);
    bookLayout.add(editionCombo, "editionCombo");

    // RATING SECTION
    
    JPanel ratingComponent = new JPanel();
    ratingComponent.setBorder(createBorder("Rating"));
    bookLayout.add(ratingComponent, "ratingComponent");
    
    this.ratingCombo = new JComboBox();
    ratingCombo.setModel(ui.getEntityCache().getAllRatings().getListModel(true));
    ratingCombo.setEditable(true);
    bookLayout.add(ratingCombo, "ratingCombo");
                
    // PAGES SECTION
    
    JPanel pagesComponent = new JPanel();
    pagesComponent.setBorder(createBorder("Pages"));
    bookLayout.add(pagesComponent, "pagesComponent");
    
    this.pagesField = new JTextField();
    bookLayout.add(pagesField, "pagesField");

    // PRINTING DATE SECTION
    
    JPanel printingDateComponent = new JPanel();
    printingDateComponent.setBorder(createBorder("Printing Date"));
    bookLayout.add(printingDateComponent, "printingDateComponent");
    
    this.printingDateField = new JTextField();
    bookLayout.add(printingDateField, "printingDateField");
        
    // PRICE SECTION
    
    JPanel priceComponent = new JPanel();
    priceComponent.setBorder(createBorder("Price"));
    bookLayout.add(priceComponent, "priceComponent");
    
    this.priceField = new JTextField();
    bookLayout.add(priceField, "priceField");
        
    // IMAGE SECTION
    
    ImagePanel imageComponent = new ImagePanel();
    imageComponent.setBorder(createBorder("Image"));
    try {
      imageComponent.loadImage("/data/covers/liveship_traders_1.jpg");
    } catch (Exception ignored) {
    }
    bookLayout.add(imageComponent, "imageComponent");
        
    // NOTE SECTION
    
    JPanel notesComponent = new JPanel() {
      public Dimension getPreferredSize() {
        return getMinimumSize();
      }
    };
    notesComponent.setBorder(createBorder("Notes"));
    bookLayout.add(notesComponent, "notesComponent");

    this.notesArea = new JTextArea();
    notesArea.setLineWrap(true);
    notesArea.setWrapStyleWord(true);
    JScrollPane notesScrollPane = new JScrollPane(notesArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    bookLayout.add(notesScrollPane, "notesScrollPane");
    
    // BUTTON SECTION
    
    this.saveBookButton = new JButton("Save");
    saveBookButton.setToolTipText("Save this Book");
    bookLayout.add(saveBookButton, "saveBookButton");
    this.newBookButton = new JButton("New");
    newBookButton.setToolTipText("Edit a new Book");
    bookLayout.add(newBookButton, "newBookButton");
    this.helpBookButton = new JButton("Help");
    helpBookButton.setToolTipText("Get help");
    bookLayout.add(helpBookButton, "helpBookButton");

    // LAYOUT SECTION
    
    xmlLayoutFactory.read(getClass().getResourceAsStream("/layouts/bookTabLayout.xml"));

    // LISTENER SECTION
    
    addAuthorButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        Author author = (Author) authorCombo.getSelectedItem();
        if (author != null) {
          book.getAuthors().add(author);
          authorList.setSelectedValue(author, true);
          authorCombo.setSelectedIndex(0);
        }
      }
    });
    
    seriesCombo.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        Series series = (Series) seriesCombo.getSelectedItem();
        if (series != null) {
          booksTotalField.setText(Integer.toString(series.getBooksTotal()));
        } else {
          booksTotalField.setText("");
        }
      }
    });
    
    imageComponent.addMouseListener(new MouseAdapter() {
      public void mouseReleased(MouseEvent e) {
        if ((dialog == null) && (e.getClickCount() > 1)) {
          dialog = new JDialog(ui, "Image (original size)");
          dialog.getContentPane().setLayout(new BorderLayout());
          ImagePanel imageComponent = new ImagePanel();
          try {
            imageComponent.loadImage("/data/covers/liveship_traders_1.jpg");
          } catch (Exception ignored) {
          }
          dialog.getContentPane().add(imageComponent, BorderLayout.CENTER);
          dialog.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
              dialog = null;
            }
          });
          dialog.pack();
          dialog.setVisible(true);
        }
      }
    });
  
  }

  private CompoundBorder createBorder(String title) {
    return BorderFactory.createCompoundBorder(
      BorderFactory.createTitledBorder(title),
      BorderFactory.createEmptyBorder(0, 2, 2, 2)
    );
  }
  
}
