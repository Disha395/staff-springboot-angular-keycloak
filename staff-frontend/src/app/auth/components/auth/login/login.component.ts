import { Component } from '@angular/core';
import { KeycloakService } from '../../../../core/services/keycloak.service';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './login.component.html'
})
export class LoginComponent {
  constructor(private keycloak: KeycloakService) {}

  login() {
    this.keycloak.login();
  }
}
