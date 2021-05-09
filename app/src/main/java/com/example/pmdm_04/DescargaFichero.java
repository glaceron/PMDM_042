package com.example.pmdm_04;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.jetbrains.annotations.NotNull;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class DescargaFichero extends Service
{
    private final String urlString = "https://raw.githubusercontent.com/glaceron/ficheros/main/imagenes.txt";
    private Main2 main2 = new Main2();

    @Override
    public void onCreate()
    {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        URL url = null;
        try
        {
            url = new URL(urlString);
            descargaOkHTTP(url);
        } catch (MalformedURLException e)
        {
            e.printStackTrace();
            Toast.makeText(main2, "Error al intentar descargar desde: " + urlString, Toast.LENGTH_SHORT).show();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void descargaOkHTTP(URL web)
    {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(web).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                main2.writeFileOnInternalStorage(urlString, e.toString());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                try (ResponseBody responseBody = response.body()) {
                    if (response.isSuccessful()) {
                        String myResponse = response.body().string();
                        enviarRespuesta(myResponse);
                    }
                }
            }
        });
    }

    private void enviarRespuesta (String mensaje) {
        Intent i = new Intent();
        i.setAction(Main2.RESPUESTA);
        i.addCategory(Intent.CATEGORY_DEFAULT);
        i.putExtra("response", mensaje);
        sendBroadcast(i);
    }
}
