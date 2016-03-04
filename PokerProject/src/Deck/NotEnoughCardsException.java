/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Deck;

/**
 *Thrown when Deck is asked to deal more cards than it contains.
 * @author Kory Bryson
 */
public class NotEnoughCardsException extends Exception{
    
    public NotEnoughCardsException(){
        super();
    }
    public NotEnoughCardsException(String message){
        super(message);
    }
    
}
