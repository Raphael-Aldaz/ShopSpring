package fr.fms.controller;

import fr.fms.Repository.UserRepository;
import fr.fms.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CustomFunc customFunc;

    public UserController() {
    }

    @GetMapping(value = "/login")
    public String index(){

        return "redirect:/index";
    }
    @PostMapping(value = "/login")
    public String login(Model model , String username, String password, HttpSession session){
        User userConnected = userRepository.findByUsernameAndPassword(username, password);
        if(userConnected != null){
            customFunc.setUserConnected(true);
            model.addAttribute("userConnected", userConnected);
            session.setAttribute("userId", userConnected.getId());
        } else {
            model.addAttribute("error", "Login ou mot de passe incorrect");
            return "redirect:/index";
        }
         System.out.println(userConnected);
        return "redirect:/index";
    }
    @GetMapping(value = "/logout")
    public String logout(Model model){
        customFunc.setUserConnected(false);
        model.addAttribute("userConnected", customFunc.getIsUserConnected());
        return "redirect:/index";
    }

}
