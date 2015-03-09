package de.seipler.bookcollection.ui;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.JPanel;

/**
 * 
 * @author Georg Seipler
 */
public class ImagePanel extends JPanel {
  
  private Image image;
  private int imageWidth;
  private int imageHeight;
  
  public void paint(Graphics g) {
    
    super.paint(g);
  
    if (image != null) {
      Insets insets = getInsets();
      int canvasWidth = getWidth() - insets.left - insets.right;
      int canvasHeight = getHeight() - insets.top - insets.bottom;
      int newWidth = imageWidth;
      int newHeight = imageHeight;
      double factor = Math.max(((double) imageWidth / (double) canvasWidth), ((double) imageHeight / (double) canvasHeight));
      if (factor > 1.0) {
        newWidth = (int) Math.ceil(imageWidth / factor);
        newHeight = (int) Math.ceil(imageHeight / factor); 
      }
      int cornerX = (getWidth() - newWidth + insets.left - insets.bottom) / 2;
      int cornerY = (getHeight() - newHeight + insets.top - insets.bottom) / 2;
      g.drawImage(image, cornerX, cornerY, cornerX + newWidth, cornerY + newHeight, 0, 0, imageWidth, imageHeight, getBackground(), this);
    }
    
  }

  public void loadImage(String resourceName) throws IOException, InterruptedException {
    byte[] chunk = new byte[4096];   
    InputStream in = getClass().getResourceAsStream(resourceName);
    ByteArrayOutputStream out = new ByteArrayOutputStream(in.available());
    int nrOfBytes = 0;
    while (nrOfBytes >= 0) {
      nrOfBytes = in.read(chunk);
      if (nrOfBytes > 0) {
        out.write(chunk, 0, nrOfBytes);
      }
    }
    in.close();
    out.close();
    this.image = Toolkit.getDefaultToolkit().createImage(out.toByteArray());
    MediaTracker mt = new MediaTracker(this);
    mt.addImage(image, 0);
    mt.waitForID(0);
    imageWidth = image.getWidth(this);
    imageHeight = image.getHeight(this);    
  }
  
}
