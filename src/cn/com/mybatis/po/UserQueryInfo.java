package cn.com.mybatis.po;

public class UserQueryInfo {
    //包装需要的查询文件
    //用户查询条件
    private UserInstance userInstance;

    public UserInstance getUserInstance() {
        return userInstance;
    }

    public void setUserInstance(UserInstance userInstance) {
        this.userInstance = userInstance;
    }
}
