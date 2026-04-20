package CaseBaseUTS;

import CaseBaseUTS.Makanan.Makanan;
import CaseBaseUTS.Makanan.Minuman;
import CaseBaseUTS.MataUang.EUR;
import CaseBaseUTS.MataUang.JPY;
import CaseBaseUTS.MataUang.MYR;
import CaseBaseUTS.MataUang.MataUang;
import CaseBaseUTS.MataUang.USD;
import CaseBaseUTS.PaymentMethod.EMoney;
import CaseBaseUTS.PaymentMethod.PilihanPembayaran;
import CaseBaseUTS.PaymentMethod.Qris;
import CaseBaseUTS.PaymentMethod.Tunai;
import CaseBaseUTS.Pesanan.ItemPesanan;
import CaseBaseUTS.Pesanan.Pesanan;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class KohiSop {
 
    private static final Map<String, Minuman> DAFTAR_MINUMAN = new LinkedHashMap<>();
    private static final Map<String, Makanan> DAFTAR_MAKANAN = new LinkedHashMap<>();
 
    static {
        DAFTAR_MINUMAN.put("A1", new Minuman("A1", "Caffe Latte", 46));
        DAFTAR_MINUMAN.put("A2", new Minuman("A2", "Cappuccino", 46));
        DAFTAR_MINUMAN.put("E1", new Minuman("E1", "Caffe Americano", 37));
        DAFTAR_MINUMAN.put("E2", new Minuman("E2", "Caffe Mocha", 55));
        DAFTAR_MINUMAN.put("E3", new Minuman("E3", "Caramel Macchiato", 59));
        DAFTAR_MINUMAN.put("E4", new Minuman("E4", "Asian Dolce Latte", 55));
        DAFTAR_MINUMAN.put("E5", new Minuman("E5", "Double Shots Iced Shaken Espresso", 50));
        DAFTAR_MINUMAN.put("B1", new Minuman("B1", "Freshly Brewed Coffee", 23));
        DAFTAR_MINUMAN.put("B2", new Minuman("B2", "Vanilla Sweet Cream Cold Brew", 50));
        DAFTAR_MINUMAN.put("B3", new Minuman("B3", "Cold Brew", 44));
 
        DAFTAR_MAKANAN.put("M1", new Makanan("M1", "Petemania Pizza", 112));
        DAFTAR_MAKANAN.put("M2", new Makanan("M2", "Mie Rebus Super Mario", 35));
        DAFTAR_MAKANAN.put("M3", new Makanan("M3", "Ayam Bakar Goreng Rebus Spesial", 72));
        DAFTAR_MAKANAN.put("M4", new Makanan("M4", "Soto Kambing Iga Guling", 124));
        DAFTAR_MAKANAN.put("S1", new Makanan("S1", "Singkong Bakar A La Carte", 37));
        DAFTAR_MAKANAN.put("S2", new Makanan("S2", "Ubi Cilembu Bakar Arang", 58));
        DAFTAR_MAKANAN.put("S3", new Makanan("S3", "Tempe Mendoan", 18));
        DAFTAR_MAKANAN.put("S4", new Makanan("S4", "Tahu Bakso Extra Telur", 28));
    }
 
    private static final int MAX_QTY_MINUMAN = 3;
    private static final int MAX_QTY_MAKANAN = 2;
    private static final int MAX_JENIS       = 5;
 
    protected static final Scanner sc = new Scanner(System.in);
 
    protected static void faseInputMenu(Pesanan pesanan) {
        System.out.println("\n" + "=".repeat(68));
        System.out.println("  TAHAP 1: Pilih Menu Pesanan (ketik 'CC' untuk batalkan)");
        System.out.println("=".repeat(68));
 
        tampilkanDaftarMenu();
 
        System.out.println("\n  Masukkan kode menu satu per satu.");
        System.out.printf("  Maksimal %d jenis minuman + %d jenis makanan per pesanan.%n%n", MAX_JENIS, MAX_JENIS);
 
        while (true) {
            if (!pesanan.isEmpty()) tampilkanRingkasanPesanan(pesanan);
 
            int jmlMinuman = pesanan.getMinumanItems().size();
            int jmlMakanan = pesanan.getMakananItems().size();
 
            if (jmlMinuman >= MAX_JENIS && jmlMakanan >= MAX_JENIS) {
                System.out.println("\n  [INFO] Batas maksimal item tercapai (5 minuman + 5 makanan).");
                break;
            }
 
            System.out.print("  >> Masukkan 1 kode menu (atau tekan ENTER untuk selesai): ");
            String input = sc.nextLine().trim().toUpperCase();
 
            if (input.equals("CC")) {
                System.out.println("\n  [INFO] Pesanan dibatalkan.");
                System.exit(0);
            }
 
            if (input.isEmpty()) {
                if (pesanan.isEmpty()) {
                    System.out.println("  [PERINGATAN] Belum ada menu yang dipilih!");
                    continue;
                }
                break;
            }
 
            if (DAFTAR_MINUMAN.containsKey(input)) {
                if (jmlMinuman >= MAX_JENIS) {
                    System.out.printf("  [ERROR] Batas %d jenis minuman sudah tercapai!%n", MAX_JENIS);
                    continue;
                }
                if (pesanan.sudahAdaMinuman(input)) {
                    System.out.printf("  [ERROR] '%s' sudah ada dalam pesanan!%n", input);
                    continue;
                }
                Minuman m = DAFTAR_MINUMAN.get(input);
                pesanan.tambahMinuman(new ItemPesanan(m, 1));
                System.out.printf("  [OK] %s - %s ditambahkan.%n", m.getKode(), m.getNamaMenu());
                continue;
            }
 
            if (DAFTAR_MAKANAN.containsKey(input)) {
                if (jmlMakanan >= MAX_JENIS) {
                    System.out.printf("  [ERROR] Batas %d jenis makanan sudah tercapai!%n", MAX_JENIS);
                    continue;
                }
                if (pesanan.sudahAdaMakanan(input)) {
                    System.out.printf("  [ERROR] '%s' sudah ada dalam pesanan!%n", input);
                    continue;
                }
                Makanan mk = DAFTAR_MAKANAN.get(input);
                pesanan.tambahMakanan(new ItemPesanan(mk, 1));
                System.out.printf("  [OK] %s - %s ditambahkan.%n", mk.getKode(), mk.getNamaMenu());
                continue;
            }
 
            System.out.printf("  [ERROR] Kode '%s' tidak valid. Silakan coba lagi.%n", input);
        }
    }
 
    protected static void faseInputKuantitas(Pesanan pesanan) {
        System.out.println("\n" + "=".repeat(68));
        System.out.println("  TAHAP 2: Masukkan Kuantitas");
        System.out.println("  (ketik '0' / 'S' untuk skip, ENTER = kuantitas 1, 'CC' batal)");
        System.out.println("=".repeat(68));
 
        if (!pesanan.getMinumanItems().isEmpty()) {
            System.out.println("\n  [ MINUMAN ] Maksimal " + MAX_QTY_MINUMAN + " porsi per jenis");
            List<ItemPesanan> toRemoveM = new ArrayList<>();
            for (ItemPesanan ip : pesanan.getMinumanItems()) {
                tampilkanRingkasanPesanan(pesanan);
                int qty = inputKuantitas(ip.getMenu().getNamaMenu(),
                        ip.getMenu().getKode(), MAX_QTY_MINUMAN);
                if (qty == 0) toRemoveM.add(ip);
                else          ip.setQuantity(qty);
            }
            pesanan.getMinumanItems().removeAll(toRemoveM);
        }
 
        if (!pesanan.getMakananItems().isEmpty()) {
            System.out.println("\n  [ MAKANAN ] Maksimal " + MAX_QTY_MAKANAN + " porsi per jenis");
            List<ItemPesanan> toRemoveMk = new ArrayList<>();
            for (ItemPesanan ip : pesanan.getMakananItems()) {
                tampilkanRingkasanPesanan(pesanan);
                int qty = inputKuantitas(ip.getMenu().getNamaMenu(),
                        ip.getMenu().getKode(), MAX_QTY_MAKANAN);
                if (qty == 0) toRemoveMk.add(ip);
                else          ip.setQuantity(qty);
            }
            pesanan.getMakananItems().removeAll(toRemoveMk);
        }
    }
 
    protected static int inputKuantitas(String namaMenu, String kode, int maxQty) {
        while (true) {
            System.out.printf("  Kuantitas [%s] %s (1-%d, S=skip): ",
                    kode, namaMenu, maxQty);
            String raw = sc.nextLine().trim();
 
            if (raw.equalsIgnoreCase("CC")) {
                System.out.println("\n  [INFO] Pesanan dibatalkan.");
                System.exit(0);
            }
 
            if (raw.isEmpty()) return 1;
 
            if (raw.equalsIgnoreCase("S") || raw.equals("0")) {
                System.out.printf("  [SKIP] %s dihapus dari pesanan.%n", namaMenu);
                return 0;
            }
 
            try {
                int qty = Integer.parseInt(raw);
                if (qty < 1 || qty > maxQty) {
                    System.out.printf("  [ERROR] Kuantitas harus antara 1-%d.%n", maxQty);
                    continue;
                }
                return qty;
            } catch (NumberFormatException e) {
                System.out.println("  [ERROR] Input tidak valid. Masukkan angka, 'S' untuk skip, atau ENTER.");
            }
        }
    }
 
    protected static MataUang pilihMataUang() {
        System.out.println("\n" + "=".repeat(68));
        System.out.println("  TAHAP 3: Pilih Mata Uang Pembayaran");
        System.out.println("=".repeat(68));
        System.out.println("  1. IDR  (Rupiah — tanpa konversi)");
        System.out.println("  2. USD  (1 USD = 15 IDR)");
        System.out.println("  3. JPY  (10 JPY = 1 IDR)");
        System.out.println("  4. MYR  (1 MYR = 4 IDR)");
        System.out.println("  5. EUR  (1 EUR = 14 IDR)");
 
        while (true) {
            System.out.print("  >> Pilih mata uang [1-5]: ");
            String raw = sc.nextLine().trim();
            switch (raw) {
                case "1": return null; 
                case "2": return new USD();
                case "3": return new JPY();
                case "4": return new MYR();
                case "5": return new EUR();
                default:
                    System.out.println("  [ERROR] Pilihan tidak valid. Masukkan 1-5.");
            }
        }
    }
 
    protected static PilihanPembayaran pilihPembayaran(double totalBayar) {
        System.out.println("\n" + "=".repeat(68));
        System.out.println("  TAHAP 4: Pilih Channel Pembayaran");
        System.out.println("=".repeat(68));
        System.out.printf("  Total yang harus dibayar: Rp %,.2f%n%n", totalBayar);
        System.out.println("  1. Tunai   (tidak ada diskon)");
        System.out.println("  2. QRIS    (diskon 5%, cek saldo)");
        System.out.println("  3. eMoney  (diskon 7%, biaya admin Rp 20, cek saldo)");
 
        while (true) {
            System.out.print("  >> Pilih channel pembayaran [1-3]: ");
            String raw = sc.nextLine().trim();
 
            switch (raw) {
                case "1":
                    return new Tunai();
 
                case "2": {
                    double saldo = inputSaldo("QRIS");
                    Qris qris = new Qris(saldo);
                    double tagihan = totalBayar - qris.hitungDiskon(totalBayar);
                    if (!qris.cekSaldo(saldo, tagihan)) {
                        System.out.printf("  [ERROR] Saldo QRIS tidak cukup. Saldo: Rp %,.2f | Tagihan: Rp %,.2f%n",
                                saldo, tagihan);
                        System.out.println("  Pilih channel lain atau masukkan saldo yang cukup.");
                        continue;
                    }
                    return qris;
                }
 
                case "3": {
                    double saldo  = inputSaldo("eMoney");
                    EMoney emoney = new EMoney(saldo);
                    double tagihan = totalBayar - emoney.hitungDiskon(totalBayar) + emoney.getBiayaAdmin();
                    if (!emoney.cekSaldo(saldo, tagihan)) {
                        System.out.printf("  [ERROR] Saldo eMoney tidak cukup. Saldo: Rp %,.2f | Tagihan: Rp %,.2f%n",
                                saldo, tagihan);
                        System.out.println("  Pilih channel lain atau masukkan saldo yang cukup.");
                        continue;
                    }
                    return emoney;
                }
 
                default:
                    System.out.println("  [ERROR] Pilihan tidak valid. Masukkan 1, 2, atau 3.");
            }
        }
    }
 
    protected static double inputSaldo(String nama) {
        while (true) {
            System.out.printf("  >> Masukkan saldo %s (IDR): ", nama);
            String raw = sc.nextLine().trim();
            try {
                double saldo = Double.parseDouble(raw.replace(",", ""));
                if (saldo < 0) {
                    System.out.println("  [ERROR] Saldo tidak boleh negatif.");
                    continue;
                }
                return saldo;
            } catch (NumberFormatException e) {
                System.out.println("  [ERROR] Input tidak valid. Masukkan angka.");
            }
        }
    }
 
    protected static void cetakBanner() {
        System.out.println("=".repeat(68));
        System.out.println("  ██╗  ██╗ ██████╗ ██╗  ██╗██╗███████╗ ██████╗ ██████╗ ");
        System.out.println("  ██║ ██╔╝██╔═══██╗██║  ██║██║██╔════╝██╔═══██╗██╔══██╗");
        System.out.println("  █████╔╝ ██║   ██║███████║██║███████╗██║   ██║██████╔╝");
        System.out.println("  ██╔═██╗ ██║   ██║██╔══██║██║╚════██║██║   ██║██╔═══╝ ");
        System.out.println("  ██║  ██╗╚██████╔╝██║  ██║██║███████║╚██████╔╝██║     ");
        System.out.println("  ╚═╝  ╚═╝ ╚═════╝ ╚═╝  ╚═╝╚═╝╚══════╝ ╚═════╝ ╚═╝     ");
        System.out.println("            Selamat Datang di KohiSop!");
        System.out.println("=".repeat(68));
    }
 
    protected static void tampilkanDaftarMenu() {
        String garis = "-".repeat(68);
        System.out.println("\n" + garis);
        System.out.printf("  %-4s  %-38s  %s%n", "Kode", "Menu Minuman", "Harga (Rp)");
        System.out.println(garis);
        for (Minuman m : DAFTAR_MINUMAN.values()) {
            System.out.printf("  %-4s  %-38s  %,9.0f%n",
                    m.getKode(), m.getNamaMenu(), m.getHarga());
        }
        System.out.println(garis);
        System.out.printf("  %-4s  %-38s  %s%n", "Kode", "Menu Makanan", "Harga (Rp)");
        System.out.println(garis);
        for (Makanan mk : DAFTAR_MAKANAN.values()) {
            System.out.printf("  %-4s  %-38s  %,9.0f%n",
                    mk.getKode(), mk.getNamaMenu(), mk.getHarga());
        }
        System.out.println(garis);
    }
 
    protected static void tampilkanRingkasanPesanan(Pesanan pesanan) {
        if (pesanan.isEmpty()) return;
        String garisT = "-".repeat(56);
        System.out.println("\n  [ Pesanan Saat Ini ]");
        System.out.println("  " + garisT);
 
        if (!pesanan.getMinumanItems().isEmpty()) {
            System.out.printf("  %-4s  %-32s  %s%n", "Kode", "Minuman", "Qty");
            System.out.println("  " + garisT);
            for (ItemPesanan ip : pesanan.getMinumanItems()) {
                System.out.printf("  %-4s  %-32s  %d%n",
                        ip.getMenu().getKode(), ip.getMenu().getNamaMenu(), ip.getQuantity());
            }
        }
 
        if (!pesanan.getMakananItems().isEmpty()) {
            System.out.println("  " + garisT);
            System.out.printf("  %-4s  %-32s  %s%n", "Kode", "Makanan", "Qty");
            System.out.println("  " + garisT);
            for (ItemPesanan ip : pesanan.getMakananItems()) {
                System.out.printf("  %-4s  %-32s  %d%n",
                        ip.getMenu().getKode(), ip.getMenu().getNamaMenu(), ip.getQuantity());
            }
        }
 
        System.out.println("  " + garisT + "\n");
    }
}