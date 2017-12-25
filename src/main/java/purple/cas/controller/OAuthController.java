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
import purple.cas.utils.AppUtils;

import javax.servlet.GenericServlet;
import javax.servlet.http.HttpServletRequest;

import static org.springframework.web.util.WebUtils.getRealPath;

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
                       Model model) {

        model.addAttribute("client_id",clientId);
        model.addAttribute("redirect_uri",redirectUri);
        model.addAttribute("response_type",responseType);
        model.addAttribute("state",state);

        model.addAttribute("appRootPath", AppUtils.getPageRootPath(2));


        return "oauth-show";
    }


    @RequestMapping(value = "/show", method = RequestMethod.POST)
    public String login(@RequestParam(value="user_name", required=true) String userName,
                        @RequestParam(value="user_login", required=true) String userLogin,
                        @RequestParam(value="client_id", required=true) String clientId,
                        @RequestParam(value="redirect_uri", required=true) String redirectUri,
                        @RequestParam(value="response_type", required=true) String responseType,
                        @RequestParam(value="state", required=true) String state,
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

            return "oauth-show";


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


