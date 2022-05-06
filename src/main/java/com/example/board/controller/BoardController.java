package com.example.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.board.repository.PostFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.board.repository.PostRepository;
import com.example.board.repository.Post;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * 掲示板のフロントコントローラー.
 */
@Controller
public class BoardController {
	
	/** 投稿の一覧 */
	@Autowired
	private PostRepository repository;
	

	/**
	 * 一覧を表示する。
	 *
	 * @param model モデル
	 * @return テンプレート
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Model model) {
		model.addAttribute("form", PostFactory.newPost());
		model = this.setList(model);
		model.addAttribute("path", "create");
		
		return "layout";
	}
	
	/**
	 * 一覧を設定する。
	 *
	 * @param model モデル
	 * @return 一覧を設定したモデル
	 */
	private Model setList(Model model) {
		Iterable<Post> list = repository.findAll();
		model.addAttribute("list", list);
		return model;
	}
	
    /**
    * 登録する。
    *
    * @param form  フォーム
    * @param model モデル
    * @return テンプレート
    */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create(@ModelAttribute("form") Post form, BindingResult result,
            Model model) {
        if (!result.hasErrors()) {
            repository.saveAndFlush(PostFactory.createPost(form));
            model.addAttribute("form", PostFactory.newPost());
        }
        model = this.setList(model);
        model.addAttribute("path", "create");
        return "layout";
    }
	
}