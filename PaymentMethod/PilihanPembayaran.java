package CaseBaseUTS.PaymentMethod;

public interface PilihanPembayaran {
    double hitungDiskon(double jumlah);
    double getBiayaAdmin();
    boolean cekSaldo(double saldo, double jumlah);
    String getNamaPembayaran();
    double getDiskonRate();
}