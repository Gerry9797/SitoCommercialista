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
      let cartToggleList = document.querySelectorAll(".elementor-widget-woocommerce-menu-cart");
      cartToggleList.forEach((cartToggle) => {
        cartToggle?.classList.remove("elementor-menu-cart--shown")
      });

      let cartList = document.querySelectorAll(".elementor-menu-cart__container");
      cartList.forEach((cartToggle) => {
        cartToggle?.classList.remove("elementor-menu-cart--shown")
      });
    }

  }
  
}
