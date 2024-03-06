import java.util.*;


public class Poker {


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
            }
        } else if(player1 == dealer1){
            winner = "Push";
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
     int times = 0;
     int playerWinAmount = 0;
     int dealerWinAmount = 0;
     int push = 0;
    // while (times <100000){
        times++;
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
        System.out.println("Players hand: " + playerCard1 + " and " + playerCard2 + "\n");
        
        newComunity.dealFlop(deckOfCards);
        String flop = newComunity.getComunityString();
        System.out.println("Flop: " + flop);

        newComunity.dealTurnandRiver(deckOfCards);
        String comunityCards = newComunity.getComunityString();
        System.out.println("Comunity: " + comunityCards + "\n");

        PokerHand playerPokerHand = new PokerHand(playerHand, newComunity);
        

        playerPokerHand.sort();


        
        playerPokerHand.print("Players"); 

        PokerHand dealerrPokerHand = new PokerHand(dealerHand, newComunity);




        dealerrPokerHand.sort();
        dealerrPokerHand.print("Dealers");
        String combination_player = playerPokerHand.printCombination("\nPlayers");


        String combination_dealer = dealerrPokerHand.printCombination("Dealers");

        if(playerPokerHand.getRank() > dealerrPokerHand.getRank()){
            System.out.println("\nPLAYER WINS!!!");
            playerWinAmount++;
/*               if (playerPokerHand.getRank() == 7){
                times = 100000;
            }   */

        }else if (playerPokerHand.getRank() < dealerrPokerHand.getRank()){
            System.out.println("\nDealer wins :( ");
            dealerWinAmount++;
/*               if (dealerrPokerHand.getRank() == 7){
                times = 100000;
            }   */




        }else if (playerPokerHand.getRank() == dealerrPokerHand.getRank()){

            int[] playerKickers = playerHand.getkKickers();
            int[] dealerKickers = dealerHand.getkKickers();

            int [] twoPairs_Player = playerPokerHand.getTwoPairValue();
            int [] twoPairs_Dealer = dealerrPokerHand.getTwoPairValue();

            

            int[] validKickers_Player  = playerPokerHand.validateKicker(playerKickers);
            int[] validKickers_Dealer = dealerrPokerHand.validateKicker(dealerKickers);

            
            if (playerPokerHand.getPairValue() > dealerrPokerHand.getPairValue()){
                System.out.println("PLAYER WINS with better pair!!!");
                playerWinAmount++;
            }else if (playerPokerHand.getPairValue() < dealerrPokerHand.getPairValue()){
                System.out.println("DEALER WINS with better pair!!!");
                dealerWinAmount++;
            }else if (combination_player == "Two Pairs" && combination_dealer == "Two Pairs"){
                int twoPairKicker_Player = playerPokerHand.getTwoPairKicker();
                int twoPairKicker_Dealer = dealerrPokerHand.getTwoPairKicker();
                // Salīdzina pārus, ja abiem ir divu pāru kombinācija
                if(twoPairs_Player[0] > twoPairs_Dealer[0]){
                    System.out.println("PLAYER WINS with better TWO pairs!!!");
                    playerWinAmount++;
                }else if (twoPairs_Player[0] < twoPairs_Dealer[0]){

                    System.out.println("DEALER WINS with better TWO pairs!!!");
                }else if (twoPairs_Player[1] > twoPairs_Dealer[1]){

                    System.out.println("PLAYER WINS with better TWO pairs!!!");
                    playerWinAmount++;
                }else if(twoPairs_Player[1] < twoPairs_Dealer[1]){
                    
                    System.out.println("DEALER WINS with better TWO pairs!!!");
                }else if ( twoPairKicker_Player > twoPairKicker_Dealer){

                    System.out.println("PLAYER WINS with same TWO pairs but better kicker!!!");
                    playerWinAmount++;
                }else if (twoPairKicker_Player < twoPairKicker_Dealer){

                    System.out.println("DEALER WINS with same TWO pairs but better kicker!!!");
                    playerWinAmount++;
                }else{
                    System.out.println("Same two pairs and same kicker. It is a push!");
                    push++;
                }
            }else{

            String winner = compareKickers(validKickers_Player[0], validKickers_Player[1], validKickers_Dealer[0], validKickers_Dealer[1]);
            if (winner == "player"){
                playerWinAmount++;
            }else if (winner == "dealer"){
                dealerWinAmount++;
            }else{
                push++;
            }
            System.out.println(winner + " wins with kicker ");
            }
            



        //}

         //System.out.println(times);
    }
    //System.out.println("Player wins: "+ playerWinAmount + "\nDealer Wins: " + dealerWinAmount + "\nPush: " + push);
        //dealerrPoekrHand.checkPair();
        //dealerrPoekrHand.checkTwoPairs();
        //if (dealerrPoekrHand.checkStraight(dealerrPoekrHand.getPokerHand())){
        //    System.out.print("STRAIGHTTT");
        //}
        
        //System.out.println(deckOfCards.getDeck());
    }
}
