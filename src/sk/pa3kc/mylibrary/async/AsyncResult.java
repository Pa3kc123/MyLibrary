package sk.pa3kc.mylibrary.async;

public class AsyncResult
{
    private Throwable exception;
    private Class<?> exceptionType;

    public AsyncResult()
    {
        this.exception = null;
        this.exceptionType = null;
    }

    public Throwable getException() { return this.exception; }
    public Class<?> getExceptionType() { return this.exceptionType; }

    public void setException(Throwable value) { this.exception = value; }
    public void setExceptionType(Class<?> value) { this.exceptionType = value; }

    public boolean exceptionOccured() { return this.exception != null; }

    @Override
    public boolean equals(Object obj) {
        return obj.getClass() == Boolean.class ? this.exceptionOccured() == false : super.equals(obj);
    }
}
