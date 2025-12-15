package com.marotech.recording.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class LocalizableErrorKeyChecker {

    private static final Pattern LOCALIZABLE_ERROR_PATTERN =
            Pattern.compile("new\\s+LocalizableError\\s*\\(\\s*\"([^\"]+)\"");

    public static Set<String> findMissingKeys(Path sourceRoot) throws IOException {// Load keys from properties file

        Properties properties = loadPropertiesFromClasspath("StripesResources.properties");
        Set<String> propertyKeys = properties.stringPropertyNames();
        Set<String> keysInCode = new HashSet<>();

        // Walk the file tree starting from sourceRoot to find all .java files
        try (Stream<Path> paths = Files.walk(sourceRoot)) {
            paths.filter(Files::isRegularFile)
                    .filter(p -> p.toString().endsWith(".java"))
                    .forEach(javaFile -> {
                        try {
                            List<String> lines = Files.readAllLines(javaFile);
                            for (String line : lines) {
                                Matcher matcher = LOCALIZABLE_ERROR_PATTERN.matcher(line);
                                while (matcher.find()) {
                                    String key = matcher.group(1);
                                    keysInCode.add(key);
                                }
                            }
                        } catch (IOException e) {
                            System.err.println("Error reading file: " + javaFile);
                        }
                    });
        }

        // Find keys that are present in code but not in properties
        Set<String> missingKeys = new HashSet<>();
        for (String key : keysInCode) {
            if (!propertyKeys.contains(key)) {
                missingKeys.add(key);
            }
        }
        return missingKeys;
    }

    public static void findAndPrintKeys(Path sourceRoot) throws IOException {
        Properties properties = loadPropertiesFromClasspath("StripesResources.properties");
        Set<String> propertyKeys = properties.stringPropertyNames();

        Set<String> keysInCode = new HashSet<>();

        // Recursively search for .java files and extract keys
        try (Stream<Path> paths = Files.walk(sourceRoot)) {
            paths.filter(Files::isRegularFile)
                    .filter(p -> p.toString().endsWith(".java"))
                    .forEach(javaFile -> {
                        try {
                            List<String> lines = Files.readAllLines(javaFile);
                            for (String line : lines) {
                                Matcher matcher = LOCALIZABLE_ERROR_PATTERN.matcher(line);
                                while (matcher.find()) {
                                    String key = matcher.group(1);
                                    keysInCode.add(key);
                                }
                            }
                        } catch (IOException e) {
                            System.err.println("Error reading file: " + javaFile + ": " + e.getMessage());
                        }
                    });
        }

        // Keys that are both in source files and properties file
        List<String> matchedKeysWithValues = new ArrayList<>();

        for (String key : keysInCode) {
            if (propertyKeys.contains(key)) {
                String value = properties.getProperty(key);
                matchedKeysWithValues.add(key + " = " + value);
            }
        }

        Collections.sort(matchedKeysWithValues);

        // Print the matched keys with values separated by new lines
        if (matchedKeysWithValues.isEmpty()) {
            System.out.println("No matching keys found in source files and StripesResources.properties.");
        } else {
            System.out.println("Keys found both in source files and StripesResources.properties:");
            matchedKeysWithValues.forEach(System.out::println);
        }
    }

    public static Properties loadPropertiesFromClasspath(String resourceName) throws IOException {
        Properties properties = new Properties();

        // Use the current thread context class loader to load the resource as a stream
        try (InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(resourceName)) {
            if (inputStream == null) {
                throw new IOException("Resource not found in classpath: " + resourceName);
            }
            // Load properties from the input stream
            properties.load(inputStream);
        }

        return properties;
    }


    public static void main(String[] args) throws IOException {
        Path sourceRoot = Paths.get(MavenSourceRootDiscoverer.getMavenSourceRoot().toUri());

        Set<String> missingKeys = findMissingKeys(sourceRoot);
        if (missingKeys.isEmpty()) {
        } else {
            missingKeys.forEach(System.out::println);
        }
        System.out.println("---------------------------KEYS FOUND--------------------------------");
        findAndPrintKeys(sourceRoot);
    }
}
