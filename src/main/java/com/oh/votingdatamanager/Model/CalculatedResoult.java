package com.oh.votingdatamanager.Model;

import lombok.Data;

@Data
public class CalculatedResoult {
    private String eredmeny;
    private int kepviselokSzama;
    private int igenekSzama;
    private int nemekSzama;
    private int tartozkodasokSzama;

    public String getEredmeny() {
        return eredmeny;
    }

    public void setEredmeny(String eredmeny) {
        this.eredmeny = eredmeny;
    }

    public int getKepviselokSzama() {
        return kepviselokSzama;
    }

    public void setKepviselokSzama(int kepviselokSzama) {
        this.kepviselokSzama = kepviselokSzama;
    }

    public int getIgenekSzama() {
        return igenekSzama;
    }

    public void setIgenekSzama(int igenekSzama) {
        this.igenekSzama = igenekSzama;
    }

    public int getNemekSzama() {
        return nemekSzama;
    }

    public void setNemekSzama(int nemekSzama) {
        this.nemekSzama = nemekSzama;
    }

    public int getTartozkodasokSzama() {
        return tartozkodasokSzama;
    }

    public void setTartozkodasokSzama(int tartozkodasokSzama) {
        this.tartozkodasokSzama = tartozkodasokSzama;
    }
}
