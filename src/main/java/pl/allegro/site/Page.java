package pl.allegro.site;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Page {

    private final String URL;

    public Page(String category, String bmatch) {
        URL = "https://allegro.pl/kategoria/" +
                category +
                "?string=bargain_zone&bmatch=" +
                bmatch;
    }

    public List<Item> parseItems() throws IOException {
        List<Item> items = new ArrayList<>();
        int pageNumber = 1;

        while (items.size() < 100 && pageNumber < 100) { // additional condition to avoid StackOverflowException
            Document page = Jsoup.parse(new URL(URL + "&p=" + pageNumber), 5000);
            Elements elements = page.select("div.opbox-listing article > div > div[class]");

            for (Element element : elements) {
                if (items.size() == 100) return items;
                String oldPrice = element.select("div[aria-label]").select("span:nth-child(2)").text();
                if (oldPrice.contains("zł")) {
                    String name = element.select("h2").select("a").text();
                    String newPrice = element.select("span[aria-label]").attr("aria-label");
                    items.add(new Item(name, parsePrice(oldPrice, 3), parsePrice(newPrice, 17)));
                }
            }
            pageNumber++;
        }
        return items;
    }

    private double parsePrice(String price, int charsToCut) {
        price = price
                .substring(0, price.length() - charsToCut)
                .replaceAll(",", ".")
                .replaceAll("\\s", "");
        return Double.parseDouble(price);
    }
}
