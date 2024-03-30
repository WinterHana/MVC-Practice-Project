package com.model2.mvc.service.product.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.service.domain.FileVO;
import com.model2.mvc.service.domain.ProductVO;
import com.model2.mvc.service.product.ProductDAO;

@Repository("productDAOImpl")
public class ProductDAOImpl implements ProductDAO {
	
	@Autowired
	@Qualifier("sqlSessionTemplate")
	private SqlSession sqlSession;
	
	public ProductDAOImpl() {
		System.out.println("[" + getClass().getName() + " Default Constructor] Call");
	}
	
	public ProductDAOImpl(SqlSession sqlSession) {
		System.out.println("[" + getClass().getName() + " SqlSeesion set Constructor] Call");
		this.sqlSession = sqlSession;
	}
	
	public void setSqlSession(SqlSession sqlSession) {
		System.out.println("[" + getClass().getName() + ".setSqlSession] Call");
		this.sqlSession = sqlSession;
	}
	
	@Override
	public int addProduct(ProductVO productVO) {
		System.out.println("[" + getClass().getName() + ".addProduct] Call");
		return sqlSession.insert("ProductMapper.addProduct", productVO);
	}

	@Override
	public ProductVO getProduct(int prodNo) {
		System.out.println("[" + getClass().getName() + ".getProduct] Call");
		return sqlSession.selectOne("ProductMapper.getProduct", prodNo);
	}

	@Override
	public int updateProduct(ProductVO productVO) {
		System.out.println("[" + getClass().getName() + ".updateProduct] Call");
		return sqlSession.update("ProductMapper.updateProduct", productVO);
	}

	@Override
	public int deleteProduct(int prodNo) {
		System.out.println("[" + getClass().getName() + ".deleteProduct] Call");
		return sqlSession.delete("ProductMapper.deleteProduct", prodNo);
	}

	@Override
	public List<ProductVO> getProductList(SearchVO searchVO) {
		System.out.println("[" + getClass().getName() + ".getProductList] Call");
		return sqlSession.selectList("ProductMapper.getProductList", searchVO);
	}

	@Override
	public int getProductCount(SearchVO searchVO) {
		System.out.println("[" + getClass().getName() + ".getProductCount] Call");
		return sqlSession.selectOne("ProductMapper.getProductCount", searchVO);
	}

	@Override
	public int updateProductCount(Map<String, Integer> map) {
		System.out.println("[" + getClass().getName() + ".updateProductCount] Call");
		return sqlSession.update("ProductMapper.updateProductCount", map);
	}

	@Override
	public int addProductImage(FileVO file) {
		System.out.println("[" + getClass().getName() + ".addProductImage] Call");
		return sqlSession.insert("ProductMapper.addProductImage", file);
	}

	@Override
	public int updateAddProductImage(FileVO file) {
			System.out.println("[" + getClass().getName() + ".updateAddProductImage] Call");
			return sqlSession.insert("ProductMapper.updateAddProductImage", file);
	}

	@Override
	public List<FileVO> getProductImage(int prodNo) {
		System.out.println("[" + getClass().getName() + ".selectProductImage] Call");
		return sqlSession.selectList("ProductMapper.getProductImage", prodNo);
	}

	@Override
	public int deleteProductImage(int prodNo) {
		System.out.println("[" + getClass().getName() + ".deleteProductImage] Call");
		return sqlSession.delete("ProductMapper.deleteProductImage", prodNo);
	}

	@Override
	public List<ProductVO> getProductInfo() {
		System.out.println("[" + getClass().getName() + ".getProductInfo] Call");
		return sqlSession.selectList("ProductMapper.getProductInfo");
	}
}
