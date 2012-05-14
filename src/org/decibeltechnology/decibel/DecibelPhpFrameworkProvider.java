package org.decibeltechnology.decibel;

import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import org.decibeltechnology.decibel.commands.DecibelCommandSupport;
import org.decibeltechnology.decibel.editor.DecibelEditorExtender;
import org.decibeltechnology.decibel.ui.options.DecibelOptions;
import org.netbeans.api.queries.VisibilityQuery;
import org.netbeans.modules.php.api.phpmodule.BadgeIcon;
import org.netbeans.modules.php.api.phpmodule.PhpModule;
import org.netbeans.modules.php.api.phpmodule.PhpModuleProperties;
import org.netbeans.modules.php.api.util.FileUtils;
import org.netbeans.modules.php.spi.editor.EditorExtender;
import org.netbeans.modules.php.spi.phpmodule.PhpFrameworkProvider;
import org.netbeans.modules.php.spi.phpmodule.PhpModuleActionsExtender;
import org.netbeans.modules.php.spi.phpmodule.PhpModuleExtender;
import org.netbeans.modules.php.spi.phpmodule.PhpModuleIgnoredFilesExtender;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.util.ImageUtilities;
import org.openide.util.NbBundle;

public final class DecibelPhpFrameworkProvider extends PhpFrameworkProvider {

	public static final String FILE_DECIBEL = "app/decibel/Decibel.php"; // NOI18N
	public static final String FILE_ROUTER = "app/decibel/router/DRouter.php"; // NOI18N

	private static final String ICON_PATH = "org/decibeltechnology/decibel/ui/resources/decibel_badge_8.png"; // NOI18N
	
	private static final DecibelPhpFrameworkProvider INSTANCE = new DecibelPhpFrameworkProvider();

	private final BadgeIcon badgeIcon;

	@PhpFrameworkProvider.Registration(position=99)
	public static DecibelPhpFrameworkProvider getInstance() {
		return INSTANCE;
	}

	private DecibelPhpFrameworkProvider() {

		super("Decibel Web Platform", // NOI18N
			NbBundle.getMessage(DecibelPhpFrameworkProvider.class, "LBL_FrameworkName"),
			NbBundle.getMessage(DecibelPhpFrameworkProvider.class, "LBL_FrameworkDescription"));
		
		badgeIcon = new BadgeIcon(
			ImageUtilities.loadImage(ICON_PATH),
			DecibelPhpFrameworkProvider.class.getResource("/" + ICON_PATH)); // NOI18N

	} // end constructor.

	/**
	 * Returns the badge icon that will be used in the interface to show
	 * this is a Decibel Web Platform project.
	 * 
	 * @since	1.0.0
	 */
	@Override
	public BadgeIcon getBadgeIcon() {

		return badgeIcon;

	} // end function getBadgeIcon.

	/**
	 * Try to locate (find) a <code>relativePath</code> in source directory.
	 * Currently, it searches source dir and its subdirs (if <code>subdirs</code> equals {@code true}).
	 * @return {@link FileObject} or {@code null} if not found
	 */
	public static FileObject locate(PhpModule phpModule, String relativePath, boolean subdirs) {
		FileObject sourceDirectory = phpModule.getSourceDirectory();

		FileObject fileObject = sourceDirectory.getFileObject(relativePath);
		if (fileObject != null || !subdirs) {
			return fileObject;
		}
		for (FileObject child : sourceDirectory.getChildren()) {
			fileObject = child.getFileObject(relativePath);
			if (fileObject != null) {
				return fileObject;
			}
		}
		return null;
	}

	/**
	 * Determine if the provided project is a Decibel Web Platform project.
	 * 
	 * @param	phpModule	The project to test.
	 * @since	1.0.0
	 */
	@Override
	public boolean isInPhpModule(PhpModule phpModule) {
		
		FileObject decibel = locate(phpModule, FILE_DECIBEL, false);
		return (decibel != null && decibel.isData());
		
	} // end function isInPhpModule.

	/**
	 * Returns an array containing all configuration files for this 
	 * Decibel Web Platform project.
	 * 
	 * @param	phpModule	The project to test.
	 * @since	1.0.0
	 */
	@Override
	public File[] getConfigurationFiles(PhpModule phpModule) {
		
		List<File> files = new LinkedList<File>();
		FileObject appConfig = phpModule.getSourceDirectory().getFileObject("_config"); // NOI18N
		FileObject appLicence = phpModule.getSourceDirectory().getFileObject("app"); // NOI18N
		if (appConfig != null || appLicence != null) {
			
			List<FileObject> fileObjects = new LinkedList<FileObject>();
			for (FileObject child : appConfig.getChildren()) {
				if (VisibilityQuery.getDefault().isVisible(child)) {
					if (child.isData() && FileUtils.isPhpFile(child)) {
						fileObjects.add(child);
					}
				}
			}
			for (FileObject child : appLicence.getChildren()) {
				if (VisibilityQuery.getDefault().isVisible(child)) {
					if (child.isData() && child.getExt().equals("licence")) {
						fileObjects.add(child);
					}
				}
			}
			
			Collections.sort(fileObjects, new Comparator<FileObject>() {
				@Override
				public int compare(FileObject o1, FileObject o2) {
					return o1.getNameExt().compareToIgnoreCase(o2.getNameExt());
				}
			});

			for (FileObject fo : fileObjects) {
				files.add(FileUtil.toFile(fo));
			}
		}
		
		return files.toArray(new File[files.size()]);
		
	} // end function getConfigurationFiles.

	/**
	 * Override properties for this project where they are a part of the
	 * Decibel Web Platform core.
	 * 
	 * @param	phpModule	The project to return properties for.
	 * @since	1.0.0
	 * @todo	This doesn't seem to work?
	 */
	@Override
	public PhpModuleProperties getPhpModuleProperties(PhpModule phpModule) {

		PhpModuleProperties properties = new PhpModuleProperties();
		
		// Add code completion in the toolkit to the include path.
        String decibel = DecibelOptions.getInstance().getToolkit();
		List<String> includePath = new LinkedList<String>();
		includePath.add(decibel + "/_decibel/hint");
		properties = properties.setIncludePath(includePath);
		
		// Set index file.
        FileObject indexFile = locate(phpModule, FILE_ROUTER, true); // NOI18N
		if (properties != null) {
			properties = properties.setIndexFile(indexFile);
		}
		
		return properties;

	} //  end function getPhpModuleProperites.

	@Override
	public PhpModuleExtender createPhpModuleExtender(PhpModule phpModule) {
		return new DecibelPhpModuleExtender();
	}

	@Override
	public PhpModuleActionsExtender getActionsExtender(PhpModule phpModule) {
		return new DecibelPhpModuleActionsExtender();
	}

	@Override
	public PhpModuleIgnoredFilesExtender getIgnoredFilesExtender(PhpModule phpModule) {
		return new DecibelPhpModuleIgnoredFilesExtender(phpModule);
	}

	@Override
	public DecibelCommandSupport getFrameworkCommandSupport(PhpModule phpModule) {
		return new DecibelCommandSupport(phpModule);
	}

	@Override
	public EditorExtender getEditorExtender(PhpModule phpModule) {
		return new DecibelEditorExtender();
	}

} // end class DecibelPhpFrameworkProvider.