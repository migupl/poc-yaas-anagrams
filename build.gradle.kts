import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    id("org.springframework.boot") version "2.1.5.RELEASE"
    id("io.spring.dependency-management") version "1.0.7.RELEASE"
    kotlin("jvm") version "1.3.20"
    kotlin("plugin.spring") version "1.3.20"

    id("org.owasp.dependencycheck") version "5.0.0-M3.1"
}

group = "poc.yaas.anagrams"
java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
    jcenter()
    
    flatDir {
        dir(rootProject.file( "libs" ))
    }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-undertow")
    implementation("org.springframework.boot:spring-boot-starter-web")

    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    compile("local:anagrams-solver:0.1")
    compile("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.2.1")
    compile("org.codehaus.groovy:groovy:2.5.7")

    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    testImplementation("org.codehaus.groovy:groovy-servlet:2.5.7")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

configurations {
    // exclude Tomcat
    compile {
        exclude(module = "spring-boot-starter-tomcat")
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "1.8"
    }
}

tasks.getByName<BootJar>("bootJar") {
    exclude("application.yml")
    exclude("wordnet")
}
