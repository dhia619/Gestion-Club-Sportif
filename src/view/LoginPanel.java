package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import util.UIConstants;

public class LoginPanel extends JPanel {

    public LoginPanel() {
        this.setLayout(new GridLayout(1, 2));

        JPanel leftChild = new JPanel();
        JPanel rightChild = new JPanel();

        leftChild.setBackground(UIConstants.primaryBgColor);
        rightChild.setBackground(UIConstants.secondaryBgColor);

        this.add(leftChild);
        this.add(rightChild);

        // ================= LEFT CHILD =================

        leftChild.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());
        JPanel bottomPanel = new JPanel(new BorderLayout());
        JPanel centerPanel = new JPanel();

        topPanel.setOpaque(false);
        bottomPanel.setOpaque(false);
        centerPanel.setOpaque(false);

        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        // Change paths according to your real image names
        JLabel topLeftImage = new JLabel(new ImageIcon("src/resources/images/exercise.png"));
        JLabel topRightImage = new JLabel(new ImageIcon("src/resources/images/lift.png"));
        JLabel bottomLeftImage = new JLabel(new ImageIcon("src/resources/images/lotus.png"));
        JLabel bottomRightImage = new JLabel(new ImageIcon("src/resources/images/running.png"));

        topPanel.add(topLeftImage, BorderLayout.WEST);
        topPanel.add(topRightImage, BorderLayout.EAST);

        bottomPanel.add(bottomLeftImage, BorderLayout.WEST);
        bottomPanel.add(bottomRightImage, BorderLayout.EAST);

        JLabel welcomeLabel = new JLabel("Bienvenue au");
        JLabel clubNameLabel = new JLabel("Club Sportif");
        JLabel sloganLabel = new JLabel("Le sport pour tous");

        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        clubNameLabel.setFont(new Font("Arial", Font.BOLD, 30));
        sloganLabel.setFont(new Font("Arial", Font.PLAIN, 17));

        welcomeLabel.setForeground(Color.WHITE);
        clubNameLabel.setForeground(Color.WHITE);
        sloganLabel.setForeground(Color.WHITE);

        welcomeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        clubNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        sloganLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        centerPanel.add(Box.createVerticalStrut(100));
        centerPanel.add(welcomeLabel);
        centerPanel.add(Box.createVerticalStrut(5));
        centerPanel.add(clubNameLabel);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(sloganLabel);

        leftChild.add(topPanel, BorderLayout.NORTH);
        leftChild.add(centerPanel, BorderLayout.CENTER);
        leftChild.add(bottomPanel, BorderLayout.SOUTH);

        // ================= RIGHT CHILD =================

        rightChild.setLayout(new GridBagLayout());

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(UIConstants.secondaryBgColor);

        JLabel titleLabel = new JLabel("Se connecter");
        JLabel identifiantLabel = new JLabel("Identifiant");
        JLabel motDePasseLabel = new JLabel("Mot de passe");

        titleLabel.setFont(new Font("Courier", Font.BOLD, 27));
        identifiantLabel.setFont(new Font("Arial", Font.BOLD, 14));
        motDePasseLabel.setFont(new Font("Arial", Font.BOLD, 14));

        JTextField identifiantField = new JTextField();
        JPasswordField motDePasseField = new JPasswordField();

        identifiantField.setFont(new Font("Arial", Font.BOLD, 17));
        motDePasseField.setFont(new Font("Arial", Font.BOLD, 17));

        Dimension inputSize = new Dimension(300, 40);

        identifiantField.setPreferredSize(inputSize);
        motDePasseField.setPreferredSize(inputSize);

        JButton loginButton = new JButton("Se connecter");
        loginButton.setFont(new Font("Arial", Font.BOLD, 18));
        loginButton.setFocusable(false);
        loginButton.setPreferredSize(new Dimension(300, 42));

        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        identifiantLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        identifiantField.setAlignmentX(Component.LEFT_ALIGNMENT);
        motDePasseLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        motDePasseField.setAlignmentX(Component.LEFT_ALIGNMENT);
        loginButton.setAlignmentX(Component.LEFT_ALIGNMENT);

        formPanel.add(titleLabel);
        formPanel.add(Box.createVerticalStrut(25));

        formPanel.add(identifiantLabel);
        formPanel.add(Box.createVerticalStrut(5));
        formPanel.add(identifiantField);
        formPanel.add(Box.createVerticalStrut(15));

        formPanel.add(motDePasseLabel);
        formPanel.add(Box.createVerticalStrut(5));
        formPanel.add(motDePasseField);
        formPanel.add(Box.createVerticalStrut(20));

        formPanel.add(loginButton);

        rightChild.add(formPanel);
    }
}