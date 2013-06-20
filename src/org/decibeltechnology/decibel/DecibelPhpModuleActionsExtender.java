package org.decibeltechnology.decibel;

import java.util.Collections;
import java.util.List;
import javax.swing.Action;
import org.decibeltechnology.decibel.ui.actions.*;
import org.netbeans.modules.php.spi.framework.actions.RunCommandAction;
import org.netbeans.modules.php.spi.framework.PhpModuleActionsExtender;
import org.openide.util.NbBundle;

public class DecibelPhpModuleActionsExtender extends PhpModuleActionsExtender {

	private static final List<Action> ACTIONS = Collections.<Action>singletonList(ClearCacheAction.getInstance());

	@Override
	public String getMenuName() {
		return NbBundle.getMessage(DecibelPhpModuleActionsExtender.class, "LBL_MenuName");
	}

	@Override
	public List<? extends Action> getActions() {
		return ACTIONS;
	}

	@Override
	public RunCommandAction getRunCommandAction() {
		return DecibelRunCommandAction.getInstance();
	}

}
