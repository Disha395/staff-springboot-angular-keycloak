package com.college.management.service;

import com.college.management.entity.Staff;
import java.util.List;
import java.util.Optional;

public interface StaffService {

    List<Staff> getAllStaff();

    Optional<Staff> getStaffById(Long id);

    Staff createStaff(Staff staff);

    Staff updateStaff(Long id, Staff staff);

    void deleteStaff(Long id);

    List<Staff> getStaffByDepartment(Long departmentId);

    List<Staff> getStaffByMinimumSalary(Double minSalary);

    List<Staff> searchStaffByName(String name);
}