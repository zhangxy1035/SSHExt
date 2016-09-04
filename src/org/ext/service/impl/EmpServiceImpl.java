package org.ext.service.impl;

import org.ext.base.AbstractBaseDao;
import org.ext.entity.Emp;
import org.ext.service.EmpService;
import org.springframework.stereotype.Service;


@Service
public class EmpServiceImpl extends AbstractBaseDao<Emp,Integer> implements EmpService {
   
}
