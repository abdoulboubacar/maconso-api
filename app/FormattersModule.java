import com.google.inject.AbstractModule;

import custom.FormattersProvider;
import play.data.format.Formatters;

/**
 * Created by abdoulbou on 07/01/17.
 */
public class FormattersModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(Formatters.class).toProvider(FormattersProvider.class);
    }
}
