package com.maker.mapper;

import com.maker.mapper.MovieMapper;
import com.maker.vo.MovieVO;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml",
"file:src/main/webapp/WEB-INF/spring/root-context.xml"})
public class MovieMapperTests {
	@Autowired
	private MovieMapper mapper;
	
	@Test
	public void testRead() {
		
		MovieVO movie = new MovieVO("영화1", "이 영화는...", "감독", "이배우", "2022");
		mapper.insert(movie);
	}
}
