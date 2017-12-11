package com.jarnoluu.juutiset.selenium;

import org.fluentlenium.adapter.junit.FluentTest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class JuutisetTest extends FluentTest {
    @LocalServerPort
    private Integer port;
    
    @Test
    public void testIndex5Articles() {
        goTo("http://localhost:" + port + "/");
        
        Assert.assertEquals(5, find("article").count());
    }
    
    @Test
    public void testLatestIsPaged() {
        goTo("http://localhost:" + port + "/uutiset/uusimmat");
        
        Assert.assertEquals(11, find("#list").find("tr").count());
        Assert.assertEquals(3, find("#pages").find("li").count());
    }
}
