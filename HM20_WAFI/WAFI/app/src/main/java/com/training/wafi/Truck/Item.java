package com.training.wafi.Truck;

public class Item {
    private String id;
    private String name;
    private String description;
    private double costBeforeTax;
    private double tax;
    private double total;
    private String dateAdded;
    private String addedBy;
    private int quantity;

    private String manufacturer;
    private String qrCodeUrl;



    public Item() { // Default constructor for Firebase
    }

    public Item(String name, String description, double costBeforeTax, double tax, double total, String dateAdded, String addedBy, String manufacturer) {
        this.name = name;
        this.description = description;
        this.costBeforeTax = costBeforeTax;
        this.tax = tax;
        this.total = total;
        this.dateAdded = dateAdded;
        this.addedBy = addedBy;
        this.manufacturer = manufacturer;
    }

    // Getters and setters for all fields...


    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public double getCostBeforeTax() {
        return costBeforeTax;
    }

    public double getTax() {
        return tax;
    }

    public double getTotal() {
        return total;
    }

    public String getDateAdded() {
        return dateAdded;
    }

    public String getAddedBy() {
        return addedBy;
    }

    public String getManufacturer(){

        return manufacturer;
    }

    public String getQrCodeUrl() {
        return qrCodeUrl;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQrCodeUrl(String qrCodeUrl) {
        this.qrCodeUrl = qrCodeUrl;
    }

    public void setId(String id) {
        this.id = id;
    }


    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
