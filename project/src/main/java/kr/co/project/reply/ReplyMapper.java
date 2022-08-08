package kr.co.project.reply;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReplyMapper {
	
	public int insert(ReplyVO vo); // 등록
	
	public int count(ReplyVO vo); // 전체 카운트
	public List<ReplyVO> list(ReplyVO vo); // 해당 페이지 조회 // selectAll을 호출
	
	public ReplyVO view(int no); // 상세글 조회 // selectOne을 호출
	
	//public int updateViewcount(int no); // 조회수 +1
	void updateViewcount(int no);
	
	public int update(ReplyVO vo); // 수정
	public int delete(int no); // 삭제
	
	int gnoUpdate(int gno);
	int onoUpdate(ReplyVO vo);
	int reply(ReplyVO vo);

}
