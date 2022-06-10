# Seeder

Digunakan untuk generate data secara masif untuk mengisi database ataupun untuk digunakan secara langsung.


## Penggunaan

1. Install dependensi yang dibutuhkan,
   ```bash
   npm install
   ```
2. Copy file `.env.example` menjadi `.env`, kemudian atur konfigurasinya.
3. Buka file `src/index.ts` (Jangan ubah apapun selain file tersebut) kemudian pastikan kode didalamnya telah sesuai dengan yang dibutuhkan.
4. Jalankan emulator firebase (Jika menggunakan emulator).
   ```bash
   firebase emulators:start
   ```
5. Jalankan command berikut untuk memulai seeding.
   ```bash
   npm start
   ```

## Available Seeder
Untuk saat ini hanya tersedia seeds untuk `Place` saja.
