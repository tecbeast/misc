package de.seipler.ausgaben.entity;

import de.seipler.ausgaben.common.Betrag;

public interface IKosten extends IEntity {

    public abstract Betrag getBetrag();

    public abstract void setBetrag(Betrag pBetrag);

    public abstract String getArtikel();

    public abstract void setArtikel(String pArtikel);

    public abstract IKategorie getKategorie();

    public abstract void setKategorie(IKategorie pKategorie);

    public abstract IAnbieter getAnbieter();

    public abstract void setAnbieter(IAnbieter pAnbieter);

    public abstract IKaeufer getKaeufer();

    public abstract void setKaeufer(IKaeufer pKaeufer);

    public abstract String getBeschreibung();

    public abstract void setBeschreibung(String pBeschreibung);

    public abstract IZahlungsart getZahlungsart();

    public abstract void setZahlungsart(IZahlungsart pZahlungsart);

}