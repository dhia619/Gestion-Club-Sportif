package view.components;

import javax.swing.JOptionPane;

import util.Lang;

import java.awt.Component;

public class PopUpHandler {

    public static void showError(Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message, Lang.get("popup.title.error"), JOptionPane.ERROR_MESSAGE);
    }

    public static void showInfo(Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message, Lang.get("popup.title.info"), JOptionPane.INFORMATION_MESSAGE);
    }

    public static boolean showConfirm(Component parent, String message) {
        int result = JOptionPane.showConfirmDialog(
            parent,
            message,
            Lang.get("popup.title.confirm"),
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );

        return result == JOptionPane.YES_OPTION;
    }
}