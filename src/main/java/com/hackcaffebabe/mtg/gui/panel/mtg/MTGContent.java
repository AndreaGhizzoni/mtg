package com.hackcaffebabe.mtg.gui.panel.mtg;

import static com.hackcaffebabe.mtg.gui.GUIUtils.JXTABLE_MTG;
import static com.hackcaffebabe.mtg.gui.GUIUtils.JXTABLE_MTG_COLUMN_ADJUSTER;
import static com.hackcaffebabe.mtg.gui.GUIUtils.PNL_MTGPROPERTIES;
import static com.hackcaffebabe.mtg.gui.GUIUtils.refreshMTGTable;
import it.hackcaffebabe.jx.table.JXTable;
import it.hackcaffebabe.jx.table.JXTableColumnAdjuster;
import it.hackcaffebabe.jx.table.model.JXObjectModel;
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
import net.miginfocom.swing.MigLayout;
import com.hackcaffebabe.mtg.gui.FramesDimensions;
import com.hackcaffebabe.mtg.gui.panel.mtg.listener.AdvanceSearchActionListener;
import com.hackcaffebabe.mtg.gui.panel.mtg.listener.SelectionMTGCardList;
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

	private JTextField txtSearch = new JTextField();
	private JButton btnAdvanceSearch = new JButton( "Advance Search" );

	/**
	 * Create the panel.
	 */
	public MTGContent(){
		super();
		setSize( FramesDimensions.DIMENSION_MAIN_FRAME );
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
		pnlSearch.add( this.txtSearch, "cell 0 0,growx,aligny center" );
		pnlSearch.add( this.btnAdvanceSearch, "cell 1 0,growx,aligny top" );
		add( pnlSearch, "cell 0 1,grow" );

		// MTG list panel
		this.pnlMTGList.setBorder( new TitledBorder( new LineBorder( new Color( 184, 207, 229 ) ), "MTG Card List",
				TitledBorder.CENTER, TitledBorder.TOP, null, null ) );
		this.pnlMTGList.setLayout( new MigLayout( "", "[grow]", "[grow]" ) );

		JXTABLE_MTG = new JXTable( new JXObjectModel<MTGCard>() );
		JXTABLE_MTG.setFillsViewportHeight( true );
		JXTABLE_MTG.setShowGrid( false );
		JXTABLE_MTG.setGridColor( new Color( 0xd9d9d9 ) );
		JXTABLE_MTG.setOpaque( false );
		JXTABLE_MTG.setRowSorter( this.txtSearch );
		JXTABLE_MTG_COLUMN_ADJUSTER = new JXTableColumnAdjuster( JXTABLE_MTG, 5 );

		SelectionMTGCardList x = new SelectionMTGCardList();
		JXTABLE_MTG.addMouseListener( x );
		JXTABLE_MTG.getSelectionModel().addListSelectionListener( x );
		JXTABLE_MTG.getSelectionModel().setSelectionMode( ListSelectionModel.SINGLE_SELECTION );

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
}
