package org.decibeltechnology.decibel.commands;

import java.lang.ref.WeakReference;
import org.decibeltechnology.decibel.DecibelToolkit;
import org.netbeans.modules.php.api.phpmodule.PhpModule;
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
			+ this.sourceDirectory + " " // NOI18N
			+ super.getPreview();
		
    } // end function getPreview.
	
} // end class DecibelCommand.