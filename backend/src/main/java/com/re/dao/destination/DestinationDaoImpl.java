package com.re.dao.destination;


import com.re.entity.REDestination;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DestinationDaoImpl implements DestinationDao {
    final static String GET_ALL_DESTINATIONS = "SELECT * FROM DESTINATIONS";
    final static String GET_DESTINATION_BY_ID = "SELECT * FROM DESTINATIONS WHERE DESTINATION_ID = ?";
    protected JdbcTemplate jdbcTemplate;

    public DestinationDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<REDestination> getAll() {
        List<REDestination> reDestinations = new ArrayList<REDestination>();
        List<Map<String, Object>> rows =jdbcTemplate.queryForList(GET_ALL_DESTINATIONS);
        for (Map row : rows) {
            REDestination reDestination = new REDestination();
            BigDecimal id = (BigDecimal) (row.get(REDestinationDaoConstants.DESTINATION_ID));
            reDestination.setId(id.longValue());
            reDestination.setDescription((String) row.get(REDestinationDaoConstants.DESCRIPTION));
            reDestinations.add(reDestination);
        }

        return reDestinations;
    }

    @Override
    public REDestination findById(Long id) {
        REDestination reDestination = jdbcTemplate.queryForObject(
                GET_DESTINATION_BY_ID, new Object[]{id}, new DestinationRowMapper());

        return reDestination;
    }
}
