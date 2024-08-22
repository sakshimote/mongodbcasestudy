import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { LoginService } from "../../login/service/login.service";
import { Cart } from "../model/cart.model";


@Injectable({
  providedIn: 'root'
})

export class CartService {


    private getByCartIdApi:string;
    private getCartsApi:string;
    private getCartByUserIdApi:string;
    private addProductsApi:string;
    private addCartApi:string;
    private addQuantityApi:string;
    private subtractQuantityApi:string;
    private deleteItemApi:string;
    private removeAllItemsApi:string;

  constructor(private http:HttpClient,private loginService:LoginService) {

       this.getByCartIdApi="http://localhost:1000/cart-service/cart/getCart/";
       this.getCartsApi="http://localhost:1000/cart-service/cart/carts";
       this.getCartByUserIdApi="http://localhost:1000/cart-service/cart/byUser/";
       this.addProductsApi="http://localhost:1000/cart-service/cart/add/items/";
       this.addCartApi="http://localhost:1000/cart-service/cart/addcart/";
       this.addQuantityApi="http://localhost:1000/cart-service/cart/add/quantity/";
       this.subtractQuantityApi="http://localhost:1000/cart-service/cart/remove/quantity/";
       this.deleteItemApi="http://localhost:1000/cart-service/cart/remove/item/";
       this.removeAllItemsApi="http://localhost:1000/cart-service/cart/removeAll/items/";

  }


  public getCartByUserId(){
    let  userId=localStorage.getItem("userId");
    return this.http.get<Cart>(this.getCartByUserIdApi+userId)
  }

  public getCartById(cartId:string){
    return this.http.get<Cart>(this.getByCartIdApi+cartId);
  }

  public getCarts():Observable<Cart[]>{
    return this.http.get<Cart[]>(this.getCartsApi);
  }

public addCart() :Observable<Cart> {
 let userId=localStorage.getItem("userId");
  return this.http.post<Cart>(this.addCartApi+userId,{});


}

public addItemsInCart(userId:string,productId:string,quantity:number):Observable<Cart>{
  userId=localStorage.getItem("userId");
return this.http.post<Cart>(this.addProductsApi+userId+"/"+productId+"/"+(1),{});
}

public addQuantity(userId:string,productId:string,quantity:number):Observable<Cart>{
  userId=localStorage.getItem("userId");
  return this.http.post<Cart>(this.addQuantityApi+userId+"/"+productId+"/"+(1),{},{headers: { authorization: this.loginService.createBasicAuthToken(this.loginService.username, this.loginService.password) } });
}

public subQuantity(userId:string,productId:string,quantity:number):Observable<Cart>{
  userId=localStorage.getItem("userId");
  return this.http.post<Cart>(this.subtractQuantityApi+userId+"/"+productId+"/"+(1),{},{headers: { authorization: this.loginService.createBasicAuthToken(this.loginService.username, this.loginService.password) } });
}

public deleteItem(userId:string,productId:string):Observable<Cart>{
  userId=localStorage.getItem("userId");
  return this.http.put<Cart>(this.deleteItemApi+userId+"/"+productId,{},{headers: { authorization: this.loginService.createBasicAuthToken(this.loginService.username, this.loginService.password) } });

}

public removeAllItems(userId:string):Observable<Cart>{
  userId=localStorage.getItem("userId");
  return this.http.put<Cart>(this.removeAllItemsApi+userId,{},{headers: { authorization: this.loginService.createBasicAuthToken(this.loginService.username, this.loginService.password) } });
}



}
