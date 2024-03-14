
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
public class GUI_Poker extends JFrame implements ActionListener{

    private JPanel panelDealer = new JPanel();
    private JPanel panelBoard  = new JPanel();
    private JPanel boardCards  = new JPanel();
    private JPanel actionBar   = new JPanel();
    private JPanel panelPlayer = new JPanel(); 
    private JPanel playerCards  = new JPanel(); 
    private JPanel infoPanel   = new JPanel(); 


    private JLabel cardPlayer1 = new JLabel();
    private JLabel cardDealer1 = new JLabel();
    private JLabel cardPlayer2 = new JLabel();
    private JLabel cardDealer2 = new JLabel();
    private JLabel cardBoard1  = new JLabel();
    private JLabel cardBoard2  = new JLabel();
    private JLabel cardBoard3  = new JLabel();
    private JLabel cardBoard4  = new JLabel();
    private JLabel cardBoard5  = new JLabel();

    private JLabel betAmount   = new JLabel();
    private JLabel balanceAmount     = new JLabel();
    private JLabel balanceUpdate     = new JLabel();

    JLabel betField = new JLabel();

    JLabel winnerField = new JLabel();

    private JButton buttonIncreaseBet  = new JButton();
    private JButton buttonDecreaseBet  = new JButton();
    private JButton buttonStartGame    = new JButton();
    private JButton buttonX4           = new JButton();
    private JButton buttonX3           = new JButton();
    private JButton buttonX2           = new JButton();
    private JButton buttonX1           = new JButton();
    private JButton buttonCheck        = new JButton();
    private JButton buttonFold         = new JButton();
    private JButton playAgain          = new JButton();

    private double amount;
    private double balance;

    private boolean checkPressed = false;
    

    @Override
    public void actionPerformed(ActionEvent e) {
         

        
        if(e.getSource()==buttonIncreaseBet){
            if (Gameplay.getBalance() > this.amount*2 + 50){
                this.amount = amount + 50;
            }
            
            
            this.betAmount.setText(""+amount);
            
        } else if(e.getSource()==buttonDecreaseBet){
            amount = amount - 50;
            if (amount < 0){
                amount = 0;
            }  
            this.betAmount.setText(""+amount);

        }else if (e.getSource()==buttonStartGame){
            Gameplay.gameStart();
            this.actionBar.removeAll();
            showButtonsBeforeFlop();

        } else if (e.getSource()==buttonCheck){
            if (!checkPressed){
                Gameplay.performCheck();
                this.actionBar.removeAll();
                showButtonsAfterFlop();
                checkPressed = true;
            }else{
                Gameplay.performCheck();
                this.actionBar.removeAll();
                showButtonsAfterTurnAndRiver();
                checkPressed = false;
            }

        }else if (e.getSource()==buttonX1){
            //this.amount = this.amount + this.amount;
            this.actionBar.removeAll();
            Gameplay.placePlayBet(1);
            
        }else if (e.getSource()==playAgain){
            checkPressed = false;
            amount = 0;
            this.betAmount.setText("0");
            this.infoPanel.remove(balanceUpdate);
            this.infoPanel.remove(balanceAmount);
            balanceAmount.setText(""+Gameplay.getBalance());
            this.infoPanel.add(balanceAmount);

            this.actionBar.removeAll();
            this.actionBar.add(buttonStartGame);
            this.actionBar.add(betField);
            this.actionBar.add(buttonIncreaseBet);
            this.actionBar.add(betAmount);
            this.actionBar.add(buttonDecreaseBet);

            this.playerCards.setBackground(new Color(200,198,175));
            
            removeCards();
            this.repaint();
            this.setVisible(true);

            Gameplay.prepearNewGame();
            
        }else if (e.getSource()==buttonX2){
            this.actionBar.removeAll();
            Gameplay.placePlayBet(2);

        }else if (e.getSource()==buttonX3){
            this.actionBar.removeAll();
            Gameplay.placePlayBet(3);

        }else if (e.getSource()==buttonX4){
            this.actionBar.removeAll();
            Gameplay.placePlayBet(4);
        }
    }
    


    private Image resizeImage(String card,int w,int h){
        BufferedImage img = null;
        try {
            img = ImageIO.read(new File("cards/" + card +".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image resized = img.getScaledInstance(w, h,
        Image.SCALE_SMOOTH);          

        return resized;
    }
    GUI_Poker(){
        
        this.buttonIncreaseBet.addActionListener(this);
        this.buttonIncreaseBet.setText("+");

        this.buttonDecreaseBet.addActionListener(this);
        this.buttonDecreaseBet.setText("-");

        this.buttonStartGame.addActionListener(this);
        this.buttonStartGame.setText("Play");

        this.buttonX4.addActionListener(this);
        this.buttonX4.setText("x4");

        this.buttonX3.addActionListener(this);
        this.buttonX3.setText("x3");

        this.buttonX2.addActionListener(this);
        this.buttonX2.setText("x2");

        this.buttonX1.addActionListener(this);
        this.buttonX1.setText("x1");

        this.buttonCheck.addActionListener(this);
        this.buttonCheck.setText("check");

        this.buttonFold.addActionListener(this);
        this.buttonFold.setText("fold");

        this.playAgain.addActionListener(this);
        this.playAgain.setText("Play again");

        ImageIcon icon = new ImageIcon("icon.png");

        
        this.betField.setText("Bet: ");
        this.betAmount.setText("0");

        JLabel balanceField = new JLabel();
        balanceField.setText("Your balace: ");
        this.balance = Gameplay.getBalance();
        this.balanceAmount.setText("" + balance);
        
        
        


        this.setTitle("Poker");
        this.setIconImage(icon.getImage());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800,500);
        this.setLayout(new BorderLayout());
        
        



        
        this.cardPlayer1.setBounds(100,100,70,100);
        this.cardDealer1.setBounds(100,100,70,100);
        this.cardPlayer2.setBounds(100,100,70,100);
        this.cardDealer2.setBounds(100,100,70,100);
        this.cardBoard1.setBounds(100,100,100,150);
        this.cardBoard2.setBounds(100,100,100,150);
        this.cardBoard3.setBounds(100,100,100,150);
        this.cardBoard4.setBounds(100,100,100,150);
        this.cardBoard5.setBounds(100,100,100,150);
   

        
         
        JPanel panel4 = new JPanel();
        JPanel panel5 = new JPanel();
        
        this.panelDealer.setBackground(new Color(200,198,175));
        this.panelBoard.setBackground(new Color(91,168,121));
        this.boardCards.setBackground(new Color(91,168,121));
        this.actionBar.setBackground(new Color(228,214,167));
        this.panelPlayer.setBackground(new Color(200,198,175));
        this.playerCards.setBackground(new Color(200,198,175));
        this.infoPanel.setBackground(new Color(228,214,167));

        //panel3.setBackground(Color.yellow);
        //panel4.setBackground(Color.magenta);
        //panel5.setBackground(Color.blue);
        this.panelDealer.setLayout(new GridBagLayout());
        this.panelBoard.setLayout(new BorderLayout());
        this.panelPlayer.setLayout(new BorderLayout());
        this.actionBar.setLayout(new GridBagLayout());
        this.infoPanel.setLayout(new GridBagLayout());
        
        this.panelDealer.setPreferredSize(new Dimension(100,110));
		this.panelBoard.setPreferredSize(new Dimension(150,100));
        this.actionBar.setPreferredSize(new Dimension(150,35));
		this.panelPlayer.setPreferredSize(new Dimension(150,145));
        this.infoPanel.setPreferredSize(new Dimension(150,35));


		panel4.setPreferredSize(new Dimension(100,100));
		panel5.setPreferredSize(new Dimension(100,100));

        this.add(this.panelDealer,BorderLayout.NORTH);
        this.add(this.panelBoard,BorderLayout.CENTER);
        this.add(this.panelPlayer,BorderLayout.SOUTH);

        this.panelBoard.add(this.actionBar,BorderLayout.SOUTH);
        this.panelPlayer.add(this.infoPanel,BorderLayout.SOUTH);

        

        this.setVisible(true);
        
        this.actionBar.add(buttonStartGame);
        this.actionBar.add(betField);
        this.actionBar.add(buttonIncreaseBet);
        this.actionBar.add(betAmount);
        this.actionBar.add(buttonDecreaseBet);

        this.infoPanel.add(balanceField);
        this.infoPanel.add(balanceAmount);
    }

    void showButtonsBeforeFlop(){
        this.actionBar.add(buttonX4);
        this.actionBar.add(buttonX3);
        this.actionBar.add(buttonCheck);
        //this.actionBar.repaint();
        this.setVisible(true);
    }
    void showButtonsAfterFlop(){
        this.actionBar.add(buttonX2);
        this.actionBar.add(buttonCheck);
        //this.actionBar.repaint();
        this.setVisible(true);
    }
    void showButtonsAfterTurnAndRiver(){
        this.actionBar.add(buttonX1);
        this.actionBar.add(buttonFold);
        //this.actionBar.repaint();
        this.setVisible(true);
    }        

    private void insertCard(String card, JLabel cardLabel, JPanel panel, boolean board, boolean player){
        Image image = resizeImage(card, cardLabel.getWidth(), cardLabel.getHeight());
        ImageIcon imageIcon = new ImageIcon(image);
        cardLabel.setIcon(imageIcon);
        if (board){
            //cardLabel.setHorizontalAlignment(JLabel.NEXT);
            
            this.boardCards.add(cardLabel);
            panel.add(boardCards, BorderLayout.CENTER);
            
        }else if (player){
            this.playerCards.add(cardLabel);
            panel.add(playerCards, BorderLayout.CENTER);
        }else{
            panel.add(cardLabel); 
        }
          
        this.setVisible(true);
        this.repaint(); 
        
    }

    void showCard(String card, int pos){
        boolean board = false;
        boolean player = false;

        switch (pos) {
            case 1:
            player = true;
            insertCard(card, cardPlayer1, panelPlayer, board, player);
            player = false;
                break;
        
            case 2:
            insertCard(card, cardDealer1, panelDealer, board, player);               
                break;

            case 3:
            player = true;
            insertCard(card, cardPlayer2, panelPlayer, board, player); 
            player = false;
                break;  

            case 4:
            insertCard(card, cardDealer2, panelDealer, board, player);   
                break;  
                
            case 5:
            board = true;
            insertCard(card, cardBoard1, panelBoard, board, player);  
                break;
        
            case 6:
            board = true;
            insertCard(card, cardBoard2, panelBoard, board, player);  
                break;

            case 7:
            board = true;
            insertCard(card, cardBoard3, panelBoard, board, player);  
                break;   

            case 8:
            board = true;
            insertCard(card, cardBoard4, panelBoard, board, player);  
                break;  

            case 9:
            board = true;
            insertCard(card, cardBoard5, panelBoard, board, player);  
                break;                                              
        }
    }

    void highlightWinner(String side){
        if (side.equals("dealer")){

            this.panelDealer.setBackground(new Color(224,202,60));
            this.playerCards.setBackground(new Color(149,25,12));
            this.winnerField.setText("DEALER wins with " + Gameplay.getCombinaton("dealer") + "!");
            this.balance = this.balance - Gameplay.getTotalBet();

            this.balanceUpdate.setText("-"+ Gameplay.getTotalBet());
            this.balanceUpdate.setForeground(new Color(107,15,26));
            this.infoPanel.add(this.balanceUpdate);
        }else if (side.equals("player")){
            this.playerCards.setBackground(new Color(224,202,60));
            this.panelDealer.setBackground(new Color(149,25,12));
            this.winnerField.setText("YOU win " + Gameplay.getTotalWin() + " with " + Gameplay.getCombinaton("player") + "!");
            this.balance = this.balance + Gameplay.getTotalWin();
            
            this.balanceUpdate.setText("+"+Gameplay.getTotalWin());
            this.balanceUpdate.setForeground(new Color(77,139,49));
            //this.infoPanel.remove(this.balanceAmount);
            this.infoPanel.add(this.balanceUpdate);
        }else{
            this.winnerField.setText("It is a push!");
        }

        this.actionBar.add(this.winnerField);
        this.actionBar.add(this.playAgain);
        this.actionBar.repaint();  
        this.setVisible(true);


    }
    void removeCards(){
        this.panelDealer.removeAll();
        this.panelBoard.remove(boardCards);
        this.panelPlayer.remove(playerCards);
        this.boardCards.removeAll();

        this.panelDealer.setBackground(new Color(200,198,175));
        this.panelPlayer.setBackground(new Color(200,198,175));

        this.panelDealer.repaint();
        this.panelBoard.repaint();
        this.panelPlayer.repaint();
        
        this.panelDealer.revalidate();
        this.panelBoard.revalidate();
        this.panelPlayer.revalidate();
        
       
    }
    public double getBet(){
        return amount;
    }


}
