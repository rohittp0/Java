/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logical.promise;

/**
 *
 * @author Rohit
 */
public interface task
{

    /**
     * This function is executed asynchronously and on successful execution then 
     * function is invoked.
     * @return some result
     * @throws java.lang.Exception No need to catch.
     */
    public Object task() throws Exception;
}
