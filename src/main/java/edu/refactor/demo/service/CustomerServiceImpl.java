package edu.refactor.demo.service;

import edu.refactor.demo.dao.BillingAccountDAO;
import edu.refactor.demo.dao.CustomerDAO;
import edu.refactor.demo.exceptions.BillingAccountNotFoundException;
import edu.refactor.demo.exceptions.CustomerNotFoundException;
import edu.refactor.demo.exceptions.CustomerUniqueCredentialsException;
import edu.refactor.demo.model.BillingAccount;
import edu.refactor.demo.model.Customer;
import edu.refactor.demo.rest.CustomerRestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerDAO customerDAO;

    private final BillingAccountDAO billingAccountDAO;

    private static final Logger LOG = LoggerFactory.getLogger(CustomerServiceImpl.class);


    @Autowired
    public CustomerServiceImpl(CustomerDAO customerDAO, BillingAccountDAO billingAccountDAO) {
        this.customerDAO = customerDAO;
        this.billingAccountDAO = billingAccountDAO;
    }

    @Override
    @Transactional
    public List<Customer> getAllCustomers() {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Start getAllCustomers");
        }
        return customerDAO.findCustomerByStatusNotLike("delete");
    }

    @Override
    @Transactional
    public List<BillingAccount> billingAccounts(Long id) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Start billingAccounts for customer with id = {}", id);
        }
        Optional<Customer> customer = customerDAO.findById(id);
        if(!customer.isPresent()) {
            CustomerNotFoundException exc = new CustomerNotFoundException("Customer doesn't exist");
            LOG.error("Customer with id = {} was not found", id, exc);
            throw exc;
        }
        return customer.get().getBillingAccounts();
    }

    @Override
    @org.springframework.transaction.annotation.Transactional
    public Customer createCustomer(Customer customer) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Start createCustomer");
        }
        if (customerDAO.findCustomerByLoginAndEmail(customer.getLogin(), customer.getEmail()) != null) {
            CustomerUniqueCredentialsException exception = new CustomerUniqueCredentialsException("Create customer error");
            LOG.error("Customer with given credentials already exists", exception);
            throw exception;
        }
        Customer newCustomer = new Customer().copyFrom(customer);
        newCustomer = customerDAO.save(newCustomer);
        if (LOG.isDebugEnabled()) {
            LOG.debug("Start creating billing account for new customer");
        }
        BillingAccount billingAccount = new BillingAccount();
        billingAccount.setMoney(100);
        billingAccount.setPrimary(true);
        newCustomer.getBillingAccounts().add(billingAccount);
        billingAccount.setCustomer(newCustomer);


        return newCustomer;
    }

    @Override
    @Transactional
    public Customer getCustomerById(Long id) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Start getCustomerById for customer with id = {}", id);
        }
        Optional<Customer> customer = customerDAO.findById(id);
        if(!customer.isPresent()) {
            CustomerNotFoundException exception = new CustomerNotFoundException("Customer doesn't exist");
            LOG.error("Customer with id = {} was not found", id, exception);
            throw exception;
        }
        return customer.get();
    }

    @Override
    @Transactional
    public BillingAccount billingAccountForCustomer(Long id, Long baId) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Start billingAccountForCustomer for customer with id = {}, baId = {}", id, baId);
        }
        Optional<Customer> customer = customerDAO.findById(id);
        if(!customer.isPresent()) {
            CustomerNotFoundException exception = new CustomerNotFoundException("Customer doesn't exist");
            LOG.error("Customer with id = {} was not found", id, exception);
            throw exception;
        }

        Optional<BillingAccount> billingAccount = customer.get().getBillingAccounts()
                                                    .stream().filter(e -> e.getId().equals(baId)).findFirst();
        if(!billingAccount.isPresent()) {
            BillingAccountNotFoundException exception = new BillingAccountNotFoundException("BillingAccount doesn't exist");
            LOG.error("Customer with id = {} doesn't have billing account with id = {} ", id, baId, exception);
            throw exception;
        }
        return billingAccount.get();
    }

    @Override
    @Transactional
    public BillingAccount createBAForCustomer(BillingAccount billingAccount, Long id) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Start createBAForCustomer for customer with id = {}", id);
        }
        Optional<Customer> customer = customerDAO.findById(id);
        if(!customer.isPresent()) {
            CustomerNotFoundException exception = new CustomerNotFoundException("Customer doesn't exist");
            LOG.error("Customer with id = {} was not found", id, exception);
            throw exception;
        }
        billingAccount.setCustomer(customer.get());
        billingAccountDAO.save(billingAccount);
        return billingAccount;
    }
}
