package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.AdminDashboardPanel;
import view.MainFrame;

public class AdminDashboardController {

    AdminDashboardPanel view;

    public AdminDashboardController(AdminDashboardPanel view, MainFrame mainFrame) {
        this.view = view;
        this.view.getLogoutButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                mainFrame.showLoginPanel();
            }
        });
    }
}
