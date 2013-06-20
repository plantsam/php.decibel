package org.decibeltechnology.decibel;

import java.util.EnumSet;
import java.util.prefs.Preferences;
import javax.swing.JComponent;
import javax.swing.event.ChangeListener;
import org.decibeltechnology.decibel.ui.customizer.DecibelCustomizerPanel;
import org.netbeans.modules.php.api.phpmodule.PhpModule;
import org.netbeans.modules.php.spi.framework.PhpModuleCustomizerExtender;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;

public class DecibelPhpModuleCustomizerExtender extends PhpModuleCustomizerExtender {

    private final PhpModule phpModule;

    private DecibelCustomizerPanel component;

    DecibelPhpModuleCustomizerExtender(PhpModule phpModule) {
        this.phpModule = phpModule;
    }

    @Override
    public String getDisplayName() {
        return NbBundle.getMessage(DecibelPhpModuleCustomizerExtender.class, "LBL_Decibel");
    }

    @Override
    public void addChangeListener(ChangeListener listener) {
        // always valid
    }

    @Override
    public void removeChangeListener(ChangeListener listener) {
        // always valid
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
        // always valid
        return true;
    }

    @Override
    public String getErrorMessage() {
        // always valid
        return null;
    }

    @Override
    public EnumSet<Change> save(PhpModule phpModule) {
        return null;
    }

    private DecibelCustomizerPanel getPanel() {
        if (component == null) {
            component = new DecibelCustomizerPanel();
        }
        return component;
    }

    private Preferences getPreferences() {
        return getPreferences(phpModule);
    }

    private static Preferences getPreferences(PhpModule module) {
        return module.getPreferences(DecibelPhpFrameworkProvider.class, true);
    }
}
