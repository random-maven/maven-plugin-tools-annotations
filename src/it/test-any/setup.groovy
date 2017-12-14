/**
 * source plugin.xml
 */

File sourceXML = new File( basedir, 'plugin.xml' )
assert sourceXML.exists()

/**
 * target plugin.xml
 */

File targetXML = new File( basedir, 'target/classes/META-INF/maven/plugin.xml' )
assert !targetXML.exists()
