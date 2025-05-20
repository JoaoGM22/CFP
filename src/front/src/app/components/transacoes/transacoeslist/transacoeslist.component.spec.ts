import { ComponentFixture, TestBed } from '@angular/core/testing';

import { TransacoeslistComponent } from './transacoeslist.component';

describe('TransacoeslistComponent', () => {
  let component: TransacoeslistComponent;
  let fixture: ComponentFixture<TransacoeslistComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [TransacoeslistComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(TransacoeslistComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
