package shan_shine.informus;

/**
 * Created by Shanakay on 5/16/2015.
 */
public class LoginLog {
    String username;
    String logStat;

    public LoginLog(String username, String password) {
        super();
        this.username = username;
        this.logStat = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.logStat;
    }

    public void setPassword(String password) {
        this.logStat = password;
    }
}
