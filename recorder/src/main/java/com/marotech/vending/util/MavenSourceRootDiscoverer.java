package com.marotech.vending.util;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MavenSourceRootDiscoverer {

    /**
     * Returns the assumed Maven source root directory path,
     * by taking the current working directory (project root)
     * and appending the standard source root (src/main/java).
     *
     * @return Path to the source root folder
     */
    public static Path getMavenSourceRoot() {
        // Get the current working directory (similar to `pwd`)
        String currentDir = System.getProperty("user.dir") + File.separator + "vend";

        // Build the path to src/main/java
        Path sourceRoot = Paths.get(currentDir, "src", "main", "java");

        return sourceRoot;
    }

    public static void main(String[] args) {
        Path sourceRoot = getMavenSourceRoot();
        System.out.println("Assumed Maven source root: " + sourceRoot.toAbsolutePath());

        // You can add additional logic to check if this directory exists or not
        if (!sourceRoot.toFile().exists()) {
            System.err.println("Warning: The assumed source root directory does not exist! " + sourceRoot);
        }
    }
}

