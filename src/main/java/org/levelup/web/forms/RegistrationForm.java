package org.levelup.web.forms;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class RegistrationForm {
    @Size(min = 4, max = 25)
    @Pattern(regexp = "[a-zA-Z-_.0-9]*",
            message = "You can use only letters, digits, underscore, minus sign and dots")
    private String login;

    @Size(min = 3, max = 25)
   /* @Pattern(regexp = "(?=.*[0-9]])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%!_-]).",
            message = "Password must contains digits from 0-9, lowercase and uppercase characters, special symbols in the list \"@#$%!_-\"")*/
    private String password;

    public RegistrationForm(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
