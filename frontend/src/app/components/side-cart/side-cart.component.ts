import { Component } from '@angular/core';

@Component({
  selector: 'app-side-cart',
  templateUrl: './side-cart.component.html',
  styleUrl: './side-cart.component.css'
})
export class SideCartComponent {

  checkIfCloseCartDetail(event: any){
    let targetClicked = event.target;
    if(targetClicked.classList.contains("no-close-cart-details")){
      return
    }
    else{
      let cart = document.querySelector(".elementor-menu-cart__container");
      cart?.classList.remove("elementor-menu-cart--shown");
    }

  }
  
}
