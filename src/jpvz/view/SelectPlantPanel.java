package jpvz.view;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import jpvz.controller.ControllerForView;

import jpvz.utils.SpriteManager;
import jpvz.utils.UiUtils;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;

public class SelectPlantPanel extends JPanel {

	// ---------------------------------------------------------------
	// STATIC CONSTANTS
	// ---------------------------------------------------------------

    private static final String MENU_IDENTIFIER = "MENU";
    private static final String GAME_PANEL_IDENTIFIER = "GAME";
    private static final String[] PLANT_NAMES = {
            "Cactus",
            "Catapulta Mais",
            "Catapulta Peperoni",
            "Cannone Cocco",
            "Fungo",
            "Fungo Solare",
            "Girasole",
            "Muro di Noci",
            "Sparapiselli",
            "Patata Mina",
            "Pugile",
            "Sparapiselli Triplo"
    };

	// ---------------------------------------------------------------
	// INSTANCE ATTRIBUTES
	// ---------------------------------------------------------------
    
    private Image bgImage;
    private JPanel mainAllPlantPanel;
    private JPanel mainPlantPanel;    
    private JPanel plantPanel;
    private JPanel buttonPanel;
    private int[] selectedPlants = new int[6];
    private int count = 0; 

    public SelectPlantPanel() {
        this.setLayout(new BorderLayout());
        this.bgImage=SpriteManager.getCurrentBackGroundImage();

        // PANNELLO PIANTE
        JLabel plantText = UiUtils.styleLabel("Piante selezionate", 25);
        plantText.setHorizontalAlignment(SwingConstants.CENTER);
        this.mainPlantPanel = new JPanel(new BorderLayout(10, 10));
        this.mainPlantPanel.setBackground(UiUtils.WOOD_DARK);
        this.mainPlantPanel.setBorder(new LineBorder(UiUtils.WOOD_BORDER, 3, true));
        this.mainPlantPanel.add(plantText, BorderLayout.NORTH);
        this.plantPanel = new JPanel(new GridLayout(1, 6, 10, 10));
        this.plantPanel.setBackground(UiUtils.WOOD_DARK);
        updatePlantPanel();

        // PANNELLO MAPPA
        JPanel mapPanel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (bgImage != null) {
                    g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
                }
            }

            @Override
            public void doLayout() {
                int w = getWidth();
                int h = getHeight();

                if (w > 0 && h > 0) {
                    if (mainPlantPanel != null) {
                        mainPlantPanel.setPreferredSize(new Dimension((int) (w * 0.53), (int) (h * 0.25)));
                    }
                    if (mainAllPlantPanel != null) {
                        mainAllPlantPanel.setPreferredSize(new Dimension((int) (w * 0.53), (int) (h * 0.59)));
                    }
                    if (buttonPanel != null) {
                        buttonPanel.setPreferredSize(new Dimension((int) (w * 0.11), (int) (h * 0.17)));
                    }
                }
                super.doLayout();
            }
        };
        mapPanel.setPreferredSize(new Dimension(1500, 948));

        // PANNELLO TUTTE PIANTE
        JLabel allPlantLabel = UiUtils.styleLabel("Scegli piante", 25);
        allPlantLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.mainAllPlantPanel = new JPanel(new BorderLayout(10, 10));
        this.mainAllPlantPanel.setBackground(UiUtils.WOOD_DARK);
        this.mainAllPlantPanel.setBorder(new LineBorder(UiUtils.WOOD_BORDER, 3, true));
        this.mainAllPlantPanel.add(allPlantLabel, BorderLayout.NORTH);
        JPanel allPlantPanel = new JPanel(new GridLayout(3, 4, 10, 10));
        allPlantPanel.setBackground(UiUtils.WOOD_DARK);

        for (int i = 0; i < 12; i++) {
            Image icon = SpriteManager.getIconImage(i + 6);
            JButton plant = UiUtils.plantButton(PLANT_NAMES[i], icon);
            final int plantId = i + 1;
            plant.addActionListener(e -> {
                if (this.count < 6) {
                    int control = 0;
                    this.selectedPlants[this.count] = plantId;
                    for (int j = 0; j < this.selectedPlants.length; j++) {
                        if (this.selectedPlants[j] == plantId) {
                            control++;
                        }

                    }
                    if (control < 2) {
                        this.count++;
                        updatePlantPanel();
                    } else {
                        this.selectedPlants[this.count] = 0;
                        ControllerForView.getInstance().showError("Seleziona una pianta diversa!");
                    }

                } else {
                    ControllerForView.getInstance().showError("Hai gia selezionato 6 piante");
                }
            });

            allPlantPanel.add(plant);
        }

        this.mainAllPlantPanel.add(allPlantPanel, BorderLayout.CENTER);

        // BUTTON PANEL
        this.buttonPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        this.buttonPanel.setBackground(UiUtils.WOOD_DARK);
        this.buttonPanel.setBorder(new LineBorder(UiUtils.WOOD_BORDER, 3, true));
        JButton menuButton = UiUtils.styleButton("Menu", UiUtils.WOOD_LIGHT, UiUtils.TEXT_GOLD, UiUtils.WOOD_BORDER, 20);
        menuButton.addActionListener(e -> {
            ControllerForView.getInstance().showPanel(MENU_IDENTIFIER);
        });
        JButton playButton = UiUtils.styleButton("Gioca", UiUtils.WOOD_LIGHT, UiUtils.TEXT_GOLD, UiUtils.WOOD_BORDER, 20);
        playButton.addActionListener(e -> {
            if (this.count == 6) {
                ControllerForView.getInstance().setSelectedPlant(this.selectedPlants);
                ControllerForView.getInstance().startTimer();
                ControllerForView.getInstance().showPanel(GAME_PANEL_IDENTIFIER);
            } else {
                ControllerForView.getInstance().showError("Seleziona almeno 6 piante!");
            }
        });

        this.buttonPanel.add(menuButton);
        this.buttonPanel.add(playButton);

        // LAYOUT

        GridBagConstraints gbcdx = new GridBagConstraints();
        gbcdx.gridx = 0;
        gbcdx.gridy = 0;
        gbcdx.weighty = 1.0;
        gbcdx.anchor = GridBagConstraints.SOUTH;
        gbcdx.insets = new Insets(0, 0, 25, 0);

        GridBagConstraints gbcsx = new GridBagConstraints();
        gbcsx.gridx = 0;
        gbcsx.gridy = 0;
        gbcsx.weighty = 0.0;
        gbcsx.anchor = GridBagConstraints.NORTH;
        gbcsx.insets = new Insets(25, 0, 0, 0);

        GridBagConstraints gbcdw = new GridBagConstraints();
        gbcdw.gridx = 0;
        gbcdw.gridy = 0;
        gbcdw.weightx = 0.9;
        gbcdw.weighty = 0.9;
        gbcdw.anchor = GridBagConstraints.SOUTHEAST;
        gbcdw.insets = new Insets(25, 25, 25, 25);

        mapPanel.add(this.mainPlantPanel, gbcsx);
        mapPanel.add(mainAllPlantPanel, gbcdx);
        mapPanel.add(this.buttonPanel, gbcdw);
        this.add(mapPanel, BorderLayout.CENTER);
    }

	// ---------------------------------------------------------------
	// INSTANCE METHODS
	// ---------------------------------------------------------------

    private void updatePlantPanel() {
        this.plantPanel.removeAll();

        for (int i = 0; i < 6; i++) {
            if (i < this.count) {
                int plantIndex = this.selectedPlants[i] - 1; 
                Image icon = SpriteManager.getIconImage(plantIndex + 6);
                JButton plant = UiUtils.plantButton(PLANT_NAMES[plantIndex], icon);

                final int removeIndex = i;
                plant.addActionListener(e -> {
                    removeSelectedPlant(removeIndex);
                });

                this.plantPanel.add(plant);
            } else {
                JButton empty = UiUtils.styleButton("Slot " + (i + 1), UiUtils.WOOD_DARK, UiUtils.TEXT_GOLD,
                        UiUtils.WOOD_BORDER, 12);
                empty.setSize(new Dimension(120, 150));
                empty.setEnabled(false);
                this.plantPanel.add(empty);
            }
        }
        this.plantPanel.revalidate();
        this.plantPanel.repaint();
        this.mainPlantPanel.add(this.plantPanel, BorderLayout.CENTER);
    }

    private void removeSelectedPlant(int ind) {
        for (int i = ind; i < this.count - 1; i++) {
            this.selectedPlants[i] = this.selectedPlants[i + 1];
        }
        this.selectedPlants[this.count - 1] = 0;
        this.count--;
        updatePlantPanel();
    }

    public void updateBackGround(){
        this.bgImage=SpriteManager.getCurrentBackGroundImage();
    }

}
