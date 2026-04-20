package CaseBaseUTS;

import CaseBaseUTS.MataUang.MataUang;
import CaseBaseUTS.PaymentMethod.PilihanPembayaran;
import CaseBaseUTS.Pesanan.Pesanan;

public class Main extends KohiSop{
    public static void main(String[] args) {
        cetakBanner();
 
        Pesanan pesanan = new Pesanan();
 
        faseInputMenu(pesanan);
 
        if (pesanan.isEmpty()) {
            System.out.println("\n  [INFO] Tidak ada pesanan. Program selesai.");
            sc.close();
            return;
        }
 
        faseInputKuantitas(pesanan);
 
        if (pesanan.isEmpty()) {
            System.out.println("\n  [INFO] Semua item diskip. Program selesai.");
            sc.close();
            return;
        }
 
        MataUang mu = pilihMataUang();
        pesanan.setMataUang(mu);
 
        PilihanPembayaran pb = pilihPembayaran(pesanan.getTotalSetelahPajak());
        if (pb == null) {
            System.out.println("\n  [INFO] Pembayaran dibatalkan. Program selesai.");
            sc.close();
            return;
        }
        pesanan.setPembayaran(pb);
 
        pesanan.cetakKuitansi();
 
        sc.close();
    }
}
