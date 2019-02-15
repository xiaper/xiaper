package io.xiaper.restkefu.controller.v1;

import io.xiaper.jpa.repository.AnswerRepository;
import io.xiaper.jpa.repository.SynonymRepository;
import io.xiaper.jpa.repository.TagRepository;
import io.xiaper.jpa.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 机器人、智能客服
 *
 * @author bytedesk.com
 */
@RestController
@RequestMapping("/api/robot")
public class RobotController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    UserRepository userRepository;

    @Autowired
    AnswerRepository answerRepository;

    @Autowired
    TagRepository tagRepository;

    @Autowired
    SynonymRepository synonymRepository;









}


