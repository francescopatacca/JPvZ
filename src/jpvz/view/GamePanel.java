package jpvz.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import jpvz.controller.ControllerForView;

import jpvz.utils.Animation;
import jpvz.utils.SpriteManager;
import jpvz.utils.UiUtils;

public class GamePanel extends JPanel {

    // ---------------------------------------------------------------
    // STATIC CONSTANTS
    // ---------------------------------------------------------------

    private static final String MENU_IDENTIFIER = "MENU";
    private static final int SHOVEL_INDEX = 136712;
    private static final double LEFT_OFFSET = 0.175;
    private static final double RIGHT_OFFSET = 0.285;
    private static final double TOP_OFFSET = 0.11;
    private static final double BOTTOM_OFFSET = 0.05;
    private static final double NUM_COLUMNS = 9.0;
    private static final double NUM_ROWS = 5.0;
    private static final double DIM_CELL = 80.0;
    private static final double DIM_PLANTS = 0.8;
    private static final double DIM_CACTUS = 1.2;
    private static final double DIM_COCCO_CANNON = 1.1;
    private static final double DIM_ZOMBIE = 0.9;
    private static final double DIM_LAWNMOWER = 0.75;
    private static final double DIM_PROJECTILE = 0.2;
    private static final double DIM_SUN = 0.7;
    private static final int PROJECTILE_OFFSET = 20;
    private static final double BAR_HEIGHT = 0.2;
    private static final double BORDER_WIDTH = 0.07;
    private static final double BORDER_HEIGHT = 0.03;
    private static final int MAP_WIDTH = 1000;
    private static final int MAP_HEIGHT = 432;
    private static final int CACTUS_ID = 1;
    private static final int COCCO_ID = 4;
    private static final int POTATO_MINE_ID = 10;
    private static final int NUM_PLANTS = 12;
    private static final int CACTUS_X_FIX = 3;
    private static final int COCCO_X_FIX = 5;
    private static final int COCCO_X_OFFSET = 15;
    private static final int COCCO_Y_FIX = 3;
    private static final int FONT_SIZE = 20;
    private static final int ICON_SUN = 3;
    private static final int ICON_LAWNMOWER = 4;
    private static final int ICON_SHOVEL = 2;
    private static final int ICON_PLANT_OFFSET = 6;
    private static final String KEY_ATTACK = "ATTACCO";
    private static final String KEY_DEATH = "MUORE";
    private static final String KEY_EXPLODE = "ESPLOSIONE";
    private static final String KEY_PLANTED = "PIANTATA";
    private static final String KEY_GROWN = "CRESCIUTA";

    // ---------------------------------------------------------------
    // INSTANCE ATTRIBUTES
    // ---------------------------------------------------------------

    private JPanel plantSelectionPanel;
    private JPanel infoGamePanel;
    private JButton pauseButton;
    private JButton sunButton;
    private JLabel score;
    private JLabel playerName;
    private JLabel waveLabel;
    private Image bgImage;
    private Image sunImage;

    private int plantIndex;
    private boolean pause = false;
    private long tickCount;
    private long tick;

    private double mouseHoverX=-1;
    private double mouseHoverY=-1;

    public GamePanel() {
        this.setLayout(new BorderLayout());

        // PANNELLO PRINCIPALE
        JPanel mainPanel = new JPanel(new BorderLayout()) {
            @Override
            public void doLayout() {
                int w = getWidth();
                int h = getHeight();

                if (w > 0 && h > 0) {
                    int barH = (int) (h * BAR_HEIGHT);
                    int borderW = (int) (w * BORDER_WIDTH);
                    int borderH = (int) (h * BORDER_HEIGHT);
                    if (plantSelectionPanel != null) {
                        plantSelectionPanel.setPreferredSize(new Dimension(w, barH));
                        plantSelectionPanel
                                .setBorder(BorderFactory.createEmptyBorder(borderH, borderW, borderH, borderW));
                    }
                    if (infoGamePanel != null) {
                        infoGamePanel.setPreferredSize(new Dimension(w, barH));
                    }
                }
                super.doLayout();
            }
        };

        // PANNELLO SELEZIONE PIANTE
        this.plantSelectionPanel = new JPanel(new GridLayout(1, 8, 10, 10));
        this.plantSelectionPanel.setBackground(UiUtils.WOOD_DARK);
        updatePlantsBar();

        // PANNELLO INFO GAME E MENU
        this.infoGamePanel = new JPanel(new GridLayout(2, 4, 10, 10));
        this.infoGamePanel.setBackground(UiUtils.WOOD_DARK);

        JLabel scoreText = UiUtils.styleLabel("Punteggio");
        scoreText.setHorizontalAlignment(SwingConstants.CENTER);
        this.infoGamePanel.add(scoreText);

        JLabel playerNameText = UiUtils.styleLabel("Nome del giocatore");
        playerNameText.setHorizontalAlignment(SwingConstants.CENTER);
        this.infoGamePanel.add(playerNameText);

        JLabel waveLabelText = UiUtils.styleLabel("Numero orda");
        waveLabelText.setHorizontalAlignment(SwingConstants.CENTER);
        this.infoGamePanel.add(waveLabelText);

        JButton menuButton = UiUtils.styleButton("Menù", UiUtils.WOOD_LIGHT, UiUtils.TEXT_GOLD, UiUtils.WOOD_BORDER,
                FONT_SIZE);
        menuButton.addActionListener(e -> {
            ControllerForView.getInstance().stopTimer();
            ControllerForView.getInstance().resetGame();
            ControllerForView.getInstance().stopAll();
            ControllerForView.getInstance().showPanel(MENU_IDENTIFIER);
        });
        this.infoGamePanel.add(menuButton);

        this.score = UiUtils.styleLabel(String.valueOf(ControllerForView.getInstance().getScore()));
        this.score.setHorizontalAlignment(SwingConstants.CENTER);
        this.infoGamePanel.add(this.score);

        this.playerName = UiUtils.styleLabel(ControllerForView.getInstance().getPlayerName());
        this.playerName.setHorizontalAlignment(SwingConstants.CENTER);
        this.infoGamePanel.add(this.playerName);

        this.waveLabel = UiUtils.styleLabel(String.valueOf(ControllerForView.getInstance().getWave()));
        this.waveLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.infoGamePanel.add(this.waveLabel);

        this.pauseButton = UiUtils.styleButton(pauseText(), UiUtils.WOOD_LIGHT, UiUtils.TEXT_GOLD, UiUtils.WOOD_BORDER,
                FONT_SIZE);
        this.pauseButton.addActionListener(e -> {
            this.pause = !this.pause;
            ControllerForView.getInstance().pauseTimer();
        });
        this.infoGamePanel.add(this.pauseButton);

        // PANNELLO MAPPA
        this.sunImage = SpriteManager.getIconImage(ICON_SUN);
        this.bgImage = SpriteManager.getCurrentBackGroundImage();
        JPanel mapPanel = new JPanel() {

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (bgImage != null) {
                    g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this);
                }

                double w = getWidth();
                double h = getHeight();
                double startX = w * LEFT_OFFSET;
                double startY = h * TOP_OFFSET;
                double endX = w * (1.0 - RIGHT_OFFSET);
                double endY = h * (1.0 - BOTTOM_OFFSET);
                double cellW = (endX - startX) / NUM_COLUMNS;
                double cellH = (endY - startY) / NUM_ROWS;

                if (ControllerForView.getInstance().isPlayGridNonNull()) {
                    drawPlants(g, startX, startY, cellW, cellH);
                    drawZombies(g, startX, startY, cellW, cellH);
                    drawProjectiles(g, startX, startY, cellW, cellH);
                    drawLawnMowers(g, startX, startY, cellW, cellH);
                    drawSuns(g, startX, startY, cellW, cellH);
                }

                if(mouseHoverX>=startX && mouseHoverX< endX && mouseHoverY>=startY && mouseHoverY<endY){
                    int c= (int) ((mouseHoverX-startX)/cellW);
                    int r=(int) ((mouseHoverY-startY)/cellH);
                    if(ControllerForView.getInstance().isPlantNonNull(r, c) && plantIndex!=SHOVEL_INDEX && plantIndex !=0){
                        g.setColor(UiUtils.GRID_ERROR_COLOR);
                        g.fillRect((int) (startX+ c*cellW), (int)(startY+r*cellH), (int) cellW, (int) cellH);
                    }
                }
            }
        };
        mapPanel.setPreferredSize(new Dimension(MAP_WIDTH, MAP_HEIGHT));

        // MOUSEADAPTER
        MouseAdapter mouseAdapter =new MouseAdapter() {
            @Override 
            public void mousePressed(MouseEvent e) {
                double w = mapPanel.getWidth();
                double h = mapPanel.getHeight();
                double startX = w * LEFT_OFFSET;
                double startY = h * TOP_OFFSET;
                double endX = w * (1.0 - RIGHT_OFFSET);
                double endY = h * (1.0 - BOTTOM_OFFSET);
                double dimX = endX - startX;
                double dimY = endY - startY;
                double cellW = dimX / NUM_COLUMNS;
                double cellH = dimY / NUM_ROWS;                
                double mouseX = e.getX();
                double mouseY = e.getY();

                if (mouseListenerSun(mouseX, mouseY, startX, startY, cellW, cellH))
                    return;
                mouseListenerGrid(mouseX, mouseY, startX, startY, endX, endY, cellW, cellH);

            }

            @Override 
            public void mouseMoved(MouseEvent e){
                mouseHoverX = e.getX();
                mouseHoverY = e.getY();
            }
        };

        mapPanel.addMouseListener(mouseAdapter);
        mapPanel.addMouseMotionListener(mouseAdapter);


        mainPanel.add(this.plantSelectionPanel, BorderLayout.NORTH);
        mainPanel.add(mapPanel, BorderLayout.CENTER);
        mainPanel.add(this.infoGamePanel, BorderLayout.SOUTH);

        this.add(mainPanel, BorderLayout.CENTER);

    }

    // ---------------------------------------------------------------
    // PRIVATE INSTANCE METHODS
    // ---------------------------------------------------------------
    private void drawPlants(Graphics g, double startX, double startY, double cellW, double cellH) {
        for (int r = 0; r < (int) NUM_ROWS; r++) {
            for (int c = 0; c < (int) NUM_COLUMNS; c++) {
                if (ControllerForView.getInstance().isPlantNonNull(r, c)) {
                    int id = ControllerForView.getInstance().getPlantId(r, c);

                    if (id >= 1 && id <= NUM_PLANTS) {
                        String state = ControllerForView.getInstance().getPlantState(r, c);
                        Animation anim = SpriteManager.getPlantAnimation(id, state);
                        if (anim != null) {
                            if (state.equals(KEY_ATTACK)) {
                                tick = ControllerForView.getInstance().getPlantAttackTick(r, c);
                            } else if (state.equals(KEY_PLANTED)) {
                                tick = ControllerForView.getInstance().getPlantPlantedTick(r, c);
                            } else if (state.equals(KEY_GROWN)
                                    && ControllerForView.getInstance().getPlantId(r, c) == POTATO_MINE_ID) {
                                tick = ControllerForView.getInstance().getPlantGrowTick(r, c);
                            } else if (state.equals(KEY_EXPLODE)
                                    && ControllerForView.getInstance().getPlantId(r, c) == POTATO_MINE_ID) {
                                tick = ControllerForView.getInstance().getPlantExplosionTick(r, c);
                            } else {
                                tick = tickCount;
                            }
                            Image plantImg = anim.getFrame(tick);
                            if (plantImg != null) {
                                int plantW = (int) (cellW * DIM_PLANTS);
                                int plantH = (int) (cellH * DIM_PLANTS);
                                int posX = (int) (startX + c * cellW + (cellW - plantW) / 2);
                                int posY = (int) (startY + r * cellH + (cellH - plantH) / 2);
                                if (id == CACTUS_ID) {
                                    int plantW1 = (int) (cellW * DIM_CACTUS);
                                    int plantH1 = (int) (cellH * DIM_CACTUS);
                                    int posX1 = (int) (startX + c * (cellW - CACTUS_X_FIX) + (cellW - plantW) / 2);
                                    int posY1 = (int) (startY + r * cellH + (cellH - plantH) / 2);
                                    g.drawImage(plantImg, posX1, posY1, plantW1, plantH1, this);
                                } else if (id == COCCO_ID) {
                                    int plantW1 = (int) (cellW * DIM_COCCO_CANNON);
                                    int plantH1 = (int) (cellH * DIM_COCCO_CANNON);
                                    int posX1 = (int) (startX + c * (cellW - COCCO_X_FIX) + (cellW - plantW) / 2
                                            - COCCO_X_OFFSET);
                                    int posY1 = (int) (startY + r * (cellH - COCCO_Y_FIX) + (cellH - plantH) / 2);
                                    g.drawImage(plantImg, posX1, posY1, plantW1, plantH1, this);
                                } else {
                                    g.drawImage(plantImg, posX, posY, plantW, plantH, this);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void drawZombies(Graphics g, double startX, double startY, double cellW, double cellH) {
        if (ControllerForView.getInstance().getNumActiveZombie() > 0) {
            for (int i = 0; i < ControllerForView.getInstance().getNumActiveZombie(); i++) {
                int id = ControllerForView.getInstance().getZombieId(i);
                String state = ControllerForView.getInstance().getZombieState(i);
                Animation anim = SpriteManager.getZombieAnimation(id, state);
                if (anim != null) {
                    if (state.equals(KEY_DEATH))
                        tick = ControllerForView.getInstance().getZombieDeathTick(i);
                    else
                        tick = tickCount;
                    Image zombieImg = anim.getFrame(tick);
                    int zombieW = (int) (cellW * DIM_ZOMBIE);
                    int zombieH = (int) (cellH * DIM_ZOMBIE);
                    int zombieX = (int) (startX
                            + (ControllerForView.getInstance().getZombieXPos(i) / DIM_CELL) * cellW);
                    int zombieY = (int) (startY + ControllerForView.getInstance().getZombieRow(i) * cellH
                            + (cellH - zombieH) / 2);
                    g.drawImage(zombieImg, zombieX, zombieY, zombieW, zombieH, this);
                    tick++;
                }
            }
        }
    }

    private void drawProjectiles(Graphics g, double startX, double startY, double cellW, double cellH) {
        if (ControllerForView.getInstance().getNumActiveProjectile() > 0) {
            for (int i = 0; i < ControllerForView.getInstance().getNumActiveProjectile(); i++) {
                int id = ControllerForView.getInstance().getProjectileId(i);
                if (id > 0 && id <= NUM_PLANTS) {
                    Image projImage = SpriteManager.getProjectileImage(id - 1);
                    if (projImage != null) {
                        int projW = (int) (cellW * DIM_PROJECTILE);
                        int projH = (int) (cellH * DIM_PROJECTILE);
                        int projX = (int) (startX
                                + (ControllerForView.getInstance().getProjectileXPos(i) / DIM_CELL) * cellW);
                        int projY = (int) (startY
                                + (ControllerForView.getInstance().getProjectileYPos(i) / DIM_CELL) * cellH
                                + (cellH - projH) / 2 + PROJECTILE_OFFSET);
                        g.drawImage(projImage, projX, projY, projW, projH, this);
                    }
                }
            }
        }
    }

    private void drawLawnMowers(Graphics g, double startX, double startY, double cellW, double cellH) {
        if (ControllerForView.getInstance().getNumLawnMowers() > 0) {
            Image lmImage = SpriteManager.getIconImage(ICON_LAWNMOWER);
            int lmW = (int) (cellW * DIM_LAWNMOWER);
            int lmH = (int) (cellH * DIM_LAWNMOWER);
            for (int i = 0; i < ControllerForView.getInstance().getNumLawnMowers(); i++) {
                if (!ControllerForView.getInstance().isLawnMowerUsed(i)) {
                    int lmX = (int) ((startX - lmW)
                            + (ControllerForView.getInstance().getLawnMowersXPos(i) / DIM_CELL) * cellW);
                    int lmY = (int) (startY + ControllerForView.getInstance().getLawnMowersRow(i) * cellH
                            + (cellH - lmH) / 2);
                    g.drawImage(lmImage, lmX, lmY, lmW, lmH, this);
                }
            }
        }
    }

    private void drawSuns(Graphics g, double startX, double startY, double cellW, double cellH) {
        if (sunImage != null && ControllerForView.getInstance().getNumActiveSun() != 0) {
            int sunW = (int) (cellW * DIM_SUN);
            int sunH = (int) (cellH * DIM_SUN);
            for (int i = 0; i < ControllerForView.getInstance().getNumActiveSun(); i++) {
                if (!ControllerForView.getInstance().isSunExpired(i)) {
                    int x = (int) (startX + (ControllerForView.getInstance().getSunXPos(i) / DIM_CELL) * cellW
                            + (cellW - sunW) / 2);
                    int y = (int) (startY + (ControllerForView.getInstance().getSunYPos(i) / DIM_CELL) * cellH
                            + (cellH - sunH) / 2);
                    g.drawImage(sunImage, x, y, sunW, sunH, this);
                }
            }
        }
    }

    private boolean mouseListenerSun(double mouseX, double mouseY, double startX, double startY, double cellW,
            double cellH) {
        if (ControllerForView.getInstance().getNumActiveSun() == 0)
            return false;

        int sunW = (int) (cellW * DIM_SUN);
        int sunH = (int) (cellH * DIM_SUN);

        for (int i = ControllerForView.getInstance().getNumActiveSun() - 1; i >= 0; i--) {
            if (!ControllerForView.getInstance().isSunExpired(i)) {
                int sunX = (int) (startX + (ControllerForView.getInstance().getSunXPos(i) / DIM_CELL) * cellW
                        + (cellW - sunW) / 2);
                int sunY = (int) (startY + (ControllerForView.getInstance().getSunYPos(i) / DIM_CELL) * cellH
                        + (cellH - sunH) / 2);

                if (mouseX >= sunX && mouseX <= sunX + sunW && mouseY >= sunY && mouseY < sunY + sunH) {
                    ControllerForView.getInstance().setSunIsCollected(i);
                    updateNumSun();
                    return true;
                }
            }
        }
        return false;
    }

    private void mouseListenerGrid(double mouseX, double mouseY, double startX, double startY, double endX, double endY,
            double cellW, double cellH) {
        if (mouseX < startX || mouseX > endX || mouseY < startY || mouseY > endY)
            return;

        int col = (int) ((mouseX - startX) / cellW);
        int row = (int) ((mouseY - startY) / cellH);

        if (row >= 0 && row < (int) NUM_ROWS && col >= 0 && col < (int) NUM_COLUMNS && plantIndex != SHOVEL_INDEX) {
            boolean success = ControllerForView.getInstance().placePlant(row, col, plantIndex);
            if (success) {
                plantIndex = 0;
                updateNumSun();
            } else if (plantIndex != 0) {
                if (ControllerForView.getInstance().isPlantNonNull(row, col)) {
                    ControllerForView.getInstance().showError("Casella gia occupata!");
                } else {
                    ControllerForView.getInstance().showError("Soli insufficienti!");
                }
            }

        } else if (plantIndex == SHOVEL_INDEX && row >= 0 && row < (int) NUM_ROWS && col >= 0
                && col < (int) NUM_COLUMNS) {
            ControllerForView.getInstance().removePlant(row, col);
            plantIndex = 0;
        }
    }

    // ---------------------------------------------------------------
    // INSTANCE METHODS
    // ---------------------------------------------------------------

    public String pauseText() {
        String s = "Pausa";
        if (this.pause) {
            s = "Riprendi";
        }
        return s;
    }

    public void updatePlantsBar() {
        this.plantSelectionPanel.removeAll();
        Image iconSun = SpriteManager.getIconImage(ICON_SUN);
        this.sunButton = UiUtils.plantButton(String.valueOf(ControllerForView.getInstance().getNumSun()), iconSun);
        this.plantSelectionPanel.add(this.sunButton);

        int[] bar = ControllerForView.getInstance().getPlantsBar();

        for (int i = 0; i < bar.length; i++) {
            if (bar[i] <= 0 || bar[i] > NUM_PLANTS) {
                continue;
            }
            Image icon = SpriteManager.getIconImage((bar[i] - 1) + ICON_PLANT_OFFSET);
            JButton plant = UiUtils.plantButton(
                    String.valueOf(ControllerForView.getInstance().getPlantCost(bar[i])) + "  Soli", icon);
            final int id = bar[i];
            plant.addActionListener(e -> {
                this.plantIndex = id;
            });

            this.plantSelectionPanel.add(plant);
        }

        Image shovelIcon = SpriteManager.getIconImage(ICON_SHOVEL);
        JButton shovelButton = UiUtils.plantButton("Pala", shovelIcon);
        shovelButton.addActionListener(e -> {
            this.plantIndex = SHOVEL_INDEX;
        });
        this.plantSelectionPanel.add(shovelButton);

        this.plantSelectionPanel.revalidate();
        this.plantSelectionPanel.repaint();
    }

    public void updateNumSun() {
        this.sunButton.setText(String.valueOf(ControllerForView.getInstance().getNumSun()));
        this.sunButton.revalidate();
    }

    public void updateLabel() {
        this.score.setText(String.valueOf(ControllerForView.getInstance().getScore()));
        this.waveLabel.setText(String.valueOf(ControllerForView.getInstance().getWave()));
        this.playerName.setText(ControllerForView.getInstance().getPlayerName());
        this.pauseButton.setText(pauseText());
    }

    public void updateBackGround() {
        this.bgImage = SpriteManager.getCurrentBackGroundImage();
    }

    public void tick() {
        tickCount++;
    }

}