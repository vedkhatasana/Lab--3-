
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AdminPage {
    private VBox view;
    private TableView<EmployeeData.EmployeeCredential> tableView;
    private ObservableList<EmployeeData.EmployeeCredential> employeeList;
    private TextField nameField, emailField, passwordField, salaryField;
    private ComboBox<String> roleComboBox;

    public AdminPage(Stage stage) {
        view = new VBox(10);
        view.setPadding(new Insets(20));

        Label title = new Label("Admin Panel - Employee Management");
        title.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

        // Initialize employee list
        employeeList = FXCollections.observableArrayList(EmployeeData.getSampleEmployees());

        // Create form fields
        createFormFields();

        // Create table
        createTableView();

        // Create buttons
        HBox buttonBox = createButtons(stage);

        view.getChildren().addAll(title, createFormLayout(), buttonBox, tableView);
    }

    private void createFormFields() {
        nameField = new TextField();
        nameField.setPromptText("Name");
        
        emailField = new TextField();
        emailField.setPromptText("Email");
        
        passwordField = new TextField();
        passwordField.setPromptText("Password");
        
        salaryField = new TextField();
        salaryField.setPromptText("Salary");
        
        roleComboBox = new ComboBox<>();
        roleComboBox.getItems().addAll("Admin", "Employee");
        roleComboBox.setPromptText("Select Role");
    }

    private GridPane createFormLayout() {
        GridPane form = new GridPane();
        form.setHgap(10);
        form.setVgap(10);
        
        form.add(new Label("Name:"), 0, 0);
        form.add(nameField, 1, 0);
        form.add(new Label("Email:"), 0, 1);
        form.add(emailField, 1, 1);
        form.add(new Label("Password:"), 0, 2);
        form.add(passwordField, 1, 2);
        form.add(new Label("Role:"), 0, 3);
        form.add(roleComboBox, 1, 3);
        form.add(new Label("Salary:"), 0, 4);
        form.add(salaryField, 1, 4);
        
        return form;
    }

    private void createTableView() {
        tableView = new TableView<>();
        
        TableColumn<EmployeeData.EmployeeCredential, String> nameCol = new TableColumn<>("Name");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        
        TableColumn<EmployeeData.EmployeeCredential, String> emailCol = new TableColumn<>("Email");
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        
        TableColumn<EmployeeData.EmployeeCredential, String> roleCol = new TableColumn<>("Role");
        roleCol.setCellValueFactory(new PropertyValueFactory<>("role"));
        
        TableColumn<EmployeeData.EmployeeCredential, Double> salaryCol = new TableColumn<>("Salary");
        salaryCol.setCellValueFactory(new PropertyValueFactory<>("salary"));
        
        tableView.getColumns().addAll(nameCol, emailCol, roleCol, salaryCol);
        tableView.setItems(employeeList);
        
        // Handle row selection
        tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                populateForm(newSelection);
            }
        });
    }

    private void populateForm(EmployeeData.EmployeeCredential employee) {
        nameField.setText(employee.getName());
        emailField.setText(employee.getEmail());
        passwordField.setText(employee.getPassword());
        roleComboBox.setValue(employee.getRole());
        salaryField.setText(String.valueOf(employee.getSalary()));
    }

    private void clearForm() {
        nameField.clear();
        emailField.clear();
        passwordField.clear();
        roleComboBox.setValue(null);
        salaryField.clear();
    }

    private HBox createButtons(Stage stage) {
        Button createBtn = new Button("Create");
        Button updateBtn = new Button("Update");
        Button deleteBtn = new Button("Delete");
        Button viewBtn = new Button("View All");
        Button logoutBtn = new Button("Logout");
        Button backBtn = new Button("Back to Dashboard");

        createBtn.setOnAction(e -> createEmployee());
        updateBtn.setOnAction(e -> updateEmployee());
        deleteBtn.setOnAction(e -> deleteEmployee());
        viewBtn.setOnAction(e -> refreshTable());
        
        logoutBtn.setOnAction(e -> {
            LoginPage loginPage = new LoginPage(stage);
            stage.setScene(new Scene(loginPage.getView(), 400, 300));
        });
        
        backBtn.setOnAction(e -> {
            DashboardPage dashboard = new DashboardPage(stage, "Admin");
            stage.setScene(new Scene(dashboard.getView(), 500, 300));
        });

        HBox buttonBox = new HBox(10);
        buttonBox.getChildren().addAll(createBtn, updateBtn, deleteBtn, viewBtn, logoutBtn, backBtn);
        return buttonBox;
    }

    private void createEmployee() {
        if (validateForm()) {
            EmployeeData.EmployeeCredential newEmployee = new EmployeeData.EmployeeCredential(
                emailField.getText(),
                passwordField.getText(),
                nameField.getText(),
                roleComboBox.getValue(),
                Double.parseDouble(salaryField.getText())
            );
            employeeList.add(newEmployee);
            clearForm();
            showAlert("Success", "Employee created successfully!");
        }
    }

    private void updateEmployee() {
        EmployeeData.EmployeeCredential selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null && validateForm()) {
            int index = employeeList.indexOf(selected);
            EmployeeData.EmployeeCredential updated = new EmployeeData.EmployeeCredential(
                emailField.getText(),
                passwordField.getText(),
                nameField.getText(),
                roleComboBox.getValue(),
                Double.parseDouble(salaryField.getText())
            );
            employeeList.set(index, updated);
            clearForm();
            showAlert("Success", "Employee updated successfully!");
        } else {
            showAlert("Error", "Please select an employee to update!");
        }
    }

    private void deleteEmployee() {
        EmployeeData.EmployeeCredential selected = tableView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            employeeList.remove(selected);
            clearForm();
            showAlert("Success", "Employee deleted successfully!");
        } else {
            showAlert("Error", "Please select an employee to delete!");
        }
    }

    private void refreshTable() {
        tableView.refresh();
    }

    private boolean validateForm() {
        if (nameField.getText().isEmpty() || emailField.getText().isEmpty() || 
            passwordField.getText().isEmpty() || roleComboBox.getValue() == null || 
            salaryField.getText().isEmpty()) {
            showAlert("Error", "Please fill in all fields!");
            return false;
        }
        
        try {
            Double.parseDouble(salaryField.getText());
        } catch (NumberFormatException e) {
            showAlert("Error", "Please enter a valid salary amount!");
            return false;
        }
        
        return true;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public VBox getView() {
        return view;
    }
}
