/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tipos;

/**
 *
 * @author alexis
 */
public class Token {
    // objeto Token el cual tiene el token en cuestion y el mensaje de que es.
   private Tokens token;
   private String mensaje;

    public Token(Tokens token, String mensaje) {
        this.token = token;
        this.mensaje = mensaje;
    }

    public Tokens getToken() {
        return token;
    }

    public void setToken(Tokens token) {
        this.token = token;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
   
   
    
    
}
