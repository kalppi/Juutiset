package com.jarnoluu.service;

import com.jarnoluu.domain.Picture;
import com.jarnoluu.repository.PictureRepository;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PictureService {
    @Autowired
    private PictureRepository pictureRepository;
    
    public Picture createSmaller(Picture pic, int height) throws IOException {
        BufferedImage img = ImageIO.read(new ByteArrayInputStream(pic.getContent()));
        
        float p = height / (float)img.getHeight();
        int width = (int)(img.getWidth() * p);
        
        BufferedImage smaller = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = smaller.createGraphics();
        
        g.drawImage(img, 0, 0, width, height, null); 
        g.dispose();
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(smaller, "jpg", baos);
        baos.flush();
        
        byte[] bytes = baos.toByteArray();
        baos.close();
        
        Picture picture = new Picture("small_" + pic.getName(), "image/jpeg", bytes.length, bytes);
        
        this.pictureRepository.save(picture);
        
        return picture;
    }
}
