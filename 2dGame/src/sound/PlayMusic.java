package sound;

import java.io.File;
import java.io.InputStream;
import java.util.Random;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class PlayMusic {
	String _30b0 = "/Users/tlg/eclipse-workspace/2dGame/res/sound/30b0.wav/";
	String _b1a3 = "/Users/tlg/eclipse-workspace/2dGame/res/sound/b1a3.wav/";
	
	String[] soundTrack = {_30b0, _b1a3};
	
	Random random = new Random();
	
	String filepath = soundTrack[random.nextInt(soundTrack.length)];
	
	public void playMusic() {
		InputStream music;
		
		try {
			File musicPath = new File(filepath);
			
			if(musicPath.exists())
			{
				AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
				Clip clip = AudioSystem.getClip();
				
				clip.open(audioInput);
				clip.start();
				
				// JOptionPane.showMessageDialog(null, "yo");
			}
			else
			{
				System.out.println("cant find file");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
