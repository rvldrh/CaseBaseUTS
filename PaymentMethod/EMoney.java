package CaseBaseUTS.PaymentMethod;

public class EMoney implements PilihanPembayaran {
    private double saldo;
    private static final double BIAYA_ADMIN = 20.0;

    public EMoney(double saldo){
        this.saldo = saldo;
    }

    public double getSaldo(){
        return saldo;
    }

    @Override
    public double hitungDiskon(double jumlah) {
        return jumlah * 0.7;
    }

    @Override
    public double getBiayaAdmin() {
        return BIAYA_ADMIN;
    }

    @Override
    public boolean cekSaldo(double saldo, double jumlah) {
        return saldo >= jumlah;
    }

    @Override
    public String getNamaPembayaran(){
        return "E-Money";
    }

    @Override
    public double getDiskonRate(){
        return 0.07;
    }
}