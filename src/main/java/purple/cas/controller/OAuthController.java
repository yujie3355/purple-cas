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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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




    private final Logger logger = LoggerFactory.getLogger(OAuthController.class);


    @RequestMapping(value = "/authorize", method = RequestMethod.GET)
    public String authorize(@RequestParam(value="client_id", required=true) String clientId,
                                  @RequestParam(value="redirect_uri", required=true) String redirectUri,
                                  @RequestParam(value="response_type", required=true) String responseType,
                                  @RequestParam(value="state", required=true) String state,
                                  RedirectAttributes redirectAttributes) throws Exception {


        oAuthService.authorize(clientId, redirectUri, responseType, state);

        redirectAttributes.addAttribute("client_id",clientId);
        redirectAttributes.addAttribute("redirect_uri",redirectUri);
        redirectAttributes.addAttribute("response_type",responseType);
        redirectAttributes.addAttribute("state",state);

        return "redirect:/oauth/show";


    }

    @RequestMapping(value = "/show", method = RequestMethod.GET)
    public String show(@RequestParam(value="client_id", required=true) String clientId,
                       @RequestParam(value="redirect_uri", required=true) String redirectUri,
                       @RequestParam(value="response_type", required=true) String responseType,
                       @RequestParam(value="state", required=true, defaultValue="code") String state,
                       @RequestParam(value="warn_info", required=false, defaultValue = "0") String warnInfo,
                       Model model) {

        model.addAttribute("client_id", clientId);
        model.addAttribute("redirect_uri", redirectUri);
        model.addAttribute("response_type", responseType);
        model.addAttribute("state", state);
        model.addAttribute("warn_info", warnInfo);

        return "oauth-show";
    }


    @RequestMapping(value = "/show", method = RequestMethod.POST)
    public String login(@RequestParam(value="user_name", required=true) String userName,
                        @RequestParam(value="user_login", required=true) String userLogin,
                        @RequestParam(value="client_id", required=true) String clientId,
                        @RequestParam(value="redirect_uri", required=true) String redirectUri,
                        @RequestParam(value="response_type", required=true) String responseType,
                        @RequestParam(value="state", required=true) String state,
                        @RequestParam(value="warn_info", required=false, defaultValue = "0") String warnInfo,
                        Model model) throws Exception {

        oAuthService.authorize(clientId, redirectUri, responseType, state);
        if(oAuthService.validate(userName, userLogin)) {

            String code = oAuthService.createCode(clientId, userName);

            return "redirect:" + redirectUri + "?code=" + code + "&state=" + state;
        }
        else
        {
            model.addAttribute("client_id",clientId);
            model.addAttribute("redirect_uri",redirectUri);
            model.addAttribute("response_type",responseType);
            model.addAttribute("state",state);
            model.addAttribute("user_name",userName);
            model.addAttribute("warn_info","1");
            return "oauth-show";
        }
    }

    @RequestMapping(value = "/token", method = RequestMethod.GET)
    @ResponseBody
    public AuthToken token(@RequestParam(value="client_id", required=true) String clientId,
                           @RequestParam(value="client_secret", required=true) String clientSecret,
                           @RequestParam(value="redirect_uri", required=true) String redirectUri,
                           @RequestParam(value="grant_type", required=true) String grantType,
                           @RequestParam(value="code", required=true) String code) throws Exception {

        AuthToken authToken = oAuthService.token(clientId,clientSecret, code);
        return authToken;

    }

}


