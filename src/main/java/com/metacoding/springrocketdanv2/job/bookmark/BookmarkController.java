package com.metacoding.springrocketdanv2.job.bookmark;

import com.metacoding.springrocketdanv2._core.error.ex.ExceptionApi400;
import com.metacoding.springrocketdanv2._core.error.ex.ExceptionApi403;
import com.metacoding.springrocketdanv2.jobBookmark.JobBookmarkRepository;
import com.metacoding.springrocketdanv2.jobBookmark.JobBookmarkRequest;
import com.metacoding.springrocketdanv2.jobBookmark.JobBookmarkResponse;
import com.metacoding.springrocketdanv2.jobBookmark.JobBookmarkService;
import com.metacoding.springrocketdanv2.user.UserResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BookmarkController {
    private final JobBookmarkRepository jobBookmarkRepository;
    private final JobBookmarkService jobBookmarkService;

    @GetMapping("/job")
    public String jobList(HttpSession session, Model model) {
        UserResponse.SessionUserDTO sessionUser = (UserResponse.SessionUserDTO) session.getAttribute("sessionUser");

        Integer sessionUserId = (sessionUser != null) ? sessionUser.getId() : null;

        List<JobBookmarkResponse.JobListWithBookmarkDTO> dtoList = jobBookmarkService.getAllJobsWithBookmarkInfo(sessionUserId); // null이면 북마크 표시 없이

        Long bookmarkCount = (sessionUserId != null) ? jobBookmarkService.count(sessionUserId) : 0L;

        model.addAttribute("models", dtoList);
        model.addAttribute("bookmarkCount", bookmarkCount);
        return "job/list";
    }

    @Transactional
    @PostMapping("/job/{jobId}/bookmark")
    public String toggle(@PathVariable("jobId") Integer jobId, HttpSession session) {
        UserResponse.SessionUserDTO sessionUser = (UserResponse.SessionUserDTO) session.getAttribute("sessionUser");

        JobBookmarkRequest.SaveDTO dto = new JobBookmarkRequest.SaveDTO();
        dto.setJobId(jobId);

        jobBookmarkService.북마크토글(dto, sessionUser.getId());

        return "redirect:/job";
    }

    @GetMapping("/user/bookmark")
    public String bookmarkList(HttpSession session, Model model) {
        UserResponse.SessionUserDTO sessionUser = (UserResponse.SessionUserDTO) session.getAttribute("sessionUser");

        if (sessionUser.getCompanyId() != null) {
            throw new ExceptionApi400("잘못된 요청입니다");
        }

        List<JobBookmarkResponse.BookmarkListDTO> dtoList = jobBookmarkService.getBookmarkList(sessionUser.getId());

        model.addAttribute("model", dtoList);

        return "bookmark/jobBookmark";
    }

    @GetMapping("/user/bookmark/{bookmarkId}/delete")
    public String deleteBookmark(@PathVariable("bookmarkId") Integer bookmarkId) {
        jobBookmarkService.북마크삭제(bookmarkId);
        return "redirect:/user/bookmark";
    }

    @PostMapping("/job-bookmark/{jobId}/toggle")
    public String toggleFromDetail(@PathVariable Integer jobId, HttpSession session) {
        UserResponse.SessionUserDTO sessionUser = (UserResponse.SessionUserDTO) session.getAttribute("sessionUser");

        if (sessionUser == null) {
            throw new ExceptionApi403("권한이 없습니다");
        }

        JobBookmarkRequest.SaveDTO dto = new JobBookmarkRequest.SaveDTO();
        dto.setJobId(jobId);

        jobBookmarkService.북마크토글(dto, sessionUser.getId());

        // 현재 상세 페이지를 그대로 다시 보여주도록 redirect
        return "redirect:/job/" + jobId;
    }
}
