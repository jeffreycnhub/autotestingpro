package com.selenium.demo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class MainPage {

    WebDriver driver;

    void needLogin() throws InterruptedException, IOException {
        //扫码登录
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.get("https://work.weixin.qq.com/wework_admin/frame");
        //Sleep 15
        Thread.sleep(15000);
        Set<Cookie> cookies = driver.manage().getCookies();
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        mapper.writeValue(new File("cookies.yaml"), cookies);
        System.exit(0);

    }

    void beforeAll() throws InterruptedException, IOException {
        File file = new File("cookies.yaml");
        if (file.exists()) {
            //读取Cookie文件
            driver = new ChromeDriver();
            driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
            driver.get("https://work.weixin.qq.com/wework_admin/frame");
            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            TypeReference typeReference = new TypeReference<List<HashMap<String, Object>>>() {
            };
            List<HashMap<String, Object>> cookies = mapper.readValue(file, typeReference);
            try {
                cookies.forEach(cookieMap -> driver.manage().addCookie(new Cookie(cookieMap.get("name").toString(), cookieMap.get("value").toString())));
            } catch (Exception e) {
                System.err.println("getMessage():" + e.getMessage());
                e.printStackTrace();
            }
            driver.navigate().refresh();
            Thread.sleep(15000);

        } else {
            needLogin();
        }
    }

    public MainPage() throws IOException, InterruptedException {
        this.beforeAll();
    }

    public ContactPage contact() {

        click(By.id("menu_contacts"));
        return new ContactPage(driver);
    }

    void click(By by){
        driver.findElement(by).click();
    }

    void sendKeys(By by, String content){
        driver.findElement(by).sendKeys(content);
    }

}
