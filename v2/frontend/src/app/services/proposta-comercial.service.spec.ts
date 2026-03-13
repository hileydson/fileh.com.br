import { TestBed } from '@angular/core/testing';

import { PropostaComercialService } from './proposta-comercial.service';

describe('PropostaComercialService', () => {
  let service: PropostaComercialService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PropostaComercialService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
