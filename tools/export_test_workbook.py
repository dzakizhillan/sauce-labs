"""Export a native Excel workbook using only the Python standard library."""
from pathlib import Path
import re
from xml.sax.saxutils import escape
from zipfile import ZipFile, ZIP_DEFLATED
import xml.etree.ElementTree as ET

ROOT = Path(__file__).resolve().parents[1]
headers = ['Test Scenario ID', 'Test Scenario Desc', 'Test Case ID', 'Testing Type',
           'Test Case Description', 'Test Steps', 'Preconditions', 'Test Data',
           'Expected Result', 'Actual Result', 'Status', 'Execution Date Start',
           'Execution Date Finish', 'Duration']
groups = {'AUTH': ('TS-001', 'Memvalidasi autentikasi dan pembatasan akses pengguna.'),
          'CAT': ('TS-002', 'Memvalidasi katalog, detail, dan pengurutan produk.'),
          'CART': ('TS-003', 'Memvalidasi isi, perubahan, dan persistensi keranjang.'),
          'CHK': ('TS-004', 'Memvalidasi formulir, ringkasan, pembatalan, dan penyelesaian checkout.'),
          'NAV': ('TS-005', 'Memvalidasi reset state aplikasi.')}
rows, mapping = [], [['Test Case ID', 'ID Project', 'Implementasi / metode', 'Prioritas']]
source = (ROOT / 'docs/TEST_CASE_DETAILS.md').read_text(encoding='utf-8')
for code, title, body in re.findall(r'^## ([A-Z]+\d+) - ([^\n]+)\n(.*?)(?=^## |\Z)', source, re.M | re.S):
    scenario, description = groups[re.match(r'[A-Z]+', code)[0]]
    steps = re.search(r'Langkah:\s*(.*?)\n\s*Expected result:', body, re.S)[1].strip()
    steps = steps.replace('melalui LoginPage', 'pada halaman login')
    expected = re.search(r'Expected result: (.*)', body)[1]
    case = f'TC-{len(rows)+1:03d}'
    data = 'standard_user / secret_sauce'
    if code == 'AUTH02': data = 'standard_user / wrong_password'
    if code == 'AUTH03': data = 'Username kosong; password kosong'
    if code == 'AUTH04': data = 'standard_user; password kosong'
    if code == 'AUTH05': data = 'locked_out_user / secret_sauce'
    if code == 'AUTH07': data = 'https://www.saucedemo.com/inventory.html; tanpa login'
    if code.startswith(('CART', 'CHK', 'NAV')) or code == 'CAT02':
        data += '\nSauce Labs Backpack: $29.99'
    if code in ('CART02', 'CHK04'): data += '\nSauce Labs Bike Light: $9.99'
    if code.startswith('CHK'):
        customer = {'CHK01': '(kosong) / Tester / 12190', 'CHK02': 'QA / (kosong) / 12190',
                    'CHK03': 'QA / Tester / (kosong)'}.get(code, 'QA / Tester / 12190')
        data += '\nFirst Name / Last Name / Postal Code: ' + customer
    if code in ('CAT03', 'CAT04', 'CAT05', 'CAT06'):
        data += '\nSort: ' + {'CAT03':'Name (A to Z)', 'CAT04':'Name (Z to A)', 'CAT05':'Price (low to high)', 'CAT06':'Price (high to low)'}[code]
    negative = code in ('AUTH02','AUTH03','AUTH04','AUTH05','AUTH07','CHK01','CHK02','CHK03')
    rows.append([scenario, description, case, 'Negative' if negative else 'Positive',
                 title + '.', '0. Buka https://www.saucedemo.com/ pada sesi browser baru.\n' + steps,
                 'Situs dapat diakses; browser/driver sesuai; sesi baru; katalog standar; akun demo tersedia.',
                 data, expected, '', '4-Not Run', '', '', ''])
    implementation = re.search(r'Implementasi: `([^`]+)`', body)[1]
    mapping.append([case, code, implementation, re.search(r'Prioritas: (\w+)', body)[1]])
assert len(rows) == 26

manual = [
 ('TS-002','Memvalidasi konsistensi seluruh detail produk.','Positive','Periksa detail keenam produk.',
  '1. Login.\n2. Buka setiap produk dari katalog.\n3. Bandingkan nama dan harga.\n4. Periksa gambar dan deskripsi.\n5. Kembali ke katalog; ulangi untuk semua produk.',
  'Enam produk katalog standar.', 'Nama dan harga konsisten; gambar berhasil dimuat; deskripsi tersedia; kembali ke katalog berhasil.'),
 ('TS-006','Memvalidasi akses keyboard.','Positive','Navigasi fungsi utama menggunakan keyboard.',
  '1. Buka login.\n2. Gunakan Tab/Shift+Tab untuk berpindah field.\n3. Login dengan keyboard.\n4. Navigasikan katalog, keranjang, dan checkout dengan keyboard.',
  'Keyboard; standard_user / secret_sauce; QA / Tester / 12190.', 'Fokus terlihat; kontrol utama dapat dijangkau; tidak ada jebakan fokus; aksi dapat dilakukan tanpa mouse.'),
 ('TS-007','Memvalidasi kompatibilitas browser desktop.','Positive','Jalankan alur pembelian pada Edge dan dua ukuran viewport.',
  '1. Buka Edge pada 1366x768.\n2. Login, tambah Backpack, checkout, selesaikan pesanan.\n3. Ulangi pada 1920x1080.\n4. Periksa tampilan dan hasil tiap langkah.',
  'Edge; 1366x768 dan 1920x1080; standard_user / secret_sauce; QA / Tester / 12190.', 'Kontrol dapat dipakai tanpa tertutup; isi keranjang benar; checkout menampilkan konfirmasi.'),
 ('TS-003','Mengeksplorasi persistensi lintas sesi.','Exploratory','Catat isi keranjang setelah logout dan login ulang.',
  '1. Login.\n2. Tambahkan Backpack.\n3. Logout.\n4. Login kembali.\n5. Catat badge dan isi keranjang.',
  'standard_user / secret_sauce; Backpack.', 'Kumpulkan perilaku aktual; aturan persistensi lintas sesi perlu dikonfirmasi sebelum memberi Passed/Failed.'),
 ('TS-004','Mengeksplorasi aturan checkout.','Exploratory','Checkout tanpa produk.',
  '1. Login pada sesi baru.\n2. Buka keranjang kosong.\n3. Klik Checkout.\n4. Bila formulir terbuka, isi data valid dan lanjutkan.\n5. Catat perilaku hingga tahap terakhir yang dapat dicapai.',
  'Keranjang kosong; QA / Tester / 12190.', 'Catat hasil; requirement checkout kosong belum tersedia sehingga keputusan Passed/Failed menunggu konfirmasi.'),
 ('TS-004','Mengeksplorasi aturan input checkout.','Exploratory','Periksa whitespace, panjang input, dan simbol secara terpisah.',
  '1. Login dan checkout Backpack.\n2. Isi satu field dengan variasi data, dua field lain valid.\n3. Klik Continue dan catat hasil.\n4. Ulangi pada sesi baru untuk tiap field dan variasi.',
  'Variasi: tiga spasi; 256 huruf A; !@#; baseline QA / Tester / 12190.', 'Catat validasi dan penyimpanan tiap variasi; batas panjang dan karakter yang diizinkan perlu konfirmasi requirement.'),
]
for scenario, desc, kind, title, steps, data, expected in manual:
    case = f'TC-{len(rows)+1:03d}'
    rows.append([scenario, desc, case, kind, title, steps, 'Situs tersedia; sesi browser baru; akun demo tersedia.', data, expected, '', '4-Not Run', '', '', ''])
    mapping.append([case, 'Manual', 'Belum diotomatisasi; eksplorasi tidak diberi Passed tanpa requirement.', 'Medium'])
for feature, scenario in [('Registrasi akun', 'TS-008'), ('Formulir kontak', 'TS-009')]:
    case = f'TC-{len(rows)+1:03d}'
    rows.append([scenario, 'Menentukan ketersediaan fitur ' + feature.lower() + '.', case, 'Scope Review',
                 'Tinjau cakupan ' + feature.lower() + '.',
                 '1. Periksa halaman login.\n2. Login dan periksa katalog, sidebar, serta footer.\n3. Cari akses fitur.\n4. Jika ditemukan, revisi cakupan dan susun kasus functional terpisah.',
                 'Situs tersedia; akun standard_user tersedia.', 'https://www.saucedemo.com/',
                 'Fitur yang tersedia mendapat kasus functional; fitur yang tidak tersedia dicatat N/A dengan alasan.',
                 'Dokumen eksplorasi 31/10/2026 mencatat fitur tidak ditemukan pada area yang diperiksa. Bukan hasil eksekusi automation.',
                 '5-N/A', '', '', ''])
    mapping.append([case, 'Scope', 'N/A berdasarkan eksplorasi terdokumentasi; tinjau kembali jika aplikasi berubah.', 'Medium'])

notes = [['Topik', 'Keterangan'],
 ['Tujuan workbook', 'Format 14 kolom mengikuti template pengguna. Test Cases merupakan lembar siklus eksekusi baru, bukan salinan hasil run historis.'],
 ['Status', '1-Passed; 2-Failed; 3-Blocked; 4-Not Run; 5-N/A; 6-Error. Error framework/environment dibedakan dari assertion Failed.'],
 ['Pengisian hasil', 'Isi actual result berdasarkan pengamatan/report, bukan menyalin expected result. Sertakan path report atau screenshot.'],
 ['Tanggal dan durasi', 'Isi waktu mulai/selesai sebagai tanggal Excel, format dd/mm/yyyy hh:mm:ss, zona Asia/Jakarta. Duration otomatis selisih finish-start.'],
 ['Riwayat', 'Report lama tetap berada di Reports. Status 4-Not Run berarti belum dicatat pada siklus workbook ini, bukan berarti kasus tidak pernah dijalankan.'],
 ['Cakupan', '26 kasus otomatis, 6 kasus manual/eksplorasi, 2 tinjauan fitur N/A. Dynamic Catalog belum tercakup.'],
 ['ID', 'TC-001 dst merupakan ID dokumen. Sheet Mapping menghubungkannya dengan AUTH/CAT/CART/CHK/NAV di Katalon.'],
 ['Data demo', 'Kredensial adalah data publik SauceDemo; bukan akun pribadi. Katalog standar memakai standard_user.'],
 ['Batasan', 'Kasus eksplorasi dengan requirement belum jelas tidak boleh dinyatakan Passed/Failed sebelum expected behavior disepakati.']]
plan = [['Bagian', 'Rencana Tes']]
for section in re.split(r'^## ', (ROOT/'docs/TEST_PLAN.md').read_text(encoding='utf-8'), flags=re.M):
    title, _, content = section.partition('\n')
    plan.append([title.lstrip('# '), content.strip()])
plan.append(['Status workbook', 'Status aktual untuk siklus baru ada pada Test Cases. Pernyataan historis dalam sumber rencana bukan hasil terbaru.'])

def col(n):
    result = ''
    while n:
        n, r = divmod(n-1, 26); result = chr(65+r) + result
    return result

def sheet(data, widths, test=False):
    out = ['<worksheet xmlns="http://schemas.openxmlformats.org/spreadsheetml/2006/main"><sheetViews><sheetView workbookViewId="0"><pane xSplit="3" ySplit="1" topLeftCell="D2" activePane="bottomRight" state="frozen"/></sheetView></sheetViews><cols>']
    out += [f'<col min="{i}" max="{i}" width="{w}" customWidth="1"/>' for i,w in enumerate(widths,1)]
    out.append('</cols><sheetData>')
    for ri,row in enumerate(data,1):
        out.append(f'<row r="{ri}" ht="{32 if ri==1 else (140 if test else 100)}" customHeight="1">')
        for ci,value in enumerate(row,1):
            ref = f'{col(ci)}{ri}'
            style = 1 if ri==1 else (3 if test and ci in (12,13) else 2)
            if test and ci==14 and ri>1:
                out.append(f'<c r="{ref}" s="4"><f>IF(OR(L{ri}="",M{ri}=""),"",M{ri}-L{ri})</f></c>')
            else:
                out.append(f'<c r="{ref}" s="{style}" t="inlineStr"><is><t xml:space="preserve">{escape(str(value))}</t></is></c>')
        out.append('</row>')
    out.append(f'</sheetData><autoFilter ref="A1:{col(len(data[0]))}{len(data)}"/>')
    if test:
        out.append(f'<dataValidations count="1"><dataValidation type="list" allowBlank="1" showErrorMessage="1" sqref="K2:K{len(data)}"><formula1>"1-Passed,2-Failed,3-Blocked,4-Not Run,5-N/A,6-Error"</formula1></dataValidation></dataValidations>')
    out.append('<pageSetup orientation="landscape" paperSize="9"/></worksheet>')
    return ''.join(out)

sheets = [('Test Cases', [headers]+rows, [19,42,17,18,45,75,45,42,65,55,19,25,25,18]),
          ('Test Plan',plan,[28,120]), ('Mapping',mapping,[18,22,85,20]), ('Panduan',notes,[28,120])]
styles = '''<styleSheet xmlns="http://schemas.openxmlformats.org/spreadsheetml/2006/main"><numFmts count="2"><numFmt numFmtId="164" formatCode="dd/mm/yyyy hh:mm:ss"/><numFmt numFmtId="165" formatCode="[h]:mm:ss"/></numFmts><fonts count="2"><font><sz val="11"/><name val="Calibri"/></font><font><b/><color rgb="FFFFFFFF"/><sz val="11"/><name val="Calibri"/></font></fonts><fills count="3"><fill><patternFill patternType="none"/></fill><fill><patternFill patternType="gray125"/></fill><fill><patternFill patternType="solid"><fgColor rgb="FF203864"/><bgColor indexed="64"/></patternFill></fill></fills><borders count="1"><border/></borders><cellStyleXfs count="1"><xf/></cellStyleXfs><cellXfs count="5"><xf/><xf fontId="1" fillId="2" applyAlignment="1"><alignment vertical="center" wrapText="1"/></xf><xf applyAlignment="1"><alignment vertical="top" wrapText="1"/></xf><xf numFmtId="164" applyNumberFormat="1" applyAlignment="1"><alignment vertical="top" wrapText="1"/></xf><xf numFmtId="165" applyNumberFormat="1" applyAlignment="1"><alignment vertical="top"/></xf></cellXfs></styleSheet>'''
output = ROOT/'docs/Swag_Labs_Test_Plan_and_Cases.xlsx'
with ZipFile(output,'w',ZIP_DEFLATED) as z:
    overrides = ''.join(f'<Override PartName="/xl/worksheets/sheet{i}.xml" ContentType="application/vnd.openxmlformats-officedocument.spreadsheetml.worksheet+xml"/>' for i in range(1,len(sheets)+1))
    z.writestr('[Content_Types].xml', '<Types xmlns="http://schemas.openxmlformats.org/package/2006/content-types"><Default Extension="rels" ContentType="application/vnd.openxmlformats-package.relationships+xml"/><Default Extension="xml" ContentType="application/xml"/><Override PartName="/xl/workbook.xml" ContentType="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet.main+xml"/><Override PartName="/xl/styles.xml" ContentType="application/vnd.openxmlformats-officedocument.spreadsheetml.styles+xml"/>'+overrides+'</Types>')
    z.writestr('_rels/.rels','<Relationships xmlns="http://schemas.openxmlformats.org/package/2006/relationships"><Relationship Id="rId1" Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/officeDocument" Target="xl/workbook.xml"/></Relationships>')
    z.writestr('xl/workbook.xml','<workbook xmlns="http://schemas.openxmlformats.org/spreadsheetml/2006/main" xmlns:r="http://schemas.openxmlformats.org/officeDocument/2006/relationships"><sheets>'+''.join(f'<sheet name="{name}" sheetId="{i}" r:id="rId{i}"/>' for i,(name,_,_) in enumerate(sheets,1))+'</sheets><calcPr fullCalcOnLoad="1"/></workbook>')
    z.writestr('xl/_rels/workbook.xml.rels','<Relationships xmlns="http://schemas.openxmlformats.org/package/2006/relationships">'+''.join(f'<Relationship Id="rId{i}" Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/worksheet" Target="worksheets/sheet{i}.xml"/>' for i in range(1,len(sheets)+1))+'<Relationship Id="styles" Type="http://schemas.openxmlformats.org/officeDocument/2006/relationships/styles" Target="styles.xml"/></Relationships>')
    z.writestr('xl/styles.xml',styles)
    for i,(_,data,widths) in enumerate(sheets,1): z.writestr(f'xl/worksheets/sheet{i}.xml',sheet(data,widths,i==1))
with ZipFile(output) as z:
    assert z.testzip() is None
    for name in z.namelist(): ET.fromstring(z.read(name))
assert len({r[2] for r in rows}) == len(rows)
assert all(len(r)==14 for r in rows)
print(f'Created {output}: {len(rows)} cases, {len(sheets)} sheets; XML and IDs validated.')
