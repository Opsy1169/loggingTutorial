package edu.refactor.demo.service;

import edu.refactor.demo.model.BillingAccount;
import edu.refactor.demo.model.Customer;

import javax.transaction.Transactional;
import java.util.List;

public interface CustomerService {
    List<Customer> getAllCustomers();

    List<BillingAccount> billingAccounts(Long id);

    Customer createCustomer(Customer customer);

    Customer getCustomerById(Long id);

    BillingAccount billingAccountForCustomer(Long id, Long baId);

    BillingAccount createBAForCustomer(BillingAccount billingAccount, Long id);
}
