package fr.fms.controller;

import fr.fms.Repository.ArticleRepository;
import fr.fms.Repository.CategoryRepository;
import fr.fms.entities.Article;
import fr.fms.entities.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@Controller
public class ArticleController {


    @Autowired
     ArticleRepository articleRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping(value = "/index")

        public String index(Model model, @RequestParam(name = "page", defaultValue = "0")int page,
                            @RequestParam(name = "kw", defaultValue = "") String kw,
                            @RequestParam(name = "idCat", defaultValue = "0") Long idCat
                            )
        {
            PageRequest pageRequest = PageRequest.of(page, 5, Sort.by("id").ascending());
            Page <Article> articles;
            List<Category> categories = categoryRepository.findAll();


            if(!kw.isEmpty()){
                articles = articleRepository.findByDescriptionContains(kw, pageRequest);
            } else if(idCat != 0){
                articles = articleRepository.findByCategoryId(idCat, pageRequest);
            } else {
                articles = articleRepository.findAll(pageRequest);
            }
                model.addAttribute("listCategories", categories);
                model.addAttribute("listArticle", articles.getContent());
                model.addAttribute("pages", new int [articles.getTotalPages()]);
                model.addAttribute("currentPage", page);




            return "articles";
        }

        @GetMapping("/articleKw")
        public String findByKw(Model model, @RequestParam(name = "kw", defaultValue = "") String kw,
                               @RequestParam(name = "page", defaultValue = "0")int page)
        {
            PageRequest pageRequest = PageRequest.of(page, 5, Sort.by("id").ascending());
            Page <Article> articleByKw = articleRepository.findByDescriptionContains(kw, pageRequest);
            model.addAttribute("listArticle", articleByKw.getContent());
            model.addAttribute("pages", new int [articleByKw.getTotalPages()]);
            model.addAttribute("currentPage", page);
        return "articles";
        }

        @GetMapping("/delete")
        public String delete(Long id, int page){
            articleRepository.deleteById(id);
            return "redirect:/index?page="+page;
        }

        @GetMapping("/article")
        public String article(Model model){
            List<Category> categories = categoryRepository.findAll();
            model.addAttribute("listCategories", categories);
            model.addAttribute("article",new Article());
            return "article";
        }

        @PostMapping ("/save")
        public String article(@Valid Article article, Long id, BindingResult bindingResult){
        if(bindingResult.hasErrors()) return "article";
        System.out.println(id);
//        Category cat = categoryRepository.findById(id).get();

            articleRepository.save(article);
            return "redirect:/index";
        }
}
