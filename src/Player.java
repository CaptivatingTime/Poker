import java.util.HashMap;

public class Player {
    private String name;
    private Double balance;

    private  HashMap <String, Double> Payouts = new HashMap<>();

    Player(){
        this.Payouts.put("Royal Flush",         500.0);
        this.Payouts.put("Straight Flush", 50.0);
        this.Payouts.put("Quads",          10.0);
        this.Payouts.put("Full House",      3.0);
        this.Payouts.put("Flush",           1.5);
        this.Payouts.put("Straight",        1.0);   
        this.balance = 1000.0;      
    }


    void setName(String name){
        this.name = name;
    }
    double addBalance(double ante, double playBet, String combination, boolean dealerQualifies){
        double blindPayout = 0;
        double totalWin   = 0;
        double anteBet    = ante/2;
        double blindBet   = ante/2;
        if (this.Payouts.get(combination) == null){
            blindPayout = 0;
        }else{
            blindPayout = this.Payouts.get(combination);
        }
        if(dealerQualifies){
            
            totalWin  = anteBet + anteBet + blindBet + blindBet * blindPayout + playBet;
            this.balance = this.balance + totalWin;
        }else{
            totalWin =  anteBet + blindBet + blindBet * blindPayout + playBet;
            this.balance = this.balance + totalWin;
        }
        return totalWin;
        
    }

    void returnMoney(double totalBet){
        this.balance = this.balance + totalBet;
    }
    void deductBalance(double amount){
        this.balance = this.balance - amount;
    }
    public Double getBalance(){
        return this.balance;
    }
}
