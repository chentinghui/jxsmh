package com.newsupplytech.jxsmh;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JxsmhApplicationTests {

    @Test
    public void contextLoads() {
        String a = "favicon.ico";
        System.out.println(a.split("\\.")[1]);
    }

}
