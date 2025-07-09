import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        LoginPage loginPage = new LoginPage(primaryStage);
        primaryStage.setTitle("HR Management - VED PATEL");
        primaryStage.setScene(new Scene(loginPage.getView(), 400, 300));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
