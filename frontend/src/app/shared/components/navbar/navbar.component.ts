import { Component } from '@angular/core';
import { AuthStore } from '../../../auth/data-access/auth.store';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss'],
})
export class NavbarComponent {
  constructor(public authStore: AuthStore) {}
}
