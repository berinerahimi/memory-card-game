import javax.swing.*;

public class MemoryCard extends Card implements Flippable {
    private JButton button;
    private Icon frontIcon;
    private Icon backIcon;

    public MemoryCard(String id, Icon frontIcon, Icon backIcon) {
        super(id);
        this.frontIcon = frontIcon;
        this.backIcon = backIcon;

        button = new JButton();
        button.setIcon(backIcon);
    }

    public JButton getButton() {
        return button;
    }

    @Override
    public void flipUp() {
        faceUp = true;
        button.setIcon(frontIcon);
    }

    @Override
    public void flipDown() {
        faceUp = false;
        button.setIcon(backIcon);
    }
}
