package main.java.com.hackcaffebabe.mtg.gui.frame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import main.java.com.hackcaffebabe.mtg.controller.statistics.StatConstants;
import main.java.com.hackcaffebabe.mtg.controller.statistics.Statistics;
import main.java.com.hackcaffebabe.mtg.gui.FramesDimensions;


/**
 * This class is the frame to display all the statistics cards.
 *  
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class StatisticsGUI extends JFrame
{
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/** Create the frame. */
	public StatisticsGUI(){
		this.init();
		setSize( FramesDimensions.DIMENSION_STATISTICS );
		setLocation( FramesDimensions.getRigth( FramesDimensions.DIMENSION_STATISTICS ) );
	}

	/* initialize all the components */
	private void init(){
		contentPane = new JPanel();
		contentPane.setBorder( new EmptyBorder( 5, 5, 5, 5 ) );
		setContentPane( contentPane );
	}

	/**
	 * This method show the statics and set visible to true.
	 * @param s {@link Statistics} to show up.
	 */
	public void show(Statistics s){
		int t = s.getStats().size();
		setLayout( new GridLayout( t, 1 ) );
		for(Map.Entry<StatConstants, Integer> i: s.getStats().entrySet()) {
			contentPane.add( new Stat( i.getKey().getName(), i.getValue() ) );
			contentPane.revalidate();
			validate();
		}
		setVisible( true );
	}

//===========================================================================================
// INNER cLASS
//===========================================================================================
	class Stat extends JPanel
	{
		private static final long serialVersionUID = 1L;
		JLabel lblValue = new JLabel();
		JLabel lblDesc = new JLabel();

		Stat(String n, int stat){
			setLayout( new BorderLayout() );
			lblDesc.setText( n );
			lblValue.setHorizontalAlignment( JLabel.RIGHT );
			lblValue.setText( String.valueOf( stat ) );
			add( lblDesc, BorderLayout.WEST );
			add( lblValue );
		}
	}
}
