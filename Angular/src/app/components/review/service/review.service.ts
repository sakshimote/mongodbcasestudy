import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable } from "rxjs";
import { LoginService } from "../../login/service/login.service";
import { Review } from "../model/review.model";

@Injectable({
  providedIn: 'root'
})
export class ReviewService {

  postReviewApi:string;

  constructor(private http:HttpClient,
    private loginService:LoginService) {
    this.postReviewApi ="http://localhost:8096/review/addReview/";
  }

  postReview(review:Review,productId:string):Observable<Review>{
    return this.http.post<Review>(this.postReviewApi+productId,review,{headers: { authorization: this.loginService.createBasicAuthToken(this.loginService.username, this.loginService.password) } });
  }
}
