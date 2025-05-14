package com.metacoding.springrocketdanv2.reply;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ReplyController {
    private final ReplyService replyService;
}
