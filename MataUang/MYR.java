package CaseBaseUTS.MataUang;

public class MYR implements MataUang {
    // 1 MYR = 4 IDR  ->  1 IDR = 1/4 MYR
    private static final double RATE = 4.0;
 
    @Override
    public double konversiFromIDR(double idr) { return idr / RATE; }
 
    @Override
    public String getNamaMataUang() { return "MYR"; }
 
    @Override
    public double getRate() { return RATE; }
}