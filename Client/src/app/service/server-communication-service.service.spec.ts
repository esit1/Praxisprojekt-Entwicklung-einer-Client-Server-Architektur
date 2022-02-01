import {TestBed} from '@angular/core/testing';

import {ServerCommunicationServiceService} from './server-communication.service';

describe('ServerCommunicationServiceService', () => {
  let service: ServerCommunicationServiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ServerCommunicationServiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
