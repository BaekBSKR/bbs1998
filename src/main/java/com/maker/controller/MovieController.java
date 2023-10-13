package com.maker.controller;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.maker.domain.CustomerVO;
import com.maker.domain.MovieVO;
import com.maker.domain.PagingVO;
import com.maker.domain.ReviewVO;
import com.maker.service.MovieService;
import com.maker.service.ReviewService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@RequiredArgsConstructor
@RequestMapping("/movie/*")
@Log4j
public class MovieController {
	@Autowired
	private MovieService mSvc;
	@Autowired
	private ReviewService rSvc;
	
	@GetMapping("/movielist")
	public void movielist(PagingVO vo, Model model, @RequestParam(value = "nowPage", required = false) String nowPage,
			@RequestParam(value = "cntPerPage", required = false) String cntPerPage) {

		int total = mSvc.countBoard();
		if (nowPage == null && cntPerPage == null) {
			nowPage = "1";
			cntPerPage = "5";
		} else if (nowPage == null) {
			nowPage = "1";
		} else if (cntPerPage == null) {
			cntPerPage = "5";
		}
		vo = new PagingVO(total, Integer.parseInt(nowPage), Integer.parseInt(cntPerPage));

		List<MovieVO> movies = mSvc.getview();

		model.addAttribute("paging", vo);
		model.addAttribute("viewAll", mSvc.selectBoard(vo));
		model.addAttribute("movies", movies);
	}

	@GetMapping("/movieinfo")
	public void movieinfo(@RequestParam("m_title") String m_title, Model model,
			PagingVO vo,
			@RequestParam(value = "nowPage", required = false) String nowPage,
			@RequestParam(value = "cntPerPage", required = false) String cntPerPage) throws Exception {
    	//영화 정보 호출
		Long mno = Long.valueOf(mSvc.findMnoByTitle(m_title));
    	List<MovieVO> mvo = mSvc.readview(m_title);
    	
    	model.addAttribute("movies", mvo);
    	//리뷰 목록 호출
		List<ReviewVO> reviews = rSvc.getList(mno);
		
		int total = rSvc.getCnt(mno);
		if (nowPage == null && cntPerPage == null) {
			nowPage = "1";
			cntPerPage = "5";
		} else if (nowPage == null) {
			nowPage = "1";
		} else if (cntPerPage == null) {
			cntPerPage = "5";
		}
		vo = new PagingVO(total, Integer.parseInt(nowPage), Integer.parseInt(cntPerPage));

		model.addAttribute("paging", vo);
		model.addAttribute("viewReviewAll", rSvc.selectBoard(vo, mno));
		model.addAttribute("reviews", reviews);
	}
	@GetMapping("/reviewReg")
	public String reviewEnd() {
		return "redirect:/";
	}
	
	@PostMapping("/reviewReg")
	public String reviewSave(@RequestParam("m_title") String m_title,
			@RequestParam("c_nick") String c_nick,
			@RequestParam("r_comment") String r_comment,
			@RequestParam("r_star") float r_star,
			@RequestParam("mno") Long mno,
			@RequestParam("cno") Long cno,
	        RedirectAttributes attributes) {
		
		ReviewVO review = new ReviewVO(
				c_nick, r_comment, r_star, mno, cno
		);
		log.info("리뷰 정보 : " + review.toString());
		rSvc.register(review);
	    return "redirect:/";
	}
}