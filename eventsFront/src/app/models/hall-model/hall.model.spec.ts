import { Hall } from './hall.model';

describe('Hall', () => {
  it('should create an instance', () => {
    expect(new Hall(null, null, null, null)).toBeTruthy();
  });
});
