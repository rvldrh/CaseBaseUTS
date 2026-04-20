package CaseBaseUTS.MataUang;


public class USD implements MataUang {
    // 1 USD = 15.000
    // 1 RP = 1/15 USD

    private static final double RATE = 15.0;

    @Override
    public double konversiFromIDR(double idr) {
        return idr / RATE;
    }

    @Override
    public String getNamaMataUang() {
        return "USD";
    }

    @Override
    public double getRate(){
        return RATE;
    }
}
