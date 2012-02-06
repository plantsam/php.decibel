package org.decibeltechnology.decibel.ui.options;

import java.util.List;
import java.util.prefs.PreferenceChangeEvent;
import java.util.prefs.PreferenceChangeListener;
import java.util.prefs.Preferences;
import javax.swing.event.ChangeListener;
import org.decibeltechnology.decibel.DecibelToolkit;
import org.netbeans.modules.php.api.util.FileUtils;
import org.openide.util.ChangeSupport;
import org.openide.util.NbPreferences;

/**
 * @author Tomas Mysik
 */
public final class DecibelOptions {

    // Do not change arbitrary - consult with layer's folder OptionsExport
    // Path to Preferences node for storing these preferences
    private static final String PREFERENCES_PATH = "decibel"; // NOI18N

    private static final DecibelOptions INSTANCE = new DecibelOptions();

    // decibel toolkit
    private static final String TOOLKIT = "toolkit.php"; // NOI18N

    final ChangeSupport changeSupport = new ChangeSupport(this);

    private volatile boolean decibelSearched = false;

    private DecibelOptions() {
        getPreferences().addPreferenceChangeListener(new PreferenceChangeListener() {
            @Override
            public void preferenceChange(PreferenceChangeEvent evt) {
                changeSupport.fireChange();
            }
        });
    }

    public static DecibelOptions getInstance() {
        return INSTANCE;
    }

    public void addChangeListener(ChangeListener listener) {
        changeSupport.addChangeListener(listener);
    }

    public void removeChangeListener(ChangeListener listener) {
        changeSupport.removeChangeListener(listener);
    }

    public synchronized String getToolkit() {
        String decibel = getPreferences().get(TOOLKIT, null);
        if (decibel == null && !decibelSearched) {
            decibelSearched = true;
            List<String> scripts = FileUtils.findFileOnUsersPath(DecibelToolkit.SCRIPT_NAME);
            if (!scripts.isEmpty()) {
                decibel = scripts.get(0);
                setToolkit(decibel);
            }
        }
        return decibel;
    }

    public void setToolkit(String toolkit) {
        getPreferences().put(TOOLKIT, toolkit);
    }

    private Preferences getPreferences() {
        return NbPreferences.forModule(DecibelOptions.class).node(PREFERENCES_PATH);
    }
}
