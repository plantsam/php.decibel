package org.decibeltechnology.decibel;

import java.util.Collections;
import java.util.List;
import javax.swing.Action;
import org.decibeltechnology.decibel.ui.actions.ClearCacheAction;
import org.decibeltechnology.decibel.ui.actions.DecibelGoToActionAction;
import org.decibeltechnology.decibel.ui.actions.DecibelGoToViewAction;
import org.decibeltechnology.decibel.ui.actions.DecibelRunCommandAction;
import org.decibeltechnology.decibel.util.DecibelUtils;
import org.netbeans.modules.php.spi.actions.GoToActionAction;
import org.netbeans.modules.php.spi.actions.GoToViewAction;
import org.netbeans.modules.php.spi.actions.RunCommandAction;
import org.netbeans.modules.php.spi.phpmodule.PhpModuleActionsExtender;
import org.openide.filesystems.FileObject;
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

    @Override
    public boolean isViewWithAction(FileObject fo) {
        return DecibelUtils.isViewWithAction(fo);
    }

    @Override
    public boolean isActionWithView(FileObject fo) {
        return DecibelUtils.isAction(fo);
    }

    @Override
    public GoToActionAction getGoToActionAction(FileObject fo, int offset) {
        return new DecibelGoToActionAction(fo);
    }

    @Override
    public GoToViewAction getGoToViewAction(FileObject fo, int offset) {
        return new DecibelGoToViewAction(fo, offset);
    }

}
