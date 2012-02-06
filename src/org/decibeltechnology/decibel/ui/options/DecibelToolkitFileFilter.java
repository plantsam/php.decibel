package org.decibeltechnology.decibel.ui.options;

import java.io.File;
import javax.swing.filechooser.FileFilter;
import org.openide.util.NbBundle;

/**
 * Defines a custom file filter for locating Decibel Toolkit bootstrap files.
 * 
 * @author	Timothy de Paris
 * @since	1.0.0
 */
public class DecibelToolkitFileFilter extends FileFilter {

	/**
	 * Determine whether the provided file will be accepted by this filter.
	 * 
	 * @param	file	The file to test.
	 * @since	1.0.0
	 */
	@Override
	public boolean accept(File file) {

		return file.isDirectory()
			|| file.getName().equals("toolkit.php");

	} // end function accept.

	@Override
	public String getDescription() {

		return NbBundle.getMessage(DecibelToolkitFileFilter.class, "LBL_FileFilter");

	} // end function getDescription.

} // end class DecibelToolkitFileFilter.