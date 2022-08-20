package com.pakachu.benfit.rv;

public class DetayItem {
    String hareketAdi,
            hakkinda = "Açıklama yok.",
            videourl = "NO URL";
    int gif = 0, kaslar = 0;

    public DetayItem(String hareketAdi) {
        this.hareketAdi = hareketAdi;
    }

    public DetayItem(String hareketAdi, String hakkinda, int gif) {
        this.hareketAdi = hareketAdi;
        this.hakkinda = hakkinda;
        this.gif = gif;
    }

    public DetayItem(String hareketAdi, String hakkinda, int gif, int kaslar, String videourl) {
        this.hareketAdi = hareketAdi;
        this.hakkinda = hakkinda;
        this.gif = gif;
        this.kaslar = kaslar;
        this.videourl = videourl;
    }
}
