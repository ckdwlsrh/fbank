package com.fbankteam.fbank.app;

import com.fbankteam.fbank.controller.Controller;
import com.fbankteam.fbank.controller.LoginMenuController;
import com.fbankteam.fbank.util.JDBCUtil;

public class Main {
    public static void main(String[] args) {
        Controller controller = new LoginMenuController();

        //컨트롤러 교환
        while (controller != null) {
            controller = controller.run();
        }

        System.out.println("프로그램 종료");
        JDBCUtil.close();

    }
}
