package purple.cas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import purple.cas.constant.ResponseType;
import purple.cas.mapper.AppMapper;
import purple.cas.mapper.UserMapper;
import purple.cas.model.App;

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


    public App show(String clientId)
    {
        return appMapper.getById(clientId);
    }


    public String authorize(String clientId, String redirectUri, String responseType, String state) throws Exception {

        App app = appMapper.getById(clientId);
        if(null == app) throw new Exception("OAuth.authorize.Client is missing.Detail-" + clientId);

        if(responseType.equals(ResponseType.DEFAULT)) {

            return String.format(Locale.ENGLISH,
                    "redirect:/oauth/show?client_id=%s&redirect_uri=%s&response_type=%s&state=%s",
                    clientId, redirectUri, responseType, state);

        }
        else if(responseType.equals(ResponseType.NBEPORT))
        {
            return "";

        }
        else {
            throw new Exception("OAuth.authorize.ResponseType is not support yet.Detail-" + responseType);
        }
    }


    public String validate(String userName, String userPassword, String redirectUri, String state)
    {

        String code = UUID.randomUUID().toString().replace("-", "");
        return String.format(Locale.ENGLISH,
                "redirect:%s?code=%s&state=%s",
                redirectUri, code, state);

    }





    public void token(String clientId, String redirectUri, String responseType, String state, String code) {



    }
}
