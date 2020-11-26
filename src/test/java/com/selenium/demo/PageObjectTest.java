package com.selenium.demo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PageObjectTest {


    private static WebDriver driver;

    static void needLogin() throws InterruptedException, IOException {
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

    @BeforeAll
    static void beforeAll() throws InterruptedException, IOException {
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

    @Test
     public void customerAddTest() throws InterruptedException {

        //driver.findElement(By.cssSelector("[node-type=addmember]")).click();
        //driver.findElement(By.linkText("添加成员")).click();
        click(By.linkText("添加成员"));
        sendKeys(By.name("username"),"testerererere");
        sendKeys(By.name("acctid"),"testerererere");
        sendKeys(By.name("mobile"),"1300000006");

//        driver.findElement(By.cssSelector("#username")).sendKeys("李四3");
//        driver.findElement(By.cssSelector("#memberAdd_english_name")).sendKeys("lisi3");
//        driver.findElement(By.cssSelector("#memberAdd_acctid")).sendKeys("lisi1003");
        //找到性别这一整模块（整个模块）
        WebElement sex_node = driver.findElement(By.cssSelector(".member_edit_item_Radios"));
        //找到全部的性别选项（单选框全部可选项）
        List<WebElement> list_sex = sex_node.findElements(By.tagName("input"));

        //通过循环选择单选框的某一项,定位到整个单选框模块，然后再从整个模块选择某一项
        for (WebElement sex : list_sex) {
            //boolean flag = sex.isSelected();
            if (sex == list_sex.get(1)) {
                sex.click();

            }
        }

        driver.findElement(By.cssSelector(".ww_telInput_mainNumber")).sendKeys("13000000005");
        driver.findElement(By.cssSelector(".ww_operationBar .js_btn_save")).click();
        Thread.sleep(5000);
        driver.quit();

    }

    @Test
    void search(){}

    @Test
    void departmentSearch(){
        click(By.id("menu_contacts"));
        sendKeys(By.id("memberSearchInput"),"销售部");
        click(By.cssSelector(".ww_icon_AddMember"));
        String content=driver.findElement(By.cssSelector(".js_party_info")).getText();
        assertTrue(content.contains("无任何成员"));

    }

    void click(By by){
        driver.findElement(by).click();
    }

    void sendKeys(By by, String content){
        driver.findElement(by).sendKeys(content);
    }


}
