package net.hrobotics.wb.api;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import net.hrobotics.wb.model.Word;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

import static net.hrobotics.wb.dao.DictionaryDAO.createDictionary;
import static net.hrobotics.wb.dao.WordDAO.createWords;
import java.util.logging.Logger;
@Api(name = "wb",
        version = "v1",
        namespace = @ApiNamespace(ownerDomain = "ge.hrobotics.net",
                ownerName = "ge.hrobotics.net"))
public class WordbookAPI {
    private static final Logger LOG = Logger.getLogger(WordbookAPI.class.getName());

    @ApiMethod(
            name = "dictionary.create",
            path = "dictionary",
            httpMethod = "POST")
    public List<Word> doCreateDictionary(DictionaryDocument dictionaryDocument) {
        LOG.info(ToStringBuilder.reflectionToString(dictionaryDocument));
        return createWords(
                createDictionary(dictionaryDocument.getDictionary()),
                    dictionaryDocument.getWords());
    }
}
