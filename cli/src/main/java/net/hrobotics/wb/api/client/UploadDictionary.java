package net.hrobotics.wb.api.client;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.IOUtils;
import net.hrobotics.wb.admin.Admin;
import net.hrobotics.wb.admin.model.DictionaryDocumentDTO;
import net.hrobotics.wb.admin.model.Word;
import net.hrobotics.wb.admin.model.Level;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class UploadDictionary {
    private static Yaml yaml = new Yaml();

    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws GeneralSecurityException, IOException {
        String rootUrl = args[0];
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        IOUtils.copy(new FileInputStream(args[1]), outputStream);
        String yamlContent = new String(outputStream.toByteArray());
        String applicationName = args[2];
        Admin admin = buildAdmin(rootUrl, applicationName);
        Map<String, Object> dictionaryData = (Map<String, Object>) yaml.load(yamlContent);
        DictionaryDocumentDTO dictionaryDocument = toDictionaryDocument(dictionaryData);
        Admin.Dictionary.Put method = admin.dictionary().put(dictionaryDocument);
        DictionaryDocumentDTO result = method.execute();
        IOUtils.copy(new ByteArrayInputStream(yaml.dump(result).getBytes()),
                new FileOutputStream(args[3]));
    }

    private static Admin buildAdmin(String rootUrl, String applicationName)
            throws IOException, GeneralSecurityException
    {
        return new Admin.Builder(GoogleNetHttpTransport.newTrustedTransport(),
                new JacksonFactory(),
                GoogleCredential.fromStream(UploadDictionary.class.getResourceAsStream("/wb19032016-504586d76f17.json"))
                        .createScoped(Collections.singleton("https://www.googleapis.com/auth/userinfo.email")))
                .setRootUrl(rootUrl)
                .setApplicationName(applicationName)
                .build();
    }

    @SuppressWarnings("unchecked")
    private static DictionaryDocumentDTO toDictionaryDocument(Map<String, Object> dictionaryDocumentData) {
        DictionaryDocumentDTO dictionaryDocument = new DictionaryDocumentDTO();
        net.hrobotics.wb.admin.model.Dictionary dictionary = new net.hrobotics.wb.admin.model.Dictionary();
        Map<String, Object> dictionaryData = (Map<String, Object>) dictionaryDocumentData.get("dictionary");
        dictionary.setId((String) dictionaryData.get("id"));
        dictionary.setCaption((String) dictionaryData.get("caption"));
        dictionary.setFrom((String) dictionaryData.get("from"));
        dictionary.setName((String) dictionaryData.get("name"));
        dictionary.setTo((String) dictionaryData.get("to"));
        dictionary.setVersion((String) dictionaryData.get("version"));
        dictionary.setLastLevel((Integer) dictionaryData.get("lastLevel"));
        dictionaryDocument.setDictionary(dictionary);
        List<Word> words = new ArrayList<>();
        for (Map<String, Object> wordData : (List<Map<String, Object>>) dictionaryDocumentData.get("words")) {
            Word word = new Word();
            word.setId((String) wordData.get("id"));
            word.setDictionaryId((String) wordData.get("dictionaryId"));
            word.setSpelling((String) wordData.get("spelling"));
            word.setTip((String) wordData.get("tip"));
            word.setTranslation((String) wordData.get("translation"));
            words.add(word);
        }
        dictionaryDocument.setWords(words);
        dictionary.setNumber(words.size());
        List<Map<String, Object>> levelsData = (List<Map<String, Object>>) dictionaryDocumentData.get("levels");
        List<Level> levels = new ArrayList<>();
        for (Map<String, Object> levelData : levelsData) {
            Level level = new Level();
            level.setDictionaryId((String) levelData.get("dictionaryId"));
            level.setDelay((Integer) levelData.get("delay"));
            level.setLevel((Integer) levelData.get("level"));
            levels.add(level);
        }
        dictionaryDocument.setLevels(levels);
        return dictionaryDocument;
    }
}
