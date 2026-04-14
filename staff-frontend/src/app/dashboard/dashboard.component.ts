import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { StaffService } from '../core/services/staff.service';
import { KeycloakService } from '../core/services/keycloak.service';
import { Staff } from '../core/models/staff.model';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './dashboard.component.html'
})
export class DashboardComponent implements OnInit {
  staffList: Staff[] = [];
  loading = true;
  error = '';

  get totalStaff() { return this.staffList.length; }
  get totalDepartments() {
    return new Set(this.staffList.map(s => s.departmentId)).size;
  }
  get avgSalary() {
    if (!this.staffList.length) return 0;
    return (this.staffList.reduce((sum, s) => sum + s.salary, 0) / this.staffList.length).toFixed(2);
  }
  get highestSalary() {
    if (!this.staffList.length) return 0;
    return Math.max(...this.staffList.map(s => s.salary));
  }

  constructor(
    private staffService: StaffService,
    public keycloak: KeycloakService
  ) {}

  ngOnInit(): void {
    this.staffService.getAllStaff().subscribe({
      next: (data) => { this.staffList = data; this.loading = false; },
      error: () => {
        this.error = 'Could not connect to backend. Make sure Spring Boot is running on port 8081.';
        this.loading = false;
      }
    });
  }
}
