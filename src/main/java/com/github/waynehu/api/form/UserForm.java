package com.github.waynehu.api.form;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserForm {
    private String username;
    private String password;
    private String email;
}
