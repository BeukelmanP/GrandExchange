package Classes;

public class Product {

    private int GTIN;
    private String naam;
    private String beschrijving;

    
    public Product(int GTIN, String naam, String beschrijving) {
        this.GTIN = GTIN;
        this.naam = naam;
        this.beschrijving = beschrijving;
    }
}
