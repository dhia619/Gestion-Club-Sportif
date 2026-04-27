package view;

import javax.swing.JFrame;

public class MainFrame extends JFrame{
    public MainFrame(int width, int height, String title){
        super();
        this.setSize(width, height);
        this.setTitle(title);
        this.add(new LoginPanel());
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
