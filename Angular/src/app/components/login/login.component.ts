import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

import { AppService } from 'src/app/app.service';
import { Cart } from '../cart/model/cart.model';
import { CartService } from '../cart/service/cart.service';

import { RegistrationService } from '../register/service/registration.service';
import { User } from './model/user.model';
import { LoginService } from './service/login.service';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  users:User[];
  logInForm:FormGroup;
  user:User;
  errorMsg: string;
  uid:string;
  role:string;

  isLoggedIn:boolean;
  userName:string;
  successMessage: string;
  invalidLogin:boolean = false;
  loginSuccess:boolean = false;
  password : string;


  constructor(private registrationService:RegistrationService,
    private appService:AppService,private router:Router,
  private loginService:LoginService) {


      this.user={
      }
      this.isLoggedIn=false;this.errorMsg="";
      this.userName="";
      this.password="";
      this.successMessage="";

    }

  ngOnInit(): void {



    this.registrationService.getUsers().subscribe(data=>{
      this.users=data;
    });

    this.logInForm=new FormGroup({

      username:new FormControl('',Validators.required),
      password:new FormControl('',Validators.required)
    });
  }

  onPostLogin(){


let username = this.logInForm.value.username;
let password = this.logInForm.value.password;

this.loginService.authenticationService(username,password).subscribe((result)=> {
  this.invalidLogin = false;
  this.loginSuccess = true;
  this.successMessage = 'Login Successful.';
  console.log("data");
  this.setData(username,password);
  this.appService.loggedIn.next(true);

}, () => {
  alert("Invalid creditionals")
  console.log("error");
  this.invalidLogin = true;
  this.loginSuccess = false;
});




}
setData(username:string,password:string){
  if(this.loginSuccess){
    this.loginService.getUserByUsername(username).subscribe(data=>{
      this.user=data;
      this.localSet(username,password);
      this.appService.loggedIn.next(true);
    });

  }
  else{
    alert("invalid")
      this.errorMsg="Invalid creditionls"
  }
}

localSet(username:string,password:string){

  this.isLoggedIn=true;
  this.appService.loggedIn.next(true);


  this.role=this.user.role;
  if(this.role=="Merchant"){
    localStorage.setItem("isMerchant","true");
    this.appService.isMerchant.next(true);
  }else{
    localStorage.setItem("isMerchant","false");
    this.appService.isMerchant.next(false);
  }



  if(this.user.role=="Merchant"){
    this.appService.isMerchant.next(true);
  }

  let token = btoa(username + ":" + password);
  localStorage.setItem("token",token);
  localStorage.setItem("isLoggedIn","true");
  localStorage.setItem("userId",this.user.id);
  localStorage.setItem("userName",this.user.userName);
  localStorage.setItem("userRole",this.user.role);
  this.appService.loggedIn.next(true);
  alert("user Logged In");
  this.router.navigateByUrl("/");

}

}
