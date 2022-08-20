package com.pakachu.benfit.rv;

public class ProgramItem {
    String programAdi;
    int imageId=0;

    public ProgramItem(String programAdi) {
        this.programAdi = programAdi;

    }

    public ProgramItem(String programAdi, int resimID) {
        this.programAdi = programAdi;
        this.imageId = resimID;

    }
}
