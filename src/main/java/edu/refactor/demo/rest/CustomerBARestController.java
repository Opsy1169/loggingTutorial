package edu.refactor.demo.rest;

import edu.refactor.demo.model.BillingAccount;
import edu.refactor.demo.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

@RestController
public class CustomerBARestController {
    private static final Logger LOG = LoggerFactory.getLogger(CustomerBARestController.class);
    private final CustomerService customerService;

    @Autowired
    public CustomerBARestController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @RequestMapping(value = "/customer/{id}/ba/", method = RequestMethod.GET)
    ResponseEntity<List<BillingAccount>> loadAllBAForCustomer(@PathVariable Long id) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Start loadAllBAForCustomer for customer with id = {}", id);
        }
        return ResponseEntity.ok(customerService.billingAccounts(id));
    }

    @RequestMapping(value = "/customer/*/ba/", method = RequestMethod.OPTIONS)
    ResponseEntity<?> options() {
        return ResponseEntity.ok().allow(POST, GET).build();
    }

    @RequestMapping(value = "/customer/{id}/ba/{baId}/", method = RequestMethod.GET)
    ResponseEntity<BillingAccount> billingAccountForCustomer(@PathVariable(name = "id") Long id,
                                                             @PathVariable(name = "baId") Long baId) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Start loadAllBAForCustomer for customer with id = {}, baId = {}", id, baId);
        }
        return ResponseEntity.ok(customerService.billingAccountForCustomer(id, baId));
    }

    @RequestMapping(value = "/customer/{id}/ba/", method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity<BillingAccount> create(@PathVariable Long id, @RequestBody BillingAccount billingAccount) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Start create for billingAccount {} for customer {}", billingAccount, id);
        }
        BillingAccount newBillingAccount = customerService.createBAForCustomer(billingAccount, id);
        UriComponents uriComponents = UriComponentsBuilder.newInstance()
                .path("/customer/{id}/ba/{baId}/")
                .buildAndExpand(id, newBillingAccount.getId());

        return ResponseEntity.created(uriComponents.toUri()).body(newBillingAccount);
    }
}
