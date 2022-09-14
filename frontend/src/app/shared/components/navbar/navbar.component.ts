import { Component, OnInit } from '@angular/core';
import { AuthStore } from 'src/app/auth/data-access/auth.store';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss'],
})
export class NavbarComponent implements OnInit {
  constructor(public authStore: AuthStore) {}

  ngOnInit(): void {}
}
