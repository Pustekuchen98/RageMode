package at.dafnik.ragemode.Main;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.NoRouteToHostException;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Updater implements Runnable{
	
	Thread thread;
	boolean running;	
	String newversion;
	String url = "http://dafnikdev.bplaced.net/index.html";
	
	public Updater() {
		this.thread = new Thread(this);
	}
	
	public void start() {
		this.running = true;
		if(running) {
			this.thread.start();
		}
	}
	
	@SuppressWarnings("deprecation")
	public void stop(){
		this.running = false;
		this.thread.stop();
	}
	
	@Override
	public void run() {
		while(running) {
			if(Main.getInstance().getConfig().getBoolean("ragemode.settings.updatecheck")) {
				System.out.println("[RageMode] Checking for updates...");
				
				try {			
					URL filesFeed = new URL(url);
					InputStream input = filesFeed.openConnection().getInputStream();
					Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(input);						
					Node latesFile = document.getElementsByTagName("item").item(0);
					NodeList children = latesFile.getChildNodes();						
					newversion = children.item(1).getTextContent();
					
					File file = new File(new File("").getAbsolutePath() + "\\plugins\\RageMode\\RageMode_" + newversion + ".jar");
					String path = file.getAbsolutePath();
					path.replace('\\', '/');
					
					if(!file.exists()) {	
						if(!Main.getInstance().getDescription().getVersion().equalsIgnoreCase(newversion)) {				
							System.out.println("[RageMode] There is a new version of RageMode available:");
							System.out.println("[RageMode] Running version: " + Main.getInstance().getDescription().getVersion());
							System.out.println("[RageMode] New version: " + newversion);
							System.out.println("[RageMode] Auto Update is starting...");
							
							try {
								if(Main.isDebug) System.out.println("[RageMode] Update Path: " + path);								
								if(Main.isDebug) System.out.println("[RageMode] Open connection to Updateserver...");
								ReadableByteChannel in = Channels.newChannel(new URL("http://dafnikdev.bplaced.net/versions/RageMode_" + newversion + ".jar").openStream());
								if(Main.isDebug) System.out.println("[RageMode] Connected to Updatestream!");
								if(Main.isDebug) System.out.println("[RageMode] Connecting FileOutputStream...");
								@SuppressWarnings("resource")
								FileChannel out = new FileOutputStream(path).getChannel();
								if(Main.isDebug) System.out.println("[RageMode] FileOutStream connected!");
								System.out.println("[RageMode] Starting download...");
								out.transferFrom(in, 0, Long.MAX_VALUE);
								System.out.println("[RageMode] Download completed!");								
									
							} catch (Exception e) {
								if(e instanceof UnknownHostException || e instanceof NoRouteToHostException) System.out.println("[RageMode] Checking for updates FAILED! - Please check your internet connection!");
								else {
									System.out.println("[RageMode] Checking for updates FAILED! - Unknown exception!");
									if(Main.isDebug) e.printStackTrace();
								}
							}
							
						} else System.out.println("[RageMode] Your RageMode version is up to date!");
					} else System.out.println("[RageMode] The new version of RageMode has been downloaded! You have to delete the old version and to move it from \"/plugins/RageMode\" to \"/plugins/\"");
					
				} catch (Exception e) {	
					System.out.println("[RageMode] Checking for updates FAILED! - Please check your internet connection!");
				}
			}
			
			this.stop();
		}
	}
}
