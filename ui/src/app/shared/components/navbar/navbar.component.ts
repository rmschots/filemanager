import { Component } from '@angular/core';
import { UserService } from '../../services/user.service';

@Component({
  selector: 'fm-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent {
  constructor(private _userService: UserService) {
  }

  get isLoggedIn$() {
    return this._userService.isLoggedIn();
  }

  get user$() {
    return this._userService.getUser();
  }
}
