package edu.refactor.demo.exceptions;

/**
 * @author rodin
 * Created 03.02.2020
 */
public class CustomerUniqueCredentialsException extends RuntimeException {
    public CustomerUniqueCredentialsException(String err){
        super(err);
    }
}
