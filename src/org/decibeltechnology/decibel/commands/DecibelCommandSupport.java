package org.decibeltechnology.decibel.commands;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import org.decibeltechnology.decibel.DecibelPhpFrameworkProvider;
import org.decibeltechnology.decibel.DecibelToolkit;
import org.netbeans.modules.php.api.phpmodule.PhpModule;
import org.netbeans.modules.php.api.executable.InvalidPhpExecutableException;
import org.netbeans.modules.php.api.util.UiUtils;
import org.netbeans.modules.php.spi.framework.commands.FrameworkCommand;
import org.netbeans.modules.php.spi.framework.commands.FrameworkCommandSupport;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.util.NbBundle;

/**
 * @author Tomas Mysik
 */
public final class DecibelCommandSupport extends FrameworkCommandSupport {

    static final Logger LOGGER = Logger.getLogger(DecibelCommandSupport.class.getName());

    public DecibelCommandSupport(PhpModule phpModule) {
        super(phpModule);
    }

    @Override
    public String getFrameworkName() {
        return NbBundle.getMessage(DecibelCommandSupport.class, "MSG_Decibel");
    }

    @Override
    public void runCommand(CommandDescriptor commandDescriptor, Runnable postExecution) {

		DecibelCommand command = (DecibelCommand) commandDescriptor.getFrameworkCommand();

        try {
            DecibelToolkit.forPhpModule(phpModule, false)
					.runCommand(phpModule, command, postExecution);

        } catch (InvalidPhpExecutableException ex) {
            UiUtils.invalidScriptProvided(
					ex.getLocalizedMessage(),
					DecibelToolkit.OPTIONS_SUB_PATH);
        }

    } // end function runCommand.

    @Override
    protected String getOptionsPath() {
        return DecibelToolkit.getOptionsPath();
    }

	/**
	 * Returns a list of commands available within the Decibel toolkit.
	 *
	 * @todo	Generate this list from the toolkit rather than statically.
	 * @since	1.0.0
	 */
    @Override
    protected List<FrameworkCommand> getFrameworkCommandsInternal() {

		List<FrameworkCommand> commands = new ArrayList<FrameworkCommand>();

		commands.add(new DecibelCommand(phpModule, "decibel:clear-cache", "Clears the application cache and flushes the shared memory cache.", "decibel:clear-cache"));
		commands.add(new DecibelCommand(phpModule, "decibel:convert-media", "Initiates conversion of the next media in the queue.", "decibel:convert-media"));
		commands.add(new DecibelCommand(phpModule, "decibel:optimise", "Optimises the Decibel database and asset store.", "decibel:optimise"));
		commands.add(new DecibelCommand(phpModule, "decibel:run-events", "Triggers any pending scheduled events.", "decibel:run-events"));

		return commands;

    } // end function getFrameworkCommandsInternal.

    @Override
    protected File getPluginsDirectory() {

        FileObject plugins = DecibelPhpFrameworkProvider.locate(phpModule, "app", true);
        if (plugins != null && plugins.isFolder()) {
            return FileUtil.toFile(plugins);
        }

        return null;

    } // end function getPluginsDirectory.

}