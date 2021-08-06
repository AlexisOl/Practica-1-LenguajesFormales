/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tipos;

import java.util.Stack;

/**
 *
 * @author alexis
 */
public class Ayuda {
     // metodo para la creacion de mensajes tipo String 
    public static String regresoString(Stack<Character> stack){
        String mensaje = "";
        for(char caracter : stack){
            mensaje = mensaje +caracter;
        }
        return mensaje;
    }
    
}
