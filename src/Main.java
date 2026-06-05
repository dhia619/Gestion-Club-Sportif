
import dao.DatabaseInitializer;
import util.Lang;
import util.LanguageHandler;
import view.MainFrame;

public class Main {
    public static void main(String[] args){
        DatabaseInitializer.init();
        LanguageHandler.init();
        new MainFrame(1000, 750 , Lang.get("app.title"));
    }
}
