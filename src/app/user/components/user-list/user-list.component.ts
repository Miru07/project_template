import {Component, Input, OnInit} from '@angular/core';
import {TestUser, User} from "../../models/user.model";
import {UserServiceService} from "../../services/user-service.service";

@Component({
  selector: 'app-user-list',
  templateUrl: './user-list.component.html',
  styleUrls: ['./user-list.component.scss']
})
export class UserListComponent implements OnInit {

  @Input()
  public userlist: User[];

  @Input()
  public colorlist: string[];

  @Input()
  public testUser: TestUser;

  public smth: number = 5;

  constructor(private userService: UserServiceService) { }

  ngOnInit() {
    this.userService.getAllUsers()
      .subscribe((testU) => this.testUser = testU);
  }

  alertUserFromList(user: User) {
    alert(user.firstname);

  }
}
