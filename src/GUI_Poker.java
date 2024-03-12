import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
public class GUI_Poker extends JFrame {


    private JPanel panel3 = new JPanel();
    private JPanel panel2 = new JPanel();
    private JPanel panel1 = new JPanel();
        
    private JLabel card1 = new JLabel();
    private JLabel card2 = new JLabel();
    private JLabel card3 = new JLabel();
    private JLabel card4 = new JLabel();
    private JLabel card5 = new JLabel();
    private JLabel card6 = new JLabel();
    private JLabel card7 = new JLabel();
    private JLabel card8 = new JLabel();
    private JLabel card9 = new JLabel();



    
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
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500,500);
        this.setLayout(new BorderLayout());
        



        
        this.card1.setBounds(100,100,70,100);
        this.card2.setBounds(100,100,70,100);
        this.card3.setBounds(100,100,70,100);
        this.card4.setBounds(100,100,70,100);
        this.card5.setBounds(100,100,70,100);
        this.card6.setBounds(100,100,70,100);
        this.card7.setBounds(100,100,70,100);
        this.card8.setBounds(100,100,70,100);
        this.card9.setBounds(100,100,70,100);
   

        
         
        JPanel panel4 = new JPanel();
        JPanel panel5 = new JPanel();
        
        this.panel1.setBackground(Color.lightGray);
        this.panel2.setBackground(Color.green);
        this.panel3.setBackground(Color.lightGray);
        //panel3.setBackground(Color.yellow);
        //panel4.setBackground(Color.magenta);
        //panel5.setBackground(Color.blue);

        this.panel2.setLayout(new GridBagLayout());

        this.panel1.setPreferredSize(new Dimension(100,100));
		this.panel2.setPreferredSize(new Dimension(150,100));
		this.panel3.setPreferredSize(new Dimension(150,100));
		panel4.setPreferredSize(new Dimension(100,100));
		panel5.setPreferredSize(new Dimension(100,100));

        this.add(this.panel1,BorderLayout.NORTH);
        this.add(this.panel2,BorderLayout.CENTER);
        this.add(this.panel3,BorderLayout.SOUTH);
        

        this.setVisible(true);
    }

    private void insertCard(String card, JLabel cardLabel, JPanel panel){
        Image image = resizeImage(card, cardLabel.getWidth(), cardLabel.getHeight());
        ImageIcon imageIcon = new ImageIcon(image);
        cardLabel.setIcon(imageIcon);

        panel.add(cardLabel);   
        this.setVisible(true);
        this.repaint(); 
    }

    void showCard(String card, int pos){
        switch (pos) {
            case 1:
            insertCard(card, card1, panel3);
                break;
        
            case 2:
            insertCard(card, card2, panel1);               
                break;
            case 3:
            insertCard(card, card3, panel3); 
                break;      
            case 4:
            insertCard(card, card4, panel1);   
                break;  
                
            case 5:
            insertCard(card, card5, panel2);  
                break;
        
            case 6:
            insertCard(card, card6, panel2);  
                break;
            case 7:
            insertCard(card, card7, panel2);  
                break;    
            case 8:
            insertCard(card, card8, panel2);  
                break;  
            case 9:
            insertCard(card, card9, panel2);  
                break;                                              
        }
    }
    void removeCards(){
        this.panel1.removeAll();
        this.panel2.removeAll();
        this.panel3.removeAll();



        this.panel1.repaint();
        this.panel2.repaint();
        this.panel3.repaint();
        
        this.panel1.revalidate();
        this.panel2.revalidate();
        this.panel3.revalidate();
        
       
    }
}
