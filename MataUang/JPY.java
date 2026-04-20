package CaseBaseUTS.MataUang;

public class JPY implements MataUang {
    // 10 JPY = 1 IDR  ->  1 IDR = 10 JPY
    private static final double RATE = 10.0;

    @Override
    public double konversiFromIDR(double idr) {
        return idr * RATE;
    }

    @Override
    public String getNamaMataUang() { return "JPY"; }

    @Override
    public double getRate() { return RATE; } 
}
