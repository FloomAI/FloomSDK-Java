<div align="center">

# FloomSDK-Java

**Floom Java SDK** - A Java library for interacting with [Floom](https://floom.ai), an AI Orchestration platform that empowers Developers and DevOps.

</div>

## About Floom

[Floom](https://floom.ai) orchestrates and executes Generative AI pipelines, allowing developers and DevOps teams to focus on what matters most. It offers enterprise-grade, production-ready, and battle-tested solutions, now open-source and free for everyone, including commercial use.

Floom's AI Pipeline model simplifies the integration and execution process of Generative AI, handling everything from prompt design and data linking to execution and cost management.

## Getting Started with FloomSDK-Java

### Installation

To start using FloomSDK-Java, add the following dependency to your project's `build.gradle` file:

```gradle
dependencies {
    // Other dependencies...

    implementation 'ai.floom:java-sdk:1.0.0'
}
```
Or if you are using Maven, add this to your pom.xml:

```pomxml
<dependencies>
    <!-- Other dependencies... -->

    <dependency>
        <groupId>ai.floom</groupId>
        <artifactId>java-sdk</artifactId>
        <version>1.0-SNAPSHOT</version>
    </dependency>
</dependencies>
```

### Usage
Here's how you can use the Floom Java SDK in your application:

```java
import ai.floom.FloomClient;
import java.util.HashMap;

public class Example {
    public static void main(String[] args) {
        // Initialize FloomClient
        FloomClient floomClient = new FloomClient("floom_endpoint", "api_key");

        // Example: Running a pipeline
        try {
            FloomResponse response = floomClient.run(
                    "your_pipeline_id",
                    "your_chat_id",
                    "your_input",
                    new HashMap<>() // For variables, if any
            );

            // Process the response
            System.out.println("Response: " + response);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}

```
Or if you are using async approach, use this:

```java
import ai.floom.FloomClient;
import java.util.HashMap;

public class Example {
    public static void main(String[] args) {
        // Initialize FloomClient
        FloomClient floomClient = new FloomClient("floom_endpoint", "api_key");

        floomClient.runAsync("docs-pipeline-v1", "abcdefghijklmnop", "Who was the first US president?", null)
                .thenAccept(response -> {
                    System.out.println("Response: " + response);
                })
                .exceptionally(e -> {
                    System.out.println("Error: " + e.getMessage());
                    return null;
                });
    }
}

```
This README provides a concise yet comprehensive introduction to Floom and its Java SDK. It includes installation instructions, a basic usage example, links to further documentation, contribution guidelines, and licensing information. The structure is designed to be user-friendly and to enhance the visibility of your project.

For more information, visit us at https://floom.ai.



