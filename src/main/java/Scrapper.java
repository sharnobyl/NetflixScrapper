
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;


import java.io.IOException;
import java.util.List;

public class Scrapper {

    private static final String baseUrl = "https://www.comparetv.com.au/streaming-search-library/?ctvcp=1770";
    public static void main(String[] args) {
        WebClient client = new WebClient();
        client.getOptions().setJavaScriptEnabled(false);
        client.getOptions().setCssEnabled(false);
        client.getOptions().setUseInsecureSSL(true);

        try {
            HtmlPage page = client.getPage(baseUrl);
            List<HtmlElement> items = page.getByXPath("//div[@class='search-content-item']");
            if(items.isEmpty()){
                System.out.println("No items found");
            }else{
                for(HtmlElement htmlItem: items){
                    HtmlAnchor itemAnchor = (HtmlAnchor) htmlItem.getFirstByXPath("/a");
                    HtmlElement showName = (HtmlElement) htmlItem.getFirstByXPath("/a/p");

                    Item item = new Item();
                    item.setTitle(itemAnchor.asNormalizedText());
                    item.setUrl(itemAnchor.getHrefAttribute());

                    ObjectMapper mapper = new ObjectMapper();


                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
