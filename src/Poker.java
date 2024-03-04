import java.util.*;


public class Poker {

    public static void dealInitials(Deck deckOfCards, Hand dealerHand, Hand playerHand){
        Card drawnCard = deckOfCards.drawCard();
        dealerHand.addCard(drawnCard);
        Card drawnCard2 = deckOfCards.drawCard();
        dealerHand.addCard(drawnCard2);
        Card drawnCard3 = deckOfCards.drawCard();
        playerHand.addCard(drawnCard3);
        Card drawnCard4 = deckOfCards.drawCard();
        playerHand.addCard(drawnCard4);
    }
    public static void main(String[] args) throws Exception {
        
        
        Deck deckOfCards = new Deck();
        Hand dealerHand = new Hand();
        Hand playerHand = new Hand();
        Comunity newComunity = new Comunity();
        deckOfCards.createDeck();
/*         for (Card card : deckOfCards.getDeck()){
            System.out.println(card.getValue() + " " + card.getSuit());
        } */
        Collections.shuffle(deckOfCards.getDeck());
/*         System.out.println("__________________");
        for (Card card : deckOfCards.getDeck()){;
            System.out.println(card.getValue() + " " + card.getSuit());
        } */
        
/*         Card drawnCard = deckOfCards.drawCard();
        dealerHand.addCard(drawnCard);
        Card drawnCard2 = deckOfCards.drawCard();
        dealerHand.addCard(drawnCard2); */
        dealInitials(deckOfCards,dealerHand,playerHand);
        String dealerCard1 =  dealerHand.getHandString(0);
        String dealerCard2 =  dealerHand.getHandString(1);
        String playerCard1 =  playerHand.getHandString(0);
        String playerCard2 =  playerHand.getHandString(1);



        

        
        

        System.out.println("Dealers hand: " + dealerCard1 + " and " + dealerCard2);
        System.out.println("Players hand: " + playerCard1 + " and " + playerCard2);
        
        newComunity.dealFlop(deckOfCards);
        String flop = newComunity.getComunityString();
        System.out.println("Flop: " + flop);

        newComunity.dealTurnandRiver(deckOfCards);
        String comunityCards = newComunity.getComunityString();
        System.out.println("Comunity: " + comunityCards);

        PokerHand playerPoekrHand = new PokerHand(playerHand, newComunity);
        //playerPoekrHand.print();
        playerPoekrHand.sort();
        playerPoekrHand.print();     
        playerPoekrHand.countSuit();
        playerPoekrHand.countNominals();
        playerPoekrHand.checkPair();
        //playerPoekrHand.checkStraight();
        PokerHand dealerrPoekrHand = new PokerHand(dealerHand, newComunity);
        //dealerrPoekrHand.print();
        
        
        int m = 10;
        int i = 0;
/*         for (Card card : dealerrPoekrHand.PokerHand){
            card.value = m;
            m++;
        }
        for (Card card : dealerrPoekrHand.PokerHand){
            card.suit = "Spade";
            m++;
        }     */
        for (Card card : dealerrPoekrHand.PokerHand){
            if (i <3){
            card.value = m;
            i++;
            }
        }
        dealerrPoekrHand.countNominals();
        dealerrPoekrHand.checkQuadsAndTrips(3);

        dealerrPoekrHand.countSuit();   
        dealerrPoekrHand.sort();
        dealerrPoekrHand.print();
        dealerrPoekrHand.checkPair();
        if (dealerrPoekrHand.checkStraight(dealerrPoekrHand.getPokerHand())){
            System.out.print("STRAIGHTTT");
        }
        
        //System.out.println(deckOfCards.getDeck());
    }
}
