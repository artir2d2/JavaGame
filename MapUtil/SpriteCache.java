package MapUtil;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.HashMap;

/**
 * Created by Adam Zadora on 2016-02-05.
 */
public class SpriteCache {
    public SpriteCache(){
        sprites = new HashMap<String, BufferedImage>();
    }

    private BufferedImage loadImage(String path){
        URL url = null;
        try{
            url = getClass().getClassLoader().getResource(path);
            return ImageIO.read(url);
        }catch (Exception e) {
            System.out.println("Przy otwieraniu " + path);
            System.out.println("Wystapil blad : " + url
                    + " message: " + e.getMessage());
            System.exit(0);
            return null;
        }
    }

    public BufferedImage getSprite(String path){
        BufferedImage img = sprites.get(path);
        if(img == null){
            img = loadImage("grafiki/" + path);
            sprites.put(path, img);
        }
        return img;
    }

    private HashMap<String, BufferedImage> sprites;
}
