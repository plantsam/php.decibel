package org.decibeltechnology.decibel.ui.actions;

import java.awt.event.ActionListener;
import org.decibeltechnology.decibel.DecibelPhpFrameworkProvider;
import org.decibeltechnology.decibel.DecibelToolkit;
import org.decibeltechnology.decibel.commands.DecibelCommand;
import org.netbeans.modules.php.api.executable.InvalidPhpExecutableException;
import org.netbeans.modules.php.api.phpmodule.PhpModule;
import org.netbeans.modules.php.api.util.UiUtils;
import org.netbeans.modules.php.spi.framework.actions.BaseAction;
import org.netbeans.modules.php.spi.framework.commands.FrameworkCommandSupport;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle;

@ActionID(
	category = "Decibel",
	id = "org.decibeltechnology.decibel.ui.actions.ClearCacheAction"
)
@ActionRegistration(displayName = "#CTL_ClearCacheAction")
@ActionReferences({
	@ActionReference(path = "Shortcuts", name = "OS-DELETE")
})
@NbBundle.Messages("CTL_ClearCacheAction=Clear Cache")
public final class ClearCacheAction extends BaseAction implements ActionListener {

	private static final ClearCacheAction INSTANCE = new ClearCacheAction();

	private ClearCacheAction() {
	}

	public static ClearCacheAction getInstance() {
		return INSTANCE;
	}

	@Override
	public void actionPerformed(PhpModule phpModule) {

		if (!DecibelPhpFrameworkProvider.getInstance().isInPhpModule(phpModule)) {
			return;
		}

		try {
			DecibelCommand command = new DecibelCommand(phpModule, "decibel:clear-cache", "Clears the application cache and flushes the shared memory cache.", "decibel:clear-cache");
			DecibelToolkit.forPhpModule(phpModule, false).runCommand(phpModule, command, null);
		} catch (InvalidPhpExecutableException ex) {
			UiUtils.invalidScriptProvided(ex.getLocalizedMessage(), DecibelToolkit.OPTIONS_SUB_PATH);
		}
	}

	@Override
	protected String getPureName() {
		return NbBundle.getMessage(ClearCacheAction.class, "LBL_ClearCache");
	}

	@Override
	protected String getFullName() {
		return NbBundle.getMessage(ClearCacheAction.class, "LBL_DecibelAction", getPureName());
	}

}