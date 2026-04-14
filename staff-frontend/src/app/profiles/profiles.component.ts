import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { StaffService } from '../core/services/staff.service';
import { Staff } from '../core/models/staff.model';

@Component({
  selector: 'app-profiles',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './profiles.component.html'
})
export class ProfilesComponent implements OnInit {
  staffList: Staff[] = [];
  loading = true;
  error = '';
  searchId: number | null = null;
  selectedStaff: Staff | null = null;
  searchError = '';

  constructor(private staffService: StaffService) {}

  ngOnInit(): void {
    this.staffService.getAllStaff().subscribe({
      next: (data) => { this.staffList = data; this.loading = false; },
      error: () => { this.error = 'Could not load staff profiles.'; this.loading = false; }
    });
  }

  searchById() {
    if (!this.searchId) { this.searchError = 'Please enter an ID.'; return; }
    this.searchError = '';
    this.selectedStaff = null;
    this.staffService.getStaffById(Number(this.searchId)).subscribe({
      next: (data) => { this.selectedStaff = data; },
      error: () => { this.searchError = `No staff found with ID ${this.searchId}.`; }
    });
  }

  selectProfile(staff: Staff) {
    this.selectedStaff = staff;
    this.searchId = staff.staffId!;
    window.scrollTo({ top: 0, behavior: 'smooth' });
  }
}
