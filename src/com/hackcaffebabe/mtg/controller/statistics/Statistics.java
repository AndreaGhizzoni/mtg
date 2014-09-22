package com.hackcaffebabe.mtg.controller.statistics;

import it.hackcaffebabe.logger.Logger;
import it.hackcaffebabe.logger.Tag;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;
import com.hackcaffebabe.mtg.gui.frame.StatisticsGUI;


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
// OVERRIDE
//===========================================================================================
	@Override
	protected Void doInBackground() throws Exception{
		long start = System.currentTimeMillis();

		int tot = this.stat.size();
		int i = 1;
		for(Map.Entry<StatConstants, Integer> e: this.stat.entrySet()) {
			//count stuff
			publish( format( e.getKey().getName(), e.getValue() ) );
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
