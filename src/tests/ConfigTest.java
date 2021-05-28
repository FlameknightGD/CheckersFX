package tests;

import application.utils.Config;

public class ConfigTest {
    public static void main(String[] args) {
        Config config = new Config("config.txt"); // Creates instance of Config class which is an extension of the HashMap class
        config.read(); // reads the config file to HashMap
        config.put("volume", "0.04545454545454544"); // set value of key
        System.out.println(config.get("volume")); // get value of key
        config.write(); // writes HashMap to config file
    }
}
