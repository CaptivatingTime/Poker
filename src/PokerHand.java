import java.util.HashMap;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map.Entry;

public class PokerHand extends Hand   {
    Card PokerHand[]        = new Card[7];
    Card PokerHandTrimmed[] = new Card[5];

    private HashMap <String, Integer>  suitAmount     = new HashMap<>();
    private HashMap <Integer, Integer> nominalAmount  = new HashMap<>();

    boolean hasAce              = false;
    boolean isPair              = false; //ready no kicker check 
    boolean isFlush             = false; //ready no kicker check 
    boolean isQuads             = false; //ready no kicker check 
    boolean isRoyal             = false; 
    boolean isTwoPair           = false; 
    boolean isStraight          = false; //ready no kicker check 
    boolean istThreeOfKind      = false; //ready no kicker check 
    boolean isStraightFlush     = false; //ready 
    
    


    
    PokerHand(Hand hand, Comunity comunity){
        int i = 0;
        for(Card card : hand.getHand()){
            this.PokerHand[i] = card;
            i++;
        }
        for (Card card : comunity.getComunity()){
            this.PokerHand[i] = card;
            i++;
        }
    }

    void countSuit (){
        sort();
        for (Card card : this.PokerHand){
            String suit = card.getSuit(); 
            if ( this.suitAmount.containsKey(suit) == false){
                this.suitAmount.put(suit, 1);
            }else{
                int oldAmount = this.suitAmount.get(suit);
                this.suitAmount.put(suit, oldAmount + 1);
                if (oldAmount + 1 == 5){
                    this.isFlush = true;
                    trim(0,6, "suit");
                    checkStraightFlush();
                    if (this.isStraightFlush){
                        System.out.println("STRAIGHT FLUSH!!!!!!!!!!!!!!!!!!");
                    }
                    
                    System.out.println("Flush of " + suit);
                }
            }
        }
        System.out.println(this.suitAmount);
    }
    
    void countNominals (){
        
        for (Card card : this.PokerHand){
            int nominal = card.getValue(); 
            if ( this.nominalAmount.containsKey(nominal) == false){
                this.nominalAmount.put(nominal, 1);
            }else{
                int oldAmount = this.nominalAmount.get(nominal);
                this.nominalAmount.put(nominal, oldAmount + 1);
            }
        }
        
        System.out.println(this.nominalAmount);
    }
    
    void sort(){
/*         int[] nominalArray = new int[7];
        Card[] pokerHandSorted = new Card[7];
        int i = 0;
        for (Card card :this.PokerHand){
            nominalArray[i] = card.getValue();
            i++;
        }
        Arrays.sort(nominalArray);
        
        for (int nominal : nominalArray){
            System.out.println(nominal);
        } */
        Arrays.sort(this.PokerHand, Comparator.comparing(Card::getValue));
        Collections.reverse(Arrays.asList(this.PokerHand));
        
    }


    void checkTwoPairs (){
        int nominal = 0;
    }

    boolean checkPair(){
        int nominal = 0;
        boolean checker = false;
        int amount = 0;
        for (Entry<Integer, Integer> entry : nominalAmount.entrySet()){
            if (entry.getValue() == 2){
                 amount++;
                 nominal = entry.getKey();
                 //checker = true;
                 //System.out.println("TRIPS OF " + nominal);

            }  
        }
        if (amount == 1){
            checker = true;
            System.out.println("Pair of " + nominal);
        }    
        return checker;   
    }
    boolean checkQuadsAndTrips(int amount){
        int nominal = 0;
        boolean checker = false;
        for (Entry<Integer, Integer> entry : nominalAmount.entrySet()){
            if (entry.getValue() == amount){
                 nominal = entry.getKey();
                 checker = true;
                 //System.out.println("TRIPS OF " + nominal);

            }  
        }
        return checker;
    }
    void trim(int start, int end, String target){
        int i = 0;
        int n = 0;
        String suit = "";
        if (target == "suit"){
            for (Entry<String, Integer> entry : suitAmount.entrySet()){
                if (entry.getValue() == 5){
                     suit = entry.getKey();
                }
        }   }

        
        for (Card card : this.PokerHand){
            if (start <= i & i <= end ){
                if (target == "nominal"){
                    this.PokerHandTrimmed[n] = card;
                    n++;
                }else if (target == "suit"){
                    if (start <= i & i <= end ){
                        if (card.getSuit() == suit){
                            this.PokerHandTrimmed[n] = card;
                            n++;
                        }                           
                    }
                }
            }
            i++;
        }
        for (Card card : this.PokerHandTrimmed){
            System.out.println(card.getValue() + " " + card.getSuit());
        }
        
        //for (Card card : this.PokerHandTrimmed){
       //     System.out.println(card.getValue() + card.getSuit());
       // }
        
    }

    boolean checkStraight(Card[] cardCollection){
        int counter          = 0;
        int straightStartPos = 0;
        int straightEndtPos  = 0;
        boolean checker = false;
        for(int i = 1; i < cardCollection.length; i++){
            if (cardCollection[i-1].getValue() - cardCollection[i].getValue()  == 1){
                counter++;
            }else{
                counter = 0;
            }
            if (counter == 5){
                checker = true;
                straightEndtPos = i - 1;
                straightStartPos = straightEndtPos - 4;
                trim(straightStartPos,straightEndtPos, "nominal");
                //System.out.println("\n\nSTRAIGHT\n\n");
                
            }else if (isFlush){
                if(cardCollection[cardCollection.length - 2].getValue() - cardCollection[cardCollection.length - 1].getValue() == 1){
                    checker = true;
                }
            }
        }
        return checker;
        
    }
    
    void checkStraightFlush(){
         if (checkStraight(this.PokerHandTrimmed)){
            this.isStraightFlush = true;
         }
    }
    
    void print(){
        String pokerHandString = "" ;
        createPictureNominals();
        for (int i = 0; i < 7; i++){
            int cardValue = this.PokerHand[i].getValue();
            pokerHandString = pokerHandString + " " + numToPicture(this.PokerHand, cardValue, i);
            
        }
        System.out.println("Pokerhand: " + pokerHandString);
    }
    Card [] getPokerHand(){
        return PokerHand;
    }
}
