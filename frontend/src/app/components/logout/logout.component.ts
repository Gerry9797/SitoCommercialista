import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UtilityService } from 'src/app/services/utility/utility.service';

@Component({
  selector: 'app-logout',
  templateUrl: './logout.component.html',
  styleUrl: './logout.component.css'
})
export class LogoutComponent implements OnInit{

  constructor(
    private utilityService: UtilityService,
    private router: Router
  ) {}
  
  ngOnInit(): void {
    this.logout();
  }

  logout(): void {
    this.utilityService.logout();
  }

}
