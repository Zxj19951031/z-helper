package org.zipper.helper.data.transport.plugins.drdsreader;

import cn.hutool.core.collection.CollectionUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zipper.helper.util.json.JsonObject;

import java.util.List;

public class SqlUtil {

    private static final Logger log = LoggerFactory.getLogger(SqlUtil.class);


    public static String buildQuerySql(JsonObject config) {
        String format = "select %s from %s where %s";
        String querySql = config.getString(Keys.QUERY_SQL);

        if (StringUtils.isNotBlank(querySql)) {
            log.info("监测到配置了querySql参数");
            return querySql;
        } else {
            return String.format(format, getColumns(config), getTableName(config), getWhere(config));
        }
    }

    private static String getTableName(JsonObject config) {
        return config.getString(Keys.TABLE);
    }

    private static String getColumns(JsonObject config) {
        List<String> columns = config.getList(Keys.COLUMNS, String.class);
        return CollectionUtil.join(columns, ",");
    }

    private static String getWhere(JsonObject config) {
        return config.getString(Keys.WHERE, "1=1");
    }
}
