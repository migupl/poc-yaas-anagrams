package poc.yaas.anagrams

import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
@CrossOrigin
class AnagramSolverController(
        private val anagramSolverService: AnagramSolverService
) {

    @GetMapping("/anagrams/{text}")
    fun solver(@PathVariable text: String): List<Anagrams> {
        return anagramSolverService.anagrams(text)
    }

    @GetMapping("/language")
    fun language(): Language {
        return anagramSolverService.language()
    }
}