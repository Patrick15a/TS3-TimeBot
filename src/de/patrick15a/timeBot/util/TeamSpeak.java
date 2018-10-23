package de.patrick15a.timeBot.util;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.TS3Config;
import com.github.theholywaffle.teamspeak3.TS3Query;
import com.github.theholywaffle.teamspeak3.api.exception.TS3CommandFailedException;
import com.github.theholywaffle.teamspeak3.api.exception.TS3ConnectionFailedException;
import com.github.theholywaffle.teamspeak3.api.reconnect.ConnectionHandler;
import com.github.theholywaffle.teamspeak3.api.reconnect.ReconnectStrategy;

public class TeamSpeak {
	
	private String		host		= "localhost";
	private int		port		= 9987;
	private int		queryport	= 10011;
	private String		queryname	= "";
	private String		password	= "";

	public TeamSpeak(String host, int port, int queryport, String queryname, String password) {
		
		this.host = host;
		this.port = port;
		this.queryport = queryport;
		this.queryname = queryname;
		this.password = password;
		
	}

	public TS3Query connect(String nickname, int channel) throws TS3ConnectionFailedException, TS3CommandFailedException {
		final TS3Config ts3config = new TS3Config();
		
		ts3config.setHost(host);
		ts3config.setQueryPort(queryport);
		ts3config.setReconnectStrategy(ReconnectStrategy.exponentialBackoff());
		
		ts3config.setConnectionHandler(new ConnectionHandler() {
			
			@Override
			public void onConnect(TS3Query ts3Query) {
				
				TS3Api api = ts3Query.getApi();
				
				api.login(queryname, password);
				api.selectVirtualServerByPort(port);
				api.setNickname(nickname);
				api.registerAllEvents();
				
				if (channel != 0) {
					
					api.moveQuery(channel);
					
				}
				
			}

			@Override
			public void onDisconnect(TS3Query ts3Query) {
				
				// TODO Auto-generated method stub
				
			}
			
		});

		final TS3Query query = new TS3Query(ts3config);
		
		query.connect();
		return query;
		
	}
	
}
