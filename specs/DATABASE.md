# SPESIFIKASI DATABASE
- [SPESIFIKASI DATABASE](#spesifikasi-database)
  - [Engine](#engine)
  - [Penggunaan dalam Development Lokal](#penggunaan-dalam-development-lokal)
  - [Pengetahuan Dasar](#pengetahuan-dasar)
    - [Document](#document)
    - [Collection](#collection)
  - [Specs](#specs)
    - [User](#user)
    - [User RecommendationPreference](#user-recommendationpreference)
    - [User SettingsPreference](#user-settingspreference)
    - [User Agenda](#user-agenda)
    - [Place](#place)
    - [Place Review](#place-review)
    - [Address](#address)
    - [VisitNumber](#visitnumber)


## Engine
[Cloud Firestore](https://firebase.google.com/products/firestore)


## Penggunaan dalam Development Lokal
Ketika dalam development disarankan untuk menggunakan Emulator yang disediakan Official, untuk instruksi dapat diakses [disini](../README.md).


## Pengetahuan Dasar

### Document
Dokumen dapat disamakan dengan **baris** pada tabel Database SQL. Berbentuk `record`, yakni pasangan `key` dan `value` dimana setiap dokumennya memiliki ID yang **tidak** disimpan dalam `record`. Untuk definisi lebih lanjut dapat dilihat [disini](https://firebase.google.com/docs/firestore/data-model#documents).

### Collection
Setiap Dokumen berada dalam suatu Collection, Collection dapat disamakan dengan **tabel** pada Database SQL. Tidak perlu mendefinisikan spesifikasi kolom layaknya Database SQL. Sebuah Collection dapat dimiliki oleh Document, Collection yang ada didalam Document disebut **Subcollection**. Untuk lebih lanjut dapat dilihat [disini](https://firebase.google.com/docs/firestore/data-model#collections).


## Specs

### User
- **Type**: Document
- **Collection path**: /User
- **Document ID**: Diambil dari ID User yang terdaftar menggunakan Firebase Auth.
- **Desc**: Untuk menyimpan data-data user.

| Field | Type | Desc |
| ----- | ---- | ---- |
| -     | -    | -    |

### User RecommendationPreference
- **Type**: Document
- **Collection path**: /User/:userId/Preferences
- **Document ID**: Recommendation
- **Desc**: Menyimpan data preferensi untuk rekomendasi tempat.

| Field | Type | Desc |
| ----- | ---- | ---- |
| -     | -    | -    |

### User SettingsPreference
- **Type**: Document
- **Collection path**: /User/:userId/Preferences
- **Document ID**: Settings
- **Desc**: Menyimpan data preferensi untuk pengaturan akun.

| Field | Type | Desc |
| ----- | ---- | ---- |
| -     | -    | -    |

### User Agenda
- **Type**: Document
- **Collection path**: /User/:userId/Agenda
- **Document ID**: *Auto generated*
- **Desc**: Agenda tempat-tempat yang ditambahkan user.

| Field        | Type                   | Desc                         |
| ------------ | ---------------------- | ---------------------------- |
| place        | ref\<[Place](#place)\> | Tempat dimaksud.             |
| markedAsDone | boolean                | Tertandai telah selesai.     |
| createdAt    | datetime               | Tanggal ditambahkan.         |
| updatedAt    | datetime               | Tanggal terakhir diperbarui. |

### Place
- **Type**: Document
- **Collection path**: /Place
- **Document ID**: *Auto generated*
- **Desc**: Pada interface aplikasi sering biasa disebut dengan `Destinasi`.

| Field        | Type                               | Desc                                                                                                     |
| ------------ | ---------------------------------- | -------------------------------------------------------------------------------------------------------- |
| title        | string                             | Nama publik yang ditampilkan.                                                                            |
| imgSrc       | string[]                           | Alamat absolut Cloud Storage (ex: gs://user-uploads/gamber.png). Gambar pertama merupakan featured image |
| address      | map\<[Address](#address)\>         | Alamat tempat.                                                                                           |
| visits?      | map\<[VisitNumber](#visitnumber)\> | Jumlah kunjungan per periode waktu.                                                                      |
| ratings?     | computed\<number>                  | Rating, range angka 1-5.                                                                                 |
| reviewCount? | computed\<number>                  | Jumlah orang yang memberi ulasan.                                                                        |
| budgetRange? | number[]                           | Range budget minimal dan maksimal.                                                                       |
| taskItems?   | string[]                           | Daftar kegiatan yang harus dilakukan.                                                                    |
| desc?        | string                             | Deskripsi lokasi. (Format: markdown)                                                                     |
| keywords     | string[]                           | Kata kunci lokasi.                                                                                       |
| submittedBy? | ref\<[User](#user)\>               | Orang yang menambahkan lokasi ini.                                                                       |
| developedBy? | string                             | Pengelola lokasi.                                                                                        |
| createdAt    | datetime                           | Tanggal ditambahkan.                                                                                     |
| updatedAt    | datetime                           | Tanggal terakhir diperbarui.                                                                             |
| ...          | *any*                              | Field yang tidak terdefinisikan akan dikategorikan sebagai informasi tambahan.                           |

### Place Review
- **Type**: Document
- **Collection path**: /Place/:placeId/Review
- **Document ID**: Diambil dari ID User yang melakukan review.
- **Desc**: Review suatu tempat.

| Field     | Type     | Desc                         |
| --------- | -------- | ---------------------------- |
| rating    | number   | Range angka 1-5              |
| comment?  | string   | Komen opsional               |
| media?    | string[] | Alamat absolut Cloud Storage |
| createdAt | datetime | Tanggal ditambahkan.         |
| updatedAt | datetime | Tanggal terakhir diperbarui. |

### Address
- **Type**: Object
- **Desc**: Alamat

| Field     | Type   | Desc |
| --------- | ------ | ---- |
| province? | string | -    |
| city?     | string | -    |
| addresses | string | -    |

### VisitNumber
- **Type**: Object
- **Desc**: Jumlah kunjungan per periode waktu

| Field    | Type   | Desc                      |
| -------- | ------ | ------------------------- |
| count    | number | -                         |
| timeUnit | string | enum(day,week,month,year) |
