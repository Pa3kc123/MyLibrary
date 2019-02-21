package sk.pa3kc.mylibrary.async;

public class AsyncResult
{
    private Throwable exception;

    public AsyncResult()
    {
        this.exception = null;
    }

    public Throwable getException() { return this.exception; }

    public void setException(Throwable value) { this.exception = value; }

    public boolean exceptionOccured() { return this.exception != null; }

    @Override
    public boolean equals(Object obj) { return obj.getClass() == Boolean.class ? this.exceptionOccured() == false : super.equals(obj); }

    @Override
    public String toString() { return String.valueOf(this.exception == null); }
}
