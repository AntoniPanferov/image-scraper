import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

public class ImageScraper {
    private Document document;

    public ImageScraper(String url) {
        try {
            document = Jsoup.connect(url).get();
            System.out.println("HTML scraped from: " + url);
        } catch (IOException e) {
            System.out.println("Failed to scrap HTML.");
            throw new RuntimeException(e);
        }
    }

    public void extractImages(String output) {
        Elements images = document.select("img");

        for (Element image : images) {
            String source = image.attr("src");
            String name = image.attr("alt");
            System.out.println(source);

            String newSource = source.split("/revision/latest/scale-to-width-down/")[0];

            if (source.contains("https://static.wikia.nocookie.net/crueltysquad")) {
                try (InputStream input = new URL(newSource).openStream()) {
                    Files.copy(input, Path.of(output + "/" + name + ".png"));
                    System.out.println("Image saved: " + name);
                } catch (IOException e) {
                    System.out.println("Failed to download Image under: " + newSource);
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
