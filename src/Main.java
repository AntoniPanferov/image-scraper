public class Main {
    public static void main(String[] args) {
        String url = "";
        String output = "";

        for (int i = 0; i < args.length; i++) {
            switch (args[i]) {
                case "-i" -> url = args[i + 1];
                case "-o" -> output = args[i + 1];
            }
        }

        ImageScraper imageScraper = new ImageScraper(url);
        imageScraper.extractImages(output);
    }
}