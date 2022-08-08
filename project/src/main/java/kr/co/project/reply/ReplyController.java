package kr.co.project.reply;

import java.io.File;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import kr.co.project.comment.CommentService;
import kr.co.project.comment.CommentVO;
import kr.co.project.member.MemberVO;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping
@Slf4j
public class ReplyController {
	
	@Autowired
	ReplyService service;
	@Autowired
	CommentService cService;
	
	// 목록
	@GetMapping("/reply/index.do")
	public String index(Model model, ReplyVO vo) { // 전송할 모델 객체, vo 커멘드객체
		model.addAttribute("data", service.index(vo)); // data에는 map 객체가 들어있음
		return "reply/index";
	}
	// 등록폼
	@GetMapping("/reply/write.do")
	public String write() { // 로그인한 사람만 볼 수 있게 하려면 세션에 loginInfo 가 있는지 확인해서 있으면 뜨도록
		return "reply/write";
	}
	
	// 등록처리
	@PostMapping("/reply/insert.do")
	public String insert(ReplyVO vo, Model model,
			@RequestParam MultipartFile filename, // 멀티파트파일 타입으로 파일을 전송
			HttpServletRequest req) { 
		// 첨부파일 처리
		// 이름 중 확장자 lastIndexOf 날짜를 숫자로 바꿔 새로 저장
		if (!filename.isEmpty()) {
			// 파일명 구하기
			String org = filename.getOriginalFilename(); // 사용자가 첨부한 첨부파일명
			String ext = org.substring(org.lastIndexOf(".")); // 확장자 추출
			String real = new Date().getTime()+ext; // 실제 파일명
			
			// 파일 저장
			String path = req.getRealPath("/upload"); // 경로 불러오는 방법 중 하나 - request에서
			try {
				filename.transferTo(new File(path+real));
			} catch (Exception e) {}
			
			vo.setFilename_org(org);
			vo.setFilename_real(real);
		}
		
		// member_no 저장 // write.jsp 에서 하기보다 안전
		HttpSession sess = req.getSession();
		MemberVO mv = (MemberVO)sess.getAttribute("loginInfo"); // 세션에서 loginInfo 꺼내
		if (mv != null) vo.setMember_no(mv.getNo()); // mv에 있는 no만 꺼내 vo에 set
		
		if (service.insert(vo)) { // 여기 vo 안에 no를 갖고 있게 하려면 어떻게 해야 하나?
			model.addAttribute("msg", "정상적으로 저장되었습니다.");
			model.addAttribute("url", "index.do"); // 저장 성공 후 게시판으로 이동
			return "common/alert";
		} else {
			model.addAttribute("msg", "저장 실패했습니다.");
			return "common/alert";
		}
	}
	// 답변폼
	@GetMapping("/reply/reply.do")
	public String reply(ReplyVO vo, Model model) {
		ReplyVO data = service.edit(vo.getNo());
		model.addAttribute("data", data);
		return "reply/reply";
	}
	
	// 답변처리
	@PostMapping("/reply/reply.do")
	public String reply(ReplyVO vo, Model model,
			@RequestParam MultipartFile filename, // 멀티파트파일 타입으로 파일을 전송
			HttpServletRequest req) {
		// 첨부파일 처리
		// 이름 중 확장자 lastIndexOf 날짜를 숫자로 바꿔 새로 저장
		if (!filename.isEmpty()) {
			// 파일명 구하기
			String org = filename.getOriginalFilename(); // 사용자가 첨부한 첨부파일명
			String ext = org.substring(org.lastIndexOf(".")); // 확장자 추출
			String real = new Date().getTime()+ext; // 실제 파일명
			
			// 파일 저장
			String path = req.getRealPath("/upload"); // 경로 불러오는 방법 중 하나 - request에서
			try {
				filename.transferTo(new File(path+real));
			} catch (Exception e) {}
			
			vo.setFilename_org(org);
			vo.setFilename_real(real);
		}
		
		// member_no 저장 // write.jsp 에서 하기보다 안전
		HttpSession sess = req.getSession();
		MemberVO mv = (MemberVO)sess.getAttribute("loginInfo"); // 세션에서 loginInfo 꺼내
		if (mv != null) vo.setMember_no(mv.getNo()); // mv에 있는 no만 꺼내 vo에 set
		
		if (service.reply(vo)) { // 여기 수정해야 새글 아닌 답변이 등록됨
			model.addAttribute("msg", "정상적으로 저장되었습니다.");
			model.addAttribute("url", "index.do"); // 저장 성공 후 게시판으로 이동
			return "common/alert";
		} else {
			model.addAttribute("msg", "저장 실패했습니다.");
			return "common/alert";
		}
	}
	// 상세
	@GetMapping("/reply/view.do")
	public String view(ReplyVO vo, Model model) {
		
		ReplyVO data = service.view(vo.getNo());
		model.addAttribute("data", data);
		//cvo.setreply_no(vo.getNo()); // 댓글리스트 view 아래에 붙여서 처리하느 방식 // 매개변수 CommentVO cvo 필요
		//cvo.setTablename("reply");
		//model.addAttribute("comment", cService.index(cvo));
		return "reply/view";
	}
	// 수정폼
	@GetMapping("/reply/edit.do")
	public String edit(ReplyVO vo, Model model) {
		ReplyVO data = service.edit(vo.getNo());
		model.addAttribute("data", data);
		return "reply/edit";
	}
	
	// 수정처리
	@PostMapping("/reply/update.do")
	public String update(ReplyVO vo, Model model) {
		if (service.update(vo)) {
			model.addAttribute("msg", "정상적으로 수정되었습니다." );
			model.addAttribute("url", "view.do?no=" +vo.getNo());
			return "common/alert";
		} else {
			model.addAttribute("msg", "수정 실패하였습니다.");
			return "common/alert";
		}
	}
	// 삭제처리
	@GetMapping("/reply/delete.do")
	public String delete(ReplyVO vo, Model model) {
		if (service.delete(vo.getNo())) {
			model.addAttribute("msg", "정상적으로 삭제되었습니다." );
			model.addAttribute("url", "index.do");
			return "common/alert";
		} else {
			model.addAttribute("msg", "삭제 실패하였습니다.");
			return "common/alert";
		}
	}
	
}














