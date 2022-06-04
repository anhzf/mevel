# Seeder

Digunakan untuk generate data secara masif untuk mengisi database ataupun untuk digunakan secara langsung.


## Penggunaan

### Basic
```bash
curl http://localhost:3000/<provider-name>/<entity-name>
```

- **provider-name**: Nama provider data, lihat daftar
- **entity-name**: Nama entity yang akan digenerate, lihat daftar

#### Contoh
```bash
curl http://localhost:3000/Faker/Place
```

##### Response
array

### Provider

| nama  | deskripsi                                 |
| ----- | ----------------------------------------- |
| Faker | Akan digenerate dari data palsu           |
| Real  | Data-data yang telah dikumpulkan oleh tim |

### Entity

- Place
- PlaceReview


## Lanjutan (Query Parameter)

Tersedia beberapa query untuk menangani kebutuhan kasus lebih banyak.

### `int` count
Jumlah data yang akan digenerate.
```bash
curl http://localhost:3000/faker/Place?count=100
# akan mengembalikan json berisi array 100 berisi Place entity
```

### `map` field
Memaksa untuk mengisi field dengan key yang telah ditentukan dengan value yang diinginkan.
```bash
curl http://localhost:3000/faker/Place?field[submittedBy]=anhzf
# akan mengembalikan json berisi array berisi Place entity dimana semua key 'submittedBy' pada setiap entity Place berisi 'anhzf'
```

