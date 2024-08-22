import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { LoginService } from "../../login/service/login.service";
import { User } from "../model/user.model";
import { Router } from "@angular/router";

@Injectable({
  providedIn: "root"
})
export class RegistrationService{

  private postRegistrationApi:string
  private getUsersApi:string


  constructor(private http: HttpClient,
    private loginService:LoginService){
    this.postRegistrationApi="http://localhost:1000/profile-service/api/user";
    this.getUsersApi="http://localhost:1000/profile-service/api/user";

  }

  public postRegistration(user:User):Observable<User>{
    return this.http.post<User>(this.postRegistrationApi,user)
   

  }

  public getUsers():Observable<User[]>{
    return this.http.get<User[]>(this.getUsersApi,{headers: { authorization: this.loginService.createBasicAuthToken(this.loginService.username, this.loginService.password) } });
  }


}
