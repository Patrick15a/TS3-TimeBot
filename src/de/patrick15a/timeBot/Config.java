package de.patrick15a.timeBot;

import de.patrick15a.timeBot.util.ConfigProperties;

public class Config {

	private final ConfigProperties properties = new ConfigProperties();
	
	public String botname = "TimeBot";
	public int botChannel = 0;
	public int dateChannel = properties.getInt("DATE_channelID", 0);
	
	public String hostname = properties.getString("TS3_hostname", "localhost");
	public int port = properties.getInt("TS3_port", 9987);
	public String serverquery_username = properties.getString("TS3_serverquery_username", "serveradmin");
	public String serverquery_password = properties.getString("TS3_serverquery_password", "");
	public int serverquery_port = properties.getInt("TS3_serverquery_port", 10011);
	
}
