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

    String getComunityString(){
        createPictureNominals();
        
        String comunityString = "";
        if (TurnDealt == false){
            for (int i = 0; i<=2; i++){
                int cardValue = this.comunityCards[i].getValue();
                comunityString = comunityString + " " + numToPicture(this.comunityCards,cardValue, i);
            }
        }else{
            for (int i = 0; i<=4; i++){
                int cardValue = this.comunityCards[i].getValue();
                comunityString =  comunityString + " " + numToPicture(this.comunityCards,cardValue, i);
            }           
        }
        return comunityString;
    }
    
    Card [] getComunity(){
        return this.comunityCards;
    }
}
