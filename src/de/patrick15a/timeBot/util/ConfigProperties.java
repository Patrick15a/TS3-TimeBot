package de.patrick15a.timeBot.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class ConfigProperties {
	
	public ConfigProperties() {
		
		File file = new File("TS3-TimeBot.properties");
		
		if (!file.exists()) {
			
			try {
				file.createNewFile();
				List<String> lines = Arrays.asList(
						"TS3_hostname=localhost",
						"TS3_port=9987",
						"TS3_serverquery_port=10011",
						"TS3_serverquery_username=serveradmin",
						"TS3_serverquery_password=PASSWORD",
						"",
						"DATE_channelID=0");
				
				Path path = Paths.get("TS3-TimeBot.properties");
				Files.write(path, lines, Charset.forName("UTF-8"));
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		
	}

	public String getString(String key, String defaultValue) {
		
		Properties prop = new Properties();
		
		try {
			
			FileInputStream fis = new FileInputStream("TS3-TimeBot.properties");
			
			prop.load(fis);
			return prop.getProperty(key, defaultValue);
		
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	public int getInt(String key, int defaultValue) {
		
		Properties prop = new Properties();
		
		try {
			
			FileInputStream fis = new FileInputStream("TS3-TimeBot.properties");
			
			prop.load(fis);
			return Integer.parseInt(prop.getProperty(key, defaultValue + ""));
		
		} catch (Exception e) {
			e.printStackTrace();
			return defaultValue;
		}
		
	}
	
}
