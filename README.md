# 📚 Public Library Management Application ☕

Welcome to the **Public Library Management Application**! This project is a hands-on lab assignment for a Jakarta EE course, focusing on building a functional web application from scratch. The goal is to create a simple system for viewing and managing a collection of books.

## 🚀 Objective

The primary objective of this application is to apply core Jakarta EE concepts, starting with CDI (Contexts and Dependency Injection), to develop a robust and maintainable web application.

## ✨ Features (Module 1)

* **Book Model:** A simple `Book` class with `id`, `title`, and `author` properties.

* **Book Service Interface:** Defines the contract for book-related operations.

* **In-Memory Book Service:** An `@ApplicationScoped` CDI bean that provides a list of pre-populated sample books.

* **CDI Test Servlet:** A servlet to demonstrate and verify that CDI is correctly injecting the `BookService` and allowing access to the book data.

## 🛠️ Technologies Used

* **Jakarta EE 10:** The platform for building enterprise Java applications.

* **CDI (Contexts and Dependency Injection):** For managing component lifecycles and injecting dependencies.

* **Maven:** Project management and build automation tool.

* **Java:** The programming language.

## 📦 Getting Started

Follow these steps to set up and run the `library-management` application.

### 1. Project Setup

If you haven't already, create a new Maven project.

```
mvn archetype:generate -DgroupId=com.yourcompany -DartifactId=library-management -DarchetypeArtifactId=maven-archetype-webapp -DinteractiveMode=false

```

### 2. `pom.xml` Configuration

Add the `jakarta.jakartaee-api` dependency to your `pom.xml` inside the `<dependencies>` section.

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="[http://maven.apache.org/POM/4.0.0](http://maven.apache.org/POM/4.0.0)" xmlns:xsi="[http://www.w3.org/2001/XMLSchema-instance](http://www.w3.org/2001/XMLSchema-instance)" xsi:schemaLocation="[http://maven.apache.org/POM/4.0.0](http://maven.apache.org/POM/4.0.0) [http://maven.apache.org/xsd/maven-4.0.0.xsd](http://maven.apache.org/xsd/maven-4.0.0.xsd)">
    <modelVersion>4.0.0</modelVersion>
    <groupId>com.mycompany</groupId>
    <artifactId>library-management</artifactId> <!-- Ensure this matches your project artifactId -->
    <version>1.0-SNAPSHOT</version>
    <packaging>war</packaging> <!-- Changed packaging to 'war' for web application -->

    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <maven.compiler.source>11</maven.compiler.source> <!-- Or your preferred Java version, e.g., 17, 21 -->
        <maven.compiler.target>11</maven.compiler.target> <!-- Or your preferred Java version -->
        <jakartaee.api.version>10.0.0</jakartaee.api.version> <!-- Define a property for the version -->
    </properties>

    <dependencies>
        <dependency>
            <groupId>jakarta.jakartaee</groupId>
            <artifactId>jakarta.jakartaee-api</artifactId>
            <version>${jakartaee.api.version}</version>
            <scope>provided</scope>
        </dependency>
    </dependencies>

    <build>
        <finalName>library-management</finalName>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.8.1</version>
                <configuration>
                    <source>${maven.compiler.source}</source>
                    <target>${maven.compiler.target}</target>
                </configuration>
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-war-plugin</artifactId>
                <version>3.3.2</version>
            </plugin>
        </plugins>
    </build>
</project>

```

### 3. Project Structure and Code

Create the following Java classes in your `src/main/java/com/mycompany/library` (or similar, based on your `groupId`) directory.

#### `Book.java`

```java
package com.mycompany.library; // Adjust package name as needed

public class Book {
    private Long id;
    private String title;
    private String author;

    public Book() {
        // Default constructor for CDI
    }

    public Book(Long id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}

```

#### `BookService.java` (Interface)

```java
package com.mycompany.library; // Adjust package name as needed

    import java.util.List;

      public interface BookService {
          List<Book> findAllBooks();
      }

```

#### `InMemoryBookService.java` (CDI Bean)

```java
package com.mycompany.library; // Adjust package name as needed

import jakarta.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class InMemoryBookService implements BookService {

    private List<Book> books;

    public InMemoryBookService() {
        books = new ArrayList<>();
        books.add(new Book(1L, "The Hitchhiker's Guide to the Galaxy", "Douglas Adams"));
        books.add(new Book(2L, "1984", "George Orwell"));
        books.add(new Book(3L, "Brave New World", "Aldous Huxley"));
    }

    @Override
    public List<Book> findAllBooks() {
        return books;
    }
}

```

#### `CdiTestServlet.java` (Servlet for Testing CDI)

```java
package com.mycompany.library; // Adjust package name as needed

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/CdiTestServlet") // Define the URL path for this servlet
public class CdiTestServlet extends HttpServlet {

    @Inject // CDI will inject an instance of BookService here
    private BookService bookService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><head><title>CDI Test Servlet</title></head><body>");
        out.println("<h1>📚 List of Books from CDI Service:</h1>");

        List<Book> books = bookService.findAllBooks();
        if (books != null && !books.isEmpty()) {
            out.println("<ul>");
            for (Book book : books) {
                out.println("<li>" + book.getTitle() + " by " + book.getAuthor() + "</li>");
            }
            out.println("</ul>");
        } else {
            out.println("<p>No books found in the service.</p>");
        }

        out.println("</body></html>");
    }
}

```

### 4. Build the Project 🏗️

Navigate to your `library-management` project's root directory in your terminal and build the project using Maven:

```
mvn clean package

```

This command will compile your Java code and package it into a `.war` file (e.g., `library-management.war`) in the `target/` directory.

### 5. Deploy and Test 🌐

Deploy the generated `.war` file to a Jakarta EE compatible application server (e.g., GlassFish, WildFly, Open Liberty, Apache Tomcat with Jakarta EE support).

After deployment, open your web browser and navigate to the `CdiTestServlet`. The URL will typically be something like:

```
http://localhost:8080/library-management/CdiTestServlet

```

(Replace `8080` with your application server's port if different, and `library-management` with the context root if you changed it).

You should see an HTML page displaying the titles of the sample books, confirming that CDI is working correctly! 🎉

## 📝 Assignment Context

This project is part of the DICT312-Week 3 Lab assignment from the University of Mpumalanga. It focuses on establishing the foundational structure of a Public Library Application using Jakarta EE and CDI.
