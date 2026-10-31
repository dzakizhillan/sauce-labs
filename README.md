# Swag Labs - Katalon Web UI Automation

Framework **Page Object Model (POM)** dengan native **Object Repository Katalon**, reusable components, test data terpusat, dan test case yang berisi skenario serta assertion. Target: Web Desktop https://www.saucedemo.com/.

## Cara Menjalankan Pengujian (Getting Started)

Ikuti langkah-langkah di bawah ini untuk membuka dan mengeksekusi automation test suite pada local environment Anda:

### 1. Prasyarat (Prerequisites)
* **Katalon Studio**: Pastikan Anda menggunakan **Katalon Studio v10.1.1** atau versi terbaru yang kompatibel.
* **Google Chrome**: Direkomendasikan menggunakan Google Chrome versi terbaru. 
* **WebDriver**: Jika terjadi masalah ketidakcocokan browser, lakukan pembaruan driver melalui menu **Tools > Update WebDrivers > Chrome** di Katalon Studio.

### 2. Mengimpor Project ke Katalon Studio
1. Klon repositori ini ke direktori lokal Anda atau unduh sebagai file ZIP lalu ekstrak.
2. Buka **Katalon Studio**.
3. Pilih menu **File > Open Project** pada toolbar atas.
4. Arahkan ke direktori folder project hasil klon (`katalon-swag-labs`), lalu klik **Select Folder**.
5. Tunggu proses *build* dan pemuatan komponen selesai.

### 3. Eksekusi Pengujian (Test Execution)
Sebelum mengeksekusi, pastikan Profile yang aktif di pojok kanan atas adalah **default**. Pengujian dirancang menggunakan skenario *clean session* di mana browser akan otomatis terbuka dan tertutup di setiap *test case*.

* **Smoke Test (Skala Kecil)**:
  1. Buka folder **Test Suites > Smoke** di Tests Explorer.
  2. Klik dua kali pada file suite tersebut.
  3. Klik tombol **Run** (ikon panah hijau) dan pilih **Chrome**. Suite ini berisi 5 *test cases* utama.
  
* **Regression Test (Skala Penuh)**:
  1. Buka folder **Test Suites > Regression**.
  2. Klik dua kali pada file suite tersebut.
  3. Klik tombol **Run** dan pilih **Chrome**. Suite ini berisi 26 *test cases* menyeluruh.

### 4. Melakukan Review Laporan (Test Reports)
* Setelah eksekusi selesai, laporan pengujian akan otomatis dibuat di folder **Reports**.
* Anda dapat melihat ringkasan hasil, log eksekusi, serta *screenshot* otomatis pada langkah *checkpoint* maupun saat terjadi kegagalan (*failure screenshots*) langsung melalui Katalon Studio UI atau membuka berkas HTML yang digenerate di direktori lokal.


Browser baru dibuka untuk setiap test case dan ditutup setelahnya. Test case juga bisa dijalankan satuan. Login Katalon menggunakan akun sendiri; jalur lokal melalui Studio UI. CLI/CI memakai Runtime Engine dengan aktivasi terpisah dan tidak diperlukan untuk tugas lokal ini.

## Di mana script dan object-nya?

| Yang dicari | Lokasi di Katalon / filesystem |
|---|---|
| Daftar test case | **Test Cases**; klik dua kali sebuah case lalu pilih tab **Script** |
| File kode skenario | `Scripts/<nama case>/Script.groovy` |
| Object yang bisa diedit | **Object Repository**; file `.rs` diurutkan per halaman |
| Aksi halaman | **Keywords > framework > pages** |
| Komponen bersama | **Keywords > framework > components** |
| Setup/teardown browser | `Keywords/framework/core/TestSession.groovy` |
| Data produk dan customer | `Keywords/framework/data/DemoData.groovy` |
| URL, username, password demo, timeout | **Profiles > default** |

Object Repository sekarang berisi **44 native TestObject**. Seluruh locator CSS berada di `.rs`, tidak lagi dibuat di helper `Shop`. Helper lama tersebut sudah dihapus. Nama `.rs` adalah kontrak yang dipanggil page object dan test case melalui `findTestObject()`.

## Contoh test case

```groovy
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import framework.core.TestSession
import framework.pages.LoginPage

TestSession.run('AUTH01') {
    LoginPage.loginValid()
    WebUI.verifyElementText(findTestObject('Common/label_title'), 'Products')
}
```

`LoginPage.loginValid()` memakai aksi halaman yang mengambil `Login/input_username`, `Login/input_password`, dan `Login/button_login` dari repository. Mengubah selector username cukup pada satu object `.rs`; semua skenario login menggunakan perubahan tersebut. Assertion khusus skenario tetap terlihat di test case.

## Dokumen

### Screenshot bukti pengujian

Screenshot checkpoint diambil setelah assertion terkait berhasil, sebelum berpindah halaman. Pada run berikutnya, Smoke menghasilkan `AUTH01-login-verified.png`, `CART01-cart-verified.png`, `CHK05-checkout-overview.png`, dan `CHK05-order-confirmed.png` bila checkpoint tersebut tercapai. Regression juga menghasilkan `CHK04-totals-verified.png` setelah pemeriksaan subtotal, pajak, dan total dua produk.

File PNG tersimpan langsung di folder report run bersama HTML dan log. Buka PNG untuk melihat gambar, atau cari langkah `takeScreenshot` pada detail log report. Screenshot kegagalan tetap memakai nama `<caseId>-failure.png`. Capture bersifat opsional: bila capture gagal, periksa log; screenshot bukan pengganti hasil assertion. Rekaman video belum diaktifkan.

Untuk menambahkan checkpoint pada test case lain, panggil `TestSession.screenshot('ID-deskripsi')` sesudah assertion. Gunakan nama unik per checkpoint. Refresh project di Katalon lalu jalankan ulang suite agar screenshot dibuat; report lama tidak berubah.

- [Arsitektur dan panduan maintenance](docs/FRAMEWORK.md): tanggung jawab layer, contoh perubahan komponen, penambahan fitur, sinkronisasi dan batas skala.
- [Katalog Object Repository](docs/OBJECT_REPOSITORY.md): 44 object, selector, parameter, dan pemakainya.
- [Test Plan](docs/TEST_PLAN.md): strategi, scope, risiko, entry/exit criteria.
- [Test Cases](docs/TEST_CASES.md): 26 kasus otomatis dan pemeriksaan manual tambahan.
- [Detail skenario dan traceability](docs/TEST_CASE_DETAILS.md): prasyarat, langkah, expected result, implementasi dan batas cakupan.
- [Status eksekusi](docs/EXECUTION_STATUS.md): bukti run sebelumnya dan status refactor sekarang.

## Spesifikasi dan troubleshooting

Environment yang ditemukan: Windows 11 x64; Katalon Studio 10.1.1, Java bundled 17.0.14, Chrome 156.0.8078.79. Driver instalasi **KS-10.1.1** telah diperbarui menjadi ChromeDriver 156.0.8078.80. Instalasi KSE lain atau laptop lain perlu driver yang sesuai sendiri.

- **SessionNotCreated / versi driver berbeda**: gunakan **Tools > Update WebDrivers > Chrome**. Perbarui saat Chrome berubah build.
- **Class Shop tidak ditemukan / AppConfig tidak ditemukan**: tutup lalu buka project dan tunggu build Keywords. Error `No such property: username for class: internal.GlobalVariable` diperbaiki dengan AppConfig yang membaca sumber profile; framework tidak lagi memakai field GlobalVariable hasil generate. Jangan edit file Libs secara manual.
- **Tertahan mencari element**: Smart Wait dinonaktifkan sekali di TestSession karena run sebelumnya tertahan pada async JavaScript. Framework memakai explicit wait dengan timeout profile. Tidak ada retry otomatis atau fallback selector yang menyembunyikan kegagalan.
- **Logout/Reset gagal**: komponen NavigationMenu menunggu sidebar masuk viewport sebelum klik. Simpan error dan screenshot bila tetap gagal; perbaikan ini belum dibuktikan lulus pada run terbaru.

## Status validasi

Refactor memiliki 26 script dan 44 object yang referensinya telah diperiksa. Sebanyak 14 class framework dan 26 script telah dikompilasi terhadap library Katalon 10.1.1 lokal; nama method WebUI juga diperiksa. AppConfig benar-benar membaca keempat nilai profile pada pemeriksaan offline, tanpa stub atau ketergantungan class GlobalVariable hasil generate. Ini belum mengeksekusi browser.

**Versi refactor belum dieksekusi di browser.** Report 24 passed / 2 failed berasal dari versi sebelum refactor. Jangan menyebut refactor lulus sebelum menjalankan ulang suite.

Pengumpulan tugas: folder project, seluruh dokumen, report asli run terbaru dan bukti environment. Registrasi/Contact tidak ditemukan pada area yang dieksplorasi dan dicatat N/A; pemeriksaan manual yang belum dilakukan tetap NOT RUN.

Referensi resmi: [Object Repository](https://docs.katalon.com/katalon-studio/test-objects/web-test-objects/manage-web-test-objects), [CLI Runtime Engine](https://docs.katalon.com/katalon-studio/execute-tests/katalon-runtime-engine/command-line-syntax-in-katalon-runtime-engine).
# Popup password Chrome saat menjalankan tes

`TestSession.groovy` menonaktifkan penyimpanan dan pemeriksaan kebocoran password pada profil browser automation sebelum browser dibuka. Pengaturan Chrome pribadi tidak diubah.

Jika run lama masih menampilkan popup, hentikan run tersebut. Refresh folder **Keywords** dengan **F5** di Tests Explorer (atau tutup dan buka kembali project), lalu jalankan **Test Suites > Smoke** dengan Chrome. Setelah Smoke lulus, jalankan **Regression** untuk seluruh kasus. Perubahan ini memerlukan sesi browser baru; tidak berlaku pada browser yang sudah terbuka.

