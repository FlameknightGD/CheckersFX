package application.utils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;

public class ConfigHandler {
	File configFile;

	public ConfigHandler(File configFile) {
		setConfigFile(configFile);
	}

	public void writeConfig(HashMap<String, String> configValues) throws IOException {
		StringBuffer stringBuffer = new StringBuffer();
		
		for(String key : configValues.keySet())
		{
			stringBuffer.append(key + "=" + configValues.get(key) + "\n");
		}
		
		Files.write(configFile.toPath(), stringBuffer.toString().getBytes());
	}
	
	public double getVolumeFromConfig(File configFile) throws IOException {
		String fileContent = new String(Files.readAllBytes(configFile.toPath()), StandardCharsets.UTF_8);
		
		String[] lineArray = new String[2];
		int i = 0;
		
        for (String line : fileContent.split("=")) {
        	lineArray[i] = line;
        	i++;
        }
        
        return Double.parseDouble(lineArray[1]);
	}

	// Setters
	public void setConfigFile(File configFile) {
		this.configFile = configFile;
	}
}
