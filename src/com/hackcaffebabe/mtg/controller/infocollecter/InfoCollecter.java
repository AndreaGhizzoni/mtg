package com.hackcaffebabe.mtg.controller.infocollecter;

import it.hackcaffebabe.logger.Logger;
import it.hackcaffebabe.logger.Tag;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.Map;
import com.hackcaffebabe.mtg.controller.Paths;


/**
 * This class collect all the hardware/software/jvm specifications.
 *  
 * @author Andrea Ghizzoni. More info at andrea.ghz@gmail.com
 * @version 1.0
 */
public final class InfoCollecter
{
	private InfoCollecter(){}

	public static void collect(){
		long start = System.currentTimeMillis();
		try {
			File info = new File( Paths.SPEC_INFO_PATH );
			if(info.exists() && !info.delete()) {
				Logger.getInstance().write( Tag.ERRORS, "Error while deleting old spec.info file!" );
				return;
			}

			FileWriter fw = new FileWriter( info );
			RuntimeMXBean runtimeBean = ManagementFactory.getRuntimeMXBean();
			Map<String, String> systemProperties = runtimeBean.getSystemProperties();
			for(Map.Entry<String, String> entry: systemProperties.entrySet())
				fw.append( String.format( "[%s] = %s.\n", entry.getKey(), entry.getValue() ) );
			fw.close();

			long end = System.currentTimeMillis();
			Logger.getInstance().write( Tag.INFO, String.format( "Data collected in %dms", (end - start) ) );
		} catch(IOException e) {
			Logger.getInstance().write( Tag.ERRORS, e.getMessage() );
			e.printStackTrace( Logger.getInstance().getPrintStream() );
		}
	}
}
