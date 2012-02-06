package org.decibeltechnology.decibel.ui.actions;

import java.util.List;
import org.netbeans.modules.csl.api.UiUtils;
import org.netbeans.modules.php.api.editor.EditorSupport;
import org.netbeans.modules.php.api.editor.PhpBaseElement;
import org.netbeans.modules.php.spi.actions.GoToViewAction;
import org.decibeltechnology.decibel.util.DecibelUtils;
import org.openide.filesystems.FileObject;
import org.openide.util.Lookup;

public final class DecibelGoToViewAction extends GoToViewAction {
    private static final long serialVersionUID = 89745632134654L;

    private final FileObject fo;
    private final int offset;

    public DecibelGoToViewAction(FileObject fo, int offset) {
        assert DecibelUtils.isAction(fo);
        this.fo = fo;
        this.offset = offset;
    }

    @Override
    public boolean goToView() {
        EditorSupport editorSupport = Lookup.getDefault().lookup(EditorSupport.class);
        PhpBaseElement phpElement = editorSupport.getElement(fo, offset);
        if (phpElement == null) {
            return false;
        }
        final List<FileObject> views = DecibelUtils.getViews(fo, phpElement);
        if (views.size() == 1) {
            UiUtils.open(views.get(0), DEFAULT_OFFSET);
            return true;
        } else if (views.size() > 1) {
            DecibelGoToViewActionPopup popup = new DecibelGoToViewActionPopup(views, offset);
            popup.show();
            return true;
        }
        return false;
    }
}
