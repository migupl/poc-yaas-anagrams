package poc.yaas.anagrams

import com.fasterxml.jackson.databind.ObjectMapper
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.json.JacksonTester
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import java.awt.PageAttributes

@RunWith(SpringRunner::class)
@SpringBootTest
@AutoConfigureMockMvc
class AnagramSolverControllerTest {

    @Autowired
    private val mvc: MockMvc? = null

    lateinit var json: JacksonTester<Any>

    @Before
    fun setup() {
        val objectMapper: ObjectMapper = ObjectMapper()
        JacksonTester.initFields(this, objectMapper)
    }

    @Test
    @Throws(Exception::class)
    fun givenHelloWord_whenAnagrams_thenReturnListOfAnagrams() {
        val expectedList = listOf(
                Anagrams(4, listOf("hell","hole")),
                Anagrams(3, listOf("Hel","hoe","ell","Leo").sortedWith(compareBy(String.CASE_INSENSITIVE_ORDER, { it }))),
                Anagrams(2, listOf("he"))
        )

        given_a_word_whenAnagrams_thenReturnListOfAnagrams("/anagrams/hello", expectedList)
    }

    private fun given_a_word_whenAnagrams_thenReturnListOfAnagrams(requestPath: String, expected: Any) {
        // when
        val response: MockHttpServletResponse = mvc!!.
                perform(MockMvcRequestBuilders.get(requestPath).accept(MediaType.APPLICATION_JSON)).
                andReturn().
                response

        // then
        assertThat(response.getStatus()).
                isEqualTo(HttpStatus.OK.value())

        assertThat(response.getContentAsString()).
                isEqualTo(json.write(expected).json)
    }

    @Test
    @Throws(Exception::class)
    fun givenEggsWord_whenAnagrams_thenReturnListOfAnagrams() {
        val expectedList = listOf(
                Anagrams(3, listOf("egg"))
        )

        given_a_word_whenAnagrams_thenReturnListOfAnagrams("/anagrams/eggs", expectedList)
    }

    @Test
    @Throws(Exception::class)
    fun givenNoneWord_whenAnagrams_thenReturnEmptyList() {
        given_a_word_whenAnagrams_thenReturnListOfAnagrams("/anagrams/None", emptyList<Anagrams>())
    }

    @Test
    @Throws(Exception::class)
    fun whenLanguage_thenReturnLanguageEn() {
        val expectedList = Language("en")

        given_a_word_whenAnagrams_thenReturnListOfAnagrams("/language", expectedList)
    }
}
