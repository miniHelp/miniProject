package onlineModel;

//存放登入的资讯，使用者物件，是否成功
public class LoginVO {

    private boolean isLoginIdExist; //账号是否存在
    private boolean isLoginSuccess; //登入是否成功
    private UserVO loginUser;
    private String loginMessage; //当前的登入讯息

    public boolean isLoginSuccess() {
        return isLoginSuccess;
    }

    public void setLoginSuccess(boolean loginSuccess) {
        isLoginSuccess = loginSuccess;
    }

    public String getLoginMessage() {
        return loginMessage;
    }

    public void setLoginMessage(String loginMessage) {
        this.loginMessage = loginMessage;
    }

    public boolean isLoginIdExist() {
        return isLoginIdExist;
    }

    public void setLoginIdExist(boolean loginIdExist) {
        isLoginIdExist = loginIdExist;
    }

    public UserVO getLoginUser() {
        return loginUser;
    }

    public void setLoginUser(UserVO loginUser) {
        this.loginUser = loginUser;
    }
}
