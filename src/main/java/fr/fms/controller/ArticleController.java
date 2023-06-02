package fr.fms.controller;

import fr.fms.Repository.ArticleRepository;
import fr.fms.Repository.CategoryRepository;
import fr.fms.Repository.OrderItemRepository;
import fr.fms.entities.Article;
import fr.fms.entities.Category;
import fr.fms.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;


@Controller
public class ArticleController {
    @Autowired
     CustomFunc customFunc;
    @Autowired
     ArticleRepository articleRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    HashMap<Long, Article> articleOrderList = new HashMap<Long, Article>();



    public ArticleController() {
    }

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
                System.out.println(customFunc.getIsUserConnected());
                model.addAttribute("userConnected", customFunc.getIsUserConnected());
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
        public String article(Model model,
                              @RequestParam( required = false ,name = "idArticle")  Long idArticle)
        {
            List<Category> categories = categoryRepository.findAll();
            model.addAttribute("listCategories", categories);
            if(idArticle == null){
                model.addAttribute("article",new Article());
            } else {
                Article article = articleRepository.findById(idArticle).get();
                model.addAttribute("article",article);
            }


            return "article";
        }

        @PostMapping ("/save")
        public String article(@Valid Article article,  BindingResult bindingResult){
        if(bindingResult.hasErrors()) return "article";
        if(article.getId() == null){
            articleRepository.save(article);
        } else {
            Article articleUpdated = article;
            articleUpdated.setDescription(article.getDescription());
            articleUpdated.setBrand(article.getBrand());
            articleUpdated.setPrice(article.getPrice());
            articleUpdated.setCategory(article.getCategory());

            articleRepository.save(articleUpdated);
        }

            return "redirect:/index";
        }

        @GetMapping("/order")
        public String order(Model model,
                            @RequestParam(required = false,name = "articleId") Long articleId)
        {
        if(articleId == null){
            model.addAttribute("articleOrderList", articleOrderList);
            return "order";
        } else {
            Article article = articleRepository.findById(articleId).get();
            Article article_Id = articleOrderList.get(article.getId());

            if(article_Id != null){
                article_Id.setQuantity(article_Id.getQuantity()+1);
            } else {
                articleOrderList.put(article.getId(), article);
            }
            Boolean isUserConnected = customFunc.getIsUserConnected();
            model.addAttribute("userConnected", isUserConnected);
            model.addAttribute("articleOrderList", articleOrderList);
            model.addAttribute("totalPrice", customFunc.getTotalPrice(articleOrderList));
            model.addAttribute("totalQuantity", customFunc.getTotalNumber(articleOrderList));
            return "order";
        }
        }

        @GetMapping("/deleteOrder")
        public String deleteOrder(Long id){
            articleOrderList.get(id).setQuantity(articleOrderList.get(id).getQuantity()-1);
            if(articleOrderList.get(id).getQuantity() < 0){
                articleOrderList.remove(id);
            }
            return "redirect:/order";
        }



}
