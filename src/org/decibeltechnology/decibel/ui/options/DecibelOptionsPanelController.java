package org.decibeltechnology.decibel.ui.options;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import javax.swing.JComponent;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import org.decibeltechnology.decibel.DecibelToolkit;
import org.netbeans.modules.php.api.util.UiUtils;
import org.netbeans.spi.options.OptionsPanelController;
import org.openide.util.HelpCtx;
import org.openide.util.Lookup;

/**
 * @author Tomas Mysik
 */
@OptionsPanelController.SubRegistration(
    location=UiUtils.OPTIONS_PATH,
    id=DecibelToolkit.OPTIONS_SUB_PATH,
    displayName="#LBL_OptionsName",
//    toolTip="#LBL_OptionsTooltip"
    position=200
)
public class DecibelOptionsPanelController extends OptionsPanelController implements ChangeListener {
    private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    private DecibelOptionsPanel decibelOptionsPanel = null;
    private volatile boolean changed = false;

    @Override
    public void update() {
        decibelOptionsPanel.setDecibel(getOptions().getToolkit());

        changed = false;
    }

    @Override
    public void applyChanges() {
        getOptions().setToolkit(decibelOptionsPanel.getDecibel());

        changed = false;
    }

    @Override
    public void cancel() {
    }

    @Override
    public boolean isValid() {
        // warnings
        String warning = DecibelToolkit.validate(decibelOptionsPanel.getDecibel());
        if (warning != null) {
            decibelOptionsPanel.setWarning(warning);
            return true;
        }

        // everything ok
        decibelOptionsPanel.setError(" "); // NOI18N
        return true;
    }

    @Override
    public boolean isChanged() {
        return changed;
    }

    @Override
    public JComponent getComponent(Lookup masterLookup) {
        if (decibelOptionsPanel == null) {
            decibelOptionsPanel = new DecibelOptionsPanel();
            decibelOptionsPanel.addChangeListener(this);
        }
        return decibelOptionsPanel;
    }

    @Override
    public HelpCtx getHelpCtx() {
        return new HelpCtx("org.netbeans.modules.php.symfony.ui.options.DecibelOptions"); // NOI18N
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener l) {
        propertyChangeSupport.addPropertyChangeListener(l);
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener l) {
        propertyChangeSupport.removePropertyChangeListener(l);
    }

    public void stateChanged(ChangeEvent e) {
        if (!changed) {
            changed = true;
            propertyChangeSupport.firePropertyChange(OptionsPanelController.PROP_CHANGED, false, true);
        }
        propertyChangeSupport.firePropertyChange(OptionsPanelController.PROP_VALID, null, null);
    }

    private DecibelOptions getOptions() {
        return DecibelOptions.getInstance();
    }
}
