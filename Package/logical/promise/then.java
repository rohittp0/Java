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
public interface then
{

    /**
     * 
     * This function is called on the successful execution of the task.
     * 
     * @param arg
     * @return New promise
     */
    public Promise then(Object arg);    
}
