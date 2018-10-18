package com.college.controller;

import java.util.List;

import com.college.funondb.ColumnDbDao;
import com.college.model.Columns;

public class ColumnsController {
	
	public static ColumnsController columnController;
	
	public static ColumnsController getInstance(){
		if (columnController==null){
			columnController = new ColumnsController();
		}
		return columnController;
	}

	public List<Columns> UpdateCollegesList(String loc, int tut) {
		System.out.println("In Db Controller \n loc: "+loc+"-tut: "+tut);
		ColumnDbDao columnDbDao = ColumnDbDao.getInstance();
		return columnDbDao.UpdateCollegesList(loc, tut);
	}
	
	

}
