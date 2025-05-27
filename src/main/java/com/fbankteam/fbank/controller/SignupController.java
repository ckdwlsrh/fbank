package com.fbankteam.fbank.controller;

import com.fbankteam.fbank.service.AccountService;
import com.fbankteam.fbank.service.UserService;
import com.fbankteam.fbank.util.Input;
import com.fbankteam.fbank.util.Print;

public class SignupController implements Controller{

    private final UserService userService = new UserService();
    private final AccountService accountService = new AccountService();

    @Override
    public Controller run() {
        Print.printTitle("회원가입 화면");
        System.out.print("본 은행의 계좌를 보유하고 있으신가요? (y/N) : ");
        String input = Input.scanner.nextLine();
        if(input.toUpperCase().equals("Y")) {
            System.out.println("은행의 계좌 번호를 입력해주세요(-붙여서) : ");
            input = Input.scanner.nextLine();
            //계좌 확인
            //TODO: AccountService validation
            //if()
            System.out.println("계좌 인증을 위해 개인정를 입력해주세요.");
            System.out.print("이름 : ");
            String name = Input.scanner.nextLine();
            System.out.print("주민등록번호 : ");
            String rrn = Input.scanner.nextLine();
            System.out.print("전화번호 : ");
            String phone = Input.scanner.nextLine();
            //본인인증 확인
            //TODO: UserService validation
            //if()


        }
        else {
            // 사용자 등록

        }
        return null;
    }

    private void addUser() {
        System.out.println("이름");
    }
}
