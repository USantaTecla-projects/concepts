import { Component, OnInit } from '@angular/core';
import { map, Observable } from 'rxjs';
import { AuthStore } from 'src/app/auth/data-access/auth.store';
import { Role } from 'src/app/auth/data-access/enum/role.enum';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss'],
})
export class NavbarComponent implements OnInit {
  constructor(public authStore: AuthStore) {}

  ngOnInit(): void {}
}
