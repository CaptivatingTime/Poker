//package com.poker;




//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;


public class Poker  {  
        
    private static Hand dealerHand;
    private static Hand playerHand;

    private static GUI_Poker gui;


    public static Hand getDealerHand(){
        return dealerHand;
    } 
    public static Hand getPlayerHand(){
        return playerHand;
    } 
    public static GUI_Poker getGUI(){
        return gui;
    }            

    public static String compareKickers(int player1, int player2, int dealer1, int dealer2) {
        
        String winner = "unknown";
        int winningKicker = 0;

        if (player1 > dealer1){
            winner = "player";
            winningKicker = player1;
        } else if(player1 < dealer1){
            winner = "dealer";
            winningKicker = dealer1;

        } else if(player1 == dealer1){
            
            if (player2 > dealer2){
                winner = "player";
                winningKicker = player2;

            } else if(player2 < dealer2){
                winner = "dealer";
                winningKicker = dealer2;
            } else if ( player2 == dealer2){
                winner = "Push";
            }
        } 
        return winner;

    }
    public static void dealInitials(Deck deckOfCards, Hand dealerHand, Hand playerHand){
        Card drawnCard1 = deckOfCards.drawCard();
        playerHand.addCard(drawnCard1);
        Card drawnCard2 = deckOfCards.drawCard();
        dealerHand.addCard(drawnCard2);
        Card drawnCard3 = deckOfCards.drawCard();
        playerHand.addCard(drawnCard3);
        Card drawnCard4 = deckOfCards.drawCard();
        dealerHand.addCard(drawnCard4);

 
    }
    public static void main(String[] args) throws Exception {

     
     gui        = new GUI_Poker();
     playerHand = new Hand();
     dealerHand = new Hand();
    

    }

}
