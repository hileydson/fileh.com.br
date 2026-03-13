import { TestBed } from '@angular/core/testing';

import { ContaReceberService } from './conta-receber.service';

describe('ContaReceberService', () => {
  let service: ContaReceberService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ContaReceberService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
