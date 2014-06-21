package com.re.dao.rehistory;

public interface REHistoryDaoConstants {
    public static final String FACILITY_HISTORY_ID = "FACILITY_HISTORY_ID";
    public static final String MODIFIED_DATE = "MAX(R.MODIFIED_DATE)";
    public static final String MODIFIED_BY = "MAX(R.MODIFIED_BY)";
    public static final String MODIFIED_BY_IP = "MAX(R.MODIFIED_BY_IP)";
    public static final String ACTION = "MAX(R.ACTION)";
    public static final String DESCRIPTION = "TXT_DESCR";

    public static final String P_FACILITY_ID = "p_facility_id";
    public static final String P_SKIP = "p_skip";
    public static final String P_TAKE = "p_take";
    public static final String P_CURSOR = "p_cursor";
    public static final String P_COUNT = "p_rowcount";

}
