package config;


import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import dao.PixivDao;
import services.PixivService;

public class GuiceConfig extends GuiceSupport {

    @Override
    protected Injector configure() {
        return Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                /**dao层注入**/
                bind(PixivDao.class).in(Singleton.class);

                /**service层注入**/
                bind(PixivService.class).in(Singleton.class);
            }
        });
    }
}

