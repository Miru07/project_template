import {Component, Input, ViewChild} from '@angular/core';
import {TestUser, User} from "./user/models/user.model";
import {Roles} from './user/models/roles.model';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'jbug-web';
  subtitle = 'M';

  @ViewChild('userListComponent', {static : false})
  public inputViewChild;

  public users: User[];

  public testUser: TestUser;

  // public users: User[] =[
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
  //   },
  //   {
  //     firstname: "c",
  //     lastname: "b",
  //     age: 21,
  //     role: [Roles.create_bug, Roles.close_bug]
  //   }
  // ];

  public colors: string[] =[
    "red", "green", "blue", "yellow"
  ];

  alertComponent(){
    alert(this.inputViewChild.tetsUser);
  }
}
