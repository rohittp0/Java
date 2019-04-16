/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logical.promise;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author HP
 */
public class Promise
{

    /**
     * This promise indicates that the execution of the promise was complected.
     * The then of this promise returns "Execution Ended Successfully".
     */
    public static Promise DONE = new Promise()
    {
        @Override
        public String toString()
        {
            return "Execution Ended Successfully.";
        }

        @Override
        public Promise then(then Then)
        {
            Then.then(this.toString());
            return Promise.EMPTY;
        }
    };

    /**
     * This promise indicates that the execution of the promise failed.
     * The then of this promise returns "Execution Failed".
     */
    public static Promise FAILED = new Promise()
    {
        @Override
        public String toString()
        {
            return "Execution Failed.";
        }

        @Override
        public Promise then(then Then)
        {
            Then.then(this.toString());
            return Promise.EMPTY;
        }
    };

    /**
     * This is an empty promise.
     * That is there is no work assigned to it nor a status code.
     */
    public static Promise EMPTY = new Promise()
    {
        @Override
        public String toString()
        {
            return null;
        }

        @Override
        public Promise then(then Then)
        {
            Then.then(null);
            return this;
        }
    };

    private Thread worker;
    private then Then;
    private onError Error;
    private Promise next;
    private Runnable work;

    private Exception error;
    private String status = "Promise not yet defigned";

    private Promise()
    {

    }

    public Promise(task work)
    {
        this.work = () ->
        {
            Object returnValue;
            try
            {
                returnValue = work.task();
                status += " , Execution compleated @" + System.currentTimeMillis();
                if (returnValue == null) return;
                if (Then == null)
                    try
                    {
                        synchronized (worker)
                        {
                            status += " , Waiting for then function @" + System.currentTimeMillis();
                            worker.wait(6000);
                        }
                    } catch (InterruptedException ex)
                    {
                    }
                if (Then != null)
                {
                    Promise nextPromise = Then.then(returnValue);
                    if (next.Then == null) return;

                    if (nextPromise.equals(Promise.EMPTY)
                            || nextPromise.equals(Promise.DONE)
                            || nextPromise.equals(Promise.FAILED))
                        next.Then.then(nextPromise);

                    else
                    {
                        nextPromise.then(next.Then);
                        nextPromise.next = next.next;
                        nextPromise.onError(Error);
                        synchronized (nextPromise.worker)
                        {
                            nextPromise.worker.interrupt();
                        }

                    }
                }
                System.gc();
            } catch (Exception er)
            {
                status += " , Encountered an error -> " + er.toString() + " @" + System.currentTimeMillis();
                if (Error != null) Error.onError(er);
                else
                {
                    error = er;
                    try
                    {
                        synchronized (this)
                        {
                            wait();
                        }
                    } catch (InterruptedException ex)
                    {
                        Error.onError(er);
                    }
                }
            }

        };
        worker = new Thread(this.work);
        status = "Initalised @" + System.currentTimeMillis();
        start();
    }

    private void start()
    {
        worker.start();
        status += " , Started Running @" + System.currentTimeMillis();
    }

    public Promise then(then Then)
    {
        this.Then = Then;

        next = new Promise();
        next.Error = Error;

        if (worker != null) synchronized (worker)
            {
                worker.notify();
            }
        status += " , Then initalised @" + System.currentTimeMillis();
        return next;
    }

    public void onError(onError error)
    {
        this.Error = error;
        status += " , onError initalised @" + System.currentTimeMillis();
        if (this.error != null) error.onError(this.error);
    }

    @Override
    public String toString()
    {
        return "Promise Object (" + this.hashCode() + ") -> [ " + status + " ]";
    }
}
