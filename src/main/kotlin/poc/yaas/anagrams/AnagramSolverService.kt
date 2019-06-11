package poc.yaas.anagrams

import chal.yaas.AnagramSolver
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.context.event.ApplicationStartedEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Service

@Service
class AnagramSolverService(
        private val dictionaryConfig: DictionaryConfig
) {

    private val LOG: Logger = LoggerFactory.getLogger(AnagramSolverService::class.java)

    private lateinit var anagramSolver: AnagramSolver

    fun anagrams(text: String, minLength: Int = 2): List<Anagrams> {
        val anagrams = anagramSolver.getAllAnagrams(text)
        val byLength = anagrams.
                groupByTo(mutableMapOf()) {
                    it.length
                }.
                filter {
                    it.key >= minLength
                }.
                toSortedMap(compareByDescending { it }).
                map { (length, words) ->
                    val sortedWords = words.sortedWith(compareBy(String.CASE_INSENSITIVE_ORDER, { it }))
                    Anagrams(length, sortedWords)
                }

        return byLength
    }

    fun language(): Language = Language(dictionaryConfig.language)

    @EventListener
    fun onApplicationEvent(event: ApplicationStartedEvent?) {
        GlobalScope.launch {
            loadAnagramsSolver()
        }
    }

    private suspend fun loadAnagramsSolver() = coroutineScope {
        val path: String = AnagramSolverService::class.java.classLoader.getResource(dictionaryConfig.folder).path
        LOG.info("Loading dictionary folder '$path'")

        anagramSolver = AnagramSolver(path)
        LOG.info("Dictionary folder loaded")
    }
}

data class Anagrams(val length: Int, val words: List<String>)
data class Language(val language: String)