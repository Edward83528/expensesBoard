package com.board.expenses.expensesBoard.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.board.expenses.expensesBoard.service.ImportService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class IndexController {

	@Autowired
	private ImportService importService;

	@GetMapping(name = "index", path = "/")
	public String index(HttpSession session, final HttpServletRequest request, final ModelMap model) {
		model.addAttribute("name", "aassssss");
		return "index";
	}

	// 单一文件上传
	@PostMapping(name = "upload", path = "/upload")
	public String uploadFile(@RequestParam("file00") MultipartFile file, final ModelMap model) throws Exception {
		String msg = "";
		try {
			if (file.isEmpty()) {
				model.addAttribute("msg", "上传失败，请选择文件！");
				return "index";
			}
			InputStream inputStream = file.getInputStream();
			List<List<Object>> list = importService.getBankListByExcel(inputStream, file.getOriginalFilename());
			inputStream.close();

			for (int i = 0; i < list.size(); i++) {
				List<Object> lo = list.get(i);
				// TODO 随意发挥
				System.out.println(lo);

			}
			

		} catch (IOException e) {
			e.printStackTrace();
		}
		model.addAttribute("msg", "文件上传成功！");
		return "index";

	}

}
