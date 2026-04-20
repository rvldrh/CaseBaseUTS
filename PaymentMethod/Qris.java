package CaseBaseUTS.PaymentMethod;

public class Qris implements PilihanPembayaran {
    private double saldo;

    public Qris(double saldo){
        this.saldo = saldo;
    }

    public double getSaldo(){
        return saldo;
    }

    @Override
    public double hitungDiskon(double jumlah) {
        return jumlah * 0.05;
    }

    @Override
    public double getBiayaAdmin() {
        return 0.0;
    }

    @Override
    public boolean cekSaldo(double saldo, double jumlah) {
        return saldo >= jumlah;
    }

    @Override
    public String getNamaPembayaran(){
        return "Qris";
    }

    @Override
    public double getDiskonRate(){
        return 0.05;
    }
}
