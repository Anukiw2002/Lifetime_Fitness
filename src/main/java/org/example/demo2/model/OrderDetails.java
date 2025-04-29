package org.example.demo2.model;

public class OrderDetails {
    private String productName;
    private float subtotal;
    private float total;

    public OrderDetails() {
    }

    public OrderDetails(String productName, float subtotal, float total) {
        this.productName = productName;
        this.subtotal = subtotal;
        this.total = total;
    }




    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public float getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(float subtotal) {
        this.subtotal = subtotal;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }


    public String getSubtotalFormatted() {
        return String.format("%.2f", subtotal);
    }

    public String getTotalFormatted() {
        return String.format("%.2f", total);
    }
}
