package com.college.management.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity
@Table(name = "staff")
@Schema(description = "Staff entity representing a college staff member")
public class Staff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "staff_id")
    @Schema(description = "Unique identifier of the staff member", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long staffId;

    @NotBlank(message = "Staff name is required")
    @Column(name = "staff_name", nullable = false)
    @Schema(description = "Full name of the staff member", example = "John Doe", required = true)
    private String staffName;

    @NotNull(message = "Department ID is required")
    @Column(name = "department_id", nullable = false)
    @Schema(description = "Department ID where staff member works", example = "101", required = true)
    private Long departmentId;

    @NotNull(message = "Salary is required")
    @Positive(message = "Salary must be positive")
    @Column(name = "salary", nullable = false)
    @Schema(description = "Monthly salary of the staff member", example = "50000.00", required = true)
    private Double salary;

    // Default constructor
    public Staff() {}

    // Constructor with parameters
    public Staff(String staffName, Long departmentId, Double salary) {
        this.staffName = staffName;
        this.departmentId = departmentId;
        this.salary = salary;
    }

    // Getters and Setters
    public Long getStaffId() {
        return staffId;
    }

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Staff{" +
                "staffId=" + staffId +
                ", staffName='" + staffName + '\'' +
                ", departmentId=" + departmentId +
                ", salary=" + salary +
                '}';
    }
}