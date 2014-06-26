package com.re.dao.realestate;

import com.re.auth.UserService;
import com.re.entity.RealEstate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Репозитарий объектов недвижимости
public class RealEstateDaoImpl implements RealEstateDao {
    protected JdbcTemplate jdbcTemplate;
    private UserService userService;

    public RealEstateDaoImpl(UserService userService, JdbcTemplate jdbcTemplate) {
        this.userService = userService;
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
    public RealEstate getItem(Long facilityId) {
        List<RealEstate> items = getItemsFromRange(facilityId, "", 0, 1);
        return items.size() > 0 ? items.get(0) : null;
    }

    @Override
    public Long saveOrUpdate(RealEstate realEstate) throws org.springframework.dao.DataAccessException {
        String currentUserName = userService.getCurrentUserName();
        String ip = userService.getUserIpAddress();
        UpdateREStoredProcedure updateREStoredProcedure = new UpdateREStoredProcedure(jdbcTemplate);
        Map inputs = new HashMap();
        inputs.put(REDaoConstants.P_FACILITY_ID, realEstate.getId());
        inputs.put(REDaoConstants.P_CADASTRAL_NUMBER, realEstate.getCadastralNumber());
        inputs.put(REDaoConstants.P_SQUARE, realEstate.getSquare());
        inputs.put(REDaoConstants.P_DESTINATION_ID, realEstate.getDestinationId());
        inputs.put(REDaoConstants.P_AREA_DESCRIPTION, realEstate.getAreaDescription());
        inputs.put(REDaoConstants.P_USAGE_ID, realEstate.getUsageId());
        inputs.put(REDaoConstants.P_ADDRESS, realEstate.getAddress());
        inputs.put(REDaoConstants.P_USER_IP, ip);
        inputs.put(REDaoConstants.P_USER_NAME, currentUserName);
        Map result = updateREStoredProcedure.execute(inputs);
        BigDecimal count = (BigDecimal)result.get(REDaoConstants.P_INSERTED_ID);
        return count.longValue();
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
