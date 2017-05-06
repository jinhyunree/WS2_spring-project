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
	
	// ���� Ÿ���� void �� ��� ��� �̸�.jsp ������ ã�´�.
	@RequestMapping("/doA")
	public void doA(){
		logger.info("doA~~~~~~~~~");
	}
	
	// ���� Ÿ���� ���ڿ��� ��� ���ڿ�.jsp ������ ã�´�.
	@RequestMapping("/doB")
	public String doB(){
		logger.info("doB~~~~~~~~~");
		return "sampleB";
	}
	
	// @ModelAttribute �̿��Ͽ� �ش� ��ü�� ����� ����.
	@RequestMapping("/doC")
	public String doC(@ModelAttribute("msg") String msg){
		logger.info("doC~~~~~~~~~~");
		logger.info("msg:::::::::" + msg);
		
		return "sampleC";
	}
	
	// ��ü�� �����͸� �����ϴ� ��� model�� addAttribute�� �̿��� ������ ���� �� ����.
	@RequestMapping("/doD")
	public String doD(Model model){
		ProductVO vo = new ProductVO();
		vo.setName("Sample Product");
		vo.setPrice(3000);
		
		model.addAttribute(vo);
		logger.info(vo.getName() + vo.getPrice());
		return "sampleD";
	}
	
	// �����̷�Ʈ�� �Ͽ� �ٸ� ���� ȣ��. addFlashAttribute �޽����� �������� Ȯ��.
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
	
	
	// JSON ���̺귯�� ���� �� @ResponseBody�� �̿��Ͽ� ��ü ���� �� �������� ���� JSON ������ ������ Ȯ��.
	@RequestMapping("/doJSON")
	public @ResponseBody ProductVO doJSON(){
		ProductVO vo = new ProductVO("���û�ǰ", 30000);
		return vo;
	}
	
	
	// @WebAppConfiguration ������̼����� �ٷ� �� ���ø����̼� ����.
	// MockMvc ��ü ��� -> build(), perform() �޼ҵ� ���.
	@Inject
    private WebApplicationContext wac; // �� ���ø����̼� ������ ���� �׽�Ʈ �ȴ�.

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




