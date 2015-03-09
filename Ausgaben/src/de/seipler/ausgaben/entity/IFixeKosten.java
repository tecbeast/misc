package de.seipler.ausgaben.entity;

import java.util.Date;

public interface IFixeKosten extends IKosten {

    public abstract Date getStartdatum();

    public abstract void setStartdatum(Date pStartdatum);

    public abstract Date getEndedatum();

    public abstract void setEndedatum(Date pEndedatum);

    public abstract IZahlungsfrequenz getZahlungsfrequenz();

    public abstract void setZahlungsfrequenz(IZahlungsfrequenz pZahlungsfrequenz);

}