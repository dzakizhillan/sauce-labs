# Swag Labs - Katalon Web UI Automation

Proyek ini adalah *automated UI testing framework* yang dibangun menggunakan **Katalon Studio** untuk menguji website [Sauce Demo](https://saucedemo.com). Framework ini dirancang dengan pendekatan **Page Object Model (POM)** memanfaatkan keunggulan *native* Object Repository Katalon guna memastikan kebersihan kode (*clean code*) dan kemudahan pemeliharaan locator secara terpusat.

## Fitur & Keunggulan Framework
* **Page Object Model (POM):** Pemisahan logis antara skenario tes, aksi halaman (*keywords*), dan elemen UI (*object repository*).
* **Robust Object Repository:** Mengelola 44 *native* TestObject berbasis CSS/Xpath selector tanpa *hardcoded* di dalam script.
* **Dynamic Configuration:** Manajemen konfigurasi (URL, kredensial, *timeout*) dikelola secara terpusat melalui Environment Profile.
* **Automated Failure Capture:** Mengambil *screenshot* secara otomatis jika terjadi kegagalan melintasi asersi.

## Cakupan Pengujian (Test Suites)
Total terdapat **26 Automated Test Cases** yang mencakup validasi fungsionalitas utama:
* **Smoke Test Suite (5 Test Cases):** Validasi alur kritis seperti *positive/negative login* dan *basic checkout*.
* **Regression Test Suite (26 Test Cases):** Pengujian menyeluruh untuk memastikan seluruh fungsionalitas keranjang belanja, kalkulasi harga/pajak, hingga *checkout workflow* berjalan stabil.

## Prasyarat System (Prerequisites)
* Katalon Studio v10.1.1 atau versi terbaru.
* Google Chrome Browser.
* *Note: Jika terjadi error SessionNotCreated akibat pembaruan browser, lakukan update driver via **Tools > Update WebDrivers > Chrome** di Katalon Studio.*

## Cara Menjalankan Pengujian

1. **Import Project:**
   * *Clone* repositori ini atau unduh sebagai ZIP lalu ekstrak.
   * Buka Katalon Studio, pilih **File > Open Project**.
   * Pilih folder project `katalon-swag-labs` dan tunggu hingga proses *build cloud dependency* selesai.

2. **Eksekusi Test Suite:**
   * Pada Tests Explorer, buka folder **Test Suites**.
   * Klik dua kali pada `Smoke` atau `Regression`.
   * Pastikan Execution Profile di pojok kanan atas diatur ke **default**.
   * Klik tombol **Run** (ikon panah hijau) lalu pilih **Chrome**.

3. **Melihat Laporan (Test Reports):**
   * Setelah eksekusi selesai, laporan pengujian terperinci (.html, log, dan *screenshot*) dapat langsung dilihat pada folder **Reports** di Tests Explorer.

## Struktur Direktori Project
* `Test Cases/` : Berisi *flow* skenario pengujian dan asersi utama.
* `Object Repository/` : Berisi elemen *locator* halaman web yang terstruktur per komponen.
* `Keywords/framework/` : Berisi *reusable custom keywords* untuk aksi halaman (*pages*) dan fungsi inti (*core*).
* `Profiles/` : Penyimpanan variabel global environment.
