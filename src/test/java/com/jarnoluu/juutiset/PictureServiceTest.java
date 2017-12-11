package com.jarnoluu.juutiset;

import com.jarnoluu.juutiset.domain.Picture;
import com.jarnoluu.juutiset.service.PictureService;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.imageio.ImageIO;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PictureServiceTest {
    @Autowired
    private PictureService pictureService;
    
    @Test
    public void testSmaller() throws IOException {
        Path path = Paths.get("pics/miljonaari_etu71217TK_tw.jpg");
        byte[] data = Files.readAllBytes(path);
        
        Picture pic = new Picture("miljonaari_etu71217TK_tw.jpg", "image/jpeg", (long)data.length, data);
        Picture smaller = this.pictureService.createSmaller(pic);
        
        BufferedImage img = ImageIO.read(new ByteArrayInputStream(smaller.getContent()));
        
        Assert.assertEquals(250, img.getHeight());
    }
    
    @Test
    public void testThumb() throws IOException {
        Path path = Paths.get("pics/miljonaari_etu71217TK_tw.jpg");
        byte[] data = Files.readAllBytes(path);
        
        Picture pic = new Picture("miljonaari_etu71217TK_tw.jpg", "image/jpeg", (long)data.length, data);
        Picture smaller = this.pictureService.createThumb(pic);
        
        BufferedImage img = ImageIO.read(new ByteArrayInputStream(smaller.getContent()));
        
        Assert.assertEquals(100, img.getHeight());
    }
}
