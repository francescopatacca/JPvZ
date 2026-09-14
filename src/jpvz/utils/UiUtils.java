package jpvz.utils;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.SwingConstants;

public class UiUtils {

	// ---------------------------------------------------------------
	// STATIC CONSTANTS
	// ---------------------------------------------------------------
    
    public static final Color BG_DARK_GREEN = new Color(25, 45, 20);
    public static final Color WOOD_LIGHT = new Color(139, 90, 43);
    public static final Color WOOD_DARK = new Color(74, 46, 24);
    public static final Color WOOD_BORDER = new Color(43, 23, 11);
    public static final Color TEXT_GOLD = new Color(245, 230, 163);
    public static final Color STONE_BACKGROUND = new Color(66, 71, 77);
    public static final Color STONEBORDER = new Color(38, 41, 45);
    public static final Color TEXT_TOMBSTONE_BOTTON = new Color(74, 46, 24);
    public static final Color TEXTCOLOR = new Color(30, 77, 43);
    public static final Color TEXT_SIGN = new Color(43, 23, 11);
    public static final Color GRID_ERROR_COLOR=new Color(255, 0, 0, 100);    
    private static final Color FOREGROUND_ERROR_COLOR=new Color(255, 225, 225);
    private static final Color BACKGROUND_ERROR_COLOR=new Color(190, 25, 25);
    private static final Color BORDER_ERROR_COLOR=new Color(130, 0, 15);

	// ---------------------------------------------------------------
	// CONSTRUCTOR
	// ---------------------------------------------------------------

    private UiUtils(){
        
    }

	// ---------------------------------------------------------------
	// STATIC METHODS
	// ---------------------------------------------------------------

    public static JLabel styleLabel(String text){
        JLabel lab=new JLabel(text);
        lab.setForeground(TEXT_GOLD);
        lab.setFont(new Font("Arial Black", Font.BOLD, 20));
        return lab;
    }

    public static JLabel styleLabel(String text,Color color){
        JLabel lab=new JLabel(text);
        lab.setForeground(color);
        lab.setFont(new Font("Arial Black", Font.BOLD, 20));
        return lab;
    }


    public static JLabel styleLabel(String text,int size){
        JLabel lab=new JLabel(text);
        lab.setForeground(TEXT_GOLD);
        lab.setFont(new Font("Arial Black", Font.BOLD, size));
        return lab;
    }

    public static JLabel styleLabel(String text,Color color, int size){
        JLabel lab=new JLabel(text);
        lab.setForeground(color);
        lab.setFont(new Font("Arial Black", Font.BOLD, size));
        return lab;
    }

    public static JLabel errorLabel(String text){
        JLabel lab=new JLabel(text);
        lab.setOpaque(true);
        lab.setHorizontalAlignment(SwingConstants.CENTER);
        lab.setPreferredSize(new Dimension(500,100));
        lab.setForeground(FOREGROUND_ERROR_COLOR);
        lab.setBackground(BACKGROUND_ERROR_COLOR);
        lab.setBorder(new LineBorder(BORDER_ERROR_COLOR,3,true));
        lab.setFont(new Font("Arial Black", Font.BOLD, 20));
        return lab;
    }

    public static JButton styleButton(String text,Color bgColor,Color fgColor,Color border,int size){
        JButton but=new JButton(text);
        but.setBackground(bgColor);
        but.setForeground(fgColor);
        but.setFont(new Font("Arial Black", Font.BOLD, size));
        but.setBorder(new LineBorder(border, 3, true));
        return but;
    }

    public static JButton plantButton(String text,Image icon){
        JButton pl=new JButton("<html><center>" + text + "</center></html>"){
            @Override
            protected void paintComponent(Graphics g) {
                if(getHeight()>0){
                    setFont(getFont().deriveFont(Math.max(9f, getHeight() * 0.10f)));
                }
                super.paintComponent(g);
            };
        };

        pl.setPreferredSize(new Dimension(120,150));
        pl.setBackground(WOOD_LIGHT);
        pl.setForeground(TEXT_GOLD);
        pl.setFont(new Font("Arial Black", Font.PLAIN, 15));
        pl.setBorder(new LineBorder(WOOD_BORDER, 3, true));
        pl.setMargin(new Insets(10, 10, 10, 10));
        if(icon!=null){
            pl.setIcon(new Icon() {
                @Override
                public void paintIcon(Component c, Graphics g, int x, int y) {
                    int w=getIconWidth();
                    int h=getIconHeight();
                    g.drawImage(icon, x, y,w,h, pl);
                }

                @Override
                public int getIconHeight() {
                    if(pl.getHeight()>0){
                        int h=(int)(pl.getHeight()*0.5);
                        return h ;
                    }else
                        return 70;
                }

                @Override
                public int getIconWidth() {
                    if(pl.getWidth()>0){
                        int w=(int)(pl.getWidth()*0.8);
                        return w;
                    }else
                        return 100;
                }
            });
        }

        pl.setVerticalTextPosition(SwingConstants.BOTTOM);
        pl.setHorizontalTextPosition(SwingConstants.CENTER);
        return pl;
    }

    public static JTextField styleAskNameTextField(String text){
        JTextField jTxt=new JTextField(text);
        jTxt.setHorizontalAlignment(JTextField.CENTER);
        jTxt.setFont(new Font("Arial Black", Font.BOLD,25));
        jTxt.setBorder(BorderFactory.createLineBorder(STONEBORDER, 3, true));
        jTxt.setBackground(STONE_BACKGROUND);
        jTxt.setForeground(TEXT_TOMBSTONE_BOTTON);
        return jTxt;
    }

    public static JTextField styleChangeNameTextField(String text){
        JTextField nameField=new JTextField(text);
        nameField.setFont(new Font("Arial Black", Font.PLAIN,20));
        nameField.setHorizontalAlignment(SwingConstants.CENTER);
        nameField.setBackground(WOOD_LIGHT);
        nameField.setForeground(TEXT_GOLD);
        nameField.setBorder(BorderFactory.createLineBorder(WOOD_BORDER, 3, true));
        return nameField;
    }

    public static <T>JComboBox <T> styleBox(T[] arr){
        JComboBox<T> box = new JComboBox<>(arr);
        box.setBackground(WOOD_LIGHT);
        box.setForeground(TEXT_GOLD);
        DefaultListCellRenderer centerRenderer = new DefaultListCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        box.setRenderer(centerRenderer);
        box.setFont(new Font("Arial Black", Font.PLAIN, 20));
        box.setBorder(BorderFactory.createLineBorder(WOOD_BORDER, 3, true));       
        return box;
    }

}
