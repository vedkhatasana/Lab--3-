import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class LoginPage {
    private VBox view;
    private TextField emailField;
    private PasswordField passField;

    public LoginPage(Stage stage) {
        view = new VBox(10);
        view.setPadding(new Insets(20));

        emailField = new TextField();
        emailField.setPromptText("Email");
        passField = new PasswordField();
        passField.setPromptText("Password");

        Button loginBtn = new Button("Login");
        Label messageLabel = new Label();

        loginBtn.setOnAction(e -> {
            String enteredEmail = emailField.getText();
            String enteredPassword = passField.getText();
            
            System.out.println("Login attempt - Email: '" + enteredEmail + "', Password: '" + enteredPassword + "'");
            
            EmployeeData.EmployeeCredential employee = LoginDAO.getEmployee(enteredEmail, enteredPassword);
            if (employee != null) {
                DashboardPage dashboard = new DashboardPage(stage, employee.getName() + " (" + employee.getRole() + ")");
                stage.setScene(new Scene(dashboard.getView(), 500, 300));
            } else {
                messageLabel.setText("Invalid email or password. Try: ved@example.com / admin123");
            }
        });

        view.getChildren().addAll(new Label("Login Page"), emailField, passField, loginBtn, messageLabel);
    }

    public VBox getView() {
        return view;
    }
}