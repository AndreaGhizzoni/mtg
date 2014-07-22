package com.hackcaffebabe.mtg.gui.components.deckeditor;

import javax.swing.tree.DefaultMutableTreeNode;


/**
 * TODO ADD DOCS AND DESCRIPTION!!!
 * This class is made to make more easy the management the click event on deck tree
 *  
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public class DeckTreeNode extends DefaultMutableTreeNode
{
	private static final long serialVersionUID = 1L;

	public enum TREENODE_TYPE
	{
		ROOT,
		//GROUP, 
		DECK;
	}

	private TREENODE_TYPE type;

	/**
	 * Instance a Deck Tree Node objects with his type and user object
	 * @param userObj {@link Object} the user object.
	 * @param type {@link TREENODE_TYPE} the type of the node
	 * @throws IllegalArgumentException if type is null.
	 */
	public DeckTreeNode(Object userObj, TREENODE_TYPE type) throws IllegalArgumentException{
		super( userObj );
		if(type == null)
			throw new IllegalArgumentException( "Type of DeckTreeNode can not be null." );
		this.type = type;
	}

//===========================================================================================
// GETTER
//===========================================================================================
	/** @return {@link TREENODE_TYPE} of the node. */
	public TREENODE_TYPE getType(){
		return this.type;
	}
}
