package config;

import org.hibernate.cfg.ImprovedNamingStrategy;
import org.hibernate.internal.util.StringHelper;

public class CustomNamingStrategy extends ImprovedNamingStrategy {

    @Override
    public String classToTableName(String className) {
        return addUnderscores(StringHelper.unqualify(className));
    }

    @Override
    public String columnName(final String columnName){
        return addUnderscores(StringHelper.unqualify(columnName));
    }

}
