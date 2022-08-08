package kr.co.project.reply;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReplyServiceImpl implements ReplyService {

	@Autowired
	ReplyMapper mapper;
	
	@Override
	public Map index(ReplyVO vo) {
		int totalCount = mapper.count(vo); // 총 게시물 수
		// 총 페이지 수
		int totalPage = totalCount / vo.getPageRow();
		if (totalCount % vo.getPageRow() > 0) totalPage++;
		
		// 시작인덱스
		int startIdx = (vo.getPage() - 1) * vo.getPageRow();
		vo.setStartIdx(startIdx); // sql문에 파라미터로 넣어줌
		List<ReplyVO> list = mapper.list(vo); // 목록
		
		// 페이징처리
		int endPage = (int)(Math.ceil(vo.getPage()/10.0) * 10); // 끝페이지
		int startPage = endPage - 9; // 시작페이지
//		int realEnd = (int)(Math.ceil((totalPage * 1.0) / vo.getPageRow()));
		
		if (endPage > totalPage) endPage = totalPage;
		boolean prev = startPage > 1 ? true : false;
		boolean next = endPage < totalPage ? true : false;
				
		Map map = new HashMap();
		map.put("totalCount", totalCount);
		map.put("totalPage", totalPage);
		map.put("list", list); // 모델에 직접 넣어줘도 됨
		
		map.put("endPage", endPage);
		map.put("startPage", startPage);
		map.put("prev", prev);
		map.put("next", next );
		
		return map;

	}

	@Override
	public ReplyVO view(int no) {
		mapper.updateViewcount(no);
		return mapper.view(no);
	}

	@Override
	public ReplyVO edit(int no) {
		return mapper.view(no);
	}

	@Override
	public boolean update(ReplyVO vo) {
		return mapper.update(vo)>0 ? true : false;
	}

	@Override
	public boolean delete(int no) {
		return mapper.delete(no)>0 ? true : false;
	}

	@Override
	public boolean insert(ReplyVO vo) { // 등록하고 gno 업뎃해야
		boolean r = mapper.insert(vo) > 0 ? true : false; // 등록여부체크
		if (r) mapper.gnoUpdate(vo.getNo()); // 업뎃
		return r;
	}
	
	@Override
	public boolean reply(ReplyVO vo) {
		mapper.onoUpdate(vo); // 부모의 gno와 같고, 부모의 ono보다 큰 ono+1
		vo.setOno(vo.getOno()+1);
		vo.setNested(vo.getNested()+1);
		return mapper.reply(vo) > 0 ? true : false;
	}
	

}
