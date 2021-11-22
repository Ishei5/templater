package com.pankov.roadtosenior.templater;

public final class Configuration {

    private static Configuration configuration;
    private String pathToDirectory;

    public void setPathToDirectory(String pathToDirectory) {
        this.pathToDirectory = pathToDirectory;
    }

    private Configuration() {
    }

    public String getPathToDirectory() {
        return pathToDirectory;
    }

    public static Configuration getConfiguration() {
        if (configuration == null) {
            configuration = new Configuration();
        }

        return configuration;
    }
}
