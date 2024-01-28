package method3;

import main.UserBMI;

public final class UserBMIHolder {
    private UserBMI user;
    private final static UserBMIHolder INSTANCE = new UserBMIHolder();

    public static UserBMIHolder getInstance(){
        return INSTANCE;
    }

    public void setUserBMI(UserBMI user){
        this.user = user;
    }

    public UserBMI getUserBMI(){
        return user;
    }
}
