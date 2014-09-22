package main.java.com.hackcaffebabe.mtg.controller.statistics;

import it.hackcaffebabe.logger.Logger;
import it.hackcaffebabe.logger.Tag;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;
import main.java.com.hackcaffebabe.mtg.controller.json.StoreManager;
import main.java.com.hackcaffebabe.mtg.gui.frame.StatisticsGUI;
import main.java.com.hackcaffebabe.mtg.model.Creature;
import main.java.com.hackcaffebabe.mtg.model.Enchantment;
import main.java.com.hackcaffebabe.mtg.model.Instant;
import main.java.com.hackcaffebabe.mtg.model.Land;
import main.java.com.hackcaffebabe.mtg.model.MTGCard;
import main.java.com.hackcaffebabe.mtg.model.Planeswalker;
import main.java.com.hackcaffebabe.mtg.model.Sorcery;
import main.java.com.hackcaffebabe.mtg.model.color.Mana;


/**
 * TODO add doc
 * TODO add method to count statistics
 *  
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public final class Statistics extends SwingWorker<Void, String>
{
	// this map contains the statistics has tuple.
	// the key is a fancy name and the value is the occurrence of the key into MTGCars's set
	private LinkedHashMap<StatConstants, Integer> stat = new LinkedHashMap<>();
	private JProgressBar progress;
	private JTextArea text;

	/** Instance the Statistics card collector */
	public Statistics(StatisticsGUI gui){
		addPropertyChangeListener( new PCL() );
		StatConstants[] s = StatConstants.values();
		for(StatConstants i: s)
			stat.put( i, 0 );

		this.progress = gui.getProgress();
		this.text = gui.getTextArea();
	}

//===========================================================================================
// METHOD
//===========================================================================================
	/* perform +1 to appropriate rarity */
	private void countRarity(MTGCard m){
		int tmp;
		switch( m.getRarity() ) {
			case COMMON:
				tmp = this.stat.get( StatConstants.COMMONS );
				this.stat.replace( StatConstants.COMMONS, tmp + 1 );
				break;
			case MYTHICAL:
				tmp = this.stat.get( StatConstants.MYTHICAL );
				this.stat.replace( StatConstants.MYTHICAL, tmp + 1 );
				break;
			case NON_COMMON:
				tmp = this.stat.get( StatConstants.NON_COMMON );
				this.stat.replace( StatConstants.NON_COMMON, tmp + 1 );
				break;
			case RARE:
				tmp = this.stat.get( StatConstants.RARE );
				this.stat.replace( StatConstants.RARE, tmp + 1 );
				break;
		}
	}

	/* perform +1 for the type of mtg card */
	private void countType(MTGCard m){
		int tmp;
		if(m instanceof Creature) {
			tmp = this.stat.get( StatConstants.CREATURE );
			this.stat.replace( StatConstants.CREATURE, tmp + 1 );
		} else if(m instanceof Instant) {
			tmp = this.stat.get( StatConstants.INSTANT );
			this.stat.replace( StatConstants.INSTANT, tmp + 1 );
		} else if(m instanceof Sorcery) {
			tmp = this.stat.get( StatConstants.SORCERY );
			this.stat.replace( StatConstants.SORCERY, tmp + 1 );
		} else if(m instanceof Enchantment) {
			tmp = this.stat.get( StatConstants.ENCHANTMENT );
			this.stat.replace( StatConstants.ENCHANTMENT, tmp + 1 );
		} else if(m instanceof Land) {
			tmp = this.stat.get( StatConstants.LAND );
			this.stat.replace( StatConstants.LAND, tmp + 1 );
		} else if(m instanceof Planeswalker) {
			tmp = this.stat.get( StatConstants.PLANESWALKER );
			this.stat.replace( StatConstants.PLANESWALKER, tmp + 1 );
		}
	}

	/* count other stuff */
	private void countMiscellaneus(MTGCard m){
		int tmp;
		if(m.isLegendary()) {
			tmp = this.stat.get( StatConstants.LEGENDARY );
			this.stat.replace( StatConstants.LEGENDARY, tmp + 1 );
		}
	}

	/* perform +1 for each card color and card color type. */
	private void countColorAndColorType(MTGCard m){
		int tmp;
		switch( m.getCardColor().getType() ) {
			case COLOR_LESS:
				tmp = this.stat.get( StatConstants.COLOR_LESS );
				this.stat.replace( StatConstants.COLOR_LESS, tmp + 1 );
				break;
			case HYBRID:
				tmp = this.stat.get( StatConstants.HYBRID );
				this.stat.replace( StatConstants.HYBRID, tmp + 1 );
				break;
			case MONO_COLOR:
				tmp = this.stat.get( StatConstants.MONO );
				this.stat.replace( StatConstants.MONO, tmp + 1 );
				break;
			case MULTI_COLOR:
				tmp = this.stat.get( StatConstants.MULTICOLOR );
				this.stat.replace( StatConstants.MULTICOLOR, tmp + 1 );
				break;
		}

		if(m.getCardColor().getColors().contains( Mana.RED )) {
			tmp = this.stat.get( StatConstants.RED );
			this.stat.replace( StatConstants.RED, tmp + 1 );
		} else if(m.getCardColor().getColors().contains( Mana.BLACK )) {
			tmp = this.stat.get( StatConstants.BLACK );
			this.stat.replace( StatConstants.BLACK, tmp + 1 );
		} else if(m.getCardColor().getColors().contains( Mana.BLUE )) {
			tmp = this.stat.get( StatConstants.BLUE );
			this.stat.replace( StatConstants.BLUE, tmp + 1 );
		} else if(m.getCardColor().getColors().contains( Mana.WHITE )) {
			tmp = this.stat.get( StatConstants.WHITE );
			this.stat.replace( StatConstants.WHITE, tmp + 1 );
		} else if(m.getCardColor().getColors().contains( Mana.GREEN )) {
			tmp = this.stat.get( StatConstants.GREEN );
			this.stat.replace( StatConstants.GREEN, tmp + 1 );
		}
	}

//===========================================================================================
// OVERRIDE
//===========================================================================================
	@Override
	protected Void doInBackground() throws Exception{
		long start = System.currentTimeMillis();

		Set<MTGCard> allCards = StoreManager.getInstance().getAllCards();
		int tot = allCards.size();
		int i = 1;

		this.stat.replace( StatConstants.CARDS, tot );
		publish( format( StatConstants.CARDS.getName(), tot ) );
		setProgress( (i++ * 100) / tot );
		for(MTGCard s: allCards) {
			countRarity( s );
			countType( s );
			countMiscellaneus( s );
			countColorAndColorType( s );
			setProgress( (i++ * 100) / tot );
		}

		long end = System.currentTimeMillis();
		Logger.getInstance().write( Tag.INFO, String.format( "Collecting data complete in %dms", (end - start) ) );
		return null;
	}

	@Override
	protected void process(List<String> chunks){
		for(String i: chunks) {
			this.text.append( i );
		}
	}

	/* the return of this method is passed at the publish method. */
	private String format(String s, int d){
		return String.format( "%-50s %10d\n", s, d );
	}

//===========================================================================================
// INNER CLASS
//===========================================================================================
	/* property change listener to determinate when swing worker is finish. */
	private class PCL implements PropertyChangeListener
	{
		@Override
		public void propertyChange(PropertyChangeEvent evt){
			switch( evt.getPropertyName() ) {
				case "progress":
					progress.setIndeterminate( false );
					progress.setValue( (Integer) evt.getNewValue() );
					break;
				case "state":
					switch( (StateValue) evt.getNewValue() ) {
						case DONE:
							break;
						case PENDING:
							progress.setVisible( true );
							progress.setIndeterminate( true );
							break;
						case STARTED:
							break;
						default:
							break;
					}
			}
		}
	}

	//TODO remove this when finish
	public static void main(String... args){

		new Statistics( new StatisticsGUI() ).execute();
	}
}
