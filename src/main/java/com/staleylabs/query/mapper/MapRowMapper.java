package com.staleylabs.query.mapper;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Custom row mapper used for mapping columns of a given database row to a {@link LinkedHashMap}. This is needed for
 * pagination support in the query application.
 *
 * @author Sean M. Staley
 * @version 2.0 (9/21/13)
 */

public class MapRowMapper implements RowMapper<Map<String, Object>> {

    private static MapRowMapper mapRowMapper;

    /**
     * Provides a single instance of the class, as we do not really need new objects for each time this is used in
     * the application. Also uses {@code lazy initialization} so we do not create this object until it is really needed.
     *
     * @return Single instance of {@link MapRowMapper}.
     */
    public static MapRowMapper getInstance() {
        if (mapRowMapper == null) {
            mapRowMapper = new MapRowMapper();
        }

        return mapRowMapper;
    }

    @Override
    public Map<String, Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
        Map<String, Object> row = Collections.emptyMap();

        // If the ResultSet is empty, there is no need to go through this.
        if (rs != null) {

            // LinkedHashMap because the columns should align in the order that they are in the database.
            row = new LinkedHashMap<String, Object>(1);
            ResultSetMetaData metaData = rs.getMetaData();
            int numberOfColumns = metaData.getColumnCount();

            // Go through each column and place the column name (as the key) and value into the map.
            for (int i = 1; i <= numberOfColumns; i++) {
                String columnName = metaData.getColumnName(i);
                Object columnValue = rs.getObject(i);

                row.put(columnName, columnValue);
            }
        }

        return row;
    }
}
