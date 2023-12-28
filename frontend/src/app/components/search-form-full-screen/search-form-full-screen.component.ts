import { Component } from '@angular/core';

@Component({
  selector: 'app-search-form-full-screen',
  templateUrl: './search-form-full-screen.component.html',
  styleUrl: './search-form-full-screen.component.css'
})
export class SearchFormFullScreenComponent {

  checkIfCloseSearchForm(event: any){
    let targetClicked = event.target;
    if(!targetClicked.classList.contains("elementor-search-form__input")){
      let searchForm = document.querySelector(".elementor-search-form__container");
      if(searchForm){
          searchForm.classList.remove("elementor-search-form--full-screen")
          searchForm.classList.remove("elementor-lightbox")
      }    
    }

  }

}
