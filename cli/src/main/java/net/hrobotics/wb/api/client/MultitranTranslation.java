package net.hrobotics.wb.api.client;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;

public class MultitranTranslation {
    public static void main(String[] args) throws IOException {
        Document doc = Jsoup.parse(new URL("http://www.multitran.ru/c/m.exe?l1=24&l2=2&s=tot+ziens"), 10000);
        Elements rows = doc.select("a[href*=s1=tot%20ziens]");
        for (Element row : rows) {
            System.out.println(row.text());
        }

    }
}
