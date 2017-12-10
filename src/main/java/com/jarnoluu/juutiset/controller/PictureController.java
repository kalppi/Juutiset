package com.jarnoluu.juutiset.controller;

import com.jarnoluu.juutiset.domain.Picture;
import com.jarnoluu.juutiset.repository.PictureRepository;
import java.util.Optional;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PictureController {
    @Autowired
    private PictureRepository pictureRepository;
    
    @GetMapping(path="/picture/{id}")
    @ResponseBody
    public byte[] content(HttpServletResponse response, @PathVariable long id) {
        Optional<Picture> pic = this.pictureRepository.findById(id);        
        
        if(pic.isPresent()) {
            response.setContentType(pic.get().getMediaType());
            
            return pic.get().getContent();
        } else {
            return null;
        }
    }
}
