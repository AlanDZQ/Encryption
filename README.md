# Simple Encryption System

This is a Spring-Boot project with Maven and Vaadin.
Requirement: Java 8+, Node.js 10+ and MongoDB 3+.

To run from the command line, use `mvn` and open [http://localhost:8080](http://localhost:8080) in your browser.

## Project structure

- `MainView.java` in `src/main/java` contains the navigation setup. It uses [App Layout](https://vaadin.com/components/vaadin-app-layout).
- `views` package in `src/main/java` contains the server-side Java views of your application.
- `views` folder in `frontend/src/` contains the client-side JavaScript views of your application.
- `crypto` folder in `src/main/java` contains the encrypt interface and API.
- `backend` folder in `src/main/java` contains the java object class and database connection API.

## How to use it?

User can input the original text data in the "Cipher Text" field and the 16 bytes encryption key and IV, with the option to randomly generate them. 
- Button "Generate Random Key & IV" is for randomly generate Key and IV. 
- Button "Encrypt" is for encrypting the original data with AES encryption algorithm + CBC/CTR block cipher algorithm + PKCS5Padding/ISO10126Padding/NoPadding padding method
- Button "Save" is saving the information into the local mongoDB with a DB name: "Encryption" and a collection name "Encryption". It will return the id of insertion for the future query of data.
- use `use Encryption` + `db.Encryption.find({})` in MongoDB to check the result

## Precautions

- The length of input text should be multiple of 16 bytes when doing NoPadding with CBC block cipher algorithm
- CTR block cipher algorithm cannot be used with ISO10126Padding
