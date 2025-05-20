import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TransacoesdetailsComponent } from './transacoesdetails.component';

describe('TransacoesdetailsComponent', () => {
  let component: TransacoesdetailsComponent;
  let fixture: ComponentFixture<TransacoesdetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TransacoesdetailsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TransacoesdetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
