import java.util.Collections;

public class Gameplay extends Poker {

    private static String dealerCard1;
    private static String dealerCard2;
    private static String playerCard1;
    private static String playerCard2;

    private static GUI_Poker gui;

    private static Deck deckOfCards;

    private static Hand dealerHand;
    private static Hand playerHand;

    private static Comunity newComunity;

    private static boolean checkPressed = false;
    private static boolean dealerQualifies = false;

    private static PokerHand playerPokerHand;
    private static PokerHand dealerrPokerHand;

    private static String combination_player;
    private static String combination_dealer;

    private static double ante;
    private static double playBet;
    private static double totalBet;
    private static double totalWin;
    private static double balance;

    private static Player newPlayer = new Player();

    public static void testing(){
        System.out.println("apple");
    }

    public static double getBalance(){
        return newPlayer.getBalance();
    }
    
    public static double getTotalWin(){
        return totalWin;
    }
    public static double getTotalBet(){
        return totalBet;
    }

    public static String getCombinaton(String side){
        String combination = null;
        if (side.equals("player")){
            combination = combination_player;
        }else{
            combination = combination_dealer;
        }
        return combination;
    }

    public static void gameStart(){
        deckOfCards = new Deck();

        dealerHand  = Poker.getDealerHand();
        playerHand  = Poker.getPlayerHand();
        
        gui    = Poker.getGUI();
        ante = gui.getBet();
        totalBet = totalBet + ante;

        deckOfCards.createDeck();
        Collections.shuffle(deckOfCards.getDeck());     

        dealInitials(deckOfCards,dealerHand,playerHand);
        dealerCard1 =  dealerHand.getHandString(0);
        dealerCard2 =  dealerHand.getHandString(1);
        playerCard1 =  playerHand.getHandString(0);
        playerCard2 =  playerHand.getHandString(1);

        gui.showCard(playerCard1, 1);
        gui.showCard("facedown", 2);
        //Thread.sleep(2000);
        gui.showCard(playerCard2, 3);
        gui.showCard("facedown", 4);  
          

        System.out.println("\nPlayers hand: " + playerCard1 + " and " + playerCard2 );
    }

    public static void performCheck(){
        if (!checkPressed){
            newComunity = new Comunity();
            newComunity.dealFlop(deckOfCards);
            newComunity.getComunityString(gui);
            checkPressed = true;
        }else{
            newComunity.dealTurnandRiver(deckOfCards);
            newComunity.getComunityString(gui);
            checkPressed = false; 
        }

    }
    public static void prepearNewGame(){
        dealerQualifies = false;
        ante = 0;
        playBet = 0;
        totalBet = 0;
        totalWin = 0;

        deckOfCards = new Deck();
        deckOfCards.createDeck();
        Collections.shuffle(deckOfCards.getDeck());  
    }
    
    public static void placePlayBet(double times){
        playBet = times * ante/2;
        totalBet = totalBet + playBet;
        if (times == 1){
            determineWinner();
        }else if (times == 3 || times ==4){
            newComunity = new Comunity();
            newComunity.dealFlop(deckOfCards);
            newComunity.getComunityString(gui);
            newComunity.dealTurnandRiver(deckOfCards);
            newComunity.getComunityString(gui);
            determineWinner();
            checkPressed = false;
        }else{ 
            newComunity.dealTurnandRiver(deckOfCards);
            newComunity.getComunityString(gui);
            determineWinner();
            checkPressed = false;
        }

        
}
    private static void determineWinner(){
        playerPokerHand = new PokerHand(playerHand, newComunity);
        dealerrPokerHand = new PokerHand(dealerHand, newComunity);
        playerPokerHand.sort();
        dealerrPokerHand.sort();

        combination_player = playerPokerHand.printCombination("\nYour");
        combination_dealer = dealerrPokerHand.printCombination("Dealers");

        gui.showCard(dealerCard1,2);
        gui.showCard(dealerCard2,4);

        if (dealerrPokerHand.getRank() > 1){
            dealerQualifies = true;
        }
        if(playerPokerHand.getRank() > dealerrPokerHand.getRank()){
                
            System.out.println("\nPLAYER WINS!!!");
            //playerWinAmount++;
            totalWin = newPlayer.addBalance(ante, playBet, combination_player, dealerQualifies);
            System.out.println("You win: " + totalWin);
            gui.highlightWinner("player");
/*               if (playerPokerHand.getRank() == 7){
                times = 100000;
            }   */

        }else if (playerPokerHand.getRank() < dealerrPokerHand.getRank()){
            System.out.println("\nDealer wins :( ");
            //dealerWinAmount++;
            newPlayer.deductBalance(totalBet);
            gui.highlightWinner("dealer");
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
                //playerWinAmount++;
                totalWin = newPlayer.addBalance(ante, playBet, combination_player, dealerQualifies);
                System.out.println("You win: " + totalWin);
                gui.highlightWinner("player");

            }else if (playerPokerHand.getPairValue() < dealerrPokerHand.getPairValue()){
                System.out.println("DEALER WINS with better pair!!!");
                //dealerWinAmount++;
                newPlayer.deductBalance(totalBet);
                gui.highlightWinner("dealer");

            // Salīdzina pārus, ja abiem ir divu pāru kombinācija
            }else if (combination_player.equals("Two Pairs") && combination_dealer.equals("Two Pairs")){
                int twoPairKicker_Player = playerPokerHand.getTwoPairKicker();
                int twoPairKicker_Dealer = dealerrPokerHand.getTwoPairKicker();
                
                if(twoPairs_Player[0] > twoPairs_Dealer[0]){
                    System.out.println("PLAYER WINS with better TWO pairs!!!");
                    //playerWinAmount++;
                    totalWin = newPlayer.addBalance(ante, playBet, combination_player, dealerQualifies);
                    System.out.println("You win: " + totalWin);
                    gui.highlightWinner("player");

                }else if (twoPairs_Player[0] < twoPairs_Dealer[0]){
                    System.out.println("DEALER WINS with better TWO pairs!!!");
                    //dealerWinAmount++;
                    newPlayer.deductBalance(totalBet);
                    gui.highlightWinner("dealer");

                }else if (twoPairs_Player[1] > twoPairs_Dealer[1]){

                    System.out.println("PLAYER WINS with better TWO pairs!!!");
                    //playerWinAmount++;
                    totalWin = newPlayer.addBalance(ante, playBet, combination_player, dealerQualifies);
                    System.out.println("You win: " + totalWin);
                    gui.highlightWinner("player");

                }else if(twoPairs_Player[1] < twoPairs_Dealer[1]){
                    
                    System.out.println("DEALER WINS with better TWO pairs!!!");
                    //dealerWinAmount++;
                    newPlayer.deductBalance(totalBet);
                    gui.highlightWinner("dealer");

                }else if ( twoPairKicker_Player > twoPairKicker_Dealer){

                    System.out.println("PLAYER WINS with same TWO pairs but better kicker!!!");
                    //playerWinAmount++;
                    totalWin = newPlayer.addBalance(ante, playBet, combination_player, dealerQualifies);
                    System.out.println("You win: " + totalWin);
                    gui.highlightWinner("player");

                }else if (twoPairKicker_Player < twoPairKicker_Dealer){

                    System.out.println("DEALER WINS with same TWO pairs but better kicker!!!");
                    //dealerWinAmount++;
                    newPlayer.deductBalance(totalBet);
                    gui.highlightWinner("dealer");

                }else{
                    System.out.println("Same two pairs and same kicker. It is a push!");
                    gui.highlightWinner("push");
                    //newPlayer.returnMoney(totalBet);  
                    //push++;
                }
            // salīdzina full house, ja abiem ir šīs kombinācijas
            }else if(combination_player.equals("Full House") && combination_dealer.equals("Full House")){
/*                 if (playerPokerHand.getRank() == 7){
                    times = 100000;
                }  */
                if(fullHouse_Player[0] > fullHouse_Dealer[0]){
                    System.out.println("PLAYER WINS with better Full House!!!");
                    //playerWinAmount++;
                    totalWin = newPlayer.addBalance(ante, playBet, combination_player, dealerQualifies);
                    System.out.println("You win: " + totalWin);
                    gui.highlightWinner("player");

                }else if(fullHouse_Player[0] < fullHouse_Dealer[0]){
                    System.out.println("DEALER WINS with better Full House!!!");
                    //dealerWinAmount++;
                    newPlayer.deductBalance(totalBet);
                    gui.highlightWinner("dealer");

                }else if(fullHouse_Player[1] > fullHouse_Dealer[1]){
                    System.out.println("PLAYER WINS with better Full House!!!");
                    //playerWinAmount++;      
                    totalWin = newPlayer.addBalance(ante, playBet, combination_player, dealerQualifies);
                    System.out.println("You win: " + totalWin);
                    gui.highlightWinner("player");

                }else if(fullHouse_Player[1] < fullHouse_Dealer[1]){
                    System.out.println("DEALER WINS with better Full House!!!");
                    //dealerWinAmount++;    
                    newPlayer.deductBalance(totalBet);   
                    gui.highlightWinner("dealer");

                }else{
                    System.out.println("Same Full Houses. It is a push!");
                    gui.highlightWinner("push");
                    //push++;     
                    //newPlayer.returnMoney(totalBet);            
                }

            }else{


            String winner = compareKickers(validKickers_Player[0], validKickers_Player[1], validKickers_Dealer[0], validKickers_Dealer[1]);
            if (winner == "player"){
                //playerWinAmount++;
                totalWin = newPlayer.addBalance(ante, playBet, combination_player, dealerQualifies);
                System.out.println(winner + " wins with kicker ");
                System.out.println("You win: " + totalWin);
                gui.highlightWinner("player");

            }else if (winner == "dealer"){
                //dealerWinAmount++;
                System.out.println(winner + " wins with kicker ");
                newPlayer.deductBalance(totalBet);
                gui.highlightWinner("dealer");
            }else{
                //push++;
                System.out.println("It is a push");
                gui.highlightWinner("push");
                //newPlayer.returnMoney(totalBet);  
            }
/*                 if (winner.equals("unknown")){
                times = 100000;
            } */
            ;
            }
    }        
    }
}

