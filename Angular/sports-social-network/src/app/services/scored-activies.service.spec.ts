import { TestBed } from '@angular/core/testing';

import { ScoredActiviesService } from './scored-activies.service';

describe('ScoredActiviesService', () => {
  beforeEach(() => TestBed.configureTestingModule({}));

  it('should be created', () => {
    const service: ScoredActiviesService = TestBed.get(ScoredActiviesService);
    expect(service).toBeTruthy();
  });
});
