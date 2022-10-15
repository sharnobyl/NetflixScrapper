
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;


import java.io.IOException;
import java.util.List;

public class Scrapper {

    private static final String baseUrl = "https://www.comparetv.com.au/streaming-search-library/?ctvcp=1770";
    public static void main(String[] args) {
        WebClient client = new WebClient(BrowserVersion.CHROME);
        client.getOptions().setJavaScriptEnabled(false);
        client.getOptions().setCssEnabled(false);
        client.getOptions().setUseInsecureSSL(true);
        client.getCurrentWindow().getJobManager().removeAllJobs();

        try {
            HtmlPage page = client.getPage(baseUrl);

            List<HtmlElement> items = page.getByXPath("//div[@class='search-content-item']");
            if(items.isEmpty()){
                System.out.println("No items found");
            }else{
                for(HtmlElement htmlItem: items){
                    String url = htmlItem.getFirstChild().getAttributes().getNamedItem("href").getTextContent();
                    String temp = htmlItem.asNormalizedText();
                    int iend = temp.indexOf("\n");
                    StringBuilder title = new StringBuilder();
                    for(int i = 0; i < iend; i++){
                        title.append(temp.charAt(i));
                    }
                    Item item = new Item();
                    item.setTitle(title.toString());
                    item.setUrl(url);


                    ObjectMapper mapper = new ObjectMapper();
                    String jsonString = mapper.writeValueAsString(item);

                    System.out.println(jsonString);
                    client.close();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
