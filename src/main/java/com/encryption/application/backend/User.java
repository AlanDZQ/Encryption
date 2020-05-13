package com.encryption.application.backend;

public class User {
    private String key;
    private String IV;
    private String cipherText;
    private String padding;
    private String algorithm;

    public User(String key, String IV, String cipherText, String padding, String algorithm) {
        this.key = key;
        this.IV = IV;
        this.cipherText = cipherText;
        this.padding = padding;
        this.algorithm = algorithm;
    }
    public String getKey() {
        return key;
    }

    public String getIV() {
        return IV;
    }

    public String getCipherText() {
        return cipherText;
    }

    public String getPadding() {
        return padding;
    }

    public String getAlgorithm() {
        return algorithm;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public void setIV(String IV) {
        this.IV = IV;
    }

    public void setCipherText(String cipherText) {
        this.cipherText = cipherText;
    }

    public void setPadding(String padding) {
        this.padding = padding;
    }

    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }
}
