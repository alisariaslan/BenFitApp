package com.pakachu.benfit.rv;

public class HareketItem {
    String hareketAdi,
            hareketDetayi="";
    int imageID=0;

    public HareketItem(String hareketAdi) {
        this.hareketAdi = hareketAdi;
    }

    public HareketItem(String hareketAdi, int resimID,String hareketDetayi) {
        this.hareketAdi = hareketAdi;
        this.imageID = resimID;
        this.hareketDetayi=hareketDetayi;
    }
}
