package jpvz.view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;

import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import jpvz.controller.ControllerForView;

import jpvz.utils.SpriteManager;
import jpvz.utils.UiUtils;

public class EndGamePanel extends JPanel {

	// ---------------------------------------------------------------
	// STATIC CONSTANTS
	// ---------------------------------------------------------------

    private static final String MENU_IDENTIFIER = "MENU";
    private static final int WIN_ICON_ID=5;
    private static final int LOSE_ICON_ID=1;

	// ---------------------------------------------------------------
	// INSTANCE ATTRIBUTES
	// ---------------------------------------------------------------

    private Image winImage;
    private Image loseImage;
    private JLabel endLabel;
    private JLabel scoreLabel;

    public EndGamePanel() {
        this.setOpaque(false);
        this.setLayout(new GridBagLayout());
        int score=0;

        this.winImage = SpriteManager.getIconImage(WIN_ICON_ID);
        this.loseImage = SpriteManager.getIconImage(LOSE_ICON_ID);
        this.endLabel = new JLabel(new ImageIcon(this.winImage));

        // PANNELLO MENU
        JPanel menuPanel=new JPanel(new GridLayout(3,1,10,10));
        menuPanel.setPreferredSize(new Dimension(220,140));
        menuPanel.setBackground(UiUtils.WOOD_DARK);
        menuPanel.setBorder(new LineBorder(UiUtils.WOOD_BORDER, 3, true));

    
        //PANNELLO PRINCIPALE
        JPanel mainPanel=new JPanel(new GridBagLayout()){
            @Override 
            public void doLayout(){
                int w=EndGamePanel.this.getWidth();
                int h=EndGamePanel.this.getHeight();
                if(w>0 && h>0){
                    menuPanel.setPreferredSize(new Dimension(Math.max(220, (int) (w* 0.3)), Math.max(140, (int) (h*0.3))));
                }
                super.doLayout();
            }
        };
        mainPanel.setOpaque(false);

        JLabel scoreText=UiUtils.styleLabel("Score:");
        scoreText.setHorizontalAlignment(SwingConstants.CENTER);

        this.scoreLabel=UiUtils.styleLabel(String.valueOf(score));
        this.scoreLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JButton menuButton=UiUtils.styleButton("Menu",  UiUtils.WOOD_LIGHT, UiUtils.TEXT_GOLD, UiUtils.WOOD_BORDER, 12);
        menuButton.addActionListener(e->{
            this.setVisible(false);
            ControllerForView.getInstance().saveCurrentScore();
            ControllerForView.getInstance().stopTimer();
            ControllerForView.getInstance().stopAll();
            ControllerForView.getInstance().resetGame();
            ControllerForView.getInstance().showPanel(MENU_IDENTIFIER);
        });

        GridBagConstraints gbc= new GridBagConstraints();
        gbc.gridx=0;
        gbc.gridy=0;
        gbc.insets=new Insets(10, 10, 10, 10);
        gbc.anchor=GridBagConstraints.SOUTH;

        mainPanel.add(this.endLabel,gbc);   

        menuPanel.add(scoreText);
        menuPanel.add(this.scoreLabel);
        menuPanel.add(menuButton);

        gbc.gridy=1;
        mainPanel.add(menuPanel,gbc);

        this.add(mainPanel,gbc);

    }

	// ---------------------------------------------------------------
	// INSTANCE METHODS
	// ---------------------------------------------------------------

    public void winLose() {
        if (ControllerForView.getInstance().isGameOver()) {
            this.endLabel.setIcon(new ImageIcon(this.loseImage));
        } else if (ControllerForView.getInstance().isVictory()) {
            this.endLabel.setIcon(new ImageIcon(this.winImage));
        }
        ControllerForView.getInstance().stopAll();
        this.scoreLabel.setText(String.valueOf(ControllerForView.getInstance().getScore()));
        this.revalidate();
        this.repaint();
    }
}
