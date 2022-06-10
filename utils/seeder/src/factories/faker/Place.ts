import { faker } from '@faker-js/faker';
import type { Place } from '../../../../types/schema';
import type { Factory } from '../types';

const factory: Factory<Place> = ({ _ } = {}) => Object.assign({
  title: faker.address.cityName(),
  imgSrc: [faker.image.imageUrl()],
  address: {
    province: faker.address.state(),
    city: faker.address.city(),
    addresses: faker.address.streetAddress(),
  },
  visits: {
    count: faker.datatype.number(),
    timeUnit: faker.helpers.arrayElement(['day', 'week', 'month', 'year']),
  },
  ratings: faker.datatype.number(),
  reviewCount: faker.datatype.number(),
  budgetRange: [Number(faker.commerce.price()), Number(faker.commerce.price())],
  taskItems: [faker.lorem.sentence(), faker.lorem.sentence(), faker.lorem.sentence()],
  desc: faker.lorem.paragraphs(faker.datatype.number(4)),
  keywords: faker.lorem.word(),
  submittedBy: faker.name.firstName(),
  developedBy: faker.company.companyName(),
  createdAt: faker.date.past(),
  updatedAt: faker.date.past(),
}, _);

export default factory;
