import { Injectable } from '@angular/core';
import { keycloakConfig } from '../keycloak.config';

declare var Keycloak: any;

@Injectable({ providedIn: 'root' })
export class KeycloakService {
  private keycloak: any;

  /**
   * Called once at app startup (in APP_INITIALIZER).
   * Initializes the Keycloak adapter in check-sso mode so the app loads
   * even when the user is not yet logged in and redirects to Keycloak
   * login/register as needed.
   */
  init(): Promise<void> {
    return new Promise((resolve, reject) => {
      this.keycloak = new Keycloak({
        url: keycloakConfig.url,
        realm: keycloakConfig.realm,
        clientId: keycloakConfig.clientId
      });

      this.keycloak
        .init({
          onLoad: 'check-sso',
          silentCheckSsoRedirectUri:
            window.location.origin + '/assets/silent-check-sso.html',
          pkceMethod: 'S256'
        })
        .then((authenticated: boolean) => {
          console.log('Keycloak init — authenticated:', authenticated);
          // Start token auto-refresh every 60s
          if (authenticated) {
            setInterval(() => {
              this.keycloak.updateToken(70).catch(() => this.logout());
            }, 60000);
          }
          resolve();
        })
        .catch((err: any) => {
          console.error('Keycloak init failed', err);
          reject(err);
        });
    });
  }

  /** Redirect to Keycloak login page */
  login(): void {
    this.keycloak.login({ redirectUri: window.location.origin + '/dashboard' });
  }

  /** Redirect to Keycloak registration page */
  register(): void {
    this.keycloak.register({ redirectUri: window.location.origin + '/dashboard' });
  }

  /** Log the user out and redirect to login page */
  logout(): void {
    this.keycloak.logout({ redirectUri: window.location.origin + '/auth/login' });
  }

  isLoggedIn(): boolean {
    return !!this.keycloak?.authenticated;
  }

  /** Returns the raw JWT access token string */
  getToken(): string {
    return this.keycloak?.token || '';
  }

  getUsername(): string {
    return this.keycloak?.tokenParsed?.preferred_username || '';
  }

  getEmail(): string {
    return this.keycloak?.tokenParsed?.email || '';
  }

  getFullName(): string {
    return this.keycloak?.tokenParsed?.name || '';
  }
}