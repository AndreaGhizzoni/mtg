package com.hackcaffebabe.mtg.gui.frame;

import static com.hackcaffebabe.mtg.gui.GUIUtils.*;
import it.hackcaffebabe.ioutil.file.PathUtil;
import it.hackcaffebabe.jx.statusbar.JXStatusBar;
import it.hackcaffebabe.logger.Logger;
import it.hackcaffebabe.logger.Tag;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;
import com.hackcaffebabe.about.About;
import com.hackcaffebabe.mtg.controller.DBCostants;
import com.hackcaffebabe.mtg.controller.json.StoreManager;
import com.hackcaffebabe.mtg.gui.panel.mtg.MTGContent;


/**
 * The main frame of Project
 *  
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class MTG extends JFrame
{
	private static final long serialVersionUID = 1L;
	private MTGContent content;

	/**
	 * Create the frame.
	 */
	public MTG(){
		super( TITLE_MAIN_FRAME );
		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		setMinimumSize( DIMENSION_MAIN_FRAME );
		setLocation( (Toolkit.getDefaultToolkit().getScreenSize().width/2)-(DIMENSION_MAIN_FRAME.width/2),
				     (Toolkit.getDefaultToolkit().getScreenSize().height/2)-(DIMENSION_MAIN_FRAME.height/2));
		addWindowListener( new WinListener() );
		this.initContent();
		this.initMenuBar();
	}
	
//===========================================================================================
// METHOD
//===========================================================================================
	/* initialize all components */
	private void initContent(){
		STATUS_BAR_MAIN_FRAME = new JXStatusBar( this, "Ready!", VERSION, 10 );
		STATUS_BAR_MAIN_FRAME.setTextFont( new Font( Font.MONOSPACED, Font.PLAIN, 11 ) );
		this.content = new MTGContent();
		this.content.setOpaque( true );
		setContentPane( this.content );
		add( STATUS_BAR_MAIN_FRAME, BorderLayout.SOUTH );
	}

	/* initialize all menu bar */
	private void initMenuBar(){
		JMenuBar bar = new JMenuBar();
		
		JMenu file = new JMenu( "File" );
		JMenuItem fileExit = new JMenuItem( "Exit" );
		fileExit.addActionListener( new ActionListener(){
			@Override public void actionPerformed(ActionEvent e){ close(); }
		} );
		file.add( fileExit );
		file.add( new JSeparator() );
//		JMenuItem fileImportUpdate = new JMenuItem("Import and Update");
//		fileImportUpdate.setActionCommand( "IU" );
//		fileImportUpdate.addActionListener( new ImportActionListener() );
//		file.add( fileImportUpdate );
//		
//		JMenuItem fileImportCleaning =  new JMenuItem("Import and Clean");
//		fileImportCleaning.setActionCommand( "IC" );
//		fileImportCleaning.addActionListener( new ImportActionListener() );
//		file.add( fileImportCleaning );

		JMenuItem fileExport =  new JMenuItem( "Export" );
		fileExport.addActionListener( new ExportActionListener() );
		file.add( fileExport );		
		
		JMenu edit = new JMenu( "Edit" );
		JMenuItem editRefresh = new JMenuItem( "Refresh" );
		editRefresh.setAccelerator( KeyStroke.getKeyStroke( "F5" ) );
		editRefresh.addActionListener( new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				refreshMTGTable();
			}
		} );
		edit.add( editRefresh );
		
		JMenu help = new JMenu( "Help" );
		JMenuItem helpAbout = new JMenuItem( "About" );
		helpAbout.addActionListener( new HelpActionListener() );
		helpAbout.setAccelerator( KeyStroke.getKeyStroke( "F1" ) );
		help.add( helpAbout );

		bar.add( file );
		bar.add( edit );
		bar.add( help );
		setJMenuBar( bar );
	}
	
	/* Close the frame */
	public void close(){ 
		dispose();
		Logger.getInstance().write( Tag.INFO, "Program will exit." );
		System.exit( 0 );
	}
	
//===========================================================================================
// INNER CLASS
//===========================================================================================
	private class WinListener implements WindowListener
	{
		public void windowClosing( WindowEvent e ){ close(); }
		public void windowOpened( WindowEvent e ){}
		public void windowClosed( WindowEvent e ){}
		public void windowIconified( WindowEvent e ){}
		public void windowDeiconified( WindowEvent e ){}
		public void windowActivated( WindowEvent e ){}
		public void windowDeactivated( WindowEvent e ){}
	}
	
	/* Import EVent */
//	private class ImportActionListener implements ActionListener
//	{
//		@Override
//		public void actionPerformed(ActionEvent e){
//			JFileChooser f = new JFileChooser( PathUtil.USER_HOME );
//			f.setDialogTitle( "Select backup file" );
//			f.setFileFilter( new FileFilter(){
//				@Override public String getDescription(){ return "Zip files"; }
//				@Override public boolean accept(File f){ return f.isDirectory() || f.getName().endsWith( ".zip" ); }
//			} );
//			
//			if(e.getActionCommand().equals( "IU" ) ){
//				String msg = "Import and update will read a backup zip file and it update current the library.\nContinue?";
//				if( JOptionPane.showConfirmDialog( null, msg, "Confirm", JOptionPane.YES_NO_OPTION )==JOptionPane.YES_OPTION ){
//					if(f.showDialog( null, "Open" )==JFileChooser.APPROVE_OPTION){
//						
//					}
//				}				
//			}else{
//				String msg = "Import and clear will read a backup zip file and replace all cards in your library with backup content.\nContinue?";
//				if( JOptionPane.showConfirmDialog( null, msg, "Confirm", JOptionPane.YES_NO_OPTION )==JOptionPane.YES_OPTION ){
//					if(f.showDialog( null, "Open" )==JFileChooser.APPROVE_OPTION){
//					
//					}
//				}				
//			}			
//		}
//	}
	
	/* Export event */
	private class ExportActionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e){
			JFileChooser f = new JFileChooser( PathUtil.USER_HOME );
			f.setDialogTitle( "Select folder to save backup file" );
			f.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int r = f.showDialog( null, "OK" );
			if(r == JFileChooser.APPROVE_OPTION){
				String destination = f.getSelectedFile().toString();
				File backupFile = new File( destination+PathUtil.FILE_SEPARATOR+DBCostants.BACKUP_FILE_NAME );
				StoreManager.getInstance().createBackup( backupFile );
				
				String msg = "Backup file saved correctly on: "+backupFile.getAbsolutePath();
				JOptionPane.showMessageDialog( null, msg, "Success!", JOptionPane.INFORMATION_MESSAGE );
			}
		}
	}
	
	/* About event */
	private class HelpActionListener implements ActionListener{
		private About about;
		@Override
		public void actionPerformed(ActionEvent e){
			if(about==null){
				About a = new About( "About MTGProject", "MTG Card Manager", VERSION );
				a.setVisible( true );
				a.toFront();
				about = a;
			}else{
				about.setVisible( true );
				about.toFront();
			}
		}		
	}
}
