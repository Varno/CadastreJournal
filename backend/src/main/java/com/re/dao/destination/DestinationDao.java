package com.re.dao.destination;


import com.re.entity.REDestination;

import java.util.List;

public interface DestinationDao {

    public List<REDestination> getAll();
    public REDestination findById(Long id);
}
