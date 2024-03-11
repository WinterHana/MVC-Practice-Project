package com.model2.mvc.service.purchase.test;

import static org.junit.Assert.assertArrayEquals;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.common.util.TranStatusCodeUtil;
import com.model2.mvc.service.domain.ProductVO;
import com.model2.mvc.service.domain.PurchaseVO;
import com.model2.mvc.service.domain.UserVO;
import com.model2.mvc.service.product.ProductDAO;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.purchase.PurchaseDAO;
import com.model2.mvc.service.purchase.PurchaseService;

@RunWith(SpringJUnit4ClassRunner.class)
// @ContextConfiguration(locations = { "classpath:config/commonservice.xml" })
//@ContextConfiguration(locations = { "classpath:config/context-*.xml" })

@ContextConfiguration(locations = {
		"classpath:config/context-common.xml",
		"classpath:config/context-aspect.xml",
		"classpath:config/context-mybatis.xml",
		"classpath:config/context-transaction.xml"
})
public class PurchaseServiceTest {

	@Autowired
	@Qualifier("purchaseDAOImpl")
	private PurchaseDAO purchaseDAO;
	
	@Autowired
	@Qualifier("purchaseServiceImpl")
	private PurchaseService purchaseService;
	
	// @Test
	public void purchaseDAOTest() {		
		// 0. Setting
		UserVO userVO = new UserVO();
		userVO.setUserId("user11");
		
		ProductVO productVO = new ProductVO();
		productVO.setProdNo(10000);
		
		PurchaseVO purchaseVO = new PurchaseVO();
		purchaseVO.setPurchaseProd(productVO);
		purchaseVO.setBuyer(userVO);
		purchaseVO.setPaymentOption("1");
		purchaseVO.setReceiverName("Test");
		purchaseVO.setReceiverPhone("010-1234-5678");
		purchaseVO.setDlvyAddr("흠흐밍");
		purchaseVO.setDlvyRequest("뿡빵띠");
		purchaseVO.setTranCode("002");
		purchaseVO.setDlvyDate("2021-01-02");
		
		SearchVO searchVO = new SearchVO();
		searchVO.setPage(1);
		searchVO.setPageSize(5);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userVO", userVO);
		map.put("searchVO", searchVO);
	
		// 1. addPurchase
		int addPurchaseResult = purchaseDAO.addPurchase(purchaseVO);
		Assert.assertEquals(1, addPurchaseResult);
		
		// 2. getPurchaseList
		List<PurchaseVO> getPurchaseListResult = purchaseDAO.getPurchaseList(map);
		getPurchaseListResult.stream().forEach(s -> System.out.println(s.getTranNo()));
		
		// 3. getPurchaseCount
		int getPurchaseCountResult = purchaseDAO.getPurchaseCount(map);
		System.out.println("getPurchaseCountResult");
		
		// 4, getPurchase
		int tranNo = 0;
		if(!getPurchaseListResult.isEmpty()) {
			tranNo = getPurchaseListResult.get(getPurchaseListResult.size() - 1).getTranNo();
		}
		PurchaseVO getPurchaseResult = purchaseDAO.getPurchase(tranNo);
		Assert.assertEquals(10000, getPurchaseResult.getPurchaseProd().getProdNo());
		
		// 5. updatePurchase
		purchaseVO.setDlvyAddr("네네코 마시로");
		purchaseVO.setDlvyRequest("아라하시 타비");
		purchaseVO.setTranNo(tranNo);
		int updatePurchaseResult = purchaseDAO.updatePurchase(purchaseVO);
		Assert.assertEquals(1, updatePurchaseResult);
				
		// 6. updateTranCode
		Map<String, Object> transMap = new HashMap<String, Object>();
		transMap.put("nextCode", TranStatusCodeUtil.getNextCode(purchaseVO.getTranCode()));
		transMap.put("tranNo", tranNo);
		int updateTranCodeResult = purchaseDAO.updateTranCode(transMap);
		Assert.assertEquals(1, updateTranCodeResult);
		
		// 7. getSaleList
		List<Map<String, Object>> getSaleListResult = purchaseDAO.getSaleList();
		getSaleListResult.stream().forEach(System.out::println);
		
		// 8. deletePurhase
		int deletePurchaseResult = purchaseDAO.deletePurchase(tranNo);
		Assert.assertEquals(1, deletePurchaseResult);
	}
	
	// @Test
	public void getPurchaseListTest() {
		UserVO userVO = new UserVO();
		userVO.setUserId("user11");
		
		SearchVO searchVO = new SearchVO();
		searchVO.setPage(1);
		searchVO.setPageSize(5);
		
		Map<String, Object> getPurchaseListTestResult = purchaseService.getPurchaseList(searchVO, userVO);
		
		List<PurchaseVO> list = (List<PurchaseVO>) getPurchaseListTestResult.get("list");
		int totalCount = (int) getPurchaseListTestResult.get("totalCount");
		
		System.out.println("<getPurchaseListTest Result>");
		
		list.stream().forEach(System.out::println);
		System.out.println("totalCount : " + totalCount);
		
		System.out.println("<getPurchaseListTest End>");
	}
	
	// @Test
	public void getSalaListTest() {
		Map<Integer, Object> getSalaListResult = purchaseService.getSalaList();
		
		System.out.println("<getSalaList Result>");
		Iterator iterator = getSalaListResult.keySet().iterator();
		while(iterator.hasNext()) {
			Integer key = (Integer) iterator.next();
			System.out.println("Key : " + key + " || value : " + getSalaListResult.get(key));
		}
		System.out.println("<getSalaList End>");
	}
	
	@Test
	public void addPurchaseWithCountTest() {
		UserVO userVO = new UserVO();
		userVO.setUserId("test");
		
		ProductVO productVO = new ProductVO();
		productVO.setProdNo(1);
		productVO.setCount(1000);
		
		PurchaseVO purchaseVO = new PurchaseVO();
		purchaseVO.setPurchaseProd(productVO);
		purchaseVO.setBuyer(userVO);
		purchaseVO.setPaymentOption("1");
		purchaseVO.setReceiverName("Test");
		purchaseVO.setReceiverPhone("010-1234-5678");
		purchaseVO.setDlvyAddr("흠흐밍테스트");
		purchaseVO.setDlvyRequest("뿡빵띠테스트");
		purchaseVO.setTranCode("002");
		purchaseVO.setDlvyDate("2021-01-02");
		purchaseVO.setProdCount(1);
		
		int addPurchaseResult = purchaseService.addPurchase(purchaseVO);
		Assert.assertEquals(2, addPurchaseResult);
	}
}