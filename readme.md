
### Maven Plugin Tools Annotations / Extra

Resolve [MPLUGIN-247](https://issues.apache.org/jira/browse/MPLUGIN-247): 
provide maven plugin descriptor for maven plugins written in Scala.

[![Project License][licence_icon]][licence_link]
[![Travis Status][travis_icon]][travis_link]
[![Project Files][tokei_files_icon]][tokei_basic_link]
[![Project Lines][tokei_lines_icon]][tokei_basic_link]
[![Lines of Code][tokei_basic_icon]][tokei_basic_link]

| Install  | Production Release | Development Release |
|----------|--------------------|---------------------|
| Artifact | [![Central][central_icon]][central_link] | [![Bintray][bintray_icon]][bintray_link] | 

Usage examples:
* [bintray-maven-plugin](https://github.com/random-maven/bintray-maven-plugin)
* [maven-plugin-tools-annotations](https://github.com/random-maven/maven-plugin-tools-annotations)

Configuration examples:

```xml
            <!-- Generate plugin.xml descriptor. -->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-plugin-plugin</artifactId>
                <dependencies>
                   <!-- Provide custom extractor. -->
                    <dependency>
                        <groupId>com.carrotgarden.maven</groupId>
                        <artifactId>maven-plugin-tools-annotations</artifactId>
                        <version>[1,2)</version>
                    </dependency>
                </dependencies>
                <configuration>
                    <goalPrefix>bintray</goalPrefix>
                    <extractors>
                        <!-- Use only custom extractor. -->
                        <extractor>java-annotations-extra</extractor>
                    </extractors>
                </configuration>
            </plugin>

```

### Build yourself

```
cd /tmp
git clone git@github.com:random-maven/maven-plugin-tools-annotations.git
cd maven-plugin-tools-annotations
./mvnw.sh clean install -B -P skip-test
```



[licence_icon]: https://img.shields.io/github/license/random-maven/maven-plugin-tools-annotations.svg?label=License
[licence_link]: http://www.apache.org/licenses/

[travis_icon]: https://travis-ci.org/random-maven/maven-plugin-tools-annotations.svg
[travis_link]: https://travis-ci.org/random-maven/maven-plugin-tools-annotations/builds

[tokei_files_icon]: https://tokei.rs/b1/github/random-maven/maven-plugin-tools-annotations?category=files 
[tokei_lines_icon]: https://tokei.rs/b1/github/random-maven/maven-plugin-tools-annotations?category=lines 
[tokei_basic_icon]: https://tokei.rs/b1/github/random-maven/maven-plugin-tools-annotations
[tokei_basic_link]: https://github.com/random-maven/maven-plugin-tools-annotations 

[central_icon]: https://maven-badges.herokuapp.com/maven-central/com.carrotgarden.maven/maven-plugin-tools-annotations/badge.svg?style=plastic
[central_link]: https://maven-badges.herokuapp.com/maven-central/com.carrotgarden.maven/maven-plugin-tools-annotations

[bintray_icon]: https://api.bintray.com/packages/random-maven/maven/maven-plugin-tools-annotations/images/download.svg
[bintray_link]: https://bintray.com/random-maven/maven/maven-plugin-tools-annotations/_latestVersion
