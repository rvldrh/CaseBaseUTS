package CaseBaseUTS.Pesanan;

import CaseBaseUTS.Makanan.Menu;

public class ItemPesanan {
    private Menu menu;
    private int quantity;

    public ItemPesanan(Menu menu, int quantity) {
        this.menu = menu;
        this.quantity = quantity;
    }

    public Menu getMenu(){
        return menu;
    }

    public int getQuantity(){
        return quantity;
    }

    public void setQuantity(int quantity){
        this.quantity = quantity;
    }

    public double getSubtotal() {
        return menu.getHarga() * quantity;
    }

    public double getPajak() {
        return menu.getHarga() * menu.hitungPajak() * quantity;
    }

    public boolean equals(Object o){
        if(this == o) return true;
        if(!(o instanceof ItemPesanan)) return false;
        ItemPesanan other = (ItemPesanan) o;
        return this.menu.getKode().equalsIgnoreCase(other.menu.getKode());
    }

    @Override
    public int hashCode(){
        return menu.getKode().toUpperCase().hashCode();
    }
}