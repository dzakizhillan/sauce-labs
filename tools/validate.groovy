import org.codehaus.groovy.control.SourceUnit

File project = new File(args ? args[0] : '.')
int count = 0
['Keywords', 'Scripts', 'Test Suites', 'Test Listeners'].each { folder ->
    new File(project, folder).eachFileRecurse { file ->
        if (file.name.endsWith('.groovy')) {
            SourceUnit unit = SourceUnit.create(file.path, file.getText('UTF-8'))
            unit.parse()
            unit.completePhase()
            unit.nextPhase()
            unit.convert()
            count++
        }
    }
}
println "Groovy syntax OK: ${count} files. This is not a Katalon execution or dependency compilation."
