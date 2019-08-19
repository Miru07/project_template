import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {User} from "../../models/user.model";

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.scss']
})
export class UserComponent implements OnInit {

  @Input()
  public user: User;

  @Output()
  public output = new EventEmitter<User>();

  constructor() { }

  ngOnInit() {
  }

  get isBatman(){
    return !(this.user.firstname == "Batman" || this.user.lastname == "Batman");
  }

  alertUser(person: User){
    this.output.emit(person);
  }

}
