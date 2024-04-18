package com.model2.mvc.service.purchase.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.purchase.PurchaseDao;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.domain.Purchase;

@Service("purchaseServiceImpl")
public class PurchaseServiceImpl implements PurchaseService{
	
	@Autowired
	@Qualifier("purchaseDaoImpl")
	private PurchaseDao purchaseDao;
	
	public PurchaseServiceImpl() {
		System.out.println("::" + getClass() + ".default Constructor Call....");
	}
	
	public void insertPurchase(Purchase purchase) throws Exception{
		purchaseDao.insertPurchase(purchase);
	}
	
	public Purchase getPurchase(int tranNo) throws Exception{
		return purchaseDao.findPurchase(tranNo);
	}

	public Map<String, Object> getPurchaseList(Search search, String buyerId) throws Exception
	{
		
		List<Object> list = purchaseDao.getPurchaseList(search, buyerId);
		int totalCount =0;
		if(list.size()>0) {
			totalCount=((Purchase)list.get(0)).getCount();
		}
		Map<String, Object>map = new HashMap<String,Object>();
		map.put("list",list);
		map.put("totalCount", totalCount);
		
		return map;
	}
	
	public Map<String, Object> getSaleList(Search search) throws Exception{
		return null;
	}

	public void updatePurchase(Purchase purchase) throws Exception {
		purchaseDao.updatePurchase(purchase);
	}

	public void updateTranCode(Purchase purchase) throws Exception {
		purchaseDao.updateTranCode(purchase);
	}

}
