import java.lang.Thread.sleep

plugins {
    java
    id( "io.qameta.allure") version "2.9.6" // Latest Plugin Version
    application
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(8))
    }
}

repositories {
    mavenCentral()
}


val log4jVersion = "2.20.0"
// проверять совместимость с установленной версией плагина в jenkins
val allureVersion = "2.17.3"
val junitPlatformVersion = "1.9.3"
val junitJupiterVersion  = "5.9.3"
val junitVersion = "4.12"
val seleniumVersion = "4.9.0"


allure {
        version.set(allureVersion)
        adapter {
            // Configure version for io.qameta.allure:allure-* adapters
            allureJavaVersion.set(allureVersion)
            aspectjVersion.set("1.9.7")

            autoconfigure.set(true)
            autoconfigureListeners.set(true)
            aspectjWeaver.set(true)

            // By default, categories.json is detected in src/test/resources/../categories.json,
            // However, it would be better to put the file in a well-known location and configure it explicitly
            //categoriesFile.set(layout.projectDirectory.file("config/allure/categories.json"))
            frameworks {
                junit5 {
                    // Defaults to allureJavaVersion
                    adapterVersion.set(allureVersion)
                    enabled.set(true)
                    // Enables allure-junit4 default test listeners via META-INF/services/...
                    autoconfigureListeners.set(true)
                }
//                spock
            }
        }
}

dependencies {
    testImplementation ("commons-io:commons-io:2.6")
    testImplementation ("org.seleniumhq.selenium:selenium-java:$seleniumVersion")
    testImplementation ("org.apache.poi:poi-ooxml:3.17")
    testImplementation ("org.slf4j:slf4j-simple:2.0.7")

    testImplementation("io.rest-assured:rest-assured:4.4.0")
    testImplementation ("org.apache.commons:commons-lang3:3.8.1")

    implementation("io.qameta.allure:allure-java-commons:$allureVersion")

    implementation("org.apache.logging.log4j:log4j-api:$log4jVersion")
    implementation("org.apache.logging.log4j:log4j-core:$log4jVersion")

    // https://mvnrepository.com/artifact/org.apache.logging.log4j/log4j-1.2-api
    implementation("org.apache.logging.log4j:log4j-1.2-api:$log4jVersion")

    testImplementation("org.jetbrains:annotations:20.1.0")

    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitJupiterVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-params:$junitJupiterVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitJupiterVersion")

    // https://mvnrepository.com/artifact/org.junit.vintage/junit-vintage-engine
    testImplementation("org.junit.vintage:junit-vintage-engine:$junitJupiterVersion")

    // https://bonigarcia.dev/webdrivermanager/
    testImplementation("io.github.bonigarcia:webdrivermanager:5.3.2")

//    implementation("javax.json:javax.json-api:$jsonApiVersion")
    // реализация json api
//    implementation("org.glassfish:javax.json:$jsonApiVersion")

    // https://mvnrepository.com/artifact/com.chfourie/uml-java-doclet
//    implementation("com.chfourie:uml-java-doclet:1.0.0")

//    javadocTaglets("org.jdrupes.taglets:plantuml-taglet:1.0.5")
    // https://mvnrepository.com/artifact/com.jcraft/jsch
//    implementation("com.jcraft:jsch:0.1.55")

//    testImplementation ("org.eclipse.swt:org.eclipse.swt.win32.win32.x86_64:4.3")

    // https://mvnrepository.com/artifact/org.apache.commons/commons-configuration2
//    implementation("org.apache.commons:commons-configuration2:2.7")

    // https://mvnrepository.com/artifact/com.github.junrar/junrar
//    implementation("com.github.junrar:junrar:7.5.1")

    // https://mvnrepository.com/artifact/net.java.dev.jna/jna-platform
//    implementation("net.java.dev.jna:jna-platform:$jnaVersion")

    // https://mvnrepository.com/artifact/javax.persistence/javax.persistence-api
//    implementation("javax.persistence:javax.persistence-api:2.2")

// https://mvnrepository.com/artifact/org.codelibs/jcifs
//    implementation("org.codelibs:jcifs:$jcifsVersion")

// https://mvnrepository.com/artifact/commons-beanutils/commons-beanutils
//    implementation("commons-beanutils:commons-beanutils:1.9.4")


    // https://mvnrepository.com/artifact/org.postgresql/postgresql
//    implementation("org.postgresql:postgresql:42.3.4")

    // https://mvnrepository.com/artifact/com.oracle.database.jdbc/ojdbc8
//    implementation("com.oracle.database.jdbc:ojdbc8:21.5.0.0")

// https://mvnrepository.com/artifact/org.firebirdsql.jdbc/jaybird
//    implementation("org.firebirdsql.jdbc:jaybird:4.0.6.java8")

    // https://mvnrepository.com/artifact/org.yaml/snakeyaml
//    implementation("org.yaml:snakeyaml:$snakeYamlVersion")

    // https://mvnrepository.com/artifact/com.jcraft/jsch
//    implementation("com.jcraft:jsch:0.1.55")

    // https://mvnrepository.com/artifact/com.jayway.jsonpath/json-path
//    implementation("com.jayway.jsonpath:json-path:2.7.0")

    // https://mvnrepository.com/artifact/org.apache.commons/commons-compress
//    implementation("org.apache.commons:commons-compress:1.21")

    // https://mvnrepository.com/artifact/org.xmlunit/xmlunit-core
//    testImplementation("org.xmlunit:xmlunit-core:2.6.0")

    // https://mvnrepository.com/artifact/org.xmlunit/xmlunit-matchers
//    testImplementation("org.xmlunit:xmlunit-matchers:2.9.1")

    // https://mvnrepository.com/artifact/xmlunit/xmlunit
//    testImplementation("xmlunit:xmlunit:1.6")


}


group = "ru.vlshestakov"
version = "2.0"

tasks {
    compileJava {
        options.encoding = "UTF-8"
    }
    compileTestJava {
        options.encoding = "UTF-8"
    }
    javadoc {
        options.encoding = "UTF-8"
        classpath += sourceSets.test.get().compileClasspath
        source += sourceSets.test.get().allJava
        //excludes += "ru/bft/qa/azk/deploy/**"
        // https://www.baeldung.com/java-doclint
        val standardOptions = (options as StandardJavadocDocletOptions)
        standardOptions.addStringOption("Xdoclint:none", "-quiet")
    }
}


tasks.withType(Test::class) {
    useJUnitPlatform()
    testLogging.showStandardStreams = true
    enableAssertions = true
//    val gzForks = project.property("GzForks").toString()
    //maxParallelForks = Integer.parseInt(gzForks);


//    systemProperty("junit.jupiter.execution.parallel.enabled", "true")
//    systemProperty("junit.jupiter.execution.parallel.mode.default", "same_thread")
//    systemProperty("junit.jupiter.execution.parallel.mode.classes.default", "concurrent")
//    systemProperty("junit.jupiter.execution.parallel.config.strategy", "fixed")
//    systemProperty("junit.jupiter.execution.parallel.config.fixed.parallelism", "5")

//    systemProperty("junit.jupiter.execution.parallel.config.strategy", "custom")
//    systemProperty("junit.jupiter.execution.parallel.config.custom.class",
//                        "ru.bft.qa.azk.tests.debug.selen4.JunitParallelCustomStrategy")



    systemProperties.putAll(System.getProperties().toMap() as Map<kotlin.String, java.lang.Object>)
//    systemProperties.put("GzForks", gzForks)
    // для linux заменяем кодировку лога консоли
    if (System.getProperty("os.name") == "Linux") {
        systemProperty("gz_log_charset", "UTF-8")
    }
    systemProperty("junit.jupiter.extensions.autodetection.enabled", "true")

}

tasks.withType(JavaCompile::class) {
    options.compilerArgs.add("-Xlint:deprecation")
}

