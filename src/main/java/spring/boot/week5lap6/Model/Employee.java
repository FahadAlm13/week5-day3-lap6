package spring.boot.week5lap6.Model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Employee {
    @NotEmpty(message = "ID cannot be null")
    @Size(min = 3, message = "ID must be more than 2 characters")
    private String id;

    @NotEmpty(message = "Name cannot be null")
    @Size(min = 5, message = "Name must be more than 4 characters")
    @Pattern(regexp = "^[A-Za-z ]+$", message = "Name must contain only characters")
    private String name;

    @NotEmpty(message = "Email cannot be null")
    @Email(message = "Must be a valid email format")
    private String email;

    @NotEmpty(message = "Phone number cannot be null")
    @Pattern(regexp = "^05\\d{8}$", message = "Phone number must start with '05' and be exactly 10 digits long")
    private String phoneNumber;

    @NotNull(message = "Age cannot be null")
    @Min(value = 26, message = "Age must be more than 25")
    private int age;

    @NotEmpty(message = "Position cannot be null")
    @Pattern(regexp = "^(supervisor|coordinator)$", message = "Position must be either 'supervisor' or 'coordinator'")
    private String position;

    @AssertFalse(message = "The employee cannot be on leave initially")
    private boolean onLeave ;

    @NotNull(message = "Hire date cannot be null")
    @PastOrPresent(message = "Hire date should be a date in the past or the present")
    private LocalDate hireDate;

    @NotNull(message = "Annual leave cannot be null")
    @Min(value = 1, message = "Annual leave must be a positive number")
    private int annualLeave;
}
