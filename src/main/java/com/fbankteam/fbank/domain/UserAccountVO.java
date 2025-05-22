package com.fbankteam.fbank.domain;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAccountVO {
    private int id;
    private int userInfoId;
    private String loginId;
    private String loginPassword;
}
