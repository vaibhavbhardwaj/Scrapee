package com.example.vaibhav.scrapee;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by vaibhav on 7/28/2015.
 */
public class FetchPage extends AsyncTask<Void, Void, Void> {

    private Context mContext;

    private TextView view;
    private static String movie;

    public FetchPage (Context c, TextView view){
        this.view= view;
        mContext=c;
    }

    @Override
    protected Void doInBackground(Void... params) {

        String movieDataLeft;
        String movieDataRight="";

        try {
            Document doc = Jsoup.connect("http://google.com/movies?near=Hyderabad&q=Baahubali+The+Beginning").get();

            Element mastLeft = doc.select("div.show_left").first();
            Element movieResultRight = doc.select("div.show_right").first();

            Elements movieNamesLeft= mastLeft.select("div.theater");
            Elements movieNamesRight= movieResultRight.select("div.theater");

            movieDataLeft="";


            for (Element name: movieNamesLeft){
                Element movieTag=name.getElementsByTag("a").first();
                String movieLinkHref = movieTag.attr("href");
                String movieLinkText = movieTag.text();
                Element movieClass= name.select("div.times").first();
                String time= movieClass.text();

                movieDataLeft=movieDataLeft+"\n"+movieLinkText+"\n" + time;


            }

            for (Element name: movieNamesRight){
                Element movieTag=name.getElementsByTag("a").first();
                String movieLinkHref = movieTag.attr("href");
                String movieLinkText = movieTag.text();

                Element movieClass= name.select("div.times").first();
                String time= movieClass.text();

                movieDataRight=movieDataRight+"\n"+ movieLinkText+"\n" + time;



            }
            movie=movieDataRight+movieDataLeft;
            System.out.println(movie);


        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void result) {

       view.setText(movie);



    }
}
