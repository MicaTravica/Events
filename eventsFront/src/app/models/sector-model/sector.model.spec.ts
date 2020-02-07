import { Sector } from './sector.model';

describe('Sector', () => {
  it('should create an instance', () => {
    expect(new Sector(null, null, null, null, null, null)).toBeTruthy();
  });
});
