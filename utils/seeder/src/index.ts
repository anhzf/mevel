import { print } from '../../debugging';
import { placeSeeds } from './factories/place';

(async () => {
  try {
    // Data bisa dilihat di factories/place/source/
    // Parameter pertama merupakan urutan data yang akan diambil
    // Parameter kedua merupakan jumlah data yang akan diambil
    await placeSeeds(0, 50);
  } catch (err) {
    print(err)
  }
})();
