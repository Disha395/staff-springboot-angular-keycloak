package com.college.management.controller;

import com.college.management.entity.Staff;
import com.college.management.service.StaffService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

//audit
import com.college.management.service.AuditLogService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@RestController
@RequestMapping("/api/staff")

@Tag(name = "Staff Management", description = "APIs for managing staff members in the college system")
public class StaffController {

    private static final Logger logger = LoggerFactory.getLogger(StaffController.class);

    @Autowired
    private StaffService staffService;

    @Autowired
private AuditLogService auditLogService;

    // ==================== GET ALL STAFF ====================
    @Operation(summary = "Get all staff members",
            description = "Retrieves a list of all staff members in the system")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved all staff members",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Staff.class)))
    @GetMapping
    public ResponseEntity<List<Staff>> getAllStaff() {
        logger.info("Fetching all staff members");
        List<Staff> staffList = staffService.getAllStaff();
        return ResponseEntity.ok(staffList);
    }

    // ==================== GET STAFF BY ID ====================
    @Operation(summary = "Get staff by ID",
            description = "Retrieves a specific staff member by their unique ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Staff member found"),
            @ApiResponse(responseCode = "404", description = "Staff member not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Staff> getStaffById(
            @Parameter(description = "ID of the staff member to retrieve", required = true)
            @PathVariable Long id) {
        logger.info("Fetching staff with ID: {}", id);
        Optional<Staff> staff = staffService.getStaffById(id);
        return ResponseEntity.ok(staff.get());
    }


    // ==================== CREATE NEW STAFF ====================
    @Operation(summary = "Create new staff member",
            description = "Creates a new staff member in the system")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Staff member created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping
    public ResponseEntity<Staff> createStaff(
            @Parameter(description = "Staff member details", required = true)
            @Valid @RequestBody Staff staff) {
        logger.info("Creating new staff: {}", staff.getStaffName());
        Staff createdStaff = staffService.createStaff(staff);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

String adminName = auth.getName();

auditLogService.saveLog(
        adminName,
        "ADDED STAFF",
        createdStaff.getStaffName()
);

        return new ResponseEntity<>(createdStaff, HttpStatus.CREATED);
    }

    // ==================== UPDATE STAFF ====================
    @Operation(summary = "Update staff member",
            description = "Updates an existing staff member's information")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Staff member updated successfully"),
            @ApiResponse(responseCode = "404", description = "Staff member not found"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Staff> updateStaff(
            @Parameter(description = "ID of the staff member to update", required = true)
            @PathVariable Long id,
            @Valid @RequestBody Staff staff) {
        logger.info("Updating staff with ID: {}", id);
        Staff updatedStaff = staffService.updateStaff(id, staff);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

String adminName = auth.getName();

auditLogService.saveLog(
        adminName,
        "UPDATED STAFF",
        updatedStaff.getStaffName()
);

        return ResponseEntity.ok(updatedStaff);
    }

    // ==================== DELETE STAFF ====================
    @Operation(summary = "Delete staff member",
            description = "Deletes a staff member from the system")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Staff member deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Staff member not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStaff(
            @Parameter(description = "ID of the staff member to delete", required = true)
            @PathVariable Long id) {
        logger.info("Deleting staff with ID: {}", id);

        Optional<Staff> staff = staffService.getStaffById(id);

Authentication auth = SecurityContextHolder.getContext().getAuthentication();

String adminName = auth.getName();

if (staff.isPresent()) {
    auditLogService.saveLog(
            adminName,
            "DELETED STAFF",
            staff.get().getStaffName()
    );
}

        staffService.deleteStaff(id);
        return ResponseEntity.noContent().build();
    }

    // ==================== GET STAFF BY DEPARTMENT ====================
    @Operation(summary = "Get staff by department",
            description = "Retrieves all staff members belonging to a specific department")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved staff by department")
    @GetMapping("/department/{departmentId}")
    public ResponseEntity<List<Staff>> getStaffByDepartment(
            @Parameter(description = "Department ID to filter staff", required = true)
            @PathVariable Long departmentId) {
        logger.info("Fetching staff for department ID: {}", departmentId);
        List<Staff> staffList = staffService.getStaffByDepartment(departmentId);
        return ResponseEntity.ok(staffList);
    }

    // ==================== GET STAFF BY MINIMUM SALARY ====================
    @Operation(summary = "Get staff by minimum salary",
            description = "Retrieves all staff members with salary greater than or equal to specified amount")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved staff by salary")
    @GetMapping("/salary/{minSalary}")
    public ResponseEntity<List<Staff>> getStaffByMinimumSalary(
            @Parameter(description = "Minimum salary threshold", required = true)
            @PathVariable Double minSalary) {
        logger.info("Fetching staff with minimum salary: {}", minSalary);
        List<Staff> staffList = staffService.getStaffByMinimumSalary(minSalary);
        return ResponseEntity.ok(staffList);
    }

    // ==================== SEARCH STAFF BY NAME ====================
    @Operation(summary = "Search staff by name",
            description = "Searches for staff members by name (case-insensitive partial match)")
    @ApiResponse(responseCode = "200", description = "Successfully retrieved matching staff members")
    @GetMapping("/search")
    public ResponseEntity<List<Staff>> searchStaffByName(
            @Parameter(description = "Name to search for (partial match supported)", required = true)
            @RequestParam String name) {
        logger.info("Searching staff by name: {}", name);
        List<Staff> staffList = staffService.searchStaffByName(name);
        return ResponseEntity.ok(staffList);
    }
}
