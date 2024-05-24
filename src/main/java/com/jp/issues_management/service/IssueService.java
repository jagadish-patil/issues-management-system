package com.jp.issues_management.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jp.issues_management.entity.Issue;
import com.jp.issues_management.repository.IssueRepository;

@Service
public class IssueService {

	@Autowired
    private IssueRepository issueRepository;

    public Issue saveIssue(Issue issue) {
        issue.setCreatedDate(LocalDateTime.now());
        return issueRepository.save(issue);
    }

    public List<Issue> getIssuesByEmail(String email) {
        return issueRepository.findByEmail(email);
    }
    
}
