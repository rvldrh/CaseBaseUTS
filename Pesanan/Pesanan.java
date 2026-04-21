package CaseBaseUTS.Pesanan;

import java.util.LinkedHashSet;

public class Pesanan {

    private static final int MAX_JENIS = 5;

    private LinkedHashSet<ItemPesanan> minumanItems = new LinkedHashSet<>();
    private LinkedHashSet<ItemPesanan> makananItems = new LinkedHashSet<>();

    private CaseBaseUTS.MataUang.MataUang          mataUangDipilih;
    private CaseBaseUTS.PaymentMethod.PilihanPembayaran pembayaranDipilih;


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


    public void setMataUang(CaseBaseUTS.MataUang.MataUang mu)             { this.mataUangDipilih    = mu; }
    public void setPembayaran(CaseBaseUTS.PaymentMethod.PilihanPembayaran pb)   { this.pembayaranDipilih  = pb; }


    public LinkedHashSet<ItemPesanan> getMinumanItems() { return minumanItems; }
    public LinkedHashSet<ItemPesanan> getMakananItems()  { return makananItems; }
    public CaseBaseUTS.MataUang.MataUang          getMataUangDipilih()        { return mataUangDipilih; }
    public CaseBaseUTS.PaymentMethod.PilihanPembayaran getPembayaranDipilih()      { return pembayaranDipilih; }

    public boolean isEmpty() {
        return minumanItems.isEmpty() && makananItems.isEmpty();
    }

    public void cetakKuitansi() {
        final String GARIS   = "=".repeat(68);
        final String GARIS_T = "-".repeat(68);
        final String FMT_HDR = "%-4s  %-34s  %3s  %10s  %5s%n";

        String mu        = mataUangDipilih.getNamaMataUang();
        boolean isIDR    = mu.equals("IDR");
        boolean isJPY    = mu.equals("JPY");

        java.util.function.Function<Double, String> fmt = (val) -> {
            double converted = mataUangDipilih.konversiFromIDR(val);
            if (isIDR)       return String.format("Rp %,12.3f", converted);
            else if (isJPY)  return String.format("%,12.0f %s", converted, mu);
            else             return String.format("%,12.4f %s", converted, mu);
        };

        java.util.function.Function<Double, String> fmtCol = (val) -> {
            double converted = mataUangDipilih.konversiFromIDR(val);
            if (isIDR)       return String.format("%10.3f", converted);
            else if (isJPY)  return String.format("%10.0f", converted);
            else             return String.format("%10.4f", converted);
        };

        java.util.function.Function<Double, String> fmtInline = (val) -> {
            double converted = mataUangDipilih.konversiFromIDR(val);
            if (isIDR)       return String.format("Rp %,.3f",  converted);
            else if (isJPY)  return String.format("%,.0f %s",  converted, mu);
            else             return String.format("%,.4f %s",  converted, mu);
        };

        System.out.println("\n" + GARIS);
        System.out.printf("%s%n", centerText("*** KUITANSI KOHISOP ***", 68));
        System.out.printf("%s%n", centerText("Warung Kopi & Makanan Terbaik Kota", 68));
        System.out.println(GARIS);
        System.out.printf("  Mata uang pembayaran : %s%n", mu);
        System.out.println(GARIS_T);

        System.out.printf(FMT_HDR, "Kode", "Nama Menu", "Qty", "Subtotal", "Pajak");
        System.out.println(GARIS_T);

        if (!minumanItems.isEmpty()) {
            System.out.println("[ MINUMAN ]");
            for (ItemPesanan ip : minumanItems) {
                double pajakPct = ip.getMenu().hitungPajak() * 100;
                String namaTrunc = truncate(ip.getMenu().getNamaMenu(), 34);
                System.out.printf("%-4s  %-34s  %3d  %s  %4.0f%%%n",
                        ip.getMenu().getKode(),
                        namaTrunc,
                        ip.getQuantity(),
                        fmtCol.apply(ip.getSubtotal()),
                        pajakPct);
                System.out.printf("      Harga/porsi : %-18s | Pajak item : %s%n",
                        fmtInline.apply(ip.getMenu().getHarga()),
                        fmtInline.apply(ip.getPajak()));
            }
        }

        if (!makananItems.isEmpty()) {
            System.out.println(GARIS_T);
            System.out.println("[ MAKANAN ]");
            for (ItemPesanan ip : makananItems) {
                double pajakPct = ip.getMenu().hitungPajak() * 100;
                String namaTrunc = truncate(ip.getMenu().getNamaMenu(), 34);
                System.out.printf("%-4s  %-34s  %3d  %s  %4.0f%%%n",
                        ip.getMenu().getKode(),
                        namaTrunc,
                        ip.getQuantity(),
                        fmtCol.apply(ip.getSubtotal()),
                        pajakPct);
                System.out.printf("      Harga/porsi : %-18s | Pajak item : %s%n",
                        fmtInline.apply(ip.getMenu().getHarga()),
                        fmtInline.apply(ip.getPajak()));
            }
        }

        System.out.println(GARIS_T);

        double totalSebelumPajak = getTotalSebelumPajak();
        double totalPajak        = getTotalPajak();
        double totalSetelahPajak = getTotalSetelahPajak();
        double diskon            = pembayaranDipilih.hitungDiskon(totalSetelahPajak);
        double biayaAdmin        = pembayaranDipilih.getBiayaAdmin();
        double totalBayar        = totalSetelahPajak - diskon + biayaAdmin;

        System.out.printf("  %-32s : %s%n", "Total sebelum pajak",  fmt.apply(totalSebelumPajak));
        System.out.printf("  %-32s : %s%n", "Total pajak",          fmt.apply(totalPajak));
        System.out.printf("  %-32s : %s%n", "Total setelah pajak",  fmt.apply(totalSetelahPajak));
        System.out.println(GARIS_T);

        System.out.printf("  %-32s : %s%n", "Metode pembayaran", pembayaranDipilih.getNamaPembayaran());
        System.out.printf("  %-32s : %s%n",
        String.format("Diskon (%.0f%%)", pembayaranDipilih.getDiskonRate() * 100), fmt.apply(diskon));
        System.out.printf("  %-32s : %s%n", "Biaya admin", fmt.apply(biayaAdmin));
        System.out.println(GARIS_T);
        System.out.printf("  %-32s : %s%n", "TOTAL BAYAR (" + mu + ")", fmt.apply(totalBayar));

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