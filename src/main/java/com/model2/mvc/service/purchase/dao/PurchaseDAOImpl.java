package com.model2.mvc.service.purchase.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.service.domain.PurchaseVO;
import com.model2.mvc.service.domain.UserVO;
import com.model2.mvc.service.purchase.PurchaseDAO;

@Repository("purchaseDAOImpl")
public class PurchaseDAOImpl implements PurchaseDAO {
	
	@Autowired
	@Qualifier("sqlSessionTemplate")
	private SqlSession sqlSession;
	
	public PurchaseDAOImpl() {
		System.out.println("[" + getClass().getName() + " Default Constructor] Call");
	}
	
	public PurchaseDAOImpl(SqlSession sqlSession) {
		System.out.println("[" + getClass().getName() + " SqlSeesion set Constructor] Call");
		this.sqlSession = sqlSession;
	}
	
	public void setSqlSession(SqlSession sqlSession) {
		System.out.println("[" + getClass().getName() + ".setSqlSession] Call");
		this.sqlSession = sqlSession;
	}
	
	@Override
	public PurchaseVO getPurchase(int tranNo) {
		System.out.println("[" + getClass().getName() + ".getPurchase] Call");
		return sqlSession.selectOne("PurchaseMapper.getPurchase", tranNo);
	}

	@Override
	public List<PurchaseVO> getPurchaseList(Map<String,Object> map) {
		System.out.println("[" + getClass().getName() + ".getPurchaseList] Call");
		return sqlSession.selectList("PurchaseMapper.getPurchaseList", map);
	}

	@Override
	public int getPurchaseCount(Map<String,Object> map) {
		System.out.println("[" + getClass().getName() + ".getPurchaseList] Call");
		return sqlSession.selectOne("PurchaseMapper.getPurchaseCount", map);
	}

	@Override
	public List<Map<String, Object>> getSaleList() {
		System.out.println("[" + getClass().getName() + ".getSaleList] Call");
		return sqlSession.selectList("PurchaseMapper.getSaleList", "p.prod_no");
	}

	@Override
	public int addPurchase(PurchaseVO purchaseVO) {
		System.out.println("[" + getClass().getName() + ".addPurchase] Call");
		return sqlSession.insert("PurchaseMapper.addPurchase", purchaseVO);
	}

	@Override
	public int updatePurchase(PurchaseVO purchaseVO) {
		System.out.println("[" + getClass().getName() + ".updatePurchase] Call");
		return sqlSession.update("PurchaseMapper.updatePurchase", purchaseVO);
	}

	@Override
	public int updateTranCode(Map<String,Object> map) {
		System.out.println("[" + getClass().getName() + ".updateTranCode] Call");
		return sqlSession.update("PurchaseMapper.updateTranCode", map);
	}

	@Override
	public int deletePurchase(int tranNo) {
		System.out.println("[" + getClass().getName() + ".deletePurchase] Call");
		return sqlSession.update("PurchaseMapper.deletePurchase", tranNo);
	}

	@Override
	public int deletePurchaseProdNo(int prodNo) {
		System.out.println("[" + getClass().getName() + ".deletePurchaseProdNo] Call");
		return sqlSession.update("PurchaseMapper.deletePurchaseProdNo", prodNo);
	}
	
	@Override
	public int deletePurchaseBuyerId(String buyerId) {
		System.out.println("[" + getClass().getName() + ".deletePurchaseBuyerId] Call");
		return sqlSession.update("PurchaseMapper.deletePurchaseBuyerId", buyerId);
	}
}
