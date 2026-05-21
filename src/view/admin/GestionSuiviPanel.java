package view.admin;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;

import model.ActiviteRow;
import model.MembreActifRow;
import util.Lang;
import util.UIConstants;
import view.components.CustomLabel;

public class GestionSuiviPanel extends JPanel {

    private JLabel titleLabel;
    private JTabbedPane tabs;

    private SuiviActivitePanel suiviActivitePanel;
    private MembreActifPanel membreActifPanel;

    public GestionSuiviPanel(
            ArrayList<ActiviteRow> activiteRows,
            ArrayList<MembreActifRow> membreActifRows
    ) {
        setLayout(new BorderLayout());
        setBackground(UIConstants.secondaryBackgroundColor);

        titleLabel = new CustomLabel(Lang.get("monitoring_and_management"), 24);

        UIManager.put("TabbedPane.contentAreaColor", UIConstants.secondaryBackgroundColor);
        UIManager.put("TabbedPane.selected", Color.WHITE);
        UIManager.put("TabbedPane.background", UIConstants.concreteGrey);
        UIManager.put("TabbedPane.focus", UIConstants.secondaryBackgroundColor);
        UIManager.put("TabbedPane.borderHightlightColor", UIConstants.secondaryBackgroundColor);
        UIManager.put("TabbedPane.darkShadow", UIConstants.secondaryBackgroundColor);
        UIManager.put("TabbedPane.light", UIConstants.secondaryBackgroundColor);
        
        tabs = new JTabbedPane();
        tabs.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));
        tabs.setFont(new Font(UIConstants.tableFont, Font.BOLD, 20));
        suiviActivitePanel = new SuiviActivitePanel(activiteRows);
        membreActifPanel = new MembreActifPanel(membreActifRows);

        tabs.addTab(Lang.get("monitor.activities"), suiviActivitePanel);
        tabs.addTab(Lang.get("members.active"), membreActifPanel);

        add(titleLabel, BorderLayout.NORTH);
        add(tabs, BorderLayout.CENTER);
    }

    public void refreshUIText() {
        titleLabel.setText(Lang.get("monitoring_and_management"));

        tabs.setTitleAt(0, Lang.get("monitor.activities"));
        tabs.setTitleAt(1, Lang.get("monitor.registrations"));

        suiviActivitePanel.refreshUIText();
        membreActifPanel.refreshUIText();

        revalidate();
        repaint();
    }

    public SuiviActivitePanel getSuiviActivitePanel() {
        return suiviActivitePanel;
    }

    public MembreActifPanel getMembreActifPanel() {
        return membreActifPanel;
    }
}