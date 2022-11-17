import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { User } from '../types/auth/model/user.model';

@Injectable({
  providedIn: 'root',
})
export class RoleGuard implements CanActivate {
  constructor(private router: Router) {}

  canActivate(
    route: ActivatedRouteSnapshot
  ): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    const authData = localStorage.getItem('auth_data');
    const { role: requiredRole } = route.data;
    if (authData) {
      const user: User = JSON.parse(authData);
      const hasRequiredRole = user.roles.findIndex(role => role === requiredRole);
      return hasRequiredRole !== -1 ? true : this.router.navigateByUrl('');
    }
    return this.router.navigateByUrl('/auth');
  }
}
