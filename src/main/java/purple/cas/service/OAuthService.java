package purple.cas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import purple.cas.constant.ResponseType;
import purple.cas.mapper.AppMapper;
import purple.cas.mapper.AuthCodeMapper;
import purple.cas.mapper.UserMapper;
import purple.cas.model.App;
import purple.cas.model.AuthCode;
import purple.cas.model.User;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Locale;
import java.util.UUID;

/**
 * Created by yujie on 2017/12/13.
 */

@Service
public class OAuthService {


    @Autowired
    AppMapper appMapper;
    //test

    @Autowired
    UserMapper userMapper;


    @Autowired
    AuthCodeMapper authCodeMapper;










    public App show(String clientId)
    {
        return appMapper.selectByPrimaryKey(clientId);
    }



    public String redirectToShow(String clientId, String redirectUri, String responseType, String state)
    {
        return String.format(Locale.ENGLISH,
                "redirect:/oauth/show?client_id=%s&redirect_uri=%s&response_type=%s&state=%s",
                clientId, redirectUri, responseType, state);

    }





    public boolean authorize(String clientId, String redirectUri, String responseType, String state) throws Exception {

        App app = appMapper.selectByPrimaryKey(clientId);
        if(null == app)
            throw new Exception("OAuth.authorize.Client is missing.Detail-" + clientId);

        if(app.getRedirectUri() == null || app.getRedirectUri().isEmpty())
            throw new Exception("OAuth.authorize.RedirectUri's configuration is null.Detail-" + clientId);

        String[] redirectUris = app.getRedirectUri().split(",");
        boolean isRedirectUriInConfiguration = Arrays.asList(redirectUris).contains(redirectUri);
        if(!isRedirectUriInConfiguration)
            throw new Exception("OAuth.authorize.RedirectUri is not in configuration.Detail-" + redirectUri);


        if(responseType.equals(ResponseType.DEFAULT)) {
            return true;

        }
        else if(responseType.equals(ResponseType.NBEPORT))
        {
            throw new Exception("OAuth.authorize.ResponseType is not support yet.Detail-" + responseType);
        }
        else {
            throw new Exception("OAuth.authorize.ResponseType is not support yet.Detail-" + responseType);
        }



    }


    public boolean validate(String userCode, String userPassword) throws Exception {

        User user = userMapper.selectByUserCode(userCode);

        if(null == user )
            return false;

        if(userPassword.equals(user.getUserPwd())) {
            return true;
        }
        else {
            return false;
        }
    }


    public String createCode(String clientId, String userName)
    {
        String code = UUID.randomUUID().toString().replace("-", "").toUpperCase();
        AuthCode authCode = new AuthCode();
        authCode.setAuthCode(code);
        authCode.setClientId(clientId);
        authCode.setCreateTime(LocalDateTime.now());
        authCode.setImplType("oraclebase");
        authCode.setUserName(userName);
        authCodeMapper.insert(authCode);

        return code;

    }





    public void token(String clientId, String redirectUri, String responseType, String state, String code) {



    }
}
