import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { StaffService } from '../../core/services/staff.service';
import { Staff } from '../../core/models/staff.model';

@Component({
  selector: 'app-staff-list',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './staff-list.component.html'
})
export class StaffListComponent implements OnInit {
  staffList: Staff[] = [];
  filteredList: Staff[] = [];
  loading = true;
  error = '';
  successMsg = '';

  // Search / filter
  searchName = '';
  filterDeptId: number | null = null;
  filterMinSalary: number | null = null;

  // Add/Edit modal
  showModal = false;
  isEditMode = false;
  modalError = '';
  formData: Staff = { staffName: '', departmentId: 0, salary: 0 };
  editingId: number | null = null;

  // Delete confirm modal
  showDeleteModal = false;
  deletingId: number | null = null;
  deletingName = '';

  constructor(private staffService: StaffService) {}

  ngOnInit(): void {
    this.loadAll();
  }

  loadAll() {
    this.loading = true;
    this.error = '';
    this.staffService.getAllStaff().subscribe({
      next: (data) => {
        this.staffList = data;
        this.filteredList = data;
        this.loading = false;
      },
      error: () => {
        this.error = 'Failed to load staff. Make sure the Spring Boot backend is running on port 8081.';
        this.loading = false;
      }
    });
  }

  // ---- Search & Filter ----
  applySearch() {
    const name = this.searchName.trim().toLowerCase();
    this.filteredList = this.staffList.filter(s =>
      (!name || s.staffName.toLowerCase().includes(name)) &&
      (!this.filterDeptId || s.departmentId === Number(this.filterDeptId)) &&
      (!this.filterMinSalary || s.salary >= Number(this.filterMinSalary))
    );
  }

  searchByNameFromServer() {
    if (!this.searchName.trim()) { this.loadAll(); return; }
    this.loading = true;
    this.staffService.searchStaffByName(this.searchName.trim()).subscribe({
      next: (data) => { this.filteredList = data; this.staffList = data; this.loading = false; },
      error: () => { this.filteredList = []; this.loading = false; }
    });
  }

  filterByDept() {
    if (!this.filterDeptId) { this.loadAll(); return; }
    this.loading = true;
    this.staffService.getStaffByDepartment(Number(this.filterDeptId)).subscribe({
      next: (data) => { this.filteredList = data; this.staffList = data; this.loading = false; },
      error: () => { this.filteredList = []; this.loading = false; }
    });
  }

  filterBySalary() {
    if (!this.filterMinSalary) { this.loadAll(); return; }
    this.loading = true;
    this.staffService.getStaffByMinimumSalary(Number(this.filterMinSalary)).subscribe({
      next: (data) => { this.filteredList = data; this.staffList = data; this.loading = false; },
      error: () => { this.filteredList = []; this.loading = false; }
    });
  }

  clearFilters() {
    this.searchName = '';
    this.filterDeptId = null;
    this.filterMinSalary = null;
    this.loadAll();
  }

  // ---- Add ----
  openAddModal() {
    this.isEditMode = false;
    this.formData = { staffName: '', departmentId: 0, salary: 0 };
    this.modalError = '';
    this.editingId = null;
    this.showModal = true;
  }

  // ---- Edit ----
  openEditModal(staff: Staff) {
    this.isEditMode = true;
    this.formData = { ...staff };
    this.editingId = staff.staffId!;
    this.modalError = '';
    this.showModal = true;
  }

  // ---- Save (create or update) ----
  saveStaff() {
  if (!this.formData.staffName || this.formData.staffName.trim().length < 3) {
    this.modalError = 'Name must be at least 3 characters.';
    return;
  }

  if (!this.formData.departmentId || this.formData.departmentId <= 0) {
    this.modalError = 'Department ID must be valid.';
    return;
  }

  if (!this.formData.salary || this.formData.salary <= 0) {
    this.modalError = 'Salary must be greater than 0.';
    return;
  }

  this.modalError = '';

  if (this.isEditMode && this.editingId !== null) {
    this.staffService.updateStaff(this.editingId, this.formData).subscribe({
      next: () => {
        this.showModal = false;
        this.showSuccess('Staff updated successfully!');
        this.loadAll();
      },
      error: (err) => {
        this.modalError = err?.error?.message || 'Update failed.';
      }
    });
  } else {
    this.staffService.createStaff(this.formData).subscribe({
      next: () => {
        this.showModal = false;
        this.showSuccess('Staff added successfully!');
        this.loadAll();
      },
      error: (err) => {
        this.modalError = err?.error?.message || 'Create failed.';
      }
    });
  }
}

  // ---- Delete ----
  openDeleteModal(staff: Staff) {
    this.deletingId = staff.staffId!;
    this.deletingName = staff.staffName;
    this.showDeleteModal = true;
  }

  confirmDelete() {
    if (this.deletingId === null) return;
    this.staffService.deleteStaff(this.deletingId).subscribe({
      next: () => {
        this.showDeleteModal = false;
        this.showSuccess('Staff deleted successfully!');
        this.loadAll();
      },
      error: () => {
        this.showDeleteModal = false;
        this.error = 'Delete failed. Please try again.';
      }
    });
  }

  showSuccess(msg: string) {
    this.successMsg = msg;
    setTimeout(() => this.successMsg = '', 3000);
  }
}