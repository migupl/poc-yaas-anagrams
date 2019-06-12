# A Anagrams solver minimal server

A server for the [Anagrams solver challenge][1] .. yes, another anagrams solver.
[Kotlin][4] and [Spring Boot 2][2] with [Undertow][3] embedded server are used.

## Environment

```bash
$ ./gradlew -v

------------------------------------------------------------
Gradle 5.4.1
------------------------------------------------------------

Build time:   2019-04-26 08:14:42 UTC
Revision:     261d171646b36a6a28d5a19a69676cd098a4c19d

Kotlin:       1.3.21
Groovy:       2.5.4
Ant:          Apache Ant(TM) version 1.9.13 compiled on July 10 2018
JVM:          1.8.0_212 (Azul Systems, Inc. 25.212-b04)
OS:           Linux 4.19.45-1-MANJARO amd64

```

## OWASP 10 dependency check

Dependency check tasks are available

```bash
[project.path]$ ./gradlew tasks
...
OWASP dependency-check tasks
----------------------------
dependencyCheckAggregate - Identifies and reports known vulnerabilities (CVEs) in multi-project dependencies.
dependencyCheckAnalyze - Identifies and reports known vulnerabilities (CVEs) in project dependencies.
dependencyCheckPurge - Purges the local cache of the NVD.
dependencyCheckUpdate - Downloads and stores updates from the NVD CVE data feeds.
...

```

As by example
```bash
[project.path]$  ./gradlew dependencyCheckAnalyze

> Task :dependencyCheckAnalyze
Verifying dependencies for project poc-yaas-server
Checking for updates and analyzing dependencies for vulnerabilities
----------------------------------------------------
.NET Assembly Analyzer could not be initialized and at least one 'exe' or 'dll' was scanned. The 'dotnet' executable could not be found on the path; either disable the Assembly Analyzer or configure the path dotnet core.
----------------------------------------------------
Generating report for project poc-yaas-server
Found 3 vulnerabilities in project poc-yaas-server


One or more dependencies were identified with known vulnerabilities in poc-yaas-

undertow-servlet-2.0.20.Final.jar (pkg:maven/io.undertow/undertow-servlet@2.0.20.Final) : CVE-2018-1067
undertow-core-2.0.20.Final.jar (pkg:maven/io.undertow/undertow-core@2.0.20.Final) : CVE-2018-1067
jackson-databind-2.9.8.jar (pkg:maven/com.fasterxml.jackson.core/jackson-databind@2.9.8, cpe:2.3:a:fasterxml:jackson:2.9.8:*:*:*:*:*:*:*, cpe:2.3:a:fasterxml:jackson-databind:2.9.8:*:*:*:*:*:*:*) : CVE-2019-12086


See the dependency-check report for more details.



BUILD SUCCESSFUL in 12s
1 actionable task: 1 executed
```

## Kotlin Spring Boot server
 
The server exposes two endpoints
- **/language**, response is dictionary's language at [ISO 639-1:2002 code][13] country
- **/anagrams/{text}**, response is all anagrams for the 'text' received as path parameter sorted alphabetically and by descendinglength 

Server would be running with
 
 ```bash
 [project.path]$ ./gradlew bootRun
> Task :compileJava NO-SOURCE
> Task :processResources UP-TO-DATE
> Task :classes UP-TO-DATE

> Task :bootRun

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::        (v2.1.5.RELEASE)

2019-06-10 17:47:53.476  INFO 11573 --- [           main] poc.yaas.anagrams.ApplicationKt          : Starting ApplicationKt on spectre with PID 11573 ([project.path]/build/classes/kotlin/main started by user in [project.path])
2019-06-10 17:47:53.482  INFO 11573 --- [           main] poc.yaas.anagrams.ApplicationKt          : No active profile set, falling back to default profiles: default
2019-06-10 17:47:53.759  WARN 11573 --- [kground-preinit] o.s.h.c.j.Jackson2ObjectMapperBuilder    : For Jackson Kotlin classes support please add "com.fasterxml.jackson.module:jackson-module-kotlin" to the classpath
2019-06-10 17:47:55.608  WARN 11573 --- [           main] io.undertow.websockets.jsr               : UT026010: Buffer pool was not set on WebSocketDeploymentInfo, the default pool will be used
2019-06-10 17:47:55.655  INFO 11573 --- [           main] io.undertow.servlet                      : Initializing Spring embedded WebApplicationContext
2019-06-10 17:47:55.655  INFO 11573 --- [           main] o.s.web.context.ContextLoader            : Root WebApplicationContext: initialization completed in 1683 ms
2019-06-10 17:47:56.994  INFO 11573 --- [           main] o.s.s.concurrent.ThreadPoolTaskExecutor  : Initializing ExecutorService 'applicationTaskExecutor'
2019-06-10 17:47:57.548  INFO 11573 --- [           main] org.xnio                                 : XNIO version 3.3.8.Final
2019-06-10 17:47:57.568  INFO 11573 --- [           main] org.xnio.nio                             : XNIO NIO Implementation Version 3.3.8.Final
2019-06-10 17:47:57.757  INFO 11573 --- [           main] o.s.b.w.e.u.UndertowServletWebServer     : Undertow started on port(s) 8080 (http) with context path ''
2019-06-10 17:47:57.763  INFO 11573 --- [           main] poc.yaas.anagrams.ApplicationKt          : Started ApplicationKt in 5.058 seconds (JVM running for 5.69)
2019-06-10 17:47:57.894  INFO 11573 --- [atcher-worker-2] poc.yaas.anagrams.AnagramSolverService   : Loading dictionary folder '[project.path]/build/resources/main/wordnet/en'
2019-06-10 17:48:02.131  INFO 11573 --- [atcher-worker-2] poc.yaas.anagrams.AnagramSolverService   : Dictionary folder loaded

```
 
Test **/language** endpoint with curl
 
```bash
[project.path]$ curl http://localhost:8080/language
{"language":"en"}
```

Test **/anagrams** endpoint with curl
 
```bash
[project.path]$ curl http://localhost:8080/anagrams/Ars%20magna
[{"length":8,"words":["anagrams"]},{"length":7,"words":["anagram"]},{"length":6,"words":["Angara","Asanga","Asmara","gasman","marang","samara"]},{"length":5,"words":["A'man","agama","Anasa","Angas","Asama","asana","G-man","grama","saran"]},{"length":4,"words":["agar","Agra","Anas","Aram","Aras","Gram","maar","mara","Mars","Masa","Naga","Rama","Rana","saga","Sana","snag"]},{"length":3,"words":["Aga","ana","Ara","arm","gam","gar","gas","man","mar","Mrs","nag","rag","ram","sag"]},{"length":2,"words":["aa","ma","Ms","Ra","SA"]}]
```

An important thing, dictionary is loading **asynchronously** at server startup.

### Docker image

You can create a Docker image without dictionary info executing

```bash
[project.path]$ ./gradlew bootJar
> Task :compileKotlin UP-TO-DATE
> Task :compileJava NO-SOURCE
> Task :processResources UP-TO-DATE
> Task :classes UP-TO-DATE
> Task :bootJar

BUILD SUCCESSFUL in 1s
3 actionable tasks: 1 executed, 2 up-to-date

[project.path]$
[project.path]$ sudo docker build -t anagrams-server .
Sending build context to Docker daemon  31.02MB
Step 1/7 : FROM openjdk:8-jdk-alpine
 ---> a3562aa0b991
Step 2/7 : RUN apk add --no-cache unzip
 ---> Using cache
 ---> e843b566568d
Step 3/7 : WORKDIR /app
 ---> Using cache
 ---> 73b10183bd9b
Step 4/7 : COPY build/libs/*.jar server.jar
 ---> Using cache
 ---> 7e321f97cc9e
Step 5/7 : RUN unzip server.jar && rm -f server.jar
 ---> Running in 36ff831dc185
Archive:  server.jar
   creating: org/
...
 extracting: BOOT-INF/lib/log4j-api-2.11.2.jar  
Removing intermediate container 36ff831dc185
 ---> a7b579fb0682
Step 6/7 : EXPOSE 8080
 ---> Running in f808e88c3d7d
Removing intermediate container f808e88c3d7d
 ---> 9ace2cbd6082
Step 7/7 : CMD java  -noverify ${JAVA_OPTS}  org.springframework.boot.loader.JarLauncher
 ---> Running in 887e5076d1d1
Removing intermediate container 887e5076d1d1
 ---> fe27d8e0b56d
Successfully built fe27d8e0b56d
Successfully tagged anagrams-server:latest
[project.path]$ 
```

Now, you can use any dictionary at running container as by example with Wordnet dictionary
```bash
[project.path]$ sudo docker run -it -p 8080:8080 -v $(pwd)/src/main/resources/wordnet:/app/wordnet -v $(pwd)/src/main/resources/application.yml:/app/application.yml anagrams-server

  .   ____          _            __ _ _
 /\\ / ___'_ __ _ _(_)_ __  __ _ \ \ \ \
( ( )\___ | '_ | '_| | '_ \/ _` | \ \ \ \
 \\/  ___)| |_)| | | | | || (_| |  ) ) ) )
  '  |____| .__|_| |_|_| |_\__, | / / / /
 =========|_|==============|___/=/_/_/_/
 :: Spring Boot ::        (v2.1.5.RELEASE)

2019-06-10 16:39:32.396  INFO 1 --- [           main] poc.yaas.anagrams.ApplicationKt          : Starting ApplicationKt on cf6d44d5ac5e with PID 1 (/app/BOOT-INF/classes started by root in /app)
2019-06-10 16:39:32.408  INFO 1 --- [           main] poc.yaas.anagrams.ApplicationKt          : No active profile set, falling back to default profiles: default
2019-06-10 16:39:32.613  WARN 1 --- [kground-preinit] o.s.h.c.j.Jackson2ObjectMapperBuilder    : For Jackson Kotlin classes support please add "com.fasterxml.jackson.module:jackson-module-kotlin" to the classpath
2019-06-10 16:39:35.826  WARN 1 --- [           main] io.undertow.websockets.jsr               : UT026010: Buffer pool was not set on WebSocketDeploymentInfo, the default pool will be used
2019-06-10 16:39:35.943  INFO 1 --- [           main] io.undertow.servlet                      : Initializing Spring embedded WebApplicationContext
2019-06-10 16:39:35.943  INFO 1 --- [           main] o.s.web.context.ContextLoader            : Root WebApplicationContext: initialization completed in 2970 ms
2019-06-10 16:39:37.655  INFO 1 --- [           main] o.s.s.concurrent.ThreadPoolTaskExecutor  : Initializing ExecutorService 'applicationTaskExecutor'
2019-06-10 16:39:38.518  INFO 1 --- [           main] org.xnio                                 : XNIO version 3.3.8.Final
2019-06-10 16:39:38.536  INFO 1 --- [           main] org.xnio.nio                             : XNIO NIO Implementation Version 3.3.8.Final
2019-06-10 16:39:38.712  INFO 1 --- [           main] o.s.b.w.e.u.UndertowServletWebServer     : Undertow started on port(s) 8080 (http) with context path ''
2019-06-10 16:39:38.720  INFO 1 --- [           main] poc.yaas.anagrams.ApplicationKt          : Started ApplicationKt in 7.062 seconds (JVM running for 7.859)
2019-06-10 16:39:38.891  INFO 1 --- [atcher-worker-1] poc.yaas.anagrams.AnagramSolverService   : Loading dictionary folder '/app/wordnet/en'
2019-06-10 16:39:42.302  INFO 1 --- [atcher-worker-1] poc.yaas.anagrams.AnagramSolverService   : Dictionary folder loaded

```

and using curl you can request by example
```bash
[project.path]$ curl http://localhost:8080/anagrams/example
[{"length":5,"words":["ample","Eelam","expel","maple"]},{"length":4,"words":["alee","apex","axle","Elam","lame","lamp","leap","male","meal","pale","palm","peal","peel","plea"]},{"length":3,"words":["ale","alp","ape","axe","eel","elm","lap","lax","lea","lee","map","pal","pax","pea"]},{"length":2,"words":["ax","Ea","em","la","LP","ma","pe"]}]
```

Use other dictionary as used in tests
```bash
[project.path]$ sudo docker run -it -p 8080:8080 -v $(pwd)/src/test/resources/dict:/app/dict -v $(pwd)/src/test/resources/application.yml:/app/application.yml anagrams-server
```

and doing the same request with curl
```bash
[project.path]$ curl http://localhost:8080/anagrams/example
[]
```

gets empty response because the dictionary only contains a few words.

The important things are the volume definition (config and dictionary path) at instance starting.

```bash
[project.path]$ sudo docker run -it -p 8080:8080 -v <absolute local path to>/<dictionary folder>:/app/<dictionary folder> -v <absolute local path to>/application.yml:/app/application.yml anagrams_server
```

You have to define:
- **application.yml**, defines path to dictionary and language like for example
  ```yaml
    dictionary:
      language: en
      folder: <dictionary folder>
    ```
- **\<dictionary folder\>**, dictionary folder

and both must be mapped with same name to **/app** folder at running Docker instance.

But playing with multiples instances who contains distinct dictionaries is most funny. Remember to mapping every one 
to a different port.

## Dictionary

The used dictionary was the [Wordnet][9] database for English ([MIT license][10] [citation][11]) or [Other laguages][12]

---
[1]: https://github.com/migupl/chal-yaas
[2]: https://spring.io/projects/spring-boot
[3]: http://undertow.io/
[4]: https://kotlinlang.org/
[10]: https://wordnet.princeton.edu/license-and-commercial-use
[11]: https://wordnet.princeton.edu/citing-wordnet
[12]: http://globalwordnet.org/resources/wordnets-in-the-world/
[13]: https://en.wikipedia.org/wiki/ISO_639-1
