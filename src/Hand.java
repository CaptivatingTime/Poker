import java.util.HashMap;

public class Hand {
    
     private Card hand[] = new Card[2];
     private HashMap<Integer, String> pictureNominals = new HashMap<>();
     
     

      void createPictureNominals (){
        String[] pictures = {"J", "Q", "K", "A"};
        int i = 11;
        for (String picture : pictures){
            this.pictureNominals.put(i, picture);
            i++;
        }
     }
     
      String numToPicture(Card [] cardCollection, int value, int i){
        String handString = "";
        if (value >= 11){
            createPictureNominals();
            String picture = pictureNominals.get(value);
            
             handString = picture + "_of_" + cardCollection[i].getSuit();
        }else{
             handString = cardCollection[i].getValue() + "_of_" + cardCollection[i].getSuit();
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

    
    int[] getkKickers(){
        int[] kickers = new int[2];
        if (this.hand[0].getValue() > this.hand[1].getValue()){
            kickers[0] = this.hand[0].getValue();
            kickers[1] = this.hand[1].getValue();
        }else if (this.hand[0].getValue() < this.hand[1].getValue()){
            kickers[0] = this.hand[1].getValue();
            kickers[1] = this.hand[0].getValue();        
        }else{
            // Both are equal
            kickers[0] = this.hand[1].getValue();
            kickers[1] = this.hand[0].getValue();         
        }
        return kickers;
    }

    String  getHandString(int i){
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
