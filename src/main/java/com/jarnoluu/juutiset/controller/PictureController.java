package com.jarnoluu.juutiset.controller;

import com.jarnoluu.juutiset.domain.Picture;
import com.jarnoluu.juutiset.repository.PictureRepository;
import java.util.Optional;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PictureController {
    @Autowired
    private PictureRepository pictureRepository;
    
    @GetMapping(path="/picture/{id}")
    public ResponseEntity<byte[]> content(@PathVariable long id) {
        Optional<Picture> oPic = this.pictureRepository.findById(id);        
        
        if(oPic.isPresent()) {       
            Picture pic = oPic.get();
            
            final HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.parseMediaType(pic.getMediaType()));
            headers.setContentLength(pic.getSize());

            return new ResponseEntity<>(pic.getContent(), headers, HttpStatus.FOUND);
        } else {
            return null;
        }
    }
}
