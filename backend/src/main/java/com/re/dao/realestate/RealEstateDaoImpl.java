package com.re.dao.realestate;

import com.re.dao.realestate.procedures.GetREStoredProcedure;
import com.re.dao.realestate.procedures.UpdateREStoredProcedure;
import com.re.entity.RealEstate;
import oracle.jdbc.OracleTypes;
import oracle.sql.CLOB;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.support.SqlLobValue;

import java.math.BigDecimal;
import java.util.HashMap;
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
        Map inputs = new HashMap();
        inputs.put(REDaoConstants.P_FACILITY_ID, realEstate.getId());
        inputs.put(REDaoConstants.P_CADASTRAL_NUMBER, realEstate.getCadastralNumber());
        inputs.put(REDaoConstants.P_SQUARE, realEstate.getSquare());
        inputs.put(REDaoConstants.P_DESTINATION_ID, realEstate.getDestinationId());
        inputs.put(REDaoConstants.P_AREA_DESCRIPTION, realEstate.getAreaDescription());
        inputs.put(REDaoConstants.P_USAGE_ID, realEstate.getUsageId());
        inputs.put(REDaoConstants.P_ADDRESS, realEstate.getAddress());
        inputs.put(REDaoConstants.P_USER_IP, "127.0.0.1");
        inputs.put(REDaoConstants.P_USER_NAME, "TestUser");
        updateREStoredProcedure.execute(inputs);
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
