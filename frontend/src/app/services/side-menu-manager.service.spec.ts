import { TestBed } from '@angular/core/testing';

import { SideMenuManagerService } from './side-menu-manager.service';

describe('SideMenuManagerService', () => {
  let service: SideMenuManagerService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SideMenuManagerService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
