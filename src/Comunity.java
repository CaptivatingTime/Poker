public class Comunity extends Hand {
    private Card comunityCards[] = new Card[5];

    private boolean flopDealt = false;
    private boolean TurnDealt = false;

    void addCard(Deck deck, int startPos, int endPos){
        for (int i = startPos; i<=endPos; i++){
            this.comunityCards[i] = deck.drawCard();
            
        }
    }
    
    void dealFlop(Deck deck){
        addCard(deck, 0, 2);
        this.flopDealt = true;
    }

    void dealTurnandRiver (Deck deck){
        addCard(deck,3,4);
        this.TurnDealt = true;
    }

    String getComunityString(GUI_Poker gui){
        createPictureNominals();
        int showCardNo = 0;
        String card = "";
        String comunityString = "";
        if (TurnDealt == false){
            showCardNo = 5;
            for (int i = 0; i<=2; i++){
                int cardValue = this.comunityCards[i].getValue();
                card = numToPicture(this.comunityCards,cardValue, i);
                gui.showCard(card,showCardNo);
                showCardNo ++;
                comunityString = comunityString + " " + card;
            }
        }else{
            showCardNo = 8;
            for (int i = 0; i<=4; i++){
                int cardValue = this.comunityCards[i].getValue();
                card = numToPicture(this.comunityCards,cardValue, i);
                if (i > 2){
                    gui.showCard(card,showCardNo);
                    showCardNo ++;                   
                }
                comunityString = comunityString + " " + card;
            }           
        }
        return comunityString;
    }
    
    Card [] getComunity(){
        return this.comunityCards;
    }
}
