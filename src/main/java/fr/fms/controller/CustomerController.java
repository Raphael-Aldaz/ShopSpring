package fr.fms.controller;

import fr.fms.Repository.CustomerRepository;
import fr.fms.Repository.UserRepository;
import fr.fms.entities.Customer;
import fr.fms.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class CustomerController {
    public CustomerController() {
    };
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private UserRepository userRepository;


    @GetMapping  (value = "/customer")
        public String submitCustomer(Model model, HttpSession session) {
        Long userId =(Long) session.getAttribute("userId");
        System.out.println(userId + "userId");
        model.addAttribute("customer", new Customer());
            return "customer";
        }
    @PostMapping(value = "/customer")
        public String submitCustomer(@Valid Customer customer,Long idUser,  BindingResult bindingResult) {
        User user = userRepository.findById(idUser).get();
        System.out.println(user);

        if(bindingResult.hasErrors()) return "customer";

        return "customer";
        }
}
