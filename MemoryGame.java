import javax.swing.*;
import java.awt.*;

public class MemoryGame extends JFrame {
    private JTextField nameField;
    private BackgroundPanel background;

    public MemoryGame() {
        setTitle("Memory Game");
        setSize(540, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        background = new BackgroundPanel("/img/background.jpg");
        background.setLayout(null);

       JLabel nameLabel = new JLabel("Enter your name:");
        nameLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        nameLabel.setForeground(new Color(0xB7, 0x76, 0x7C));
        nameLabel.setBounds(193, 230, 200, 30);
        background.add(nameLabel);


        nameField = new JTextField();
        nameField.setBounds(170, 260, 200, 30);
        nameField.setHorizontalAlignment(JTextField.CENTER);
        background.add(nameField);

        JButton startButton = new JButton("Start Game");
        startButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        startButton.setBounds(195, 310, 150, 40);
        background.add(startButton);

        startButton.addActionListener(e -> {
            String playerName = nameField.getText().trim();
            if (!playerName.isEmpty()) {
                getContentPane().remove(background);
                setContentPane(new GamePanel(playerName));  
                revalidate();
                repaint();
            } else {
                JOptionPane.showMessageDialog(MemoryGame.this, "Please enter your name.");
            }
        });

        setContentPane(background);
        setVisible(true);
    }

    class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        public BackgroundPanel(String resourcePath) {
            try {
                java.net.URL imgURL = getClass().getResource(resourcePath);
                if (imgURL != null) {
                    backgroundImage = new ImageIcon(imgURL).getImage();
                } else {
                    System.out.println("Image not found: " + resourcePath);
                }
            } catch (Exception e) {
                System.out.println("Error loading background image: " + e.getMessage());
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MemoryGame::new);
    }
}
