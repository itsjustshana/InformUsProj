package shan_shine.informus;
/**
 * Created by Shanakay on 3/19/2015.
 */
public class LoginItem {

    private String username;
    private String password;


    public LoginItem(String username, String password) {
        super();
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}

