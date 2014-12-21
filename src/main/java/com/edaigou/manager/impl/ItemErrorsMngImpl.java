package com.edaigou.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edaigou.dao.ItemErrorsDao;
import com.edaigou.entity.ItemErrors;
import com.edaigou.manager.ItemErrorsMng;

@Transactional
@Service
public class ItemErrorsMngImpl implements ItemErrorsMng {

	@Override
	public void add(Long itemId, String errorType) {
		if (dao.getByItemAndType(itemId, errorType) == null) {
			ItemErrors itemErrors = new ItemErrors(itemId, errorType);
			itemErrors.init();
			dao.add(itemErrors);
		}
	}

	@Override
	public ItemErrors getByItemAndType(Long itemId, String errorType) {
		return dao.getByItemAndType(itemId, errorType);
	}

	@Override
	public List<ItemErrors> getByErrorType(String errorType) {
		return dao.getByErrorType(errorType);
	}

	@Override
	public void delete(Long id) {
		dao.delete(id);
	}

	@Override
	public List<ItemErrors> getByItem(Long itemId) {
		return dao.getByItem(itemId);
	}

	@Autowired
	private ItemErrorsDao dao;
}
