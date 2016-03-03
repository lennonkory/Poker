/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HandRankings;

import Deck.StandardCard;
import Deck.StandardCard.Suit;
import static Deck.StandardCard.Suit.*;
import static Deck.StandardCard.Value.*;
import static HandRankings.Rank.*;
import java.util.HashMap;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author kory
 */
public class RankHandTest {
    
    public RankHandTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getStraight method, of class RankHand.
     */
    @Test
    public void testGetStraight() {
        System.out.println("getStraight");
        Hand hand = new Hand();
        
        hand.addCard(new StandardCard(CLUBS,FOUR));
        hand.addCard(new StandardCard(CLUBS,THREE));
        hand.addCard(new StandardCard(CLUBS,FIVE));
        hand.addCard(new StandardCard(HEARTS,ACE));
        hand.addCard(new StandardCard(CLUBS,TWO));
        
        hand.sortHandHigh();
        
        Rank expResult = STRAIGHT;
        Rank result = RankHand.getStraight(hand).getRank();

        assertEquals(expResult, result);
       
    }
    
    @Test
    public void testGetFlush() {
        System.out.println("getFlush");
        Hand hand = new Hand();
        
        hand.addCard(new StandardCard(CLUBS,FOUR));
        hand.addCard(new StandardCard(CLUBS,THREE));
        hand.addCard(new StandardCard(CLUBS,FIVE));
        hand.addCard(new StandardCard(CLUBS,KING));
        hand.addCard(new StandardCard(CLUBS,TWO));
        
        hand.sortHandHigh();
        
        HashMap<Suit, Integer> map = RankHand.hashHand(hand)[1];
        
        Rank expResult = FLUSH;
        Rank result = RankHand.getFlush(map,hand).getRank();
        assertEquals(expResult, result);
       
    }

    /**
     * Test of getOnlyOneSuit method, of class RankHand.
     */
    @Test
    public void testGetOnlyOneSuit() {
        System.out.println("getOnlyOneSuit");
        Hand hand = new Hand();
        
        hand.addCard(new StandardCard(CLUBS,FOUR));
        hand.addCard(new StandardCard(CLUBS,THREE));
        hand.addCard(new StandardCard(CLUBS,FIVE));
        hand.addCard(new StandardCard(CLUBS,KING));
        hand.addCard(new StandardCard(CLUBS,TWO));
        
        hand.sortHandHigh();

        Suit expResult = CLUBS ;
        Suit result = RankHand.getOnlyOneSuit(hand, CLUBS).get(0).getSuit();
        assertEquals(expResult, result);
    }

    /**
     * Test of getStraightFlush method, of class RankHand.
     */
    @Test
    public void testGetStraightFlush() {
        System.out.println("getStraightFlush");    
        Hand hand = new Hand();
        
        hand.addCard(new StandardCard(CLUBS,FOUR));
        hand.addCard(new StandardCard(CLUBS,THREE));
        hand.addCard(new StandardCard(CLUBS,FIVE));
        hand.addCard(new StandardCard(CLUBS,SIX));
        hand.addCard(new StandardCard(CLUBS,TWO));
        
        hand.sortHandHigh();
        
        Rank expResult = STRAIGHTFLUSH;
        HandValue result = RankHand.getStraightFlush(CLUBS, hand);
        assertEquals(expResult, result.rank);
    }

    /**
     * Test of getKind method, of class RankHand.
     */
    @Test
    public void testGetKind() {
        System.out.println("getKind");
        
        Hand hand = new Hand();
        
        hand.addCard(new StandardCard(CLUBS,KING));
        hand.addCard(new StandardCard(CLUBS,THREE));
        hand.addCard(new StandardCard(CLUBS,FIVE));
        hand.addCard(new StandardCard(CLUBS,KING));
        hand.addCard(new StandardCard(CLUBS,KING));
             
        HashMap<StandardCard.Value, Integer> valueHash = RankHand.hashHand(hand)[0];
        Rank expResult = THREEOFAKIND;
        HandValue result = RankHand.getKind(valueHash);
        assertEquals(expResult, result.rank);
    }

    /**
     * Test of getFullHouse method, of class RankHand.
     */
    @Test
    public void testGetFullHouse() {
        System.out.println("getFullHouse");
        
        Hand hand = new Hand();
        
        hand.addCard(new StandardCard(CLUBS,KING));
        hand.addCard(new StandardCard(CLUBS,FIVE));
        hand.addCard(new StandardCard(CLUBS,FIVE));
        hand.addCard(new StandardCard(HEARTS,KING));
        hand.addCard(new StandardCard(CLUBS,FIVE));
        hand.addCard(new StandardCard(SPADES,KING));

        HashMap<StandardCard.Value, Integer> valueHash = RankHand.hashHand(hand)[0];

        HandValue result = RankHand.getFullHouse(valueHash);
        assertEquals(FULLHOUSE, result.rank);
    }

    /**
     * Test of getPairs method, of class RankHand.
     */
    @Test
    public void testGetPairs() {
        System.out.println("getPairs");
        
        Hand hand = new Hand();        
        hand.addCard(new StandardCard(CLUBS,FOUR));
        hand.addCard(new StandardCard(HEARTS,FIVE));
        hand.addCard(new StandardCard(CLUBS,FIVE));
        hand.addCard(new StandardCard(CLUBS,KING));
        hand.addCard(new StandardCard(CLUBS,TWO));
        
        hand.sortHandHigh();
        
        HashMap<StandardCard.Value, Integer> valueHash = RankHand.hashHand(hand)[0];

        Rank expResult = PAIR;
        HandValue result = RankHand.getPairs(valueHash);
        assertEquals(expResult, result.rank);
        
    }

    /**
     * Test of getFlushSuit method, of class RankHand.
     */
    @Test
    public void testGetFlushSuit() {
        
        System.out.println("getFlushSuit");
        
        Hand hand = new Hand();        
        hand.addCard(new StandardCard(CLUBS,FOUR));
        hand.addCard(new StandardCard(CLUBS,FIVE));
        hand.addCard(new StandardCard(CLUBS,FIVE));
        hand.addCard(new StandardCard(CLUBS,KING));
        hand.addCard(new StandardCard(CLUBS,TWO));
        
        hand.sortHandHigh();
      
        HashMap<Suit, Integer> suitHash = RankHand.hashHand(hand)[1];

        Suit result = RankHand.getFlushSuit(suitHash);
        assertEquals(CLUBS, result);
        
    }

    /**
     * Test of getRank method, of class RankHand.
     */
    @Test
    public void testGetRank() {
        System.out.println("getRank");
        
        Hand hand = new Hand();        
        hand.addCard(new StandardCard(CLUBS,FOUR));
        hand.addCard(new StandardCard(CLUBS,THREE));
        hand.addCard(new StandardCard(CLUBS,FIVE));
        hand.addCard(new StandardCard(HEARTS,KING));
        hand.addCard(new StandardCard(CLUBS,TEN));
        hand.addCard(new StandardCard(CLUBS,JACK));
        hand.addCard(new StandardCard(CLUBS,TWO));
        
        Rank expResult = FLUSH;
        HandValue result = RankHand.getRank(hand);
        assertEquals(expResult, result.rank);
    }

    @Test
    public void testRandomStraights(){
    
        System.out.println("testRandomStraights");
        
        for(int i = 0 ; i < 100; i++){
            Hand hand = GenerateHands.createStraight();

            Rank expResult = STRAIGHT;
            HandValue result = RankHand.getStraight(hand);
            
            try{
                assertEquals(expResult, result.rank);
            }
            catch (NullPointerException e){
                hand.printHand();
                fail();
            }
        }
        
    }
    
    @Test
    public void testRandomFlush(){
    
        System.out.println("testRandomFlush");
        
        for(int i = 0 ; i < 100; i++){
            
            Hand hand = GenerateHands.createFlush();
            Rank expResult = FLUSH;
            HandValue result = RankHand.getFlush(RankHand.hashHand(hand)[1],hand);
            
            try{
                assertEquals(expResult, result.rank);
            }
            catch (NullPointerException e){
                hand.printHand();
                fail();
            }
        }
        
    }
    
    @Test
    public void testRandomFullHouse(){
    
        System.out.println("testRandomFullHouse");
        
        for(int i = 0 ; i < 100; i++){
            
            Hand hand = GenerateHands.createFullHouse();
            Rank expResult = FULLHOUSE;
            HandValue result = RankHand.getFullHouse(RankHand.hashHand(hand)[0]);
            
            try{
                assertEquals(expResult, result.rank);
            }
            catch (NullPointerException e){
                hand.printHand();
                fail();
            }
        }
        
    }
    
    @Test
    public void testRandomStraightFlush(){
    
        System.out.println("testRandomStraightFlush");
        
        for(int i = 0 ; i < 100; i++){

            Hand hand = GenerateHands.createStraightFlush();
            Rank expResult = STRAIGHTFLUSH;
            Suit s = RankHand.getFlushSuit(RankHand.hashHand(hand)[1]);
            HandValue result = RankHand.getStraightFlush(s,hand);
            
            try{
                assertEquals(expResult, result.rank);
            }
            catch (NullPointerException e){
                hand.printHand();
                fail();
            }
        }
        
    }
    
    @Test
    public void testRandomPair(){
    
        System.out.println("testPair");
        
        for(int i = 0 ; i < 100; i++){
            
            Hand hand = GenerateHands.createPair();
            Rank expResult = PAIR;
            
            HandValue result = RankHand.getPairs(RankHand.hashHand(hand)[0]);
            
            try{
                assertEquals(expResult, result.rank);
            }
            catch (NullPointerException e){
                hand.printHand();
                fail();
            }
        }
        
    }
    
    @Test
    public void testRandomTwoPair(){
    
        System.out.println("testRandomTwoPair");
        
        for(int i = 0 ; i < 100; i++){
            
            Hand hand = GenerateHands.createTwoPair();
            Rank expResult = TWOPAIR;
            
            HandValue result = RankHand.getPairs(RankHand.hashHand(hand)[0]);
            
            try{
                assertEquals(expResult, result.rank);
            }
            catch (NullPointerException e){
                hand.printHand();
                fail();
            }
        }
        
    }

}
