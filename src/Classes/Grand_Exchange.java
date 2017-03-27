package Classes;

import Classes.User;
import java.util.*;

public class Grand_Exchange {

    Collection<Product> products;
    Collection<User> users;

    /**
     * adds user tot he collection of users
     *
     * @param user : may not be null
     */
    public void addUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException();
        } else {
            users.add(user);
        }
    }

    /**
     * removes user from collection of users
     *
     * @param user : may not be null
     */
    public void removeUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException();
        } else {
            users.remove(user);
        }
    }

    /**
     * Adds product to collection of products
     *
     * @param product : may not be null
     */
    public void addProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException();
        } else {
            products.add(product);
        }
    }

    /**
     * removes product from collection of products
     *
     * @param product : may not be null
     */
    public void removeProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException();
        } else {
            products.remove(product);
        }
    }

    /**
     * Checks user login
     *
     * @param username : may not be empty nor null
     * @param password : may not be empty nor null
     */
    public void login(String username, String password) {

    }

    /**
     * logout of user who is logged in
     *
     */
    public void logout() {

    }

    /**
     * handles transaction
     *
     * @param transaction : may not be null
     */
    public void handleTransaction(Transaction transaction) {

    }

    /**
     * Returns list of all 'official' products
     *
     * @return List<Product>
     */
    public Collection<Product> getProducts() {
        return products;
    }

}
