/*
 * $Id: EntityManager.java,v 1.1 2004/04/25 20:12:39 Georg Exp $
 *
 * (C) Copyright 2002 by Yuval Oren. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 *
 */

package com.bluecast.xml;

import java.util.*;
import java.io.*;
import java.net.*;
import com.bluecast.util.*;
import org.xml.sax.*;


public class EntityManager {
	// Entity types
	static public final int GENERAL = 0;
	static public final int PARAMETER = 1;

	 final private class Entry implements Entity {
		boolean isOpen=false;
		char[] value;
		String pubID,sysID,ndata;
		XMLInputReader reader = null;
                boolean isStandalone=false;

		Entry(String value) {
                    pubID = sysID = ndata = null;
                    this.value = value.toCharArray();
		}

		Entry(String pubID, String sysID) {
                    this(pubID,sysID,null);
		}

                Entry(String pubID, String sysID, String ndata) {
                    this.pubID = pubID;
                    this.sysID = sysID;
                    this.ndata = ndata;
                }

		public void open()
                throws RecursionException, SAXException, IOException {
                    if (isOpen)
                            throw new RecursionException();

                    if (!isInternal()) {
                        if (resolver == null) {
                            reader = new XMLStreamReader(
                                (new URL(sysID)).openStream(),true);
                        }
                        else {
                            InputSource source = resolver.resolveEntity(pubID,sysID);
                            Reader r = source.getCharacterStream();

                            if (r != null)
                                reader = new XMLReaderReader(r,true);
                            else {
                                InputStream in;

                                in = source.getByteStream();
                                if (in != null)
                                    reader = new XMLStreamReader(in,true);
                                else
                                    reader = new XMLStreamReader(
                                      (new URL(sysID)).openStream(),true);
                            }
                        }
                        isStandalone = reader.isXMLStandalone();
                    }
                    isOpen = true;
  		}

		 public boolean isOpen() { return isOpen; }

		 public void close() { isOpen = false; reader = null; }

		 public String getSystemID() { return sysID; }
		 public String getPublicID() { return pubID; }

                 public boolean isStandalone() { return isStandalone; }
                 public void setStandalone(boolean standalone) {
                    isStandalone = standalone;
                 }

		 public boolean isInternal() { return (sysID == null); }
                 public boolean isParsed() { return (ndata == null); }

                public String getDeclaredEncoding() {
                    if (reader != null)
                        return reader.getXMLDeclaredEncoding();
                    else
                        return null;
                }

                public boolean isStandaloneDeclared() {
                    if (reader != null)
                        return reader.isXMLStandaloneDeclared();
                    else
                        return false;
                }

                public String getXMLVersion() {
                    if (reader != null)
                        return reader.getXMLVersion();
                    else
                        return null;
                }


		public Reader getReader() {
                    if (isInternal())
                            return new CharArrayReader(value);
                    else
                            return reader;
		}

		 public String stringValue() { return new String(value); }
		 public char[] charArrayValue() { return value; }

	}


	private EntityResolver resolver;
	private Map[] entityMaps = new HashMap[] { new HashMap(), new HashMap() };
        private Map entitiesByURI = new HashMap();

	public EntityManager() { this(null); }

	public EntityManager(EntityResolver resolver) {
		setResolver(resolver);
	}

	public void setResolver(EntityResolver resolver) {
		this.resolver = resolver;
	}

        public EntityResolver getResolver() {
                return resolver;
        }

    /**
     * Defines an internal entity. Returns true if successful,
     * false if the entity was already defined.
     */
	 public boolean putInternal(String name, String value, int type) {
        if (entityMaps[type].get(name) == null) {
		  entityMaps[type].put(name,new Entry(value));
          return true;
        }

        return false;
	}

    /**
     * Defines an external entity. Returns true if successful,
     * false if the entity was already defined.
     */
	 public boolean putExternal(Entity context, String name,
                                 String pubID, String sysID, int type)
         throws MalformedURLException {

        if (entityMaps[type].get(name) == null) {
            // First we resolve the sysID in context of the entity
            sysID = resolveSystemID(context.getSystemID(),sysID);

            // TODO: perhaps we should do this with pubID too

            Entry e = new Entry(pubID,sysID);
            entityMaps[type].put(name,e);
            if (pubID != null && pubID.length() > 0)
                entitiesByURI.put(pubID,e);
            entitiesByURI.put(sysID,e);
            return true;
        }

        return false;
	}

    /**
     * Defines an unparsed entity. Returns true if successful,
     * false if the entity was already defined.
     */

	 public boolean putUnparsed(Entity context, String name,
                                 String pubID, String sysID, String ndata, int type)
         throws MalformedURLException {

         if (entityMaps[type].get(name) == null) {
            // TODO: are we supposed to resolve unparsed entities?
            // sysID = resolveSystemID(context.getSystemID(),sysID);
            entityMaps[type].put(name,new Entry(pubID,sysID,ndata));
            return true;
         }

         return false;
	}

	 public void clear() {
		entityMaps[GENERAL].clear();
		entityMaps[PARAMETER].clear();
                entitiesByURI.clear();
	}

	 public Entity getByName(String name, int type)  {
		return (Entry) entityMaps[type].get(name);
	}


         public Entity getByID(Entity context,String pubID, String sysID)
         throws MalformedURLException {
            Entity result=null;

            sysID = resolveSystemID(context.getSystemID(),sysID);

            result = (Entity) entitiesByURI.get(sysID);
            if (result != null)
                return result;

            if (pubID != null && pubID.length() > 0) {
                result = (Entity) entitiesByURI.get(pubID);
                if (result != null)
                    return result;
            }

            // No entity exists for this URI; create one
            result = new Entry(pubID,sysID);
            if (pubID != null && pubID.length() > 0)
                entitiesByURI.put(pubID,result);
            entitiesByURI.put(sysID,result);

            return result;
         }

         static public
         String resolveSystemID(String contextSysID, String sysID)
         throws MalformedURLException {
            URL url;
            if (contextSysID != null)
                url = new URL(new URL(contextSysID),sysID);
            else
                url = new URL(sysID);

            return url.toString();
         }




}
