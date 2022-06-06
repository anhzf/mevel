import express from 'express';
import { faker } from '@faker-js/faker';
import { middleware } from 'query-types'
import type { Providers } from './factories/types';

const PORT = process.env.PORT || 3000;
const COLLECTION_COUNT = 5;

const app = express();

faker.setLocale('id_ID');

app.get('/', (req, res) => res.json('ready!'));

app.get('/:providerName/:factoryName', middleware(), async (req, res, next) => {
  const {
    providerName,
    factoryName,
  } = req.params;

  try {
    const provider: Providers = await import(`./factories/${providerName}`);

    if (factoryName in provider) {
      const factory: () => unknown = provider[factoryName as keyof typeof provider];
      const count = Number(req.query.count) || COLLECTION_COUNT;
      return res.json(Array.from(Array(count), factory.bind(null, { _: req.query.field })));
    }
  } catch (err) { }
  next();
});

app.listen(PORT, () => console.log(`The Seeder is listening on port ${PORT}!`));
