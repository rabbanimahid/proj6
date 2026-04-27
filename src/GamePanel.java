import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class GamePanel extends JPanel implements KeyListener, ActionListener {
    private final int CELL_SIZE = 50;
    private final int ROWS = 10;
    private final int COLS = 10;

    private DuckSprite duck;
    private PondSprite pond;
    private ArrayList<SquirrelSprite> squirrels;
    private ArrayList<WallSprite> walls;
    private ArrayList<BreadcrumbSprite> breadcrumbs;
    private ArrayList<BushSprite> bushes;

    private enum State { MENU, PLAYING, WON, LOST }
    private State currentState = State.MENU;

    // --- LEVEL PROGRESSION VARIABLES ---
    private int currentLevel = 1;
    private final int MAX_LEVEL = 3;

    private Timer timer;
    private int animFrame = 0;

    public GamePanel() {
        setPreferredSize(new Dimension(COLS * CELL_SIZE, ROWS * CELL_SIZE));
        setBackground(new Color(34, 139, 34)); // Forest green
        addKeyListener(this);
        setFocusable(true);

        timer = new Timer(200, this);
        timer.start();

        loadLevel(1); // Load the level into memory but don't start playing yet
    }

    // --- THE LEVEL MANAGER ---
    private void loadLevel(int level) {
        duck = new DuckSprite(0, 0); // Duck always starts top-left
        squirrels = new ArrayList<>();
        walls = new ArrayList<>();
        breadcrumbs = new ArrayList<>();
        bushes = new ArrayList<>();

        if (level == 1) {
            // LEVEL 1: The Basics (Easy)
            pond = new PondSprite(9, 9);
            squirrels.add(new SquirrelSprite(3, 5));
            squirrels.add(new SquirrelSprite(7, 8));

            walls.add(new WallSprite(2, 2));
            walls.add(new WallSprite(2, 3));
            walls.add(new WallSprite(2, 4));

            breadcrumbs.add(new BreadcrumbSprite(5, 5));
            bushes.add(new BushSprite(0, 9));

        } else if (level == 2) {
            // LEVEL 2: The Maze (Medium) - More walls, forcing you into squirrel paths
            pond = new PondSprite(9, 0); // Pond moved to top right

            squirrels.add(new SquirrelSprite(2, 8));
            squirrels.add(new SquirrelSprite(4, 5));
            squirrels.add(new SquirrelSprite(6, 2));
            squirrels.add(new SquirrelSprite(8, 7)); // 4 squirrels now!

            // A solid wall blocking the direct path
            for(int i = 0; i < 7; i++) {
                walls.add(new WallSprite(5, i));
            }

            breadcrumbs.add(new BreadcrumbSprite(2, 9));
            breadcrumbs.add(new BreadcrumbSprite(7, 9));
            bushes.add(new BushSprite(0, 5));
            bushes.add(new BushSprite(9, 9));

        } else if (level == 3) {
            // LEVEL 3: The Gauntlet (Hard) - Tight quarters, lots of squirrels
            pond = new PondSprite(9, 9);

            // 6 Squirrels patrolling heavily!
            squirrels.add(new SquirrelSprite(1, 2));
            squirrels.add(new SquirrelSprite(3, 4));
            squirrels.add(new SquirrelSprite(5, 6));
            squirrels.add(new SquirrelSprite(6, 8));
            squirrels.add(new SquirrelSprite(7, 1));
            squirrels.add(new SquirrelSprite(8, 5));

            // Checkerboard walls
            walls.add(new WallSprite(2, 2)); walls.add(new WallSprite(4, 2));
            walls.add(new WallSprite(2, 4)); walls.add(new WallSprite(4, 4));
            walls.add(new WallSprite(2, 6)); walls.add(new WallSprite(4, 6));
            walls.add(new WallSprite(8, 8));
        }
    }

    public void startGame() {
        if (currentState == State.MENU) {
            currentLevel = 1;
            loadLevel(currentLevel);
            currentState = State.PLAYING;
        }
        repaint();
    }

    public void restartGame() {
        currentLevel = 1;
        loadLevel(currentLevel);
        currentState = State.PLAYING;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        if (currentState == State.MENU) {
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Arial", Font.BOLD, 24));
            g2d.drawString("Press START to play!", 120, 250);
            return;
        }

        // Draw all sprites
        pond.drawAnimated(g2d, pond.getX() * CELL_SIZE, pond.getY() * CELL_SIZE, CELL_SIZE, CELL_SIZE, animFrame);
        for (WallSprite w : walls) w.draw(g2d, w.getX() * CELL_SIZE, w.getY() * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        for (BushSprite b : bushes) b.draw(g2d, b.getX() * CELL_SIZE, b.getY() * CELL_SIZE, CELL_SIZE, CELL_SIZE);
        for (BreadcrumbSprite bc : breadcrumbs) bc.drawAnimated(g2d, bc.getX() * CELL_SIZE, bc.getY() * CELL_SIZE, CELL_SIZE, CELL_SIZE, animFrame);
        for (SquirrelSprite s : squirrels) s.drawAnimated(g2d, s.getX() * CELL_SIZE, s.getY() * CELL_SIZE, CELL_SIZE, CELL_SIZE, animFrame);
        duck.drawAnimated(g2d, duck.getX() * CELL_SIZE, duck.getY() * CELL_SIZE, CELL_SIZE, CELL_SIZE, animFrame);

        // --- UI Overlays ---
        if (currentState == State.PLAYING) {
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Arial", Font.BOLD, 16));
            g2d.drawString("Level: " + currentLevel + " / " + MAX_LEVEL, 10, 20);
        } else if (currentState == State.WON) {
            g2d.setColor(Color.YELLOW);
            g2d.setFont(new Font("Arial", Font.BOLD, 36));
            g2d.drawString("YOU SURVIVED!", 100, 250);
        } else if (currentState == State.LOST) {
            g2d.setColor(Color.RED);
            g2d.setFont(new Font("Arial", Font.BOLD, 36));
            g2d.drawString("CAUGHT BY SQUIRRELS", 40, 250);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        animFrame++;
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (currentState != State.PLAYING) return;

        int dx = 0, dy = 0;
        if (e.getKeyCode() == KeyEvent.VK_W) dy = -1;
        else if (e.getKeyCode() == KeyEvent.VK_S) dy = 1;
        else if (e.getKeyCode() == KeyEvent.VK_A) dx = -1;
        else if (e.getKeyCode() == KeyEvent.VK_D) dx = 1;
        else return;

        // 1. Calculate Player Move
        int newX = (int) duck.getX() + dx;
        int newY = (int) duck.getY() + dy;

        if (newX >= 0 && newX < COLS && newY >= 0 && newY < ROWS) {
            boolean hitWall = false;
            for (WallSprite w : walls) {
                if (w.getX() == newX && w.getY() == newY) hitWall = true;
            }
            if (!hitWall) duck.setPosition(newX, newY);
        }

        // 2. Turn-based Monster Movement
        for (SquirrelSprite s : squirrels) {
            s.moveUp(ROWS);
        }

        // 3. Optional Collectibles interaction
        breadcrumbs.removeIf(b -> b.getX() == duck.getX() && b.getY() == duck.getY());

        checkCollisions();
        repaint();
    }

    private void checkCollisions() {
        // Did we hit the pond?
        if (duck.getX() == pond.getX() && duck.getY() == pond.getY()) {
            if (currentLevel < MAX_LEVEL) {
                currentLevel++;          // Advance to next level
                loadLevel(currentLevel); // Load the new map
            } else {
                currentState = State.WON; // Beat the final level!
            }
        }

        // Did we hit a squirrel?
        for (SquirrelSprite s : squirrels) {
            if (duck.getX() == s.getX() && duck.getY() == s.getY()) {
                currentState = State.LOST;
            }
        }
    }

    @Override public void keyReleased(KeyEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}
}