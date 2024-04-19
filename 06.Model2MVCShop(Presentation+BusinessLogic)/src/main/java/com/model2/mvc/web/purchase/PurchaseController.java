package com.model2.mvc.web.purchase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.domain.Purchase;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.domain.User;

@Controller
public class PurchaseController{

	@Autowired
	@Qualifier("purchaseServiceImpl")
	private PurchaseService purchaseService;

	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;

	@Autowired
	@Qualifier("userServiceImpl")
	private UserService userService;

	@Value("#{commonProperties['pageUnit'] ?: 5}")
	int pageUnit;

	@Value("#{commonProperties['pageSize'] ?: 5}")
	int pageSize;
	
	public PurchaseController() {
		System.out.println(this.getClass());
	}
	
	@RequestMapping("/addPurchase.do")
	public ModelAndView insertPurchase(@ModelAttribute("purchase") Purchase purchase, Model model,
												@RequestParam("buyerId")String buyerId,@RequestParam("prodNo")int prodNo)
													throws Exception{
		
		System.out.println("/addPurchaseView");
		
		ModelAndView modelAndView = new ModelAndView();
		purchase.setBuyer(userService.getUser(buyerId));
		purchase.setPurchaseProd(productService.getProduct(prodNo));
		purchase.setTranCode("1");
		
		modelAndView.setViewName("forward:/purchase/addPurchase.jsp");
		model.addAttribute("purchase", purchase);
		purchaseService.insertPurchase(purchase);
		
		return modelAndView;
	}
	
	@RequestMapping("/addPurchaseView.do")
	public ModelAndView addPurchaseView(@RequestParam("prodNo") int prodNo, HttpSession session) throws Exception {

		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("user", session.getAttribute("user"));
		modelAndView.addObject("product", productService.getProduct(prodNo));
		modelAndView.setViewName("forward:/purchase/addPurchaseView.jsp");

		return modelAndView;
	}
	
	@RequestMapping("/getPurchase.do")
	public ModelAndView getPurchase(@RequestParam("tranNo") int tranNo, Model model
														) throws Exception{
		
		System.out.println("/getPurchase");
		//Business Logic
		ModelAndView modelAndView = new ModelAndView();
		Purchase purchase = purchaseService.getPurchase(tranNo);
		
		modelAndView.setViewName("forward:/purchase/getPurchase.jsp");
		// Model °ú View ¿¬°á
		model.addAttribute("purchase", purchase);
		
		return modelAndView;
	}
	
	@RequestMapping("/listPurchase.do")
	public ModelAndView getPurchaseList(@ModelAttribute("search")Search search, Model model,HttpSession session) throws Exception{
		
		System.out.println("/listPurchase");
		
		if (search.getCurrentPage() == 0) {
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);
		
		ModelAndView modelAndView = new ModelAndView();
		String buyerId = ((User)session.getAttribute("user")).getUserId();
		Map<String,Object> map = purchaseService.getPurchaseList(search, buyerId);

		Page resultPage = new Page( search.getCurrentPage(),((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);

		System.out.println("ListPurchase ::" + resultPage);
		System.out.println("ListPurchase ::" + search);
		
		modelAndView.addObject("list",map.get("list"));
		modelAndView.addObject("resultPage", resultPage);
		modelAndView.addObject("search", search);
		modelAndView.setViewName("forward:/purchase/listPurchase.jsp");
		
		return modelAndView;
	}
	
	
	@RequestMapping("/updatePurchase.do")
	public ModelAndView updatePurchase(@ModelAttribute("purchase") Purchase purchase,@RequestParam("tranNo")int tranNo) throws Exception {
		
		System.out.println("/updatePurchase");
		ModelAndView modelAndView = new ModelAndView();
		purchaseService.updatePurchase(purchase);
		
		return modelAndView;
	}
	
	@RequestMapping("/updateTranCode.do")
	public ModelAndView updateTranCode(@ModelAttribute("purchase") Purchase purchase,
															@RequestParam("tranNo") int tranNo,
															@RequestParam("tranCode") int tranCode) throws Exception {
		System.out.println("/updateTranCode");
		ModelAndView modelAndView = new ModelAndView();
		purchaseService.updateTranCode(purchase);
		purchaseService.getPurchase(tranNo);
		purchaseService.getPurchase(tranCode);
		
		modelAndView.setViewName("redirect:/listPurchase.do");
		
		return modelAndView;
	}
}