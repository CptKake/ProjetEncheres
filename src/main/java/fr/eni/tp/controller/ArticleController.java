package fr.eni.tp.controller;

	import fr.eni.tp.bo.Article;
	import org.springframework.http.ResponseEntity;
	import org.springframework.web.bind.annotation.*;

	import java.util.List;
	import java.util.Optional;

	@RestController
	@RequestMapping("/articles") // URL de base pour les articles
	public class ArticleController {

	    private final ArticleService articleService;

	    public ArticleController(ArticleService articleService) {
	        this.articleService = articleService;
	    }

	    // 🔹 1. Récupérer tous les articles
	    @GetMapping
	    public List<Article> getAllArticles() {
	        return articleService.getAllArticles();
	    }

	    // 🔹 2. Récupérer un article par son ID
	    @GetMapping("/{id}")
	    public ResponseEntity<Article> getArticleById(@PathVariable int id) {
	        Optional<Article> article = articleService.getArticleById(id);
	        return article.map(ResponseEntity::ok)
	                      .orElseGet(() -> ResponseEntity.notFound().build());
	    }

	    // 🔹 3. Créer un nouvel article
	    @PostMapping
	    public ResponseEntity<Article> createArticle(@RequestBody Article article) {
	        Article savedArticle = articleService.saveArticle(article);
	        return ResponseEntity.ok(savedArticle);
	    }

	    // 🔹 4. Mettre à jour un article existant
	    @PutMapping("/{id}")
	    public ResponseEntity<Article> updateArticle(@PathVariable int id, @RequestBody Article newArticle) {
	        return articleService.getArticleById(id).map(article -> {
	            article.setName(newArticle.getName());
	            article.setDescription(newArticle.getDescription());
	            article.setBidStart(newArticle.getBidStart());
	            article.setBidEnd(newArticle.getBidEnd());
	            article.setInitPrice(newArticle.getInitPrice());
	            article.setSellPrice(newArticle.getSellPrice());
	            article.setUser(newArticle.getUser());
	            article.setCategory(newArticle.getCategory());

	            Article updatedArticle = articleService.saveArticle(article);
	            return ResponseEntity.ok(updatedArticle);
	        }).orElseGet(() -> ResponseEntity.notFound().build());
	    }

	    // 🔹 5. Supprimer un article
	    @DeleteMapping("/{id}")
	    public ResponseEntity<Void> deleteArticle(@PathVariable int id) {
	        articleService.deleteArticle(id);
	        return ResponseEntity.noContent().build();
	    }
	}

}
