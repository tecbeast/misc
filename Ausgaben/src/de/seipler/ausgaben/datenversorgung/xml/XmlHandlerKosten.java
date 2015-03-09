package de.seipler.ausgaben.datenversorgung.xml;

import java.util.Date;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import de.seipler.ausgaben.common.Betrag;
import de.seipler.ausgaben.datenversorgung.DatenversorgungException;
import de.seipler.ausgaben.datenversorgung.IDatenversorgungAnbieter;
import de.seipler.ausgaben.datenversorgung.IDatenversorgungKaeufer;
import de.seipler.ausgaben.datenversorgung.IDatenversorgungKategorie;
import de.seipler.ausgaben.datenversorgung.IDatenversorgungZahlungsart;
import de.seipler.ausgaben.datenversorgung.IDatenversorgungZahlungsfrequenz;
import de.seipler.ausgaben.entity.IAnbieter;
import de.seipler.ausgaben.entity.IKaeufer;
import de.seipler.ausgaben.entity.IKategorie;
import de.seipler.ausgaben.entity.IZahlungsart;
import de.seipler.ausgaben.entity.IZahlungsfrequenz;
import de.seipler.ausgaben.entity.xml.XmlFixeKosten;
import de.seipler.ausgaben.entity.xml.XmlKategorie;
import de.seipler.ausgaben.entity.xml.XmlKosten;
import de.seipler.ausgaben.entity.xml.XmlVariableKosten;

/**
 * 
 * @author Georg Seipler
 */
public class XmlHandlerKosten extends DefaultHandler {

    private XmlEntityCache fCache;
    private IDatenversorgungAnbieter fDatenversorgungAnbieter;
    private IDatenversorgungKaeufer fDatenversorgungKaeufer;
    private IDatenversorgungKategorie fDatenversorgungKategorie;
    private IDatenversorgungZahlungsart fDatenversorgungZahlungsart;
    private IDatenversorgungZahlungsfrequenz fDatenversorgungZahlungsfrequenz;
    
    private StringBuffer fValue;
    private XmlVariableKosten fVariableKosten;
    private XmlFixeKosten fFixeKosten;
    
    public XmlHandlerKosten(
        XmlEntityCache pCache,
        IDatenversorgungAnbieter pDatenversorgungAnbieter,
        IDatenversorgungKaeufer pDatenversorgungKaeufer,
        IDatenversorgungKategorie pDatenversorgungKategorie,
        IDatenversorgungZahlungsart pDatenversorgungZahlungsart,
        IDatenversorgungZahlungsfrequenz pDatenversorgungZahlungsfrequenz
    ) {
        fValue = new StringBuffer();
        fCache = pCache;
        fDatenversorgungAnbieter = pDatenversorgungAnbieter;
        fDatenversorgungKaeufer = pDatenversorgungKaeufer;
        fDatenversorgungKategorie = pDatenversorgungKategorie;
        fDatenversorgungZahlungsart = pDatenversorgungZahlungsart;
        fDatenversorgungZahlungsfrequenz = pDatenversorgungZahlungsfrequenz;
    }

    /**
     * @see org.xml.sax.ContentHandler#characters(char, int, int)
     */
    public void characters(char[] ch, int start, int length) {
        fValue.append(new String(ch, start, length));
    }

    /**
     * @see org.xml.sax.ContentHandler#endElement(java.lang.String,
     *      java.lang.String, java.lang.String)
     */
    public void endElement(String uri, String localName, String qName) throws SAXException {
        try {
            
            // allgemeiner Teil
            if (XmlKosten.TAG_BETRAG.equals(qName)) {
                Betrag betrag = new Betrag(fValue.toString().trim());
                aktuelleKosten().setBetrag(betrag);
            }
            if (XmlKosten.TAG_ARTIKEL.equals(qName)) {
                String artikel = fValue.toString().trim();
                aktuelleKosten().setArtikel(artikel);
            }
            if (XmlKosten.TAG_KATEGORIE.equals(qName)) {
                String kategorieId = fValue.toString().trim();
                IKategorie kategorie = fDatenversorgungKategorie.liesKategorieMitId(kategorieId);
                aktuelleKosten().setKategorie(kategorie);
            }
            if (XmlKosten.TAG_ANBIETER.equals(qName)) {
                String anbieterId = fValue.toString().trim();
                IAnbieter anbieter = fDatenversorgungAnbieter.liesAnbieterMitId(anbieterId);
                aktuelleKosten().setAnbieter(anbieter);
            }
            if (XmlKosten.TAG_KAEUFER.equals(qName)) {
                String kaeuferId = fValue.toString().trim();
                IKaeufer kaeufer = fDatenversorgungKaeufer.liesKaeuferMitId(kaeuferId);
                aktuelleKosten().setKaeufer(kaeufer);
            }
            if (XmlKosten.TAG_ZAHLUNGSART.equals(qName)) {
                String zahlungsartId = fValue.toString().trim();
                IZahlungsart zahlungsart = fDatenversorgungZahlungsart.liesZahlungsartMitId(zahlungsartId);
                aktuelleKosten().setZahlungsart(zahlungsart);
            }
            if (XmlKosten.TAG_BESCHREIBUNG.equals(qName)) {
                String beschreibung = fValue.toString().trim();
                aktuelleKosten().setBeschreibung(beschreibung);
            }
            
            // spezifischer Teil fuer fixe Kosten
            if (XmlFixeKosten.TAG_FIXE_KOSTEN.equals(qName)) {
                fCache.put(fFixeKosten);
                fFixeKosten = null;
            }
            if (XmlFixeKosten.TAG_STARTDATUM.equals(qName)) {
                String dateString = fValue.toString().trim();
                Date date = XmlFormatter.stringToDate(dateString);
                fFixeKosten.setStartdatum(date);
            }
            if (XmlFixeKosten.TAG_ENDEDATUM.equals(qName)) {
                String dateString = fValue.toString().trim();
                Date date = XmlFormatter.stringToDate(dateString);
                fFixeKosten.setEndedatum(date);
            }
            if (XmlFixeKosten.TAG_ZAHLUNGSFREQUENZ.equals(qName)) {
                String zahlungsfrequenzId = fValue.toString().trim();
                IZahlungsfrequenz zahlungsfrequenz = fDatenversorgungZahlungsfrequenz.liesZahlungsfrequenzMitId(zahlungsfrequenzId);
                fFixeKosten.setZahlungsfrequenz(zahlungsfrequenz);
            }
            
            // spezifischer Teil fuer variable Kosten
            if (XmlVariableKosten.TAG_VARIABLE_KOSTEN.equals(qName)) {
                fCache.put(fVariableKosten);
                fVariableKosten = null;
            }
            if (XmlVariableKosten.TAG_KAUFDATUM.equals(qName)) {
                String dateString = fValue.toString().trim();
                Date date = XmlFormatter.stringToDate(dateString);
                fVariableKosten.setKaufdatum(date);
            }
            
            fValue = new StringBuffer();
            
        } catch (DatenversorgungException de) {
            throw new SAXException(de);
        }
    }

    /**
     * @see org.xml.sax.ContentHandler#startElement(java.lang.String,
     *      java.lang.String, java.lang.String, org.xml.sax.Attributes)
     */
    public void startElement(String uri, String localName, String qName, Attributes atts) {
        if (XmlFixeKosten.TAG_FIXE_KOSTEN.equals(qName)) {
            fFixeKosten = new XmlFixeKosten();
            String id = atts.getValue(XmlKategorie.ATTRIBUTE_ID).trim();
            fFixeKosten.setId(id);
        }
        if (XmlVariableKosten.TAG_VARIABLE_KOSTEN.equals(qName)) {
            fVariableKosten = new XmlVariableKosten();
            String id = atts.getValue(XmlKategorie.ATTRIBUTE_ID).trim();
            fVariableKosten.setId(id);
        }
    }
    
    private XmlKosten aktuelleKosten() {
        XmlKosten kosten;
        if (fFixeKosten != null) {
            kosten = fFixeKosten;
        } else {
            kosten = fVariableKosten;
        }
        return kosten;
    }
    
}
