package jpvz.view;

import java.awt.Dimension;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;

import jpvz.controller.ControllerForView;
import jpvz.utils.UiUtils;

public class SettingPanel extends JPanel {

	// ---------------------------------------------------------------
	// STATIC CONSTANTS
	// ---------------------------------------------------------------

	private static final String MENU_IDENTIFIER = "MENU";
	private static final int PANEL_HEIGTH=632;
	private static final int PANEL_WIDTH=1000;

	// ---------------------------------------------------------------
	// INSTANCE ATTRIBUTES
	// ---------------------------------------------------------------

	private JSlider musicSlider;
	private JComboBox<String> mapsBox;
	private JComboBox<String> difficultyBox;
	private JTextField nameField;

	public SettingPanel() {

		// SFONDO PRINCIPALE
		this.setBackground(UiUtils.BG_DARK_GREEN);
		this.setLayout(new GridBagLayout());

		// SETTING LABEL
		JLabel settingLabel = UiUtils.styleLabel("Impostazioni", 30);

		// NAMEFIELD
		this.nameField = UiUtils.styleChangeNameTextField("Inserisci nome e premi INVIO");
		this.nameField.setText(ControllerForView.getInstance().getPlayerName());
		JLabel nameLabel = UiUtils.styleLabel("Nome Giocatore: ", 25);

		// MUSICSLIDER
		this.musicSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, ControllerForView.getInstance().getMusicVolume());
		this.musicSlider.setOpaque(false);
		this.musicSlider.setMajorTickSpacing(20);
		this.musicSlider.setBackground(UiUtils.WOOD_LIGHT);
		this.musicSlider.setBorder(BorderFactory.createLineBorder(UiUtils.WOOD_BORDER, 3, true));
		JLabel musicLabel = UiUtils.styleLabel("Volume Musica: ", 25);

		// MAPPE
		String[] maps = { "prato giorno", "prato notte", "antico egitto", "far west", "futuro" };
		this.mapsBox = UiUtils.styleBox(maps);
		this.mapsBox.setSelectedItem(ControllerForView.getInstance().getMap());
		JLabel mapLabel = UiUtils.styleLabel("Seleziona Mappa: ", 25);

		// DIFFICOLTA'
		String[] diff = { "facile", "normale", "difficile", "impossibile" };
		this.difficultyBox = UiUtils.styleBox(diff);
		this.difficultyBox.setSelectedItem(ControllerForView.getInstance().getDifficulty());
		JLabel diffLabel = UiUtils.styleLabel("Seleziona Difficoltà: ", 25);

		// TORNA INDIETRO BOTTONE
		JButton backButton = UiUtils.styleButton("Salva e torna indietro", UiUtils.WOOD_LIGHT,
				UiUtils.TEXT_GOLD,
				UiUtils.WOOD_BORDER, 25);
		backButton.addActionListener(e -> {

			String selectedName = this.nameField.getText();
			int musicVolume = this.musicSlider.getValue();
			String selectedDiff = (String) this.difficultyBox.getSelectedItem();
			String selectedMap = (String) this.mapsBox.getSelectedItem();

			ControllerForView.getInstance().saveSetting(selectedName, musicVolume, selectedMap, selectedDiff);

			ControllerForView.getInstance().showPanel(MENU_IDENTIFIER);
		});

		// PANNELLO SOPRA ELEVATO
		JPanel centralPanel = new JPanel();
		centralPanel.setBackground(UiUtils.WOOD_DARK);
		centralPanel.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGTH));

		// LAYOUT
		GroupLayout layout = new GroupLayout(centralPanel);
		centralPanel.setLayout(layout);
		centralPanel.setBorder(BorderFactory.createLineBorder(UiUtils.WOOD_BORDER, 3, true));

		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		int proportionalComponentH = (int) (PANEL_HEIGTH * 0.1);
		int proportionalComponentW = (int) (PANEL_WIDTH * 0.7);

		layout.setHorizontalGroup(
				layout.createSequentialGroup()
						.addGap(50)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
								.addComponent(settingLabel)
								.addGroup(layout.createSequentialGroup()
										.addGroup(layout.createParallelGroup(
												GroupLayout.Alignment.TRAILING)
												.addComponent(nameLabel)
												.addComponent(musicLabel)
												.addComponent(mapLabel)
												.addComponent(diffLabel))
										.addGroup(layout.createParallelGroup(
												GroupLayout.Alignment.LEADING)
												.addComponent(this.nameField,
														GroupLayout.DEFAULT_SIZE,
														proportionalComponentW,
														Short.MAX_VALUE)
												.addComponent(this.musicSlider,
														GroupLayout.DEFAULT_SIZE,
														proportionalComponentW,
														Short.MAX_VALUE)
												.addComponent(this.mapsBox,
														GroupLayout.DEFAULT_SIZE,
														proportionalComponentW,
														Short.MAX_VALUE)
												.addComponent(this.difficultyBox,
														GroupLayout.DEFAULT_SIZE,
														proportionalComponentW,
														Short.MAX_VALUE))

										.addGap(50))
								.addComponent(backButton, GroupLayout.PREFERRED_SIZE,
										proportionalComponentW,
										GroupLayout.PREFERRED_SIZE)
								.addGap(50)

						)

		);

		layout.setVerticalGroup(
				layout.createSequentialGroup()
						.addGap(30)
						.addComponent(settingLabel)
						.addGap(20)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(nameLabel)
								.addComponent(this.nameField, GroupLayout.DEFAULT_SIZE,
										proportionalComponentH,
										GroupLayout.PREFERRED_SIZE))
						.addGap(20)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
								.addComponent(musicLabel)
								.addComponent(this.musicSlider,
										GroupLayout.DEFAULT_SIZE,
										proportionalComponentH,
										GroupLayout.PREFERRED_SIZE))
						.addGap(20)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(mapLabel)
								.addComponent(this.mapsBox, GroupLayout.DEFAULT_SIZE,
										proportionalComponentH,
										GroupLayout.PREFERRED_SIZE))
						.addGap(20)
						.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
								.addComponent(diffLabel)
								.addComponent(this.difficultyBox,
										GroupLayout.DEFAULT_SIZE,
										proportionalComponentH,
										GroupLayout.PREFERRED_SIZE))
						.addGap(50)
						.addComponent(backButton, GroupLayout.DEFAULT_SIZE,
								proportionalComponentH,
								GroupLayout.PREFERRED_SIZE)
						.addGap(30));

		this.add(centralPanel);

	}

	// ---------------------------------------------------------------
	// INSTANCE METHODS
	// ---------------------------------------------------------------

	public void updateSettings() {
		this.nameField.setText(ControllerForView.getInstance().getPlayerName());
		this.musicSlider.setValue(ControllerForView.getInstance().getMusicVolume());
		this.mapsBox.setSelectedItem(ControllerForView.getInstance().getMap());
		this.difficultyBox.setSelectedItem(ControllerForView.getInstance().getDifficulty());
	}

}
