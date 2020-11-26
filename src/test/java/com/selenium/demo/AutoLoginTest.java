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
import org.openqa.selenium.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;


/**
 * 常用接口
 * from selenium import webdriver
 * from selenium.webdriver import ActionChains  #这个是模仿鼠标动作的
 * from selenium.webdriver.common.by import By #这个是设置查找方式的By.ID,By.CSS_SELECTOR
 * from selenium.webdriver.common.keys import Keys #这个是模拟键盘按键操作的
 * from selenium.webdriver.support import expected_conditions  #这个是标注状态的
 * from selenium.webdriver.support.wait import WebDriverWait #这个是等待页面加载某些元素
 * <p>
 * 选择器
 * 1、find_element_by_id        按照id 查找
 * 2、find_element_by_link_text　　按照里面的文本查找，比如查找<h1>好呀</h1>find_element_by_link_text("好呀")
 * 3、find_element_by_partial_link_text   按照文本的部分模糊查找，比如查找<h1>好呀</h1>find_element_by_link_text("好")
 * 4、find_element_by_tag_name　　　　按照标签名
 * 5、find_element_by_class_name　　　　按照类名
 * 6、find_element_by_name　　　　　　　　按照name属性查找
 * 7、find_element_by_css_selector　　　　css选择器的方式查找
 * 8、find_element_by_xpath/find_elements_by_xpath　　　　　　　比较神奇的查找方式
 * <p>
 * 9、所有方式均可以用find_element(By.ID,"lala")这种形式替代
 * <p>
 * p.s. 一些方法取到的是元素集合，用索引或者for循环取单独的值。
 */


public class AutoLoginTest {
    //private static WebDriver driver;
    @Test
    public void openWebTest() {

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
        mapper.writeValue(new File("cookies.yaml"), cookies);
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
        TypeReference typeReference = new TypeReference<List<HashMap<String, Object>>>() {
        };
        List<HashMap<String, Object>> cookies = mapper.readValue(new File("cookies.yaml"), typeReference);

        //System.out.print(cookies);
        try {
            cookies.forEach(cookieMap -> driver.manage().addCookie(new Cookie(cookieMap.get("name").toString(), cookieMap.get("value").toString())));
        } catch (Exception e) {
            System.err.println("getMessage():" + e.getMessage());
            e.printStackTrace();
        }
        driver.navigate().refresh();
        Thread.sleep(15000);
        driver.quit();
    }

    @Test
    public void autoSaveCustomerInfoTest() throws InterruptedException, IOException {
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.get("https://work.weixin.qq.com/wework_admin/frame");
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        TypeReference typeReference = new TypeReference<List<HashMap<String, Object>>>() {
        };
        List<HashMap<String, Object>> cookies = mapper.readValue(new File("cookies.yaml"), typeReference);

        //System.out.print(cookies);
        try {
            cookies.forEach(cookieMap -> driver.manage().addCookie(new Cookie(cookieMap.get("name").toString(), cookieMap.get("value").toString())));
            driver.navigate().refresh();
            driver.findElement(By.cssSelector(".index_service_cnt_itemWrap")).click();
            driver.findElement(By.cssSelector("#username")).sendKeys("李四2");
            driver.findElement(By.cssSelector("#memberAdd_english_name")).sendKeys("lisi2");
            driver.findElement(By.cssSelector("#memberAdd_acctid")).sendKeys("lisi1002");

            //ArrayList<WebElement> search_type = (ArrayList<WebElement>) driver.findElements(By.cssSelector(".ww_label_Middle"));

            //
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

//                for (int i = 0; i < list_sex.size(); i++) {
//                    WebElement sex = list_sex.get(i);
//                    //boolean flag = sex.isSelected();
//                    if (sex == list_sex.get(1)) {
//                        sex.click();
//
//                    }
            }

            driver.findElement(By.cssSelector(".ww_telInput_mainNumber")).sendKeys("13000000003");
            driver.findElement(By.cssSelector(".ww_operationBar .js_btn_save")).click();
            Thread.sleep(5000);
            driver.quit();

        } catch (Exception e) {
            System.err.println("getMessage():" + e.getMessage());
            e.printStackTrace();
        }

//            for(WebElement ele : search_type){
//                ele.click();
//                Thread.sleep(5000);
//            }


    }

}
