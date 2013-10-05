package com.hackcaffebabe.mtg.controller.json;

//import java.io.File;
//import it.hackcaffebabe.ioutil.file.IOSerializable;
//import it.hackcaffebabe.logger.Logger;
//import it.hackcaffebabe.logger.Tag;
//import com.hackcaffebabe.mtg.controller.DBCostants;
//import com.hackcaffebabe.mtg.model.MTGCard;

public class Test
{
	public static void main(String...args){
		try{
//			for(File f: new File(DBCostants.STORE_PATH).listFiles()){
//				Logger.getInstance().write( Tag.DEBUG, f.getName() );
//				MTGCard c1 = IOSerializable.load( MTGCard.class, f );
//				StoreManager.getInstance().store( c1 );
//			}		
			
			StoreManager.getInstance();
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
