package com.hackcaffebabe.mtg.gui.panel.mtg;

import static com.hackcaffebabe.mtg.gui.GUIUtils.*;
import it.hackcaffebabe.jx.table.JXTable;
import it.hackcaffebabe.jx.table.JXTableColumnAdjuster;
import it.hackcaffebabe.jx.table.model.JXObjectModel;
import it.hackcaffebabe.logger.Logger;
import it.hackcaffebabe.logger.Tag;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import net.miginfocom.swing.MigLayout;
import com.hackcaffebabe.mtg.gui.frame.InsertCard;
import com.hackcaffebabe.mtg.model.MTGCard;
import com.hackcaffebabe.mtg.model.color.BasicColors;
import com.hackcaffebabe.mtg.model.color.CardColor;
import com.hackcaffebabe.mtg.controller.json.Criteria;
import com.hackcaffebabe.mtg.controller.json.StoreManager;


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

	private JXTable tableMTG;
	private JXTableColumnAdjuster tableAdjuster;
	private MTGCardListSelectionListener tableSelectionListener = new MTGCardListSelectionListener();

	private MTGPropertiesV2 pnlMTGPropreties;

	private JPanel pnlSearch;
	private JTextField txtSearch;
	private JButton btnAdvanceSearch;

	private JButton btnNewCard;
	private JButton btnDeleteCard;

	private Logger log = Logger.getInstance();

	/**
	 * Create the panel.
	 */
	public MTGContent(){
		super();
		setSize( DIMENSION_MAIN_FRAME );
		setLayout( new MigLayout("", "[698.00,grow][190!][190!]", "[grow][60!]") );
		this.initContent();
		this.refreshMTGTable();
	}

//===========================================================================================
// METHOD
//===========================================================================================
	/* initialize all components */
	private void initContent(){
		// MTG search
		this.pnlSearch = new JPanel();
		this.pnlSearch.setBorder( new TitledBorder( null, "Card Search by String:", TitledBorder.LEADING, TitledBorder.TOP, null, null ) );
		this.pnlSearch.setLayout( new MigLayout( "", "[grow][150!]", "[]" ) );
		add( this.pnlSearch, "cell 0 1,grow" );

		this.txtSearch = new JTextField();
		this.txtSearch.getInputMap().put( KeyStroke.getKeyStroke( "ESCAPE" ), "clear" );
		this.txtSearch.getActionMap().put( "clear", new AbstractAction(){
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e){
				txtSearch.setText( "" );
			}
		} );
		this.pnlSearch.add( this.txtSearch, "cell 0 0,grow" );

		this.btnAdvanceSearch = new JButton( "Advance Search" );
		this.btnAdvanceSearch.addActionListener( new AdvanceSearchActionListener() );
		this.pnlSearch.add( this.btnAdvanceSearch, "cell 1 0,growx,aligny top" );

		// MTG list panel
		this.pnlMTGList.setBorder( new TitledBorder( null, "MTG Cards", TitledBorder.LEADING, TitledBorder.TOP, null, null ) );
		this.pnlMTGList.setLayout( new MigLayout( "", "[grow]", "[grow]" ) );
		add( this.pnlMTGList, "cell 0 0 1 1,grow" );

		this.tableMTG = new JXTable( new JXObjectModel<MTGCard>() );
		this.tableMTG.setFillsViewportHeight( true );
		this.tableMTG.setShowVerticalLines( false );
		this.tableMTG.setRowSorter( this.txtSearch );
		this.tableMTG.getSelectionModel().setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
		this.tableMTG.getSelectionModel().addListSelectionListener( this.tableSelectionListener );
		this.tableAdjuster = new JXTableColumnAdjuster( this.tableMTG );
		pnlMTGList.add( new JScrollPane( this.tableMTG ), "cell 0 0,grow" );

		// MTG card properties
		this.pnlMTGPropreties = new MTGPropertiesV2();
		this.pnlMTGPropreties.setBorder( new TitledBorder( null, "MTG Properties", TitledBorder.LEADING, TitledBorder.TOP, null, null ) );
		add( this.pnlMTGPropreties, "cell 1 0 2 1,grow" );

		// button
		this.btnNewCard = new JButton( "New Card" );
		this.btnNewCard.addActionListener( new NewClassActionListener() );
		add( this.btnNewCard, "cell 1 1,grow" );

		this.btnDeleteCard = new JButton( "Delete Card" );
		this.btnDeleteCard.setEnabled( false );
		add( this.btnDeleteCard, "cell 2 1,grow" );		
	}

//	/* This method call a method that take the frame which is in and close it. */
//	private void close(){
//		((MTG) SwingUtilities.getWindowAncestor( this )).close();
//	}

	/**
	 * Refresh the MTG card list
	 */
	public void refreshMTGTable(){
		log.write( Tag.DEBUG, "Refreshing mtg card list" );
		SwingUtilities.invokeLater( new Runnable(){
			public void run(){
				List<MTGCard> lst = StoreManager.getInstance().getAllCardsAsList();
				if(!lst.isEmpty()){
					tableMTG.setModel( new JXObjectModel<MTGCard>( lst ) );
	
					//update sorter and text search
					tableMTG.refreshRowSorter();
					tableAdjuster.adjustColumns();
				}
			}
		} );
	}

//===========================================================================================
// INNER CLASS
//===========================================================================================
	/* Event on button Advance search */
	private class AdvanceSearchActionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e){
			try {
				//TODO finish to check Criteria
				Criteria c = new Criteria();
				c.byColor( new CardColor( BasicColors.RED ) );
				c.byLegendary( true );

				JXObjectModel<MTGCard> model = new JXObjectModel<>();
				for(MTGCard i: StoreManager.getInstance().searchBy( c )) {
					model.addObject( i );
				}
				tableMTG.setModel( model );

				//update sorter and text search
				tableMTG.refreshRowSorter();
				tableAdjuster.adjustColumns();
			}
			catch(Exception ex) {
				ex.printStackTrace( log.getPrintStream() );
			}
		}
	}

	/* Event handle on row selection */
	private class MTGCardListSelectionListener implements ListSelectionListener
	{
		private MTGCard selCard = null;
		// prevents multiple read on MTG card from table.
		// otherwise this event is called twice for each row selection.
		private int prevIndex = -2;

		@Override
		@SuppressWarnings("unchecked")
		public void valueChanged(ListSelectionEvent e){
			int selRow = tableMTG.getSelectedModelRow();
			if(selRow != -1 && selRow != prevIndex) {
				selCard = ((JXObjectModel<MTGCard>) tableMTG.getModel()).getObject( selRow );
				pnlMTGPropreties.setMTGCardToView( selCard );
				btnDeleteCard.setEnabled( true );
				log.write( Tag.DEBUG, selCard.toString() );

				prevIndex = selRow;// update selected index
			}
		}
	}

	/* Event handle on button btnNewCard */
	private class NewClassActionListener implements ActionListener
	{
		private InsertCard frame;

		@Override
		public void actionPerformed(ActionEvent e){
			if(frame == null) {
				InsertCard card = new InsertCard( MTGContent.this );
				card.setVisible( true );
				card.toFront();
				frame = card;
			}
			else {
				frame.setVisible( true );
				frame.toFront();
			}
		}
	}
}
