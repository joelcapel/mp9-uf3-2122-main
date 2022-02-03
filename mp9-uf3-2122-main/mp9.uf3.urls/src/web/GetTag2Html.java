package web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetTag2Html {

    public static void main(String[] args) throws IOException {
        String tag;
        if (args.length != 2){
            System.out.println("Error en els arguments.");
            System.out.println("Execució: java web.GetTag2Html URL tag");
            System.exit(0);
        } else {
            URL url = new URL(args[0]);
            tag = args[1];

            String pattern = "<" + tag + ".*\\/?>";
            Pattern p = Pattern.compile(pattern);

            HttpURLConnection con = (HttpURLConnection) url.openConnection();

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String res;
            while ((res = in.readLine()) != null){
                Matcher m = p.matcher(res);
                if (m.find()){
                    int inici = m.start();
                    System.out.println(res.substring(inici));
                }
            }
            in.close();

            System.out.println("TODO");
        }
    }
}

