import { print } from '../../debugging';
import { placeSeeds } from './factories/place';

(async () => {
  try {
    await placeSeeds(0, 50);
  } catch (err) {
    print(err)
  }
})();
