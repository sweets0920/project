package kr.co.project.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import kr.co.project.board.BoardMapper;
import kr.co.project.board.BoardVO;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/resources/config/servlet-context.xml")
@Log4j
public class BoardMapperTests {
	
	@Autowired
	public BoardMapper mapper; // 인터페이스 구현 클래스
	
	//@Test
	public void testObj() {
		log.info(mapper);
	}
	
	//@Test
	public void testInsert() {
		BoardVO vo = new BoardVO();
		vo.setTitle("new 제목");
		vo.setContent("new 내용");
		int result = 0;
		for (int i=0; i<50; i++) {
			result += mapper.insert(vo);
		}
		log.info("추가된 개수: " +result);
	}
	
	//@Test
//	public void testCount() {
//		Map map = new HashMap();
//		map.put("stype", "all");
//		map.put("sword", "수정");
//		int totalCount = mapper.count(map);
//		log.info("총 카운트: " +totalCount);
//	}
	
	//@Test
//	public void testList() {
//		Map map = new HashMap();
//		map.put("startIdx", 20);
//		map.put("pageRow", 10);
//		List<BoardVO> list = mapper.list(map);
//		list.forEach(vo->log.info(vo));
//		log.info("리스트 조회: " +list);
//	}
	
	//@Test
	public void testView() {
		mapper.updateViewcount(1);;
		log.info(mapper.view(1));
	}
	
	//@Test
	public void testUpdate() {
		BoardVO vo = new BoardVO();
		vo.setNo(33);
		vo.setTitle("제목이 수정되었습니다");
		vo.setContent("내용이 업뎃되었습니다");
		int r = mapper.update(vo);
		log.info("수정된 개수: " +r);
	}
	
	//@Test
	public void testDelete() {
		log.info("삭제: " +mapper.delete(23));
	}
	
}
