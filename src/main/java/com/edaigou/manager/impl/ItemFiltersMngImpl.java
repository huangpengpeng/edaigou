package com.edaigou.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edaigou.dao.ItemFiltersDao;
import com.edaigou.entity.ItemFilters;
import com.edaigou.manager.ItemFiltersMng;


@Transactional
@Service
public class ItemFiltersMngImpl implements ItemFiltersMng{

	@Override
	public void add(Long pNumIid, String nick) {
		ItemFilters itemFilters=new ItemFilters(pNumIid, nick);
		itemFilters.init();
		dao.add(itemFilters);
	}

	@Override
	public ItemFilters getByItemAndNick(Long pNumIid, String nick) {
		return dao.getByItemAndNick(pNumIid, nick);
	}

	@Override
	public List<ItemFilters> query() {
		return dao.query();
	}
	

	@Override
	public List<ItemFilters> getByNumIid(Long pNumIid) {
		return dao.getByNumIid(pNumIid);
	}

	@Override
	public void delete(Long id) {
		dao.delete(id);
	}
	
	@Autowired
	private ItemFiltersDao dao;
}
