package com.selenium.demo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;


/**
 *
 *
 *
 *
 * */


public class AutoLoginTest {
    //private static WebDriver driver;
    @Test
    public void openWebTest(){

    }

    @Test
    public void autoLoginTest() throws InterruptedException, IOException {
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.get("https://work.weixin.qq.com/wework_admin/frame");
        //Sleep 15
        Thread.sleep(15000);
        Set<Cookie> cookies = driver.manage().getCookies();
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        mapper.writeValue(new File("cookies.yaml"),cookies);
        System.exit(0);
        driver.quit();

    }
    //自动登录企业微信，方式是通过读取已经存储的cookie，自动登录，注意cookie的过期时间
    // quit()和 close() 方法的区别
    @Test
    public void autoLoginedTest() throws InterruptedException, IOException {
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.get("https://work.weixin.qq.com/wework_admin/frame");

        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        TypeReference typeReference = new TypeReference<List<HashMap<String, Object>>>(){};
        List<HashMap<String,Object>> cookies = mapper.readValue(new File("cookies.yaml"), typeReference);
        System.out.print(cookies);

        cookies.forEach(cookieMap-> driver.manage().addCookie(new Cookie(cookieMap.get("name").toString(),cookieMap.get("value").toString())));
        driver.navigate().refresh();
        Thread.sleep(15000);
        driver.quit();

    }




}
