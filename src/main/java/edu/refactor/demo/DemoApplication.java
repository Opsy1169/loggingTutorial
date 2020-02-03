package edu.refactor.demo;

import edu.refactor.demo.model.BillingAccount;
import edu.refactor.demo.model.Customer;
import edu.refactor.demo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import java.util.List;

@SpringBootApplication
@EnableTransactionManagement
@Configuration
public class DemoApplication implements CommandLineRunner {
    @Autowired
    CustomerService customerService;

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }

    @Override
    public void run(String... args) throws Exception {
        {
            Customer customer = new Customer();
            customer.setLogin("test");
            customer.setEmail("test@ya.ru");
            customer.setStatus("active");
            customerService.createCustomer(customer);
        }
        {
            Customer customer = new Customer();
            customer.setLogin("test2");
            customer.setEmail("test2@ya.ru");
            customer.setStatus("delete");
            customerService.createCustomer(customer);
        }
        List<Customer> allCustomers = customerService.getAllCustomers();
        List<BillingAccount> billingAccounts = customerService.billingAccounts(1L);
        if (allCustomers.size() != 1 && billingAccounts.size() != 1) {
            throw new IllegalStateException();
        }
    }
}