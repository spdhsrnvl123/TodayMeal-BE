package app.todaymealvote_backend.controller;
import app.todaymealvote_backend.dto.UserDTO;
import app.todaymealvote_backend.dto.VoteDTO;
import app.todaymealvote_backend.service.LocationService;
import app.todaymealvote_backend.service.MenuService;
import app.todaymealvote_backend.service.VoteService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import app.todaymealvote_backend.service.UserService;

@CrossOrigin("origins = 'http://localhost:4200")
@RestController
public class MainController {
    @Autowired
    UserService userService;
    @Autowired
    LocationService locationService;
    @Autowired
    MenuService menuService;
    @Autowired
    VoteService voteService;

    //장소등록
    @PostMapping("/register")
    public Object register(@RequestBody String id) throws JsonProcessingException {
        return locationService.save(id);
    }

    //장소조회 - 등록한 장소 조회
//    @GetMapping("/location")
//    public Object locationFindAll(){
//        return locationService.findAll();
//    }
    @GetMapping("/location")
    public Object locationFindAll(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String sessionId = session.getId();
        return locationService.findAll();
    }

    //장소조회 - 삭제대기 장소 조회
    @GetMapping("/loading")
    public Object loadingLocation(){
        return locationService.loadingLocation();
    }

    //장소삭제
    @GetMapping("/location/delete/{id}")
    public Object del(@PathVariable String id){
        return locationService.del(id);
    }

    //장소삭제 취소
    @GetMapping("/loading/cancel/{id}")
    public Object loadingCancel(@PathVariable String id){
        return locationService.loadingCancel(id);
    }

    //메뉴조회
    @GetMapping("/menu/{id}")
    public Object menuFindAll(@PathVariable String id){ return menuService.menuFindAll(id); }

    //각 장소 조회
    @GetMapping("/location/{id}")
    public Object locationFindEach(@PathVariable String id){
        return locationService.findOne(id);
    }

    //유저 정보 조회 - 중복검사
    @GetMapping("/duplication/{id}")
    public int duplicationFindAll(@PathVariable String id) {
        return userService.duplicationFindAll(id);
    }

    //회원가입
    @PostMapping("/join")
    public int join(@RequestBody UserDTO userDTO){
        return userService.joinProcess(userDTO);
    }

    //투표등록(최종완료)
    @PostMapping("/vote")
    public ResponseEntity<String> vote(@RequestBody VoteDTO voteDTO){
        voteService.vote(voteDTO);
        return ResponseEntity.ok("{\"message\": \"투표가 완료되었습니다.\"}");
    }

    //투표삭제(최종완료)
    @DeleteMapping("/vote/{user_id}")
    public ResponseEntity<String> voteDel(@PathVariable String user_id){
        voteService.voteDel(user_id);
        return ResponseEntity.ok("{\"message\": \"삭제가 완료되었습니다.\"}");
    };

    //투표조회(최종완료)
    @GetMapping("/vote")
    public Object getLocationVoteCount(){
        return locationService.getLocationVoteCount();
    }

    //내가 등록한 투표 조회
    @GetMapping("/vote/{user_id}")
    public Object voteUser(@PathVariable String user_id){
        return voteService.voteUser(user_id);
    };

    //회원탈퇴
    @PostMapping("/user/cancel")
    public int userLeave(@RequestBody Object userInfo){
        return userService.userLeave(userInfo);
    }

    //비밀번호 변경
    @PostMapping("/user/password")
    public int userChange(@RequestBody Object userInfo){
        System.out.println(userInfo);
        return userService.userChange(userInfo);
    }
}
