package com.example.haripriya.rss_reader.Common;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Haripriya on 16-03-2018.
 */

public class HTTPDataHandler {
    static String stream=null;

    public HTTPDataHandler() {
    }

    public String GetHTTPData(String urlString)
    {
        try{
            URL url=new URL(urlString);
            HttpURLConnection urlConnection=(HttpURLConnection)url.openConnection();
            if(urlConnection.getResponseCode()==HttpURLConnection.HTTP_OK)
            {
                InputStream in=new BufferedInputStream(urlConnection.getInputStream());

                BufferedReader bf=new BufferedReader(new InputStreamReader(in));
                StringBuilder sb=new StringBuilder();
                String line;
                while ((line=bf.readLine())!=null)
                {
                    sb.append(line);
                }
                stream=sb.toString();
                urlConnection.disconnect();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stream;
    }
}
