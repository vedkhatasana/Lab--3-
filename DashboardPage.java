import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DashboardPage {
    private VBox view;

    public DashboardPage(Stage stage, String username) {
        view = new VBox(10);

        Label welcome = new Label("Welcome, " + username);
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM dd, yyyy");
        Label dateLabel = new Label("Current Date: " + currentDate.format(formatter));
        
        Button adminBtn = new Button("Admin");
        Button empBtn = new Button("Employee");
        Button logoutBtn = new Button("Logout");
        Button exitBtn = new Button("Exit");

        adminBtn.setOnAction(e -> stage.setScene(new Scene(new AdminPage(stage).getView(), 600, 400)));
        empBtn.setOnAction(e -> stage.setScene(new Scene(new EmployeePage(stage).getView(), 600, 400)));
        logoutBtn.setOnAction(e -> stage.setScene(new Scene(new LoginPage(stage).getView(), 400, 300)));
        exitBtn.setOnAction(e -> stage.close());

        view.getChildren().addAll(welcome, dateLabel, adminBtn, empBtn, logoutBtn, exitBtn);
    }

    public VBox getView() {
        return view;
    }
}