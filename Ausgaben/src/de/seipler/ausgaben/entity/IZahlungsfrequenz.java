package de.seipler.ausgaben.entity;

public interface IZahlungsfrequenz extends IEntity {

    public abstract String getName();

    public abstract void setName(String pName);

    public abstract String getBeschreibung();

    public abstract void setBeschreibung(String pBeschreibung);

}