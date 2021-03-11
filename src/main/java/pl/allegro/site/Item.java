package pl.allegro.site;

public class Item {

    private final String name;
    private final double oldPrice;
    private final double newPrice;
    private final double discount;

    public Item(String name, double oldPrice, double newPrice) {
        this.name = name;
        this.oldPrice = oldPrice;
        this.newPrice = newPrice;
        discount = Math.floor((1 - newPrice / oldPrice) * 100);
    }

    @Override
    public String toString() {
        return name + ";" + oldPrice + ";" + newPrice + ";" + discount;
    }
}