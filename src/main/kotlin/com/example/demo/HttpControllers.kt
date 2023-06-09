package com.example.demo

import org.springframework.http.HttpStatus.*
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/api/article")
class ArticleController(private val repository: ArticleRepository) {

    @GetMapping("/")
    fun findAll() = repository.findAllByOrderByAddedAtDesc()

    @GetMapping("/{slug}")
    fun findOne(@PathVariable slug: String) =
        repository.findBySlug(slug) ?: throw ResponseStatusException(NOT_FOUND, "This article does not exist")

    @PostMapping
    fun postArticle(@RequestBody article: Article) {
        repository.save(article)
    }

    @PutMapping
    fun updatedArticle(@RequestBody article: Article, @RequestParam id: Long) {
        val existingArticle = repository.findById(id).orElseThrow { ResponseStatusException(NOT_FOUND, "This article does not exist") }
        val updatedArticle = Article(
                title = article.title,
        headline = article.headline,
        content = article.content,
        author = article.author,
        slug = article.slug,
        id = existingArticle.id
        )
        repository.save(updatedArticle)
    }

    @DeleteMapping
    fun deleteArticle(@RequestParam id: Long){
        repository.deleteById(id)
    }
}

@RestController
@RequestMapping("/api/user")
class UserController(private val repository: UserRepository) {

    @GetMapping("/")
    fun findAll() = repository.findAll()

    @GetMapping("/{login}")
    fun findOne(@PathVariable login: String) = repository.findByLogin(login) ?: throw ResponseStatusException(NOT_FOUND, "This user does not exist")

    @PostMapping
    fun postUser(@RequestBody user: User) {
        if (repository.findByLogin(user.login) != null) throw ResponseStatusException(CONFLICT, "user already exists") else repository.save(user)
    }

    @DeleteMapping
    fun deleteUser(@RequestParam id: Long){
        repository.deleteById(id)
    }

}
