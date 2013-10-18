package com.hackcaffebabe.mtg.gui.frame;

import static com.hackcaffebabe.mtg.gui.GUIUtils.*;
import it.hackcaffebabe.jx.table.JXTable;
import java.awt.Toolkit;
import javax.swing.JFrame;
import com.hackcaffebabe.mtg.gui.panel.advancesearch.AdvanceSearchContent;

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
	public AdvanceSearch(JXTable table){
		super(TITLE_ADVANCE_SEARCH);
		setResizable( false );
		setMinimumSize( DIMENSION_ADVANCE_SEARCH );
		setAlwaysOnTop( true );
		setLocation( (Toolkit.getDefaultToolkit().getScreenSize().width/2)-(DIMENSION_ADVANCE_SEARCH.width/2),
				     (Toolkit.getDefaultToolkit().getScreenSize().height/2)-(DIMENSION_ADVANCE_SEARCH.height/2));
		this.initContent(table);
	}
	
//===========================================================================================
// METHOD
//===========================================================================================
	/* Initialize the content */
	private void initContent(JXTable t){
		this.contentPane = new AdvanceSearchContent(t);
		setContentPane( this.contentPane );
	}
}
