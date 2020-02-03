package edu.refactor.demo.dao;

import edu.refactor.demo.model.BillingAccount;
import edu.refactor.demo.model.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BillingAccountDAO extends CrudRepository<BillingAccount, Long> {
    BillingAccount findBillingAccountByCustomerAndId(Customer customer, Long id);
}