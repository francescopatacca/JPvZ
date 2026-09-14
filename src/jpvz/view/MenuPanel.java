package jpvz.view;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import jpvz.controller.ControllerForView;

import jpvz.utils.UiUtils;
import jpvz.utils.SpriteManager;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.BorderLayout;

public class MenuPanel extends JPanel {

	// ---------------------------------------------------------------
	// STATIC CONSTANTS
	// ---------------------------------------------------------------

    private static final String SCORE_IDENTIFIER="SCORE";
    private static final String SETTING_IDENTIFIER="SETTING";
    private static final String SELECT_PLANT_PANEL_IDENTIFIER="SELECT";

	// ---------------------------------------------------------------
	// INSTANCE ATTRIBUTES
	// ---------------------------------------------------------------

    private JButton jButNewGame;
    private JButton jButSettings;
    private JButton jButScore;
    private JLabel textLabel;
    private JLabel gardenLabel;
    private JLabel nameLabel;
    private JTextField nameField;
    private Image bgImage;

    public MenuPanel() {

        this.setLayout(new BorderLayout());
        bgImage=SpriteManager.getIconImage(0);
        
        // PANNELLO PRINCIPALE 
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (bgImage != null) {
                    g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
                }
            }

            // LAYOUT
            @Override
            public void doLayout() {
                super.doLayout();
                int w = getWidth();
                int h = getHeight();

                int btnW = (int) (w * 0.34);
                int btnH = (int) (h * 0.10);
                int btnX = (int) (w * 0.52);

                int labW= (int)(w*0.4);
                int labH= (int)(h*0.12);
                int labX =(w-labW)/2;

                int namW = (int) (w * 0.3);
                int namH = (int) (h * 0.08);

                if (jButNewGame != null) {
                    jButNewGame.setBounds(btnX, (int) (h * 0.3), btnW, btnH);
                }
                if (jButSettings != null) {
                    jButSettings.setBounds(btnX, (int) (h * 0.41), btnW, btnH);
                }
                if (jButScore != null) {
                    jButScore.setBounds(btnX, (int) (h * 0.52), btnW, btnH);
                }
                if(textLabel!=null){
                    textLabel.setBounds(labX, (int)(h*0.05), labW, labH);
                    int fontSize = Math.max(18, (int) (h * 0.08));
                    textLabel.setFont(new Font("Arial Black", Font.BOLD, fontSize));
                }
                if (gardenLabel != null) {
                    gardenLabel.setBounds((int) (w * 0.01), (int) (h * 0.62), namW, namH);
                }
                if (nameLabel != null) {
                    nameLabel.setBounds((int) (w * 0.01), (int) (h * 0.68), namW, namH);
                }
                if (nameField != null) {
                    int nameX = (w - btnW) / 2;
                    int nameY = (int)((h - btnH) - h*0.1);
                    nameField.setBounds(nameX, nameY, btnW, btnH);
                }
            }
        };

        // TEXTFIELD CHIEDI NOME
        this.nameField=UiUtils.styleAskNameTextField("Inserisci nome e premi INVIO");
        nameField.addActionListener(e->{
            String name=nameField.getText().trim();
            if(!name.isEmpty() && !name.equals("inserisci nome e premi INVIO")){
                nameLabel.setText(name);
                ControllerForView.getInstance().setPlayerName(name);
                nameField.setVisible(false);
            }
        });
        mainPanel.add(nameField);

        // CARTELLO A SX
        this.gardenLabel = UiUtils.styleLabel("Giardino di: ",UiUtils.TEXT_SIGN,16);
        mainPanel.add(gardenLabel);

        this.nameLabel = UiUtils.styleLabel(ControllerForView.getInstance().getPlayerName(),UiUtils.TEXT_SIGN,16);
        mainPanel.add(nameLabel);

        // TITOLO
        this.textLabel = UiUtils.styleLabel("JPvZ",UiUtils.TEXT_GOLD);
        this.textLabel.setOpaque(true);
        this.textLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.textLabel.setVerticalAlignment(SwingConstants.CENTER);
        this.textLabel.setBackground(UiUtils.WOOD_DARK);
        this.textLabel.setBorder(new LineBorder(UiUtils.WOOD_BORDER,3,true));       
        mainPanel.add(textLabel);

        // NEW GAME BUTTON
        this.jButNewGame = UiUtils.styleButton("Inizia nuova partita",UiUtils.STONE_BACKGROUND,UiUtils.TEXT_TOMBSTONE_BOTTON,UiUtils.STONEBORDER,25);
        jButNewGame.addActionListener(e->{
            ControllerForView.getInstance().showPanel(SELECT_PLANT_PANEL_IDENTIFIER);
        });
        mainPanel.add(jButNewGame);

        // SETTING BUTTON
        this.jButSettings = UiUtils.styleButton("Impostazioni",UiUtils.STONE_BACKGROUND,UiUtils.TEXT_TOMBSTONE_BOTTON,UiUtils.STONEBORDER,25);
        jButSettings.addActionListener(e->{
            ControllerForView.getInstance().showPanel(SETTING_IDENTIFIER);
        });
        mainPanel.add(jButSettings);

        // SCORE BUTTON
        this.jButScore = UiUtils.styleButton("Punteggi",UiUtils.STONE_BACKGROUND,UiUtils.TEXT_TOMBSTONE_BOTTON,UiUtils.STONEBORDER,25);
        jButScore.addActionListener(e->{
            ControllerForView.getInstance().showPanel(SCORE_IDENTIFIER);
        });
        mainPanel.add(jButScore);

        this.add(mainPanel);

    }

	// ---------------------------------------------------------------
	// INSTANCE METHODS
	// ---------------------------------------------------------------

    public void updateName(){
        String playerName = ControllerForView.getInstance().getPlayerName();
        if(playerName != null && !playerName.isEmpty()){
            nameLabel.setText(playerName);
            if(!playerName.equals("Giocatore")){
            this.nameField.setVisible(false);
            }
        }
    }

}
