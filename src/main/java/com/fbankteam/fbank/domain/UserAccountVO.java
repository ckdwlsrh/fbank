package com.fbankteam.fbank.domain;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class UserAccountVO {
    private Integer id;
    private Integer userInfoId;
    private String loginId;
    private String loginPassword;
}
