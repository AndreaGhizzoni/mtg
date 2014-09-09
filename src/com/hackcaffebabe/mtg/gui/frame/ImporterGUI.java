package com.hackcaffebabe.mtg.gui.frame;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import com.hackcaffebabe.mtg.controller.impoexpo.Importer;
import com.hackcaffebabe.mtg.controller.impoexpo.What;


/**
 * TODO add doc
 *  
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class ImporterGUI extends JFrame
{
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextArea textArea;
	private JProgressBar bar;

	/**
	 * Create the frame.
	 */
	public ImporterGUI(What what){
		this.initContent();
		setBounds( 100, 100, 450, 300 );//TODO maybe make this in the middle of the screen
		setContentPane( contentPane );
		setVisible( true );
		new Importer( what, textArea, bar ).execute();
	}

	private void initContent(){
		contentPane = new JPanel();
		contentPane.setBorder( new EmptyBorder( 5, 5, 5, 5 ) );
		contentPane.setLayout( new BorderLayout( 2, 2 ) );

		this.textArea = new JTextArea();
		contentPane.add( new JScrollPane( this.textArea ) );

		this.bar = new JProgressBar();
		this.bar.setStringPainted( true );
		contentPane.add( this.bar, BorderLayout.SOUTH );
	}
}
