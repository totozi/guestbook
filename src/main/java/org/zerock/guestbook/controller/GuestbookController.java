package org.zerock.guestbook.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.guestbook.dto.GuestbookDTO;
import org.zerock.guestbook.dto.PageRequestDTO;
import org.zerock.guestbook.entity.Guestbook;
import org.zerock.guestbook.service.GuestbookService;

@Controller
@RequestMapping("/guestbook")
@Log4j2
@RequiredArgsConstructor
public class GuestbookController {

    private final GuestbookService service;

    @GetMapping({"/"})
    public String list() {
        log.info("list..................");
        return "/guestbook/list";
    }

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model) {
        log.info("list......" + pageRequestDTO);

        model.addAttribute("result", service.getList(pageRequestDTO));
    }

    @PostMapping("/remove")
    public String remove(long gno, RedirectAttributes redirectAttributes){
        log.info("gno : " + gno);

        service.remove(gno);

        redirectAttributes.addFlashAttribute("msg", gno);

        return "redirect:/guestbook/list";
    }

    @PostMapping("/modify")
    public String modify(GuestbookDTO dto
                       , @ModelAttribute("requestDTO") PageRequestDTO requestDTO
                       , RedirectAttributes redirectAttributes){
        log.info("post modify....................");
        log.info("dto : " + dto);

        service.modify(dto);

        redirectAttributes.addFlashAttribute("page", requestDTO.getPage());
        redirectAttributes.addFlashAttribute("gno", dto.getGno());

        // TODO  addFlashAttribute 변수를 read 메서드가 받지 못함
        return "redirect:/guestbook/read?page="+requestDTO.getPage()+"&gno="+dto.getGno();
    }

    @GetMapping({"/read", "/modify"})
    public void read(long gno, @ModelAttribute("requestDTO") PageRequestDTO requestDTO, Model model) {
        log.info("read............");
        GuestbookDTO dto = service.read(gno);
        model.addAttribute("dto", dto);
    }

    @GetMapping("/register")
    public void register() {
        log.info("register get...");
    }

    @PostMapping("/register")
    public String registerPost(GuestbookDTO dto, RedirectAttributes redirectAttributes) {
        log.info("dto....." + dto);

        // 새로 추가된 엔티티의 번호
        Long gno = service.register(dto);
        // RedirectAttributes를 이용하여 화면에서 한 번만 msg를 이용할 수 있다.
        redirectAttributes.addFlashAttribute("msg", gno);

        return "redirect:/guestbook/list";
    }
}