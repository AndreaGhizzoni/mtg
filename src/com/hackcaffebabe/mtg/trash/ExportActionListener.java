package com.hackcaffebabe.mtg.trash;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Unified Export Action Listener that describe all the export action in the MTG Project.
 *  
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class ExportActionListener implements ActionListener
{
	/** Enumeration that describe the perform action */
	public enum WhatToExport
	{
		/** export all cards */
		ALL_CARDS,
		/** export all decks */
		ALL_DECKS,
	}

//	private WhatToExport whatToExport = null;
//	private String lastLocationUserSelection = PathUtil.USER_HOME;

	/**
	 * Instance a export listener in a given mode.
	 * @param whatExport mode to export
	 * @throws IllegalArgumentException if argument given is null.
	 */
	public ExportActionListener(WhatToExport whatExport) throws IllegalArgumentException{
		if(whatExport == null)
			throw new IllegalArgumentException( "Argument given can not be null." );
//		this.whatToExport = whatExport;
	}

	/**
	 * Instance an export listener whit a given mode and a default export location.
	 * @param whatExport mode to export
	 * @param defaultExportlocation {@link String} the default location to export, is user don't choose differently.
	 * @throws IllegalArgumentException if argument given is null or empty string.
	 */
	public ExportActionListener(WhatToExport whatExport, String defaultExportlocation) throws IllegalArgumentException{
//		this( whatExport );
//		if(defaultExportlocation != null && !defaultExportlocation.isEmpty())
//			this.lastLocationUserSelection = defaultExportlocation;
	}

	@Override
	public void actionPerformed(ActionEvent e){
		// if user select a valid location to export.
//		if(showUserLocationChooser()) {
//			enableDisableSource( e.getSource(), false );
//			try {
//				switch( whatToExport ) {
//					case ALL_CARDS: {
//						StoreManager.getInstance().createCardsBackup( makeBackupFile() );
//						break;
//					}
//					case ALL_DECKS: {
//						//TODO here goes the export all decks action.
//						break;
//					}
//					default:
//						break;
//				}
//			} catch(IOException ex) {
//				displayError( null, new Exception( "Error to export.\n" + ex.getMessage() ) );
//			}
//
//			enableDisableSource( e.getSource(), true );
//			// if all is done right display success message.
//			displaySuccessMessage( null, String.format( "Backup file saved correctly on %s", lastLocationUserSelection ) );
//		}
	}

//===========================================================================================
// METHOD
//===========================================================================================
//	/* this method show a JFileChooser to let the user to choose the export location. */
//	private boolean showUserLocationChooser(){
//		JFileChooser f = new JFileChooser( PathUtil.USER_HOME );
//		f.setDialogTitle( "Select export folder" );
//		f.setFileSelectionMode( JFileChooser.DIRECTORIES_ONLY );
//		if(f.showDialog( null, "Export Here!" ) == JFileChooser.APPROVE_OPTION) {
//			lastLocationUserSelection = f.getSelectedFile().getAbsolutePath();
//			return true;
//		} else {
//			return false;
//		}
//	}
//
//	/* this method prevent the multiple calling of method */
//	private void enableDisableSource(Object src, boolean enableDisable){
//		if(src != null) {
//			if(src instanceof JButton) {
//				((JButton) src).setEnabled( enableDisable );
//				((JButton) src).setCursor( enableDisable ? null : Cursor.getPredefinedCursor( Cursor.WAIT_CURSOR ) );
//			} else if(src instanceof JMenuItem) {
//				((JMenuItem) src).setEnabled( enableDisable );
//				((JMenuItem) src).setCursor( enableDisable ? null : Cursor.getPredefinedCursor( Cursor.WAIT_CURSOR ) );
//			}
//		}
//	}
//
//	/* this method creates the appropriate File object according to the mode and the last location user selection */
//	private File makeBackupFile(){
//		if(whatToExport.equals( WhatToExport.ALL_DECKS )) {
//			return new File( lastLocationUserSelection + PathUtil.FILE_SEPARATOR + Paths.BCK_DECKS_NAME );
//		} else if(whatToExport.equals( WhatToExport.ALL_CARDS )) {
//			return new File( lastLocationUserSelection + PathUtil.FILE_SEPARATOR + Paths.BCK_CARDS_NAME );
//		} else {
//			return null;
//		}
//	}
}
