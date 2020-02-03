package edu.refactor.demo.dao;

import edu.refactor.demo.model.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerDAO extends CrudRepository<Customer, Long> {
    Customer findCustomerByLoginAndEmail(String login, String customer);

    List<Customer> findCustomerByStatusNotLike(String status);
}