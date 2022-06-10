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
    - [Media](#media)
    - [Place](#place)
    - [Place Story](#place-story)
    - [Place Story Vote](#place-story-vote)
    - [Address](#address)
    - [VisitNumber](#visitnumber)
    - [PlaceFromGmaps](#placefromgmaps)


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

### Media
- **Type**: Document
- **Collection path**: /Media
- **Document ID**: *Auto generated*
- **Desc**: Menyimpan data media yang diupload.

| Field      | Type                           | Desc                                                                           |
| ---------- | ------------------------------ | ------------------------------------------------------------------------------ |
| src        | string                         | Alamat absolut cloud storage                                                   |
| title?     | string                         | Judul gambar                                                                   |
| desc?      | string                         | Deskripsi singkat gambar, dapat digunakan sebagai `alt` attribute pada html    |
| owner?     | ref\<[User](#user)\> \| string | Pemilik media                                                                  |
| uploadedBy | ref\<[User](#user)\>           | Pengupload media                                                               |
| createdAt  | datetime                       | Tanggal media diupload                                                         |
| updatedAt  | datetime                       | Tanggal terakhir diperbarui                                                    |
| ...        | *any*                          | Field yang tidak terdefinisikan akan dikategorikan sebagai informasi tambahan. |


### Place
- **Type**: Document
- **Collection path**: /Place
- **Document ID**: *Auto generated*
- **Desc**: Pada interface aplikasi sering biasa disebut dengan `Destinasi`.

| Field                | Type                               | Desc                                                                           |
| -------------------- | ---------------------------------- | ------------------------------------------------------------------------------ |
| title                | string                             | Nama publik yang ditampilkan.                                                  |
| imgSrc               | ref\<[Media](#media)\>[]           | Gambar2 tempat. Gambar pertama dalam array merupakan featured image.           |
| address              | map\<[Address](#address)\>         | Alamat tempat.                                                                 |
| displayAddress       | string                             | Alamat yang akan ditampilkan dalam UI.                                         |
| mapsUrl              | string                             | Alamat url menuju ke maps (ex: Google Maps Url).                               |
| visits?              | map\<[VisitNumber](#visitnumber)\> | Jumlah kunjungan per periode waktu.                                            |
| ratingScore?         | computed\<number\>                 | Rating, total skor rating yang didapatkan.                                     |
| reviewCount?         | computed\<number\>                 | Jumlah orang yang memberi ulasan.                                              |
| desc?                | string                             | Deskripsi lokasi. (Format: markdown)                                           |
| category             | string[]                           | enum(alam_darat,alam_laut,alam_salju,seni,sports)                              |
| keywords             | string[]                           | Kata kunci lokasi.                                                             |
| submittedBy?         | ref\<[User](#user)\>               | Orang yang menambahkan lokasi ini.                                             |
| createdAt            | datetime                           | Tanggal ditambahkan.                                                           |
| updatedAt            | datetime                           | Tanggal terakhir diperbarui.                                                   |
| *Informasi tambahan* |
| developedBy?         | string                             | Pengelola lokasi.                                                              |
| budgetRange?         | number[]                           | Range budget minimal dan maksimal.                                             |
| taskItems?           | string[]                           | Daftar kegiatan yang harus dilakukan.                                          |
| fromGmaps?           | map\<PlaceFromGmaps\>              | Tambahan informasi jika data didapat dari gmaps.                               |
| ...                  | *any*                              | Field yang tidak terdefinisikan akan dikategorikan sebagai informasi tambahan. |

### Place Story
- **Type**: Document
- **Collection path**: /Place/:placeId/Review
- **Document ID**: Diambil dari ID User yang melakukan review.
- **Desc**: Review suatu tempat.

| Field     | Type               | Desc                         |
| --------- | ------------------ | ---------------------------- |
| rating    | number             | Range angka 1-5              |
| comment?  | string             | Komen opsional               |
| media?    | ref\<Media\>[]     | Alamat absolut Cloud Storage |
| upvoted   | computed\<number\> | Jumlah Upvote                |
| downvoted | computed\<number\> | Jumlah Downvote              |
| createdAt | datetime           | Tanggal ditambahkan.         |
| updatedAt | datetime           | Tanggal terakhir diperbarui. |

### Place Story Vote
- **Type**: Document
- **Collection path**: /Place/:placeId/Review
- **Document ID**: *Auto generated*
- **Desc**: Fitur Upvote/Downvote Story

| Field     | Type    | Desc                                    |
| --------- | ------- | --------------------------------------- |
| ...userId | boolean | true untuk Upvote, false untuk Downvote |

**Penjelasan**

Jadi nanti bukan setiap Vote akan disimpan sebagai dokumen, akan tetapi setiap dokumen akan menyimpan 20 Vote. Dokumen akan berisi field dengan nama `key`-nya adalah userId yang melakukan vote, kemudian `value`-nya berisi boolean true/false. `true` untuk Upvote dan `false` untuk Downvote.

Hal ini bertujuan untuk menghemat banyaknya dokumen yang disimpan dalam Firestore sehingga akan menghemat billing. [see pricing](https://firebase.google.com/pricing)

### Address
- **Type**: Object
- **Desc**: Alamat

| Field      | Type               | Desc                                  |
| ---------- | ------------------ | ------------------------------------- |
| province?  | string             | -                                     |
| city?      | string             | -                                     |
| districts? | string             | Kecamatan.                            |
| addresses? | string             | Alamat jalan, alamat lebih detailnya. |
| latlong?   | geographical point | Koordinat Latlong.                    |

### VisitNumber
- **Type**: Object
- **Desc**: Jumlah kunjungan per periode waktu

| Field    | Type   | Desc                      |
| -------- | ------ | ------------------------- |
| count    | number | -                         |
| timeUnit | string | enum(day,week,month,year) |

### PlaceFromGmaps
- **Type**: Object
- **Desc**: Data-data yang diambil dari Gmaps Place API.

| Field   | Type     | Desc                                     |
| ------- | -------- | ---------------------------------------- |
| placeId | string   | placeId                                  |
| fields  | string[] | Daftar field yang diambil dari Place API |
| ...     | *any*    | metadata                                 |
