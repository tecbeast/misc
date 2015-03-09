package de.seipler.ausgaben.entity.xml;

import de.seipler.ausgaben.common.Betrag;
import de.seipler.ausgaben.datenversorgung.xml.XmlFormatter;
import de.seipler.ausgaben.entity.IAnbieter;
import de.seipler.ausgaben.entity.IEntity;
import de.seipler.ausgaben.entity.IKaeufer;
import de.seipler.ausgaben.entity.IKategorie;
import de.seipler.ausgaben.entity.IKosten;
import de.seipler.ausgaben.entity.IZahlungsart;


/**
 * 
 * @author Georg Seipler
 */
public abstract class XmlKosten extends XmlEntity implements IKosten {
    
    public static final String TAG_KOSTEN = "kosten";
    public static final String TAG_KOSTEN_LISTE = TAG_KOSTEN + TAG_LISTE_SUFFIX;
    
    public static final String TAG_BETRAG = "betrag";
    public static final String TAG_ARTIKEL = "artikel";
    public static final String TAG_KATEGORIE = "kategorie";
    public static final String TAG_ANBIETER = "anbieter";
    public static final String TAG_KAEUFER = "kaeufer";
    public static final String TAG_ZAHLUNGSART = "zahlungsart";
    public static final String TAG_BESCHREIBUNG = "beschreibung";
    
    public static final String ATTRIBUTE_TYP = "typ";
    
    private Betrag fBetrag;
    private String fArtikel;
    private IKategorie fKategorie;
    private IAnbieter fAnbieter;
    private IKaeufer fKaeufer;
    private IZahlungsart fZahlungsart;
    private String fBeschreibung;
    
    public XmlKosten() {
        super();
    }

    public Betrag getBetrag() {
        return fBetrag;
    }

    public void setBetrag(Betrag pBetrag) {
        fBetrag = pBetrag;
    }

    public String getArtikel() {
        return fArtikel;
    }

    public void setArtikel(String pArtikel) {
        fArtikel = pArtikel;
    }

    public IKategorie getKategorie() {
        return fKategorie;
    }

    public void setKategorie(IKategorie pKategorie) {
        fKategorie = pKategorie;
    }

    public IAnbieter getAnbieter() {
        return fAnbieter;
    }

    public void setAnbieter(IAnbieter pAnbieter) {
        fAnbieter = pAnbieter;
    }

    public IKaeufer getKaeufer() {
        return fKaeufer;
    }

    public void setKaeufer(IKaeufer pKaeufer) {
        fKaeufer = pKaeufer;
    }

    public String getBeschreibung() {
        return fBeschreibung;
    }

    public void setBeschreibung(String pBeschreibung) {
        fBeschreibung = pBeschreibung;
    }

    public IZahlungsart getZahlungsart() {
        return fZahlungsart;
    }

    public void setZahlungsart(IZahlungsart pZahlungsart) {
        fZahlungsart = pZahlungsart;
    }
    
    protected void addElements(StringBuffer pBuffer, int pIndentation) {
        XmlFormatter.addElement(pBuffer, pIndentation, TAG_ARTIKEL, getArtikel());
        String betragString = ((getBetrag() != null) ? getBetrag().asString() : null); 
        XmlFormatter.addElement(pBuffer, pIndentation, TAG_BETRAG, betragString);
        XmlFormatter.addElement(pBuffer, pIndentation, TAG_KATEGORIE, entityIdIfNotNull(getKategorie()));
        XmlFormatter.addElement(pBuffer, pIndentation, TAG_ANBIETER, entityIdIfNotNull(getAnbieter()));
        XmlFormatter.addElement(pBuffer, pIndentation, TAG_KAEUFER, entityIdIfNotNull(getKaeufer()));
        XmlFormatter.addElement(pBuffer, pIndentation, TAG_ZAHLUNGSART, entityIdIfNotNull(getZahlungsart()));
        XmlFormatter.addElement(pBuffer, pIndentation, TAG_BESCHREIBUNG, getBeschreibung());
    }
    
    protected String entityIdIfNotNull(IEntity pEntity) {
        return ((pEntity != null) ? pEntity.getId() : null);
    }

}
