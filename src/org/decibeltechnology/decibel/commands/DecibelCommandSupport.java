package org.decibeltechnology.decibel.commands;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.logging.Logger;
import org.decibeltechnology.decibel.DecibelPhpFrameworkProvider;
import org.decibeltechnology.decibel.DecibelToolkit;
import org.netbeans.api.extexecution.ExecutionDescriptor;
import org.netbeans.api.extexecution.ExecutionService;
import org.netbeans.api.extexecution.ExternalProcessBuilder;
import org.netbeans.modules.php.api.phpmodule.PhpModule;
import org.netbeans.modules.php.api.phpmodule.PhpProgram.InvalidPhpProgramException;
import org.netbeans.modules.php.api.util.UiUtils;
import org.netbeans.modules.php.spi.commands.FrameworkCommand;
import org.netbeans.modules.php.spi.commands.FrameworkCommandSupport;
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
    public void runCommand(CommandDescriptor commandDescriptor) {
		String[] params = commandDescriptor.getCommandParams();
//		params = Arrays.<String>asList(
//			phpModule.getSourceDirectory().getNameExt()
//		).toArray(params);
		//String[] params = commandDescriptor.getCommandParams();
		
        Callable<Process> callable = createCommand(commandDescriptor.getFrameworkCommand().getCommands(), params);
        ExecutionDescriptor descriptor = getDescriptor();
        String displayName = getOutputTitle(commandDescriptor);
        ExecutionService service = ExecutionService.newService(callable, descriptor, displayName);
        service.run();
    }

    @Override
    protected String getOptionsPath() {
        return DecibelToolkit.getOptionsPath();
    }

    @Override
    protected ExternalProcessBuilder getProcessBuilder(boolean warnUser) {
		ExternalProcessBuilder externalProcessBuilder = super.getProcessBuilder(warnUser);
		if (externalProcessBuilder == null) {
			return null;
		}
		DecibelToolkit toolkit;
		try {
			toolkit = DecibelToolkit.forPhpModule(phpModule, warnUser);
		} catch (InvalidPhpProgramException ex) {
			if (warnUser) {
				UiUtils.invalidScriptProvided(
						ex.getMessage(),
						DecibelToolkit.getOptionsSubPath());
			}
			return null;
		}
		assert toolkit.isValid();

		externalProcessBuilder = externalProcessBuilder
				.workingDirectory(FileUtil.toFile(phpModule.getSourceDirectory()))
				.addArgument(toolkit.getProgram());

		for (String param : toolkit.getParameters()) {
			externalProcessBuilder = externalProcessBuilder.addArgument(param);
		}
		
//		externalProcessBuilder = externalProcessBuilder.addArgument(
//				phpModule.getSourceDirectory().getNameExt());

		return externalProcessBuilder;
    }

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
    }

}