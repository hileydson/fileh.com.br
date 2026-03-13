import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PropostaComercialListComponent } from './proposta-comercial-list.component';

describe('PropostaComercialListComponent', () => {
  let component: PropostaComercialListComponent;
  let fixture: ComponentFixture<PropostaComercialListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PropostaComercialListComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PropostaComercialListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
