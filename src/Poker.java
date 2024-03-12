//package com.poker;
import java.util.*;

//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;

import java.io.FileWriter;
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
     int times = 0;
     int playerWinAmount = 0;
     int dealerWinAmount = 0;
     int push = 0;

     boolean playing = true;
     boolean firstGame = true;
     boolean playBetPlaced = false;
     boolean folded = false;
     boolean dealerQualifies = false;

     double ante = 0;
     double playBet = 0;
     double totalBet = 0;
     double totalWin = 0;
     double balance = 0;

     String userName = "";
     String decision = "";
     
     
     Scanner sc = new Scanner(System.in);

    GUI_Poker gui = new GUI_Poker();

     Player newPlayer = new Player();

     while (playing){
        playBetPlaced = false;
        folded = false;
        dealerQualifies = false;
        ante = 0;
        playBet = 0;
        totalBet = 0;
        totalWin = 0;
        
        //times++;
        Deck deckOfCards = new Deck();
        
        Hand dealerHand = new Hand();
        Hand playerHand = new Hand();
        Comunity newComunity = new Comunity();
        deckOfCards.createDeck();
        Collections.shuffle(deckOfCards.getDeck());

        if (firstGame){
            firstGame = false;
            System.out.println("Hello newcomer!");
            balance = newPlayer.getBalance();
            System.out.println("Your balance is: " + balance);
            System.out.print("Please enter your name: ");
            userName = sc.nextLine();
            newPlayer.setName(userName);
            System.out.print("Nice to meet you " + userName + "! Please place your ante bet: ");
            ante = Double.parseDouble(sc.nextLine());
            totalBet = ante;

        }else{
            System.out.println("Playing again.");
            balance = newPlayer.getBalance();
            System.out.println("Your balance is: " + balance);            
            System.out.print(userName + ", place your new ante bet: ");
            ante = Double.parseDouble(sc.nextLine());
         
        }
        
        System.out.println("Dealing initil cards....");
        Thread.sleep(1000);
        dealInitials(deckOfCards,dealerHand,playerHand);
        String dealerCard1 =  dealerHand.getHandString(0);
        String dealerCard2 =  dealerHand.getHandString(1);
        String playerCard1 =  playerHand.getHandString(0);
        String playerCard2 =  playerHand.getHandString(1);

        

        System.out.println("\nPlayers hand: " + playerCard1 + " and " + playerCard2 );
        gui.showCard(playerCard1, 1);
        gui.showCard("facedown", 2);
        //Thread.sleep(2000);
        gui.showCard(playerCard2, 3);
        gui.showCard("facedown", 4);
        
        //Thread.sleep(2000);
        //System.out.println("Dealers hand: " + dealerCard1 + " and " + dealerCard2+ "\n");

        if(newPlayer.getBalance() >= ante + ante * 4){
            System.out.print("\nWhat is your decision? (x4, x3, check)?: ");
        }else if (newPlayer.getBalance() >= ante + ante * 3){
            System.out.print("What is your decision? (x3, check): ");
        }else{
            System.out.print("What is your decision? (check): ");
        }     
        decision = sc.nextLine();

        switch (decision) {
            case "x4":
                playBet = ante * 4;
                totalBet = totalBet + playBet;
                playBetPlaced = true;
                System.out.println("Play bet has been placed. No more bets can be placed this round");
                break;

            case "x3":
                playBet = ante * 3;
                totalBet = totalBet + playBet;
                playBetPlaced = true;
                System.out.println("Play bet has been placed. No more bets can be placed this round");                
                break;       

            case "check":
            break;

        }
        
        System.out.println("Dealing flop...");
        Thread.sleep(2000);
        newComunity.dealFlop(deckOfCards);
        String flop = newComunity.getComunityString(gui);
        System.out.println("\nFlop: " + flop);
        if (!playBetPlaced){
            if (newPlayer.getBalance() >= ante + ante * 2){
                System.out.print("What is your decision? (x2, check)?: ");
            }else{
                System.out.print("What is your decision? (check)?: ");
            }
            decision = sc.nextLine();

            switch (decision) {
                case "x2":
                    playBet = ante * 2;
                    totalBet = totalBet + playBet;
                    playBetPlaced = true;
                    System.out.println("Play bet has been placed. No more bets can be placed this round");
                    break;   

                case "check":
                    break;
            }
        }  

        System.out.println("Dealing turn and river...");
        Thread.sleep(2000);
        newComunity.dealTurnandRiver(deckOfCards);
        String comunityCards = newComunity.getComunityString(gui);
        System.out.println("\nYour hand: " + playerCard1 + " and " + playerCard2 );
        System.out.println("Comunity: " + comunityCards + "\n");

        PokerHand playerPokerHand = new PokerHand(playerHand, newComunity);
        playerPokerHand.sort();
        //playerPokerHand.print("Your");  
        Thread.sleep(2000);
        String combination_player = playerPokerHand.printCombination("\nYour");    

        if(!playBetPlaced){
            System.out.print("What is your decision? (x1, fold): ");

            decision = sc.nextLine();

            switch (decision) {
                case "x1":
                    playBet = ante;
                    totalBet = totalBet + playBet;
                    break;
                
                case "fold":
                    folded = true;
            }
        }
 

        PokerHand dealerrPokerHand = new PokerHand(dealerHand, newComunity);

        dealerrPokerHand.sort();
        if (dealerrPokerHand.getRank() > 1){
            dealerQualifies = true;
        }
        //dealerrPokerHand.print("Dealers");

        if (!folded){
            Thread.sleep(2000);
            System.out.println("Dealers hand: " + dealerCard1 + " and " + dealerCard2+ "\n");
            gui.showCard(dealerCard1,2);
            gui.showCard(dealerCard2,4);
            Thread.sleep(2000);
            String combination_dealer = dealerrPokerHand.printCombination("Dealers");
            Thread.sleep(3000);
            if(playerPokerHand.getRank() > dealerrPokerHand.getRank()){
                
                System.out.println("\nPLAYER WINS!!!");
                playerWinAmount++;
                totalWin = newPlayer.addBalance(ante, playBet, combination_player, dealerQualifies);
                System.out.println("You win: " + totalWin);
    /*               if (playerPokerHand.getRank() == 7){
                    times = 100000;
                }   */

            }else if (playerPokerHand.getRank() < dealerrPokerHand.getRank()){
                System.out.println("\nDealer wins :( ");
                dealerWinAmount++;
                newPlayer.deductBalance(totalBet);
    /*               if (dealerrPokerHand.getRank() == 7){
                    times = 100000;
                }   */




            }else if (playerPokerHand.getRank() == dealerrPokerHand.getRank()){

                int[] playerKickers = playerHand.getkKickers();
                int[] dealerKickers = dealerHand.getkKickers();

                int [] twoPairs_Player = playerPokerHand.getTwoPairValue();
                int [] twoPairs_Dealer = dealerrPokerHand.getTwoPairValue();

                int [] fullHouse_Player = playerPokerHand.getFullHouse();
                int [] fullHouse_Dealer = dealerrPokerHand.getFullHouse();          

                int[] validKickers_Player  = playerPokerHand.validateKicker(playerKickers);
                int[] validKickers_Dealer = dealerrPokerHand.validateKicker(dealerKickers);

                
                if (playerPokerHand.getPairValue() > dealerrPokerHand.getPairValue()){
                    System.out.println("PLAYER WINS with better pair!!!");
                    playerWinAmount++;
                    totalWin = newPlayer.addBalance(ante, playBet, combination_player, dealerQualifies);
                    System.out.println("You win: " + totalWin);

                }else if (playerPokerHand.getPairValue() < dealerrPokerHand.getPairValue()){
                    System.out.println("DEALER WINS with better pair!!!");
                    dealerWinAmount++;
                    newPlayer.deductBalance(totalBet);

                // Salīdzina pārus, ja abiem ir divu pāru kombinācija
                }else if (combination_player.equals("Two Pairs") && combination_dealer.equals("Two Pairs")){
                    int twoPairKicker_Player = playerPokerHand.getTwoPairKicker();
                    int twoPairKicker_Dealer = dealerrPokerHand.getTwoPairKicker();
                    
                    if(twoPairs_Player[0] > twoPairs_Dealer[0]){
                        System.out.println("PLAYER WINS with better TWO pairs!!!");
                        playerWinAmount++;
                        totalWin = newPlayer.addBalance(ante, playBet, combination_player, dealerQualifies);
                        System.out.println("You win: " + totalWin);

                    }else if (twoPairs_Player[0] < twoPairs_Dealer[0]){
                        System.out.println("DEALER WINS with better TWO pairs!!!");
                        dealerWinAmount++;
                        newPlayer.deductBalance(totalBet);

                    }else if (twoPairs_Player[1] > twoPairs_Dealer[1]){

                        System.out.println("PLAYER WINS with better TWO pairs!!!");
                        playerWinAmount++;
                        totalWin = newPlayer.addBalance(ante, playBet, combination_player, dealerQualifies);
                        System.out.println("You win: " + totalWin);

                    }else if(twoPairs_Player[1] < twoPairs_Dealer[1]){
                        
                        System.out.println("DEALER WINS with better TWO pairs!!!");
                        dealerWinAmount++;
                        newPlayer.deductBalance(totalBet);

                    }else if ( twoPairKicker_Player > twoPairKicker_Dealer){

                        System.out.println("PLAYER WINS with same TWO pairs but better kicker!!!");
                        playerWinAmount++;
                        totalWin = newPlayer.addBalance(ante, playBet, combination_player, dealerQualifies);
                        System.out.println("You win: " + totalWin);

                    }else if (twoPairKicker_Player < twoPairKicker_Dealer){

                        System.out.println("DEALER WINS with same TWO pairs but better kicker!!!");
                        dealerWinAmount++;
                        newPlayer.deductBalance(totalBet);

                    }else{
                        System.out.println("Same two pairs and same kicker. It is a push!");
                        push++;
                    }
                // salīdzina full house, ja abiem ir šīs kombinācijas
                }else if(combination_player.equals("Full House") && combination_dealer.equals("Full House")){
    /*                 if (playerPokerHand.getRank() == 7){
                        times = 100000;
                    }  */
                    if(fullHouse_Player[0] > fullHouse_Dealer[0]){
                        System.out.println("PLAYER WINS with better Full House!!!");
                        playerWinAmount++;
                        totalWin = newPlayer.addBalance(ante, playBet, combination_player, dealerQualifies);
                        System.out.println("You win: " + totalWin);

                    }else if(fullHouse_Player[0] < fullHouse_Dealer[0]){
                        System.out.println("DEALER WINS with better Full House!!!");
                        dealerWinAmount++;
                        newPlayer.deductBalance(totalBet);

                    }else if(fullHouse_Player[1] > fullHouse_Dealer[1]){
                        System.out.println("PLAYER WINS with better Full House!!!");
                        playerWinAmount++;      
                        totalWin = newPlayer.addBalance(ante, playBet, combination_player, dealerQualifies);
                        System.out.println("You win: " + totalWin);

                    }else if(fullHouse_Player[1] < fullHouse_Dealer[1]){
                        System.out.println("DEALER WINS with better Full House!!!");
                        dealerWinAmount++;    
                        newPlayer.deductBalance(totalBet);   

                    }else{
                        System.out.println("Same Full Houses. It is a push!");
                        push++;     
                        newPlayer.returnMoney(totalBet);            
                    }

                }else{


                String winner = compareKickers(validKickers_Player[0], validKickers_Player[1], validKickers_Dealer[0], validKickers_Dealer[1]);
                if (winner == "player"){
                    playerWinAmount++;
                    totalWin = newPlayer.addBalance(ante, playBet, combination_player, dealerQualifies);
                    System.out.println(winner + " wins with kicker ");
                    System.out.println("You win: " + totalWin);

                }else if (winner == "dealer"){
                    dealerWinAmount++;
                    System.out.println(winner + " wins with kicker ");
                    newPlayer.deductBalance(totalBet);
                }else{
                    push++;
                    System.out.println("It is a push");
                    newPlayer.returnMoney(totalBet);  
                }
/*                 if (winner.equals("unknown")){
                    times = 100000;
                } */
                ;
                }
            
        }else{
            System.out.println("You folded, dealer wins");
            newPlayer.deductBalance(totalBet);
        }

        System.out.println("Are you playing again? (y/n): ");
        decision = sc.nextLine();
        if (decision.equals("n")){
            playing = false;
            sc.close();
        }else{
            gui.removeCards();
        }
        }

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
