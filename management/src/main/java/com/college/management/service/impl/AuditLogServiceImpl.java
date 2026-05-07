
package com.college.management.service.impl;

import com.college.management.entity.AuditLog;
import com.college.management.repository.AuditLogRepository;
import com.college.management.service.AuditLogService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuditLogServiceImpl implements AuditLogService {

    @Autowired
    private AuditLogRepository auditRepo;

    @Override
    public void saveLog(String adminName, String action, String staffName) {

        AuditLog log = new AuditLog();

        log.setAdminName(adminName);
        log.setAction(action);
        log.setStaffName(staffName);
        log.setTimestamp(LocalDateTime.now());

        auditRepo.save(log);
    }
}
