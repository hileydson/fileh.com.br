import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SelecionarEntidadeComponent } from './selecionar-entidade.component';

describe('SelecionarEntidadeComponent', () => {
  let component: SelecionarEntidadeComponent;
  let fixture: ComponentFixture<SelecionarEntidadeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SelecionarEntidadeComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SelecionarEntidadeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
