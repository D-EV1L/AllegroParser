package pl.allegro;

import pl.allegro.site.Item;
import pl.allegro.site.Page;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Parser {

    private Parser() {
        Page[] pages = {
                new Page("elektronika", "cl-e2101-d3681-c3682-ele-1-1-0304"),
                new Page("zdrowie", "e2101-d3681-c3682-hea-1-1-0304"),
                new Page("motoryzacja", "cl-e2101-d3681-c3682-aut-1-1-0304")
        };

        writeToCSVFile(parsePages(Arrays.asList(pages)), "goods");
    }

    public static void main(String[] args) {
        new Parser();
    }

    private List<Item> parsePages(List<Page> pages) {
        List<Item> items = new ArrayList<>();
        pages.forEach(page -> {
            try {
                items.addAll(page.parseItems());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return items;
    }

    private void writeToCSVFile(List<Item> items, String filename) {
        File csvFile = Paths.get("src", "main", "resources", filename + ".csv").toFile();
        try (BufferedWriter bf = new BufferedWriter(new FileWriter(csvFile))) {
            bf.write("Product name;Old price, PLN;New price, PLNDiscount, %" + '\n');
            for (Item item : items) {
                bf.write(item.toString() + '\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}