package org.example;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

@SpringBootApplication
@RestController
public class ConfigClient {

    @Value("${user.role}")
    private String role;

    @Value("${user.password}")
    private String password;

    public static void main(String[] args) {
        SpringApplication.run(ConfigClient.class, args);
    }

    private String decryptPassword(String encryptedPwd) throws IOException {
        URL url = new URL("http://localhost:8888/decrypt");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");

        con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

        con.setDoOutput(true);
        OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream());
        writer.write(encryptedPwd);
        writer.flush();
        writer.close();
        con.getOutputStream().close();

        InputStream responseStream = con.getResponseCode() / 100 == 2
                ? con.getInputStream()
                : con.getErrorStream();
        Scanner s = new Scanner(responseStream).useDelimiter("\\A");
        String response = s.hasNext() ? s.next() : "";
        return response;
    }
    private void callAndGetPassword() {
        try {
            // password -> {cipher}44405462eff0ece3dd7d43869e11831a13271126ca3c2d1133d49adc10b4084d
            String decryptedPassword = decryptPassword(password.replace("{cipher}", ""));
            System.out.println("Decrypted pwd: " + decryptedPassword);
        }
        catch (Exception e) {
            System.out.println("Caught exception: " + e);
        }
    }
    @GetMapping(
        value = "/whoami/{username}",
        produces = MediaType.TEXT_PLAIN_VALUE)
    public String whoami(@PathVariable("username") String username) {
//        int maxKeySize = javax.crypto.Cipher.getMaxAllowedKeyLength("AES");
//        System.out.println("CryptoKeySize: " + maxKeySize);
        return String.format("Hello! Your 'username' is '%s' and you'll become a(n) '%s', your password decoded from file stored in git '%s'\n", username, role, password);
    }
}

//curl http://localhost:8080/whoami/usernameInputValue