# **REST API** with JWT, Spring Boot(Maven), MyBatis
## BBS BackEnd
### Purpose
1. Understand and implement JWT. 
2. ... Spring Boot Excution.
3. ... Inteceptor, Configurer.
4. ... REST API.

### Technical Stack
* Spring Boot(Maven)
* MyBatis
* MySQL
* JWT
* REST API
* Swagger


## Spring Boot
**HttpRequest -> Controller -> Service -> Mapper -> MySQL**
### Applicatin.java
Main Method in Spring Boot.
```java
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
```

### GlobalExceptionHandler.java
400, 401, 404, 500, etc...
Handle exceptions and Return Httpstatus.
```java
@RestControllerAdvice
@RestController
public class GlobalExceptionHandler implements ErrorController {

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void baseException (Exception e) {e.printStackTrace();}

}
```

### JWTInterceptor.java
JWT intercepts all requests.
```java
@Component
public class JWTInterceptor implements HandlerInterceptor {

    // Name of JWT
    private static final String HEADER_user = "token_name";

    @Override
    public boolean preHandle (HttpServletRequest request, HttpServletResponse response, Object handler) {

    }

}
```

### MVCConfig.java
Solve CORS Issues.
```java
@Configuration
public class MVCConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedHeaders("*")
                .exposedHeaders("token_name")
                .allowedMethods("GET", "POST")
                .allowCredentials(true)
                .maxAge(3600);
    }

}
```

### SqlSession.java
Creating SQL Session.
```java
@Configuration
public class SqlSession {

    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {

        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);

        Resource[] res = new PathMatchingResourcePatternResolver().getResources("classpath:mapper/*.xml");
        sqlSessionFactoryBean.setMapperLocations(res);

        sqlSessionFactoryBean.setTypeAliases(new Class<?>[] { UserModel.class, PostModel.class, ReplyModel.class, FileModel.class});

        return sqlSessionFactoryBean.getObject();

    }

}
```

### SwaggerConfig.java
Swagger Configure
```java
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.nomsa.bbs.Controller"))
                .paths(PathSelectors.any())
                .build();
    }

}
```


## DataBase
### User
Column | Datatype | PK | NN | UQ | UN | AI | Default
--- | --- | --- | --- | --- | --- | --- | ---
index | INT(11) | Yes | Yes |  | Yes | Yes |
identification | VARCHAR(30) |  | Yes | Yes |  |  |
password | VARCHAR(50) |  | Yes |  |  |  |
name | VARCHAR(15) |  | Yes |  |  |  |
available | CHAR(1) |  | Yes |  |  |  |

### Post
Column | Datatype | PK | NN | UQ | UN | AI | Default
--- | --- | --- | --- | --- | --- | --- | --
index | INT(11) | Yes | Yes |  | Yes | Yes | 
writer | INT(11) |  | Yes |  | Yes |  | 
created | DATETIME |  | Yes |  |  |  | CURRENT_TIMESTAMP
type | VARCHAR(15) |  | Yes |  |  |  |
title | VARCHAR(100) |  | Yes |  |  |  | 
content | TEXT |  | Yes |  |  |  |
available | CHAR(1) |  | Yes |  |  |  |

### Reply
Column | Datatype | PK | NN | UQ | UN | AI | Default
--- | --- | --- | --- | --- | --- | --- | --
index | INT(11) | Yes | Yes |  | Yes | Yes | 
writer | INT(11) |  | Yes |  | Yes |  | 
created | DATETIME |  | Yes |  |  |  | CURRENT_TIMESTAMP
post | INT(11) |  | Yes |  | Yes |  |
content | VARCHAR(300) |  | Yes |  |  |  |
available | CHAR(1) |  | Yes |  |  |  |

### File
Column | Datatype | PK | NN | UQ | UN | AI | Default
--- | --- | --- | --- | --- | --- | --- | --
index | INT(11) | Yes | Yes |  | Yes | Yes | 
post | INT(11) |  | Yes |  | Yes |  |
name | VARCHAR(100) |  | Yes |  |  |  |
size | INT(11) |  | Yes |  | Yes |  |
bytes | LONGBLOB |  | Yes |  |  |  |
available | CHAR(1) |  | Yes |  |  |  |


## Histories
    2018.08.06. JWT Util.
    2018.08.07. Define REST API.
    2018.08.08. Sign Up, Sing In.
    2018.08.09. Post Write, Reply Write.
    2018.08.10. Get Posts, Get Post.
    2018.08.11. Problems during communication. (CORS)
    2018.08.12. Solve CORS. Spring Boot excution problem when solving CORS.
    2018.08.13. Solve Spring Boot excution.

