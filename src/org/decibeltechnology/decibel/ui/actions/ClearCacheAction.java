package org.decibeltechnology.decibel.ui.actions;

import java.awt.event.ActionListener;
import java.util.concurrent.Callable;
import org.decibeltechnology.decibel.DecibelPhpFrameworkProvider;
import org.decibeltechnology.decibel.DecibelToolkit;
import org.netbeans.api.extexecution.ExecutionDescriptor;
import org.netbeans.api.extexecution.ExecutionService;
import org.netbeans.modules.php.api.phpmodule.PhpModule;
import org.netbeans.modules.php.spi.actions.BaseAction;
import org.netbeans.modules.php.spi.commands.FrameworkCommandSupport;
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

        FrameworkCommandSupport commandSupport = DecibelPhpFrameworkProvider.getInstance().getFrameworkCommandSupport(phpModule);
        Callable<Process> callable = commandSupport.createCommand(DecibelToolkit.CMD_CLEAR_CACHE);
        ExecutionDescriptor descriptor = commandSupport.getDescriptor();
        String displayName = commandSupport.getOutputTitle(DecibelToolkit.CMD_CLEAR_CACHE);
        ExecutionService service = ExecutionService.newService(callable, descriptor, displayName);
        service.run();
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