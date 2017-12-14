
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.Execute;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.codehaus.plexus.logging.Logger;

import com.carrotgarden.maven.tools.Description;

/**
 * mojo javadoc
 * 
 * @since 0.0
 */
@Description("mojo annotation")
@Mojo(name = "test", defaultPhase = LifecyclePhase.COMPILE, threadSafe = true)
@Execute(goal = "package", lifecycle = "package", phase = LifecyclePhase.PACKAGE)
public class TestMojo extends AbstractMojo {

	/**
	 * component javadoc
	 * 
	 * @since 1.0
	 */
	@Description("component annotation")
	@Component
	protected Logger logger;

	/**
	 * parameter javadoc
	 * 
	 * @since 2.0
	 */
	@Description("parameter annotation")
	@Parameter(property = "test.parameter", required = true, defaultValue = "${project.artifactId}")
	protected String parameter;

	public void execute() throws MojoExecutionException, MojoFailureException {
	}

}
