/**
 * Info about this package doing something for package-info.java fi1le.
 */

package com.example.springsis.Controller;

import com.example.springsis.Entity.Book;
import com.example.springsis.Entity.Image;
import com.example.springsis.Entity.Users;
import com.example.springsis.Repository.BookRepository;
import com.example.springsis.Repository.ImageRepository;
import com.example.springsis.Service.BookServiceInterface;
import com.example.springsis.Service.UserService;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


/**
 * smth.
 */
@Controller
public class MainController {


  @Autowired
    private BookServiceInterface bookService;
  @Autowired
    private BookRepository bookRepository;

  @Autowired
    private BookRepository securityConfig;

  @Autowired
    private ImageRepository imageRepository;


  @Autowired
    private UserService userService;

  /**
   * smth.
   */
  @GetMapping("/books")
    public String books(Model model) {
    model.addAttribute("currentUser", getUserData());
    model.addAttribute("books", bookService.FindAllBooks());
    return "books";
  }

  /**
   * smth.
   */
  @GetMapping("/navbar")
    public String nav(Model model) {
    model.addAttribute("currentUser", getUserData());
    return "navbar";
  }

  /**
   * smth.
   */
  @GetMapping("/books/new")
    public String addBooksForm(Model model) {
    model.addAttribute("currentUser", getUserData());
    Book book = new Book();
    model.addAttribute("book", book);
    return "add_book";
  }

  /**
   * smth.
   */
  @PostMapping(value = "/books")
    public String saveBook(@ModelAttribute("book") Book book,
      @RequestParam("file") MultipartFile file) throws IOException {
    bookService.saveBook(book, file);
    return "redirect:/books";
  }

  /**
   * smth.
   */
  @GetMapping("/books/update/{id}")
    public String updateBookForm(Model model, @PathVariable("id") Long id) {
    model.addAttribute("currentUser", getUserData());
    Book book1 = bookService.findById(id);
    model.addAttribute("book1", book1);
    return "update";
  }

  /**
   * smth.
   */
  @GetMapping("/books/delete/{id}")
    public String deleteBook(@PathVariable("id") Long id) {
    bookService.deleteBook(id);
    return "redirect:/books";
  }

  /**
   * smth.
   */
  @PostMapping("/books/update/{id}")
    public String updateBook(@PathVariable("id") Long id,
      @ModelAttribute("book1") Book book1) {
    Book book = bookRepository.findById(id).get();
    book1.setPreviewImageId(book.getPreviewImageId());
    bookRepository.save(book1);
    return "redirect:/books";
  }

  /**
   * smth.
   */
  private boolean isAuthenticated() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication == null
            ||
        AnonymousAuthenticationToken.class.isAssignableFrom(authentication.getClass())) {
      return false;
    }
    return authentication.isAuthenticated();
  }

  /**
   * smth.
   */
  @GetMapping("/login")
    public String getUserLoginPage(Model model) {
    if (isAuthenticated()) {
      model.addAttribute("currentUser", getUserData());
      return "redirect:/books";
    }
    return "login";
  }

  /**
   * smth.
   */
  @GetMapping("/")
    public String home() {
    return "redirect:/books";
  }

  /**
   * smth.
   */
  @GetMapping("/index")
    public String log() {
    return "redirect:/books";
  }

  /**
   * smth.
   */
  @GetMapping("/static/sounds/8e6d249.mp3")
    public String sounds() {
    return "redirect:/books";
  }

  /**
   * smth.
   */
  @GetMapping("/mindbox-services-worker.js")
    public String mindbox() {
    return "redirect:/books";
  }

  /**
   * smth.
   */
  @GetMapping("/logout")
    public String logout() {
    return "login";
  }

  /**
   * smth.
   */
  @GetMapping(value = "/register")
    public String register(Model model) {
    model.addAttribute("currentUser", getUserData());

    return "register";
  }

  /**
   * smth.
   */
  @PostMapping(value = "/register")
    public String registerForm(@RequestParam(name = "user_email") final String email,
                               @RequestParam(name = "user_password") final String password,
                               @RequestParam(name = "re_user_password") final String rePassword,
                               @RequestParam(name = "user_fullName") final String fullName) {
    if (password.equals(rePassword)) {
      Users newUser = new Users();
      newUser.setFullName(fullName);
      newUser.setPassword(password);
      newUser.setEmail(email);
      if (userService.createUser(newUser) != null) {
        return "redirect:/register?success";
      }

    }
    return "redirect:/register?error";
  }

  /**
   * smth.
   */
  @GetMapping("/images/{id}")
    private ResponseEntity<?> getImageById(@PathVariable Long id) {
    Image image = imageRepository.findById(id).orElse(null);
    return ResponseEntity.ok().header("fileName", image.getOriginalFileName())
    .contentType(MediaType.valueOf(image.getContentType())).contentLength(image.getSize())
    .body(new InputStreamResource(new ByteArrayInputStream(image.getBytes())));
  }

  /**
   * smth.
   * */
  private Users getUserData() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (!(authentication instanceof AnonymousAuthenticationToken)) {
      User secUser = (User) authentication.getPrincipal();
      Users myUser = userService.getUserByEmail(secUser.getUsername());
      return myUser;
    }
    return null;
  }

}
