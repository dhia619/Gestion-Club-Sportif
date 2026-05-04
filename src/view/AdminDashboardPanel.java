package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import controller.GestionActiviteController;
import controller.GestionMembreController;
import model.Utilisateur;
import util.UIConstants;

public class AdminDashboardPanel extends JPanel {

    private JPanel contentPanel;
    private CardLayout cardLayout;
    JButton logoutButton;

    public AdminDashboardPanel(Utilisateur utilisateur) {
        setLayout(new BorderLayout());

        add(createTopBar(), BorderLayout.NORTH);
        add(createSidebar(), BorderLayout.WEST);
        add(createContentPanel(), BorderLayout.CENTER);
    }

    private JPanel createTopBar() {
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setPreferredSize(new Dimension(0, 60));
        topBar.setBackground(Color.WHITE);
        topBar.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel title = new CustomLabel("Dashboard Administrateur", UIConstants.navy, "Georgia", Font.ITALIC, 22);

        logoutButton = new CustomButton("Déconnexion", UIConstants.terracotta);

        topBar.add(title, BorderLayout.WEST);
        topBar.add(logoutButton, BorderLayout.EAST);

        return topBar;
    }

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setPreferredSize(new Dimension(220, 0));
        sidebar.setBackground(UIConstants.primaryBackgroundColor);
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBorder(BorderFactory.createEmptyBorder(20, 15, 20, 15));

        sidebar.add(createMenuButton("Dashboard", "dashboard"));
        sidebar.add(Box.createVerticalStrut(10));

        sidebar.add(createMenuButton("Membres", "membres"));
        sidebar.add(Box.createVerticalStrut(10));

        sidebar.add(createMenuButton("Activités", "activites"));
        sidebar.add(Box.createVerticalStrut(10));

        sidebar.add(createMenuButton("Inscriptions", "inscriptions"));
        sidebar.add(Box.createVerticalStrut(10));

        sidebar.add(createMenuButton("Suivi", "suivi"));

        return sidebar;
    }

    private JButton createMenuButton(String text, String cardName) {
        JButton button = new CustomButton(text, UIConstants.menuButtonBackgroundColor);
        button.setMaximumSize(new Dimension(190, 45));
        button.setHorizontalAlignment(SwingConstants.LEFT);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                cardLayout.show(contentPanel, cardName);
            }
        });

        return button;
    }

    private JPanel createContentPanel() {
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);

        contentPanel.add(createDashboardPage(), "dashboard");
        contentPanel.add(gestionMembresPage(), "membres");
        contentPanel.add(gestionActivitesPage(), "activites");
        contentPanel.add(gestionInscriptionsPage(), "inscriptions");
        contentPanel.add(gestionSuiviPage(), "suivi");

        return contentPanel;
    }

    private JPanel createDashboardPage() {
        JPanel panel = createBasePage("Vue générale");

        return panel;
    }

    private JPanel gestionMembresPage() {
        JPanel panel = createBasePage("Gestion des membres");
        GestionMembreController controller = new GestionMembreController();
        panel.add(controller.getView());
        return panel;
    }

    private JPanel gestionActivitesPage() {
        JPanel panel = createBasePage("Gestion des activités");
        GestionActiviteController controller = new GestionActiviteController();
        panel.add(controller.getView());

        return panel;
    }

    private JPanel gestionInscriptionsPage() {
        JPanel panel = createBasePage("Gestion des inscriptions");

        return panel;
    }

    private JPanel gestionSuiviPage() {
        JPanel panel = createBasePage("Suivi et gestion");

        JLabel complete = new CustomLabel("Activités complètes : Musculation, Natation");
        JLabel active = new CustomLabel("Membres les plus actifs : Sami, Lina");

        panel.add(complete);
        panel.add(active);

        return panel;
    }

    private JPanel createBasePage(String titleText) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(UIConstants.secondaryBackgroundColor);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JLabel title = new CustomLabel(titleText, 24);
        title.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(title);
        panel.add(Box.createVerticalStrut(25));

        return panel;
    }

    private JPanel createStatCard(String title, String value) {
        JPanel card = new JPanel();
        card.setMaximumSize(new Dimension(300, 90));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        card.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.PLAIN, 15));

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Arial", Font.BOLD, 28));

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);

        return card;
    }

    public JButton getLogoutButton() {
        return logoutButton;
    }
}