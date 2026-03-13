import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ContaReceberListComponent } from './conta-receber-list.component';

describe('ContaReceberListComponent', () => {
  let component: ContaReceberListComponent;
  let fixture: ComponentFixture<ContaReceberListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ContaReceberListComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ContaReceberListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
