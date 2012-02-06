package org.decibeltechnology.decibel.ui.actions;

import org.decibeltechnology.decibel.DecibelPhpFrameworkProvider;
import org.netbeans.modules.php.api.phpmodule.PhpModule;
import org.netbeans.modules.php.spi.actions.RunCommandAction;
import org.openide.util.NbBundle;

/**
 * @author Tomas Mysik
 */
public final class DecibelRunCommandAction extends RunCommandAction {

	private static final DecibelRunCommandAction INSTANCE = new DecibelRunCommandAction();

	private DecibelRunCommandAction() {
	}

	public static DecibelRunCommandAction getInstance() {
		return INSTANCE;
	}

	@Override
	public void actionPerformed(PhpModule phpModule) {
		
		if (!DecibelPhpFrameworkProvider.getInstance().isInPhpModule(phpModule)) {
			return;
		}

		DecibelPhpFrameworkProvider.getInstance().getFrameworkCommandSupport(phpModule).runCommand();
	}

	@Override
	protected String getFullName() {
		return NbBundle.getMessage(DecibelRunCommandAction.class, "LBL_DecibelAction", getPureName());
	}

}