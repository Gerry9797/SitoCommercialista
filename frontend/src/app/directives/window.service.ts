import { Injectable } from '@angular/core';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class WindowService {

  private windowScroll: Subject<any> = new Subject<any>();

  // Set scroll
  public set scroll (event: any) {
     this.windowScroll.next(event);
  }

  /**
   * Returns the subject to the windows scroll
   */
  public onWindowScroll(): Subject<any> {
    return this.windowScroll;
  }

}