import { TestBed } from '@angular/core/testing';

import { InternalSessionManagerService } from './internal-session-manager.service';

describe('InternalSessionManagerService', () => {
  let service: InternalSessionManagerService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(InternalSessionManagerService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
