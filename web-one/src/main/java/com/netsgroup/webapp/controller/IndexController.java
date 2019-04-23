package com.netsgroup.webapp.controller;

import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.netsgroup.api.domain.Customer;
import com.netsgroup.webapp.client.RtsfApiTemplate;
import com.netsgroup.webapp.client.RtsfApiTemplate2;
import com.netsgroup.webapp.client.ServiceOneTemplate;
import com.netsgroup.webapp.client.ServiceTwoTemplate;

@Controller
public class IndexController {

  protected static Logger logger = LoggerFactory.getLogger(IndexController.class);

  @Autowired
  ServiceOneTemplate serviceOneTemplate;

  @Autowired
  ServiceTwoTemplate serviceTwoTemplate;

  @Autowired
  RtsfApiTemplate apiTemplate;

  @Autowired
  RtsfApiTemplate2 apiTemplate2;

  @Autowired
  OAuth2RestTemplate template;

  @Autowired
  private TokenStore tokenStore;

  @PreAuthorize("#oauth2.hasScope('FDSC/menu')")
  @GetMapping("/v2/test")
  @ResponseBody
  public String index (OAuth2Authentication auth, HttpServletRequest request) {
    Enumeration<String> hh = request.getHeaderNames();
    while (hh.hasMoreElements()) {
      String string = (String) hh.nextElement();
      System.out.println(hh + " - " +request.getHeader(string));
    }
    return "ok";
  }

  //  @PreAuthorize("hasAuthority('ACCESS')")
  //  @GetMapping("/welcome")
  //  public String welcome(OAuth2Authentication auth, HttpServletResponse response, Model model) {
  //    System.out.println(auth.getName());
  //    OAuth2AccessToken token = (OAuth2AccessToken) auth.getDetails();
  //    model.addAttribute("at", token.toString());
  //    response.addHeader("X-AUTH", token.toString());
  //    return "welcome";
  //  }

  //@PreAuthorize("isAuthenticated() && hasAuthority('NEXI#FDSC#P99')")
  @GetMapping("/welcome")
  public String welcome(HttpServletResponse response, 
      OAuth2Authentication authentication,
      Model model) {
    OAuth2AccessToken token = (OAuth2AccessToken) authentication.getDetails();
    model.addAttribute("at", token.toString());
    response.addHeader("X-AUTH", token.toString());

    return "welcome";
  }

  //    @GetMapping("/index")
  //    public String index(Model model) {
  //        logger.info("retrieving information from webservice 2");
  //        model.addAttribute("s2Items", serviceTwoTemplate.items());
  //        logger.info("retrieving information from webservice 1");
  //        model.addAttribute("s1Items", serviceOneTemplate.items());
  //        return "index";
  //    }
  //	@GetMapping("/login")
  //    public String indexApi(Model model) throws UnsupportedEncodingException, DecoderException, Exception {
  //        logger.info("retrieving information from rtsf");
  //        Customer uc = apiTemplate.getUserCustomer(Hex.encodeHexString("m.cenci".getBytes()));
  //        model.addAttribute("customer", uc);
  //        model.addAttribute("private", apiTemplate2.getPrivate("FTFS_WEB"));
  //        return "index";
  //    }

  @GetMapping("/home")
  public String home(Model model) throws UnsupportedEncodingException, DecoderException, Exception {
    logger.info("retrieving information from rtsf");
    Customer uc = apiTemplate.getUserCustomer(Hex.encodeHexString("m.cenci".getBytes()));
    model.addAttribute("customer", uc);
    model.addAttribute("private", apiTemplate2.getPrivate("FTFS_WEB"));
    return "index";
  }


  @GetMapping("/login")
  public String token(Authentication authentication, 
      HttpServletRequest request,
      HttpServletResponse response,
      Model model,
      RedirectAttributes redirectAttributes) {
    if (authentication == null) {
      OAuth2AccessToken token = template.getAccessToken();
      OAuth2Authentication auth = tokenStore.readAuthentication(token);
      auth.setDetails(token);
      SecurityContext sc = SecurityContextHolder.getContext(); 
      sc.setAuthentication(auth);
    }	    
    return "redirect:welcome";
  }
}
