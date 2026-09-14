package jpvz.view;

import java.awt.CardLayout;

import javax.swing.Timer;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import jpvz.controller.ControllerForView;
import jpvz.utils.UiUtils;

public class MainGUI extends JFrame {

    // ---------------------------------------------------------------
    // STATIC CONSTANTS
    // ---------------------------------------------------------------

    private static final String SCORE_IDENTIFIER = "SCORE";
    private static final String MENU_IDENTIFIER = "MENU";
    private static final String SETTING_IDENTIFIER = "SETTING";
    private static final String GAME_PANEL_IDENTIFIER = "GAME";
    private static final String SELECT_PLANT_PANEL_IDENTIFIER = "SELECT";
    private static final int ERROR_TIMER = 5000;

    // ---------------------------------------------------------------
    // INSTANCE ATTRIBUTES
    // ---------------------------------------------------------------

    private CardLayout panelLayout;
    private JPanel panelContainer;
    private JLabel errorLabel;
    private MenuPanel menuPanel;
    private SettingPanel settingPanel;
    private ScorePanel scorePanel;
    private GamePanel gamePanel;
    private SelectPlantPanel selectPlantPanel;
    private EndGamePanel endGamePanel;

    private Timer errorTimer;
    private Timer sunTimer;
    private Timer zombieTimer;
    private Timer gameLoop;

    public MainGUI() {

        super("JPvZ");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(1500, 948);

        this.errorLabel = UiUtils.errorLabel("");
        this.errorLabel.setVisible(false);
        this.getLayeredPane().add(this.errorLabel, JLayeredPane.POPUP_LAYER);

        // CARDLAYOUT
        this.panelLayout = new CardLayout();
        this.panelContainer = new JPanel(this.panelLayout);

        this.menuPanel = new MenuPanel();
        this.settingPanel = new SettingPanel();
        this.scorePanel = new ScorePanel();
        this.gamePanel = new GamePanel();
        this.selectPlantPanel = new SelectPlantPanel();
        this.endGamePanel = new EndGamePanel();
        this.setGlassPane(this.endGamePanel);

        this.panelContainer.add(this.menuPanel, MENU_IDENTIFIER);
        this.panelContainer.add(this.scorePanel, SCORE_IDENTIFIER);
        this.panelContainer.add(this.settingPanel, SETTING_IDENTIFIER);
        this.panelContainer.add(this.gamePanel, GAME_PANEL_IDENTIFIER);
        this.panelContainer.add(this.selectPlantPanel, SELECT_PLANT_PANEL_IDENTIFIER);

        this.add(panelContainer);
        this.pack();
        this.setVisible(true);
    }

    // ---------------------------------------------------------------
    // INSTANCE METHODS
    // ---------------------------------------------------------------

    public void showPanel(String panelId) {
        if (GAME_PANEL_IDENTIFIER.equals(panelId)) {
            this.gamePanel.updatePlantsBar();
            this.gamePanel.updateNumSun();
            this.gamePanel.updateBackGround();
        } else if (SCORE_IDENTIFIER.equals(panelId)) {
            this.scorePanel.updateScoreList();
        } else if (MENU_IDENTIFIER.equals(panelId)) {
            this.menuPanel.updateName();
        } else if (SETTING_IDENTIFIER.equals(panelId)) {
            this.settingPanel.updateSettings();
        } else if (SELECT_PLANT_PANEL_IDENTIFIER.equals(panelId))
            this.selectPlantPanel.updateBackGround();
        this.panelLayout.show(this.panelContainer, panelId);
    }

    public void repaintGame() {
        this.gamePanel.updateNumSun();
        this.gamePanel.updateLabel();
        this.gamePanel.repaint();
        if (ControllerForView.getInstance().isGameOver() || ControllerForView.getInstance().isVictory()) {
            this.endGamePanel.winLose();
            this.endGamePanel.setVisible(true);
        }
    }

    public void showError(String error) {
        this.errorLabel.setText(error);
        int w = 400;
        int h = 70;
        int x = (this.getWidth() - w) / 2;
        int y = (this.getHeight() - h - 50);
        this.errorLabel.setBounds(x, y, w, h);
        this.errorLabel.setVisible(true);
        if (this.errorTimer != null && this.errorTimer.isRunning()) {
            this.errorTimer.stop();
        } else {
            this.errorTimer = new Timer(ERROR_TIMER, e -> {
                this.errorLabel.setVisible(false);
            });
        }

        this.errorTimer.setRepeats(false);
        this.errorTimer.start();
    }

    public void startTimer() {
        if (gameLoop == null) {
            gameLoop = new Timer(ControllerForView.getInstance().getTickDelay(), e -> {
                ControllerForView.getInstance().updateGame();
                this.gamePanel.tick();
                View.getInstance().repaintGame();
            });
        }
        gameLoop.start();

        if (sunTimer == null) {
            sunTimer = new Timer(ControllerForView.getInstance().getSunDelay(), e -> {
                ControllerForView.getInstance().spawnSunTick();
                View.getInstance().repaintGame();
            });
        }
        sunTimer.start();

        if (zombieTimer == null) {
            zombieTimer = new Timer(ControllerForView.getInstance().getZombieSpawnDelay(), e -> {
                ControllerForView.getInstance().spawnZombieTick();
                if (ControllerForView.getInstance().isInWave())
                    zombieTimer.setDelay(ControllerForView.getInstance().getZombieSpawnWaveDelay());
                else {
                    zombieTimer.setDelay(ControllerForView.getInstance().getZombieSpawnDelay());

                }
                View.getInstance().repaintGame();
            });
        }
        zombieTimer.start();
    }

    public void stopTimer() {
        if (gameLoop != null && gameLoop.isRunning())
            gameLoop.stop();
        if (sunTimer != null && sunTimer.isRunning())
            sunTimer.stop();
        if (zombieTimer != null && zombieTimer.isRunning())
            zombieTimer.stop();
    }

    public void pauseTimer() {
        ControllerForView.getInstance().pauseGame();
        boolean isPaused = ControllerForView.getInstance().isPaused();

        if (isPaused) {
            if (gameLoop != null)
                gameLoop.stop();
            if (sunTimer != null)
                sunTimer.stop();
            if (zombieTimer != null)
                zombieTimer.stop();
        } else {
            if (gameLoop != null)
                gameLoop.start();
            if (sunTimer != null)
                sunTimer.start();
            if (zombieTimer != null)
                zombieTimer.start();
        }
    }
}
