package purple.cas.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import purple.cas.mapper.AppMapper;
import purple.cas.model.App;

/**
 * Created by yujie on 2017/12/12.
 */

@Controller
@RequestMapping("/oauth")
public class OAuthController {

    @Autowired
    private AppMapper appMapper;


    @RequestMapping(value = "/show", method = RequestMethod.GET)
    public String show(@RequestParam(value="client_id", required=true, defaultValue="1234567890") String clientId,
                       @RequestParam(value="redirect_uri", required=true, defaultValue="http://localhost/test") String redirectUri,
                       @RequestParam(value="response_type", required=true, defaultValue="code") String responseType,
                       @RequestParam(value="state", required=true, defaultValue="code") String state,
                       Model model) {


        App app = appMapper.queryById(clientId);




        model.addAttribute("client_id",app.getId());
        model.addAttribute("redirect_uri",app.getRedirectUri());
        return "oauth-show";
    }
}


//client_id=fd0f308303&redirect_uri=http%3A%2F%2Ftsc.nbciq.gov.cn%2Fmbf%2Fauth%2Fcallback&response_type=code&state=home


