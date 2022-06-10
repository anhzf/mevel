# Motravel
**Mau travel? ya Motravel!**. Motravel is a Mobile Application, that provides the best experience to recommend your travel destination near you based on AI analysis of your interest.


## Daftar Isi
- [Motravel](#motravel)
  - [Daftar Isi](#daftar-isi)
  - [Struktur Folder](#struktur-folder)
  - [Kontribusi dan Membuat Perubahan pada Projek](#kontribusi-dan-membuat-perubahan-pada-projek)
  - [Menggunakan Firebase untuk Development](#menggunakan-firebase-untuk-development)
    - [Setup Firebase Tools](#setup-firebase-tools)
    - [Setup Firebase pada Projek](#setup-firebase-pada-projek)
    - [Menggunakan Firebase Emulator](#menggunakan-firebase-emulator)
      - [Menjalankan Emulator](#menjalankan-emulator)
      - [Integrasi Firebase Emulator ke Projek](#integrasi-firebase-emulator-ke-projek)
      - [Seeding](#seeding)
  - [Disclaimer](#disclaimer)


## Struktur Folder
| path                 | deskripsi                                            |
| -------------------- | ---------------------------------------------------- |
| `/android`           | Kode sumber untuk aplikasi android                   |
| `/ai-recommendation` | Kode sumber untuk pengembangan AI Rekomendasi Travel |
...more coming soon


## Kontribusi dan Membuat Perubahan pada Projek
Silahkan lihat [Panduan Kontribusi](./CONTRIBUTING.md).

## Menggunakan Firebase untuk Development

### Setup Firebase Tools
Ikuti petunjuk berikut atau melalui dokumentasi resminya yang dapat diakses [disini](https://github.com/firebase/firebase-tools).
1. Install [Nodejs >14.18.0](https://nodejs.org/)
2. Install [Java Development Kit >16](https://www.oracle.com/java/technologies/downloads/)
3. Install Firebase Tools dengan command
   ```bash
   npm i -g firebase-tools
   ```
4. Pastikan Firebase Tools telah terpasang
   ```bash
   firebase -V
   // output: 11.0.0
   ```
5. Login Firebase Tools
   ```bash
   firebase login
   ```


### Setup Firebase pada Projek
- [**Android**](https://firebase.google.com/docs/android/setup#register-app)
- [**Server**](https://firebase.google.com/docs/admin/setup) (NodeJS, Java, Python, Go, C#)


### Menggunakan Firebase Emulator
Pastikan telah mengikuti [panduan setup Firebase Tools](#setup-firebase-tools).


#### Menjalankan Emulator
Untuk menjalakan emulator gunakan command berikut:

```bash
firebase emulators:start
```

- *Akan keluar beberapa terminal setelah menjalankan emulator*
- *Untuk menghentikan emulator gunakan CTRL + C pada terminal utama (yang digunakan untuk menjalankan emulator). **PASTIKAN** terminal-terminal yang dibuka saat menjalankan emulator juga ditutup (bisa dilakukan dengan manual)*


#### Integrasi Firebase Emulator ke Projek
https://firebase.google.com/docs/emulator-suite/connect_and_prototype


#### Seeding
Dokumentasi seeding dapat dilihat lebih lanjut [disini](./utils/seeder/README.md).

## Disclaimer
Projek ini didedikasikan untuk Capstone Project [Bangkit 2022](https://bangkit.academy/).
