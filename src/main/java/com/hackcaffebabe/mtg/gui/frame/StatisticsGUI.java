package main.java.com.hackcaffebabe.mtg.gui.frame;

import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;


/**
 * This class is the frame to display all the statistics cards.
 *  
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class StatisticsGUI extends JFrame
{
	private static final long serialVersionUID = 1L;
	private JProgressBar progress;
	private JTextArea text;

	/** Create the frame. */
	public StatisticsGUI(){
		this.init();
		setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE ); //TODO remove when finish
		setSize( 450, 550 );
		setVisible( true );
	}

	/* initialize all the components */
	private void init(){
		JPanel contentPane = new JPanel();
		contentPane.setBorder( new EmptyBorder( 5, 5, 5, 5 ) );
		contentPane.setLayout( new BorderLayout( 0, 0 ) );

		this.progress = new JProgressBar();
		this.progress.setStringPainted( true );
		this.text = new JTextArea();
		this.text.setEditable( false );
		this.text.setFont( new Font( Font.MONOSPACED, Font.PLAIN, 12 ) );
		this.text.setBackground( UIManager.getColor( "windowBorder" ) );

		contentPane.add( new JScrollPane( this.text ) );
		contentPane.add( this.progress, BorderLayout.SOUTH );
		setContentPane( contentPane );
	}

//===========================================================================================
// GETTER
//===========================================================================================
	/** @return {@link JProgressBar} the progress bar for the task */
	public JProgressBar getProgress(){
		return this.progress;
	}

	/** @return {@link JTextArea} the text area to display what collected. */
	public JTextArea getTextArea(){
		return this.text;
	}
}
