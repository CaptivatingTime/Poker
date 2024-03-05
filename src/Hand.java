import java.util.HashMap;

public class Hand {
    
     private Card hand[] = new Card[2];
     private HashMap<Integer, String> pictureNominals = new HashMap<>();
     
     

      void createPictureNominals (){
        String[] pictures = {"J", "Q", "K", "A"};
        int i = 11;
        for (String picture : pictures){
            pictureNominals.put(i, picture);
            i++;
        }
     }
     
      String numToPicture(Card [] cardCollection, int value, int i){
        String handString = "";
        if (value >= 11){
            
            String picture = pictureNominals.get(value);
            
             handString = picture + " " + cardCollection[i].getSuit();
        }else{
             handString = cardCollection[i].getValue() + " " + cardCollection[i].getSuit();
        }
        return handString;
     }
     
     void addCard(Card card){
        if (hand[0] == null){
            this.hand[0] = card;
        }else{
            this.hand[1] = card;
        }
    }

    
    String getHandString(int i){
        createPictureNominals();
        int cardValue = this.hand[i].getValue();
        String handString = numToPicture(this.hand, cardValue, i);
        //String nominal = this.hand[i].getValue() + " ";
        
        //String handString = this.hand[i].getValue() + " " + this.hand[i].getSuit();
        return handString;
    }
    
    Card [] getHand(){
        return this.hand;
    }
}
