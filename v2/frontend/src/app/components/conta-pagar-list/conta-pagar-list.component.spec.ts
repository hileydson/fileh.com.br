import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ContaPagarListComponent } from './conta-pagar-list.component';

describe('ContaPagarListComponent', () => {
  let component: ContaPagarListComponent;
  let fixture: ComponentFixture<ContaPagarListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ContaPagarListComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ContaPagarListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
