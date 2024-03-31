package com.model2.mvc.service.purchase.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.common.util.TranStatusCodeUtil;
import com.model2.mvc.service.domain.ProductVO;
import com.model2.mvc.service.domain.PurchaseVO;
import com.model2.mvc.service.domain.UserVO;
import com.model2.mvc.service.product.ProductDAO;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.purchase.PurchaseDAO;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.dao.PurchaseDAOImpl;
import com.model2.mvc.service.user.dao.UserDAOImpl;

@Service("purchaseServiceImpl")
public class PurchaseServiceImpl implements PurchaseService {
	
	@Autowired
	@Qualifier("purchaseDAOImpl")
	private PurchaseDAO purchaseDAO;
	
	@Autowired
	@Qualifier("productDAOImpl")
	private ProductDAO productDAO;
	
	public PurchaseServiceImpl() {
		System.out.println("[" + getClass().getName() + " Default Constructor] Call");
	}
	
	public PurchaseServiceImpl(PurchaseDAO purchaseDAO) {
		System.out.println("[" + getClass().getName() + " Default Constructor] Call");
		this.purchaseDAO = purchaseDAO;
	}
	
	public void setPurchaseDAO(PurchaseDAO purchaseDAO) {
		this.purchaseDAO = purchaseDAO;
	}
	
	@Override
	public int addPurchase(PurchaseVO purchaseVO) {	
		// addPurchase
		int result = 0;
		
		// updateProductCount
		Map<String, Integer> requestMap = new HashMap<String, Integer>();
		requestMap.put("prodNo", purchaseVO.getPurchaseProd().getProdNo());
		requestMap.put("countResult", purchaseVO.getPurchaseProd().getCount() - purchaseVO.getProdCount());
		
		try {
			result += purchaseDAO.addPurchase(purchaseVO);
			result += productDAO.updateProductCount(requestMap);
		} catch (Exception e) {
			System.out.println("[" + getClass().getName() + " .addPurchase] Exception");
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public Map<String, Object> getPurchaseList(SearchVO searchVO, UserVO userVO) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<PurchaseVO> list = null;
		int totalCount = 0;
		
		Map<String, Object> tmp = new HashMap<String, Object>();
		tmp.put("userVO", userVO);
		tmp.put("searchVO", searchVO);
		
		try {
			list = purchaseDAO.getPurchaseList(tmp);
			totalCount = purchaseDAO.getPurchaseCount(tmp);
			
			if(list != null) {
				list.stream().forEach(e -> {
					e.getPurchaseProd().setProdName(productDAO.getProduct(e.getPurchaseProd().getProdNo()).getProdName());
				});
			}
			
		} catch (Exception e) {
			System.out.println("[" + getClass().getName() + " .getPurchaseList] Exception");
			e.printStackTrace();
		}
		
		result.put("list", list);
		result.put("totalCount", totalCount);
		
		return result;
	}

	@Override
	public PurchaseVO getPurchase(int tranNo) {		
		PurchaseVO result = null;
		
		try {
			result = purchaseDAO.getPurchase(tranNo);
			result.getPurchaseProd().setProdName(productDAO.getProduct(result.getPurchaseProd().getProdNo()).getProdName());
		} catch (Exception e){
			System.out.println("[" + getClass().getName() + " .getPurchase] Exception");
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public int updatePurchase(PurchaseVO purchaseVO) {
		int result = 0;
		
		try {
			result = purchaseDAO.updatePurchase(purchaseVO);
		} catch (Exception e){
			System.out.println("[" + getClass().getName() + " .updatePurchase] Exception");
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public Map<Integer, String> getSalaList() {
		Map<Integer, String> result = new HashMap<Integer, String>();
		List<Map<String, Object>> tmp = null;
				
		try {
			tmp = purchaseDAO.getSaleList();
		} catch (Exception e) {
			System.out.println("[" + getClass().getName() + " .getSalaList] Exception");
			e.printStackTrace();
		}
		
		// 후처리 후 반환
		for(Map<String, Object> m : tmp) {
			result.put(((BigDecimal)m.get("TRAN_NO")).intValue(), (String) m.get("TRAN_STATUS_CODE"));
		}
		
		return result;
	}

	@Override
	public int updateTranCode(PurchaseVO purchaseVO) {
		int result = 0;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("tranCode", purchaseVO.getTranCode());
		map.put("tranNo", purchaseVO.getTranNo());
		
		try {
			result = purchaseDAO.updateTranCode(map);
		} catch (Exception e) {
			System.out.println("[" + getClass().getName() + " .updateTranCode] Exception");
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public int deletePurchase(PurchaseVO purchaseVO) {
		// addPurchase
		int result = 0;
		
		// updateProductCount
		Map<String, Integer> requestMap = new HashMap<String, Integer>();
		int prodNo = purchaseVO.getPurchaseProd().getProdNo();
		
		try {
			requestMap.put("prodNo", prodNo);
			ProductVO product = productDAO.getProduct(prodNo);
			
			// 제품이 삭제되어 더 이상 갯수를 Update할 필요가 없을 때의 예외 처리
			if(product != null) {
				int count = product.getCount();
				requestMap.put("countResult", count + purchaseVO.getProdCount());
				result += productDAO.updateProductCount(requestMap);
			}
			
			result += purchaseDAO.deletePurchase(purchaseVO.getTranNo());
		} catch (Exception e) {
			System.out.println("[" + getClass().getName() + " .addPurchase] Exception");
			e.printStackTrace();
		}
		
		return result;
	}
}
