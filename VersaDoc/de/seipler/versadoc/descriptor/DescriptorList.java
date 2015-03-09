package de.seipler.versadoc.descriptor;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.zip.GZIPOutputStream;

import de.seipler.versadoc.util.*;

/**
 * 
 * @author Georg Seipler
 */
public class DescriptorList {
  
  private List descriptors;
  
  /**
   * 
   */
  public DescriptorList() {
    this.descriptors = new ArrayList();   
  }
  
  /**
   * 
   */
  public void add(Descriptor descriptor) {
    this.descriptors.add(descriptor);
  }
  
  /**
   * 
   */
  public void addAll(DescriptorList list) {
    this.descriptors.addAll(list.descriptors);
  }
  
  /**
   * 
   */
  public Iterator iterator() {
    return this.descriptors.iterator();
  }

  /**
   * 
   */
  public int size() {
    return this.descriptors.size();
  }

  /**
   * 
   */
  public void sort(Comparator comparator) {
    Collections.sort(this.descriptors, comparator);
  }

  /**
   * 
   */
  public void writeTo(File xmlFile, boolean useCompression) {
    OutputStream out = null;
    try {
      if (useCompression) {
        out = new BufferedOutputStream(new GZIPOutputStream(new FileOutputStream(xmlFile)));
      } else {
        out = new BufferedOutputStream(new FileOutputStream(xmlFile));
      }
      XMLFormatUtil.writeXMLHeaderTo(out);
      XMLFormatUtil.writeElementHeaderTo(out, 0, "descriptor-list");
      Iterator descriptorIterator = iterator();
      while (descriptorIterator.hasNext()) {
        Descriptor descriptor = (Descriptor) descriptorIterator.next();
        descriptor.writeTo(out, 1);
      }
      XMLFormatUtil.writeElementFooterTo(out, 0, "descriptor-list");
    } catch (Exception e) {
      throw new DescriptorException(e);
    } finally {
      try { out.close(); } catch (IOException ignored) { }
    }
  }

}
