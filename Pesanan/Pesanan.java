package CaseBaseUTS.Pesanan;

import CaseBaseUTS.MataUang.MataUang;
import CaseBaseUTS.PaymentMethod.PilihanPembayaran;
import java.util.LinkedHashSet;

public class Pesanan {
 
    private static final int MAX_JENIS = 5;
 
    // LinkedHashSet: tidak ada duplikat kode, urutan insert terjaga
    private LinkedHashSet<ItemPesanan> minumanItems = new LinkedHashSet<>();
    private LinkedHashSet<ItemPesanan> makananItems = new LinkedHashSet<>();
 
    private MataUang          mataUangDipilih;
    private PilihanPembayaran pembayaranDipilih;
 
 
    public boolean tambahMinuman(ItemPesanan item) {
        if (minumanItems.size() >= MAX_JENIS) return false;
        return minumanItems.add(item); 
    }
 
    public boolean tambahMakanan(ItemPesanan item) {
        if (makananItems.size() >= MAX_JENIS) return false;
        return makananItems.add(item);
    }
 
    public boolean sudahAdaMinuman(String kode) {
        for (ItemPesanan ip : minumanItems)
            if (ip.getMenu().getKode().equalsIgnoreCase(kode)) return true;
        return false;
    }
 
    public boolean sudahAdaMakanan(String kode) {
        for (ItemPesanan ip : makananItems)
            if (ip.getMenu().getKode().equalsIgnoreCase(kode)) return true;
        return false;
    }
 
 
    public double getTotalSebelumPajak() {
        double total = 0;
        for (ItemPesanan ip : minumanItems) total += ip.getSubtotal();
        for (ItemPesanan ip : makananItems) total += ip.getSubtotal();
        return total;
    }
 
    public double getTotalPajak() {
        double total = 0;
        for (ItemPesanan ip : minumanItems) total += ip.getPajak();
        for (ItemPesanan ip : makananItems) total += ip.getPajak();
        return total;
    }
 
    public double getTotalSetelahPajak() {
        return getTotalSebelumPajak() + getTotalPajak();
    }
 
 
    public void setMataUang(MataUang mu)             { this.mataUangDipilih    = mu; }
    public void setPembayaran(PilihanPembayaran pb)   { this.pembayaranDipilih  = pb; }
 
 
    public LinkedHashSet<ItemPesanan> getMinumanItems() { return minumanItems; }
    public LinkedHashSet<ItemPesanan> getMakananItems()  { return makananItems; }
    public MataUang          getMataUangDipilih()        { return mataUangDipilih; }
    public PilihanPembayaran getPembayaranDipilih()      { return pembayaranDipilih; }
 
    public boolean isEmpty() {
        return minumanItems.isEmpty() && makananItems.isEmpty();
    }
  
   public void cetakKuitansi() {
        final String GARIS   = "=".repeat(68);
        final String GARIS_T = "-".repeat(68);
        final String FMT_ROW = "%-4s  %-34s  %3d  %10.2f  %4.0f%%%n";
        final String FMT_HDR = "%-4s  %-34s  %3s  %10s  %5s%n";
        final String FMT_SUB = "      Harga/porsi : Rp %,-8.2f | Pajak item : Rp %,.2f%n";
        final String FMT_KET = "  %-32s : Rp %,12.2f%n";
 
        System.out.println("\n" + GARIS);
        System.out.printf("%s%n", centerText("*** KUITANSI KOHISOP ***", 68));
        System.out.printf("%s%n", centerText("Warung Kopi & Makanan Terbaik Kota", 68));
        System.out.println(GARIS);
 
        System.out.printf(FMT_HDR, "Kode", "Nama Menu", "Qty", "Subtotal", "Pajak");
        System.out.println(GARIS_T);
 
        if (!minumanItems.isEmpty()) {
            System.out.println("[ MINUMAN ]");
            for (ItemPesanan ip : minumanItems) {
                double pajak    = ip.getPajak();
                double subtotal = ip.getSubtotal();
                double pajakPct = ip.getMenu().hitungPajak() * 100;
                String nama = truncate(ip.getMenu().getNamaMenu(), 34);
                System.out.printf(FMT_ROW,
                        ip.getMenu().getKode(), nama,
                        ip.getQuantity(), subtotal, pajakPct);
                System.out.printf(FMT_SUB, ip.getMenu().getHarga(), pajak);
            }
        }
 
        if (!makananItems.isEmpty()) {
            System.out.println(GARIS_T);
            System.out.println("[ MAKANAN ]");
            for (ItemPesanan ip : makananItems) {
                double pajak    = ip.getPajak();
                double subtotal = ip.getSubtotal();
                double pajakPct = ip.getMenu().hitungPajak() * 100;
                String nama = truncate(ip.getMenu().getNamaMenu(), 34);
                System.out.printf(FMT_ROW,
                        ip.getMenu().getKode(), nama,
                        ip.getQuantity(), subtotal, pajakPct);
                System.out.printf(FMT_SUB, ip.getMenu().getHarga(), pajak);
            }
        }
 
        System.out.println(GARIS_T);
 
        double totalSebelumPajak = getTotalSebelumPajak();
        double totalPajak        = getTotalPajak();
        double totalSetelahPajak = getTotalSetelahPajak();
        double diskon            = pembayaranDipilih.hitungDiskon(totalSetelahPajak);
        double biayaAdmin        = pembayaranDipilih.getBiayaAdmin();
        double totalBayar        = totalSetelahPajak - diskon + biayaAdmin;
 
        System.out.printf(FMT_KET, "Total sebelum pajak",    totalSebelumPajak);
        System.out.printf(FMT_KET, "Total pajak",            totalPajak);
        System.out.printf(FMT_KET, "Total setelah pajak",    totalSetelahPajak);
        System.out.println(GARIS_T);
 
        System.out.printf("  %-32s : %s%n",
                "Metode pembayaran", pembayaranDipilih.getNamaPembayaran());
        System.out.printf("  %-32s : Rp %,12.2f%n",
                String.format("Diskon (%.0f%%)", pembayaranDipilih.getDiskonRate() * 100), diskon);
        System.out.printf(FMT_KET, "Biaya admin", biayaAdmin);
        System.out.println(GARIS_T);
        System.out.printf(FMT_KET, "TOTAL BAYAR (IDR)", totalBayar);
 
        System.out.println(GARIS_T);
        if (mataUangDipilih == null) {
            System.out.printf("  %-32s : IDR%n", "Mata uang pembayaran");
            System.out.printf(FMT_KET, "Total sebelum pajak (IDR)", totalSebelumPajak);
            System.out.printf(FMT_KET, "TOTAL BAYAR (IDR)",         totalBayar);
        } else {
            String nama = mataUangDipilih.getNamaMataUang();
            System.out.printf("  %-32s : %s%n", "Mata uang pembayaran", nama);
            if (nama.equals("JPY")) {
                System.out.printf("  %-32s : %,.0f %s%n",
                        "Total sebelum pajak (" + nama + ")",
                        mataUangDipilih.konversiFromIDR(totalSebelumPajak), nama);
                System.out.printf("  %-32s : %,.0f %s%n",
                        "TOTAL BAYAR (" + nama + ")",
                        mataUangDipilih.konversiFromIDR(totalBayar), nama);
            } else {
                System.out.printf("  %-32s : %,.4f %s%n",
                        "Total sebelum pajak (" + nama + ")",
                        mataUangDipilih.konversiFromIDR(totalSebelumPajak), nama);
                System.out.printf("  %-32s : %,.4f %s%n",
                        "TOTAL BAYAR (" + nama + ")",
                        mataUangDipilih.konversiFromIDR(totalBayar), nama);
            }
        }
 
        System.out.println(GARIS);
        System.out.printf("%s%n", centerText("Terima kasih dan silakan datang kembali!", 68));
        System.out.printf("%s%n", centerText("~~ KohiSop ~~", 68));
        System.out.println(GARIS);
    }

        private String centerText(String text, int width) {
        if (text.length() >= width) return text;
        int pad = (width - text.length()) / 2;
        return " ".repeat(pad) + text;
    }
 
    private String truncate(String text, int maxLen) {
        if (text.length() <= maxLen) return text;
        return text.substring(0, maxLen - 2) + "..";
    }
}