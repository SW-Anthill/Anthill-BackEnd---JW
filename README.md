# 프로젝트명
### 앤트힐 백앤드
# 프로젝트의 실행 환경
### - Window 10
### - Java가 11버전 이상 필요

# 이 프로젝트를 local 에서 어떻게 돌릴 수 있는지 소개

REST API 서버입니다.

Intellij나 Eclipse와 같은 IDE에 Anthill 폴더안에있는 파일들을 넣고 src/main/java/anthill/Anthill/AnthillApplication.java를 실행시키면 됩니다.

만약 자바버전이 16이 아니라면 사전작업이 필요합니다.

build.gradle의 sourceCompatibility = 16을 사용자에 맞는 자바 버전으로 변경합니다

만약 11버전을 사용하고 있다면 16 -> 11으로 변경하면 됩니다.

Anthill/src/main/resources에 application.properties를 작성해주셔야 합니다.
```
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost:3306/DB이름?useSSL=false&useUnicode=true&serverTimezone=Asia/Seoul
spring.datasource.username=DB아이디
spring.datasource.password=DB비밀번호
spring.jpa.show-sql=true
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.format_sql=true
```

# 프로젝트의 주요 Dependencies를 나열
```
	implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
	implementation 'org.springframework.boot:spring-boot-starter-web'
	implementation 'mysql:mysql-connector-java'
	implementation 'org.springframework.boot:spring-boot-starter-validation'
	implementation 'org.projectlombok:lombok:1.18.24'
	implementation 'org.mindrot:jbcrypt:0.4'
	asciidoctorExtensions 'org.springframework.restdocs:spring-restdocs-asciidoctor'
	testImplementation 'org.springframework.restdocs:spring-restdocs-mockmvc'
	compileOnly 'org.projectlombok:lombok'
	runtimeOnly 'com.h2database:h2'
	annotationProcessor 'org.projectlombok:lombok'
	testImplementation 'org.springframework.boot:spring-boot-starter-test'

```


# 기타 프로젝트 실행 혹은 이해에 필요한 글을 적어주세요.
프로젝트를 진행하며 고민하거나 적용해본 기술들을 기술블로그에 정리하였습니다.

시작부터 배포까지의 과정이 모두 담겨있으니 관심있으시면 다음링크를 참고하세요.

https://junuuu.tistory.com/category/%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8



# 실제 프로젝트를 서비스하고 있는 링크
http://ec2-3-39-221-135.ap-northeast-2.compute.amazonaws.com:8080/docs/index.html
