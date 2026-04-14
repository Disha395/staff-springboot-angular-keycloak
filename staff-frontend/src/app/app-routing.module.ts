import { Routes } from '@angular/router';
import { authGuard } from './core/guards/auth.guard';

export const routes: Routes = [
  { path: '', redirectTo: '/dashboard', pathMatch: 'full' },
  {
    path: 'auth',
    children: [
      {
        path: 'login',
        loadComponent: () =>
          import('./auth/components/auth/login/login.component').then(m => m.LoginComponent)
      },
      {
        path: 'register',
        loadComponent: () =>
          import('./auth/components/auth/register/register.component').then(m => m.RegisterComponent)
      }
    ]
  },
  {
    path: 'dashboard',
    canActivate: [authGuard],
    loadComponent: () =>
      import('./dashboard/dashboard.component').then(m => m.DashboardComponent)
  },
  {
    path: 'staff',
    canActivate: [authGuard],
    loadComponent: () =>
      import('./staff/staff-list/staff-list.component').then(m => m.StaffListComponent)
  },
  {
    path: 'profiles',
    canActivate: [authGuard],
    loadComponent: () =>
      import('./profiles/profiles.component').then(m => m.ProfilesComponent)
  },
  { path: '**', redirectTo: '/dashboard' }
];
