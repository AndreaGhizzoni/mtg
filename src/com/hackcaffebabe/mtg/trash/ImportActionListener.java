package com.hackcaffebabe.mtg.trash;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Unified Import Action Listener that describe all the import action in the MTG Project.
 *  
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class ImportActionListener implements ActionListener
{

//	private File lastFileSelected = null;

	/**
	 * Instance a import listener in a given mode.
	 * @param whatToImport mode to export
	 */
//	public ImportActionListener(WhatToImport whatToImport) throws IllegalArgumentException{
//		this.whatToImport = whatToImport;
//	}

	@Override
	public void actionPerformed(ActionEvent e){
//		// if user select a valid backup file.
//		if(showUserLocationChooser()) {
//			enableDisableSource( e.getSource(), false );
//			try {
//				switch( whatToImport ) {
//					case ALL_CARDS: {
//						log.write( Tag.INFO, String.format( "Try to Import all from backup: %s", lastFileSelected ) );
//						File jsonPath = new File( Paths.JSON_PATH );
//						List<File> lst = new UnZipper( lastFileSelected, jsonPath ).unZipAll( false );
//						log.write( Tag.DEBUG, String.format( "File unzipped: %s", lst ) );
//						completeAction( lst );
//						break;
//					}
//					case ALL_DECKS: {
//						break;
//					}
//					case SELECTIVE_CARDS: {
//						log.write( Tag.INFO, String.format( "Try to Selected Import from backup: %s", lastFileSelected ) );
//						UnZipper unzip = new UnZipper( lastFileSelected, new File( Paths.JSON_PATH ) );
//						log.write( Tag.DEBUG, "unzipper created correctly" );
//						DefaultListModel<JXCheckListEntry<String>> model = JXCheckList.convertToModel(
//								unzip.listZipContent(), true );
//						JXCheckList<String> lstCheckList = new JXCheckList<>();
//						lstCheckList.setModel( model );
//						JComponent[] input = { new JLabel( "Select wich card you want to import:" ),
//								new JScrollPane( lstCheckList ) };
//						int r = JOptionPane.showConfirmDialog( null, input, "Selected Import",
//								JOptionPane.OK_CANCEL_OPTION );
//						if(r == JOptionPane.OK_OPTION) {
//							log.write( Tag.DEBUG,
//									String.format( "Selected Objects: %s", lstCheckList.getCheckedObjects() ) );
//							List<File> lst = unzip.unZipSelective( lstCheckList.getCheckedObjects() );
//							log.write( Tag.DEBUG, String.format( "File unzipped: %s", lst ) );
//							completeAction( lst );
//						}
//						break;
//					}
//					case SELECTIVE_DECKS: {
//						break;
//					}
//
//				}
//			} catch(IOException ex) {
//				GUIUtils.displayError( null,
//						new Exception( String.format( "Error while reading backup file.\n%s", ex.getMessage() ) ) );
//			}
//
//			enableDisableSource( e.getSource(), true );
//		}
	}

//===========================================================================================
// METHOD
//===========================================================================================
	/* this method show a JFileChooser to let the user to choose the import file. */
//	private boolean showUserLocationChooser(){
//		JFileChooser f = new JFileChooser( PathUtil.USER_HOME );
//		f.setDialogTitle( "Select backup file" );
//		f.setFileFilter( new FileFilter(){
//			@Override
//			public String getDescription(){
//				return "Zip files";
//			}
//
//			@Override
//			public boolean accept(File f){
//				if(whatToImport.equals( WhatToImport.ALL_CARDS ) || whatToImport.equals( WhatToImport.SELECTIVE_CARDS ))
//					return f.isDirectory() || f.getName().endsWith( Paths.BCK_CARDS_EXT );
//
//				if(whatToImport.equals( WhatToImport.ALL_DECKS ) || whatToImport.equals( WhatToImport.SELECTIVE_DECKS ))
//					return f.isDirectory() || f.getName().endsWith( Paths.BCK_DECKS_EXT );
//
//				return false;
//			}
//		} );
//		if(f.showDialog( null, "Open" ) == JFileChooser.APPROVE_OPTION) {
//			this.lastFileSelected = f.getSelectedFile();
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
//	/* this method complete the import action, refreshing the store manager and MTGTable and displaying the file imported */
//	private void completeAction(List<File> lst){
//		if(lst != null && !lst.isEmpty()) {
//			StoreManager.getInstance().refresh();
//			GUIUtils.refreshMTGTable();
//			GUIUtils.displayFiles( lst );
//		}
//	}
}
