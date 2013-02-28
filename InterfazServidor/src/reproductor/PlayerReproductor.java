package reproductor;

import java.awt.image.BufferedImage;
import modelos.Cancion;
import uk.co.caprica.vlcj.component.AudioMediaPlayerComponent;
import uk.co.caprica.vlcj.player.MediaMeta;
import uk.co.caprica.vlcj.player.MediaPlayer;


/**
 *
 * @author 66785379
 */
public class PlayerReproductor extends configVlcj{
    
    private static AudioMediaPlayerComponent reproductor=new AudioMediaPlayerComponent();
    private static AudioMediaPlayerComponent identificador=new AudioMediaPlayerComponent();
    
    public PlayerReproductor(){
        
    }
    
    public boolean reproducir(String mrl){
        return reproductor.getMediaPlayer().playMedia(mrl);
    }
        
    public static void pausar(){
        reproductor.getMediaPlayer().pause();
    }
    
    public MediaPlayer getMediaPlayer(){
        return reproductor.getMediaPlayer();
    }
    
    public static BufferedImage getImage(){
        identificador.getMediaPlayer().prepareMedia(reproductor.getMediaPlayer().mrl());
        identificador.getMediaPlayer().parseMedia();
        return identificador.getMediaPlayer().getMediaMeta().getArtwork();
    }
    
    public Cancion getCancion(String mrl){
        Cancion cancion;
        MediaMeta metadata;
        String duracion="";
        long dur=0,min=0;
        identificador.getMediaPlayer().prepareMedia(mrl);
        identificador.getMediaPlayer().parseMedia();
        metadata = identificador.getMediaPlayer().getMediaMeta();
        dur = reproductor.getMediaPlayer().getLength();
        cancion = new Cancion(0,metadata.getTitle(), metadata.getAlbum(),metadata.getArtist(),dur, mrl);
        return cancion; 
    }
    
}
