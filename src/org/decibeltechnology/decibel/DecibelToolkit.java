package org.decibeltechnology.decibel;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import org.decibeltechnology.decibel.commands.DecibelCommand;
import org.decibeltechnology.decibel.ui.options.DecibelOptions;
import org.netbeans.api.extexecution.ExecutionDescriptor;
import org.netbeans.api.extexecution.input.LineProcessor;
import org.netbeans.modules.php.api.phpmodule.PhpModule;
import org.netbeans.modules.php.api.executable.InvalidPhpExecutableException;
import org.netbeans.modules.php.api.executable.PhpExecutable;
import org.netbeans.modules.php.api.executable.PhpExecutableValidator;
import org.netbeans.modules.php.api.util.FileUtils;
import org.netbeans.modules.php.api.util.UiUtils;
import org.openide.filesystems.FileUtil;
import org.openide.util.NbBundle;

/**
 * @author Tomas Mysik
 */
public class DecibelToolkit {

    public static final String SCRIPT_NAME = "toolkit.php"; // NOI18N

    public static final String OPTIONS_SUB_PATH = "Decibel"; // NOI18N

    public static final String CMD_INIT_PROJECT = "generate:project"; // NOI18N
    public static final String CMD_CLEAR_CACHE = "decibel:clear-cache"; // NOI18N
    public static final String CMD_INIT_APP = "generate:app"; // NOI18N

	private final String decibelPath;

    DecibelToolkit(String command) {
		this.decibelPath = command;
    }

    /**
     * Get the default, <b>valid only</b> Decibel Toolkit.
     * @return the default, <b>valid only</b> Decibel Toolkit.
     * @throws InvalidPhpProgramException if Decibel Toolkit is not valid.
     */
    public static DecibelToolkit getDefault() throws InvalidPhpExecutableException {
        String decibel = DecibelOptions.getInstance().getToolkit();
        return new DecibelToolkit(decibel);
    }

    /**
     * Get the project specific, <b>valid only</b> Symfony script. If not found, the {@link #getDefault() default} Symfony script is returned.
     * @param phpModule PHP module for which Symfony script is taken
     * @param warn <code>true</code> if user is warned when the {@link #getDefault() default} Symfony script is returned.
     * @return the project specific, <b>valid only</b> Symfony script.
     * @throws InvalidPhpProgramException if Symfony script is not valid. If not found, the {@link #getDefault() default} Symfony script is returned.
     * @see #getDefault()
     */
	public static DecibelToolkit forPhpModule(PhpModule phpModule, boolean warn) throws InvalidPhpExecutableException {
		return getDefault();
	}

	public void clearCache(PhpModule phpModule) {
		//runCommand(phpModule, Collections.singletonList(DecibelToolkit.CMD_CLEAR_CACHE), null);
	}

	private PhpExecutable createPhpExecutable(PhpModule phpModule) {

		return new PhpExecutable(this.decibelPath)
				.workDir(FileUtil.toFile(phpModule.getSourceDirectory()));

	} // end function createPhpExecutable.

	/**
	 * Runs the provided Decibel Toolkit command.
	 *
	 * @param	phpModule
	 * @param	command			Definition of the command to run.
	 * @param	postExecution
	 * @since	1.0.3
	 */
	public void runCommand(PhpModule phpModule,	DecibelCommand command, Runnable postExecution) {

		// Create and run the command using toolkit.
		createPhpExecutable(phpModule)
				.displayName(phpModule.getDisplayName())
				.additionalParameters(command.getToolkitCommands())
				.run(getDescriptor(postExecution));

	} // end function runCommand.

    private ExecutionDescriptor getDescriptor(Runnable postExecution) {
		ExecutionDescriptor executionDescriptor = PhpExecutable.DEFAULT_EXECUTION_DESCRIPTOR
				.optionsPath(OPTIONS_SUB_PATH);
		if (postExecution != null) {
			executionDescriptor = executionDescriptor.postExecution(postExecution);
		}
		return executionDescriptor;
    }

   /**
     * @return full IDE options Decibel path
     */
    public static String getOptionsPath() {
        return UiUtils.OPTIONS_PATH + "/" + getOptionsSubPath(); // NOI18N
    }

    /**
     * @return IDE options Decibel sub-path
     */
    public static String getOptionsSubPath() {
        return OPTIONS_SUB_PATH;
    }

//    public boolean initProject(PhpModule phpModule, String[] params) {
//        String projectName = phpModule.getDisplayName();
//
//        String[] cmdParams = mergeArrays(new String[]{projectName}, params);
//        DecibelCommandSupport commandSupport = DecibelPhpFrameworkProvider.getInstance().getFrameworkCommandSupport(phpModule);
//        ExternalProcessBuilder processBuilder = commandSupport.createSilentCommand(CMD_INIT_PROJECT, cmdParams);
//        if (processBuilder == null) {
//            // #172777
//            return false;
//        }
//        ExecutionDescriptor executionDescriptor = commandSupport.getDescriptor();
//        runService(processBuilder, executionDescriptor, commandSupport.getOutputTitle(CMD_INIT_PROJECT, cmdParams), false);
//        return DecibelPhpFrameworkProvider.getInstance().isInPhpModule(phpModule);
//    }

    @NbBundle.Messages("DecibelToolkit.script.label=Decibel Toolkit")
	public static String validate(String command) {
        return PhpExecutableValidator.validateCommand(command, Bundle.DecibelToolkit_script_label());
	}

//    public boolean initProject(PhpModule phpModule, String[] params) {
//        String projectName = phpModule.getDisplayName();
//
//        String[] cmdParams = mergeArrays(new String[]{projectName}, params);
//        DecibelCommandSupport commandSupport = DecibelPhpFrameworkProvider.getInstance().getFrameworkCommandSupport(phpModule);
//        ExternalProcessBuilder processBuilder = commandSupport.createSilentCommand(CMD_INIT_PROJECT, cmdParams);
//        if (processBuilder == null) {
//            // #172777
//            return false;
//        }
//        ExecutionDescriptor executionDescriptor = commandSupport.getDescriptor();
//        runService(processBuilder, executionDescriptor, commandSupport.getOutputTitle(CMD_INIT_PROJECT, cmdParams), false);
//        return DecibelPhpFrameworkProvider.getInstance().isInPhpModule(phpModule);
//    }

//    public void initApp(PhpModule phpModule, String app, String[] params) {
//        assert StringUtils.hasText(app);
//        assert params != null;
//
//        String[] cmdParams = mergeArrays(params, new String[]{app});
//        FrameworkCommandSupport commandSupport = DecibelPhpFrameworkProvider.getInstance().getFrameworkCommandSupport(phpModule);
//        ExternalProcessBuilder processBuilder = commandSupport.createCommand(CMD_INIT_APP, cmdParams);
//        assert processBuilder != null;
//        ExecutionDescriptor executionDescriptor = commandSupport.getDescriptor();
//        runService(processBuilder, executionDescriptor, commandSupport.getOutputTitle(CMD_INIT_APP, cmdParams), true);
//    }

//    public static String getHelp(PhpModule phpModule, DecibelCommand command) {
//
//		assert phpModule != null;
//		assert command != null;
//
//		FrameworkCommandSupport commandSupport = DecibelPhpFrameworkProvider.getInstance().getFrameworkCommandSupport(phpModule);
//		ExternalProcessBuilder processBuilder = commandSupport.createSilentCommand("?", command.getTask()); // NOI18N
//		assert processBuilder != null;
//
//		final HelpLineProcessor lineProcessor = new HelpLineProcessor();
//		ExecutionDescriptor executionDescriptor = new ExecutionDescriptor()
//				.inputOutput(InputOutput.NULL)
//				.outProcessorFactory(new ExecutionDescriptor.InputProcessorFactory() {
//			@Override
//			public InputProcessor newInputProcessor(InputProcessor defaultProcessor) {
//				return InputProcessors.ansiStripping(InputProcessors.bridge(lineProcessor));
//			}
//		});
//		runService(processBuilder, executionDescriptor, "getting help for: " + command.getPreview(), true); // NOI18N
//		return lineProcessor.getHelp();
//    }

    static <T> T[] mergeArrays(T[]... arrays) {
        List<T> list = new LinkedList<T>();
        for (T[] array : arrays) {
            list.addAll(Arrays.asList(array));
        }
        @SuppressWarnings("unchecked")
        T[] merged = (T[]) Array.newInstance(arrays[0].getClass().getComponentType(), list.size());
        return list.toArray(merged);
    }

//    private static void runService(ExternalProcessBuilder processBuilder, ExecutionDescriptor executionDescriptor, String title, boolean warnUser) {
//        try {
//            executeAndWait(processBuilder, executionDescriptor, title);
//        } catch (CancellationException ex) {
//            // canceled
//        } catch (ExecutionException ex) {
//            if (warnUser) {
//                UiUtils.processExecutionException(ex, DecibelToolkit.getOptionsSubPath());
//            }
//        } catch (InterruptedException ex) {
//            Thread.currentThread().interrupt();
//        }
//    }

    static class HelpLineProcessor implements LineProcessor {
        private final StringBuilder buffer = new StringBuilder(500);

        @Override
        public void processLine(String line) {
            buffer.append(line);
            buffer.append("\n"); // NOI18N
        }

        @Override
        public void reset() {
        }

        @Override
        public void close() {
        }

//        public String getHelp() {
//            return buffer.toString().trim() + "\n"; // NOI18N
//        }
    }
}