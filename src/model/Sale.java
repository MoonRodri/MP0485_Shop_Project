package model;

import java.util.List;

public class Sale {

    private Client client;
    private List<Product> products;
    private double amount;

    public Sale(Client client, List<Product> products, double amount) {
        this.client = client;
        this.products = products;
        this.amount = amount;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "VENTA: Cliente: " + client + ", Productos: " + products + ", Cantidad total: " + amount;
    }
}