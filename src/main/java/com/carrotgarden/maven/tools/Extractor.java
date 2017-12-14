package com.carrotgarden.maven.tools;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import org.apache.maven.artifact.Artifact;
import org.apache.maven.plugin.descriptor.InvalidParameterException;
import org.apache.maven.plugin.descriptor.MojoDescriptor;
import org.apache.maven.plugin.descriptor.PluginDescriptor;
import org.apache.maven.project.MavenProject;
import org.apache.maven.tools.plugin.PluginToolsRequest;
import org.apache.maven.tools.plugin.extractor.ExtractionException;
import org.apache.maven.tools.plugin.extractor.MojoDescriptorExtractor;
import org.apache.maven.tools.plugin.extractor.annotations.JavaAnnotationsMojoDescriptorExtractor;
import org.apache.maven.tools.plugin.extractor.annotations.datamodel.MojoAnnotationContent;
import org.apache.maven.tools.plugin.extractor.annotations.datamodel.ParameterAnnotationContent;
import org.apache.maven.tools.plugin.extractor.annotations.scanner.MojoAnnotatedClass;

/**
 * Extract plugin descriptor from both maven standard and extractor custom
 * annotations.
 */
@org.codehaus.plexus.component.annotations.Component( //
		role = MojoDescriptorExtractor.class, //
		hint = A.extractor)
public class Extractor extends JavaAnnotationsMojoDescriptorExtractor implements A {

	/**
	 * Code reuse: call private parent method.
	 */
	@SuppressWarnings("unchecked")
	public Map<String, MojoAnnotatedClass> scanAnnotations(PluginToolsRequest request) throws ExtractionException {
		try {
			Method method = this.getClass().getSuperclass() //
					.getDeclaredMethod("scanAnnotations", new Class[] { PluginToolsRequest.class });
			method.setAccessible(true);
			return (Map<String, MojoAnnotatedClass>) method.invoke(this, request);
		} catch (Throwable e) {
			throw new ExtractionException("scanAnnotations", e);
		}
	}

	/**
	 * Code reuse: call private parent method.
	 */
	@SuppressWarnings("unchecked")
	public List<MojoDescriptor> toMojoDescriptors(Map<String, MojoAnnotatedClass> mojoAnnotatedClasses,
			PluginDescriptor pluginDescriptor) throws InvalidParameterException {
		try {
			Method method = this.getClass().getSuperclass() //
					.getDeclaredMethod("toMojoDescriptors", new Class[] { Map.class, PluginDescriptor.class });
			method.setAccessible(true);
			return (List<MojoDescriptor>) method.invoke(this, mojoAnnotatedClasses, pluginDescriptor);
		} catch (Throwable e) {
			throw new InvalidParameterException("toMojoDescriptors", e);
		}
	}

	/**
	 * Provide project build class path.
	 */
	public ClassLoader projectClasLoader(PluginToolsRequest request) throws Exception {
		MavenProject project = request.getProject();
		File outputFolder = new File(project.getBuild().getOutputDirectory());
		Stream<URL> outputStream = Stream.of(outputFolder.toURI().toURL());
		Set<Artifact> artifactList = project.getArtifacts();
		Stream<URL> artifactStream = artifactList.stream().map(artifact -> {
			try {
				return artifact.getFile().toURI().toURL();
			} catch (Throwable e) {
				throw new RuntimeException(e);
			}
		});
		Stream<URL> projectStream = Stream.concat(outputStream, artifactStream);
		URL[] elementArray = projectStream.toArray(URL[]::new);
		URLClassLoader loader = new URLClassLoader(elementArray);
		return loader;
	}

	/**
	 * Extractor logger prefix.
	 */
	public String prefix = "[extra] ";

	/**
	 * Report at debugging level.
	 */
	public void dbug(String line) {
		getLogger().debug(prefix + line);
	}

	/**
	 * Report at information level.
	 */
	public void info(String line) {
		getLogger().info(prefix + line);
	}

	/**
	 * Report at warning level.
	 */
	public void warn(String line) {
		getLogger().warn(prefix + line);
	}

	/**
	 * Logger line size limit.
	 */
	public int trimSize = 50;

	/**
	 * Logger line cleanup regular expression.
	 */
	public String trimReplace = "[\n\t ]+";

	/**
	 * Produce description digest.
	 */
	public String trimText(String text) {
		text = text.replaceAll(trimReplace, " ");
		text = text.substring(0, Math.min(text.length(), trimSize));
		return text;
	}

	/**
	 * Produce description digest.
	 */
	public String summaryText(String text) {
		return "[" + trimText(text) + "]";
	}

	/**
	 * Locate annotation in separate class loader by class name.
	 */
	public Annotation annotation(Annotation[] annoList, Class<?> annoClass) throws Exception {
		for (Annotation anno : annoList) {
			if (anno.annotationType().getCanonicalName().equals(annoClass.getCanonicalName())) {
				return anno;
			}
		}
		return null;
	}

	/**
	 * Locate member annotation in separate class loader.
	 */
	public Annotation annotation(AccessibleObject member, Class<?> annoClass) throws Exception {
		return annotation(member.getAnnotations(), annoClass);
	}

	/**
	 * Locate class annotation in separate class loader.
	 */
	public Annotation annotation(Class<?> klaz, Class<?> annoClass) throws Exception {
		return annotation(klaz.getAnnotations(), annoClass);
	}

	/**
	 * Locate annotation value in separate class loader.
	 */
	@SuppressWarnings("unchecked")
	public <T> T annotationValue(Annotation anno, String fieldName) throws Exception {
		return (T) anno.annotationType().getMethod(fieldName).invoke(anno);
	}

	/**
	 * Null safe text trim.
	 */
	public String trim(String text) {
		if (text == null) {
			return "";
		} else {
			return text.trim();
		}
	}

	/**
	 * Extract annotation {@link Description#value()}.
	 */
	public String annotationValue(Annotation anno) throws Exception {
		return trim(annotationValue(anno, "value"));
	}

	/**
	 * Update Mojo description from custom annotation.
	 */
	public void updateMojoDescription(Class<?> klaz, MojoAnnotatedClass annoClass) throws Exception {
		final String className = annoClass.getClassName();
		final Annotation klazAnno = annotation(klaz, Description.class);
		if (klazAnno == null) {
			warn("class: " + className + " " + summaryText("<no description>"));
		} else {
			final String text = annotationValue(klazAnno);
			MojoAnnotationContent mojo = annoClass.getMojo();
			if (mojo.getDescription() != null) {
				warn("replacing existing mojo description");
			}
			annoClass.getMojo().setDescription(text);
			info("class: " + className + " " + summaryText(text));
		}
	}

	/**
	 * Update Parameter description from custom annotation.
	 */
	public void updateParameterDescription(Class<?> klaz, MojoAnnotatedClass annoClass) throws Exception {
		for (ParameterAnnotationContent annoParam : annoClass.getParameters().values()) {
			final String fieldName = annoParam.getFieldName();
			final Field field = klaz.getDeclaredField(fieldName);
			final Annotation fieldAnno = annotation(field, Description.class);
			if (fieldAnno == null) {
				warn("   field: " + fieldName + " " + summaryText("<no description>"));
			} else {
				final String text = annotationValue(fieldAnno);
				if (annoParam.getDescription() != null) {
					warn("   replacing existing parameter description");
				}
				annoParam.setDescription(text);
				dbug("   field: " + fieldName + " " + summaryText(text));
			}
		}
	}

	/**
	 * Extract plugin descriptor from both maven standard and extractor custom
	 * annotations.
	 */
	@Override
	public List<MojoDescriptor> execute(PluginToolsRequest request) throws ExtractionException {
		try {
			getLogger().info(A.extractor + ":");
			final ClassLoader loader = projectClasLoader(request);
			Map<String, MojoAnnotatedClass> mojoAnnotatedClasses = scanAnnotations(request);
			for (MojoAnnotatedClass annoClass : mojoAnnotatedClasses.values()) {
				if (annoClass.getMojo() == null) {
					continue; // Non-mojo class.
				}
				final String className = annoClass.getClassName();
				final Class<?> klaz = loader.loadClass(className);
				updateMojoDescription(klaz, annoClass);
				updateParameterDescription(klaz, annoClass);
			}
			return toMojoDescriptors(mojoAnnotatedClasses, request.getPluginDescriptor());
		} catch (Throwable e) {
			throw new ExtractionException(A.extractor, e);
		}
	}

}
