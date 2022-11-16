import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { Subscription, take } from 'rxjs';
import { AuthStore } from '../../../auth/data-access/auth.store';
import { SnackbarService } from '../../service/snackbar.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss'],
})
export class NavbarComponent {
  constructor(public authStore: AuthStore, private router: Router, private snackbarService: SnackbarService) {}

  logoutSubscription!: Subscription;

  logout() {
    this.authStore
      .logout()
      .pipe(take(1))
      .subscribe({
        next: () => this.router.navigateByUrl(''),
        error: () => this.snackbarService.openSnackBar('Unable to logout'),
      });
  }
}
