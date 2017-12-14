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
import org.springframework.web.servlet.ModelAndView;
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
    public String authorize(@RequestParam(value="client_id", required=true, defaultValue="1234567890") String clientId,
                          @RequestParam(value="redirect_uri", required=true, defaultValue="http://localhost/test") String redirectUri,
                          @RequestParam(value="response_type", required=true, defaultValue="response_type") String responseType,
                          @RequestParam(value="state", required=true, defaultValue="state") String state) throws Exception {

        String url = oAuthService.authorize(clientId, redirectUri, responseType, state);
        return  url;
        //String serverName = request.getServerName();
    }

    @RequestMapping(value = "/show", method = RequestMethod.GET)
    public String show(@RequestParam(value="client_id", required=true, defaultValue="1234567890") String clientId,
                       @RequestParam(value="redirect_uri", required=true) String redirectUri,
                       @RequestParam(value="response_type", required=true, defaultValue="response_type") String responseType,
                       @RequestParam(value="state", required=true, defaultValue="code") String state,
                       Model model) {


        //App app = oAuthService.show(clientId);




        //model.addAttribute("client_id",app.getId());
        model.addAttribute("client_id",clientId);
        model.addAttribute("redirect_uri",redirectUri);
        model.addAttribute("response_type",responseType);
        model.addAttribute("state",state);

        //model.addAttribute(app);
        return "oauth-show";
    }


    @RequestMapping(value = "/login")
    public String login(@RequestParam(value="user_name", required=true, defaultValue="1234567890") String userName,
                        @RequestParam(value="user_login", required=true, defaultValue="http://localhost/test") String userLogin,
                        @RequestParam(value="client_id", required=true, defaultValue="1234567890") String clientId,
                        @RequestParam(value="redirect_uri", required=true) String redirectUri,
                        @RequestParam(value="response_type", required=true, defaultValue="response_type") String responseType,
                        @RequestParam(value="state", required=true, defaultValue="code") String state,
                        Model model) throws Exception {


        //App app = oAuthService.show(clientId);

        String url = oAuthService.authorize(clientId, redirectUri, responseType, state);

        String clientUrl = oAuthService.validate(userName, userLogin, redirectUri, state);


        if(userName.equals("1") &&  userLogin.equals("1")) {
            return "redirect:" + redirectUri;
        }
        else
        {

            model.addAttribute("client_id",clientId);
            model.addAttribute("redirect_uri",redirectUri);
            model.addAttribute("response_type",responseType);
            model.addAttribute("state",state);
            model.addAttribute("message","password error");

            //model.addAttribute(app);
            return "oauth-show";

            //return  url;
        }
    }






    @RequestMapping(value = "/token", method = RequestMethod.GET)
    @ResponseBody
    public AuthToken token(@RequestParam(value="client_id", required=true, defaultValue="1234567890") String clientId,
                           @RequestParam(value="client_secret", required=true, defaultValue="http://localhost/test") String redirectUri,
                           @RequestParam(value="redirect_uri", required=true, defaultValue="redirect_uri") String responseType,
                           @RequestParam(value="grant_type", required=true, defaultValue="grant_type") String state,
                           @RequestParam(value="code", required=true, defaultValue="code") String code)
    {


        oAuthService.token(clientId,redirectUri,responseType, state, code);


        AuthToken token = new AuthToken();
        return token;

    }


}


