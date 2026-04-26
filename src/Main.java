import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("CS180 Game Jam: Duck vs Squirrels!");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new BorderLayout());

            GamePanel gamePanel = new GamePanel();
            frame.add(gamePanel, BorderLayout.CENTER);

            // Requirement: Add a start and restart button
            JPanel buttonPanel = new JPanel();
            buttonPanel.setBackground(Color.DARK_GRAY);

            JButton startBtn = new JButton("Start");
            JButton restartBtn = new JButton("Restart");

            startBtn.addActionListener(e -> {
                gamePanel.startGame();
                gamePanel.requestFocusInWindow();
            });
            restartBtn.addActionListener(e -> {
                gamePanel.restartGame();
                gamePanel.requestFocusInWindow();
            });

            buttonPanel.add(startBtn);
            buttonPanel.add(restartBtn);
            frame.add(buttonPanel, BorderLayout.SOUTH);

            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            gamePanel.requestFocusInWindow();
        });
    }
}