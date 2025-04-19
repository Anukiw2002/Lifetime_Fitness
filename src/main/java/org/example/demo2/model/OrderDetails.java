package org.example.demo2.model;

public class OrderDetails {
    private String productName;
    private float subtotal;
    private float shipping;
    private float tax;
    private float total;

    public OrderDetails() {
    }

    public OrderDetails(String productName, float subtotal, float shipping, float tax, float total) {
        this.productName = productName;
        this.subtotal = subtotal;
        this.shipping = shipping;
        this.tax = tax;
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

    public float getShipping() {
        return shipping;
    }

    public void setShipping(float shipping) {
        this.shipping = shipping;
    }

    public float getTax() {
        return tax;
    }

    public void setTax(float tax) {
        this.tax = tax;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    // Optional: Formatted String getters for UI display
    public String getSubtotalFormatted() {
        return String.format("%.2f", subtotal);
    }

    public String getShippingFormatted() {
        return String.format("%.2f", shipping);
    }

    public String getTaxFormatted() {
        return String.format("%.2f", tax);
    }

    public String getTotalFormatted() {
        return String.format("%.2f", total);
    }
}
