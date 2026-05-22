package view.admin;

import javax.swing.*;
import util.Lang;
import util.UIConstants;
import view.components.CustomLabel;

import java.awt.*;
import java.util.List;

public class OverviewPanel extends JPanel {

    private JPanel cardsPanel;

    public record BreakdownItem(String label, String value, Color dotColor) {}

    public OverviewPanel() {
        setLayout(new BorderLayout());
        setBackground(UIConstants.secondaryBackgroundColor);
        setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JLabel title = new CustomLabel(
            Lang.get("statistics"),
            UIConstants.belizeBlue,
            UIConstants.tableFont,
            Font.BOLD,
            30
        );

        JPanel titleWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        titleWrapper.setOpaque(false);
        titleWrapper.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        titleWrapper.add(title);

        cardsPanel = new JPanel(new GridLayout(0, 3, 20, 20));
        cardsPanel.setOpaque(false);

        JPanel cardsWrapper = new JPanel(new BorderLayout());
        cardsWrapper.setOpaque(false);
        cardsWrapper.add(cardsPanel, BorderLayout.NORTH);

        add(titleWrapper, BorderLayout.NORTH);
        add(cardsWrapper, BorderLayout.CENTER);
    }

    // Simple Card
    public JPanel createStatCard(String title, String value) {
        JPanel card = buildCardSkeleton();

        JLabel titleLabel = new CustomLabel(title, UIConstants.tableFont, 19);
        JLabel valueLabel = new CustomLabel(value, UIConstants.tableFont, 28);

        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        valueLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(Box.createVerticalGlue());
        card.add(titleLabel);
        card.add(Box.createVerticalStrut(10));
        card.add(valueLabel);
        card.add(Box.createVerticalGlue());

        return card;
    }

    // Compound card (main stat + breakdown rows)
    public JPanel createCompoundCard(String title, String totalValue, List<BreakdownItem> items) {
        JPanel card = buildCardSkeleton();

        // Header
        card.add(createStatCard(title, totalValue));

        // Divider
        card.add(Box.createVerticalStrut(12));
        JSeparator sep = new JSeparator(SwingConstants.HORIZONTAL);
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        sep.setForeground(UIConstants.concreteGrey);
        card.add(sep);
        card.add(Box.createVerticalStrut(10));

        // Breakdown rows
        for (BreakdownItem item : items) {
            card.add(buildBreakdownRow(item));
            card.add(Box.createVerticalStrut(7));
        }

        return card;
    }

    private JPanel buildCardSkeleton() {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(UIConstants.lightGrey),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        card.setPreferredSize(new Dimension(0, 270));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 300));
        return card;
    }

    private JPanel buildBreakdownRow(BreakdownItem item) {
        JPanel row = new JPanel(new BorderLayout());
        row.setOpaque(false);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));

        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        left.setOpaque(false);

        JPanel dot = new JPanel();
        dot.setPreferredSize(new Dimension(8, 8));
        dot.setMaximumSize(new Dimension(8, 8));
        dot.setBackground(item.dotColor());
        dot.setPreferredSize(new Dimension(8, 8));

        JLabel lblLabel = new CustomLabel(item.label(), UIConstants.tableFont, 12);
        left.add(dot);
        left.add(Box.createHorizontalStrut(6));
        left.add(lblLabel);

        JLabel lblValue = new CustomLabel(item.value(), UIConstants.tableFont, 13);

        row.add(left, BorderLayout.WEST);
        row.add(lblValue, BorderLayout.EAST);
        
        return row;
    }

    public JPanel getCardsPanel() { 
        return cardsPanel;
    }
}