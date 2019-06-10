package poc.yaas.anagrams

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.validation.annotation.Validated
import javax.validation.constraints.NotBlank

@Configuration
@PropertySource("classpath:application.yml")
@ConfigurationProperties("dictionary")
@Validated
class DictionaryConfig {

    @NotBlank
    lateinit var language: String

    @NotBlank
    lateinit var folder: String
}