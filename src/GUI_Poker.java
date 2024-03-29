
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
public class GUI_Poker extends JFrame {


    private JPanel panelPlayer = new JPanel();
    private JPanel panelBoard  = new JPanel();
    private JPanel panelDealer = new JPanel();
    private JPanel actionBar = new JPanel();
    private JPanel boardCards = new JPanel();

    private JLabel cardPlayer1 = new JLabel();
    private JLabel cardDealer1 = new JLabel();
    private JLabel cardPlayer2 = new JLabel();
    private JLabel cardDealer2 = new JLabel();
    private JLabel cardBoard1  = new JLabel();
    private JLabel cardBoard2  = new JLabel();
    private JLabel cardBoard3  = new JLabel();
    private JLabel cardBoard4  = new JLabel();
    private JLabel cardBoard5  = new JLabel();

    JButton buttonCheck = new JButton();


 
    
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
        
        ImageIcon icon = new ImageIcon("icon.png");

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
        this.panelPlayer.setBackground(new Color(200,198,175));
        this.actionBar.setBackground(new Color(70,70,70));
        //panel3.setBackground(Color.yellow);
        //panel4.setBackground(Color.magenta);
        //panel5.setBackground(Color.blue);
        this.panelDealer.setLayout(new GridBagLayout());
        this.panelBoard.setLayout(new BorderLayout());
        this.panelPlayer.setLayout(new GridBagLayout());
        this.actionBar.setLayout(new BorderLayout());
        
        this.panelDealer.setPreferredSize(new Dimension(100,110));
		this.panelBoard.setPreferredSize(new Dimension(150,100));
		this.panelPlayer.setPreferredSize(new Dimension(150,110));
        this.actionBar.setPreferredSize(new Dimension(150,35));
		panel4.setPreferredSize(new Dimension(100,100));
		panel5.setPreferredSize(new Dimension(100,100));

        this.add(this.panelDealer,BorderLayout.NORTH);
        this.add(this.panelBoard,BorderLayout.CENTER);
        this.add(this.panelPlayer,BorderLayout.SOUTH);

        this.panelBoard.add(this.actionBar,BorderLayout.SOUTH);
        

        this.setVisible(true);

        
    }

    private void insertCard(String card, JLabel cardLabel, JPanel panel, boolean board){
        Image image = resizeImage(card, cardLabel.getWidth(), cardLabel.getHeight());
        ImageIcon imageIcon = new ImageIcon(image);
        cardLabel.setIcon(imageIcon);
        if (board){
            //cardLabel.setHorizontalAlignment(JLabel.NEXT);
            this.boardCards.add(cardLabel);
            panel.add(boardCards, BorderLayout.CENTER);
            
        }else{
            panel.add(cardLabel); 
        }
          
        this.setVisible(true);
        this.repaint(); 
    }

    void showCard(String card, int pos){
        boolean board = false;
        switch (pos) {
            case 1:
            insertCard(card, cardPlayer1, panelPlayer, board);
                break;
        
            case 2:
            insertCard(card, cardDealer1, panelDealer, board);               
                break;

            case 3:
            insertCard(card, cardPlayer2, panelPlayer, board); 
                break;  

            case 4:
            insertCard(card, cardDealer2, panelDealer, board);   
                break;  
                
            case 5:
            board = true;
            insertCard(card, cardBoard1, panelBoard, board);  
                break;
        
            case 6:
            board = true;
            insertCard(card, cardBoard2, panelBoard, board);  
                break;

            case 7:
            board = true;
            insertCard(card, cardBoard3, panelBoard, board);  
                break;   

            case 8:
            board = true;
            insertCard(card, cardBoard4, panelBoard, board);  
                break;  

            case 9:
            board = true;
            insertCard(card, cardBoard5, panelBoard, board);  
                break;                                              
        }
    }

    void highlightWinner(String side){
        if (side.equals("dealer")){

            this.panelDealer.setBackground(new Color(224,202,60));
            this.panelPlayer.setBackground(new Color(149,25,12));
            
           
        }else{
            this.panelPlayer.setBackground(new Color(224,202,60));
            this.panelDealer.setBackground(new Color(149,25,12));
        }

    }
    void removeCards(){
        this.panelDealer.removeAll();
        this.panelBoard.remove(boardCards);
        this.panelPlayer.removeAll();
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

}
