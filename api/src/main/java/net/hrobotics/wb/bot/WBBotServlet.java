package net.hrobotics.wb.bot;

import com.google.appengine.api.urlfetch.*;
import com.google.appengine.repackaged.org.codehaus.jackson.map.ObjectMapper;
import net.hrobotics.wb.bot.telegram.model.Message;
import net.hrobotics.wb.bot.telegram.model.SendMessage;
import net.hrobotics.wb.bot.telegram.model.Update;
import net.hrobotics.wb.dao.DictionaryDAO;
import net.hrobotics.wb.dao.WordDAO;
import net.hrobotics.wb.model.Dictionary;
import net.hrobotics.wb.model.Word;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.logging.Logger;

public class WBBotServlet extends HttpServlet {
    private static final Logger LOG = Logger.getLogger(WBBotServlet.class.getName());
    private static final String BOT_KEY = "234230391:AAH7THfX9vUn4tDDtlHP2r-Ya2x0qEf960Y";
    private static URLFetchService urlFetchService = URLFetchServiceFactory.getURLFetchService();

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Message message = mapper.readValue(req.getInputStream(), Update.class).getMessage();

            String text = message.getText();
            LOG.info("message: " + text);
            String username = message.getFrom().getUsername();
            LOG.info("username: " + username);

            String response = handle(text, username);

            if("".equals(response)) {
                response = "нет ответа!";
            }

            LOG.info(new String(
                    httpCall(
                            new URL("https://api.telegram.org/bot" + BOT_KEY + "/sendMessage"),
                            mapper.writeValueAsBytes(new SendMessage(message.getChat().getId(), response)),
                            HTTPMethod.POST,
                            new HTTPHeader("Content-Type", "application/json"))));
        } catch (Exception e) {
            LOG.severe("error: " + e.getMessage());
        }
    }

    private byte[] httpCall(URL url,
                            byte[] payload,
                            HTTPMethod post,
                            HTTPHeader... headers) throws IOException {
        FetchOptions fetchOptions = getFetchOptions();
        LOG.info(new String(payload));
        HTTPRequest request = new HTTPRequest(url, post, fetchOptions);
        if (headers != null && headers.length > 0) {
            for (HTTPHeader header : headers) {
                request.addHeader(header);
            }

        }
        if (payload.length > 0) {
            request.setPayload(payload);
        }
        return urlFetchService.fetch(request).getContent();
    }

    private FetchOptions getFetchOptions() {
        FetchOptions fetchOptions = FetchOptions.Builder.withDefaults();
        fetchOptions.doNotValidateCertificate();
        fetchOptions.doNotFollowRedirects();////!!!!!!!!!
        fetchOptions.setDeadline(30D);
        return fetchOptions;
    }

    private String handle(String text, String username) throws IOException {
        String translation;
        if(username != null && !"".equals(username)) {
            String wordbookId = wordbookId(username);
            Word word = WordDAO.getWordBySpelling(wordbookId, text);
            if(word != null) {
                translation = word.getTranslation();
            } else {
                translation = translateMultitran(text);
                WordDAO.putWord(
                        new Dictionary(wordbookId),
                        new Word(text, translation));
            }
        } else {
            translation = translateMultitran(text);
        }
        return translation;
    }

    private String wordbookId(String username) {
        String wordbookId = null;
        List<Dictionary> dictionaries = DictionaryDAO.list();
        for (Dictionary dictionary : dictionaries) {
            if(wordbookName(username).equals(dictionary.getName())) {
                wordbookId = dictionary.getId();
            }
        }
        if(wordbookId == null) {
            Dictionary dictionary = DictionaryDAO.putDictionary(new Dictionary(null,
                    wordbookName(username),
                    "Wordbook",
                    "ne",
                    "ru",
                    "1",
                    1,
                    3));
            wordbookId = dictionary.getId();
        }
        return wordbookId;
    }

    private String wordbookName(String username) {
        return "Wordbook_" + username;
    }

    private String translateMultitran(String text) throws IOException {
        String textEncoded = URLEncoder.encode(text, "UTF-8");
        String url = "http://www.multitran.ru/c/m.exe?l1=24&l2=2&s=" + textEncoded;
        Document doc = Jsoup.parse(new String(
                httpCall(new URL(url), new byte[0], HTTPMethod.GET), "windows-1251"));
        Elements rows = doc.select("a[href*=s1=" + textEncoded + "]");
        StringBuilder builder = new StringBuilder();
        for (Element row : rows) {
            builder.append(row.text()).append("\n");
        }
        return builder.toString();
    }
}
