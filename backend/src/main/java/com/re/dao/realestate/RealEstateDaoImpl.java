package com.re.dao.realestate;

import com.re.entity.RealEstate;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

public class RealEstateDaoImpl implements RealEstateDao {
    protected JdbcTemplate jdbcTemplate;

    public RealEstateDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<RealEstate> getItemsFromRange(Long facilityId, String cadastralNumber, int skipFirst, int numberOfItems) {
        GetREStoredProcedure getREStoredProcedure = new GetREStoredProcedure(jdbcTemplate);
        List result;
        Map data = getREStoredProcedure.getRealEstates(facilityId, cadastralNumber, skipFirst, numberOfItems);
        result = (List) data.get(REDaoConstants.P_CURSOR);
        return result;
    }

}
