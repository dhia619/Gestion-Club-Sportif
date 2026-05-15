
import dao.DatabaseInitializer;
import util.Lang;
import util.LanguageHandler;
import view.MainFrame;

public class Main {
    public static void main(String[] args){
        DatabaseInitializer.init();
        LanguageHandler.init();
        new MainFrame(800, 500 , Lang.get("app.title"));
    }
}
