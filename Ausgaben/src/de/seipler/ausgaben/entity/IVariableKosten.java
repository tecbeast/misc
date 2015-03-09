package de.seipler.ausgaben.entity;

import java.util.Date;

public interface IVariableKosten extends IKosten {

    public abstract Date getKaufdatum();

    public abstract void setKaufdatum(Date pKaufdatum);

}