package Classes;

public class Product {

    private String GTIN;
    private String name;
    private String description;

    
    public Product(String GTIN, String name, String description) {
        this.GTIN = GTIN;
        this.name = name;
        this.description = description;
    }
    
    public String getGTIN(){
        return this.GTIN;
    }
    
    public String getName(){
        return this.name;
    }
    
    public String getDescription(){
        return this.description;
    }
}
