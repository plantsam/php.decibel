package org.decibeltechnology.decibel.util;

import java.io.File;
import java.io.FileFilter;
import java.util.LinkedList;
import java.util.List;
import org.netbeans.modules.php.api.editor.PhpBaseElement;
import org.netbeans.modules.php.api.editor.PhpClass;
import org.netbeans.spi.project.support.ant.PropertyUtils;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;

/**
 * @author Tomas Mysik
 */
public final class DecibelUtils {
	
    public static final String ACTION_METHOD_PREFIX = "execute"; // NOI18N
    public static final String ACTION_CLASS_SUFFIX = "actions"; // NOI18N

    private static final String FILE_ACTION = "actions.class.php"; // NOI18N
    private static final String FILE_ACTION_RELATIVE = "../actions/" + FILE_ACTION; // NOI18N

    private static final String DIR_TEMPLATES = "templates"; // NOI18N
    private static final String DIR_TEMPLATES_RELATIVE = "../" + DIR_TEMPLATES; // NOI18N
    private static final String VIEW_FILE_SUFFIX = "Success"; // NOI18N
    private static final String TEMPLATE_REGEX = "%s" + VIEW_FILE_SUFFIX + "(\\.\\w+)?\\.php"; // NOI18N

    private DecibelUtils() {
    }

    public static boolean isView(FileObject fo) {
		return (fo.getExt().equals("tpl"));
    }

    public static boolean isViewWithAction(FileObject fo) {
		return true;
//        return isView(fo) && getAction(fo) != null;
    }

    public static boolean isAction(FileObject fo) {
        return FILE_ACTION.equals(fo.getNameExt());
    }

    public static FileObject getAction(FileObject fo) {
        File parent = FileUtil.toFile(fo).getParentFile();
        File action = PropertyUtils.resolveFile(parent, FILE_ACTION_RELATIVE);
        if (action.isFile()) {
            return FileUtil.toFileObject(action);
        }
        return null;
    }

    public static String getActionName(FileObject view) {
        return getActionName(view.getName());
    }

    static String getActionName(String viewName) {
        String[] parts = viewName.split("\\."); // NOI18N
        return ACTION_METHOD_PREFIX + parts[0].replaceAll(VIEW_FILE_SUFFIX + "$", "").toLowerCase(); // NOI18N
    }

    public static List<FileObject> getViews(FileObject fo, PhpBaseElement phpElement) {
        List<FileObject> views = new LinkedList<FileObject>();
        if (phpElement instanceof PhpClass.Method) {
            String methodName = phpElement.getName();
            if (methodName.startsWith(ACTION_METHOD_PREFIX)) {
                String partName = methodName.substring(ACTION_METHOD_PREFIX.length());
                views.addAll(getViews(fo, partName.substring(0, 1).toLowerCase() + partName.substring(1)));
            }
        }
        return views;
    }

    private static List<FileObject> getViews(FileObject fo, final String viewName) {
        List<FileObject> views = new LinkedList<FileObject>();
        File parent = FileUtil.toFile(fo).getParentFile();
        File templatesDir = PropertyUtils.resolveFile(parent, DIR_TEMPLATES_RELATIVE);
        File[] fileViews = templatesDir.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                if (pathname.isFile() && pathname.getName().matches(String.format(TEMPLATE_REGEX, viewName))) {
                    return true;
                }
                return false;
            }
        });
        for (File view : fileViews) {
            views.add(FileUtil.toFileObject(view));
        }
        return views;
    }
}
