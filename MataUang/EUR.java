package CaseBaseUTS.MataUang;

public class EUR implements MataUang {
    // 1 EUR = 14 IDR  ->  1 IDR = 1/14 EUR
    private static final double RATE = 14.0;
 
    @Override
    public double konversiFromIDR(double idr) { return idr / RATE; }
 
    @Override
    public String getNamaMataUang() { return "EUR"; }
 
    @Override
    public double getRate() { return RATE; }
}
 