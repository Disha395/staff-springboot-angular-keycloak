package com.college.management.repository;

import com.college.management.entity.Staff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Long> {

    // Custom query methods
    List<Staff> findByDepartmentId(Long departmentId);

    @Query("SELECT s FROM Staff s WHERE s.salary >= :minSalary")
    List<Staff> findByMinimumSalary(@Param("minSalary") Double minSalary);

    List<Staff> findByStaffNameContainingIgnoreCase(String name);
}