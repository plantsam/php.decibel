package org.decibeltechnology.decibel.commands;

import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.ArrayList;
import org.decibeltechnology.decibel.DecibelToolkit;
import org.netbeans.modules.php.api.phpmodule.PhpModule;
import org.netbeans.modules.php.api.util.StringUtils;
import org.netbeans.modules.php.spi.commands.FrameworkCommand;

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

        return DecibelToolkit.getHelp(module, this);

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
				+ getTask() + " " // NOI18N
				+ this.sourceDirectory + " " // NOI18N
				+ getParameters();

    } // end function getPreview.
	
	/**
	 * Returns the parameter component of this command (e.g. --test --author="Author Name")
	 * 
	 * @return 
	 * @since	1.0.0
	 */
	public String getParameters() {
		
		// Remove the task name from the parameters.
		String[] commands = getCommands();
		ArrayList<String> params = new ArrayList<String>(Arrays.asList(commands));
		params.remove(0);
		
		return StringUtils.implode(params, " ");
		
	} // end function getParameters
	
	/**
	 * Returns the task component of this command (e.g. decibel:clear-cache)
	 * 
	 * @return 
	 * @since	1.0.0
	 */
	public String getTask() {
		
		// Extract the task name.
		String[] commands = getCommands();
		return commands[0];
		
	} // end function getTask
	
} // end class DecibelCommand.