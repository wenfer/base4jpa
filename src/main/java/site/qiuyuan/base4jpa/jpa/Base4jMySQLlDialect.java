package site.qiuyuan.base4jpa.jpa;

import org.hibernate.dialect.InnoDBStorageEngine;
import org.hibernate.dialect.MySQL8Dialect;
import org.hibernate.dialect.MySQLStorageEngine;
import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.type.StringType;

/**
 * <p>
 * mysql方言扩展
 * </p>
 *
 * @author qiuyuan
 * @since 1.1
 */
public class Base4jMySQLlDialect extends MySQL8Dialect {

    public Base4jMySQLlDialect() {
        super.registerFunction("convert", new SQLFunctionTemplate(StringType.INSTANCE, "CONVERT(?1 USING ?2)"));
        super.registerFunction("find_in_set", new SQLFunctionTemplate(StringType.INSTANCE, "find_in_set(?1 USING ?2)"));
    }

    @Override
    protected MySQLStorageEngine getDefaultMySQLStorageEngine() {
        return InnoDBStorageEngine.INSTANCE;
    }
}
