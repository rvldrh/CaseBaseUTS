package CaseBaseUTS.MataUang;

public class IDR implements MataUang {
    private static final double RATE = 1.0;

    @Override
    public double konversiFromIDR(double idr) {
        return idr;
    }

    @Override
    public String getNamaMataUang() { return "IDR"; }

    @Override
    public double getRate() { return RATE; }
}
