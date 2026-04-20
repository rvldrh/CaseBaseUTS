package CaseBaseUTS.Makanan;

public class Makanan extends Menu {

    public Makanan(String kode, String namaMenu, double harga) {
        super(kode, namaMenu, harga);
    }

    @Override
    public double hitungPajak() {
        if (harga > 50) {
            return 0.08;
        }
        else{
            return 0.11;
        }
    }
}
