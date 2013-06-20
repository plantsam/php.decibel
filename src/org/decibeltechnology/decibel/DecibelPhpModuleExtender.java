package org.decibeltechnology.decibel;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;
import javax.swing.event.ChangeListener;
import org.netbeans.modules.php.api.executable.InvalidPhpExecutableException;
import org.decibeltechnology.decibel.ui.wizards.NewProjectConfigurationPanel;
import org.netbeans.modules.php.api.executable.PhpInterpreter;
import org.netbeans.modules.php.api.phpmodule.PhpModule;
import org.netbeans.modules.php.spi.framework.PhpModuleExtender;
import org.openide.filesystems.FileObject;
import org.openide.util.Exceptions;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;

/**
 * @author Tomas Mysik
 */
public class DecibelPhpModuleExtender extends PhpModuleExtender {
    //@GuardedBy(this)
    private NewProjectConfigurationPanel panel = null;

    @Override
    public Set<FileObject> extend(PhpModule phpModule) throws ExtendingException {

        // Load the Decibel Toolkit.
        DecibelToolkit toolkit = null;
        try {
            toolkit = DecibelToolkit.getDefault();

		// The IDE should have already checked that a valid toolkit exists,
		// so this exception should never occur - but catch it just in case.
        } catch (InvalidPhpExecutableException ex) {
            Exceptions.printStackTrace(ex);
        }

		// Initialise the project using Decibel Toolkit.
//        if (!toolkit.initProject(phpModule, getPanel().getProjectParams())) {
//            Logger.getLogger(DecibelPhpModuleExtender.class.getName())
//                    .log(Level.INFO, "Decibel Framework not found in newly created project {0}", phpModule.getDisplayName());
//            throw new ExtendingException(NbBundle.getMessage(DecibelPhpModuleExtender.class, "MSG_NotExtended"));
//        }

        // prefetch commands
        DecibelPhpFrameworkProvider.getInstance().getFrameworkCommandSupport(phpModule).refreshFrameworkCommandsLater(null);

        // return files
        Set<FileObject> files = new HashSet<FileObject>();
//        FileObject databases = DecibelPhpFrameworkProvider.locate(phpModule, "config/databases.yml", true); // NOI18N
//        if (databases != null) {
//            // likely --orm=none
//            files.add(databases);
//        }
//        FileObject config = DecibelPhpFrameworkProvider.locate(phpModule, "config/ProjectConfiguration.class.php", true); // NOI18N
//        if (config != null) {
//            // #176041
//            files.add(config);
//        }
//
//        if (files.isEmpty()) {
//            // open at least index.php
//            FileObject index = DecibelPhpFrameworkProvider.locate(phpModule, "web/index.php", true); // NOI18N
//            if (index != null) {
//                files.add(index);
//            }
//        }

        return files;
    }

    @Override
    public void addChangeListener(ChangeListener listener) {
        getPanel().addChangeListener(listener);
    }

    @Override
    public void removeChangeListener(ChangeListener listener) {
        getPanel().removeChangeListener(listener);
    }

    @Override
    public JComponent getComponent() {
        return getPanel();
    }

    @Override
    public HelpCtx getHelp() {
        return null;
    }

    @Override
    public boolean isValid() {
        return getErrorMessage() == null;
    }

    @Override
    public String getErrorMessage() {
        try {
            PhpInterpreter.getDefault();
        } catch (InvalidPhpExecutableException ex) {
            return ex.getLocalizedMessage();
        }
        try {
            DecibelToolkit.getDefault();
        } catch (InvalidPhpExecutableException ex) {
            return NbBundle.getMessage(DecibelPhpModuleExtender.class, "MSG_CannotExtend", ex.getMessage());
        }
        return getPanel().getErrorMessage();
    }

    @Override
    public String getWarningMessage() {
        return getPanel().getWarningMessage();
    }

    private synchronized NewProjectConfigurationPanel getPanel() {
        if (panel == null) {
            panel = new NewProjectConfigurationPanel();
        }
        return panel;
    }
}
