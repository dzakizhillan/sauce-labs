import groovy.lang.GroovyClassLoader
import org.codehaus.groovy.control.CompilerConfiguration
import org.codehaus.groovy.control.CompilationUnit
import groovy.io.FileType

// Type/API compilation only. Does not execute test cases.
File project = new File(args[0]).canonicalFile
File installation = new File(args[1]).canonicalFile
File output = new File(project, 'tools/.compile-check')
output.mkdirs()
CompilerConfiguration config = new CompilerConfiguration()
config.targetDirectory = output
GroovyClassLoader loader = new GroovyClassLoader(this.class.classLoader, config)
['plugins', 'configuration/resources/lib'].each { directory ->
new File(installation, directory).eachFileRecurse(FileType.FILES) { file ->
    if (file.name.endsWith('.jar') && !file.name.contains('-sources') && !file.name.contains('-javadoc')) loader.addClasspath(file.path)
}
}
CompilationUnit unit = new CompilationUnit(config, null, loader)
// Compile real project sources. Do not replace generated config with a stub.
int count = 0
new File(project, 'Keywords').eachFileRecurse(FileType.FILES) { file ->
    if (file.name.endsWith('.groovy')) { unit.addSource(file); count++ }
}
int scripts = 0
new File(project, 'Scripts').eachFileRecurse(FileType.FILES) { file ->
    if (file.name.endsWith('.groovy')) {
        unit.addSource(file.parentFile.name + '.groovy', file.getText('UTF-8'))
        scripts++
    }
}
def webUI = loader.loadClass('com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords')
Set methods = webUI.methods.collect { it.name }.toSet()
['Keywords', 'Scripts'].each { directory ->
    new File(project, directory).eachFileRecurse(FileType.FILES) { file ->
        if (file.name.endsWith('.groovy')) {
            (file.getText('UTF-8') =~ /WebUI\.(\w+)\s*\(/).each { match ->
                assert methods.contains(match[1]): "Unknown Katalon WebUI method ${match[1]} in ${file}"
            }
        }
    }
}
unit.compile()
println "Compilation OK: ${count} framework classes + ${scripts} scripts against installed Katalon libraries; WebUI method names verified. No browser executed."
loader.addClasspath(output.path)
def runtimeConfig = loader.loadClass('com.kms.katalon.core.configuration.RunConfiguration')
runtimeConfig.setExecutionSetting([projectDir: project.path])
def appConfig = loader.loadClass('framework.core.AppConfig')
appConfig.validate()
assert appConfig.timeout == 15
assert appConfig.baseUrl == 'https://www.saucedemo.com/'
assert appConfig.username == 'standard_user'
assert appConfig.password == 'secret_sauce'
println 'Profile source check OK: all four real default values resolved without generated GlobalVariable or stub.'
