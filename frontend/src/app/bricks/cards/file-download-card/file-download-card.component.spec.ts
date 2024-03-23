import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FileDownloadCardComponent } from './file-download-card.component';

describe('FileDownloadCardComponent', () => {
  let component: FileDownloadCardComponent;
  let fixture: ComponentFixture<FileDownloadCardComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [FileDownloadCardComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(FileDownloadCardComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
