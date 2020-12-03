package com.selenium.demo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ContactPage {

    private WebDriver driver;

    public ContactPage(WebDriver driver) {
        this.driver=driver;

    }

    public ContactPage addMember(String username, String acctid, String mobile){
        return this;
    }

    public ContactPage searchDepart(String departName){
        sendKeys(By.id("memberSearchInput"),"销售部");
        click(By.cssSelector(".ww_icon_AddMember"));
        String content=driver.findElement(By.cssSelector(".js_party_info")).getText();
        return this;
    }

    public String getPartInfo(){
        String content=driver.findElement(By.cssSelector(".js_party_info")).getText();
        System.out.println(content);
        return content;
    }

    void click(By by){
        driver.findElement(by).click();
    }

    void sendKeys(By by, String content){
        driver.findElement(by).sendKeys(content);
    }


}
