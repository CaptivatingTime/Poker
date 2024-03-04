public class Card {
     String suit;
     int value;

    Card(String suit, int value){
        this.suit = suit;
        this.value = value;
    }

    
    String getSuit(){
        return suit;
    }
    int getValue(){
        return value;
    }
}

