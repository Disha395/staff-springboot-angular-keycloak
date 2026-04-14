import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Staff } from '../models/staff.model';

@Injectable({
  providedIn: 'root'
})
export class StaffService {
  private apiUrl = 'http://localhost:8081/api/staff';

  constructor(private http: HttpClient) {}

  getAllStaff(): Observable<Staff[]> {
    return this.http.get<Staff[]>(this.apiUrl);
  }

  getStaffById(id: number): Observable<Staff> {
    return this.http.get<Staff>(`${this.apiUrl}/${id}`);
  }

  createStaff(staff: Staff): Observable<Staff> {
    return this.http.post<Staff>(this.apiUrl, staff);
  }

  updateStaff(id: number, staff: Staff): Observable<Staff> {
    return this.http.put<Staff>(`${this.apiUrl}/${id}`, staff);
  }

  deleteStaff(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }

  getStaffByDepartment(departmentId: number): Observable<Staff[]> {
    return this.http.get<Staff[]>(`${this.apiUrl}/department/${departmentId}`);
  }

  getStaffByMinimumSalary(minSalary: number): Observable<Staff[]> {
    return this.http.get<Staff[]>(`${this.apiUrl}/salary/${minSalary}`);
  }

  searchStaffByName(name: string): Observable<Staff[]> {
    const params = new HttpParams().set('name', name);
    return this.http.get<Staff[]>(`${this.apiUrl}/search`, { params });
  }
}
