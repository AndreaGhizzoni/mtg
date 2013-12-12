package com.hackcaffebabe.mtg.gui.panel.deckeditor;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.plaf.basic.BasicButtonUI;


/**
 * top tab component
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
class TabComponent extends JPanel
{
	private static final long serialVersionUID = 1L;
	private final JTabbedPane pane;

	public TabComponent(final JTabbedPane pane){
		//set default FlowLayout' gaps
		super( new FlowLayout( FlowLayout.LEFT, 0, 0 ) );
		if(pane == null)
			throw new NullPointerException( "TabbedPane is null" );

		this.pane = pane;
		setOpaque( false );
		//add more space to the top of the component
		setBorder( BorderFactory.createEmptyBorder( 2, 0, 0, 0 ) );

		//make JLabel read titles from JTabbedPane
		JLabel label = new JLabel(){
			private static final long serialVersionUID = 1L;

			public String getText(){
				int i = pane.indexOfTabComponent( TabComponent.this );
				if(i != -1)
					return pane.getTitleAt( i );
				return null;
			}
		};

		//add more space between the label and the button
		label.setBorder( BorderFactory.createEmptyBorder( 0, 0, 0, 5 ) );
		add( label );

		//tab button
		add( new CloseButton() );
	}

	class CloseButton extends JButton implements ActionListener
	{
		private static final long serialVersionUID = 1L;
		private final MouseListener buttonMouseListener = new MouseAdapter(){
			public void mouseEntered(MouseEvent e){
				Component component = e.getComponent();
				if(component instanceof AbstractButton) {
					AbstractButton button = (AbstractButton) component;
					button.setBorderPainted( true );
				}
			}

			public void mouseExited(MouseEvent e){
				Component component = e.getComponent();
				if(component instanceof AbstractButton) {
					AbstractButton button = (AbstractButton) component;
					button.setBorderPainted( false );
				}
			}
		};

		public CloseButton(){
			setPreferredSize( new Dimension( 17, 17 ) );
			setToolTipText( "close this tab" );
			setUI( new BasicButtonUI() );//Make the button looks the same for all Laf's
			setContentAreaFilled( false );//Make it transparent
			setFocusable( false );//No need to be focusable
			setBorder( BorderFactory.createEtchedBorder() );
			setBorderPainted( false );
			setRolloverEnabled( true );
			addActionListener( this );//Close the proper tab by clicking the button
			//Making nice roll over effect
			//we use the same listener for all buttons
			addMouseListener( buttonMouseListener );

		}

		public void actionPerformed(ActionEvent e){
			int i = pane.indexOfTabComponent( TabComponent.this );
			//TODO check if the last file changes has been saved.
			//if not: ask user if wants to save the changes or not.
			if(i != -1) {
				pane.remove( i );
			}
		}

		//we don't want to update UI for this button
		public void updateUI(){}

		//paint the cross
		protected void paintComponent(Graphics g){
			super.paintComponent( g );
			Graphics2D g2 = (Graphics2D) g.create();
			if(getModel().isPressed()) {//shift the image for pressed buttons
				g2.translate( 1, 1 );
			}
			g2.setStroke( new BasicStroke( 2 ) );
			g2.setColor( Color.BLACK );
			if(getModel().isRollover()) {
				g2.setColor( Color.MAGENTA );
			}
			int delta = 6;
			g2.drawLine( delta, delta, getWidth() - delta - 1, getHeight() - delta - 1 );
			g2.drawLine( getWidth() - delta - 1, delta, delta, getHeight() - delta - 1 );
			g2.dispose();
		}
	}
}