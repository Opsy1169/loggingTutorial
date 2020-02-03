package edu.refactor.demo.rest;

import edu.refactor.demo.model.Customer;
import edu.refactor.demo.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import java.util.List;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

@RestController
public class CustomerRestController {
    private static final Logger LOG = LoggerFactory.getLogger(CustomerRestController.class);

    private final CustomerService customerService;

    @Autowired
    public CustomerRestController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @RequestMapping(value = "/customer", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<List<Customer>> getAll() {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Start getAll");
        }
        return ResponseEntity.ok(customerService.getAllCustomers());
    }

    @RequestMapping(value = "/customer/", method = RequestMethod.OPTIONS)
    ResponseEntity<?> options() {
        return ResponseEntity.ok().allow(POST, GET).build();
    }

    @RequestMapping(value = "/customer/{id}/", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<Customer> getCustomer(@PathVariable Long id) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Start getCustomer with id = {}", id);
        }
        return ResponseEntity.ok(customerService.getCustomerById(id));
    }

    @RequestMapping(value = "/customer/", method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity<Customer> create(@RequestBody Customer customer) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Start create customer {}", customer);
        }
        Customer newCustomer = customerService.createCustomer(customer);
        UriComponents uriComponents =
                UriComponentsBuilder.newInstance().path("/customer/{id}/").buildAndExpand(newCustomer.getId());

        return ResponseEntity.created(uriComponents.toUri()).body(newCustomer);
    }
}
