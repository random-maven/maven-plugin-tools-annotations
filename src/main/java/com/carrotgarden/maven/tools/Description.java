package com.carrotgarden.maven.tools;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Custom annotation recognized by the extractor.
 * 
 * Provides mojo/parameter documentation for the plugin descriptor.
 */
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.TYPE, ElementType.FIELD })
public @interface Description {

	/**
	 * Provide mojo/parameter documentation for the plugin descriptor.
	 */
	String value();

}
