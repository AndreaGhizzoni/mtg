package com.hackcaffebabe.mtg.controller.json;

import it.hackcaffebabe.logger.Logger;
import it.hackcaffebabe.logger.Tag;
import com.hackcaffebabe.mtg.model.Artifact;
import com.hackcaffebabe.mtg.model.Creature;
import com.hackcaffebabe.mtg.model.Enchantment;
import com.hackcaffebabe.mtg.model.Instant;
import com.hackcaffebabe.mtg.model.Land;
import com.hackcaffebabe.mtg.model.MTGCard;
import com.hackcaffebabe.mtg.model.Planeswalker;
import com.hackcaffebabe.mtg.model.Sorcery;

import java.io.File;
import it.hackcaffebabe.ioutil.file.IOSerializable;
import it.hackcaffebabe.logger.Logger;
import it.hackcaffebabe.logger.Tag;
import com.hackcaffebabe.mtg.controller.DBCostants;
import com.hackcaffebabe.mtg.model.MTGCard;

public class Test
{
	public static void main(String...args){
		try{
//			for(File f: new File(DBCostants.STORE_PATH).listFiles()){
//				Logger.getInstance().write( Tag.DEBUG, f.getName() );
//				MTGCard c1 = IOSerializable.load( MTGCard.class, f );
//				StoreManager.getInstance().store( c1 );
//			}		
			
			for( MTGCard c : StoreManager.getInstance().getAllCards() ){
				String log = "";
				String eff = "";
				String abb = "";
				if(c instanceof Creature){
					log = ((Creature)c).toString();
					eff = ((Creature)c).getEffects().toString();
					abb = ((Creature)c).getAbilities().toString();
				}else if(c instanceof Artifact){
					log = ((Artifact)c).toString();
					eff = ((Artifact)c).getEffects().toString();
					abb = ((Artifact)c).getAbilities().toString();
				}else if(c instanceof Enchantment){
					log = ((Enchantment)c).toString();
					eff = ((Enchantment)c).getEffects().toString();
					abb = ((Enchantment)c).getAbilities().toString();
				}else if(c instanceof Instant){
					log = ((Instant)c).toString();
					eff = ((Instant)c).getEffects().toString();
					abb = ((Instant)c).getAbilities().toString();
				}else if(c instanceof Land){
					log = ((Land)c).toString();
					eff = ((Land)c).getEffects().toString();
					abb = ((Land)c).getAbilities().toString();
				}else if(c instanceof Planeswalker){
					log = ((Planeswalker)c).toString();
					eff = ((Planeswalker)c).getEffects().toString();
					abb = ((Planeswalker)c).getPlanesAbilities().toString();
				}else if(c instanceof Sorcery){
					log = ((Sorcery)c).toString();
					eff = ((Sorcery)c).getEffects().toString();
					abb = ((Sorcery)c).getAbilities().toString();
				}								
				Logger.getInstance().write( Tag.DEBUG, log+" "+eff+" "+abb);			
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
