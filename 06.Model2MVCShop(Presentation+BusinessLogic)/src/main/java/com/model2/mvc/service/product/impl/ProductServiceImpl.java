package com.model2.mvc.service.product.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductDao;
import com.model2.mvc.service.product.ProductService;

@Service("productServiceImpl")
public class ProductServiceImpl implements ProductService {

	@Autowired
	@Qualifier("productDaoImpl")
	private ProductDao productDao;
	
	public ProductServiceImpl() {
		System.out.println("::" + getClass() + ".default Constructor Call....");
	}
	
	public void addProduct(Product product) throws Exception{
		 productDao.addProduct(product);
	}
	
	public Product getProduct(int prodNo) throws Exception{
		return productDao.findProduct(prodNo);
	}
	
	public Map<String,Object> getProductList(Search search) throws Exception {
		List<Object> list = productDao.getProductList(search);
		int totalCount = productDao.getTotalCount(search);
		
		for(int i=0;i<list.size();i++) {
			if(((Product)list.get(i)).getProTranCode()==null) {
				((Product)list.get(i)).setProTranCode("0");
			}
		}
		
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("list", list);
		map.put("totalCount", new Integer(totalCount));
		
		return map;
	}
	
	public void updateProduct (Product product) throws Exception {
		 productDao.updateProduct(product);
	}
	
	
}
