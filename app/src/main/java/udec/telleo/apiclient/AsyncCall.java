package udec.telleo.apiclient;

public interface AsyncCall<TResult> {
    void onSuccess(TResult res);
    void onFailure(Throwable err);
}
