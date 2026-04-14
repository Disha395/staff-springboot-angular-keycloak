package com.college.management.service;

import com.college.management.entity.Staff;
import com.college.management.exception.ResourceNotFoundException;
import com.college.management.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StaffServiceImpl implements StaffService {

    @Autowired
    private StaffRepository staffRepository;

    @Override
    public List<Staff> getAllStaff() {
        return staffRepository.findAll();
    }


    @Override
    public Staff createStaff(Staff staff) {
        return staffRepository.save(staff);
    }

    @Override
    public Staff updateStaff(Long id, Staff staff) {
        if (!staffRepository.existsById(id)) {
            throw new ResourceNotFoundException("Staff not found with id: " + id);
        }
        staff.setStaffId(id);
        return staffRepository.save(staff);
    }

    @Override
    public List<Staff> getStaffByDepartment(Long departmentId) {
        List<Staff> staffList = staffRepository.findByDepartmentId(departmentId);
        if (staffList.isEmpty()) {
            throw new ResourceNotFoundException("No staff found in department with id: " + departmentId);
        }
        return staffList;
    }

    @Override
    public List<Staff> getStaffByMinimumSalary(Double minSalary) {
        List<Staff> staffList = staffRepository.findByMinimumSalary(minSalary);
        if (staffList.isEmpty()) {
            throw new ResourceNotFoundException("No staff found with salary greater than or equal to: " + minSalary);
        }
        return staffList;
    }

    @Override
    public List<Staff> searchStaffByName(String name) {
        List<Staff> staffList = staffRepository.findByStaffNameContainingIgnoreCase(name);
        if (staffList.isEmpty()) {
            throw new ResourceNotFoundException("No staff found with name containing: " + name);
        }
        return staffList;
    }

    @Override
public Optional<Staff> getStaffById(Long id) {
    Optional<Staff> staff = staffRepository.findById(id);
    if (staff.isEmpty()) {
        throw new ResourceNotFoundException("Staff not found with id: " + id);
    }
    return staff;
   }

   @Override
public void deleteStaff(Long id) {
    if (!staffRepository.existsById(id)) {
        throw new ResourceNotFoundException("Staff not found with id: " + id);
    }
    staffRepository.deleteById(id);
}

}
