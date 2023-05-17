package com.example.tugasbesarpbo;

public class Customer {
    private String namaBuku;
    private String namaPeminjam;
    private String tanggalPengembalian;

    public Customer(String namaBuku, String namaPeminjam, String tanggalPengembalian) {
        this.namaBuku = namaBuku;
        this.namaPeminjam = namaPeminjam;
        this.tanggalPengembalian = tanggalPengembalian;
    }

    public String getNamaBuku() {
        return namaBuku;
    }

    public String getNamaPeminjam() {
        return namaPeminjam;
    }

    public String getTanggalPengembalian() {
        return tanggalPengembalian;
    }

    public void setNamaBuku(String namaBuku) {
        this.namaBuku = namaBuku;
    }

    public void setNamaPeminjam(String namaPeminjam) {
        this.namaPeminjam = namaPeminjam;
    }

    public void setTanggalPengembalian(String tanggalPengembalian) {
        this.tanggalPengembalian = tanggalPengembalian;
    }
}
