package com.maker.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.maker.domain.Criteria;
import com.maker.domain.CustomerVO;
import com.maker.mapper.CustomerMapper;


@Service
public class CustomerServiceImpl implements CustomerService {
	@Autowired
	private CustomerMapper customerMapper;
	
	@Override
	public void customerJoin(CustomerVO customer) {
		try {
			customerMapper.customerJoin(customer);
		} catch (DuplicateKeyException e) {
            e.printStackTrace();
        }

	}

	@Override
	public int idCheck(String customerId) {
		return customerMapper.idCheck(customerId);
		
	}

	@Override
	public CustomerVO customerLogin(CustomerVO customer) {
		return customerMapper.customerLogin(customer);
	}

	@Override
	public List<CustomerVO> customerGetList(Criteria cri) {
		return customerMapper.customerGetList(cri);
	}

	@Override
	public int customerGetTotal(Criteria cri) {
		return customerMapper.customerGetTotal(cri);
	}

}