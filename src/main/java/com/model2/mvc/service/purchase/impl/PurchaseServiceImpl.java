package com.model2.mvc.service.purchase.impl;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.common.util.TranStatusCodeUtil;
import com.model2.mvc.service.domain.PurchaseVO;
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
		int result = 0;
		
		try {
			result = purchaseDAO.addPurchase(purchaseVO);
		} catch (Exception e) {
			System.out.println("[" + getClass().getName() + " .addPurchase] Exception");
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public Map<String, Object> getPurchaseList(SearchVO searchVO, String userId) {
		Map<String, Object> result = new HashMap<String, Object>();
		List<PurchaseVO> list = null;
		int totalCount = 0;
		
		Map<String, Object> tmp = new HashMap<String, Object>();
		tmp.put("userId", userId);
		tmp.put("searchVO", searchVO);
		
		try {
			list = purchaseDAO.getPurchaseList(tmp);
			totalCount = purchaseDAO.getPurchaseCount(userId);
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
	public Map<Integer, Object> getSalaList() {
		Map<Integer, Object> result = new HashMap<Integer, Object>();
		List<Map<String, Object>> tmp = null;
				
		try {
			tmp = purchaseDAO.getSaleList();
		} catch (Exception e) {
			System.out.println("[" + getClass().getName() + " .getSalaList] Exception");
			e.printStackTrace();
		}
		
		for(Map<String, Object> m : tmp) {
			PurchaseVO purchaseVO = new PurchaseVO();
			purchaseVO.setTranNo(((BigDecimal)m.get("TRAN_NO")).intValue());
			purchaseVO.setTranCode((String) m.get("TRAN_STATUS_CODE"));
			result.put(((BigDecimal)m.get("PROD_NO")).intValue(), purchaseVO);
		}
		
		return result;
	}

	@Override
	public int updateTranCode(PurchaseVO purchaseVO) {
		int result = 0;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("nextCode", TranStatusCodeUtil.getNextCode(purchaseVO.getTranCode()));
		map.put("tranNo", purchaseVO.getTranNo());
		
		try {
			result = purchaseDAO.updateTranCode(map);
		} catch (Exception e) {
			System.out.println("[" + getClass().getName() + " .updateTranCode] Exception");
			e.printStackTrace();
		}
		
		return result;
	}
}
