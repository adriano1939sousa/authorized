package br.com.adrianosousa.authorized.authorization;

public record Authorization(
        String message) {

    public boolean isAuthorized(){
        return message.equals("Autorizado");
    }
}
