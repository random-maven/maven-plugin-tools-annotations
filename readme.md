
### Maven Plugin Tools Annotations / Extra

Resolve [MPLUGIN-247](https://issues.apache.org/jira/browse/MPLUGIN-247): 
provide maven plugin descriptor for maven plugins written in Scala.

[![Apache License, Version 2.0, January 2004](https://img.shields.io/github/license/mojohaus/versions-maven-plugin.svg?label=License)](http://www.apache.org/licenses/)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.carrotgarden.maven/maven-plugin-tools-annotations/badge.svg?style=plastic)](https://maven-badges.herokuapp.com/maven-central/com.carrotgarden.maven/maven-plugin-tools-annotations)
[![Download](https://api.bintray.com/packages/random-maven/maven/maven-plugin-tools-annotations/images/download.svg)](https://bintray.com/random-maven/maven/maven-plugin-tools-annotations/_latestVersion)
[![Travis Status](https://travis-ci.org/random-maven/maven-plugin-tools-annotations.svg?branch=master)](https://travis-ci.org/random-maven/maven-plugin-tools-annotations/builds)

Usage examples:
* [bintray-maven-plugin](https://github.com/random-maven/bintray-maven-plugin)
* [scalor-maven-plugin](https://github.com/random-maven/scalor-maven-plugin)

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
