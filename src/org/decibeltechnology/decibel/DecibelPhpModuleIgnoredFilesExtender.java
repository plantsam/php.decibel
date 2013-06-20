package org.decibeltechnology.decibel;

import java.io.File;
import java.util.HashSet;
import java.util.Set;
import org.netbeans.modules.php.api.phpmodule.PhpModule;
import org.netbeans.modules.php.spi.framework.PhpModuleIgnoredFilesExtender;

public class DecibelPhpModuleIgnoredFilesExtender extends PhpModuleIgnoredFilesExtender {

	private final Set<File> defaultIgnores;

    public DecibelPhpModuleIgnoredFilesExtender(PhpModule phpModule) {

        assert phpModule != null;

		defaultIgnores = new HashSet<File>();
		String sourcePath = phpModule.getSourceDirectory().getPath();

		defaultIgnores.add(new File(sourcePath + "/_config/DConfigurationManager"));
		defaultIgnores.add(new File(sourcePath + "/_temp"));
		//defaultIgnores.add(new File(sourcePath + "/app/decibel"));
		//defaultIgnores.add(new File(sourcePath + "/app/DecibelCMS"));

    }

    @Override
    public Set<File> getIgnoredFiles() {
		return defaultIgnores;
    }

}
