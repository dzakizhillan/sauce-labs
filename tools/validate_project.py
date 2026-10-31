"""Offline structural validation; does not run Katalon or claim test success."""
from pathlib import Path
import xml.etree.ElementTree as ET
import re

root = Path(__file__).resolve().parents[1]
files = [*root.rglob('*.prj'), *root.rglob('*.tc'), *root.rglob('*.ts'), *root.rglob('*.glbl'), *root.rglob('*.rs')]
for file in files:
    ET.parse(file)
cases = list((root / 'Test Cases').glob('*.tc'))
objects = {}
for file in (root / 'Object Repository').rglob('*.rs'):
    path = file.relative_to(root / 'Object Repository').with_suffix('').as_posix()
    entity = ET.parse(file).getroot()
    assert entity.tag == 'WebElementEntity', file
    assert entity.findtext('selectorMethod') == 'CSS', file
    css = entity.findtext("selectorCollection/entry[key='CSS']/value")
    assert css, file
    objects[path] = css
references = set()
for folder in ('Keywords', 'Scripts'):
    for file in (root / folder).rglob('*.groovy'):
        code = file.read_text(encoding='utf-8')
        assert 'new TestObject(' not in code, file
        assert 'Shop.' not in code, file
        assert 'import internal.GlobalVariable' not in code, f'Generated config dependency reintroduced: {file}'
        assert 'data-test=' not in code, f'Inline selector outside repository: {file}'
        for path in re.findall(r"(?:findTestObject|Ui\.(?:object|click|fill|texts))\('([^']+)'", code):
            assert path in objects, f'{file}: missing object {path}'
            references.add(path)
for case in cases:
    scripts = list((root / 'Scripts' / case.stem).glob('*.groovy'))
    assert len(scripts) == 1, case
    assert f"TestSession.run('{case.stem.split('_')[0]}')" in scripts[0].read_text(encoding='utf-8'), case
all_ids = {'Test Cases/' + file.stem for file in cases}
for suite in (root / 'Test Suites').glob('*.ts'):
    links = ET.parse(suite).findall('testCaseLink')
    ids = [link.findtext('testCaseId') for link in links]
    assert len(ids) == len(set(ids)), suite
    assert set(ids) <= all_ids, suite
    if suite.stem == 'Regression': assert set(ids) == all_ids
    for link in links:
        case = root / (link.findtext('testCaseId') + '.tc')
        assert case.is_file(), case
        scripts = root / 'Scripts' / case.stem
        assert list(scripts.glob('*.groovy')), scripts
    print(f'{suite.stem}: {len(links)} valid case links')
assert len(cases) == 26
assert len(objects) == 44
assert not (set(objects) - references), f'Unreferenced objects: {set(objects) - references}'
print(f'XML OK: {len(files)} files; 26 cases; {len(objects)} repository objects, all referenced.')
print('No inline selectors or legacy Shop calls. Runtime results must be read from Reports.')
