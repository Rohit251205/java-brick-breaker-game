package game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BrickBreakerGame extends JPanel implements KeyListener, ActionListener {
    private boolean play = false;
    private int score = 0;
    private int totalBricks;

    private Timer timer;
    private int delay = 8;

    private int playerX;
    private int ballposX;
    private int ballposY;
    private int ballXdir = -1;
    private int ballYdir = -2;

    private int screenWidth;
    private int screenHeight;

    private final int bottomMargin = 100;
    private BrickGenerator map;

    public BrickBreakerGame(int width, int height) {
        this.screenWidth = width;
        this.screenHeight = height - bottomMargin;

        this.setPreferredSize(new Dimension(screenWidth, screenHeight + bottomMargin));

        ballposX = screenWidth / 2;
        ballposY = screenHeight - 120;
        playerX = screenWidth / 2 - 50;

        int rows = 4;
        int cols = width / 60;

        map = new BrickGenerator(rows, cols, width);
        totalBricks = rows * cols;

        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();
    }

    public void paint(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(0, 0, screenWidth, screenHeight + bottomMargin);

        map.draw((Graphics2D) g);

        g.setColor(Color.yellow);
        g.fillRect(0, 0, 3, screenHeight + bottomMargin);
        g.fillRect(0, 0, screenWidth, 3);
        g.fillRect(screenWidth - 3, 0, 3, screenHeight + bottomMargin);

        g.setColor(Color.white);
        g.setFont(new Font("serif", Font.BOLD, 25));
        g.drawString("Score: " + score, screenWidth - 150, 30);

        g.setColor(Color.green);
        g.fillRect(playerX, screenHeight - 50, 100, 10);

        g.setColor(Color.yellow);
        g.fillOval(ballposX, ballposY, 20, 20);

        if (ballposY > screenHeight) {
            play = false;
            ballXdir = 0;
            ballYdir = 0;
            g.setColor(Color.RED);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("Game Over, Score: " + score, screenWidth / 2 - 150, screenHeight / 2);

            g.setFont(new Font("serif", Font.BOLD, 20));
            g.drawString("Press Enter to Restart", screenWidth / 2 - 100, screenHeight / 2 + 40);
        }

        if (totalBricks == 0) {
            play = false;
            ballXdir = 0;
            ballYdir = 0;
            g.setColor(Color.GREEN);
            g.setFont(new Font("serif", Font.BOLD, 30));
            g.drawString("You Won! Score: " + score, screenWidth / 2 - 150, screenHeight / 2);

            g.setFont(new Font("serif", Font.BOLD, 20));
            g.drawString("Press Enter to Restart", screenWidth / 2 - 100, screenHeight / 2 + 40);
        }

        g.dispose();
    }

    public void actionPerformed(ActionEvent e) {
        timer.start();

        if (play) {
            if (new Rectangle(ballposX, ballposY, 20, 20)
                    .intersects(new Rectangle(playerX, screenHeight - 50, 100, 10))) {
                ballYdir = -ballYdir;
            }

            A:
            for (int i = 0; i < map.map.length; i++) {
                for (int j = 0; j < map.map[0].length; j++) {
                    if (map.map[i][j] > 0) {
                        int brickX = j * map.brickWidth + 60;
                        int brickY = i * map.brickHeight + 100;
                        int brickWidth = map.brickWidth;
                        int brickHeight = map.brickHeight;

                        Rectangle rect = new Rectangle(brickX, brickY, brickWidth, brickHeight);
                        Rectangle ballRect = new Rectangle(ballposX, ballposY, 20, 20);

                        if (ballRect.intersects(rect)) {
                            map.setBrickValue(0, i, j);
                            totalBricks--;
                            score += 5;

                            if (score % 30 == 0 && delay > 2) {
                                delay--;
                                timer.setDelay(delay);
                            }

                            if (ballposX + 19 <= rect.x || ballposX + 1 >= rect.x + rect.width) {
                                ballXdir = -ballXdir;
                            } else {
                                ballYdir = -ballYdir;
                            }

                            break A;
                        }
                    }
                }
            }

            ballposX += ballXdir;
            ballposY += ballYdir;

            if (ballposX < 0) ballXdir = -ballXdir;
            if (ballposY < 0) ballYdir = -ballYdir;
            if (ballposX > screenWidth - 20) ballXdir = -ballXdir;
        }

        repaint();
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (playerX >= screenWidth - 110) playerX = screenWidth - 110;
            else moveRight();
        }

        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (playerX <= 10) playerX = 10;
            else moveLeft();
        }

        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (!play) {
                play = true;
                ballposX = screenWidth / 2;
                ballposY = screenHeight - 120;
                ballXdir = -1;
                ballYdir = -2;
                playerX = screenWidth / 2 - 50;
                score = 0;

                timer.stop();
                delay = 8;
                timer = new Timer(delay, this);
                timer.start();

                int rows = 4;
                int cols = screenWidth / 60;
                map = new BrickGenerator(rows, cols, screenWidth);
                totalBricks = rows * cols;

                repaint();
            }
        }
    }

    public void moveRight() {
        play = true;
        playerX += 30;
    }

    public void moveLeft() {
        play = true;
        playerX -= 30;
    }

    public void keyReleased(KeyEvent e) {}
    public void keyTyped(KeyEvent e) {}

    public static void main(String[] args) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = screenSize.width;
        int height = screenSize.height;

        JFrame obj = new JFrame();
        BrickBreakerGame gamePlay = new BrickBreakerGame(width, height);
        obj.setTitle("Brick Breaker Game");
        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        obj.setExtendedState(JFrame.MAXIMIZED_BOTH);
        obj.setUndecorated(false);
        obj.add(gamePlay);
        obj.pack();
        obj.setVisible(true);
    }

    class BrickGenerator {
        public int[][] map;
        public int brickWidth;
        public int brickHeight;

        public BrickGenerator(int row, int col, int screenWidth) {
            map = new int[row][col];
            for (int[] rowArr : map) {
                for (int j = 0; j < map[0].length; j++) {
                    rowArr[j] = 1;
                }
            }

            brickWidth = (screenWidth - 120) / col;
            brickHeight = 40;
        }

        public void draw(Graphics2D g) {
            long time = System.currentTimeMillis() / 500; // changes every 0.5 sec

            for (int i = 0; i < map.length; i++) {
                for (int j = 0; j < map[0].length; j++) {
                    if (map[i][j] > 0) {
                        int hueIndex = (int)((i + j + time) % 6);
                        Color color;

                        switch (hueIndex) {
                            case 0: color = Color.CYAN; break;
                            case 1: color = Color.MAGENTA; break;
                            case 2: color = Color.ORANGE; break;
                            case 3: color = Color.PINK; break;
                            case 4: color = new Color(0, 255, 127); break; // spring green
                            default: color = Color.YELLOW; break;
                        }

                        g.setColor(color);
                        g.fillRect(j * brickWidth + 60, i * brickHeight + 100, brickWidth, brickHeight);

                        g.setStroke(new BasicStroke(2));
                        g.setColor(Color.black);
                        g.drawRect(j * brickWidth + 60, i * brickHeight + 100, brickWidth, brickHeight);
                    }
                }
            }
        }

        public void setBrickValue(int value, int row, int col) {
            map[row][col] = value;
        }
    }
}