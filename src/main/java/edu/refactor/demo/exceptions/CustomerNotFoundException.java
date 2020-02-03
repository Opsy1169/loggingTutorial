package edu.refactor.demo.exceptions;

/**
 * @author rodin
 * Created 03.02.2020
 */
public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException(String err){
        super(err);
    }
}
