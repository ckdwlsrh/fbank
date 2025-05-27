package com.fbankteam.fbank.controller;

import com.fbankteam.fbank.service.UserService;
import com.fbankteam.fbank.util.Input;
import com.fbankteam.fbank.util.Print;

public class LoginController implements Controller{

    private final UserService userService = new UserService();

    @Override
    public Controller run() {
        Print.printTitle("로그인 화면");
        while(true) {
            System.out.print("아이디 : ");
            String loginId = Input.scanner.nextLine();
            System.out.print("비밀 번호 : ");
            String password = Input.scanner.nextLine();

            //TODO: 로그인 서비스 구현하기
//            if (userService. ()){
//                return new MainController();
//            }
//            else {
//                System.out.println("아이디나 비밀번호가 일치하지 않습니다.");
//            }
        }

    }
}
