package custom;

import models.Deal;
import models.User;
import play.Logger;
import play.data.format.Formatters;
import play.db.ebean.Transactional;
import play.i18n.MessagesApi;
import play.libs.Json;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;
import java.text.ParseException;
import java.util.Locale;

/**
 * Created by abdoulbou on 07/01/17.
 */
@Singleton
public class FormattersProvider implements Provider<Formatters> {

    private final MessagesApi messagesApi;
    private static final Logger.ALogger logger = Logger.of(FormattersProvider.class);


    @Inject
    public FormattersProvider(MessagesApi messagesApi) {
        this.messagesApi = messagesApi;
    }

    @Override
    public Formatters get() {
        Formatters formatters = new Formatters(messagesApi);

        //user
        formatters.register(User.class, new Formatters.SimpleFormatter<User>() {

            @Override
            public User parse(String text, Locale locale) throws ParseException {
                return User.find.byId(Long.valueOf(text));
            }

            @Override
            public String print(User user, Locale locale) {
                return Json.toJson(user.getId()).asText();
            }
        });

        //deal
        formatters.register(Deal.class, new Formatters.SimpleFormatter<Deal>() {

            @Override
            public Deal parse(String text, Locale locale) throws ParseException {
                return Deal.find.byId(Long.valueOf(text));
            }

            @Override
            public String print(Deal deal, Locale locale) {
                return Json.toJson(deal.getId()).asText();
            }
        });

        return formatters;
    }
}
