package com.woon7713.to_do_list_with_dto.controller;


import com.woon7713.to_do_list_with_dto.dto.SignupDto;
import com.woon7713.to_do_list_with_dto.model.User;
import com.woon7713.to_do_list_with_dto.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class SignUpController {

    private final UserRepository userRepository;

    @GetMapping("/signup")
    public String showSignup(Model model) {
        model.addAttribute("signupDto", new SignupDto());

        return "signup";
    }

    @PostMapping("/signup")
    public String doSignup(@Valid @ModelAttribute SignupDto signupDto,
                           BindingResult bidingResult,
                           Model model) {

        if (bidingResult.hasErrors()) {
            return "signup";
        }

        //중복가입 여부 체크

        if (userRepository.findByUsername(signupDto.getUsername()) != null) {
            model.addAttribute("error", "이미 사용 중인 아이디입니다.");

            return "signup";
        }


        User user = User.builder()
                .username(signupDto.getUsername())
                .password(signupDto.getPassword())
                .build();
        userRepository.save(user);

        return "redirect:/login?registered";

    }

}
