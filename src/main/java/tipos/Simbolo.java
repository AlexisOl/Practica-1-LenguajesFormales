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
public class Simbolo {
    // metodo para ver si hay un simbolo 
    
   public static boolean simbolo(char actual){
        if ((actual=='{')||(actual =='}')||(actual=='[')||(actual ==']') || (actual==';')||(actual==',')){
            return true;
        } else {
            return false;
        }
    }
}
