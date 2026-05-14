package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import controller.GestionActiviteController;
import controller.GestionMembreController;
import model.Utilisateur;
import util.Lang;
import util.LanguageHandler;
import util.UIConstants;
import view.components.CustomButton;
import view.components.CustomLabel;

public class AdminDashboardPanel extends JPanel {

    private JPanel contentPanel;
    private CardLayout cardLayout;
    JButton logoutButton;
    JComboBox<String> languageBox;

    private JLabel titleLabel;

    private JButton dashboardButton;
    private JButton membersButton;
    private JButton activitiesButton;
    private JButton registrationsButton;
    private JButton monitoringButton;

    private GestionMembreController membreController;
    private GestionActiviteController activiteController;

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

        titleLabel = new CustomLabel(
                Lang.get("admin.dashboard.title"),
                UIConstants.navy,
                UIConstants.titleFont,
                Font.PLAIN,
                22
        );

        logoutButton = new CustomButton(
                Lang.get("disconnect"),
                UIConstants.terracotta
        );

        String[] languages = {
                "English",
                "Français",
                "العربية"
        };

        ImageIcon languageIcon = new ImageIcon("./resources/images/worldwide.png");

        languageBox = new JComboBox<>(languages);

        languageBox.setPreferredSize(new Dimension(90, 35));

        languageBox.setSelectedItem(switch (LanguageHandler.getLocale()) {
            case "en" -> "English";
            case "fr" -> "Français";
            case "ar" -> "العربية";
            default -> "Français";
        });

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));

        rightPanel.setOpaque(false);

        rightPanel.add(new JLabel(languageIcon));
        rightPanel.add(languageBox);
        rightPanel.add(logoutButton);

        topBar.add(titleLabel, BorderLayout.CENTER);
        topBar.add(rightPanel, BorderLayout.EAST);

        return topBar;
    }

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setPreferredSize(new Dimension(220, 0));
        sidebar.setBackground(UIConstants.primaryBackgroundColor);
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBorder(BorderFactory.createEmptyBorder(20, 15, 20, 15));


        dashboardButton = createMenuButton(Lang.get("dashboard"), "dashboard");
        membersButton = createMenuButton(Lang.get("members"), "membres");
        activitiesButton = createMenuButton(Lang.get("activities"), "activites");
        registrationsButton = createMenuButton(Lang.get("registrations"), "inscriptions");
        monitoringButton = createMenuButton(Lang.get("monitoring"), "suivi");

        sidebar.add(dashboardButton);
        sidebar.add(Box.createVerticalStrut(10));

        sidebar.add(membersButton);
        sidebar.add(Box.createVerticalStrut(10));

        sidebar.add(activitiesButton);
        sidebar.add(Box.createVerticalStrut(10));

        sidebar.add(registrationsButton);
        sidebar.add(Box.createVerticalStrut(10));

        sidebar.add(monitoringButton);

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
        JPanel panel = createBasePage();

        return panel;
    }

    private JPanel gestionMembresPage() {
        JPanel panel = createBasePage();
        membreController = new GestionMembreController();
        panel.add(membreController.getView());
        return panel;
    }

    private JPanel gestionActivitesPage() {
        JPanel panel = createBasePage();
        activiteController = new GestionActiviteController();
        panel.add(activiteController.getView());

        return panel;
    }

    private JPanel gestionInscriptionsPage() {
        JPanel panel = createBasePage();

        return panel;
    }

    private JPanel gestionSuiviPage() {
        JPanel panel = createBasePage();

        JLabel complete = new CustomLabel("Activités complètes : Musculation, Natation");
        JLabel active = new CustomLabel("Membres les plus actifs : Sami, Lina");

        panel.add(complete);
        panel.add(active);

        return panel;
    }

    private JPanel createBasePage() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(UIConstants.secondaryBackgroundColor);
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        panel.add(Box.createVerticalStrut(25));

        return panel;
    }

    public JButton getLogoutButton() {
        return logoutButton;
    }

    public JComboBox<String> getLanguageComboBox() {
        return languageBox;
    }

    public void refreshUIText() {
        
        titleLabel.setFont(new Font(LanguageHandler.getTitleFont(), Font.PLAIN, 22));
        titleLabel.setText(Lang.get("admin.dashboard.title"));

        logoutButton.setText(Lang.get("disconnect"));

        dashboardButton.setText(Lang.get("dashboard"));
        membersButton.setText(Lang.get("members"));
        activitiesButton.setText(Lang.get("activities"));
        registrationsButton.setText(Lang.get("registrations"));
        monitoringButton.setText(Lang.get("monitoring"));

        membreController.getView().refreshUIText();
        activiteController.getView().refreshUIText();

        revalidate();
        repaint();
    }

}