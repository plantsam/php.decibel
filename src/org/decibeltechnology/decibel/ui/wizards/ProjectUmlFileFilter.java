package org.decibeltechnology.decibel.ui.wizards;

import java.io.File;
import javax.swing.filechooser.FileFilter;
import org.openide.util.NbBundle;

/**
 * @author Timothy de Paris
 */
public class ProjectUmlFileFilter extends FileFilter {

	@Override
	public boolean accept(File f) {
		return f.isDirectory()
			|| f.getName().endsWith(".uml");
	}

	@Override
	public String getDescription() {
		return NbBundle.getMessage(ProjectUmlFileFilter.class, "LBL_UmlFiles");
	}
	
}
