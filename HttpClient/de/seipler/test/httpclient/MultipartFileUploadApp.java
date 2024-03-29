package de.seipler.test.httpclient;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.MultipartPostMethod;

/**
 *
 * This is a Swing application that demonstrates
 * how to use the Jakarta HttpClient multipart POST method
 * for uploading files
 *
 * @author Sean C. Sullivan
 * @author Michael Becke
 *
 */
public class MultipartFileUploadApp {

    public static void main(String[] args) {
        MultipartFileUploadFrame f = new MultipartFileUploadFrame();
        f.setTitle("HTTP multipart file upload application");
        f.pack();
        f.addWindowListener(
            new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    System.exit(0);
                }
            }
        );
        f.setVisible(true);
	}

    public static class MultipartFileUploadFrame extends JFrame {

        private File targetFile;
        private JTextArea taTextResponse;
        private DefaultComboBoxModel cmbURLModel;

        public MultipartFileUploadFrame() {
            String[] aURLs = {
                "http://localhost:8080/httpclienttest/fileupload"
            };
            
            cmbURLModel = new DefaultComboBoxModel(aURLs);
            final JComboBox cmbURL = new JComboBox(cmbURLModel);
            cmbURL.setToolTipText("Enter a URL");
            cmbURL.setEditable(true);
            cmbURL.setSelectedIndex(0);
            
            JLabel lblTargetFile = new JLabel("Selected file:");
            
            final JTextField tfdTargetFile = new JTextField(30);
            tfdTargetFile.setEditable(false);
            
            final JButton btnDoUpload = new JButton("Upload");
            btnDoUpload.setEnabled(false);

            final JButton btnSelectFile = new JButton("Select a file...");
            btnSelectFile.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        JFileChooser chooser = new JFileChooser();
                        chooser.setFileHidingEnabled(false);
                        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                        chooser.setMultiSelectionEnabled(false);
                        chooser.setDialogType(JFileChooser.OPEN_DIALOG);
                        chooser.setDialogTitle("Choose a file...");
                        if (
                            chooser.showOpenDialog(MultipartFileUploadFrame.this) 
                            == JFileChooser.APPROVE_OPTION
                        ) {
                            targetFile = chooser.getSelectedFile();
                            tfdTargetFile.setText(targetFile.toString());
                            btnDoUpload.setEnabled(true);
                        }
                    }
                }
            );
			
            taTextResponse = new JTextArea(10, 40);
            taTextResponse.setEditable(false);

            final JLabel lblURL = new JLabel("URL:");

            btnDoUpload.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent ae) {
                    String targetURL = cmbURL.getSelectedItem().toString();
                    // add the URL to the combo model if it's not already there
                    if (!targetURL
                        .equals(
                            cmbURLModel.getElementAt(
                                cmbURL.getSelectedIndex()))) {
                        cmbURLModel.addElement(targetURL);
                    }

                    MultipartPostMethod filePost =
                        new MultipartPostMethod(targetURL);

                    try {
                        appendMessage("Uploading " + targetFile.getName() + " to " + targetURL);
                        filePost.addParameter(targetFile.getName(), targetFile);
                        HttpClient client = new HttpClient();
                        client.setConnectionTimeout(5000);
                        int status = client.executeMethod(filePost);
                        if (status == HttpStatus.SC_OK) {
                            appendMessage(
                                "Upload complete, response=" + filePost.getResponseBodyAsString()
                            );
                        } else {
                            appendMessage(
                                "Upload failed, response=" + HttpStatus.getStatusText(status)
                            );
                        }
                    } catch (Exception ex) {
                        appendMessage("Error: " + ex.getMessage());
                        ex.printStackTrace();
                    } finally {
                        filePost.releaseConnection();
                    }

                }
            });

            getContentPane().setLayout(new GridBagLayout());
            GridBagConstraints c = new GridBagConstraints();

            c.anchor = GridBagConstraints.EAST;
            c.fill = GridBagConstraints.NONE;
            c.gridheight = 1;
            c.gridwidth = 1;
            c.gridx = 0;
            c.gridy = 0;
            c.insets = new Insets(10, 5, 5, 0);
            c.weightx = 1;
            c.weighty = 1;
            getContentPane().add(lblURL, c);

            c.anchor = GridBagConstraints.WEST;
            c.fill = GridBagConstraints.HORIZONTAL;
            c.gridwidth = 2;
            c.gridx = 1;
            c.insets = new Insets(5, 5, 5, 10);
            getContentPane().add(cmbURL, c);

            c.anchor = GridBagConstraints.EAST;
            c.fill = GridBagConstraints.NONE;
            c.insets = new Insets(10, 5, 5, 0);
            c.gridwidth = 1;
            c.gridx = 0;
            c.gridy = 1;
            getContentPane().add(lblTargetFile, c);

            c.anchor = GridBagConstraints.CENTER;
            c.fill = GridBagConstraints.HORIZONTAL;
            c.insets = new Insets(5, 5, 5, 5);
            c.gridwidth = 1;
            c.gridx = 1;
            getContentPane().add(tfdTargetFile, c);

            c.anchor = GridBagConstraints.WEST;
            c.fill = GridBagConstraints.NONE;
            c.insets = new Insets(5, 5, 5, 10);
            c.gridwidth = 1;
            c.gridx = 2;
            getContentPane().add(btnSelectFile, c);

            c.anchor = GridBagConstraints.CENTER;
            c.fill = GridBagConstraints.NONE;
            c.insets = new Insets(10, 10, 10, 10);
            c.gridwidth = 3;
            c.gridx = 0;
            c.gridy = 2;
            getContentPane().add(btnDoUpload, c);

            c.anchor = GridBagConstraints.CENTER;
            c.fill = GridBagConstraints.BOTH;
            c.insets = new Insets(10, 10, 10, 10);
            c.gridwidth = 3;
            c.gridheight = 3;
            c.weighty = 3;
            c.gridx = 0;
            c.gridy = 3;
            getContentPane().add(new JScrollPane(taTextResponse), c);
		}
        
        private void appendMessage(String m) {
            taTextResponse.append(m + "\n");
        }
	}
}
