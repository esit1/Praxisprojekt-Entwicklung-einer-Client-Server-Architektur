import { TestBed } from '@angular/core/testing';

import { ConvertDateService } from './convert-date.service';

describe('ConvertDateService', () => {
  let service: ConvertDateService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ConvertDateService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
