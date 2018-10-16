package de.patrick15a.timeBot;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import com.github.theholywaffle.teamspeak3.TS3Api;
import com.github.theholywaffle.teamspeak3.TS3Query;
import com.github.theholywaffle.teamspeak3.api.ChannelProperty;

import de.patrick15a.timeBot.util.TeamSpeak;


public class Main {

	private static TeamSpeak ts;
	private static TS3Query query;
	private static TS3Api api;
	private static Config config;
	
	private static DateTimeFormatter dtf2;
	private static LocalDateTime dateTime;
	
	public static void main(String[] args) {
		
		log("Starting TimeBot...");
		
		config = new Config();
		dtf2 = DateTimeFormatter.ofPattern("EEEE',' dd.MM.yyyy").withLocale(Locale.GERMAN);
		ts = new TeamSpeak(config.hostname,
				config.port,
				config.serverquery_port,
				config.serverquery_username,
				config.serverquery_password);
		
		connect();
		log("Fetching Channel Date....");
		
		if (config.dateChannel != 0) {
			
			String date = api.getChannelInfo(config.dateChannel).getName() + " 00:00:00";
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("'[cspacer]'EEEE',' dd.MM.yyyy HH:mm:ss").withLocale(Locale.GERMAN);
			dateTime = LocalDateTime.parse(date, dtf);
			log("Current Channel Date is " + dtf2.format(dateTime));
			
		} else {
			log("ChannelID is not valid! Stopping Bot!");
			disconnect();
			System.exit(0);
		}
		
		disconnect();
		
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			
			@Override
			public void run() {
				
				LocalDateTime now = LocalDateTime.now();
				
				if (dateTime.getDayOfMonth() != now.getDayOfMonth()) {
					connect();
					log("Updating Channel...");
					api.editChannel(config.dateChannel, ChannelProperty.CHANNEL_NAME, "[cspacer]" + dtf2.format(now));
					log("New Channel Date is " + dtf2.format(now));
					dateTime = now;
					disconnect();
				}
				
			}
		}, 1000, 60*1000);
		
	}
	
	private static void connect() {
		try {
			query = ts.connect(config.botname, config.botChannel);
			api = query.getApi();
		} catch (Exception e) {
			log("Could not connect to the TeamSpeak Server. Stopping Bot!");
			System.exit(0);
		} finally {
			log("Connected to the TeamSpeak Server.");
		}
	}
	
	private static void disconnect() {
		query.exit();
		log("Disconnected from the TeamSpeak Server.");
	}
	
	private static void log(String message) {
		
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("'['dd.MM.yyyy HH:mm:ss']'");
		
		System.out.println(dtf.format(now) + " " + message);
		
	}
	
	public static TS3Api getApi() {
		return api;
	}
	
	public static Config getConfig() {
		return config;
	}

}
