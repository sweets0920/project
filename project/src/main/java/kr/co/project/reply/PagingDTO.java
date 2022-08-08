package kr.co.project.reply;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PagingDTO { // 페이징 클래스
	
	private int startPage;
	private int endPage;
	private int realPage;
	
}
