package com.fbankteam.fbank.util;

import java.util.List;

public class Print {
    public static void printTitle(String title) {
        System.out.println("++++++++++++++++++++" + title + "++++++++++++++++++++");
        System.out.println();
    }
    public static void printMenu(String[] menus) {
        for(int i = 0; i < menus.length; i ++) {
            System.out.print(i + 1);
            System.out.println("." + menus[i]);
        }
        System.out.print("번호를 입력해주세요 :");
    }
}
