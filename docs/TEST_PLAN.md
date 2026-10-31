# Rencana Tes Swag Labs

## Tujuan dan cakupan
Memverifikasi alur pembelian demo dari login, katalog, detail, pengurutan, keranjang, formulir checkout, ringkasan, penyelesaian, pembatalan, logout dan reset state. Target https://www.saucedemo.com/; platform yang dipilih Web Desktop. Test negatif meliputi kredensial salah, akun terkunci, field wajib dan akses tanpa autentikasi.

Registrasi dan formulir kontak adalah pemeriksaan cakupan: verifikasi ketersediaannya, lalu dokumentasikan N/A jika fitur tidak tersedia. Jangan mengganti fitur kontak dengan formulir checkout. API, mobile native, pembayaran riil, performance dan penetration testing di luar cakupan karena tidak ada kontrak API atau requirement terkait.

Eksplorasi 31 Oktober 2026 tidak menemukan Register/Contact pada login, katalog, menu dan footer yang diperiksa; dicatat N/A. Menu Dynamic Catalog ditemukan dan belum tercakup: suite ini menggunakan katalog standar. Tautan media sosial/About tidak diuji sampai ke situs eksternal.

## Strategi

Implementasi kini memakai Page Object Model dan 44 native Object Repository, bukan dynamic locator helper Shop. Tanggung jawab page/component, core lifecycle dan fixture dipisah; assertion khusus skenario berada di test case. Lihat FRAMEWORK.md untuk aturan maintenance dan TEST_CASE_DETAILS.md untuk traceability. Implementasi refactor perlu dieksekusi ulang; hasil sebelum refactor adalah bukti historis, bukan kelulusan versi sekarang.
Black-box functional testing dengan positive/negative testing, equivalence partitioning untuk kredensial dan field wajib, serta transisi state login-cart-checkout. Smoke adalah subset kritis; Regression mencakup seluruh kasus otomatis. Assertion memeriksa URL, isi pesan, item, badge dan nilai uang; klik tanpa assertion tidak cukup. Setiap kasus membuka browser baru sehingga urutan tidak menjadi prasyarat. Tidak menggunakan retry untuk menyembunyikan kegagalan.

## Environment dan data
Windows desktop, Katalon Studio 10.1.1 yang ditemukan pada perangkat, Chrome terpasang dengan WebDriver sesuai versi. Profile default menyimpan baseUrl. Data publik demo: standard_user, locked_out_user, secret_sauce; password negatif wrong_password; nama QA Tester; kode pos 12190. Tidak menggunakan data pribadi. Harga yang dijadikan baseline: Backpack 29.99 dan Bike Light 9.99; subtotal 39.98, tax demo 3.20, total 43.18. Perubahan katalog harus direview sebelum mengubah assertion.

## Entry dan exit criteria
Entry: project dapat dibuka/compile, Katalon aktif, browser/driver siap, situs dapat diakses. Exit: semua kasus otomatis sudah dieksekusi; semua High lulus; tidak ada defect kritis terbuka; failure/blocked dicatat beserta bukti; manual dan N/A diberi status jujur. Kelulusan otomatis tidak membuktikan kasus manual selesai.

## Prosedur dan bukti
Jalankan Smoke lalu Regression. Simpan report asli Katalon, log, screenshot kegagalan dan ringkasan lingkungan. Catat tanggal eksekusi, suite, versi OS/browser/Katalon, jumlah pass/fail/error/skipped dan defect. Status tidak boleh diisi PASS sebelum run. Untuk defect catat ID kasus, expected/actual, langkah reproduksi, severity, screenshot, environment dan status retest. Kegagalan aktivasi/runtime adalah blocked environment, bukan defect aplikasi.

## Risiko
Situs publik dan jaringan dapat tidak stabil; ulangi hanya setelah penyebab dicatat. Update browser bisa membuat driver tidak cocok; update driver melalui Katalon. Akun demo khusus memiliki perilaku cacat disengaja. Eksekusi console memerlukan runtime dan aktivasi yang sesuai; jalur Studio UI menjadi alternatif bila lisensi console tidak tersedia. Tidak ada specification resmi lengkap: aturan yang belum jelas dicatat untuk konfirmasi, bukan diasumsikan defect.

## Deliverables
Project native Katalon, TEST_CASES.md, TEST_PLAN.md, README.md, EXECUTION_STATUS.md dan report aktual bila runtime berhasil dijalankan. Project lokal memenuhi opsi penyimpanan tugas; unggah repository dapat dilakukan kemudian tanpa memasukkan token atau informasi akun.
