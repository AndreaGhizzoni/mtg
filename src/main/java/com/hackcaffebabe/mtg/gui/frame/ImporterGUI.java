package main.java.com.hackcaffebabe.mtg.gui.frame;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import main.java.com.hackcaffebabe.mtg.gui.FramesDimensions;
import main.java.com.hackcaffebabe.mtg.gui.FramesTitles;


/**
 * User Interface for importer.
 *  
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class ImporterGUI extends JFrame
{
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextArea textArea;
	private JButton btnClose;
	private JProgressBar bar;

	/** Create the frame. */
	public ImporterGUI(){
		super( FramesTitles.TITLE_IMPORTER );
		this.initContent();
		setContentPane( contentPane );
		setSize( FramesDimensions.DIMENSION_IMPOEXPO );
		setLocation( 100, 100 );
		setUndecorated( true );
		toFront();
		setVisible( true );
	}

	/* initialize all the components */
	private void initContent(){
		contentPane = new JPanel();
		contentPane.setBorder( new EmptyBorder( 5, 5, 5, 5 ) );
		contentPane.setLayout( new BorderLayout( 2, 2 ) );

		this.textArea = new JTextArea();
		this.textArea.setFont( new Font( Font.MONOSPACED, Font.PLAIN, 12 ) );
		this.textArea.setEditable( false );
		this.textArea.setBackground( UIManager.getColor( "windowBorder" ) );
		contentPane.add( new JScrollPane( this.textArea ) );

		JPanel tmp = new JPanel();
		tmp.setLayout( new BorderLayout( 1, 1 ) );
		this.btnClose = new JButton( "Close" );
		this.btnClose.setEnabled( false );
		this.btnClose.addActionListener( new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				dispose();
				System.gc();
			}
		} );
		tmp.add( this.btnClose, BorderLayout.EAST );

		this.bar = new JProgressBar();
		this.bar.setStringPainted( true );
		tmp.add( this.bar );

		contentPane.add( tmp, BorderLayout.SOUTH );
	}

//===========================================================================================
// GETTER
//===========================================================================================
	/** getter for text area */
	public JTextArea getTextArea(){
		return this.textArea;
	}

	/** getter for progress bar */
	public JProgressBar getProgressBar(){
		return this.bar;
	}

	/** getter for closed button */
	public JButton getClosedButton(){
		return this.btnClose;
	}
}
