package com.jarnoluu.juutiset.service;

import com.jarnoluu.juutiset.domain.Picture;
import com.jarnoluu.juutiset.repository.PictureRepository;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class PictureService {
    @Autowired
    private PictureRepository pictureRepository;
    
    public Picture createSmaller(Picture pic) throws IOException {
        return this.resize(pic, 250);
    }
    
    public Picture createThumb(Picture pic) throws IOException {
        return this.resize(pic, 100);
    }
    
    private Picture resize(Picture pic, int height) throws IOException {
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
        
        Picture picture = new Picture(pic.getName(), "image/jpeg", (long)bytes.length, bytes);
        
        this.pictureRepository.save(picture);
        
        return picture;
    }
}
