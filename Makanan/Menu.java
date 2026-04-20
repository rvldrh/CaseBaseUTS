package CaseBaseUTS.Makanan;


public abstract class Menu {
    protected String kode;
    protected String namaMenu;
    protected double harga;

    public Menu(String kode, String namaMenu, double harga) {
        this.kode = kode;
        this.namaMenu = namaMenu;
        this.harga = harga;
    }

    
    public String getKode(){
        return kode;
    }
    
    public String getNamaMenu(){
        return namaMenu;
    }
    public double getHarga() {
        return harga;
    }


    public abstract double hitungPajak();

    
}
