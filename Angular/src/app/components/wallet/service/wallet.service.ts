import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { LoginService } from '../../login/service/login.service';
import { OrderStatement } from '../model/orderstatement.model';
import { wallet } from '../model/wallet.model';


const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

@Injectable({
  providedIn: 'root'
})
export class WalletServiceService {

  getWalletApi:string;
  createWalletApi:string;
  addMoneyApi:string;
  payByWalletApi:string;
  getStatementsApi:string;


  constructor(private http:HttpClient,private loginService:LoginService) {
    this.getWalletApi="http://localhost:1000/wallet-service/api/wallet/user/";
    this.createWalletApi="http://localhost:1000/wallet-service/api/wallet/";
    this.addMoneyApi="http://localhost:1000/wallet-service/api/add/wallet/";
    this.payByWalletApi="http://localhost:1000/wallet-service/api/pay/byWallet/";
    this.getStatementsApi="http://localhost:1000/wallet-service/api/payment/history/";

   }

  getWallet():Observable<wallet>{
    let userId = localStorage.getItem('userId');
    return this.http.get<wallet>(this.getWalletApi+userId,{headers: { authorization: this.loginService.createBasicAuthToken(this.loginService.username, this.loginService.password) } });
  }

  createWallet():Observable<wallet>{
    let userId = localStorage.getItem('userId');
    return this.http.post<wallet>(this.createWalletApi+userId,{currentBalance:0},{headers: { authorization: this.loginService.createBasicAuthToken(this.loginService.username, this.loginService.password) } });
  }
  addMoney(walletId:string,amount:number):Observable<wallet>{
    return this.http.post<wallet>(this.addMoneyApi+walletId+"/"+amount,{},{headers: { authorization: this.loginService.createBasicAuthToken(this.loginService.username, this.loginService.password) } });
  }
  createOrder(order:any): Observable<any> {
		return this.http.post("http://localhost:1000/wallet-service/pg/createOrder", {
		customerName: order.name,
		email: order.email,
		phoneNumber: order.phone,
		amount: order.amount
		}, httpOptions
);
	}

  payBywallet(amount:number, walletId:string):Observable<wallet>{
    return this.http.get<wallet>(this.payByWalletApi+amount+"/"+walletId);
  }

  getStatement(walletId:string):Observable<OrderStatement[]>{
    return this.http.get<OrderStatement[]>(this.getStatementsApi+walletId);
  }
}

