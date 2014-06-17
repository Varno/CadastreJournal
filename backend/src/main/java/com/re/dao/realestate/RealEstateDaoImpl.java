package com.re.dao.realestate;

import com.re.dao.realestate.procedures.GetREStoredProcedure;
import com.re.dao.realestate.procedures.UpdateREStoredProcedure;
import com.re.entity.RealEstate;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;
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

    @Override
    public void saveOrUdate(RealEstate realEstate) {
        UpdateREStoredProcedure updateREStoredProcedure = new UpdateREStoredProcedure(jdbcTemplate);
/*
        updateREStoredProcedure.saveOrUpdate(realEstate);
*/
    }

    @Override
    public int getNumberOfItems(String cadastralNumber) {
        GetREStoredProcedure getREStoredProcedure = new GetREStoredProcedure(jdbcTemplate);
        List result;
        Map data = getREStoredProcedure.getRealEstates(null, cadastralNumber, 0, 0);
        BigDecimal count = (BigDecimal)data.get(REDaoConstants.P_COUNT);
        return count.intValue();
    }

}
