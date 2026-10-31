# Detail test case dan traceability

Baseline: Web Desktop, Chrome, default profile, katalog standar. Setiap kasus membuka sesi browser baru melalui TestSession, sehingga tidak bergantung hasil kasus sebelumnya. Situs harus dapat diakses dan driver cocok. Data adalah public demo/sintetis, bukan data pelanggan.

## Pemetaan kebutuhan

| Kebutuhan | Kasus otomatis | Bagian framework |
|---|---|---|
| Autentikasi dan pembatasan akses | AUTH01-AUTH07 | LoginPage, NavigationMenu |
| Katalog, detail, sort | CAT01-CAT06 | InventoryPage, ProductPage |
| Keranjang dan state refresh | CART01-CART05 | InventoryPage, CartPage, Header |
| Validasi informasi pembeli | CHK01-CHK03 | CheckoutInformationPage |
| Ringkasan, total, selesai, batal | CHK04-CHK07 | CheckoutFlow, halaman Checkout |
| Reset state aplikasi | NAV01 | NavigationMenu, CartPage, Header |

## Batas cakupan

26 kasus mencakup baseline fungsi utama, bukan seluruh kombinasi aplikasi. Registrasi/Contact tidak ditemukan pada area login, katalog, menu dan footer yang diperiksa (31 Oktober 2026); dicatat N/A. Dynamic Catalog, akun simulasi cacat, accessibility penuh, kompatibilitas semua browser, semua gambar/detail produk dan input boundary/whitespace belum diotomasi. Lihat daftar manual dalam TEST_CASES.md. Aturan input bebas dan checkout cart kosong memerlukan requirement bisnis sebelum diberi expected pass/fail.

Hasil historis sebelum refactor: 24 passed, 2 failed. Semua script versi POM/repository saat ini berstatus **PENDING RETEST**. Jangan membawa status pass versi lama ke implementasi baru tanpa run.


## AUTH01 - Login valid

Prioritas: High. Jenis: fungsional/transisi state.

Prasyarat: browser baru, situs dan driver siap; akun demo tersedia. Login valid dilakukan eksplisit jika tercantum pada langkah.

Langkah:

1. Masukkan kredensial standard_user / secret_sauce melalui LoginPage.
2. Klik Login dan tunggu inventory.html.
3. Periksa judul halaman Products.

Expected result: URL inventory.html dan judul Products.

Implementasi: `Test Cases/AUTH01_Login_valid`; kode `Scripts/AUTH01_Login_valid/Script.groovy`. Status versi refactor: PENDING RETEST. Actual result dan evidence diambil dari report run baru; belum diisi.


## AUTH02 - Password salah

Prioritas: High. Jenis: negatif.

Prasyarat: browser baru, situs dan driver siap; akun demo tersedia. Login valid dilakukan eksplisit jika tercantum pada langkah.

Langkah:

1. Masukkan standard_user / wrong_password.
2. Klik Login.
3. Periksa pesan kredensial tidak cocok dan tombol login masih terlihat.

Expected result: Pesan kredensial tidak cocok; tetap di login.

Implementasi: `Test Cases/AUTH02_Password_salah`; kode `Scripts/AUTH02_Password_salah/Script.groovy`. Status versi refactor: PENDING RETEST. Actual result dan evidence diambil dari report run baru; belum diisi.


## AUTH03 - Username kosong

Prioritas: High. Jenis: negatif.

Prasyarat: browser baru, situs dan driver siap; akun demo tersedia. Login valid dilakukan eksplisit jika tercantum pada langkah.

Langkah:

1. Kosongkan username dan password.
2. Klik Login.
3. Periksa error Username is required.

Expected result: Username is required.

Implementasi: `Test Cases/AUTH03_Username_kosong`; kode `Scripts/AUTH03_Username_kosong/Script.groovy`. Status versi refactor: PENDING RETEST. Actual result dan evidence diambil dari report run baru; belum diisi.


## AUTH04 - Password kosong

Prioritas: High. Jenis: negatif.

Prasyarat: browser baru, situs dan driver siap; akun demo tersedia. Login valid dilakukan eksplisit jika tercantum pada langkah.

Langkah:

1. Isi username standard_user, password kosong.
2. Klik Login.
3. Periksa error Password is required.

Expected result: Password is required.

Implementasi: `Test Cases/AUTH04_Password_kosong`; kode `Scripts/AUTH04_Password_kosong/Script.groovy`. Status versi refactor: PENDING RETEST. Actual result dan evidence diambil dari report run baru; belum diisi.


## AUTH05 - Akun terkunci

Prioritas: High. Jenis: negatif.

Prasyarat: browser baru, situs dan driver siap; akun demo tersedia. Login valid dilakukan eksplisit jika tercantum pada langkah.

Langkah:

1. Masukkan locked_out_user / secret_sauce.
2. Klik Login.
3. Periksa pesan akun terkunci.

Expected result: Pesan akun terkunci.

Implementasi: `Test Cases/AUTH05_Akun_terkunci`; kode `Scripts/AUTH05_Akun_terkunci/Script.groovy`. Status versi refactor: PENDING RETEST. Actual result dan evidence diambil dari report run baru; belum diisi.


## AUTH06 - Logout

Prioritas: High. Jenis: fungsional/transisi state.

Prasyarat: browser baru, situs dan driver siap; akun demo tersedia. Login valid dilakukan eksplisit jika tercantum pada langkah.

Langkah:

1. Login valid.
2. Buka sidebar, tunggu panel masuk viewport, klik Logout.
3. Periksa tombol Login.
4. Navigasi langsung inventory.html; periksa tombol Login dan pesan akses ditolak.

Expected result: Kembali ke login; URL inventory langsung ditolak.

Implementasi: `Test Cases/AUTH06_Logout`; kode `Scripts/AUTH06_Logout/Script.groovy`. Status versi refactor: PENDING RETEST. Actual result dan evidence diambil dari report run baru; belum diisi.


## AUTH07 - Akses inventory tanpa login

Prioritas: High. Jenis: negatif.

Prasyarat: browser baru, situs dan driver siap; akun demo tersedia. Login valid dilakukan eksplisit jika tercantum pada langkah.

Langkah:

1. Dari browser baru tanpa login, navigasi inventory.html.
2. Periksa diarahkan ke login.
3. Periksa pesan bahwa inventory.html hanya dapat diakses setelah login.

Expected result: Diarahkan ke login dengan pesan akses ditolak.

Implementasi: `Test Cases/AUTH07_Akses_inventory_tanpa_login`; kode `Scripts/AUTH07_Akses_inventory_tanpa_login/Script.groovy`. Status versi refactor: PENDING RETEST. Actual result dan evidence diambil dari report run baru; belum diisi.


## CART01 - Tambah satu produk

Prioritas: High. Jenis: fungsional/transisi state.

Prasyarat: browser baru, situs dan driver siap; akun demo tersedia. Login valid dilakukan eksplisit jika tercantum pada langkah.

Langkah:

1. Login valid; tambah Backpack.
2. Periksa badge 1 dan tombol produk berubah menjadi Remove.
3. Buka cart.
4. Periksa nama Backpack, harga $29.99 dan quantity 1.

Expected result: Badge 1; tombol Remove; isi cart dan harga sesuai.

Implementasi: `Test Cases/CART01_Tambah_satu_produk`; kode `Scripts/CART01_Tambah_satu_produk/Script.groovy`. Status versi refactor: PENDING RETEST. Actual result dan evidence diambil dari report run baru; belum diisi.


## CART02 - Tambah dua produk

Prioritas: High. Jenis: fungsional/transisi state.

Prasyarat: browser baru, situs dan driver siap; akun demo tersedia. Login valid dilakukan eksplisit jika tercantum pada langkah.

Langkah:

1. Login valid; tambah Backpack dan Bike Light.
2. Periksa badge 2.
3. Buka cart dan bandingkan kedua nama produk dengan fixture.

Expected result: Badge 2; dua item yang benar.

Implementasi: `Test Cases/CART02_Tambah_dua_produk`; kode `Scripts/CART02_Tambah_dua_produk/Script.groovy`. Status versi refactor: PENDING RETEST. Actual result dan evidence diambil dari report run baru; belum diisi.


## CART03 - Hapus dari katalog

Prioritas: High. Jenis: fungsional/transisi state.

Prasyarat: browser baru, situs dan driver siap; akun demo tersedia. Login valid dilakukan eksplisit jika tercantum pada langkah.

Langkah:

1. Login valid; tambah Backpack lalu hapus dari katalog.
2. Periksa badge tidak ada dan tombol Add tersedia kembali.
3. Buka cart; periksa tidak ada product row.

Expected result: Badge hilang; tombol Add tersedia; cart kosong.

Implementasi: `Test Cases/CART03_Hapus_dari_katalog`; kode `Scripts/CART03_Hapus_dari_katalog/Script.groovy`. Status versi refactor: PENDING RETEST. Actual result dan evidence diambil dari report run baru; belum diisi.


## CART04 - Hapus dari cart

Prioritas: High. Jenis: fungsional/transisi state.

Prasyarat: browser baru, situs dan driver siap; akun demo tersedia. Login valid dilakukan eksplisit jika tercantum pada langkah.

Langkah:

1. Login valid; tambah Backpack dan buka cart.
2. Hapus Backpack dari cart.
3. Periksa tidak ada product row dan badge hilang.

Expected result: Cart kosong dan badge hilang.

Implementasi: `Test Cases/CART04_Hapus_dari_cart`; kode `Scripts/CART04_Hapus_dari_cart/Script.groovy`. Status versi refactor: PENDING RETEST. Actual result dan evidence diambil dari report run baru; belum diisi.


## CART05 - Cart bertahan setelah refresh

Prioritas: High. Jenis: fungsional/transisi state.

Prasyarat: browser baru, situs dan driver siap; akun demo tersedia. Login valid dilakukan eksplisit jika tercantum pada langkah.

Langkah:

1. Login valid; tambah Backpack.
2. Refresh browser dan periksa badge tetap 1.
3. Buka cart dan periksa Backpack tetap ada.
4. Klik Continue Shopping dan periksa inventory.html.

Expected result: Badge dan item bertahan; kembali ke katalog.

Implementasi: `Test Cases/CART05_Cart_bertahan_setelah_refresh`; kode `Scripts/CART05_Cart_bertahan_setelah_refresh/Script.groovy`. Status versi refactor: PENDING RETEST. Actual result dan evidence diambil dari report run baru; belum diisi.


## CAT01 - Katalog produk

Prioritas: High. Jenis: fungsional/transisi state.

Prasyarat: browser baru, situs dan driver siap; akun demo tersedia. Login valid dilakukan eksplisit jika tercantum pada langkah.

Langkah:

1. Login valid.
2. Ambil semua nama produk dan bandingkan dengan enam nama baseline DemoData.
3. Periksa ada enam harga dan semua positif.
4. Periksa ada enam tombol Add to Cart.

Expected result: Enam produk; enam harga positif; enam tombol tambah.

Implementasi: `Test Cases/CAT01_Katalog_produk`; kode `Scripts/CAT01_Katalog_produk/Script.groovy`. Status versi refactor: PENDING RETEST. Actual result dan evidence diambil dari report run baru; belum diisi.


## CAT02 - Detail produk

Prioritas: High. Jenis: fungsional/transisi state.

Prasyarat: browser baru, situs dan driver siap; akun demo tersedia. Login valid dilakukan eksplisit jika tercantum pada langkah.

Langkah:

1. Login valid.
2. Buka detail Backpack melalui link productIndex 4.
3. Periksa nama Backpack, harga $29.99, deskripsi terlihat dan tidak kosong.
4. Klik Back to Products dan periksa inventory.html.

Expected result: Nama, harga $29.99 dan deskripsi tersedia; kembali berhasil.

Implementasi: `Test Cases/CAT02_Detail_produk`; kode `Scripts/CAT02_Detail_produk/Script.groovy`. Status versi refactor: PENDING RETEST. Actual result dan evidence diambil dari report run baru; belum diisi.


## CAT03 - Urut nama A-Z

Prioritas: Medium. Jenis: fungsional/transisi state.

Prasyarat: browser baru, situs dan driver siap; akun demo tersedia. Login valid dilakukan eksplisit jika tercantum pada langkah.

Langkah:

1. Login valid.
2. Pilih opsi az pada dropdown sort.
3. Periksa jumlah enam nama dan seluruh daftar sama dengan urutan ascending.

Expected result: Seluruh daftar mengikuti urutan yang dipilih.

Implementasi: `Test Cases/CAT03_Urut_nama_A_Z`; kode `Scripts/CAT03_Urut_nama_A_Z/Script.groovy`. Status versi refactor: PENDING RETEST. Actual result dan evidence diambil dari report run baru; belum diisi.


## CAT04 - Urut nama Z-A

Prioritas: Medium. Jenis: fungsional/transisi state.

Prasyarat: browser baru, situs dan driver siap; akun demo tersedia. Login valid dilakukan eksplisit jika tercantum pada langkah.

Langkah:

1. Login valid.
2. Pilih opsi za pada dropdown sort.
3. Periksa jumlah enam nama dan seluruh daftar sama dengan urutan descending.

Expected result: Seluruh daftar mengikuti urutan yang dipilih.

Implementasi: `Test Cases/CAT04_Urut_nama_Z_A`; kode `Scripts/CAT04_Urut_nama_Z_A/Script.groovy`. Status versi refactor: PENDING RETEST. Actual result dan evidence diambil dari report run baru; belum diisi.


## CAT05 - Urut harga rendah-tinggi

Prioritas: Medium. Jenis: fungsional/transisi state.

Prasyarat: browser baru, situs dan driver siap; akun demo tersedia. Login valid dilakukan eksplisit jika tercantum pada langkah.

Langkah:

1. Login valid.
2. Pilih opsi lohi pada dropdown sort.
3. Konversi seluruh harga menjadi BigDecimal, periksa jumlah enam dan ascending.

Expected result: Seluruh daftar mengikuti urutan yang dipilih.

Implementasi: `Test Cases/CAT05_Urut_harga_rendah_tinggi`; kode `Scripts/CAT05_Urut_harga_rendah_tinggi/Script.groovy`. Status versi refactor: PENDING RETEST. Actual result dan evidence diambil dari report run baru; belum diisi.


## CAT06 - Urut harga tinggi-rendah

Prioritas: Medium. Jenis: fungsional/transisi state.

Prasyarat: browser baru, situs dan driver siap; akun demo tersedia. Login valid dilakukan eksplisit jika tercantum pada langkah.

Langkah:

1. Login valid.
2. Pilih opsi hilo pada dropdown sort.
3. Konversi seluruh harga menjadi BigDecimal, periksa jumlah enam dan descending.

Expected result: Seluruh daftar mengikuti urutan yang dipilih.

Implementasi: `Test Cases/CAT06_Urut_harga_tinggi_rendah`; kode `Scripts/CAT06_Urut_harga_tinggi_rendah/Script.groovy`. Status versi refactor: PENDING RETEST. Actual result dan evidence diambil dari report run baru; belum diisi.


## CHK01 - Nama depan wajib

Prioritas: High. Jenis: negatif.

Prasyarat: browser baru, situs dan driver siap; akun demo tersedia. Login valid dilakukan eksplisit jika tercantum pada langkah.

Langkah:

1. Login valid; tambah Backpack; buka cart; klik Checkout.
2. Isi firstName, lastName dan postalCode dengan string kosong.
3. Klik Continue.
4. Periksa Error: First Name is required dan URL tetap checkout-step-one.html.

Expected result: Error: First Name is required; tetap di formulir.

Implementasi: `Test Cases/CHK01_Nama_depan_wajib`; kode `Scripts/CHK01_Nama_depan_wajib/Script.groovy`. Status versi refactor: PENDING RETEST. Actual result dan evidence diambil dari report run baru; belum diisi.


## CHK02 - Nama belakang wajib

Prioritas: High. Jenis: negatif.

Prasyarat: browser baru, situs dan driver siap; akun demo tersedia. Login valid dilakukan eksplisit jika tercantum pada langkah.

Langkah:

1. Login valid; tambah Backpack; buka cart; klik Checkout.
2. Isi firstName QA, lastName kosong, postalCode kosong.
3. Klik Continue.
4. Periksa Error: Last Name is required dan URL tetap checkout-step-one.html.

Expected result: Error: Last Name is required; tetap di formulir.

Implementasi: `Test Cases/CHK02_Nama_belakang_wajib`; kode `Scripts/CHK02_Nama_belakang_wajib/Script.groovy`. Status versi refactor: PENDING RETEST. Actual result dan evidence diambil dari report run baru; belum diisi.


## CHK03 - Kode pos wajib

Prioritas: High. Jenis: negatif.

Prasyarat: browser baru, situs dan driver siap; akun demo tersedia. Login valid dilakukan eksplisit jika tercantum pada langkah.

Langkah:

1. Login valid; tambah Backpack; buka cart; klik Checkout.
2. Isi firstName QA, lastName Tester, postalCode kosong.
3. Klik Continue.
4. Periksa Error: Postal Code is required dan URL tetap checkout-step-one.html.

Expected result: Error: Postal Code is required; tetap di formulir.

Implementasi: `Test Cases/CHK03_Kode_pos_wajib`; kode `Scripts/CHK03_Kode_pos_wajib/Script.groovy`. Status versi refactor: PENDING RETEST. Actual result dan evidence diambil dari report run baru; belum diisi.


## CHK04 - Ringkasan dan total dua produk

Prioritas: High. Jenis: fungsional/transisi state.

Prasyarat: browser baru, situs dan driver siap; akun demo tersedia. Login valid dilakukan eksplisit jika tercantum pada langkah.

Langkah:

1. Login valid; tambah Backpack dan Bike Light; masuk formulir checkout.
2. Isi QA / Tester / 12190, klik Continue.
3. Periksa overview memuat tepat dua nama produk.
4. Periksa subtotal $39.98, tax $3.20 dan total $43.18 dengan baseline independen.

Expected result: Dua item sesuai; subtotal 39.98; pajak 3.20; total 43.18.

Implementasi: `Test Cases/CHK04_Ringkasan_dan_total_dua_produk`; kode `Scripts/CHK04_Ringkasan_dan_total_dua_produk/Script.groovy`. Status versi refactor: PENDING RETEST. Actual result dan evidence diambil dari report run baru; belum diisi.


## CHK05 - Selesaikan pesanan

Prioritas: High. Jenis: fungsional/transisi state.

Prasyarat: browser baru, situs dan driver siap; akun demo tersedia. Login valid dilakukan eksplisit jika tercantum pada langkah.

Langkah:

1. Login valid; tambah Backpack; masuk formulir checkout.
2. Isi QA / Tester / 12190, klik Continue, lalu Finish.
3. Periksa checkout-complete.html dan Thank you for your order!.
4. Klik Back Home, periksa inventory.html dan badge tidak ada.

Expected result: Konfirmasi sukses; kembali ke inventory; badge kosong.

Implementasi: `Test Cases/CHK05_Selesaikan_pesanan`; kode `Scripts/CHK05_Selesaikan_pesanan/Script.groovy`. Status versi refactor: PENDING RETEST. Actual result dan evidence diambil dari report run baru; belum diisi.


## CHK06 - Batal formulir checkout

Prioritas: Medium. Jenis: fungsional/transisi state.

Prasyarat: browser baru, situs dan driver siap; akun demo tersedia. Login valid dilakukan eksplisit jika tercantum pada langkah.

Langkah:

1. Login valid; tambah Backpack; masuk formulir checkout.
2. Klik Cancel.
3. Periksa cart.html dan Backpack masih ada.

Expected result: Kembali ke cart; produk tetap ada.

Implementasi: `Test Cases/CHK06_Batal_formulir_checkout`; kode `Scripts/CHK06_Batal_formulir_checkout/Script.groovy`. Status versi refactor: PENDING RETEST. Actual result dan evidence diambil dari report run baru; belum diisi.


## CHK07 - Batal ringkasan checkout

Prioritas: Medium. Jenis: fungsional/transisi state.

Prasyarat: browser baru, situs dan driver siap; akun demo tersedia. Login valid dilakukan eksplisit jika tercantum pada langkah.

Langkah:

1. Login valid; tambah Backpack; masuk checkout overview dengan data valid.
2. Klik Cancel di overview.
3. Periksa inventory.html dan badge tetap 1.

Expected result: Kembali ke inventory; badge tetap 1.

Implementasi: `Test Cases/CHK07_Batal_ringkasan_checkout`; kode `Scripts/CHK07_Batal_ringkasan_checkout/Script.groovy`. Status versi refactor: PENDING RETEST. Actual result dan evidence diambil dari report run baru; belum diisi.


## NAV01 - Reset App State

Prioritas: Medium. Jenis: fungsional/transisi state.

Prasyarat: browser baru, situs dan driver siap; akun demo tersedia. Login valid dilakukan eksplisit jika tercantum pada langkah.

Langkah:

1. Login valid; tambah Backpack.
2. Buka sidebar, tunggu panel masuk viewport, klik Reset App State.
3. Refresh; periksa badge tidak ada.
4. Buka cart dan periksa tidak ada product row.

Expected result: Badge hilang dan cart kosong.

Implementasi: `Test Cases/NAV01_Reset_App_State`; kode `Scripts/NAV01_Reset_App_State/Script.groovy`. Status versi refactor: PENDING RETEST. Actual result dan evidence diambil dari report run baru; belum diisi.
