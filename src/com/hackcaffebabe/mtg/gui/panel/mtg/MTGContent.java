package com.hackcaffebabe.mtg.gui.panel.mtg;

import static com.hackcaffebabe.mtg.gui.GUIUtils.DIMENSION_MAIN_FRAME;
import static com.hackcaffebabe.mtg.gui.GUIUtils.JXTABLE_MTG;
import static com.hackcaffebabe.mtg.gui.GUIUtils.JXTABLE_MTG_COLUMN_ADJUSTER;
import static com.hackcaffebabe.mtg.gui.GUIUtils.PNL_MTGPROPERTIES;
import static com.hackcaffebabe.mtg.gui.GUIUtils.refreshMTGTable;
import it.hackcaffebabe.jx.table.JXTable;
import it.hackcaffebabe.jx.table.JXTableColumnAdjuster;
import it.hackcaffebabe.jx.table.model.JXObjectModel;
import it.hackcaffebabe.logger.Logger;
import it.hackcaffebabe.logger.Tag;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import net.miginfocom.swing.MigLayout;
import com.hackcaffebabe.mtg.gui.panel.mtg.listener.AdvanceSearchActionListener;
import com.hackcaffebabe.mtg.gui.panel.mtg.listener.DoubleClickMouseAdapter;
import com.hackcaffebabe.mtg.model.MTGCard;


/**
 * This is the content pane of main panel
 *  
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.1
 */
public class MTGContent extends JPanel
{
	private static final long serialVersionUID = 1L;

	private JPanel pnlMTGList = new JPanel();

	private JTextField txtSearch;
	private JButton btnAdvanceSearch;

	private Logger log = Logger.getInstance();

	/**
	 * Create the panel.
	 */
	public MTGContent(){
		super();
		setSize( DIMENSION_MAIN_FRAME );
		setLayout( new MigLayout( "", "[698.00,grow][190!][190!]", "[grow][60!]" ) );
		this.initContent();
		this.initShortcut();
		refreshMTGTable();
	}

//===========================================================================================
// METHOD
//===========================================================================================
	/* initialize all components */
	private void initContent(){
		// MTG search
		JPanel pnlSearch = new JPanel();
		pnlSearch.setBorder( new TitledBorder( "Search by String:" ) );
		pnlSearch.setLayout( new MigLayout( "", "[grow][150!]", "[]" ) );
		add( pnlSearch, "cell 0 1,grow" );

		this.txtSearch = new JTextField();
		pnlSearch.add( this.txtSearch, "cell 0 0,growx,aligny center" );

		this.btnAdvanceSearch = new JButton( "Advance Search" );
		pnlSearch.add( this.btnAdvanceSearch, "cell 1 0,growx,aligny top" );

		// MTG list panel
		this.pnlMTGList.setBorder( new TitledBorder( new LineBorder( new Color( 184, 207, 229 ) ), "MTG Card List",
				TitledBorder.CENTER, TitledBorder.TOP, null, null ) );
		this.pnlMTGList.setLayout( new MigLayout( "", "[grow]", "[grow]" ) );

		JXTABLE_MTG = new JXTable( new JXObjectModel<MTGCard>() );
		JXTABLE_MTG.setFillsViewportHeight( true );
		JXTABLE_MTG.setShowVerticalLines( false );
		JXTABLE_MTG.setRowSorter( this.txtSearch );
		JXTABLE_MTG.getSelectionModel().setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
		JXTABLE_MTG.getSelectionModel().addListSelectionListener( new MTGCardListSelectionListener() );
		JXTABLE_MTG.addMouseListener( new DoubleClickMouseAdapter() );
		JXTABLE_MTG_COLUMN_ADJUSTER = new JXTableColumnAdjuster( JXTABLE_MTG, 5 );
		pnlMTGList.add( new JScrollPane( JXTABLE_MTG, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED ), "cell 0 0,grow" );

		add( this.pnlMTGList, "cell 0 0,grow" );

		// MTG card properties
		PNL_MTGPROPERTIES = new MTGProperties();
		add( PNL_MTGPROPERTIES, "cell 1 0 2 2,grow" );
	}

	/* initialize all the short cut in the content */
	private void initShortcut(){
		this.txtSearch.getInputMap().put( KeyStroke.getKeyStroke( "ESCAPE" ), "clear" );
		this.txtSearch.getActionMap().put( "clear", new AbstractAction(){
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e){
				txtSearch.setText( "" );
			}
		} );

		this.btnAdvanceSearch.setMnemonic( KeyEvent.VK_S );
		this.btnAdvanceSearch.addActionListener( new AdvanceSearchActionListener() );
	}

//===========================================================================================
// INNER CLASS
//===========================================================================================
	/* Event handle on row selection */
	private class MTGCardListSelectionListener implements ListSelectionListener
	{
		@Override
		@SuppressWarnings("unchecked")
		// TODO this event is performed two times for click!
		public void valueChanged(ListSelectionEvent e){
			int selRow = JXTABLE_MTG.getSelectedModelRow();
			if(selRow != -1) {
				MTGCard c = ((JXObjectModel<MTGCard>) JXTABLE_MTG.getModel()).getObject( selRow );
				PNL_MTGPROPERTIES.setMTGCardToView( c );
				log.write( Tag.DEBUG, c.toString() );
			}
		}
	}
}
