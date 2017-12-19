package purple.cas.model;

import java.time.LocalDateTime;


public class AuthCode {
    private String authCode;

    private String clientId;

    private String userName;

    private String implType;

    private LocalDateTime createTime;

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode == null ? null : authCode.trim();
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId == null ? null : clientId.trim();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getImplType() {
        return implType;
    }

    public void setImplType(String implType) {
        this.implType = implType == null ? null : implType.trim();
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
}