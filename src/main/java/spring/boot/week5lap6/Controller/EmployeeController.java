package spring.boot.week5lap6.Controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import spring.boot.week5lap6.Api.ApiEmployee;
import spring.boot.week5lap6.Model.Employee;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {

    ArrayList<Employee> employees = new ArrayList<>();

    //Get
    @GetMapping("/get")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.ok(employees);
    }

    //Post
    @PostMapping("/add")
    public ResponseEntity addEmployee(@Valid @RequestBody Employee employee, Errors error) {
        if (error.hasErrors()) {
            String message = error.getFieldError().getDefaultMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
        employees.add(employee);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiEmployee("Success add new employee"));
    }

    //update
    @PutMapping("/update/{index}")
    public ResponseEntity updateEmployee(@Valid @RequestBody Employee employee, @PathVariable int index, Errors error) {
        if (error.hasErrors()) {
            String message = error.getFieldError().getDefaultMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
        employees.set(index, employee);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiEmployee("Success update employee"));
    }

    //delete
    @DeleteMapping("/delete/{index}")
    public ResponseEntity deleteEmployee(@PathVariable int index) {
        employees.remove(index);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiEmployee("Success remove employee"));
    }

    // searchEmployeesByPosition
    @GetMapping("/searchEmployeesByPosition")
    public ResponseEntity<List<Employee>> searchEmployeesByPosition(@RequestParam String position) {
        List<Employee> positionList = new ArrayList<>();
        for (Employee employee : employees) {
            if (employee.getPosition().equalsIgnoreCase(position)) {
                positionList.add(employee);
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(positionList);
    }

    // Get employees by age range
    @GetMapping("/getEmployeesByAgeRange")
    public ResponseEntity<List<Employee>> getEmployeesByAgeRange(@RequestParam int minAge, @RequestParam int maxAge) {
        List<Employee> result = new ArrayList<>();
        for (Employee employee : employees) {
            if (employee.getAge() >= minAge && employee.getAge() <= maxAge) {
                result.add(employee);
            }
        }
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // Apply for annual leave
    @PutMapping("/applyForAnnualLeave/{id}")
    private ResponseEntity<ApiEmployee> applyForAnnualLeave(@PathVariable String id) {
        for (Employee employee : employees) {
            if (employee.getId().equals(id)) {
                if (!employee.isOnLeave() && employee.getAnnualLeave() > 0) {
                    employee.setOnLeave(true);
                    employee.setAnnualLeave(employee.getAnnualLeave() - 1);
                    return ResponseEntity.status(HttpStatus.OK).body(new ApiEmployee("Success annual leave"));
                }
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ApiEmployee("Employee cannot apply for leave"));
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiEmployee("Employee not found"));
    }

    //Get Employees with No Annual Leave
    @GetMapping("/getEmployeesWithNoAnnualLeave")
    public ResponseEntity<List<Employee>> getEmployeesWithNoAnnualLeave() {
        List<Employee> leave = new ArrayList<>();
        for (Employee emp : employees) {
            if (emp.getAnnualLeave() == 0) {
                leave.add(emp);
            }
        }
        return ResponseEntity.ok(leave);
    }

    //Promote Employee to Supervisor
    @PutMapping("/promoteEmployeeToSupervisor/{id}")
    public ResponseEntity<ApiEmployee> promoteEmployeeToSupervisor(@PathVariable String id) {
        for (Employee employee : employees) {
            if (employee.getId().equals(id)) {
                if (employee.getAge() >= 30 && !employee.isOnLeave() && employee.getPosition().equalsIgnoreCase("coordinator")) {
                    employee.setPosition("supervisor");
                    return ResponseEntity.ok(new ApiEmployee("Employee promoted to supervisor"));
                }
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ApiEmployee("Employee cannot be promoted"));
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiEmployee("Employee not found"));
    }
}


