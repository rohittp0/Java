/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logical.promise;

/**
 *
 * @author HP
 */
public interface onError
{

    /**
     * This function is called if an error occurs during the execution of the 
     * task.
     * @param error
     */
    public void onError(Exception error);
}
