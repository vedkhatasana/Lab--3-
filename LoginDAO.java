
public class LoginDAO {
    public static boolean validate(String email, String password) {
        // Check against sample employee data
        EmployeeData.EmployeeCredential employee = EmployeeData.validateLogin(email, password);
        return employee != null;
    }
    
    public static EmployeeData.EmployeeCredential getEmployee(String email, String password) {
        return EmployeeData.validateLogin(email, password);
    }
}
