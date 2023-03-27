import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.0.0"
    id("com.microsoft.azure.azurewebapp") version "1.2.0"
    id("io.spring.dependency-management") version "1.1.0"
    kotlin("jvm") version "1.7.21"
    kotlin("plugin.spring") version "1.7.21"
    kotlin("plugin.jpa") version "1.7.21"
}

group = "com.invoice"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")

    // MySQL
    runtimeOnly("com.mysql:mysql-connector-j")

    // Security
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.security:spring-security-web")
    implementation("org.springframework.security:spring-security-config")
    implementation("org.springframework:spring-jdbc")

    implementation("eu.bitwalker:UserAgentUtils:1.21")

    // Para que la encriptación del token funcione
    implementation("javax.xml.bind:jaxb-api:2.4.0-b180830.0359")
    implementation("io.jsonwebtoken:jjwt:0.9.1")

    implementation("com.microsoft.sqlserver:mssql-jdbc:11.2.2.jre17")
    implementation("com.microsoft.azure:azure-webapp-maven-plugin:2.8.0")
    implementation("com.azure:azure-ai-anomalydetector:3.0.0-beta.5")

    compileOnly("org.projectlombok:lombok")

    // Retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.4")
    implementation ("com.squareup.okhttp3:okhttp:5.0.0-alpha.4")
    implementation ("org.jbundle.util.osgi.wrapped:org.jbundle.util.osgi.wrapped.org.apache.http.client:4.1.2")

    // Corrutinas
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-swing:1.6.4")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-jdk8:1.6.4")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-slf4j:1.6.4")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-debug:1.6.4")

}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
