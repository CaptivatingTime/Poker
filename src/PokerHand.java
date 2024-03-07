import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map.Entry;

public class PokerHand extends Hand   {
     Card PokerHand[]        = new Card[7];
     Card PokerHandTrimmed[] = new Card[5];

    int TwoPairs[] = new int[2];
    int FullHouse[] = new int[2];
    private int pairStrength    = 0;
    private int kicker1         = 0;
    private int kicker2         = 0;
    private int combinationRank = 0;

    
    private HashMap <String, Integer>  suitAmount     = new HashMap<>();
    private TreeMap <Integer, Integer> nominalAmount  = new TreeMap<>(Collections.reverseOrder());

    private boolean hasAce              = false; //ready
    private boolean isPair              = false; //ready no kicker check 
    private boolean isFlush             = false; //ready no kicker check 
    private boolean isQuads             = false; //ready no kicker check 
    private boolean isTwoPair           = false; //ready no kicker check
    private boolean isStraight          = false; //ready no kicker check 
    private boolean isFullHouse         = false;
    private boolean istThreeOfKind      = false; //ready no kicker check 
    private boolean isStraightFlush     = false; //ready 
    
    


    
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

    String printCombination(String position){
        String combination = determineCombination();
        System.out.println( position +" combination is " + combination);
        return combination;
    }



    String determineCombination(){
        String message = "unknown";
        sort();
        this.isStraightFlush = countSuit();
        this.isStraight = checkStraight(this.PokerHand);
        countNominals();
        trim(0,4, "nominal");
        this.hasAce = checkAce();
        this.isFullHouse = checkFullHouse();

        if (this.hasAce & this.isStraightFlush){
            message = "Royal";
            this.combinationRank = 10;
            return message;
        }
        //checkStraightFlush();
        if (isStraightFlush){
            message = "Straight Flush";
            this.combinationRank = 9;
            return message;
        }
        this.isQuads = checkQuadsAndTrips(4);
        if (isQuads){
            message = "Quads";
            this.combinationRank = 8;
            return message;
        }
        if (isFullHouse){
            message = "Full House";
            this.combinationRank = 7;
            return message;
        }       
        if(this.isFlush){
            message = "Flush";
            this.combinationRank = 6;
            return message;
        }      
        if (this.isStraight){
            message = "Straight";
            this.combinationRank = 5;
            return message;
        }

        this.istThreeOfKind = checkQuadsAndTrips(3);
        if(this.istThreeOfKind){
            message = "Trips";
            this.combinationRank = 4;
            return message;
        }

        this.isTwoPair = checkTwoPairs();
        if (this.isTwoPair){
            message = "Two Pairs";
            this.combinationRank = 3;
            return message;           
        }

        this.isPair = checkPair();
        if(this.isPair){
            message = "Pair";
            this.combinationRank = 2;
            //this.pairStrength = 
            return message;             
        }else{
            String highCard = numToPicture(PokerHand, this.PokerHand[0].getValue(), 0);
            message = "highcard of " + highCard;
            this.combinationRank = 1;
        }

        return message;

    }

    private void collectFullHouse(){
            int highestTrips = 0;
            int highestPair = 0;
        for (Map.Entry<Integer, Integer> entry : nominalAmount.entrySet()){
            if (entry.getValue() == 3){
                if (entry.getKey() > highestTrips){
                    highestTrips = entry.getKey();
                }
                this.FullHouse[0] = highestTrips;
            }
            if (entry.getValue() == 2){
                if (entry.getKey() > highestPair){
                    highestPair = entry.getKey();
                }               
                this.FullHouse[1] = highestPair;
            }
        }
    }

    private boolean checkFullHouse(){
        boolean checker = false;
        boolean hasTrips = false;
        boolean hasPair = false;
        for (Map.Entry<Integer, Integer> entry : nominalAmount.entrySet()){
            if (entry.getValue() == 3){
                hasTrips = true;
            }
            if (entry.getValue() == 2){
                hasPair = true;
            }
        }
        if (hasTrips && hasPair){
            collectFullHouse();
            checker = true;
        }
        return checker;
    }

    private boolean checkAce(){
        boolean checker = false;
        String neededSuit = "";
        //Find suit that is 5 times in pokerhand
        for (Map.Entry<String, Integer> entry : suitAmount.entrySet()){
            if (entry.getValue() == 5){
                neededSuit = entry.getKey();
            }
        }
        for(Card card : PokerHandTrimmed){
            if (card.getValue() == 14 && card.getSuit().equals(neededSuit)){
                checker = true;
            }
        }
        return checker;
    }





    private boolean checkTwoPairs (){
        sort();
        int amount = 0;
        int nominal1 = 0;
        int nominal2 = 0;
        boolean checker = false;
        //Map<String, Integer> decendingNominalAmount = new TreeMap<>(Collections.reverseOrder());

        //Card[] twoPairs = new Card[2];
        for (Entry<Integer, Integer> entry : nominalAmount.entrySet()){
            if (entry.getValue() == 2){
                if (nominal1 == 0){
                    amount++;
                    nominal1 = entry.getKey();
                    this.TwoPairs[0] = nominal1;
                }else if (nominal2 == 0){
                    nominal2 = entry.getKey();
                    this.TwoPairs[1] = nominal2;
                    amount++;
                    //System.out.println("Two pairs of " + nominal1 + " and " + nominal2);
                }/* else if (nominal1 < entry.getKey()){
                    nominal1 = entry.getKey();
                }else if (nominal2 < entry.getKey()){
                    nominal2 = entry.getKey();
                } */
            }
        }
        if (amount == 2){
            checker = true;
        }
        return checker;

    }



    private boolean checkPair(){
        int nominal = 0;
        boolean checker = false;
        int amount = 0;
        for (Entry<Integer, Integer> entry : nominalAmount.entrySet()){
            if (entry.getValue() == 2){
                 amount++;
                 nominal = entry.getKey();
                 this.pairStrength = nominal;
                 //checker = true;
                 //System.out.println("TRIPS OF " + nominal);

            }  
        }
        if (amount == 1){
            checker = true;
            //System.out.println("Pair of " + nominal);
        }    
        return checker;   
    }
    private boolean checkQuadsAndTrips(int amount){
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


    private boolean checkStraight(Card[] cardCollection){
        int counter          = 0;
        int straightStartPos = 0;
        int straightEndtPos  = 0;
        int reqAmount = 0;
        boolean checker = false;

        if(isFlush){
            reqAmount = 4;
        }else{
            reqAmount = 5;
        }
        for(int i = 1; i < cardCollection.length; i++){
            if (cardCollection[i-1].getValue() - cardCollection[i].getValue()  == 1){
                counter++;
            }else{
                counter = 0;
            }
            if (counter == reqAmount){
                checker = true;
                straightEndtPos = i - 1;
                straightStartPos = straightEndtPos - 4;
                trim(straightStartPos,straightEndtPos, "nominal");
                //System.out.println("\n\nSTRAIGHT\n\n");
                
            }//else if (isFlush){
              //  if(cardCollection[cardCollection.length - 2].getValue() - cardCollection[cardCollection.length - 1].getValue() == 1){
              //      checker = true;
              //  }
            //}
        }
        return checker;
        
    }
    
    private boolean countSuit (){
        sort();
        boolean checker = false;
        for (Card card : this.PokerHand){
            String suit = card.getSuit(); 
            if ( this.suitAmount.containsKey(suit) == false){
                this.suitAmount.put(suit, 1);
            }else{
                int oldAmount = this.suitAmount.get(suit);
                this.suitAmount.put(suit, oldAmount + 1);
                if (oldAmount + 1 == 5){
                    this.isFlush = true;
                    //checker = true;
                    trim(0,6, "suit");
                    checkStraightFlush();
                    if (this.isStraightFlush){
                        //System.out.println("STRAIGHT FLUSH!!!!!!!!!!!!!!!!!!");
                        checker = true;
                    }
                    
                    //System.out.println("Flush of " + suit);
                }
            }
        }
        return checker;
        //System.out.println(this.suitAmount);
    }
    
    private void countNominals (){
        
        for (Card card : this.PokerHand){
            int nominal = card.getValue(); 
            if ( this.nominalAmount.containsKey(nominal) == false){
                this.nominalAmount.put(nominal, 1);
            }else{
                int oldAmount = this.nominalAmount.get(nominal);
                this.nominalAmount.put(nominal, oldAmount + 1);
            }
        }
        
       // System.out.println(this.nominalAmount);
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

    private void trim(int start, int end, String target){
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
            if (start <= i && i <= end ){
                if (target == "nominal"){
                    this.PokerHandTrimmed[n] = card;
                    n++;
                }else if (target == "suit"){
                    if (start <= i && i <= end && i <7 ){
                        if (card.getSuit() == suit && n < 5){
                            this.PokerHandTrimmed[n] = card;
                            n++;
                        }                           
                    }
                }
            }
            i++;
        }
       // for (Card card : this.PokerHandTrimmed){
        //    System.out.println(card.getValue() + " " + card.getSuit());
        //}
        
        //for (Card card : this.PokerHandTrimmed){
       //     System.out.println(card.getValue() + card.getSuit());
       // }
        
    }

    int getTwoPairKicker (){
        return this.PokerHand[4].getValue();
    }

    int[] validateKicker (int[] kickers){

        int[] validKickers = new int[2];
        int kicker1 = 0;
        int kicker2 = 0;

        for (Card card : this.PokerHandTrimmed){
            if (card.getValue() == kickers[0]){
                kicker1 = card.getValue();
            } 
            if (card.getValue() == kickers[1]){
                kicker2 = card.getValue();
            }            
        }
        if (kicker1 > kicker2){
            validKickers[0] = kicker1;
            validKickers[1] = kicker2;
        }else if (kicker1 < kicker2){
            validKickers[0] = kicker2;
            validKickers[1] = kicker1;           
        }else if (kicker1 == kicker2){
            validKickers[0] = kicker2;
            validKickers[1] = kicker1;           
        }else{
            validKickers[0] = 0;
            validKickers[1] = 0;          
        }
        return validKickers;
    }
    private void checkStraightFlush(){
         if (checkStraight(this.PokerHandTrimmed)){
            this.isStraightFlush = true;
         }
    }
    
    void print(String side){
        String pokerHandString = "" ;
        createPictureNominals();
        for (int i = 0; i < 7; i++){
            int cardValue = this.PokerHand[i].getValue();
            pokerHandString = pokerHandString + " " + numToPicture(this.PokerHand, cardValue, i);
            
        }
        System.out.println(side + " Pokerhand: " + pokerHandString);
    }

    int getRank(){
        return this.combinationRank;
    }
    int[] getTwoPairValue (){
        return this.TwoPairs;
    }
    int[] getFullHouse (){
        return this.FullHouse;
    }
    int getPairValue(){
        return this.pairStrength;
    }
    Card [] getPokerHand(){
        return this.PokerHand;
    }
}
