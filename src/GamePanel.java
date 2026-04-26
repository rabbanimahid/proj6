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

    private Timer timer;
    private int animFrame = 0; // Controls the 4 animations

    public GamePanel() {
        setPreferredSize(new Dimension(COLS * CELL_SIZE, ROWS * CELL_SIZE));
        setBackground(new Color(34, 139, 34)); // Forest green
        addKeyListener(this);
        setFocusable(true);

        // Timer to drive the visual animations (not movement)
        timer = new Timer(200, this);
        timer.start();

        initLevel();
    }

    private void initLevel() {
        duck = new DuckSprite(0, 0);
        pond = new PondSprite(9, 9); // Goal

        squirrels = new ArrayList<>();
        squirrels.add(new SquirrelSprite(3, 5));
        squirrels.add(new SquirrelSprite(7, 8));
        squirrels.add(new SquirrelSprite(5, 2));

        walls = new ArrayList<>();
        walls.add(new WallSprite(2, 2));
        walls.add(new WallSprite(2, 3));
        walls.add(new WallSprite(2, 4));
        walls.add(new WallSprite(8, 8));

        breadcrumbs = new ArrayList<>();
        breadcrumbs.add(new BreadcrumbSprite(5, 5));
        breadcrumbs.add(new BreadcrumbSprite(8, 2));

        bushes = new ArrayList<>();
        bushes.add(new BushSprite(0, 9));
        bushes.add(new BushSprite(9, 0));
    }

    public void startGame() {
        if (currentState == State.MENU) currentState = State.PLAYING;
        repaint();
    }

    public void restartGame() {
        initLevel();
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

        // UI Overlays
        if (currentState == State.WON) {
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
        animFrame++; // Advance animation frame
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
        else return; // Ignore non-movement keys

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

        // 2. Requirement: Turn-based Monster Movement (move up, wrap to bottom)
        for (SquirrelSprite s : squirrels) {
            s.moveUp(ROWS);
        }

        // 3. Optional Collectibles interaction
        breadcrumbs.removeIf(b -> b.getX() == duck.getX() && b.getY() == duck.getY());

        checkCollisions();
        repaint();
    }

    private void checkCollisions() {
        if (duck.getX() == pond.getX() && duck.getY() == pond.getY()) {
            currentState = State.WON;
        }
        for (SquirrelSprite s : squirrels) {
            if (duck.getX() == s.getX() && duck.getY() == s.getY()) {
                currentState = State.LOST;
            }
        }
    }

    @Override public void keyReleased(KeyEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}
}