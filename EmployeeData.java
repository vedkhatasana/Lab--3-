
import java.util.ArrayList;
import java.util.List;

public class EmployeeData {
    public static class EmployeeCredential {
        private String email;
        private String password;
        private String name;
        private String role;
        private double salary;
        
        public EmployeeCredential(String email, String password, String name, String role, double salary) {
            this.email = email;
            this.password = password;
            this.name = name;
            this.role = role;
            this.salary = salary;
        }
        
        public String getEmail() { return email; }
        public String getPassword() { return password; }
        public String getName() { return name; }
        public String getRole() { return role; }
        public double getSalary() { return salary; }
    }
    
    public static List<EmployeeCredential> getSampleEmployees() {
        List<EmployeeCredential> employees = new ArrayList<>();
        
        employees.add(new EmployeeCredential("ved@example.com", "admin123", "Ved Patel", "Admin", 80000));
        employees.add(new EmployeeCredential("admin1@company.com", "admin1", "Preet Admin", "Admin", 55000));
        employees.add(new EmployeeCredential("manager1@company.com", "manager1", "Piyush Manager", "Manager", 60000));
        employees.add(new EmployeeCredential("supervisor1@company.com", "supervisor1", "Param Supervisor", "Supervisor", 52000));

        
        return employees;
    }
    
    public static EmployeeCredential validateLogin(String email, String password) {
        // Trim whitespace and make email comparison case-insensitive
        String trimmedEmail = email != null ? email.trim().toLowerCase() : "";
        String trimmedPassword = password != null ? password.trim() : "";
        
        for (EmployeeCredential emp : getSampleEmployees()) {
            if (emp.getEmail().toLowerCase().equals(trimmedEmail) && 
                emp.getPassword().equals(trimmedPassword)) {
                return emp;
            }
        }
        return null;
    }
}
