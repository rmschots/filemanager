import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { Observable } from 'rxjs/Observable';
import { UserService } from '../services/user.service';

@Injectable()
export class LoggedInGuard implements CanActivate {

  constructor(private _userService: UserService,
              private _router: Router) {
  }

  canActivate(): Observable<boolean> {
    this._userService.fetchUser();

    return this._userService.isLoggedIn()
      .do((result) => {
        if (!result) {
          this._router.navigate(['/login']);
        }
      });
  }

}
