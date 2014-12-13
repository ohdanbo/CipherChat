import java.io.FileOutputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

import Client.Resources;


public class Updater {
	
	public Updater() {
		checkForUpdate();
	}
	
	public void checkForUpdate() {
		int currentVersion = Resources.versionID;
		try {
			URL url = new URL("C:\\Users\\Danbo\\Desktop\\newversion.txt");
			ReadableByteChannel rbc = Channels.newChannel(url.openStream());
			FileOutputStream fos = new FileOutputStream("C:\\Users\\Danbo\\Desktop\\dlnewversion.txt");
			fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
		}catch(Exception ex) {ex.printStackTrace();}
//		int newVersion = 
	}
	
	public static void main(String[] args) {
		new Updater();
	}
}
