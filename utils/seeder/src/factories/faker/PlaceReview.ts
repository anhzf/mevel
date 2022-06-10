import { faker } from '@faker-js/faker';
import type { PlaceStory } from '../../../../types/schema';
import type { Factory } from '../types';

const factory: Factory<PlaceStory> = ({ _ } = {}) => Object.assign({
  rating: faker.datatype.number({ min: 1, max: 5 }),
  comment: faker.lorem.paragraphs(faker.datatype.number(3)),
  media: faker.helpers.uniqueArray(
    () => faker.image.nature(undefined, undefined, true),
    faker.datatype.number({ min: 1, max: 7 })
  ),
  createdAt: faker.date.past(),
  updatedAt: faker.date.past(),
}, _);

export default factory;
