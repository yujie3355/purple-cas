package purple.cas.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import purple.cas.mapper.AppMapper;
import purple.cas.model.App;
import purple.cas.model.AuthToken;
import purple.cas.service.OAuthService;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by yujie on 2017/12/12.
 */

@Controller
@RequestMapping("/oauth")
public class OAuthController {

    @Autowired
    private OAuthService oAuthService;

    @Autowired
    private HttpServletRequest request;

    private final Logger logger = LoggerFactory.getLogger(OAuthController.class);


    @RequestMapping(value = "/authorize", method = RequestMethod.GET)
    @ResponseBody
    public void authorize(@RequestParam(value="client_id", required=true, defaultValue="1234567890") String clientId,
                          @RequestParam(value="redirect_uri", required=true, defaultValue="http://localhost/test") String redirectUri,
                          @RequestParam(value="response_type", required=true, defaultValue="code") String responseType,
                          @RequestParam(value="state", required=true, defaultValue="code") String state)
    {

        String serverName = request.getServerName();
        logger.info(serverName+"1");



        //http://localhost:8080/oauth/authorize?client_id=1&redirect_uri=2&response_type=3&state=4

    }

    @RequestMapping(value = "/show", method = RequestMethod.GET)
    public String show(@RequestParam(value="client_id", required=true, defaultValue="1234567890") String clientId,
                       @RequestParam(value="redirect_uri", required=true, defaultValue="http://localhost/test") String redirectUri,
                       @RequestParam(value="response_type", required=true, defaultValue="code") String responseType,
                       @RequestParam(value="state", required=true, defaultValue="code") String state,
                       Model model) {


        App app = oAuthService.show(clientId);




        model.addAttribute("client_id",app.getId());
        model.addAttribute("redirect_uri",app.getRedirectUri());
        //model.addAttribute(app);
        return "oauth-show";
    }

    @RequestMapping(value = "/token", method = RequestMethod.GET)
    @ResponseBody
    public AuthToken token(@RequestParam(value="client_id", required=true, defaultValue="1234567890") String clientId,
                           @RequestParam(value="client_secret", required=true, defaultValue="http://localhost/test") String redirectUri,
                           @RequestParam(value="redirect_uri", required=true, defaultValue="redirect_uri") String responseType,
                           @RequestParam(value="grant_type", required=true, defaultValue="grant_type") String state,
                           @RequestParam(value="code", required=true, defaultValue="code") String code)
    {

        AuthToken token = new AuthToken();
        return token;

    }


}


