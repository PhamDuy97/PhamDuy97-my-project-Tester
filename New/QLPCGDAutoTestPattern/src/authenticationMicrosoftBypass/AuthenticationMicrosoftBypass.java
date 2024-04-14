package authenticationMicrosoftBypass;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AuthenticationMicrosoftBypass {

	private final static String COOKIES = "COOKIES";
	private final static String USERNAME = "USERNAME";
	private final static String PASSWORD = "PASSWORD";
	private final static String CONFIG_PROPERTIES_FILE = "config.properties";
	
	public String getCookiesFromPropertiesFile() {
		try {
			Properties properties = new Properties();
			InputStream accessConfigFile = new FileInputStream(CONFIG_PROPERTIES_FILE);
			properties.load(accessConfigFile);
			
			
			String cookiesProperties = properties.getProperty(COOKIES);
			
			accessConfigFile.close();
			return cookiesProperties;
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void setCookiesToPropertiesFile(String cookies) {
		try {
			String[] usernamePassword = getUsernameAndPasswordFromPropertiesFile();
			String username = usernamePassword[0];
			String password = usernamePassword[1];
			
			Properties properties = new Properties();
			properties.setProperty(COOKIES, cookies);
			properties.setProperty(USERNAME, username);
			properties.setProperty(PASSWORD, password);
			
			String tempConfigFileName = "temp_config.properties";
			FileOutputStream tempConfigPropertiesFile = new FileOutputStream(tempConfigFileName);
			
			properties.store(tempConfigPropertiesFile, null);
			
			tempConfigPropertiesFile.close();
			
			copyFile(tempConfigFileName, CONFIG_PROPERTIES_FILE);
			
			File tempFile = new File(tempConfigFileName);
			tempFile.delete();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String[] getUsernameAndPasswordFromPropertiesFile() {
		try {
			Properties properties = new Properties();
			InputStream accessConfigFile = new FileInputStream(CONFIG_PROPERTIES_FILE);
			properties.load(accessConfigFile);
			
			String username = properties.getProperty(USERNAME);
			String password = properties.getProperty(PASSWORD);
			
			accessConfigFile.close();
			return new String[] { username, password };
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	private void copyFile(String fromFileName, String toFileName) throws IOException {
		FileInputStream fileInputStream = new FileInputStream(fromFileName);
		FileOutputStream fileOutputStream = new FileOutputStream(toFileName);
		byte[] buffer = new byte[1024];
		int length;
		
		while ((length = fileInputStream.read(buffer)) > 0) {
			fileOutputStream.write(buffer, 0, length);
		}
		
		fileInputStream.close();
		fileOutputStream.close();
	}

}
