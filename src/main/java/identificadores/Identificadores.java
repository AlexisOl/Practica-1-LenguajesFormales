/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package identificadores;

import tipos.*;
import tipos.Token;
import java.util.ArrayList;
import java.util.Stack;

/**
 *
 * @author alexis
 */
public class Identificadores {
   
    // utilize la opcion de Stack para que al momento de trabajar todo esto como "estados", asi como un automata
    // ya que esta funcion me ayuda a manejar la informacion como una pila
    // posteriormente el uso de un arraylist para guardar todo.
    public static Stack<Character> token_Actual = new Stack<>();
    private static ArrayList<Token> tokens; 
    private static Mensaje_EstadosTokens actualEstado = Mensaje_EstadosTokens.EstadoEspacio;
    
    
    
    // metodo que practicamente es un Switch para ver los tipos de tokens que hay y regresa el token que necesito.
    
    public static Token[] Ver_Token(char[] linea){
    
        tokens = new ArrayList<>();
        for (char actual : linea){
            switch(actualEstado){
            case EstadoEspacio:
                estadoVacio(actual);
                break;
            case Estadodigito:
                estadoDigito(actual);
                break;
            case Estadoid:
                estadoID(actual);
                break;
            case EstadoSimbolo:
                estadoSimbolo(actual);
                break;
            case   EstadoDecimal  :
                estadoDecimal(actual);
                break;
                
            case EstadoClaseNumero:
                estadoClaseDeNumero(actual);
                break;
                
            case EstadoError:
                estadoError(actual);
                break;
            
        }
    
        
        
    }
        return tokens.toArray(new Token[0]);    
    }
        
    // primer metodo cuando es un espacio vacio, comprueba que la cadena tiene un espacio vacio,
    // posteriormente verifica las otras opciones, si tiene letras y numeros,
    // depsues si tiene un simbolo y sino pues es un error.
    // Este va a ser el metodo pivote el cual me va a servir para determinar los demas tokens.
    
    static void estadoVacio(char actual){
        if (Character.isWhitespace(actual)){
        } else {
            
            if (Character.isLetter(actual)){
                actualEstado=Mensaje_EstadosTokens.Estadoid;
                 token_Actual.push(actual);
            } else if (Character.isDigit(actual)){
                actualEstado=Mensaje_EstadosTokens.Estadodigito;
                token_Actual.push(actual);
            } else if (Simbolo.simbolo(actual)==true){
                actualEstado=Mensaje_EstadosTokens.EstadoSimbolo;
                token_Actual.push(actual);
                tokens.add(new Token(Tokens.SIMBOLO, regresoString(token_Actual)));
                token_Actual.clear();
            } else {
                  actualEstado=Mensaje_EstadosTokens.EstadoError;
                token_Actual.push(actual); 
            }
        }
    }
    // Segundo metodo el cual verfica si es un ID, el cual primero revisa si tiene un espacio vacio, para regresar al pivote
    // de no ser asi se verfica si tiene letras o numeros, de no ser asi, revisas los simbolos 
    
     static void estadoID(char actual){
        if (Character.isWhitespace(actual)){
            actualEstado=Mensaje_EstadosTokens.EstadoEspacio;
                 token_Actual.push(actual);
        } else {
            if (Character.isLetter(actual)||Character.isDigit(actual)){
                 token_Actual.push(actual); 
            } else if (Simbolo.simbolo(actual)==true) {
                actualEstado=Mensaje_EstadosTokens.EstadoSimbolo;
                tokens.add(new Token(Tokens.ID, regresoString(token_Actual)));
                token_Actual.clear();
                token_Actual.push(actual);
                tokens.add(new Token(Tokens.SIMBOLO, regresoString(token_Actual)));
                token_Actual.clear();
                
            } else {
                    actualEstado=Mensaje_EstadosTokens.EstadoError;
                token_Actual.push(actual); 
            }
        }
    }
     
     // Metodo para determinar si es un digito, determinando si es asi si se encuantra con un espacio en blanco
     // sino si es digito continua, si tiene un punto, cambia de estado a decimal, si es un simbolo solo determina 
     // que es entero y prosigue con que es simbolo, sino es un error.
    
    static void estadoDigito(char actual){
       if (Character.isWhitespace(actual)){
            actualEstado=Mensaje_EstadosTokens.EstadoEspacio;
            tokens.add(new Token(Tokens.ENTERO, regresoString(token_Actual)));
                 token_Actual.clear();

        } else {
           // tipos de numeros enteros o decimales
           if (Character.isDigit(actual)){
                 token_Actual.push(actual); 
                 
           } else if (actual == '.'){
               actualEstado=Mensaje_EstadosTokens.EstadoDecimal;
               token_Actual.push(actual); 
           } else if (Simbolo.simbolo(actual)==true){
                actualEstado=Mensaje_EstadosTokens.EstadoSimbolo;
                tokens.add(new Token(Tokens.ENTERO, regresoString(token_Actual)));
                 token_Actual.clear();
                 token_Actual.push(actual); 
                
                tokens.add(new Token(Tokens.SIMBOLO, regresoString(token_Actual)));
                token_Actual.clear();
           } else {
               actualEstado=Mensaje_EstadosTokens.EstadoError;
                token_Actual.push(actual); 
           }
           
           
       }  
    }
    
    // Aqui determina si la cadena es decimal, en caso lo sea lo envia a ver si esta prosigue o es error
   static void estadoDecimal(char actual){
     if (Character.isWhitespace(actual)){
            actualEstado=Mensaje_EstadosTokens.EstadoEspacio;
            tokens.add(new Token(Tokens.ERROR, regresoString(token_Actual)));
                 token_Actual.clear();

        } else {
            if (Character.isDigit(actual)){
                actualEstado = Mensaje_EstadosTokens.EstadoClaseNumero;
                token_Actual.push(actual); 
            } else {
                actualEstado=Mensaje_EstadosTokens.EstadoError;
                token_Actual.push(actual); 
            }
        }
    
    // estado definitivo para comprobar numeros, en este caso comprueba si termina la cadena de numero determinando si es decimal
    // si sigue lo determina como decimal, sino como simbolo
    }static void estadoClaseDeNumero(char actual){
    if (Character.isWhitespace(actual)){
         actualEstado=Mensaje_EstadosTokens.EstadoEspacio;
            tokens.add(new Token(Tokens.DECIMAL, regresoString(token_Actual)));
                 token_Actual.clear();
    } else {
        if (Character.isDigit(actual)){
            
            actualEstado = Mensaje_EstadosTokens.EstadoClaseNumero;
                token_Actual.push(actual); 
        } else if (Simbolo.simbolo(actual)==true){
            actualEstado = Mensaje_EstadosTokens.EstadoDecimal;
            tokens.add(new Token(Tokens.DECIMAL, regresoString(token_Actual)));
            token_Actual.clear();
            token_Actual.push(actual);
            tokens.add(new Token(Tokens.SIMBOLO, regresoString(token_Actual)));
            token_Actual.clear();
        } else {
             actualEstado=Mensaje_EstadosTokens.EstadoError;
                token_Actual.push(actual); 
        }
    }
    
    
    }
      // Metodo de error el cual verifica si este primero no es un espacio en vacio, cuando lo sea lo marca como error.
    // si es simbolo lo marca como error y despues lo marca como simbolo.
    
    static void estadoError(char actual){
        if (Character.isWhitespace(actual)){
             actualEstado=Mensaje_EstadosTokens.EstadoEspacio;
            tokens.add(new Token(Tokens.ERROR, regresoString(token_Actual)));
                 token_Actual.clear();
        } else {
            if (Simbolo.simbolo(actual)==true){
                actualEstado = Mensaje_EstadosTokens.EstadoSimbolo;
                 tokens.add(new Token(Tokens.ERROR, regresoString(token_Actual)));
                 token_Actual.clear();
                 token_Actual.push(actual);
                 tokens.add(new Token(Tokens.SIMBOLO, regresoString(token_Actual)));
                 token_Actual.clear();
            } else {
                token_Actual.push(actual);
            } 
        }
    }
    // metodo para simbolos, aqui revisa si no es un espacio en blanco, sino revisa las demas opciones, en caso si sea simbolo
    // marca el token como uno.
    static void estadoSimbolo(char actual){
        if (Character.isWhitespace(actual)){
            actualEstado = Mensaje_EstadosTokens.EstadoEspacio;
        } else {
            if (Character.isLetter(actual)){
                actualEstado = Mensaje_EstadosTokens.Estadoid;
                token_Actual.push(actual);
            } else if (Character.isDigit(actual)){
                actualEstado = Mensaje_EstadosTokens.Estadodigito;
                token_Actual.push(actual);
            } else if (Simbolo.simbolo(actual)==true){
                token_Actual.push(actual);
                tokens.add(new Token(Tokens.SIMBOLO, regresoString(token_Actual)));
                
                token_Actual.clear();
            } else {
                 actualEstado=Mensaje_EstadosTokens.EstadoError;
                token_Actual.push(actual); 
            }
        }
 
    }
    
    
  
    
    
    // metodo para la creacion de mensajes tipo String 
   public  static String regresoString(Stack<Character> stack){
        String mensaje = "";
        for(char caracter : stack){
            mensaje = mensaje +caracter;
        }
        return mensaje;
    } 
}
