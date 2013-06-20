package org.decibeltechnology.decibel.commands;

import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.ArrayList;
import org.decibeltechnology.decibel.DecibelToolkit;
import org.netbeans.modules.php.api.phpmodule.PhpModule;
import org.netbeans.modules.php.api.util.StringUtils;
import org.netbeans.modules.php.spi.framework.commands.FrameworkCommand;

/**
 * @author	Timothy de Paris
 * @since	1.0.0
 */
public class DecibelCommand extends FrameworkCommand {

    private final WeakReference<PhpModule> phpModule;
    private final String sourceDirectory;

    public DecibelCommand(PhpModule phpModule, String command, String description, String displayName) {
        super(command, description, displayName);
        assert phpModule != null;

		// Store a weak reference to the project to avoid memory leaks.
        this.phpModule = new WeakReference<PhpModule>(phpModule);

		// Store the source directory of the project we are working on
		// as only a weak reference to the project will be available later.
		this.sourceDirectory = phpModule.getSourceDirectory().getNameExt();
    }

    @Override
    protected String getHelpInternal() {

        PhpModule module = phpModule.get();
        if (module == null) {
            return ""; // NOI18N
        }

		return ""; // NOI18N
//        return DecibelToolkit.getHelp(module, this);

    }

	/**
	 * Returns a preview of the command to be displayed in the Run Command
	 * dialog for a Decibel application.
	 *
	 * @since	1.0.0
	 */
    @Override
    public String getPreview() {

		return DecibelToolkit.SCRIPT_NAME + " " // NOI18N
				+ StringUtils.implode(getToolkitCommands(), " ");

    } // end function getPreview.

	/**
	 * Returns the parameter component of this command
	 * (for example, --test --author="Author Name")
	 *
	 * @since	1.0.0
	 */
	public String getParameters() {

		// Remove the task name from the parameters.
		ArrayList<String> params = new ArrayList<String>(Arrays.asList(getCommands()));
		params.remove(0);

		return StringUtils.implode(params, " ");

	} // end function getParameters.

	/**
	 * Returns the task component of this command
	 * (for example, decibel:clear-cache)
	 *
	 * @since	1.0.0
	 */
	public String getTask() {

		// Extract the task name.
		String[] commands = getCommands();
		return commands[0];

	} // end function getTask.

	/**
	 * Returns a list of parameters needed by the toolkit
	 * to execute this command.
	 *
	 * This list includes the task, source directory
	 * and any additional parameters.
	 *
	 * @since	1.1.0
	 */
    public final ArrayList<String> getToolkitCommands() {

		ArrayList<String> commands = new ArrayList<String>(Arrays.asList(getCommands()));
		commands.add(1, this.sourceDirectory);

		return commands;

	} // end function getToolkitCommands.

} // end class DecibelCommand.