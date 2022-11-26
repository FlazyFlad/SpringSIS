package com.example.springsis.Controller;

import com.example.springsis.Entity.Book;
import com.example.springsis.Entity.Users;
import com.example.springsis.Repository.BookRepository;
import com.example.springsis.Service.BookServiceInterface;
import com.example.springsis.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class MainController {

    @Autowired
    BookServiceInterface bookService;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    BookRepository SecurityConfig;


    @Autowired
    private UserService userService;

    @GetMapping("/books")
    public String books(Model model){
        model.addAttribute("currentUser", getUserData());
        model.addAttribute("books", bookService.FindAllBooks());
        return "books";
    }

    @GetMapping("/navbar")
    public String nav(Model model) {
        model.addAttribute("currentUser", getUserData());
        return "navbar";
    }


    @GetMapping("/books/new")
    public String addBooksForm(Model model){
        model.addAttribute("currentUser", getUserData());
        Book book = new Book();
        model.addAttribute("book", book);
        return "add_book";
    }

    @PostMapping(value = "/books")
    public String saveBook(@ModelAttribute("book") Book book){
        bookService.saveBook(book);
        return "redirect:/books";
    }


    @GetMapping("/books/update/{id}")
    public String updateBookForm(Model model, @PathVariable("id") Long id){
        model.addAttribute("currentUser", getUserData());
        Book book1 = bookService.findById(id);
        model.addAttribute("book", book1);
        return "update";
    }

    @GetMapping("/books/delete/{id}")
    public String deleteBook(@PathVariable("id") Long id) {
        bookService.deleteBook(id);
        return "redirect:/books";
    }

    @PostMapping("/books/update/{id}")
    public String updateBook(@PathVariable("id") Long id, @ModelAttribute("book") Book book1){
        Book Book2 = bookService.findById(id);
        Book2 = book1;
        bookService.saveBook(Book2);
        return "redirect:/books";
    }

    private boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || AnonymousAuthenticationToken.class.
                isAssignableFrom(authentication.getClass())) {
            return false;
        }
        return authentication.isAuthenticated();
    }

    @GetMapping("/login")
    public String getUserLoginPage(Model model) {
        if (isAuthenticated()) {
            model.addAttribute("currentUser", getUserData());
            return "redirect:/books";
        }
        return "login";
    }

    @GetMapping("/index")
    public String log(){
        return "redirect:/books";
    }

    @GetMapping("/logout")
    public String logout() { return "login"; }

    @GetMapping(value = "/register")
    public String register(Model model){
        model.addAttribute("currentUser", getUserData());

        return "register";
    }

    @PostMapping(value = "/register")
    public String registerForm(@RequestParam(name = "user_email")String email,
                               @RequestParam(name = "user_password")String password,
                               @RequestParam(name = "re_user_password")String rePassword,
                               @RequestParam(name = "user_fullName")String fullName){

        if(password.equals(rePassword)){

            Users newUser = new Users();
            newUser.setFullName(fullName);
            newUser.setPassword(password);
            newUser.setEmail(email);


            if(userService.createUser(newUser)!=null){
                return "redirect:/register?success";
            }

        }

        return "redirect:/register?error";
    }


    private Users getUserData(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(!(authentication instanceof AnonymousAuthenticationToken)){
            User secUser = (User)authentication.getPrincipal();
            Users myUser = userService.getUserByEmail(secUser.getUsername());
            return myUser;
        }
        return null;
    }

}
