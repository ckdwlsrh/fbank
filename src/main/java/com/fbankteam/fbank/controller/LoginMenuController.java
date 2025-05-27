package com.fbankteam.fbank.controller;

import com.fbankteam.fbank.util.Input;
import com.fbankteam.fbank.util.Print;

public class LoginMenuController implements Controller{

    private String[] menus = {"로그인", "회원가입", "종료"};
    @Override
    public Controller run() {
        Print.printTitle("인터넷 뱅킹 콘솔 시스템");
        while(true) {
            Print.printMenu(menus);
            String command = Input.scanner.nextLine();
            switch (command) {
                case "1":
                    return new LoginController();
                case "2":
                    return new SignupController();
                case "3":
                    return null;
            }
            System.out.println("메뉴와 번호가 일치하지 않습니다.");
        }
    }

}
