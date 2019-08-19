import {Injectable} from '@angular/core';
import {TestUser, User} from "../models/user.model";
import {Roles} from "../models/roles.model";
import {Observable, of} from "rxjs";
import {delay} from "rxjs/operators";
import {BackendService} from "../../core/backend/services/backend.service";

@Injectable({
  providedIn: 'root'
})
export class UserServiceService {

  constructor(private backendServices: BackendService) { }

  public hasRole(user: User): boolean{

    return user.role.includes(Roles.close_bug);
  }

  public getAllUsers(): Observable<TestUser>{
    // return of([
    //   {
    //     firstname: "a",
    //     lastname: "a",
    //     age: 19,
    //     role: [Roles.edit_bug, Roles.close_bug]
    //   },
    //   {
    //     firstname: "b",
    //     lastname: "b",
    //     age: 20,
    //     role: [Roles.create_bug]
    //   }
    //   ]).pipe(delay(5000));


    return this.backendServices.get('http://localhost:8080/myapp/users');
  }
}
