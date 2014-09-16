package com.hackcaffebabe.mtg.controller.impoexpo;

import it.hackcaffebabe.ioutil.file.PathUtil;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import com.hackcaffebabe.mtg.controller.Paths;


/**
 * Importer and Exporter utility class.
 *  
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public final class ImpoExpoUtils
{
	/**
	 * This method let the user to select the location to import/export according to the first argument.
	 * @param impoexpo 1 for import, 0 for export
	 * @param w {@link WhatImpoExpo} what to import or export.
	 * @return {@link File} the file representing a directory (export mode) or a zip file (import mode), 
	 * null if cancel is pressed.
	 */
	public static final File showUserLocationChooser(final int impoexpo, final WhatImpoExpo w){
		JFileChooser f = new JFileChooser( PathUtil.USER_HOME );
		if(impoexpo == 1) {
			f.setDialogTitle( "Select backup file" );
			f.setFileFilter( new ImporterFileFilter( w ) );
		} else if(impoexpo == 0) {
			f.setDialogTitle( "Select export folder" );
			f.setFileSelectionMode( JFileChooser.DIRECTORIES_ONLY );
		}

		if(f.showDialog( null, (impoexpo == 1 ? "Open" : "Export Here!") ) == JFileChooser.APPROVE_OPTION) {
			return f.getSelectedFile();
		} else {
			return null;
		}
	}

	/* internal file filter for importer file chooser. */
	private static class ImporterFileFilter extends FileFilter
	{
		private WhatImpoExpo w;

		public ImporterFileFilter(WhatImpoExpo w){
			this.w = w;
		}

		@Override
		public boolean accept(File f){
			if(w.equals( WhatImpoExpo.ALL_CARDS ) || w.equals( WhatImpoExpo.SELECTIVE_CARDS ))
				return f.isDirectory() || f.getName().endsWith( Paths.BCK_CARDS_EXT );

			if(w.equals( WhatImpoExpo.ALL_DECKS ) || w.equals( WhatImpoExpo.SELECTIVE_DECKS ))
				return f.isDirectory() || f.getName().endsWith( Paths.BCK_DECKS_EXT );

			return false;
		}

		@Override
		public String getDescription(){
			return "Zip files";
		}
	}
}
