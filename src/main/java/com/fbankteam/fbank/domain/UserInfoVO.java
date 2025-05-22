package com.fbankteam.fbank.domain;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class UserInfoVO {
    private int id;
    private String userName;
    private String rrn;
    private String phoneNumber;
}

