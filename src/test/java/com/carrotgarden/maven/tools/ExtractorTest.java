package com.carrotgarden.maven.tools;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.artifact.DefaultArtifact;
import org.apache.maven.artifact.handler.ArtifactHandler;
import org.apache.maven.model.Build;
import org.apache.maven.model.Model;
import org.apache.maven.plugin.descriptor.MojoDescriptor;
import org.apache.maven.plugin.descriptor.PluginDescriptor;

import org.apache.maven.project.MavenProject;
import org.apache.maven.repository.RepositorySystem;
import org.apache.maven.tools.plugin.DefaultPluginToolsRequest;
import org.apache.maven.tools.plugin.PluginToolsRequest;
import org.apache.maven.tools.plugin.extractor.MojoDescriptorExtractor;
import org.apache.maven.tools.plugin.extractor.annotations.scanner.DefaultMojoAnnotationsScanner;
import org.apache.maven.tools.plugin.extractor.annotations.scanner.MojoAnnotatedClass;
import org.apache.maven.tools.plugin.extractor.annotations.scanner.MojoAnnotationsScanner;
import org.apache.maven.tools.plugin.extractor.annotations.scanner.MojoAnnotationsScannerRequest;
import org.codehaus.plexus.PlexusTestCase;
import org.codehaus.plexus.component.repository.ComponentDependency;
import org.codehaus.plexus.logging.Logger;
import org.codehaus.plexus.logging.console.ConsoleLogger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class ExtractorTest extends PlexusTestCase {

	Artifact mavenArtifact(String groupId, String artifactId, String version) {
		String scope = null;
		String type = "jar";
		String classifier = "";
		ArtifactHandler artifactHandler = null;
		Artifact artifact = new DefaultArtifact(groupId, artifactId, version, scope, type, classifier, artifactHandler);
		return artifact;
	}

	MavenProject mavenProject() {
		MavenProject project = new MavenProject();
		Artifact artifact = mavenArtifact("gid-main", "aid-main", "0");
		project.setArtifact(artifact);
		return project;
	}

	Set<Artifact> mavenDependencies() {
		Set<Artifact> dependencies = new HashSet<>();
		Artifact artifact = mavenArtifact("gid-dep", "aid-dep", "0");
		File destination = new File("src/test/resources/scala-js-junit-tools_2.12-1.1.1.20180225204138.jar");
		artifact.setFile(destination);
		dependencies.add(artifact);
		assertTrue(artifact.getFile().exists());
		return dependencies;
	}

	@Test
	public void testScan() throws Exception {

		DefaultMojoAnnotationsScanner scanner = new DefaultMojoAnnotationsScanner();
		Logger logger = new ConsoleLogger();
		scanner.enableLogging(logger);

		List<File> classesDirectories = new ArrayList<>();
		classesDirectories.add(new File("target/classes"));
		classesDirectories.add(new File("target/test-classes"));

		MojoAnnotationsScannerRequest request = new MojoAnnotationsScannerRequest();
		request.setProject(mavenProject());
		// request.setDependencies(mavenDependencies());
		request.setClassesDirectories(classesDirectories);

		Map<String, MojoAnnotatedClass> result = scanner.scan(request);

		result.values().forEach(entry -> //
		logger.info("result: klaz=" + entry.getClassName() + "  meta=" + entry.getMojo()));

		MojoAnnotatedClass testMojo = result.get("test.TestMojo");
		assertTrue(testMojo != null);

	}

}
