package edu.refactor.demo.model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

@Entity
public class BillingAccount implements Serializable {
    public static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue
    @Column
    private Long id;
    @Column
    private boolean isPrimary;
    @ManyToOne
    @JoinColumn(name = "billing_account_id")
    private Customer customer;
    @Column
    private Instant createdDate;
    @Column
    private double money;

    public BillingAccount() {
        createdDate = Instant.now();
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isPrimary() {
        return isPrimary;
    }

    public void setPrimary(boolean primary) {
        isPrimary = primary;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }
}
