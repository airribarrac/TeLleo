package udec.telleo.apiclient;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.toolbox.Volley;

public class RequestQueue {
    private static RequestQueue mInstance;
    private com.android.volley.RequestQueue mRequestQueue;
    private static Context mContext;

    private RequestQueue(Context context){
        // Specify the application context
        mContext = context;
        // Get the request queue
        mRequestQueue = getRequestQueue();
    }

    public static synchronized RequestQueue getInstance(Context context){
        // If Instance is null then initialize new Instance
        if(mInstance == null){
            mInstance = new RequestQueue(context);

        }
        // Return MySingleton new Instance
        return mInstance;
    }

    public com.android.volley.RequestQueue getRequestQueue(){
        // If RequestQueue is null the initialize new RequestQueue
        if(mRequestQueue == null){
            mRequestQueue = Volley.newRequestQueue(mContext.getApplicationContext());
        }
        // Return RequestQueue
        return mRequestQueue;
    }

    public<T> void addToRequestQueue(Request<T> request){
        // Add the specified request to the request queue
        getRequestQueue().add(request);
    }
}