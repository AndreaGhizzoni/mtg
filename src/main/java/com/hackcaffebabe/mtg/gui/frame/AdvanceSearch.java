package main.java.com.hackcaffebabe.mtg.gui.frame;

import it.hackcaffebabe.jx.table.JXTable;
import javax.swing.JFrame;
import main.java.com.hackcaffebabe.mtg.gui.FramesDimensions;
import main.java.com.hackcaffebabe.mtg.gui.FramesTitles;
import main.java.com.hackcaffebabe.mtg.gui.panel.advancesearch.AdvanceSearchContent;


/**
 * This is the frame to show advance search on {@link JXTable}. 
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class AdvanceSearch extends JFrame
{
	private static final long serialVersionUID = 1L;
	private AdvanceSearchContent contentPane;

	/**
	 * Create the frame.
	 */
	public AdvanceSearch(){
		super( FramesTitles.TITLE_ADVANCE_SEARCH );
		setResizable( false );
		setMinimumSize( FramesDimensions.DIMENSION_ADVANCE_SEARCH );
		setAlwaysOnTop( true );
		setLocation( FramesDimensions.getCenter( FramesDimensions.DIMENSION_ADVANCE_SEARCH ) );
		this.initContent();
		pack();
	}

//===========================================================================================
// METHOD
//===========================================================================================
	/* initialize all components */
	private void initContent(){
		this.contentPane = new AdvanceSearchContent();
		this.contentPane.setOpaque( true );
		setContentPane( this.contentPane );
	}
}
