package de.seipler.bookcollection.ui;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.MutableComboBoxModel;
import javax.swing.border.CompoundBorder;

import de.seipler.bookcollection.Book;
import de.seipler.bookcollection.EntityCache;
import de.seipler.util.swing.layout.LayoutWrapper;
import de.seipler.util.swing.layout.LayoutWrapperFactory;


/**
 * 
 * @author Georg Seipler
 */
public class BookTab {
  
  private JTabbedPane tabbedPane;
  private UiState uiState;
  private EntityCache cache;
  
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
  private JComboBox languageCombo;
  private JComboBox editionCombo;
  private JTextField pagesField;
  private JTextField printingDateField;
  private JTextArea notesArea;
  
  private JButton saveBookButton;
  private JButton newBookButton;
  private JButton helpBookButton;
  
  public BookTab(JTabbedPane tabbedPane, UiState uiState, EntityCache cache) {
    
    this.tabbedPane = tabbedPane;
    this.uiState = uiState;
    this.cache = cache;
    this.book = new Book();

    // GENERAL SETUP
    
    JPanel bookComponent = new JPanel();
    LayoutWrapperFactory layoutFactory = new LayoutWrapperFactory();
    bookComponent.setLayout(layoutFactory.create(LayoutWrapper.GRID_BAG_LAYOUT, "bookLayout"));
    tabbedPane.addTab("Books", bookComponent);

    // ISBN SECTION
    
    JPanel isbnComponent = new JPanel();
    isbnComponent.setBorder(createBorder("ISBN"));
    isbnComponent.setLayout(new BoxLayout(isbnComponent, BoxLayout.X_AXIS));
    bookComponent.add(isbnComponent, "isbnComponent");

    this.isbnField = new JTextField();
    isbnComponent.add(isbnField);
    
    // PUBLISHER SECTION
    
    JPanel publisherComponent = new JPanel();
    publisherComponent.setBorder(createBorder("Publisher"));
    publisherComponent.setLayout(new BoxLayout(publisherComponent, BoxLayout.X_AXIS));
    bookComponent.add(publisherComponent, "publisherComponent");

    this.publisherCombo = new JComboBox();
    publisherCombo.setEditable(false);
    publisherComponent.add(publisherCombo);
  
    // TITLE SECTION
    
    JPanel titleComponent = new JPanel();
    titleComponent.setBorder(createBorder("Titles"));
    titleComponent.setLayout(layoutFactory.create(LayoutWrapper.GRID_BAG_LAYOUT, "titleLayout"));
    bookComponent.add(titleComponent, "titleComponent");
    
    JLabel titleLabel = new JLabel("Title");
    titleComponent.add(titleLabel, "titleLabel");
    
    this.titleField = new JTextField();
    titleComponent.add(titleField, "titleField");
    
    JLabel subtitleLabel = new JLabel("Subtitle");
    titleComponent.add(subtitleLabel, "subtitleLabel");

    this.subtitleField = new JTextField();
    titleComponent.add(subtitleField, "subtitleField");

    // AUTHOR SECTION
    
    JPanel authorComponent = new JPanel();
    authorComponent.setBorder(createBorder("Authors"));
    authorComponent.setLayout(layoutFactory.create(LayoutWrapper.GRID_BAG_LAYOUT, "authorLayout"));
    bookComponent.add(authorComponent, "authorComponent");
    
    this.authorList = new JList();
    authorList.setVisibleRowCount(3);
    JScrollPane authorListScrollPane = new JScrollPane(authorList);
    authorComponent.add(authorListScrollPane, "authorListScrollPane");

    this.authorCombo = new JComboBox();
    authorCombo.setEditable(false);
    authorComponent.add(authorCombo, "authorCombo");

    this.addAuthorButton = new JButton("Add");
    authorComponent.add(addAuthorButton, "addAuthorButton");

    // SERIES SECTION
    
    JPanel seriesComponent = new JPanel();
    seriesComponent.setBorder(createBorder("Series"));
    seriesComponent.setLayout(layoutFactory.create(LayoutWrapper.GRID_BAG_LAYOUT, "seriesLayout"));
    bookComponent.add(seriesComponent, "seriesComponent");
    
    JLabel seriesLabel = new JLabel("Series");
    seriesComponent.add(seriesLabel, "seriesLabel");
    
    this.seriesCombo = new JComboBox();
    seriesCombo.setEditable(false);
    seriesComponent.add(seriesCombo, "seriesCombo");
    
    JLabel bookNumberLabel = new JLabel("Book Number");
    seriesComponent.add(bookNumberLabel, "bookNumberLabel");
    
    this.bookNumberField = new JTextField(3);
    seriesComponent.add(bookNumberField, "bookNumberField");
    
    JLabel booksTotalLabel1 = new JLabel("of");
    seriesComponent.add(booksTotalLabel1, "booksTotalLabel1");

    this.booksTotalField = new JTextField(3);
    booksTotalField.setEditable(false);
    seriesComponent.add(booksTotalField, "booksTotalField");
    
    JLabel booksTotalLabel2 = new JLabel("Books Total");
    seriesComponent.add(booksTotalLabel2, "booksTotalLabel2");

    // DETAILS GRID

    JPanel detailsComponent = new JPanel();
    detailsComponent.setLayout(new GridLayout(3, 2));
    bookComponent.add(detailsComponent, "detailsComponent");

    // CATEGORY SECTION
    
    JPanel categoryComponent = new JPanel();
    categoryComponent.setBorder(createBorder("Category"));
    categoryComponent.setLayout(new BoxLayout(categoryComponent, BoxLayout.X_AXIS));
    detailsComponent.add(categoryComponent);
    
    this.categoryCombo = new JComboBox();
    categoryCombo.setEditable(true);
    categoryComponent.add(categoryCombo);
    
    // GENRE SECTION
    
    JPanel genreComponent = new JPanel();
    genreComponent.setBorder(createBorder("Genre"));
    genreComponent.setLayout(new BoxLayout(genreComponent, BoxLayout.X_AXIS));
    detailsComponent.add(genreComponent);
    
    this.genreCombo = new JComboBox();
    genreCombo.setEditable(true);
    genreComponent.add(genreCombo);
                
    // LANGUAGE SECTION
    
    JPanel languageComponent = new JPanel();
    languageComponent.setBorder(createBorder("Language"));
    languageComponent.setLayout(new BoxLayout(languageComponent, BoxLayout.X_AXIS));
    detailsComponent.add(languageComponent);
    
    this.languageCombo = new JComboBox();
    languageCombo.setEditable(true);
    languageComponent.add(languageCombo);
    
    // EDITION SECTION

    JPanel editionComponent = new JPanel();
    editionComponent.setBorder(createBorder("Edition"));
    editionComponent.setLayout(new BoxLayout(editionComponent, BoxLayout.X_AXIS));
    detailsComponent.add(editionComponent);
    
    this.editionCombo = new JComboBox();
    editionCombo.setEditable(true);
    editionComponent.add(editionCombo);

    // PAGES SECTION
    
    JPanel pagesComponent = new JPanel();
    pagesComponent.setBorder(createBorder("Pages"));
    pagesComponent.setLayout(new BoxLayout(pagesComponent, BoxLayout.X_AXIS));
    detailsComponent.add(pagesComponent);
    
    this.pagesField = new JTextField();
    pagesComponent.add(pagesField);

    // PRINTING DATE SECTION
    
    JPanel printingDateComponent = new JPanel();
    printingDateComponent.setBorder(createBorder("Printing Date"));
    printingDateComponent.setLayout(new BoxLayout(printingDateComponent, BoxLayout.X_AXIS));
    detailsComponent.add(printingDateComponent);
    
    this.printingDateField = new JTextField();
    printingDateComponent.add(printingDateField);
        
    // IMAGE SECTION
    
    ImagePanel imageComponent = new ImagePanel();
    imageComponent.setBorder(createBorder("Image"));
    imageComponent.setPreferredSize(new Dimension(200, 200));
    try {
      imageComponent.loadImage("/covers/liveship_traders_1.jpg");
    } catch (Exception ignored) {
    }
    bookComponent.add(imageComponent, "imageComponent");
        
    // NOTE SECTION
    
    JPanel notesComponent = new JPanel() {
      public Dimension getPreferredSize() {
        return getMinimumSize();
      }
    };
    notesComponent.setBorder(createBorder("Notes"));
    notesComponent.setLayout(new BoxLayout(notesComponent, BoxLayout.X_AXIS));
    bookComponent.add(notesComponent, "notesComponent");

    this.notesArea = new JTextArea();
    notesArea.setLineWrap(true);
    notesArea.setWrapStyleWord(true);
    JScrollPane notesScrollPane = new JScrollPane(notesArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    notesComponent.add(notesScrollPane);
    
    // BUTTON SECTION
    
    JPanel buttonComponent = new JPanel();
    buttonComponent.setLayout(new BoxLayout(buttonComponent, BoxLayout.X_AXIS));
    bookComponent.add(buttonComponent, "buttonComponent");

    this.saveBookButton = new JButton("Save");
    saveBookButton.setToolTipText("Save this Book");
    buttonComponent.add(saveBookButton);
    this.newBookButton = new JButton("New");
    newBookButton.setToolTipText("Edit a new Book");
    buttonComponent.add(newBookButton);
    this.helpBookButton = new JButton("Help");
    helpBookButton.setToolTipText("Get help");
    buttonComponent.add(helpBookButton);

    // DATA SECTION

    refreshData();
    
    layoutFactory.readConstraints(getClass().getResourceAsStream("/layouts/bookTabLayout.xml"));
    
    /*

    // POPUP SECTION
    
    Menu popupMenu = new Menu(shell, SWT.POP_UP);
    MenuItem pasteItem = new MenuItem(popupMenu, SWT.PUSH);
    pasteItem.setText("Paste");
    pasteItem.addSelectionListener(new SelectionAdapter() {
      public void widgetSelected(SelectionEvent e) {
        FileTransfer transfer = FileTransfer.getInstance();
        String[] filenames = (String[]) clipboard.getContents(transfer);
        if ((filenames != null) && (filenames.length > 0)) {
          coverImage.dispose();
          try {
            InputStream in = new FileInputStream(filenames[0]);
            coverImage = new Image(Display.getCurrent(), in);
            in.close();
          } catch (IOException ignored) {
          }
          coverPhoto.redraw();
        }
      }
    });
    coverPhoto.setMenu(popupMenu);

    // DND SECTION
    
    DropTarget target = new DropTarget(coverPhoto, DND.DROP_COPY | DND.DROP_MOVE | DND.DROP_DEFAULT);
    target.setTransfer(new Transfer[] { TextTransfer.getInstance(), FileTransfer.getInstance() });
  
    target.addDropListener(new DropTargetAdapter() {
      public void drop(DropTargetEvent event) {
        String[] filenames = (String[]) FileTransfer.getInstance().nativeToJava(event.currentDataType);
        dropCover(filenames);
      }
    });
    
    // DATA SECTION
        
    refreshData();
    verticalSash.setWeights(new int[] { 70, 30 });
    horizontalSash.setWeights(new int[] { 60, 40 });

    noteText.setText("Dies ist eine ziemlich lange Beschreibung, die in mehreren Zeilen dargestellt werden sollte.");
    try {
      InputStream in = getClass().getResourceAsStream("/data/covers/liveship_traders_1.jpg");
      coverImage = new Image(Display.getCurrent(), in);
      in.close();
    } catch (IOException ignored) {
    }

    // LISTENER SECTION
    
    EnterTraverseListener enterTraverseListener = new EnterTraverseListener();
    
    // enter in isbn text traverses focus to publisher combo
    isbnText.addTraverseListener(enterTraverseListener);

    // enter in publisher combo traverses focus to title text
    publisherCombo.addTraverseListener(enterTraverseListener);

    // enter in title traverses focus to subtitle
    titleText.addTraverseListener(enterTraverseListener);

    // enter in subtitle traverses focus to author combo
    subtitleText.addSelectionListener(new SelectionAdapter() {
      public void widgetDefaultSelected(SelectionEvent e) {
        authorCombo.setFocus();
      }
    });

    // enter in author combo adds author and traverses focus to publisher combo
    authorCombo.addSelectionListener(new SelectionAdapter() {
      public void widgetDefaultSelected(SelectionEvent e) {
        addAuthor();
        seriesCombo.setFocus();
      }
    });

    // enter in series combo traverses focus to book number text
    seriesCombo.addTraverseListener(enterTraverseListener);

    // enter in book number text traverses focus to category combo
    bookNumberText.addTraverseListener(enterTraverseListener);
    
    // enter in category combo traverses focus to genre combo
    categoryCombo.addTraverseListener(enterTraverseListener);
    
    // enter in genre combo traverses focus to language combo
    genreCombo.addTraverseListener(enterTraverseListener);
    
    // enter in language combo traverses focus to edition combo
    languageCombo.addTraverseListener(enterTraverseListener);
    
    // enter in edition combo traverses focus to pages text
    editionCombo.addTraverseListener(enterTraverseListener);
    
    // enter in pages text traverses focus to date text
    pagesText.addTraverseListener(enterTraverseListener);
    
    // enter in date text traverses focus to note text
    dateText.addTraverseListener(enterTraverseListener);
    
    // enable tab traverse in note text
    noteText.addTraverseListener(new TraverseListener() {
      public void keyTraversed(TraverseEvent e) {
        if (e.detail == SWT.TRAVERSE_TAB_NEXT) {
          e.doit = true;
        }
      }
    });

    // add author button is enabled when author combo has focus
    authorCombo.addFocusListener(new FocusAdapter() {
      public void focusGained(FocusEvent e) {
        addAuthorButton.setEnabled(true);
      }
      public void focusLost(FocusEvent e) {
        if (authorCombo.getText().trim().length() == 0) {
          addAuthorButton.setEnabled(false);
        }
      }
    });

    // click or enter on button
    addAuthorButton.addSelectionListener(new SelectionAdapter() {
      public void widgetSelected(SelectionEvent e) {
        addAuthor();
      }
    });
    
    // delete key works in author list
    authorList.addKeyListener(new KeyAdapter() {
      public void keyReleased(KeyEvent e) {
        if (e.character == SWT.DEL) {
          delAuthor();
        } else if (e.character == SWT.CR) {
          editAuthor();
        }
      }
    });

    // double-click on listElement
    authorList.addSelectionListener(new SelectionAdapter() {
      public void widgetDefaultSelected(SelectionEvent e) {
        editAuthor();
      }
    });

    tabFolder.addSelectionListener(new SelectionAdapter() {
      public void widgetSelected(SelectionEvent e) {
        if (e.item == tabItem) {
          uiState.changeStateTo(UiState.STATE_EDIT_BOOK);
          refreshData();
          setFocus(focus);
        }
      }
    });
    
    coverPhoto.addPaintListener(new PaintListener() {
      public void paintControl(PaintEvent event) {
        if ((coverImage != null) && !coverImage.isDisposed()) {
          int originalWidth = coverImage.getBounds().width;
          int originalHeight = coverImage.getBounds().height;
          int canvasWidth = coverPhoto.getBounds().width;
          int canvasHeight = coverPhoto.getBounds().height;
          int newWidth = originalWidth;
          int newHeight = originalHeight;
          double factor = Math.max(((double) originalWidth / (double) canvasWidth), ((double) originalHeight / (double) canvasHeight));
          if (factor > 1.0) {
            newWidth = (int) Math.ceil(originalWidth / factor);
            newHeight = (int) Math.ceil(originalHeight / factor); 
          }
          event.gc.drawImage(coverImage, 0, 0, originalWidth, originalHeight, (canvasWidth - newWidth) / 2, (canvasHeight - newHeight) / 2, newWidth, newHeight);
        }
      }
    });
    
    coverPhoto.addMouseListener(new MouseAdapter() {
      public void mouseDoubleClick(MouseEvent e) {
        Shell imageShell = new Shell(Display.getCurrent(), SWT.SHELL_TRIM);
        imageShell.setText("Image (full size)");
        imageShell.setLayout(new FillLayout(SWT.HORIZONTAL | SWT.VERTICAL));
        Label imageLabel = new Label(imageShell, SWT.NONE);
        imageLabel.setImage(coverImage);
        imageShell.pack();
        imageShell.open();
      }
    });
    
    */
    
  }
  
  public void setFocus(int focus) {
    /*
    this.focus = focus;
    switch (focus) {
      case FOCUS_EDIT_ISBN:
        isbnText.setFocus();
        break; 
      case FOCUS_EDIT_AUTHOR:
        authorCombo.setFocus();
        break;
      case FOCUS_EDIT_SERIES:
        seriesCombo.setFocus();
        break;
      case FOCUS_EDIT_PUBLISHER:
        publisherCombo.setFocus();
        break;
      default:
        setFocus(FOCUS_DEFAULT);
        break;
    }
    */
  }

  private void addAuthor() {
    /*
    int index = authorCombo.getSelectionIndex() + 1;
    if (index > 0) {
      Author author = cache.getAllAuthors().get(index);      
      int position = book.getAuthors().add(author);
      if (position > 0) {
        position--;
        authorList.add(author.toString(), position);
      } else {
        position++;
      }
      authorList.setSelection(position);
      authorList.showSelection();
    }
    */
  }

  private void delAuthor() {
    /*
    int index = authorList.getSelectionIndex() + 1;
    if (index > 0) {
      Author author = book.getAuthors().get(index);
      int position = book.getAuthors().remove(author) - 1;
      authorList.remove(position);
    }
    */
  }

  private void editAuthor() {
    /*
    setFocus(FOCUS_EDIT_AUTHOR);
    Author author = null;
    int index = authorCombo.getSelectionIndex() + 1;
    if (index > 0) {
      author = cache.getAllAuthors().get(index);      
    } else {
      index = authorList.getSelectionIndex() + 1;
      if (index > 0) {
        author = book.getAuthors().get(index); 
      }
    }
    uiState.setAuthor(author);
    uiState.changeStateTo(UiState.STATE_EDIT_AUTHOR);
    */
  }
  
  private void refreshData() {
    
    MutableComboBoxModel publisherModel = new DefaultComboBoxModel(cache.getAllPublishers().toStringArray());
    publisherModel.insertElementAt("", 0);
    publisherCombo.setModel(publisherModel);
    publisherCombo.setSelectedIndex(0);
    
    MutableComboBoxModel authorModel = new DefaultComboBoxModel(cache.getAllAuthors().toStringArray());
    authorModel.insertElementAt("", 0);
    authorCombo.setModel(authorModel);
    authorCombo.setSelectedIndex(0);

    MutableComboBoxModel seriesModel = new DefaultComboBoxModel(cache.getAllSeries().toStringArray());
    seriesModel.insertElementAt("", 0);
    seriesCombo.setModel(seriesModel);
    seriesCombo.setSelectedIndex(0);
    
    MutableComboBoxModel categoryModel = new DefaultComboBoxModel(cache.getAllCategories().toStringArray());
    categoryModel.insertElementAt("", 0);
    categoryCombo.setModel(categoryModel);
    categoryCombo.setSelectedIndex(0);
    
    MutableComboBoxModel genreModel = new DefaultComboBoxModel(cache.getAllGenres().toStringArray());
    genreModel.insertElementAt("", 0);
    genreCombo.setModel(genreModel);
    genreCombo.setSelectedIndex(0);

    MutableComboBoxModel languageModel = new DefaultComboBoxModel(cache.getAllLanguages().toStringArray());
    languageModel.insertElementAt("", 0);
    languageCombo.setModel(languageModel);
    languageCombo.setSelectedIndex(0);

    MutableComboBoxModel editionModel = new DefaultComboBoxModel(cache.getAllEditions().toStringArray());
    editionModel.insertElementAt("", 0);
    editionCombo.setModel(editionModel);
    editionCombo.setSelectedIndex(0);

    /*
    for (int i = 1; i <= book.getAuthors().size(); i++) {
      Author author = book.getAuthors().get(i);
      if (cache.getAllAuthors().findPosition(author) == 0) {
        book.getAuthors().remove(author);
      }
    }
    authorList.setItems(book.getAuthors().toStringArray());
    */
    
  }

  public void edit(Book book) {
    // do something ...
  }

  private CompoundBorder createBorder(String title) {
    return BorderFactory.createCompoundBorder(
      BorderFactory.createTitledBorder(title),
      BorderFactory.createEmptyBorder(0, 2, 2, 2)
    );
  }
  
}
