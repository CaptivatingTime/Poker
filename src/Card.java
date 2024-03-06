public class Card {
       private String suit;
       private int value;

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

