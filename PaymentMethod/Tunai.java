package CaseBaseUTS.PaymentMethod;

public class Tunai implements PilihanPembayaran {

    @Override
    public double hitungDiskon(double jumlah) {
        return 0.0;
    }

    @Override
    public double getBiayaAdmin() {
        return 0.0;
    }

    @Override
    public boolean cekSaldo(double saldo, double jumlah) {
        return true;
    }

    @Override
    public String getNamaPembayaran(){
        return "Tunai";
    }

    @Override
    public double getDiskonRate(){
        return 0.0;
    }
}