import { Component } from '@angular/core';
import { KeycloakService } from '../../../../core/services/keycloak.service';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [RouterLink],
  templateUrl: './register.component.html'
})
export class RegisterComponent {
  constructor(private keycloak: KeycloakService) {}

  register() {
    this.keycloak.register();
  }
}
