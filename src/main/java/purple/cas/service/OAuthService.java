package purple.cas.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import purple.cas.mapper.AppMapper;
import purple.cas.model.App;

/**
 * Created by yujie on 2017/12/13.
 */

@Service
public class OAuthService {


    @Autowired
    AppMapper appMapper;
    //test




    public App show(String clientId)
    {
        return appMapper.getById(clientId);
    }


}
