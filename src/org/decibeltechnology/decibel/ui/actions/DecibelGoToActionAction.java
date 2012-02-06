package org.decibeltechnology.decibel.ui.actions;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.decibeltechnology.decibel.util.DecibelUtils;
import org.netbeans.modules.csl.api.UiUtils;
import org.netbeans.modules.php.api.editor.EditorSupport;
import org.netbeans.modules.php.api.editor.PhpClass;
import org.netbeans.modules.php.spi.actions.GoToActionAction;
import org.openide.filesystems.FileObject;
import org.openide.util.Lookup;

public final class DecibelGoToActionAction extends GoToActionAction {
    private static final long serialVersionUID = 89756313874L;
    private static final Pattern ACTION_METHOD_NAME = Pattern.compile("^(\\w+)[A-Z]"); // NOI18N

    private final FileObject fo;

    public DecibelGoToActionAction(FileObject fo) {
        assert DecibelUtils.isViewWithAction(fo);
        this.fo = fo;
    }

    @Override
    public boolean goToAction() {
        FileObject action = DecibelUtils.getAction(fo);
        if (action != null) {
            UiUtils.open(action, getActionMethodOffset(action));
            return true;
        }
        return false;
    }

    private int getActionMethodOffset(FileObject action) {
        String actionMethodName = getActionMethodName(fo.getName());
        EditorSupport editorSupport = Lookup.getDefault().lookup(EditorSupport.class);
        for (PhpClass phpClass : editorSupport.getClasses(action)) {
            if (actionMethodName != null) {
                for (PhpClass.Method method : phpClass.getMethods()) {
                    if (actionMethodName.equals(method.getName())) {
                        return method.getOffset();
                    }
                }
            }
            return phpClass.getOffset();
        }
        return DEFAULT_OFFSET;
    }

    static String getActionMethodName(String filename) {
        Matcher matcher = ACTION_METHOD_NAME.matcher(filename);
        if (matcher.find()) {
            String group = matcher.group(1);
            return DecibelUtils.ACTION_METHOD_PREFIX + group.substring(0, 1).toUpperCase() + group.substring(1);
        }
        return null;
    }
}
