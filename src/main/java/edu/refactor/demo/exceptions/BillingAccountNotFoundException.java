package edu.refactor.demo.exceptions;

/**
 * @author rodin
 * Created 03.02.2020
 */
public class BillingAccountNotFoundException extends RuntimeException {

    public BillingAccountNotFoundException(String err){
        super(err);
    }
}
