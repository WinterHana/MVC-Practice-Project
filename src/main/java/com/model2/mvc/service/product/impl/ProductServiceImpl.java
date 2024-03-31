package com.model2.mvc.service.product.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.common.util.CommonUtil;
import com.model2.mvc.service.domain.FileVO;
import com.model2.mvc.service.domain.ProductVO;
import com.model2.mvc.service.domain.UserVO;
import com.model2.mvc.service.product.ProductDAO;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.purchase.PurchaseDAO;

@Service("productServiceImpl")
public class ProductServiceImpl implements ProductService {
	
	@Autowired
	@Qualifier("productDAOImpl")
	private ProductDAO productDAO;
	
	@Autowired
	@Qualifier("purchaseDAOImpl")
	private PurchaseDAO purchaseDAO;
	
	public ProductServiceImpl() {
		System.out.println("[" + getClass().getName() + " Default Constructor] Call");
	}
	
	public ProductServiceImpl(ProductDAO productDAO) {
		System.out.println("[" + getClass().getName() + " ProductDAO set Constructor] Call");
		this.productDAO = productDAO;
	}
	
	public void setProductDAO(ProductDAO productDAO) {
		this.productDAO = productDAO;
	}
	
	@Override
	public ProductVO getProduct(int productId) {
		ProductVO product = null;
		List<FileVO> fileList = null;
		List<String> fileName = new ArrayList<String>();
		
		try {
			product = productDAO.getProduct(productId);
			fileList = productDAO.getProductImage(productId);
			if(fileList != null && fileList.size() > 0) {
				fileList.stream().forEach(s -> {
					fileName.add(s.getFileName());
				});
				
				product.setFileName(fileName);
			}
		} catch (Exception e) {
			System.out.println(getClass().getName() + ".findProduct Exception");
			e.printStackTrace();
		}
		
		return product;
	}
	
	@Override
	public Map<String, Object> getProductList(SearchVO searchVO) {	
		Map<String, Object> map = new HashMap<String, Object>();
		Map<Integer, String> fileNameMap  = new HashMap<Integer, String>();
		List<ProductVO> list = null;
		List<FileVO> fileList = null;
		List<String> fileName = new ArrayList<String>();
		
		int totalCount = 0;
		
		// 번호나 정수에 대해서 한글 입력에 대한 유효성 처리
		if(searchVO != null) {
			String searchCondition = searchVO.getSearchCondition();
			String serachKeyword = searchVO.getSearchKeyword();
			
			if(searchCondition != null && serachKeyword != null) {
				if (searchCondition.equals("prodNo") || searchCondition.equals("price")) {
					serachKeyword = CommonUtil.notNumToNum(serachKeyword);
				} else if (searchCondition.equals("prodName")) {
					serachKeyword = CommonUtil.null2str(serachKeyword);
				}
				System.out.println("searchKeyword : " + serachKeyword);
				
				searchVO.setSearchKeyword(serachKeyword);
			}
		}
		
		// productImage 관련 데이터를 가져옴
		try {
			list = productDAO.getProductList(searchVO);
			totalCount = productDAO.getProductCount(searchVO);
			for(ProductVO p : list) {
				// fileName을 가져와서 productVO에 따로 저장
				fileList = productDAO.getProductImage(p.getProdNo());
				if(fileList != null) {
					fileName.add(fileList.get(0).getFileName());
					p.setFileName(fileName);
					fileName = new ArrayList<String>();
				}
			}
		} catch (Exception e) {
			System.out.println(getClass().getName() + ".getProduct Exception");
			e.printStackTrace();
		}
		map.put("list", list);
		map.put("totalCount", totalCount);
		map.put("fileNameMap", fileNameMap);
		
		return map;
	}

	@Override
	public int addProduct(ProductVO productVO, List<MultipartFile> multipartFiles) {
		int result = 0;
		
		// 1. 제품 정보 추가
		try {
			result = productDAO.addProduct(productVO);
		} catch (Exception e) {
			System.out.println(getClass().getName() + ".addProduct Exception");
			e.printStackTrace();
		}
		
		// 2. 제품 이미지 추가
		// 0) 데이터 검증
		boolean test = true;
		for(MultipartFile f : multipartFiles) {
			String originalFileName = f.getOriginalFilename();
			if(originalFileName.equals("") || originalFileName == null) {
				test = false;
				break;
			}
		}
		try {
			if(multipartFiles != null && multipartFiles.size() > 0) {
				for(MultipartFile f : multipartFiles) {
					String originalFileName = f.getOriginalFilename();
					UUID uuid  = UUID.randomUUID();			// 유일자 식별은 java.util.UUID를 이용한다.
					String fileName = uuid + originalFileName;
					
					f.transferTo(new File("/Project_Eclipse/01.Model2MVCShop(stu)/src/main/webapp/resources/img/uploadFiles/" + fileName));		
					
					FileVO file = new FileVO();
					file.setFileName(fileName);
					productDAO.addProductImage(file);
				}
			}
		} catch (Exception e) {
			System.out.println(getClass().getName() + ".addProductImage Exception");
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public int updateProduct(ProductVO productVO, List<MultipartFile> multipartFiles) {
		int result = 0;
		
		// 1. 제품 정보 업데이트
		try {
			result += productDAO.updateProduct(productVO);
		} catch (Exception e) {
			System.out.println(getClass().getName() + ".updateProduct Exception");
			e.printStackTrace();
		}
		
		// 2. 제품 이미지 수정 : 기존에 있는 정보 삭제 후 다시 추가하기
		// 0) 데이터 검증
		boolean test = true;
		for(MultipartFile f : multipartFiles) {
			String originalFileName = f.getOriginalFilename();
			if(originalFileName.equals("") || originalFileName == null) {
				test = false;
				break;
			}
		}
		
		try {
			if(multipartFiles != null && multipartFiles.size() > 0 && test) {
				// 1) 삭제
				result += productDAO.deleteProductImage(productVO.getProdNo());
				
				// 2) 다시 이미지 추가
				for(MultipartFile f : multipartFiles) {
					String originalFileName = f.getOriginalFilename();
					UUID uuid  = UUID.randomUUID();			// 유일자 식별은 java.util.UUID를 이용한다.
					String fileName = uuid + originalFileName;
					
					f.transferTo(new File("/Project_Eclipse/01.Model2MVCShop(stu)/src/main/webapp/resources/img/uploadFiles/" + fileName));		
					
					FileVO file = new FileVO();
					file.setFileName(fileName);
					file.setProdNo(productVO.getProdNo());
					productDAO.updateAddProductImage(file);
				} 
			}
		} catch (Exception e) {
			System.out.println(getClass().getName() + ".addProductImage Exception");
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public int deleteProduct(ProductVO product) {
		int result = 0;
		try {
			result += purchaseDAO.deletePurchaseProdNo(product.getProdNo());
			result += productDAO.deleteProductImage(product.getProdNo());
			result += productDAO.deleteProduct(product.getProdNo());
		} catch (Exception e) {
			System.out.println(getClass().getName() + ".deleteProduct Exception");
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public int addProductImage(FileVO file) {
		int result = 0;
		
		try {
			result = productDAO.addProductImage(file);
		} catch (Exception e) {
			System.out.println(getClass().getName() + ".addProductImage Exception");
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public List<FileVO> getProductImage(int prodNo) {
		List<FileVO> result = null;
		
		try {
			result = productDAO.getProductImage(prodNo);
		} catch (Exception e) {
			System.out.println(getClass().getName() + ".updateProductImage Exception");
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public List<String> getProductInfo(String key) {
		List<ProductVO> queryResult = null;
		List<String> result = new ArrayList<String>();
		
		try {
			queryResult = productDAO.getProductInfo();
			if(queryResult != null && queryResult.size() >= 0) {
				queryResult.stream().forEach(s -> {
					if(key.equals("prodName")) {
						result.add(s.getProdName());
					} else if (key.equals("prodNo")) {
						result.add(String.valueOf(s.getProdNo()));
					}
				});
			}
			
		} catch (Exception e) {
			System.out.println("[" + getClass().getName() + ".getUserIdAndUserNames] Exception");
			e.printStackTrace();
		}
		
		return result.stream().distinct().collect(Collectors.toList());
	}
}
