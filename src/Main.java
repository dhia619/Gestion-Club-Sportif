
import dao.DatabaseInitializer;
import view.MainFrame;

public class Main {
    public static void main(String[] args){
        DatabaseInitializer.init();
        new MainFrame(800, 500 , "Club Sportif").showAdminDashboard(null);
    }
}
