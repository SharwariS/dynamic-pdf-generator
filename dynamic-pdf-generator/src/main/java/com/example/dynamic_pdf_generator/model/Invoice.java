package com.example.dynamic_pdf_generator.model;

import lombok.Data;

import java.util.List;

@Data
public class Invoice {
    private String seller;
    private String sellerGstin;
    private String sellerAddress;
    private String buyer;
    private String buyerGstin;
    private String buyerAddress;
    private List<Item> items;


    public String getBuyer() {
        return buyer;
    }

    public String getSeller() {
        return seller;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public void setSellerGstin(String sellerGstin) {
        this.sellerGstin = sellerGstin;
    }

    public void setSellerAddress(String sellerAddress) {
        this.sellerAddress = sellerAddress;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public void setBuyerGstin(String buyerGstin) {
        this.buyerGstin = buyerGstin;
    }

    public void setBuyerAddress(String buyerAddress) {
        this.buyerAddress = buyerAddress;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public String getSellerGstin() {
        return sellerGstin;
    }

    public String getSellerAddress() {
        return sellerAddress;
    }

    public String getBuyerGstin() {
        return buyerGstin;
    }

    public String getBuyerAddress() {
        return buyerAddress;
    }

    @Data
    public static class Item {
        private String name;
        private String quantity;
        private double rate;
        private double amount;

        public String getName() {
            return name;
        }

        public String getQuantity() {
            return quantity;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }

        public void setRate(double rate) {
            this.rate = rate;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public double getRate() {
            return rate;
        }

        public double getAmount() {
            return amount;
        }
    }
}