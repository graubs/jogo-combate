/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.unijorge.exception;

/**
 *
 * @author Glauber
 */
public class PecaException extends Exception{
    
    public PecaException(String message){
        super(message);
    }

    public PecaException(Throwable cause) {
        super(cause);
    }

    public PecaException() {
    }

    public PecaException(String message, Throwable cause) {
        super(message, cause);
    }
    
}
