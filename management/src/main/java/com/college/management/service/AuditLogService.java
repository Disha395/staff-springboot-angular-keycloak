
package com.college.management.service;

import com.college.management.entity.AuditLog;

public interface AuditLogService {

    void saveLog(String adminName, String action, String staffName);
}
