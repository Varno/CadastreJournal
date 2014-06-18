package com.re.dao.usage;

import com.re.dao.destination.REDestinationDaoConstants;
import com.re.entity.REDestination;
import com.re.entity.REUsage;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UsageDaoImpl implements UsageDao {
    final static String GET_ALL_USAGES = "SELECT * FROM USAGES";
    protected JdbcTemplate jdbcTemplate;

    public UsageDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<REUsage> findAll() {
        List<REUsage> reUsages = new ArrayList<REUsage>();
        List<Map<String, Object>> rows =jdbcTemplate.queryForList(GET_ALL_USAGES);
        for (Map row : rows) {
            REUsage reUsage = new REUsage();
            BigDecimal id = (BigDecimal) (row.get("USAGE_ID"));
            reUsage.setId(id.longValue());
            reUsage.setDescription((String) row.get("DESCRIPTION"));
            reUsages.add(reUsage);
        }

        return reUsages;
    }
}
