package com.example.spring.Pro_two.domain;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
@Data
public class MemberDto {


    private int twSeq;
    @NotEmpty
    private String twEmail;
    private String twCellphone;
    private String twJoinDate;
    private String twGender;
    private String twAddress;
    private String twBirth;
    private String twSalt;
    @NotEmpty
    private String twId;
    @NotEmpty
    private String twName;
    @NotEmpty
    private String twPwd;
    @Min(1)@Max(200)
    private int twAge;
    @NotEmpty
    private String twNick;


}
