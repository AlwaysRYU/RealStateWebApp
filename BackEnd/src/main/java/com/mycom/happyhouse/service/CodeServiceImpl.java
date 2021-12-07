package com.mycom.happyhouse.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mycom.happyhouse.dao.CodeDao;
import com.mycom.happyhouse.dto.CodeDto;

@Service
public class CodeServiceImpl implements CodeService{

	@Autowired
	CodeDao codeDao;
	
	@Override
	public List<CodeDto> codeList(String groupCode) {
		return codeDao.codeList(groupCode);
	}

}
