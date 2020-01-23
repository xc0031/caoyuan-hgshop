package com.caoyuan.hgshop.controller;

import com.caoyuan.hgshop.domain.Spec;
import com.caoyuan.hgshop.service.SpecService;
import com.github.pagehelper.PageInfo;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class SpecController {

	@Reference(url="dubbo://localhost:20880",timeout=5000)
	private SpecService specService;
	
	@RequestMapping("/specList")
	public String list(Model model, Spec spec, @RequestParam(defaultValue="1")Integer pageNum, @RequestParam(defaultValue="2")Integer pageSize) {
		PageInfo<Spec> pageInfo = specService.list(spec, pageNum, pageSize);
		model.addAttribute("spec", spec);
		model.addAttribute("pageInfo", pageInfo);
		return "spec_list";
	}
	
	@RequestMapping("/specAdd")
	@ResponseBody
	public boolean specAdd(Spec spec){
		return specService.saveOrUpdateSpec(spec) > 0;
	}
	
	@RequestMapping("/getSpecById")
	@ResponseBody
	public Spec getSpecById(Integer id) {
		return specService.getSpecById(id);
	}
	
	@RequestMapping("/specDelete")
	@ResponseBody
	public boolean specDelete(Integer[] ids){
		return specService.deleteSpecByIds(ids) > 0;
	}

	/**
	 * 添加sku页面获取规格列表
	 * @return
	 */
	@RequestMapping("/specs")
	@ResponseBody
	public List<Spec> specs() {
		return specService.specs();
	}
}
