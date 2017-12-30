import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { User } from '../models/user';
import { BehaviorSubject } from 'rxjs/BehaviorSubject';

@Injectable()
export class UserService {

  private _currentUser$: BehaviorSubject<User> = new BehaviorSubject<User>(null);
  private _isUserFetched$: BehaviorSubject<boolean> = new BehaviorSubject<boolean>(false);
  private _isFetchingUser = false;

  constructor(private _http: HttpClient) {
  }

  fetchUser() {
    if (!this._isFetchingUser && !this._isUserFetched$.getValue()) {
      this._isFetchingUser = true;
      this._http.get<User>(`/api/users`)
        .map((data: User) => User.fromJSON(data)).take(1)
        .subscribe(user => {
            this._currentUser$.next(user);
            this._isUserFetched$.next(true);
          },
          error => {
            this._isUserFetched$.next(true);
            this._currentUser$.next(null);
          });
    }
  }

  isLoggedIn(): Observable<boolean> {
    return this.getUser().map(user => !!user);
  }

  getUser(): Observable<User> {
    return this._isUserFetched$
      .filter(isFetched => isFetched)
      .switchMap(() => this._currentUser$);
  }

  currentUserValue() {
    return this._currentUser$.getValue();
  }
}
