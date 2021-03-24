package com.tothenew.entities.user;

import com.tothenew.entities.product.Product;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import java.util.ArrayList;
import java.util.List;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class Seller extends User {
    private Double gst;
    private String companyName;
    private String companyContact;

    @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL)
    private List<Product> products = new ArrayList<>();

    public Double getGst() {
        return gst;
    }

    public Seller setGst(Double gst) {
        this.gst = gst;
        return this;
    }

    public String getCompanyName() {
        return companyName;
    }

    public Seller setCompanyName(String companyName) {
        this.companyName = companyName;
        return this;
    }

    public String getCompanyContact() {
        return companyContact;
    }

    public Seller setCompanyContact(String companyContact) {
        this.companyContact = companyContact;
        return this;
    }

    public List<Product> getProducts() {
        return products;
    }

    public Seller setProducts(List<Product> products) {
        this.products = products;
        return this;
    }
}