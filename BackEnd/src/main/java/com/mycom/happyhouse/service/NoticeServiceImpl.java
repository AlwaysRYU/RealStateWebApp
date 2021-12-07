package com.mycom.happyhouse.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.mycom.happyhouse.dao.NoticeDao;
import com.mycom.happyhouse.dto.BoardDto;
import com.mycom.happyhouse.dto.BoardFileDto;
import com.mycom.happyhouse.dto.BoardParamDto;
import com.mycom.happyhouse.dto.BoardResultDto;
import com.mycom.happyhouse.dto.NoticeDto;
import com.mycom.happyhouse.dto.NoticeFileDto;
import com.mycom.happyhouse.dto.NoticeParamDto;
import com.mycom.happyhouse.dto.NoticeResultDto;

@Service
public class NoticeServiceImpl implements NoticeService{
	
	@Autowired
	NoticeDao dao;
	
	static final String uploadFolder = "upload";
	
	/* for production code */
    //uploadPath = getServletContext().getRealPath("/");
    // F:\SSAFY\ssafy5\.metadata\.plugins\org.eclipse.wst.server.core\tmp0\wtpwebapps\BoardWebFileUpload\
    
	// 파일 구분자를 이용해 upload경로를 지정해준다.
    /* for eclipse development code */
	private static final String uploadPath = "C:" + File.separator + "SSAFY_SpringBoot"
            + File.separator + "Happy_House_5_Spring" 
            + File.separator + "src" 
            + File.separator + "main"
            + File.separator + "resources"
            + File.separator + "static";
    
    private static final int SUCCESS = 1;
    private static final int FAIL = -1;
    //MulrtipartHttpServltRequest를 통해 입력받은 파일을 처리한다. 
    // Lsut에 넘겨받은 각각의 파일은 순차적으로 저장한다.
    // uploadDir에서는 입력받은 파일들의 경로를 지정해주며 만약 경로가 지정되어 있지 않다면 mkdir를 통해 경로를 자동으로 지정해준다.
    // for문을 통해 리스트에 저장된 파일들의 이름을 받아 파일들의 아이디가 겹치지 않도록  randmUUID를 통해 값을 지정해주고
    // extension에는 파일이름을 받아 확장자명을 받아온다.
    // 이후 uuid에 저장된 파일의 id와 extension에 저장된 확장자 명을 연결하여 최종적으로 파일의 경로를 만들어 준다.
    // 해당 경로(이름)을 list에 저장해준다.
    // boardFileDto에는 해당 파일의 id, 이름, 크기 값들을 저장하고 지정한 파일의 경로를 이름으로 uploadFolder에 저장
    @Override
    @Transactional // 분기해서 받아온다.
    public NoticeResultDto boardInsert(NoticeDto dto, MultipartHttpServletRequest request) {// BoardController에서 파일을 넘겨 받음

        NoticeResultDto boardResultDto = new NoticeResultDto();

        try {
            dao.boardInsert(dto); // useGeneratedKeys="true" keyProperty="boardId"  dto에 key값이 들어간다.

            List<MultipartFile> fileList = request.getFiles("file"); // file를 MultiparFile로 넘겨줌으로 각각의 파일이 filelist에 저장된다.

            File uploadDir = new File(uploadPath + File.separator + uploadFolder); // 경로지정
            if (!uploadDir.exists()) uploadDir.mkdir(); // 만약 경로가 없다면 자동으로 만들어 준다.

        for (MultipartFile part : fileList) {

            int boardId = dto.getBoardId(); // DB에서 auto incremernt된 값들이 넘어온다.
            
            String fileName = part.getOriginalFilename(); // 실제로 첨부했을때 file 이름
            
            //Random File Id
            UUID uuid = UUID.randomUUID(); // id가 중복되지 않도록 해준다. 
            
            //file extension
            String extension = FilenameUtils.getExtension(fileName); // vs FilenameUtils.getBaseName() // 확장자
        
            String savingFileName = uuid + "." + extension; // 파일 이름과 확장자를 붙인다.
        
            File destFile = new File(uploadPath + File.separator + uploadFolder + File.separator + savingFileName); // 최종 경로
            
            System.out.println(uploadPath + File.separator + uploadFolder + File.separator + savingFileName);
            part.transferTo(destFile); // part를 destFile쪽으로 넘긴다.	
        
            // Table Insert
            NoticeFileDto boardFileDto = new NoticeFileDto();
            boardFileDto.setBoardId(boardId);
            boardFileDto.setFileName(fileName);
            boardFileDto.setFileSize(part.getSize());
            boardFileDto.setFileContentType(part.getContentType());
            String boardFileUrl = uploadFolder + "/" + savingFileName;
            boardFileDto.setFileUrl(boardFileUrl);
            
            dao.boardFileInsert(boardFileDto);
        }

        boardResultDto.setResult(SUCCESS);
        
    }catch(IOException e) {
        e.printStackTrace();
        boardResultDto.setResult(FAIL);
    }
    return boardResultDto;
}

    // boardUpdate는 파일을 덮어 씌우는 형식이 아닌 update할 파일과 같은 boardId를 가진 파일을 삭제한뒤 update할 파일을 insert하는 방식이다.
    // 먼저 fileList에 update할 파일을 받아 온다.
    // 입력받은 파일의 경로를 지정해준다.
    // fileUrlList에는 boardId를 통해 해당 id값에 잇는 파일의 물리적 경로를 저장한다.
    // 이후 fileUrlList에 저장된 경로에 파일이 존재하면 파일을 지워준다. 테이블에서도 삭제해준다.
    // 이후 boardInsert와 동일
	@Override
	@Transactional
	public NoticeResultDto boardUpdate(NoticeDto dto, MultipartHttpServletRequest request){
	    
		NoticeResultDto boardResultDto = new NoticeResultDto();
	
	    
	    try {
	        dao.boardUpdate(dto); // board table update
	
	        List<MultipartFile> fileList = request.getFiles("file");
	        
	        File uploadDir = new File(uploadPath + File.separator + uploadFolder);
	        if (!uploadDir.exists()) uploadDir.mkdir();
	        
	        // 물리 파일 삭제, 첨부파일 여러개 고려, 파일 경로를 찾는다.
	        List<String> fileUrlList = dao.boardFileUrlDeleteList(dto.getBoardId());    
	        for(String fileUrl : fileUrlList) {    // DB가 아닌 물리적 경로에 잇는 파일을 삭제 하기 때문에 url 경로를 가져온다.
	            File file = new File(uploadPath + File.separator, fileUrl);
	            if(file.exists()) {
	                file.delete();
	            }
	        }
	
	        dao.boardFileDelete(dto.getBoardId()); // 테이블 파일 삭제
	        
	        // 새로운 첨부 파일이 있다면 insert
	        for (MultipartFile part : fileList) {
	            int boardId = dto.getBoardId();
	            
	            String fileName = part.getOriginalFilename();
	            
	            //Random File Id
	            UUID uuid = UUID.randomUUID();
	            
	            //file extension
	            String extension = FilenameUtils.getExtension(fileName); // vs FilenameUtils.getBaseName()
	        
	            String savingFileName = uuid + "." + extension;
	        
	            File destFile = new File(uploadPath + File.separator + uploadFolder + File.separator + savingFileName);
	            
	            System.out.println(uploadPath + File.separator + uploadFolder + File.separator + savingFileName);
	            part.transferTo(destFile);
	        
	            // Table Insert 
	            NoticeFileDto boardFileDto = new NoticeFileDto();
	            boardFileDto.setBoardId(boardId);
	            boardFileDto.setFileName(fileName);
	            boardFileDto.setFileSize(part.getSize());
	            boardFileDto.setFileContentType(part.getContentType());
	            String boardFileUrl = uploadFolder + "/" + savingFileName;
	            boardFileDto.setFileUrl(boardFileUrl);
	            
	            dao.boardFileInsert(boardFileDto);
	        }
	
	        boardResultDto.setResult(SUCCESS);
	        
	    }catch(IOException e) {
	        e.printStackTrace();
	        boardResultDto.setResult(FAIL);
	    }
	    
	    return boardResultDto;
	}
	
	
	// boardId를 통해 file을 삭제한다.
	// 먼저 fileUrlList에 삭제할 경로를 boarId를 통해 저장하고 해당 경로에 file이 존재하면 삭제
	// 삭제 할 시에는 보드에서 파일을 삭제하고, 조회수 삭제  후 마지막으로 물리적경로에 있는 파일을 삭제해준다.
	@Override
	@Transactional
	public NoticeResultDto boardDelete(int boardId) {
	    
		NoticeResultDto boardResultDto = new NoticeResultDto();
	    
	    try {
	        List<String> fileUrlList = dao.boardFileUrlDeleteList(boardId);
	        for(String fileUrl : fileUrlList) { // file 물리적 삭제
	            File file = new File(uploadPath + File.separator, fileUrl);                
	            if(file.exists()) {
	                file.delete();
	            }
	        }
	        
	        dao.boardFileDelete(boardId);
	        dao.boardReadCountDelete(boardId);
	        dao.boardDelete(boardId);   // 원본글을 삭제    이걸 마지막으로 삭제 해야함, 부모이기 떄문에 먼저 삭제 하면 앞에 것들에서 오류가난다.
	        boardResultDto.setResult(SUCCESS);
	        
	    }catch(Exception e) {
	        e.printStackTrace();
	        boardResultDto.setResult(FAIL);
	    }
	    
	    return boardResultDto;
	}
	
	// 상세조회
	// 먼저 해당 글을 읽었는지 파악한다. 만약 읽은적이 없다면 
	
	// 이후 게시글을 불러온다 . 이때 만약 글을 불러온사람이 현재 session에 저장된 사람이라면 글을 수정, 삭제 할수 잇는 버튼을 보여주도록 true
	// session에 저장된 사람이 아니라면 false를 넘겨줘 버튼이 뜨지 않도록 한다.(SameUser를 통해 ture, false를 넘겨준다.)
	@Override
	public NoticeResultDto boardDetail(NoticeParamDto boardParamDto) {
	    
		NoticeResultDto boardResultDto = new NoticeResultDto();
	    
	    try {
	        int userReadCnt = dao.boardUserReadCount(boardParamDto);
	        if( userReadCnt == 0 ) { // 읽은적이 없다면...
	            dao.boardUserReadInsert(boardParamDto.getBoardId(), boardParamDto.getUserSeq());
	            dao.boardReadCountUpdate(boardParamDto.getBoardId());
	        }
	        
	        NoticeDto boardDto = dao.boardDetail(boardParamDto); // 게시글을 읽어온다.
	        if( boardDto.getUserSeq() == boardParamDto.getUserSeq() ) { // session에 있는 읽는사람이랑 게시한 사람이 같다면
	            boardDto.setSameUser(true); // 수정 삭제 버튼을 보여줄지 판단하도록 true를 넘겨준다.
	        }else {
	            boardDto.setSameUser(false); // // session에 있는 읽는사람이랑 게시한 사람이 다르다면
	        }
	        
	        List<NoticeFileDto> fileList = dao.boardDetailFileList(boardDto.getBoardId());
	
	        boardDto.setFileList(fileList);
	        boardResultDto.setDto(boardDto);
	
	        boardResultDto.setResult(SUCCESS);
	        
	    }catch(Exception e) {
	        e.printStackTrace();
	        boardResultDto.setResult(FAIL);
	    }
	    
	    return boardResultDto;
	}
	// 리스트를 생성
	// board와 user를 join한 결과 값들을 list에 순차적으로 저장한다.
	// count는 board에 있는 튜플의 개수로 지정
	// 리스트, 개수, 전송 성공을 return
	@Override
	public NoticeResultDto boardList(NoticeParamDto boardParamDto) {
	    
		NoticeResultDto boardResultDto = new NoticeResultDto();
	    
	    try {
	        List<NoticeDto> list = dao.boardList(boardParamDto);
	        int count = dao.boardListTotalCount();    // service에서 모두 계산해서 넘겨온다.
	        boardResultDto.setList(list);
	        boardResultDto.setCount(count);
	        boardResultDto.setResult(SUCCESS);
	        
	    }catch(Exception e) {
	        e.printStackTrace();
	        boardResultDto.setResult(FAIL);   // 오류 발생시
	    }
	    
	    return boardResultDto;
	}
	
	// 리스트를 생성
	// board와 user를 join한 결과 중 찾는 단어를 포함하는 값들을 list에 순차적으로 저장한다.
	// count는 board에 찾는 단어를 포함하는 튜플의 개수로 지정
	// 리스트, 개수, 전송 성공을 return
	@Override
	public NoticeResultDto boardListSearchWord(NoticeParamDto boardParamDto) {
	    
		NoticeResultDto boardResultDto = new NoticeResultDto();
	    
	    try {
	        List<NoticeDto> list = dao.boardListSearchWord(boardParamDto);
	        int count = dao.boardListSearchWordTotalCount(boardParamDto);
	        
	        boardResultDto.setList(list);
	        boardResultDto.setCount(count);
	        
	        boardResultDto.setResult(SUCCESS);
	    
	    }catch(Exception e) {
	        e.printStackTrace();
	        boardResultDto.setResult(FAIL);
	    }
	    
	    return boardResultDto;
	}

}
