package utils;

import java.io.IOException;
import java.io.Reader;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.model2.mvc.service.domain.UserVO;

public class SqlSessionFactoryBean {
	
	public static SqlSession getSqlSession() {
		// 1. Read meta-data by stream
		Reader reader = null;
		try {
			reader = Resources.getResourceAsReader("sql/mybatis-config.xml");
		} catch (IOException e) {
			System.out.println("IOException in SqlSessionFactoryBean.getSqlSession");
			e.printStackTrace();
		}
		
		// 2. make SqlSessionFactory instance
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
		
		// 3. autoCommit true SqlSession instance
		SqlSession sqlSession = sqlSessionFactory.openSession(true);
		
		return sqlSession;
	}
		
	public static void printList(List<UserVO> list) {
		for(UserVO o : list) {
			System.out.println(o.toString());
		}
	}
}
