package model;

import java.util.Date;

public class ParkirKeluar {

    private int idKeluar;
    private int idMasuk;
    private Date jamKeluar;
    private int totalBiaya;

    public ParkirKeluar(int idKeluar, int idMasuk) {
        this.idKeluar = idKeluar;
        this.idMasuk = idMasuk;
    }

    public int getIdKeluar() {
        return idKeluar;
    }

    public int getIdMasuk() {
        return idMasuk;
    }

    public int getTotalBiaya() {
        return totalBiaya;
    }
}
