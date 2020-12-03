package com.selenium.demo;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ContactPOTest {

    @Test
    void teestAddMember() throws IOException, InterruptedException {
        //打开页面
        //复用Session登录
        MainPage mainPage = new MainPage();
        //跳转页面
        //部门搜索
        ContactPage contactPage=mainPage.contact();

        //addMember("tester","tester","13900000001");
        contactPage.searchDepart("销售部");
        String content = contactPage.getPartInfo();
        assertTrue(content.contains("无任何成员"));
       // assert  contactPage.search("tester").getInfo()
    }


}
