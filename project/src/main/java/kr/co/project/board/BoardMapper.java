package kr.co.project.board;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BoardMapper {
	
	public int insert(BoardVO vo); // 등록
	
	public int count(BoardVO vo); // 전체 카운트
	public List<BoardVO> list(BoardVO vo); // 해당 페이지 조회 // selectAll을 호출
	
	public BoardVO view(int no); // 상세글 조회 // selectOne을 호출
	
	//public int updateViewcount(int no); // 조회수 +1
	void updateViewcount(int no);
	
	public int update(BoardVO vo); // 수정
	public int delete(int no); // 삭제`

}
