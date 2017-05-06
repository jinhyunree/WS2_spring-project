package org.hyunree.web;

import javax.inject.Inject;

import org.hyunree.domain.ProductVO;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(
		locations ={"file:src/main/webapp/WEB-INF/spring/**/*.xml"})
@Controller
public class SampleControllerTest {
	
	private static final Logger logger = 
			LoggerFactory.getLogger(SampleControllerTest.class);
	
	// 리턴 타입이 void 인 경우 경로 이름.jsp 파일을 찾는다.
	@RequestMapping("/doA")
	public void doA(){
		logger.info("doA~~~~~~~~~");
	}
	
	// 리턴 타입이 문자열인 경우 문자열.jsp 파일을 찾는다.
	@RequestMapping("/doB")
	public String doB(){
		logger.info("doB~~~~~~~~~");
		return "sampleB";
	}
	
	// @ModelAttribute 이용하여 해당 객체를 뷰까지 전달.
	@RequestMapping("/doC")
	public String doC(@ModelAttribute("msg") String msg){
		logger.info("doC~~~~~~~~~~");
		logger.info("msg:::::::::" + msg);
		
		return "sampleC";
	}
	
	// 객체의 데이터를 전달하는 경우 model의 addAttribute를 이용해 데이터 전달 및 보관.
	@RequestMapping("/doD")
	public String doD(Model model){
		ProductVO vo = new ProductVO();
		vo.setName("Sample Product");
		vo.setPrice(3000);
		
		model.addAttribute(vo);
		logger.info(vo.getName() + vo.getPrice());
		return "sampleD";
	}
	
	// 리다이렉트를 하여 다른 파일 호출. addFlashAttribute 메시지는 브라우저로 확인.
	@RequestMapping("/doE")
	public String doE(RedirectAttributes rttr){
		logger.info("doE called but redirect to /doF~~~~~~~~");
		
		rttr.addFlashAttribute("msg", "This is the Message! with redirected!");
		//rttr.addAttribute("msg", "This is the Message! with redirected");
		logger.info("msg:::"+rttr.toString());
		return "redirect:/doF";
	}
	
	@RequestMapping("/doF")
	public void doF(String msg){
		logger.info("doF called~~~~~~~~~" + msg);
	}
	
	
	// JSON 라이브러리 설정 후 @ResponseBody를 이용하여 객체 전달 후 브라우저를 통해 JSON 형식의 데이터 확인.
	@RequestMapping("/doJSON")
	public @ResponseBody ProductVO doJSON(){
		ProductVO vo = new ProductVO("샘플상품", 30000);
		return vo;
	}
	
	
	// @WebAppConfiguration 어노테이션으로 바로 웹 어플리케이션 실행.
	// MockMvc 객체 사용 -> build(), perform() 메소드 사용.
	@Inject
    private WebApplicationContext wac; // 웹 어플리케이션 단위로 실행 테스트 된다.

    private MockMvc mockMvc;
    
    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
        logger.info("setup............");
    }
    
    @Test
    public void testDoA() throws Exception{
    	mockMvc.perform(MockMvcRequestBuilders.get("/doA"));
    }

}




