import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import {UserComponent} from "./components/user/user.component";
import {UserListComponent} from "./components/user-list/user-list.component";
import { JumperDirective } from './directives/jumper.directive';
import { AuthorizationDirective } from './directives/authorization.directive';
import {UserServiceService} from "./services/user-service.service";
import {BackendModule} from "../core/backend/backend.module";

@NgModule({
  declarations: [
    UserComponent,
    UserListComponent,
    JumperDirective,
    AuthorizationDirective,
  ],
  imports: [
    CommonModule,
    BackendModule
  ],
  exports:[
    UserListComponent
  ],
  providers:[
    UserServiceService
  ]
})
export class UserModule { }
