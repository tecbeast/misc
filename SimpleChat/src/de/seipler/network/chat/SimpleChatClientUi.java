package de.seipler.network.chat;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class SimpleChatClientUi extends JFrame implements IChatCommandHandler {
  
  private JTextArea fTextArea;
  private JTextField fTextField;
  private NioChatClient fNioClient;
  private String fUser;
  
  protected static String LINE_SEPARATOR = System.getProperty("line.separator", "\n");
  
  protected class TextAreaUpdate implements Runnable {
    private String fText;
    public TextAreaUpdate(String pText) {
      fText = pText;
    }
    public void run() {
      fTextArea.append(fText);
      fTextArea.setCaretPosition(fTextArea.getDocument().getLength());
    }
  }
  
  public SimpleChatClientUi(NioChatClient pNioClient, String pUser) throws IOException {
    
    super("SimpleChatClient: " + pUser);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
    fNioClient = pNioClient;
    fUser = pUser;
    
    fTextArea = new JTextArea(5, 40);
    fTextArea.setLineWrap(true);
    fTextArea.setEditable(false);

    fTextField = new JTextField(40);

    fTextField.addActionListener(
      new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          String text = fTextField.getText();
          if (text != null) {
            text = text.trim();
            if (text.length() > 0) {
              try {
                ChatCommandTalk talkCommand = new ChatCommandTalk(fUser, text);
                fNioClient.send(talkCommand);
                fTextField.setText("");
              } catch (IOException ioe) {
                ioe.printStackTrace(System.err);
              }
            } 
          }
        }
      }
    );
    
    JScrollPane scrollPane = new JScrollPane(fTextArea);
    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    getContentPane().add(scrollPane, BorderLayout.CENTER);
    getContentPane().add(fTextField, BorderLayout.SOUTH);
    pack();
    
    fNioClient.initiateConnection(this);
    ChatCommandJoin joinCommand = new ChatCommandJoin(fUser);    
    fNioClient.send(joinCommand);
    
  }
  
  public synchronized boolean handleChatCommand(ChatCommand chatCommand) {
    StringBuffer text = new StringBuffer();
    switch (chatCommand.getType()) {
      case ChatCommand.JOIN:
        text.append("* ");
        text.append(chatCommand.getUser());
        text.append(" joins the chat.");
        text.append(LINE_SEPARATOR);
        break;
      case ChatCommand.LEAVE:
        text.append("* ");
        text.append(chatCommand.getUser());
        text.append(" leaves the chat.");
        text.append(LINE_SEPARATOR);
        break;
      case ChatCommand.USERS:
        ChatCommandUsers usersCommand = (ChatCommandUsers) chatCommand;
        String[] users = usersCommand.getAllUsers();
        if (users.length > 1) {
          text.append("* Current Users in Chat:");
          text.append(LINE_SEPARATOR);
          for (int i = 1; i < users.length; i++) {
            text.append("* ");
            text.append(users[i]);
            text.append(LINE_SEPARATOR);
          }
        } else {
          text.append("* No other users present.");
          text.append(LINE_SEPARATOR);
        }
        break;
      case ChatCommand.TALK:
        ChatCommandTalk talkCommand = (ChatCommandTalk) chatCommand;
        if (!fUser.equals(talkCommand.getUser())) {
          text.append(talkCommand.getUser());
          text.append(": ");
        }
        text.append(talkCommand.getTalk());
        text.append(LINE_SEPARATOR);
        break;
    }
    SwingUtilities.invokeLater(new TextAreaUpdate(text.toString()));
    return true;
  }
 
}
