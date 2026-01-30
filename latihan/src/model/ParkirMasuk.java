package model;

import java.util.Date;

public class ParkirMasuk {

    private int idMasuk;
    private String noPlat;
    private Date jamMasuk;

    public ParkirMasuk(int idMasuk, String noPlat) {
        this.idMasuk = idMasuk;
        this.noPlat = noPlat;
    }

    public int getIdMasuk() {
        return idMasuk;
    }

    public String getNoPlat() {
        return noPlat;
    }

    public Date getJamMasuk() {
        return jamMasuk;
    }
}
