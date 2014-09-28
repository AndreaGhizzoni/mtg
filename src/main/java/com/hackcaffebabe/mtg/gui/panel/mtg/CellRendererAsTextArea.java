package com.hackcaffebabe.mtg.gui.panel.mtg;

import java.awt.Component;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.TableCellRenderer;


/**
 * Class that wrap the lines into description column of table ability and effects 
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class CellRendererAsTextArea extends JTextArea implements TableCellRenderer
{
	private static final long serialVersionUID = 1L;

	public CellRendererAsTextArea(){
		setLineWrap( true );
		setWrapStyleWord( true );
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column){
		setText( (String) value );
		setSize( table.getColumnModel().getColumn( column ).getWidth(), getPreferredSize().height );
		if(table.getRowHeight( row ) != getPreferredSize().height)
			table.setRowHeight( row, getPreferredSize().height );
		return this;
	}
}
