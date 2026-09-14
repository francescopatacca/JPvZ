package jpvz.view;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;

import java.util.ArrayList;
import java.util.List;

import jpvz.utils.UiUtils;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import jpvz.controller.ControllerForView;




public class ScorePanel extends JPanel {

    // ---------------------------------------------------------------
	// STATIC CONSTANTS
	// ---------------------------------------------------------------

    private static final String MENU_IDENTIFIER = "MENU";

	// ---------------------------------------------------------------
	// INSTANCE ATTRIBUTES
	// ---------------------------------------------------------------

    private List<String> score = new ArrayList<>();
    private JList<String> scoreList;

    public ScorePanel() {
        this.setBackground(UiUtils.BG_DARK_GREEN);
        this.setLayout(new GridBagLayout());
        this.score = ControllerForView.getInstance().getScoreList();

        // TITOLO
        JLabel scoreLabel = UiUtils.styleLabel("Punteggi", 30);

        // PANNELLO CENTRALE
        int panelH = 632;
        int panelW = 1000;
        JPanel centralPanel = new JPanel();
        centralPanel.setBackground(UiUtils.WOOD_DARK);
        centralPanel.setPreferredSize(new Dimension(panelW, panelH));
        centralPanel.setBorder(BorderFactory.createLineBorder(UiUtils.WOOD_BORDER, 3, true));

        // PANNELLO SCORREVOLE
        this.scoreList = new JList<>(this.score.toArray(new String[0]));
        JScrollPane scrollPane = new JScrollPane(this.scoreList);
        this.scoreList.setBackground(UiUtils.WOOD_LIGHT);
        this.scoreList.setForeground(UiUtils.TEXT_GOLD);
        this.scoreList.setFont(new Font("Arial Black", Font.BOLD, 20));
        this.scoreList.setBorder(BorderFactory.createLineBorder(UiUtils.WOOD_BORDER, 3, true));

        // TORNA INDIETRO BOTTONE
        JButton backButton = UiUtils.styleButton("Torna indietro", UiUtils.WOOD_LIGHT, UiUtils.TEXT_GOLD, UiUtils.WOOD_BORDER, 25);
        backButton.addActionListener(e -> {
            ControllerForView.getInstance().showPanel(MENU_IDENTIFIER);
        });

        // LAYOUT
        GroupLayout layout = new GroupLayout(centralPanel);
        centralPanel.setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);

        int proportionalComponentH = (int) (panelH * 0.1);
        int proportionalComponentW = (int) (panelW * 0.7);
        int scrollPaneH = (int) (panelH * 0.55);
        int gapX = (panelW - proportionalComponentW) / 2;

        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                        .addGap(gapX)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                                .addComponent(scoreLabel)
                                .addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, proportionalComponentW, GroupLayout.PREFERRED_SIZE)
                                .addComponent(backButton, GroupLayout.PREFERRED_SIZE, proportionalComponentW, GroupLayout.PREFERRED_SIZE))
                        .addGap(gapX)
        );

        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addGap(30)
                        .addComponent(scoreLabel)
                        .addGap(20)
                        .addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, scrollPaneH, GroupLayout.PREFERRED_SIZE)
                        .addGap(25)
                        .addComponent(backButton, GroupLayout.PREFERRED_SIZE, proportionalComponentH, GroupLayout.PREFERRED_SIZE)
                        .addGap(30)
        );

        this.add(centralPanel);
    }

	// ---------------------------------------------------------------
	// INSTANCE METHODS
	// ---------------------------------------------------------------

    public void updateScoreList(){
        this.score=ControllerForView.getInstance().getScoreList();
        this.scoreList.setListData(this.score.toArray(new String[0]));
    }

}
