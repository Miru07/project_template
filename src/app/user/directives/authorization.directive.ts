import {AfterViewInit, Directive, ElementRef, Input} from '@angular/core';
import {Roles} from "../models/roles.model";
import {UserServiceService} from "../services/user-service.service";
import {User} from "../models/user.model";

@Directive({
  selector: '[appAuthorization]',
})
export class AuthorizationDirective implements AfterViewInit{

  @Input()
  roles : Roles[];

  @Input()
  user: User;

  constructor(private el: ElementRef, private userService: UserServiceService) { }

  ngAfterViewInit(): void {

    if(this.userService.hasRole(this.user) == false)
      this.el.nativeElement.disabled = true;
  }
}
