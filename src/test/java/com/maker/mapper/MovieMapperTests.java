package com.maker.mapper;

import com.maker.domain.MovieVO;
import com.maker.mapper.MovieMapper;

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
	public void testModify() {
		MovieVO movie = new MovieVO(" 기억의 밤", "2");
		mapper.update(movie);
		
	}
	@Test
	public void testTime() {
		mapper.getMovieTimeByTitle(" 성난 얼굴로 돌아보라");
	}
	@Test
	public void cntRv() {
		System.out.println(mapper.countStar(2L));
	}
	@Test
	public void updateStar() {
		mapper.updateStar(10, 4L);
	}
}