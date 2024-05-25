import { TestBed } from '@angular/core/testing';

import { ErrorViewerHandlerService } from './error-viewer-handler.service';

describe('ErrorViewerHandlerService', () => {
  let service: ErrorViewerHandlerService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ErrorViewerHandlerService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
