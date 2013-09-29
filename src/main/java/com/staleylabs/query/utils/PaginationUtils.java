package com.staleylabs.query.utils;

import com.staleylabs.query.beans.QueryPage;
import com.staleylabs.query.mapper.MapRowMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Sean M. Staley
 * @version 2.0 (9/21/13)
 */

public class PaginationUtils<E> {

    public QueryPage<E> fetchPage(final JdbcTemplate jt, final String sqlCountRows, final String sqlFetchRows,
                                  final int pageNo, final long pageSize) throws BadSqlGrammarException {

        // determine how many rows are available
        final int rowCount = jt.queryForInt(sqlCountRows);

        // calculate the number of pages
        long pageCount = rowCount / pageSize;
        if (rowCount > pageSize * pageCount) {
            pageCount++;
        }

        // create the page object
        final QueryPage<E> page = new QueryPage<E>();
        page.setPageNumber(pageNo);
        page.setPagesAvailable(pageCount);

        // fetch a single page of results
        final long startRow = (pageNo - 1) * pageSize;
        jt.query(sqlFetchRows, new ResultSetExtractor<Object>() {
            @SuppressWarnings("unchecked")
            @Override
            public Object extractData(ResultSet rs) throws SQLException, DataAccessException {
                final List pageItems = page.getPageItems();
                int currentRow = 0;
                while (rs.next() && currentRow < startRow + pageSize) {
                    if (currentRow >= startRow) {
                        pageItems.add(MapRowMapper.getInstance().mapRow(rs, currentRow));
                    }
                    currentRow++;
                }
                return page;
            }
        });

        return page;
    }
}
