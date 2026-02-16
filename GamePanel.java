import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GamePanel extends JPanel {
    private GameBoard gameBoard;
    private GameTimer gameTimer;
    private JLabel timerLabel;
    private JLabel movesLabel;
    private boolean busy = true;
    private JPanel grid;
    private String playerName;

    public GamePanel(String playerName) {
        this.playerName = playerName;
        setBackground(new Color(245, 245, 255));
        setLayout(new BorderLayout());

        JLabel playerLabel = new JLabel("Game for: " + playerName, SwingConstants.CENTER);
        playerLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        add(playerLabel, BorderLayout.NORTH);

        
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        timerLabel = new JLabel("Time: 0s");
        timerLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        statusPanel.add(timerLabel);

        movesLabel = new JLabel("Moves: 0");
        movesLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        statusPanel.add(movesLabel);

        JButton restartButton = new JButton("Restart Game");
        restartButton.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        statusPanel.add(restartButton);

        add(statusPanel, BorderLayout.SOUTH);

        grid = new JPanel(new GridLayout(4, 5, 0, 0));
        grid.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        grid.setBackground(new Color(245, 245, 255));
        add(grid, BorderLayout.CENTER);

        setupGame();

        restartButton.addActionListener(e -> setupGame());
    }

    private void setupGame() {
        busy = true;

        grid.removeAll();
        if (gameTimer != null) {
            gameTimer.stop();
        }

        String[] cardIDs = {"pic1", "pic2", "pic3", "pic4", "pic5", "pic6", "pic7", "pic8", "pic9", "pic10"};

        Icon backIcon = loadIcon("/img/back.jpg");
        Icon[] frontIcons = new Icon[cardIDs.length];
        for (int i = 0; i < cardIDs.length; i++) {
            frontIcons[i] = loadIcon("/img/" + cardIDs[i] + ".jpg");
        }

        gameBoard = new GameBoard(4, 5);
        gameBoard.initializeCards(cardIDs, frontIcons, backIcon);

        List<MemoryCard> cards = gameBoard.getCards();

        final int[] moveCount = {0};
        movesLabel.setText("Moves: 0");
        timerLabel.setText("Time: 0s");

        for (MemoryCard card : cards) {
            JButton btn = card.getButton();
            btn.setPreferredSize(new Dimension(90, 128));
            btn.addActionListener(e -> {
                if (busy || card.isFaceUp() || (gameBoard.getSecondSelected() != null && gameBoard.getSecondSelected() == card)) {
                    return;
                }

                boolean matchFound = gameBoard.selectCard(card);

                if (gameBoard.getSecondSelected() != null) {
                    moveCount[0]++;
                    movesLabel.setText("Moves: " + moveCount[0]);
                }

                if (!matchFound && gameBoard.getSecondSelected() != null) {
                    busy = true;
                    Timer flipBackTimer = new Timer(1000, evt -> {
                        gameBoard.flipBackUnmatched();
                        busy = false;
                        ((Timer) evt.getSource()).stop();
                    });
                    flipBackTimer.setRepeats(false);
                    flipBackTimer.start();
                }

                if (gameBoard.allMatched()) {
                    gameTimer.stop();
                    JOptionPane.showMessageDialog(GamePanel.this,
                            "Congrats " + playerName + "! You finished in " + timerLabel.getText().substring(6) +
                                    " with " + moveCount[0] + " moves.");
                }
            });
            grid.add(btn);
        }

        revalidate();
        repaint();

        for (MemoryCard card : cards) {
            card.flipUp();
        }

        Timer initialShowTimer = new Timer(3000, e -> {
            for (MemoryCard card : cards) {
                card.flipDown();
            }
            busy = false;  // allow clicks now
            gameTimer = new GameTimer(timerLabel);
            gameTimer.start();
            ((Timer) e.getSource()).stop();
        });
        initialShowTimer.setRepeats(false);
        initialShowTimer.start();
    }

    private Icon loadIcon(String path) {
        java.net.URL url = getClass().getResource(path);
        if (url == null) {
            System.out.println("Image not found: " + path);
            return new ImageIcon();
        }
        Image img = new ImageIcon(url).getImage().getScaledInstance(90, 128, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }
}
