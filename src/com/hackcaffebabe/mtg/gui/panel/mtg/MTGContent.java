package com.hackcaffebabe.mtg.gui.panel.mtg;

import static com.hackcaffebabe.mtg.gui.GUIUtils.DIMENSION_MAIN_FRAME;
import static com.hackcaffebabe.mtg.gui.GUIUtils.JXTABLE_MTG;
import static com.hackcaffebabe.mtg.gui.GUIUtils.JXTABLE_MTG_COLUMN_ADJUSTER;
import static com.hackcaffebabe.mtg.gui.GUIUtils.PNL_MTGPROPERTIES;
import static com.hackcaffebabe.mtg.gui.GUIUtils.displayError;
import static com.hackcaffebabe.mtg.gui.GUIUtils.refreshMTGTable;
import it.hackcaffebabe.jx.table.JXTable;
import it.hackcaffebabe.jx.table.JXTableColumnAdjuster;
import it.hackcaffebabe.jx.table.model.JXObjectModel;
import it.hackcaffebabe.logger.Logger;
import it.hackcaffebabe.logger.Tag;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import net.miginfocom.swing.MigLayout;
import com.hackcaffebabe.mtg.controller.json.StoreManager;
import com.hackcaffebabe.mtg.gui.panel.mtg.listener.AdvanceSearchActionListener;
import com.hackcaffebabe.mtg.gui.panel.mtg.listener.DoubleClickMouseAdapter;
import com.hackcaffebabe.mtg.gui.panel.mtg.listener.NewCardActionListener;
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

	private MTGCardListSelectionListener tableSelectionListener = new MTGCardListSelectionListener();

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
		setLayout( new MigLayout( "", "[698.00,grow][190!][190!]", "[grow][60!]" ) );
		this.initContent();
		refreshMTGTable();
	}

//===========================================================================================
// METHOD
//===========================================================================================
	/* initialize all components */
	private void initContent(){
		// MTG search
		this.pnlSearch = new JPanel();
		this.pnlSearch.setBorder( new TitledBorder( "Search by String:" ) );
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
		this.pnlSearch.add( this.txtSearch, "cell 0 0,growx,aligny center" );

		this.btnAdvanceSearch = new JButton( "Advance Search" );
		this.btnAdvanceSearch.setMnemonic( KeyEvent.VK_S );
		this.btnAdvanceSearch.addActionListener( new AdvanceSearchActionListener() );
		this.pnlSearch.add( this.btnAdvanceSearch, "cell 1 0,growx,aligny top" );

		// MTG list panel
		this.pnlMTGList.setBorder( new TitledBorder( "MTG Cards" ) );
		this.pnlMTGList.setLayout( new MigLayout( "", "[grow]", "[grow]" ) );
		add( this.pnlMTGList, "cell 0 0 1 1,grow" );

		JXTABLE_MTG = new JXTable( new JXObjectModel<MTGCard>() );
		JXTABLE_MTG.setFillsViewportHeight( true );
		JXTABLE_MTG.setShowVerticalLines( false );
		JXTABLE_MTG.setRowSorter( this.txtSearch );
		JXTABLE_MTG.getSelectionModel().setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
		JXTABLE_MTG.getSelectionModel().addListSelectionListener( this.tableSelectionListener );
		JXTABLE_MTG.addMouseListener( new DoubleClickMouseAdapter() );
		JXTABLE_MTG_COLUMN_ADJUSTER = new JXTableColumnAdjuster( JXTABLE_MTG );
		pnlMTGList.add( new JScrollPane( JXTABLE_MTG, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED ), "cell 0 0,grow" );

		// MTG card properties
		PNL_MTGPROPERTIES = new MTGProperties();
		PNL_MTGPROPERTIES.setBorder( new TitledBorder( "MTG Properties" ) );
		add( PNL_MTGPROPERTIES, "cell 1 0 2 1,grow" );

		// button
		this.btnNewCard = new JButton( "New Card" );
		this.btnNewCard.setMnemonic( KeyEvent.VK_N );
		this.btnNewCard.addActionListener( new NewCardActionListener() );
		add( this.btnNewCard, "cell 1 1,alignx center,aligny center" );

		this.btnDeleteCard = new JButton( "Delete Card" );
		this.btnDeleteCard.addActionListener( new DeleteCardActionListener() );
		this.btnDeleteCard.setEnabled( false );
		add( this.btnDeleteCard, "cell 2 1,alignx center,aligny center" );
	}

//===========================================================================================
// INNER CLASS
//===========================================================================================
	/* Event handle on row selection */
	private class MTGCardListSelectionListener implements ListSelectionListener
	{
		@Override
		@SuppressWarnings("unchecked")
		public void valueChanged(ListSelectionEvent e){
			int selRow = JXTABLE_MTG.getSelectedModelRow();
			if(selRow != -1) {
				MTGCard c = ((JXObjectModel<MTGCard>) JXTABLE_MTG.getModel()).getObject( selRow );
				PNL_MTGPROPERTIES.setMTGCardToView( c );
				log.write( Tag.DEBUG, c.toString() );
				btnDeleteCard.setEnabled( true );
			}
		}
	}

	/* Event handle on button btnDeleteCard */
	private class DeleteCardActionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e){
			MTGCard card = PNL_MTGPROPERTIES.getDisplayedCard();
			log.write( Tag.DEBUG, "card to delete: " + card );
			if(card != null) {
				String msg = String.format( "Are you sure to delete %s ?", card.getName() );
				if(JOptionPane.showConfirmDialog( MTGContent.this, msg, "Be careful!",
						JOptionPane.YES_NO_OPTION ) == 0) {
					try {
						StoreManager.getInstance().delete( card );
						PNL_MTGPROPERTIES.clearAll();
						btnDeleteCard.setEnabled( false );
						refreshMTGTable();
						JOptionPane.showMessageDialog( MTGContent.this, card.getName()
								+ " delete correctly!", "Operation complete!",
								JOptionPane.INFORMATION_MESSAGE );
					} catch(Exception ex) {
						log.write( Tag.ERRORS, ex.getMessage() );
						ex.printStackTrace( Logger.getInstance().getPrintStream() );
						displayError( MTGContent.this, "Error to delete " + card.getName() );
					}
				}
			}
		}
	}
}
