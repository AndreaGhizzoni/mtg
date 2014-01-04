package com.hackcaffebabe.mtg.gui.frame.listener;

import it.hackcaffebabe.ioutil.file.PathUtil;
import it.hackcaffebabe.ioutil.file.UnZipper;
import it.hackcaffebabe.jx.checklist.JXCheckList;
import it.hackcaffebabe.jx.checklist.JXCheckListEntry;
import it.hackcaffebabe.logger.Logger;
import it.hackcaffebabe.logger.Tag;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.filechooser.FileFilter;
import com.hackcaffebabe.mtg.controller.DBCostants;
import com.hackcaffebabe.mtg.controller.json.StoreManager;
import com.hackcaffebabe.mtg.gui.GUIUtils;


/**
 * Unified Import Action Listener that describe all the import action in the MTG Project.
 *  
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class ImportActionListener implements ActionListener
{
	/** Enumeration that describe the perform action */
	public enum WhatToImport
	{
		/** import all cards */
		ALL_CARDS,
		/** import only the cards that are choose by the user. */
		SELECTIVE_CARDS,
		/** import all decks */
		ALL_DECKS,
		/** import only the decks that are choose by the user. */
		SELECTIVE_DECKS
	}

	private Logger log = Logger.getInstance();

	private WhatToImport whatToImport = null;
	private File lastFileSelected = null;

	/**
	 * Instance a import listener in a given mode.
	 * @param whatToImport mode to export
	 * @throws IllegalArgumentException if argument given is null.
	 */
	public ImportActionListener(WhatToImport whatToImport) throws IllegalArgumentException{
		if(whatToImport == null)
			throw new IllegalArgumentException( "Argument given can not be null." );
		this.whatToImport = whatToImport;
	}

	@Override
	public void actionPerformed(ActionEvent e){
		// if user select a valid backup file.
		if(showUserLocationChooser()) {
			enableDisableSource( e.getSource(), false );
			try {
				switch( whatToImport ) {
					case ALL_CARDS: {
						log.write( Tag.INFO, "Try to Import all from backup: " + lastFileSelected );
						File jsonPath = new File( DBCostants.JSON_PATH );
						List<File> lst = new UnZipper( lastFileSelected, jsonPath ).unZipAll( false );
						log.write( Tag.DEBUG, "File unzipped: " + lst );
						completeAction( lst );
						break;
					}
					case ALL_DECKS: {
						break;
					}
					case SELECTIVE_CARDS: {
						log.write( Tag.INFO, "Try to Selected Import from backup: " + lastFileSelected );
						UnZipper unzip = new UnZipper( lastFileSelected, new File( DBCostants.JSON_PATH ) );
						log.write( Tag.DEBUG, "unzipper created correctly" );
						DefaultListModel<JXCheckListEntry<String>> model = JXCheckList.convertToModel(
								unzip.listZipContent(), true );
						JXCheckList<String> lstCheckList = new JXCheckList<>();
						lstCheckList.setModel( model );
						JComponent[] input = { new JLabel( "Select wich card you want to import:" ),
								new JScrollPane( lstCheckList ) };
						int r = JOptionPane.showConfirmDialog( null, input, "Selected Import",
								JOptionPane.OK_CANCEL_OPTION );
						if(r == JOptionPane.OK_OPTION) {
							log.write( Tag.DEBUG, "Selected Objects: " + lstCheckList.getCheckedObjects() );
							List<File> lst = unzip.unZipSelective( lstCheckList.getCheckedObjects() );
							log.write( Tag.DEBUG, "File unzipped: " + lst );
							completeAction( lst );
						}
						break;
					}
					case SELECTIVE_DECKS: {
						break;
					}

				}
			} catch(IOException ex) {
				GUIUtils.displayError( null, new Exception( "Error while reading backup file.\n" + ex.getMessage() ) );
			}

			enableDisableSource( e.getSource(), true );
		}
	}

//===========================================================================================
// METHOD
//===========================================================================================
	/* this method show a JFileChooser to let the user to choose the import file. */
	private boolean showUserLocationChooser(){
		JFileChooser f = new JFileChooser( PathUtil.USER_HOME );
		f.setDialogTitle( "Select backup file" );
		f.setFileFilter( new FileFilter(){
			@Override
			public String getDescription(){
				return "Zip files";
			}

			@Override
			public boolean accept(File f){
				if(whatToImport.equals( WhatToImport.ALL_CARDS ) || whatToImport.equals( WhatToImport.SELECTIVE_CARDS ))
					return f.isDirectory() || f.getName().endsWith( DBCostants.BCK_CARDS_EXT );

				if(whatToImport.equals( WhatToImport.ALL_DECKS ) || whatToImport.equals( WhatToImport.SELECTIVE_DECKS ))
					return f.isDirectory() || f.getName().endsWith( DBCostants.BCK_DECKS_EXT );

				return false;
			}
		} );
		if(f.showDialog( null, "Open" ) == JFileChooser.APPROVE_OPTION) {
			this.lastFileSelected = f.getSelectedFile();
			return true;
		} else {
			return false;
		}
	}

	/* this method prevent the multiple calling of method */
	private void enableDisableSource(Object src, boolean enableDisable){
		if(src != null) {
			if(src instanceof JButton) {
				((JButton) src).setEnabled( enableDisable );
				((JButton) src).setCursor( enableDisable ? null : Cursor.getPredefinedCursor( Cursor.WAIT_CURSOR ) );
			} else if(src instanceof JMenuItem) {
				((JMenuItem) src).setEnabled( enableDisable );
				((JMenuItem) src).setCursor( enableDisable ? null : Cursor.getPredefinedCursor( Cursor.WAIT_CURSOR ) );
			}
		}
	}

	/* this method complete the import action, refreshing the store manager and MTGTable and displaying the file imported */
	private void completeAction(List<File> lst){
		if(lst != null && !lst.isEmpty()) {
			StoreManager.getInstance().refresh();
			GUIUtils.refreshMTGTable();
			GUIUtils.displayFiles( lst );
		}
	}
}
