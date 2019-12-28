package chess.GameSound;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class BackgroundMusicPlay extends Thread{
    private File musicFile;
    private FileInputStream fis;
    private BufferedInputStream stream;
    private Player player;
    public void run(){
        musicFile = new File("res/sound/bgm.mp3");
        try {
            fis = new FileInputStream(musicFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        stream = new BufferedInputStream(fis);
        try {
            player = new Player(stream);
        } catch (JavaLayerException e) {
            e.printStackTrace();
        }
        try {
            player.play();
        } catch (JavaLayerException e) {
            e.printStackTrace();
        }

    }
}
