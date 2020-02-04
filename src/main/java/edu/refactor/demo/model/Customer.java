package edu.refactor.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Customer implements Serializable {
    public static final long serialVersionUID = 1L;
    @Column
    private String category;
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BillingAccount> billingAccounts = new ArrayList<>();
    @Column
    @NotNull
    private String login;
    @Column
    @NotNull
    @Email
    private String email;
    @Column
    private Instant registration;
    @Column
    private String status;
    @Id
    @GeneratedValue
    @Column
    private Long id;

    public Customer() {
        this.registration = Instant.now();
    }

    public Customer copyFrom(Customer customer) {
        this.setEmail(customer.getEmail());
        this.setLogin(customer.getLogin());
        this.setCategory(customer.getCategory());
        this.setStatus(customer.getStatus());

        this.getBillingAccounts().clear();
        this.getBillingAccounts().addAll(customer.getBillingAccounts());

        return this;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Instant getRegistration() {
        return registration;
    }

    public void setRegistration(Instant registration) {
        this.registration = registration;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public List<BillingAccount> getBillingAccounts() {
        return billingAccounts;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "category='" + category + '\'' +
                ", registration=" + registration +
                ", status='" + status + '\'' +
                '}';
    }
}
