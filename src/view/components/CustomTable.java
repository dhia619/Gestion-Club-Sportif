package view.components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;

import util.UIConstants;

public class CustomTable extends JTable{
    
    public CustomTable(TableModel model) {
        super(model);
        setRowHeight(40);
        setFont(new Font(UIConstants.tableFont, Font.PLAIN, 14));
        setShowGrid(false);
        setIntercellSpacing(new Dimension(0, 0));
        setFillsViewportHeight(true);
        setSelectionBackground(UIConstants.tableSelectionBackgroundColor);
        setSelectionForeground(UIConstants.tableSelectionForegroundColor);
        setForeground(UIConstants.tableForegroundColor);

        setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {

                Component c = super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column);

                setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(238, 240, 243)),
                    BorderFactory.createEmptyBorder(0, 6, 0, 6)
                ));

                if (isSelected) {
                    c.setBackground(UIConstants.tableSelectionBackgroundColor);
                    c.setForeground(UIConstants.tableSelectionForegroundColor);
                } else {
                    c.setForeground(UIConstants.tableForegroundColor);
                    c.setBackground(row % 2 == 0
                        ? UIConstants.tableRowEvenColor
                        : UIConstants.tableRowOddColor);
                }

                return c;
            }
        });

        JTableHeader header = getTableHeader();
        header.setBackground(UIConstants.navy);
        header.setForeground(Color.WHITE);
        header.setFont(new Font(UIConstants.tableFont, Font.PLAIN, 12));
        header.setPreferredSize(new Dimension(0, 44));

        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {

                JLabel lbl = (JLabel) super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column);

                lbl.setBackground(UIConstants.navy);
                lbl.setForeground(new Color(200, 210, 225));
                lbl.setFont(new Font(UIConstants.tableFont, Font.PLAIN, 12));
                lbl.setText(value != null ? value.toString().toUpperCase() : "");
                lbl.setBorder(BorderFactory.createEmptyBorder(0, 16, 0, 16));
                lbl.setOpaque(true);

                return lbl;
            }
        });
    }

}
