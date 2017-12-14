/**
 * source plugin.xml
 */

File sourceXML = new File( basedir, 'plugin.xml' )
assert sourceXML.exists()
def sourcePlugin = new XmlSlurper().parse( sourceXML )

/**
 * target plugin.xml
 */

File targetXML = new File( basedir, 'target/classes/META-INF/maven/plugin.xml' )
assert targetXML.exists()
def targetPlugin = new XmlSlurper().parse( targetXML )

/**
 * compare source and target
 */

assert sourcePlugin.mojos == targetPlugin.mojos

assert sourcePlugin.version != targetPlugin.version
