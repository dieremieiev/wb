package net.hrobotics.wb.api.client;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.IOUtils;
import net.hrobotics.ge.wb.Wb;
import net.hrobotics.ge.wb.model.DictionaryDocument;
import org.yaml.snakeyaml.Yaml;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;

public class UploadDictionary {
    private static Yaml yaml = new Yaml();

    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws GeneralSecurityException, IOException {
        String rootUrl = args[0];
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        IOUtils.copy(new FileInputStream(args[1]), outputStream);
        String yamlContent = new String(outputStream.toByteArray());
        Wb wb = new Wb.Builder(GoogleNetHttpTransport.newTrustedTransport(),
                new JacksonFactory(), null)
                .setRootUrl(rootUrl)
                .setApplicationName(args[2])
                .build();
        DictionaryDocument dictionaryDocument = new DictionaryDocument();
        Map<String, Object> dictionaryData = (Map<String, Object>) yaml.load(yamlContent);
        Map<String, Object> dictionary = new HashMap<>();
        dictionary.put("caption", dictionaryData.get("caption"));
        dictionary.put("from", dictionaryData.get("from"));
        dictionary.put("name", dictionaryData.get("name"));
        dictionary.put("to", dictionaryData.get("to"));
        dictionary.put("version", dictionaryData.get("version"));
        dictionaryDocument.set("dictionary", dictionary);
        dictionaryDocument.set("words", dictionaryData.get("words"));
        Wb.Dictionary.Create create = wb.dictionary().create(dictionaryDocument);
        create.execute().getItems();
    }
}
