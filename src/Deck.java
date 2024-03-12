import java.util.ArrayList; 
public class Deck {
    private  ArrayList<Card> Cards = new ArrayList<>();
    
    void createDeck(){
        String[] suits = {"diamonds", "spades", "hearts", "clubs"};
        int[]    values = {2,3,4,5,6,7,8,9,10,11,12,13,14};
        for (String suit : suits){
            for (int value : values) {
                Cards.add(new Card(suit, value));
            }
        }
    }
    
    ArrayList<Card> getDeck(){
        return Cards;
    }
    
    Card drawCard(){
        Card drawnCard = Cards.get(0);
        Cards.remove(0);
        return drawnCard;
    }
}
