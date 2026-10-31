package framework.core

import com.kms.katalon.core.configuration.RunConfiguration
import com.kms.katalon.core.main.TestCaseMain
import javax.xml.parsers.DocumentBuilderFactory

/** Reads profile source files, independent of stale generated fields/runtime maps. */
class AppConfig {
    private static Map readProfile(String name) {
        if (!(name ==~ /[A-Za-z0-9 _-]+/)) throw new IllegalArgumentException('Invalid profile name')
        File file = new File(RunConfiguration.getProjectDir(), 'Profiles/' + name + '.glbl')
        if (!file.isFile()) throw new IllegalStateException('Profile file not found: ' + file.path)
        def factory = DocumentBuilderFactory.newInstance()
        factory.setFeature('http://apache.org/xml/features/disallow-doctype-decl', true)
        def document = factory.newDocumentBuilder().parse(file)
        def entities = document.getElementsByTagName('GlobalVariableEntity')
        Map result = [:]
        for (int i = 0; i < entities.length; i++) {
            def entity = entities.item(i)
            String key = entity.getElementsByTagName('name').item(0).textContent
            if (!(key in ['baseUrl', 'username', 'password', 'timeout'])) continue
            String literal = entity.getElementsByTagName('initValue').item(0).textContent.trim()
            if (literal ==~ /[0-9]+/) result[key] = Integer.parseInt(literal)
            else if (literal.size() >= 2 && ((literal.startsWith("'") && literal.endsWith("'")) || (literal.startsWith('"') && literal.endsWith('"')))) {
                result[key] = literal.substring(1, literal.length() - 1)
            } else throw new IllegalStateException('Expected a quoted string or integer for profile value: ' + key)
        }
        return result
    }

    static Map profileValues() {
        Map values = readProfile('default')
        String selected = RunConfiguration.getExecutionProfile()
        if (selected && selected != 'default') values.putAll(readProfile(selected))
        values.putAll(TestCaseMain.getParsedValues(RunConfiguration.getOverridingParameters(), values))
        return values
    }

    static Object required(String name) {
        Map values = profileValues()
        if (!values.containsKey(name) || values[name] == null || values[name].toString().trim().isEmpty()) {
            throw new IllegalStateException('Missing required execution profile value: ' + name)
        }
        return values[name]
    }

    static String getBaseUrl() { required('baseUrl').toString() }
    static String getUsername() { required('username').toString() }
    static String getPassword() { required('password').toString() }
    static int getTimeout() {
        int seconds = Integer.parseInt(required('timeout').toString())
        if (seconds <= 0) throw new IllegalStateException('Profile timeout must be positive')
        return seconds
    }

    static void validate() {
        if (!getBaseUrl().startsWith('https://') || !getBaseUrl().endsWith('/')) {
            throw new IllegalStateException('Profile baseUrl must use HTTPS and end with /')
        }
        getUsername()
        getPassword()
        getTimeout()
    }
}
